package ru.spb.itolia.redmine;

import android.app.Application;
import android.util.Log;
import ru.spb.itolia.redmine.api.beans.RedmineAccount;
import ru.spb.itolia.redmine.api.beans.RedmineHost;
import ru.spb.itolia.redmine.db.RedmineDBAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: itolia
 * Date: 28.08.12
 * Time: 10:36
 */
public class RedmineApp extends Application{
    private RedmineDBAdapter DBAdapter = new RedmineDBAdapter(getApplicationContext());
    private static final String TAG = "Redmine.Application";

    @Override
    public void onCreate(){
        super.onCreate();
        DBAdapter.open();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        DBAdapter.close();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        DBAdapter.close();
    }

    public long saveHost(RedmineHost host) {
        long host_id = DBAdapter.saveHost(host);
        Log.v(TAG, "Saved host. Host id is " + host_id);
        return host_id;
    }

    public void saveAccount(RedmineAccount account) {
        DBAdapter.saveAccount(account);
        Log.v(TAG, "Saved account");
    }
}
