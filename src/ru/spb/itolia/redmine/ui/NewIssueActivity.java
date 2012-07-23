package ru.spb.itolia.redmine.ui;

import ru.spb.itolia.redmine.R;
import ru.spb.itolia.redmine.R.style;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;

public class NewIssueActivity extends SherlockActivity {
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock);
		super.onCreate(savedInstanceState);
	
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    	return true;
	}
}
