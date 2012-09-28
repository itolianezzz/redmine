package ru.spb.itolia.redmine.ui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import ru.spb.itolia.redmine.R;
import ru.spb.itolia.redmine.RedmineApp;
import ru.spb.itolia.redmine.adapters.HostsAdapter;
import ru.spb.itolia.redmine.api.beans.RedmineSession;
import ru.spb.itolia.redmine.util.Settings;

import java.util.List;

public class HostsActivity extends SherlockListActivity {
    protected RedmineApp app;
    public static Context context;


	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Sherlock_Light_DarkActionBar);
        context = this;
        app = (RedmineApp) getApplication();
        setContentView(R.layout.hosts_activity_layout);
        Boolean HostsAvailable = Settings.getBoolean(Settings.PREF_HOSTS_AVAILABLE);
        if(HostsAvailable) {
            HostsDBTask task = new HostsDBTask();
            task.execute();
        } else {
            getListView().setEmptyView(findViewById(android.R.id.empty));
        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
    	menu.add(Menu.NONE, R.string.action_bar_new_host, Menu.NONE, getString(R.string.action_bar_new_host))
		.setIcon(R.drawable.new_project)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int selectedItemId = item.getItemId();
		switch (selectedItemId) {
/*		case android.R.id.home:
			Intent intent = new Intent(this, ProjectsActivity.class)
					.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("host_id", getIntent().getIntExtra("host_id", -1));
			startActivity(intent);
			break;*/
		case R.string.action_bar_new_host:
			Intent mIntent = new Intent(this, LoginActivity.class);
			//mIntent.putExtra("from_hosts_list", true);
			startActivityForResult(mIntent, 0);
			break;
		}
		return false;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		RedmineSession item = (RedmineSession) getListAdapter().getItem(position);
		Integer host_id = item.getHost_id();
		Intent intent = new Intent(this, ProjectsActivity.class);
		intent.putExtra(RedmineApp.HOST_ID, host_id);
		startActivity(intent);
		finish();
	}


	


	private class HostsDBTask extends AsyncTask<Void, Void, List<RedmineSession>> {

		@Override
		protected List<RedmineSession> doInBackground(Void... params) {
			List<RedmineSession> sessions = app.getSessions();
			return sessions;
		}

		@Override
		protected void onPostExecute(List<RedmineSession> sessions) {
			HostsAdapter mAdapter = new HostsAdapter(HostsActivity.this, sessions);
			setListAdapter(mAdapter);
		}
	}

}
