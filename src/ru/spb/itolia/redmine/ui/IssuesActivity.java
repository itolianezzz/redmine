package ru.spb.itolia.redmine.ui;


import java.util.List;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;

import ru.spb.itolia.redmine.R;
import ru.spb.itolia.redmine.R.drawable;
import ru.spb.itolia.redmine.R.id;
import ru.spb.itolia.redmine.R.layout;
import ru.spb.itolia.redmine.R.string;
import ru.spb.itolia.redmine.R.style;
import ru.spb.itolia.redmine.api.RedmineApiManager;
import ru.spb.itolia.redmine.api.beans.Issue;
import ru.spb.itolia.redmine.db.RedmineDBAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class IssuesActivity extends SherlockListActivity {
	private IssuesAdapter mAdapter;
	private SharedPreferences settings;
	private final String PREFS_NAME = "prefs";
	RedmineDBAdapter DBAdapter;


	@Override
    public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock);
		super.onCreate(savedInstanceState);
    	IssuesTask task = new IssuesTask();
    	task.execute(getIntent().getStringExtra("projectId"));
   	
	}
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
    	menu.add("New issue")
        	.setIntent(new Intent(this, NewIssueActivity.class))
        	.setIcon(R.drawable.new_task)
        	.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    	
    	menu.add("Search")
            .setIcon(R.drawable.search)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
 
    	menu.add(Menu.NONE, R.string.action_bar_refresh, Menu.NONE, getString(R.string.action_bar_refresh))
    		.setIcon(R.drawable.refresh)
    		.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    	    	
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected (MenuItem item) {
		int selectedItemId = item.getItemId();
    	switch (selectedItemId) {
		case android.R.id.home:
			Intent intent = new Intent(this, ProjectsActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.string.action_bar_refresh:
	    	IssuesTask task = new IssuesTask();
	    	task.execute(getIntent().getStringExtra("projectId"));
			break;
    	}
    	return false;   
    	
    }
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Issue item = (Issue) getListAdapter().getItem(position);
		String issueId = item.getId();
		Intent intent = new Intent(this, IssueActivity.class);
		intent.putExtra("issueId", issueId);
		startActivity(intent);
	}
	
	private class IssuesAdapter extends ArrayAdapter<Issue> {
    	private Context context;
        private List<Issue> issues;
        private LayoutInflater mInflater;
 
        public IssuesAdapter(Context context, List<Issue> objects) {
			super(context, R.layout.issue_row, objects);
			this.issues = objects;
			this.context = context;
		}
        
        class ViewHolder { //TODO fix static
    		public TextView issueId;
    		public TextView issueSubject;

    	}
        
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
        	View rowView = convertView;
        	if (rowView == null) {
    			LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    			rowView = inflater.inflate(R.layout.issue_row, null);
    			ViewHolder viewHolder = new ViewHolder();
    			viewHolder.issueId = (TextView) rowView.findViewById(R.id.issueId);
    			viewHolder.issueSubject = (TextView) rowView.findViewById(R.id.issueName);

    			rowView.setTag(viewHolder);
    		}

    		ViewHolder holder = (ViewHolder) rowView.getTag();
    		Issue p = issues.get(position);
    		holder.issueId.setText(p.getId());
    		holder.issueSubject.setText(p.getSubject());

    		return rowView;
        }
 
    }

	private class IssuesTask extends AsyncTask<String, Void, List<Issue>> {

		@Override
		protected void onPreExecute() {
			DBAdapter = new RedmineDBAdapter(IssuesActivity.this.getApplicationContext());
			DBAdapter.open();
			List<Issue> issues = DBAdapter.getIssues();
	        if(issues.size() >= 1) {
	        	System.out.println("taking data from db");
	        	IssuesAdapter mAdapter = new IssuesAdapter(IssuesActivity.this, issues);
	        	setListAdapter(mAdapter);
	        }
	        DBAdapter.close();
		}
		
		@Override
		protected List<Issue> doInBackground(String... params) {
			settings = getSharedPreferences(PREFS_NAME, 0);
			String api_key = settings.getString("api_key", null);
			String host = settings.getString("host", null);
			String projectId = params[0];
			RedmineApiManager mgr = new RedmineApiManager(host);
			List<Issue> issues = null;
			try {
				issues = mgr.getIssues(api_key, projectId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			
			return issues;
		}
		
		@Override
		protected void onPostExecute(List<Issue> issues) {
			RedmineDBAdapter DBAdapter = new RedmineDBAdapter(IssuesActivity.this.getApplicationContext());
			DBAdapter.open();
			for(Issue issue: issues) {
				DBAdapter.saveIssue(issue);
			}
			DBAdapter.close();
			System.out.println("taking data from web");
			IssuesAdapter mAdapter = new IssuesAdapter(IssuesActivity.this, issues);
	        setListAdapter(mAdapter);
		}
		
	}

}