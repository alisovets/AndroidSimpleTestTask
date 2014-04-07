package alisovets.example.simpletesttask.view;

import alisovets.example.simpletesttask.bean.RecordItemViewHolder;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class RecordLinearLayout extends LinearLayout {
	public RecordLinearLayout(Context context) {
		super(context);
	}

	public RecordLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// in this point all subviews have sizes and it is possible to determine
		// or the entire text in the field
		RecordItemViewHolder holder = (RecordItemViewHolder) getTag();
		holder.changeExpandButtonVisibility();
		super.onLayout(changed, l, t, r, b);
	}

}
