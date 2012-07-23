package ru.spb.itolia.redmine.ui;

import java.util.List;

import ru.spb.itolia.redmine.R;
import ru.spb.itolia.redmine.R.drawable;
import ru.spb.itolia.redmine.R.id;
import ru.spb.itolia.redmine.R.layout;
import ru.spb.itolia.redmine.R.string;
import ru.spb.itolia.redmine.R.style;
import ru.spb.itolia.redmine.api.beans.Project;
import ru.spb.itolia.redmine.api.beans.RedmineHost;
import ru.spb.itolia.redmine.db.RedmineDBAdapter;
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

public class RedmineHostsActivity extends SherlockListActivity {
	RedmineDBAdapter DBAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light_DarkActionBar);
		super.onCreate(savedInstanceState);
		HostsDBTask task = new HostsDBTask();
		task.execute();
		/*
		 * DBAdapter = new RedmineDBAdapter(
		 * RedmineHostsActivity.this.getApplicationContext()); DBAdapter.open();
		 * List<RedmineHost> hosts = DBAdapter.getHosts(); DBAdapter.close();
		 */

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
    	menu.add(Menu.NONE, R.string.action_bar_new_host, Menu.NONE, getString(R.string.action_bar_new_host))
		.setIcon(R.drawable.new_project)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int selectedItemId = item.getItemId();
		switch (selectedItemId) {
		case android.R.id.home:
			Intent intent = new Intent(this, ProjectsActivity.class)
					.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("host_id", getIntent().getIntExtra("host_id", -1));
			startActivity(intent);
			break;
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
	
	private class HostsAdapter extends ArrayAdapter<RedmineHost> {
		private Context context;
		private List<RedmineHost> hosts;

		// private LayoutInflater mInflater;

		public HostsAdapter(Context context, List<RedmineHost> hosts) {
			super(context, R.layout.hosts_row_spinner, R.id.host_label, hosts);
			this.hosts = hosts;
			this.context = context;
		}

		class ViewHolder { // TODO fix static
			public TextView label;
			public TextView host;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View rowView = convertView;
			if (rowView == null) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				rowView = inflater.inflate(R.layout.hosts_row_spinner, null);
				ViewHolder viewHolder = new ViewHolder();
				rowView.setTag(viewHolder);

			}

			ViewHolder holder = (ViewHolder) rowView.getTag();
			holder.host = (TextView) rowView.findViewById(R.id.host_address);
			holder.label = (TextView) rowView.findViewById(R.id.host_label);
			RedmineHost p = (RedmineHost) hosts.get(position);
			holder.host.setText(p.getAddress());
			holder.label.setText(p.getLabel());
			return rowView;
		}
	}

	private class HostsDBTask extends AsyncTask<Void, Void, List<RedmineHost>> {

		@Override
		protected List<RedmineHost> doInBackground(Void... params) {
			// String host = params[0];
			DBAdapter = new RedmineDBAdapter(
					RedmineHostsActivity.this.getApplicationContext());
			DBAdapter.open();
			List<RedmineHost> list = DBAdapter.getHosts();
			DBAdapter.close();

			return list;
		}

		@Override
		protected void onPostExecute(List<RedmineHost> projects) {
			HostsAdapter mAdapter = new HostsAdapter(RedmineHostsActivity.this,
					projects);
			setListAdapter(mAdapter);
		}
	}

}
