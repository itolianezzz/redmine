package ru.spb.itolia.redmine.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RedmineDBHelper extends SQLiteOpenHelper {
	private final static int DATABASE_VERSION = 1;
	private final static String DATABASE_NAME = "redmine";
	protected final String USERS_TABLE_NAME = "users";
    protected final String PROJECTS_TABLE_NAME = "projects";
    protected final String ISSUES_TABLE_NAME = "issues";
    protected final String TRACKERS_TABLE_NAME = "trackers";
    protected final String STATUSES_TABLE_NAME = "statuses";
    protected final String MEMBERSHIPS_TABLE_NAME = "memberships";
    protected final String HOSTS_TABLE_NAME = "hosts";
    protected final String KEYS_TABLE_NAME = "api_keys";
    protected final String ACCOUNTS_TABLE_NAME = "accounts";


    public RedmineDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE "
				+ ISSUES_TABLE_NAME
				+ " (_id INTEGER PRIMARY KEY, issue_id NUMERIC, project_id NUMERIC, tracker NUMERIC, status NUMERIC, author NUMERIC, assigned_to NUMERIC, subject TEXT, description TEXT, start_date TEXT, due_date TEXT);"); //Issues
		db.execSQL("CREATE TABLE " + MEMBERSHIPS_TABLE_NAME
				+ " (host_id NUMERIC, project NUMERIC, user NUMERIC);"); //Memberships 
		db.execSQL("CREATE TABLE "
				+ PROJECTS_TABLE_NAME
				+ " (_id INTEGER PRIMARY KEY, project_id NUMERIC, name TEXT, identifier TEXT, description TEXT, created_on TEXT, host_id NUMERIC);"); //Projects
		db.execSQL("CREATE TABLE " + STATUSES_TABLE_NAME
				+ " (_id INTEGER PRIMARY KEY AUTOINCREMENT, host_id NUMERIC, status_id NUMERIC, name TEXT, is_default NUMERIC, is_closed NUMERIC);"); //Statuses
		db.execSQL("CREATE TABLE " + TRACKERS_TABLE_NAME
				+ " (_id INTEGER PRIMARY KEY, tracker_id NUMERIC, name TEXT, host_id NUMERIC);"); //Trackers
		db.execSQL("CREATE TABLE "
				+ USERS_TABLE_NAME
				+ " (_id INTEGER PRIMARY KEY, user_id NUMERIC, login_layout TEXT, FirstName TEXT, LastName TEXT, mail TEXT, created_on TEXT, host_id NUMERIC);"); //Users
		db.execSQL("CREATE TABLE "
				+ HOSTS_TABLE_NAME
				+ " (host_id INTEGER PRIMARY KEY, address TEXT, label TEXT);"); //Hosts
        db.execSQL("CREATE TABLE "
                + ACCOUNTS_TABLE_NAME
                + " (_id INTEGER PRIMARY KEY, username TEXT, api_key TEXT, host_id INTEGER);"); //RedmineAccounts
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
