package ru.spb.itolia.redmine.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RedmineDBHelper extends SQLiteOpenHelper {
	private final static int DATABASE_VERSION = 1;
	private final static String DATABASE_NAME = "redmine";
    private static final String ROWID = "_id";
    private static final String ISSUE_ID = "issue_id";
    private static final String PROJECT_ID = "project_id";
    private static final String TRACKER_ID = "tracker";
    private static final String STATUS_ID = "status";
    private static final String AUTHOR_ID = "author";
    private static final String ASSIGNED_TO = "assigned_to";
    private static final String SUBJECT = "subject";
    private static final String DESCRIPTION = "description";
    private static final String START_DATE = "start_date";
    private static final String DUE_DATE = "due_date";
    private static final String HOST_ID = "host_id";
    private static final String PROJECT_NAME = "name";
    private static final String PROJECT_IDENTIFIER = "identifier";
    private static final String CREATED_ON = "created_on";
    private static final String IS_DEFAULT_STATUS = "is_default";
    private static final String IS_CLOSED_STATUS = "is_closed";
    private static final String USER_ID = "user_id";
    private static final String LOGIN = "login";
    private static final String FIRST_NAME = "FirstName";
    private static final String LAST_NAME = "LastName";
    private static final String MAIL_ADDRESS = "mail";
    private static final String HOST_ADDRESS = "address";
    private static final String HOST_LABEL = "label";
    private static final String USERNAME = "username";
    private static final String API_KEY_FIELD = "api_key";
    protected final String USERS_TABLE_NAME = "users";
    protected final String PROJECTS_TABLE_NAME = "projects";
    protected final String ISSUES_TABLE_NAME = "issues";
    protected final String TRACKERS_TABLE_NAME = TRACKER_ID + "s";
    protected final String STATUSES_TABLE_NAME = STATUS_ID + "es";
    protected final String MEMBERSHIPS_TABLE_NAME = "memberships";
    protected final String HOSTS_TABLE_NAME = "hosts";
    protected final String KEYS_TABLE_NAME = API_KEY_FIELD + "s";
    protected final String ACCOUNTS_TABLE_NAME = "accounts";
    private static final String STATUS_NAME = "name";
    private static final String TRACKER_NAME = "name";


    public RedmineDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + ISSUES_TABLE_NAME + " (" +
                ROWID + " INTEGER PRIMARY KEY, " +
                ISSUE_ID + " NUMERIC, " +
                PROJECT_ID + " NUMERIC, " +
                TRACKER_ID + " NUMERIC, " +
                STATUS_ID + " NUMERIC, " +
                AUTHOR_ID + " NUMERIC, " +
                ASSIGNED_TO + " NUMERIC, " +
                SUBJECT + " TEXT, " +
                DESCRIPTION + " TEXT, " +
                START_DATE + " TEXT, " +  //TODO fix TEXT -> DATE
                DUE_DATE + " TEXT);");    //TODO fix TEXT -> DATE
        db.execSQL("CREATE TABLE " + MEMBERSHIPS_TABLE_NAME + " (" +
                HOST_ID + " NUMERIC, " +
                PROJECT_ID + " NUMERIC, user NUMERIC);");
        db.execSQL("CREATE TABLE "
                + PROJECTS_TABLE_NAME + " (" +
                ROWID + " INTEGER PRIMARY KEY, " +
                PROJECT_ID + " NUMERIC, " +
                PROJECT_NAME + " TEXT, " +
                PROJECT_IDENTIFIER + " TEXT, " +
                DESCRIPTION + " TEXT, " +
                CREATED_ON + " TEXT, " +
                HOST_ID + " NUMERIC);");
        db.execSQL("CREATE TABLE " + STATUSES_TABLE_NAME + " (" +
                ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                HOST_ID + " NUMERIC, " +
                STATUS_ID + "_id NUMERIC, " +
                STATUS_NAME + " TEXT, " +
                IS_DEFAULT_STATUS + " NUMERIC, " +
                IS_CLOSED_STATUS + " NUMERIC);"); //Statuses
        db.execSQL("CREATE TABLE " + TRACKERS_TABLE_NAME + " (" +
                ROWID + " INTEGER PRIMARY KEY, " +
                TRACKER_ID + "_id NUMERIC, " +
                TRACKER_NAME + " TEXT, " +
                HOST_ID + " NUMERIC);"); //Trackers
        db.execSQL("CREATE TABLE " + USERS_TABLE_NAME + " (" +
                ROWID + " INTEGER PRIMARY KEY, " +
                USER_ID + " NUMERIC, " +
                LOGIN + " TEXT, " +
                FIRST_NAME + " TEXT, " +
                LAST_NAME + " TEXT, " +
                MAIL_ADDRESS + " TEXT, " + CREATED_ON + " TEXT, " + HOST_ID + " NUMERIC);"); //Users
        db.execSQL("CREATE TABLE " + HOSTS_TABLE_NAME  + " (" +
                HOST_ID + " INTEGER PRIMARY KEY, " +
                HOST_ADDRESS + " TEXT, " +
                HOST_LABEL + " TEXT);"); //Hosts
        db.execSQL("CREATE TABLE " + ACCOUNTS_TABLE_NAME + " (" +
                ROWID + " INTEGER PRIMARY KEY, " +
                USERNAME + " TEXT, " +
                API_KEY_FIELD + " TEXT, " +
                HOST_ID + " INTEGER);"); //RedmineAccounts
    }

    @Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
