package alisovets.example.simpletesttask;

import alisovets.example.simpletesttask.data.DataAdapter;
import android.app.Application;

/**
 * Simple demo application class initializes and stores
 * some common variables for several activities.
 * 
 * @author Alexander Lisovets 2014
 * 
 */
public class SimpleTaskApplication extends Application {
	private static SimpleTaskApplication mInstance;
	DataAdapter mDataAdapter;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		mDataAdapter = new DataAdapter(getApplicationContext());
	}

	public static SimpleTaskApplication getInstance() {
		return mInstance;
	}

	public DataAdapter getDataAdapter() {
		return mDataAdapter;
	}

}
