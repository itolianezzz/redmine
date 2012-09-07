package ru.spb.itolia.redmine.ui;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import ru.spb.itolia.redmine.R;
import ru.spb.itolia.redmine.RedmineApp;
import ru.spb.itolia.redmine.api.beans.RedmineHost;
import ru.spb.itolia.redmine.api.beans.RedmineSession;
import ru.spb.itolia.redmine.util.Settings;

import java.util.List;

public class HostsActivity extends SherlockListActivity {
    //private final String PREFS_NAME = "prefs";
    //RedmineDBAdapter DBAdapter;
    protected RedmineApp app;
    public static Context context;
    //SharedPreferences Prefs;

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
			mIntent.putExtra("from_hosts_list", true);
			startActivityForResult(mIntent, 0);
			break;
		}
		return false;
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		RedmineHost item = (RedmineHost) getListAdapter().getItem(position);
		Integer host_id = item.getId();
		Intent intent = new Intent(this, ProjectsActivity.class);
		intent.putExtra("host_id", host_id);
		startActivity(intent);
		finish();
	}


	
	private class HostsAdapter extends ArrayAdapter<RedmineSession> {
		private Context context;
		private List<RedmineSession> sessions;

		public HostsAdapter(Context context, List<RedmineSession> sessions) {
			super(context, R.layout.hosts_row, R.id.host_label, sessions);
			this.sessions = sessions;
			this.context = context;
		}

		class ViewHolder {
			public TextView label;
			public TextView host;
            public TextView username;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View rowView = convertView;
			if (rowView == null) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				rowView = inflater.inflate(R.layout.hosts_row, null);
				ViewHolder viewHolder = new ViewHolder();
				rowView.setTag(viewHolder);
			}
			ViewHolder holder = (ViewHolder) rowView.getTag();
			holder.host = (TextView) rowView.findViewById(R.id.address);
			holder.label = (TextView) rowView.findViewById(R.id.host_label);
            holder.username = (TextView) rowView.findViewById(R.id.username);
			RedmineSession p = (RedmineSession) sessions.get(position);   //TODO change for RedmineSession
			holder.host.setText(p.getAddress());
			holder.label.setText(p.getLabel());
            holder.username.setText(p.getUsername());
			return rowView;
		}
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
