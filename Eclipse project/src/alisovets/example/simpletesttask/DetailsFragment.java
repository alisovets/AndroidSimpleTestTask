package alisovets.example.simpletesttask;

import java.util.List;

import alisovets.example.simpletesttask.data.DataAdapter;
import alisovets.example.simpletesttask.dialog.DialogCreator;
import alisovets.example.simpletesttask.dto.Comment;
import alisovets.example.simpletesttask.dto.Record;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * to show the details of the selected record 
 * 
 * @author Alexander Lisovets, 2014
 *
 */
public class DetailsFragment extends Fragment implements OnClickListener{
	public static final String RECORD_ID_KEY = "recordId";
	private DataAdapter mDataAdapter;
	private Record mRecord;
	private List<Comment> mCommmentList;
	private TextView mNameTextView;
	private TextView mCodeTextView;
	private TextView mDescriptionTextView;
	private TextView mPriceTextView;
	private TextView mDisplayTextView;
	private TextView mProcessorTextView;
	private TextView mMemoryTextView;
	private TextView mHddTextView;
	private TextView mOtherTextView;
	private Button mCommentsButton;
	private LinearLayout mCommentsLayout;
	private boolean mExpandCommentFlag;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View viewHierarchy = inflater.inflate(R.layout.details_activity, container, false);

		mNameTextView = (TextView) viewHierarchy.findViewById(R.id.recordNameText);
		mCodeTextView = (TextView) viewHierarchy.findViewById(R.id.codeText);
		mDescriptionTextView = (TextView) viewHierarchy.findViewById(R.id.decrriptionText);
		mPriceTextView = (TextView) viewHierarchy.findViewById(R.id.priceText);
		mDisplayTextView = (TextView) viewHierarchy.findViewById(R.id.displayText);
		mProcessorTextView = (TextView) viewHierarchy.findViewById(R.id.processorText);
		mMemoryTextView = (TextView) viewHierarchy.findViewById(R.id.memoryText);
		mHddTextView = (TextView) viewHierarchy.findViewById(R.id.hddText);
		mOtherTextView = (TextView) viewHierarchy.findViewById(R.id.otherText);
		mCommentsButton = (Button) viewHierarchy.findViewById(R.id.commentsButton);
		mCommentsLayout = (LinearLayout) viewHierarchy.findViewById(R.id.commentsLayout);
		mCommentsButton.setOnClickListener(this);
		
		SimpleTaskApplication applicaton = SimpleTaskApplication.getInstance();
		mDataAdapter = applicaton.getDataAdapter();
		mExpandCommentFlag = false;
		setHasOptionsMenu(true);
		
		return viewHierarchy;
	}

	@Override
	public void onResume() {
		super.onResume();

		Intent intent = getActivity().getIntent();
		long recordId = intent.getLongExtra(RECORD_ID_KEY, 1L);
		mRecord = mDataAdapter.getRecord(recordId);
		Log.d("log", "onResume recordId = " + recordId  +"  mRecord= " + mRecord + " name= " + mRecord.getName() + "  " + mNameTextView);
		
		mCommmentList = mDataAdapter.getCommentsByRecord(recordId);
		mNameTextView.setText(mRecord.getName());
		mCodeTextView.setText(mRecord.getCode());
		mDescriptionTextView.setText(mRecord.getDescription());
		mPriceTextView.setText("" + mRecord.getPrice());
		mDisplayTextView.setText(mRecord.getDetails().getDisplay());
		mProcessorTextView.setText(mRecord.getDetails().getProcessor());
		mMemoryTextView.setText(mRecord.getDetails().getMemory());
		mHddTextView.setText(mRecord.getDetails().getHdd());
		mOtherTextView.setText(mRecord.getDetails().getOther());
		
		LayoutInflater inflater = getActivity().getLayoutInflater();
		
		for(Comment comment : mCommmentList){
			LinearLayout commentLayout = (LinearLayout)inflater.inflate(R.layout.comment_layout, null);
			TextView userTextView = (TextView)commentLayout.findViewById(R.id.userNameText);
			TextView commentTextView = (TextView)commentLayout.findViewById(R.id.commentText);
			userTextView.setText(comment.getName());
			commentTextView.setText(comment.getComment());
			mCommentsLayout.addView(commentLayout);
		}
		
		if(mCommmentList.size() > 0){
			mCommentsButton.setVisibility(View.VISIBLE);
			mCommentsLayout.setVisibility(View.GONE);
		}
		else{
			mCommentsButton.setVisibility(View.GONE);
			mCommentsLayout.setVisibility(View.GONE);
		}
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.details_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_about:
			DialogCreator.messageDialogNoButton(getActivity(), R.string.about_title, R.string.about_text);
			return true;
		default:
			return super.onOptionsItemSelected(item);

		}	
	}
	
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.commentsButton:
			expandCollapseComments();
			break;
		}
	}
	
	/*
	 * expands or collapses the comments list.
	 */
	private void expandCollapseComments(){
		if(mExpandCommentFlag){
			mCommentsButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.expand, 0, 0, 0);
			mCommentsLayout.setVisibility(View.GONE);
			mExpandCommentFlag = false;
		}
		else{
			mCommentsButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.collapse, 0, 0, 0);
			mCommentsLayout.setVisibility(View.VISIBLE);
			mExpandCommentFlag = true;
		}
	}

}
