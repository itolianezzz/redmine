package ru.spb.itolia.redmine;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import ru.spb.itolia.redmine.api.beans.Project;
import ru.spb.itolia.redmine.api.beans.RedmineAccount;
import ru.spb.itolia.redmine.api.beans.RedmineHost;
import ru.spb.itolia.redmine.db.RedmineDBAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: itolia
 * Date: 28.08.12
 * Time: 10:36
 */
public class RedmineApp extends Application{
    private RedmineDBAdapter DBAdapter;
    private static final String TAG = "Redmine.Application";


    @Override
    public void onCreate(){
        super.onCreate();
        DBAdapter = new RedmineDBAdapter(getApplicationContext());
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

    public List<RedmineHost> getHosts() {
        return DBAdapter.getHosts();
    }

    public Boolean checkHost(Map<String, String> credentials) {
        return DBAdapter.checkHost(credentials);
    }

    public RedmineHost getHost(int host_id) {
        return DBAdapter.getHost(host_id);
    }

    public List<Project> getProjects(Integer host_id) {
        return DBAdapter.getProjects(host_id);
    }

    public String getApi_key(Integer host_id) {
        return DBAdapter.getApi_key(host_id);
    }

    public void saveProject(Project project) {
        DBAdapter.saveProject(project);
    }
}
