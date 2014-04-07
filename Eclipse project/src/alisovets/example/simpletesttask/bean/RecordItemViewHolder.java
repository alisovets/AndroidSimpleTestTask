package alisovets.example.simpletesttask.bean;


import alisovets.example.simpletesttask.R;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * To store links to record item views to manage their visibilities and view parameters.
 * 
 * @author Alexander Lisovets
 *
 */
public class RecordItemViewHolder {
	private static final int NORMAL_DESCRIPTION_LINE_COUNT = 2;

	private TextView nameTextView;
	private TextView descriptionTextView;
	private ImageView deleteButton;
	private ImageView expandButton;
	

	public TextView getNameTextView() {
		return nameTextView;
	}

	public void setNameTextView(TextView nameTextView) {
		this.nameTextView = nameTextView;
	}

	public TextView getDescriptionTextView() {
		return descriptionTextView;
	}

	public void setDescriptionTextView(TextView descriptionTextView) {
		this.descriptionTextView = descriptionTextView;
	}

	public ImageView getDeleteButton() {
		return deleteButton;
	}

	public void setDeleteButton(ImageView deleteButton) {
		this.deleteButton = deleteButton;
	}

	public ImageView getExpandButton() {
		return expandButton;
	}

	public void setExpandButton(ImageView expandButton) {
		this.expandButton = expandButton;
	}

	/**
	 * changes visibilities an expand button depending on whether there has hidden description rows
	 */
	public void changeExpandButtonVisibility() {
		int lineCount = descriptionTextView.getLineCount();
		if (lineCount == 0) {
			return;
		}

		if (lineCount <= NORMAL_DESCRIPTION_LINE_COUNT) {
			expandButton.setVisibility(View.GONE);
		}
		else{
			expandButton.setVisibility(View.VISIBLE);
		}
	}
	
	
	/**
	 * exchange expands-collapses condition of the description text view to opposite and exchanges an expand-collapse button icon 
	 */
	public void changeDescriptionExpansion(){
		MutableBoolean expandFlag = (MutableBoolean)descriptionTextView.getTag(); 
		if(expandFlag.getValue()){
			descriptionTextView.setLines(NORMAL_DESCRIPTION_LINE_COUNT);
			expandFlag.setValue(false);
			expandButton.setImageResource(R.drawable.btn_expand_selector);
		}
		else{
			int lineCount = descriptionTextView.getLineCount();
			descriptionTextView.setLines(lineCount);
			expandFlag.setValue(true);
			expandButton.setImageResource(R.drawable.btn_collapse_selector);
		}
	}
	
	/**
	 * set expands-collapses condition of the description text view
	 */
	public void setInitDescriptionExpansion(){
		MutableBoolean expandFlag = (MutableBoolean)descriptionTextView.getTag(); 
		if(expandFlag.getValue()){
			int lineCount = descriptionTextView.getLineCount();
			descriptionTextView.setLines(lineCount);	
			expandButton.setImageResource(R.drawable.btn_collapse_selector);
		}
		else{
			descriptionTextView.setLines(NORMAL_DESCRIPTION_LINE_COUNT);
			expandButton.setImageResource(R.drawable.btn_expand_selector);
		}
	}
	
	
}
