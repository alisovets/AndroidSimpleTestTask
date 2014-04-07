package alisovets.example.simpletesttask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;

import alisovets.example.simpletesttask.bean.MutableBoolean;
import alisovets.example.simpletesttask.bean.RecordItemViewHolder;
import alisovets.example.simpletesttask.data.CascadeRecordDataProcessor;
import alisovets.example.simpletesttask.data.DataAdapter;
import alisovets.example.simpletesttask.dialog.DialogCreator;
import alisovets.example.simpletesttask.dto.CascadeRecord;
import alisovets.example.simpletesttask.dto.Record;
import alisovets.example.simpletesttask.json.RecordsJsonParser;
import alisovets.example.simpletesttask.net.ConnectChecker;
import alisovets.example.simpletesttask.net.URLReader;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * downloads and shows the record list, allows to select record to see details
 * 
 * @author Alexander Lisovets, 2014
 */
public class RecordsFragment extends Fragment implements OnItemClickListener, OnClickListener {
	private static final String EDSSON_SERVICE_URL = "http://www.edsson.com/services.php?format=json";

	private ListView mListView;
	private RecordsAdapter mAdapter;
	private List<Record> mRecordList;
	private DataAdapter mDataAdapter;
	private ProgressBar mProgressBar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View viewHierarchy = inflater.inflate(R.layout.record_activity, container, false);

		mListView = (ListView) viewHierarchy.findViewById(R.id.recordsListView);
		mProgressBar = (ProgressBar) viewHierarchy.findViewById(R.id.progressBar);
		SimpleTaskApplication application = SimpleTaskApplication.getInstance();
		mDataAdapter = application.getDataAdapter();
		mRecordList = getRecordsFromDB();
		mAdapter = new RecordsAdapter(getActivity(), mRecordList);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(this);
		mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		mListView.setItemsCanFocus(false);
		setHasOptionsMenu(true);
		return viewHierarchy;
	}

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.records_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_about:
			DialogCreator.messageDialogNoButton(getActivity(), R.string.about_title, R.string.about_text);
			return true;
		case R.id.action_refresh:
			refresh();
			return true;
		default:
			return super.onOptionsItemSelected(item);

		}
	}

	/**
	 * sets visibility of progress bar
	 * 
	 * @param visibility
	 */
	public void setProgressBarVisibility(final int visibility) {

		mProgressBar.post(new Runnable() {

			@Override
			public void run() {
				mProgressBar.setVisibility(visibility);
			}
		});
	}

	/*
	 * gets from db and returns the list of all records
	 */
	private List<Record> getRecordsFromDB() {
		return mDataAdapter.getAllRecords();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		long currentRecordId = mRecordList.get(position).getId();
		Intent intent = new Intent(getActivity(), DetailsActivity.class);
		intent.putExtra(DetailsFragment.RECORD_ID_KEY, currentRecordId);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.deleteButton:
			confirmAndDelete((Integer) v.getTag());
			break;
		case R.id.expandButton:
			RecordItemViewHolder holder = (RecordItemViewHolder) v.getTag();
			holder.changeDescriptionExpansion();
			break;
		}
	}

	/**
	 * opens the confirm dialog to delete the records with the specified
	 * position
	 * 
	 * @param position
	 *            position of the record to be deleted
	 */
	public void confirmAndDelete(final int position) {
		DialogCreator.questionYesCancelDialog(getActivity(), R.string.delete_confirm, R.string.are_you_sure_delete,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						deleteRecord(position);
					}
				});
	}

	/*
	 * delete record on the specified position in the list
	 */
	private void deleteRecord(int position) {
		long id = mRecordList.get(position).getId();
		mDataAdapter.deleteCommentByRecord(id);
		if (mDataAdapter.deleteRecord(id) != 1) {
			Log.d("log", "failed to remove the record.");
			return;
		}
		mAdapter.deleteRecord(position);
	}

	/*
	 * reloads data from the url
	 */
	private void refresh() {
		mProgressBar.setVisibility(View.VISIBLE);
		new Thread() {
			@Override
			public void run() {
				if (!ConnectChecker.checkNetworkAvailableWithDialog(getActivity())) {
					setProgressBarVisibility(View.INVISIBLE);
					return;
				}

				try {
					String json = URLReader.obtainStringFromUrl(EDSSON_SERVICE_URL);
					List<CascadeRecord> recordList = RecordsJsonParser.unmarshalRecords(json);

					mDataAdapter.deleteAllComments();
					mDataAdapter.deleteAllRecords();
					int count = CascadeRecordDataProcessor.insertRecordsInDB(recordList, mDataAdapter);
					if (count < recordList.size()) {
						Log.d("log", (recordList.size() - recordList.size()) + "/ " + recordList.size() + " records failed to insert");
					}

					mRecordList = getRecordsFromDB();
					mAdapter = new RecordsAdapter(getActivity(), mRecordList);
					mListView.post(new Runnable() {

						@Override
						public void run() {
							mListView.setAdapter(mAdapter);
						}
					});

				} catch (IOException e) {
					DialogCreator.messageDialog(getActivity(), R.string.failed_data_refresh, R.string.refresh_network_problems);
				} catch (JSONException e) {
					DialogCreator.messageDialog(getActivity(), R.string.failed_data_refresh, R.string.refresh_json_wrong);
				} finally {
					setProgressBarVisibility(View.INVISIBLE);
				}

			}
		}.start();
	}

	/*
	 * Array adapter to show records
	 */
	private class RecordsAdapter extends ArrayAdapter<Record> {
		Activity activity;
		List<Record> records;
		List<MutableBoolean> expandFlags;

		public RecordsAdapter(Activity activity, List<Record> records) {
			super(activity, R.layout.record_item, records);
			this.activity = activity;
			this.records = records;
			expandFlags = new ArrayList<MutableBoolean>();
			for (int i = 0; i < records.size(); i++) {
				expandFlags.add(new MutableBoolean());
			}
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			RecordItemViewHolder holder;

			if (convertView == null) {
				LayoutInflater inflater = activity.getLayoutInflater();
				convertView = inflater.inflate(R.layout.record_item, null, false);
				holder = new RecordItemViewHolder();
				holder.setNameTextView((TextView) convertView.findViewById(R.id.nameText));
				holder.setDescriptionTextView((TextView) convertView.findViewById(R.id.descriptionText));
				ImageView deleteButton = (ImageView) convertView.findViewById(R.id.deleteButton);
				deleteButton.setOnClickListener(RecordsFragment.this);
				holder.setDeleteButton(deleteButton);
				ImageView expandButton = (ImageView) convertView.findViewById(R.id.expandButton);
				expandButton.setOnClickListener(RecordsFragment.this);
				holder.setExpandButton(expandButton);
				holder.getExpandButton().getBackground().setAlpha(200);
				holder.getDeleteButton().getBackground().setAlpha(200);
				holder.getExpandButton().setTag(holder);
				convertView.setTag(holder);
			} else {
				holder = (RecordItemViewHolder) convertView.getTag();
			}
			Record record = records.get(position);
			MutableBoolean expandFlag = expandFlags.get(position);
			holder.getDeleteButton().setTag(position);
			holder.getDescriptionTextView().setTag(expandFlag);
			holder.getNameTextView().setText(record.getName());
			holder.getDescriptionTextView().setText(record.getDescription());
			holder.setInitDescriptionExpansion();
			return convertView;
		}

		public void deleteRecord(int position) {
			records.remove(position);
			expandFlags.remove(position);
			notifyDataSetChanged();
		}
	}

}
