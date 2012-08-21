package ru.spb.itolia.redmine.ui;

import java.util.HashMap;
import java.util.Map;

import ru.spb.itolia.redmine.R;
import ru.spb.itolia.redmine.api.RedmineApiManager;
import ru.spb.itolia.redmine.api.beans.RedmineHost;
import ru.spb.itolia.redmine.api.beans.User;
import ru.spb.itolia.redmine.db.RedmineDBAdapter;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockActivity;

public class LoginActivity extends SherlockActivity implements OnClickListener {
	private final String PREFS_NAME = "prefs";
	EditText loginEdit;
	EditText passwordEdit;
	EditText hostEdit;
	SharedPreferences settings;
	RedmineDBAdapter DBAdapter; // = new RedmineDBAdapter(Login.this.getApplicationContext());
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		//setTheme(R.style.login);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		DBAdapter = new RedmineDBAdapter(LoginActivity.this.getApplicationContext());
		settings = getSharedPreferences(PREFS_NAME, 0);
		if(getIntent().getBooleanExtra("from_hosts_list", false)) {
			settings.edit().remove("host_id").commit();
		}
		Integer host_id = settings.getInt("host_id", -1);
		if (host_id < 0) {
			loginEdit = (EditText) findViewById(R.id.login_edit);
			passwordEdit = (EditText) findViewById(R.id.password_edit);
			hostEdit = (EditText) findViewById(R.id.host_edit);
			Button loginButton = (Button) findViewById(R.id.login_button);
			loginButton.setOnClickListener(this);

		} else {
			Intent intent = new Intent(this, ProjectsActivity.class);
			intent.putExtra("host_id", host_id);
			startActivity(intent);
			finish();
		}
	}

	@SuppressWarnings("unchecked")
	public void onClick(View arg0) {
		Map<String, String> credentials = new HashMap<String, String>();
		credentials.put("login", loginEdit.getText().toString());
		credentials.put("pass", passwordEdit.getText().toString());
		credentials.put("host", hostEdit.getText().toString());
		LoginTask task = new LoginTask();
		task.execute(credentials);
		
	}

	public class LoginTask extends AsyncTask<Map<String, String>, Void, Integer> {
		protected ProgressDialog dialog;
		
		protected void onPreExecute(){
			dialog = ProgressDialog.show(LoginActivity.this, "", 
                    "Loading. Please wait...", true);
		}
		
		@Override
		protected Integer doInBackground(Map<String, String>... params) {
			String login = params[0].get("login");
			String pass = params[0].get("pass");
			String host = params[0].get("host");
			String api_key;
			String label;
			User current_user;
			RedmineApiManager mgr = new RedmineApiManager(login, pass, host);
			try {
				api_key = mgr.getApiKey();
				current_user = mgr.getCurrentUser(api_key);
				label = mgr.getHostLabel();
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
			System.out.println("Saving host to DB");
			//RedmineDBAdapter DBAdapter = new RedmineDBAdapter(Login.this.getApplicationContext());
			DBAdapter.open();
			DBAdapter.saveHost(new RedmineHost(null, LoginActivity.this.hostEdit.getText().toString(), label));
			RedmineHost mHost = DBAdapter.getHostByAddress(host);
			current_user.setHost(mHost.getId());
			DBAdapter.saveUser(current_user);
			DBAdapter.saveApi_key(mHost.getId(), api_key);
			DBAdapter.close();
			settings.edit().putInt("host_id", mHost.getId()).commit();
			return mHost.getId();
		}
		
		protected void onPostExecute(Integer host_id) {
			Intent intent = new Intent(LoginActivity.this, ProjectsActivity.class);
		    intent.putExtra("host_id", host_id);
		    setResult(RESULT_OK, intent);
		    dialog.dismiss();
		    startActivity(intent);
		    finish();
		    
		}
		
	}


}
