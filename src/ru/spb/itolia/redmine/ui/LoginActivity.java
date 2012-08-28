package ru.spb.itolia.redmine.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.actionbarsherlock.app.SherlockActivity;
import ru.spb.itolia.redmine.R;
import ru.spb.itolia.redmine.RedmineApp;
import ru.spb.itolia.redmine.api.RedmineApiManager;
import ru.spb.itolia.redmine.api.beans.RedmineAccount;
import ru.spb.itolia.redmine.api.beans.RedmineHost;
import ru.spb.itolia.redmine.util.Settings;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends SherlockActivity implements OnClickListener {
    private static final String TAG = "Redmine.LoginActivity";
    protected RedmineApp app;
    EditText loginEdit;
	EditText passwordEdit;
	EditText hostEdit;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		//setTheme(R.style.login_layout);
		super.onCreate(savedInstanceState);
        app = (RedmineApp) getApplication();
        setContentView(R.layout.login_layout);
		loginEdit = (EditText) findViewById(R.id.login_edit);
		passwordEdit = (EditText) findViewById(R.id.password_edit);
		hostEdit = (EditText) findViewById(R.id.host_edit);
		Button loginButton = (Button) findViewById(R.id.login_button);
		loginButton.setOnClickListener(this);

	}

	public void onClick(View arg0) {
		final Map<String, String> credentials = new HashMap<String, String>();
		credentials.put("login", loginEdit.getText().toString());
		credentials.put("pass", passwordEdit.getText().toString());
		credentials.put("host", hostEdit.getText().toString());
		//TODO check if host exists in DB, and suggest to update it
        LoginTask task = new LoginTask();
        task.execute(credentials);
	}

	private class LoginTask extends AsyncTask<Map<String, String>, Void, String> {
		protected ProgressDialog dialog;
		
		@Override
        protected void onPreExecute(){
			dialog = ProgressDialog.show(LoginActivity.this, "", 
                    "Loading. Please wait...", true);  //TODO Define string in strings.xml
		}
		
		@Override
		protected String doInBackground(Map<String, String>... params) {
			String login = params[0].get("login");
			String pass = params[0].get("pass");
			String host = params[0].get("host");
			String api_key;
			String label;
            Log.v(TAG, "Creating RedmineApiManager instance, and performing login");
            RedmineApiManager mgr = new RedmineApiManager(login, pass, host);
			try {
				api_key = mgr.getApiKey();
				Log.v(TAG, "api_key is: " + api_key);
                label = mgr.getHostLabel();
			} catch (Exception e) {
				Log.e(TAG, "Error! Exception: ", e);
                e.printStackTrace();
				return null;
			}
			long host_id = app.saveHost(new RedmineHost(null, LoginActivity.this.hostEdit.getText().toString(), label));
            RedmineAccount account = new RedmineAccount(LoginActivity.this.loginEdit.getText().toString(), api_key, host_id);
            app.saveAccount(account);
            Settings.setBoolean(Settings.PREF_HOSTS_AVAILABLE, true);
			return api_key;
		}
		
		@Override
        protected void onPostExecute(String api_key) {
			if(api_key == null) {
                dialog.dismiss();
                Toast toast = Toast.makeText(LoginActivity.this, "Error! Try again", 5);
                toast.show();
            } else {
                Intent intent = new Intent(LoginActivity.this, HostsActivity.class);
		        //intent.putExtra("host_id", host_id);
		        setResult(RESULT_OK, intent);
		        dialog.dismiss();
		        startActivity(intent);
		        finish();
            }
		}
	}
}
