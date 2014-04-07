package alisovets.example.simpletesttask;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

/**
 * Shows details
 * 
 * @author Alexander Lisovets, 2014
 */
public class DetailsActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().add(R.id.container, new DetailsFragment()).commit();
		}
	}

}
