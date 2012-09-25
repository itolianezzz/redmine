package ru.spb.itolia.redmine.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import ru.spb.itolia.redmine.api.beans.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RedmineDBAdapter {
	private final Context context;
	//private RedmineDBHelper RedmineDBHelper; 
	private SQLiteDatabase db;
	private RedmineDBHelper DBHelper; 
	
	public RedmineDBAdapter(Context ctx) {  
	    this.context = ctx;  
	    DBHelper = new RedmineDBHelper(context);  
	} 

	public synchronized RedmineDBAdapter open() {
		db = DBHelper.getWritableDatabase();
		return this;
	}
	
	public synchronized void close() {
		//DBHelper.close();  
		db.close();
	}
	
	public synchronized List<Project> getProjects(Integer host_id) {
		//TODO: query host id
		//Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelper.PROJECTS_TABLE_NAME + " INNER JOIN " + DBHelper.HOSTS_TABLE_NAME + " ON " + DBHelper.PROJECTS_TABLE_NAME + ".host = " + DBHelper.HOSTS_TABLE_NAME + ".id WHERE address = '" + address + "';", null);
		Cursor cursor = db.query(DBHelper.PROJECTS_TABLE_NAME, new String[] {"*"}, "host_id=?", new String[] {host_id.toString()}, null, null, null);
		cursor.moveToFirst();
		List<Project> list = new ArrayList<Project>();
		while(!cursor.isAfterLast()){
			Project project = new Project();
			project.setId(cursor.getInt(1));
			project.setName(cursor.getString(2));
			project.setIdentifier(cursor.getString(3));
			project.setCreated_on(cursor.getString(4));
			list.add(project);
			cursor.moveToNext();
		}
		cursor.close();
		return list;
	}

    public synchronized List<Project> getProjects(String address) {
        Cursor cursor = db.query(DBHelper.HOSTS_TABLE_NAME, new String[] {"host_id"}, "address=?", new String[] {address}, null, null, null);
        cursor.moveToFirst();
        String host_id = cursor.getString(1);
        return getProjects(host_id);
    }
	
	public synchronized void saveProject(Project project) {
		Cursor cursor = db.query(DBHelper.PROJECTS_TABLE_NAME, new String[] {"identifier, host_id"}, "identifier=? AND host_id=?", new String[] {project.getIdentifier(), project.getHost_id().toString()}, null, null, null);
		ContentValues values = new ContentValues();
		values.put("project_id", project.getId());
		values.put("name", project.getName());
		values.put("identifier", project.getIdentifier());
		values.put("description", project.getDescription());
		values.put("created_on", project.getCreated_on());
		values.put("host_id", project.getHost_id());
		if(cursor.getCount() < 1) {
			db.insert(DBHelper.PROJECTS_TABLE_NAME, null, values);
		} else {
			db.update(DBHelper.PROJECTS_TABLE_NAME, values, "identifier=? AND host_id=?", new String[] {project.getIdentifier(), project.getHost_id().toString()});
		}
	}

	public synchronized List<Issue> getIssues() {
		Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelper.ISSUES_TABLE_NAME, null);
		cursor.moveToFirst();
		List<Issue> list = new ArrayList<Issue>();
		while(!cursor.isAfterLast()){
			Issue issue = new Issue();
			issue.setId(cursor.getString(0));
			issue.setProject(cursor.getString(1));
			issue.setTracker(cursor.getString(2));
			issue.setStatus(cursor.getString(3));
			issue.setAuthor(cursor.getString(4));
			issue.setAssigned_to(cursor.getString(5));
			issue.setSubject(cursor.getString(6));
			issue.setDescription(cursor.getString(7));
			issue.setStart_date(cursor.getString(8));
			issue.setDue_date(cursor.getString(9));
			list.add(issue);
			cursor.moveToNext();
		}
		cursor.close();
		return list;
	}

	public synchronized void saveIssue(Issue issue) {
		//TODO: сделать проверку есть ли уже такая запись в БД
		ContentValues values = new ContentValues();
		values.put("id", issue.getId());
		values.put("project", issue.getProject());
		values.put("tracker", issue.getTracker());
		values.put("status", issue.getStatus());
		values.put("author", issue.getAuthor());
		values.put("assigned_to", issue.getAssigned_to());
		values.put("subject", issue.getSubject());
		values.put("description", issue.getDescription());
		values.put("start_date", issue.getStart_date());
		values.put("due_date", issue.getDue_date());
		db.insert(DBHelper.ISSUES_TABLE_NAME, null, values);
	}
	
	public synchronized Cursor rawQuery(String sql, String[] selectionArgs) {
		return db.rawQuery(sql, selectionArgs);
	}
	
	public synchronized long saveHost(RedmineHost host) {
		ContentValues values = new ContentValues();
		values.put("address", host.getAddress());
		values.put("label", host.getLabel());
        long id = db.insert(DBHelper.HOSTS_TABLE_NAME, null, values);
	    System.out.println("Inserted host to DB");
        return id;
	}
	
	public synchronized List<RedmineHost> getHosts() {
		Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelper.HOSTS_TABLE_NAME, null);
		cursor.moveToFirst();
		List<RedmineHost> list = new ArrayList<RedmineHost>();
		while(!cursor.isAfterLast()){
			RedmineHost host = new RedmineHost(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
			list.add(host);
			cursor.moveToNext();
		}
		cursor.close();
		return list;
	}
	//TODO fix method 
	public synchronized String getApi_key(Integer host_id) {
		//Cursor cursor = db.rawQuery("SELECT api_key FROM " + DBHelper.HOSTS_TABLE_NAME + " WHERE address = '" + host + "';", null);
		Cursor cursor = db.query(DBHelper.KEYS_TABLE_NAME, new String[] {"api_key"},"host_id = ?", new String[] {host_id.toString()}, null, null, null);
		cursor.moveToFirst();
		return cursor.getString(0);
	}

	public synchronized RedmineHost getHost(Integer host_id) {
		Cursor cursor = db.query(DBHelper.HOSTS_TABLE_NAME, new String[] {"*"},"host_id = ?", new String[] {host_id.toString()}, null, null, null);
		cursor.moveToFirst();
		RedmineHost host = new RedmineHost(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
		return host;
	}
	
	public synchronized void saveUser(User user) {
		ContentValues values = new ContentValues();
		values.put("user_id", user.getId());
		values.put("login_layout", user.getLogin());
		values.put("mail", user.getMail());
		values.put("firstname", user.getFirstname());
		values.put("lastname", user.getLastname());
		values.put("created_on", user.getCreated_on());
		values.put("host_id", user.getHost());
		Cursor cursor = db.query(DBHelper.USERS_TABLE_NAME, new String[] {"user_id, host_id"}, "user_id=? AND host_id=?", new String[] {user.getId(), user.getHost().toString()}, null, null, null);
		if(cursor.getCount() < 1) {
			db.insert(DBHelper.USERS_TABLE_NAME, null, values);
			System.out.println("Inserted user to DB");
		} else {
			db.update(DBHelper.USERS_TABLE_NAME, values, "user_id = ? AND host_id=?", new String[] {user.getId(), user.getHost().toString()});
			System.out.println("Updated user in DB");
		}

	}

	public synchronized RedmineHost getHostByAddress(String address) {
		Cursor cursor = db.query(DBHelper.HOSTS_TABLE_NAME, new String[] {"*"}, "address = ?", new String[] {address}, null, null, null);
		cursor.moveToFirst();
		RedmineHost host = new RedmineHost(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
		cursor.close();
		return host;
	}

	public void saveApi_key(Integer host_id, String api_key) {
		ContentValues values = new ContentValues();
		values.put("host_id", host_id);
		values.put("api_key", api_key);
		Cursor cursor = db.rawQuery("SELECT * FROM " + DBHelper.KEYS_TABLE_NAME + " WHERE host_id = '" + host_id + "';", null);
		if(cursor.getCount() < 1) {
			db.insert(DBHelper.KEYS_TABLE_NAME, null, values);
			System.out.println("Inserted api_key to DB");
		} else {
			db.update(DBHelper.KEYS_TABLE_NAME, values, "host_id = ?", new String[] {host_id.toString()});
			System.out.println("Updated api_key in DB");
		}
		
	}

    public void saveAccount(RedmineAccount account) {
        ContentValues values = new ContentValues();
        values.put("username", account.getUsername());
        values.put("api_key", account.getApi_key());
        values.put("host_id", account.getHost_id());
        db.insert(DBHelper.ACCOUNTS_TABLE_NAME, null, values);
    }

    public Boolean checkHost(Map<String, String> credentials) {
        Cursor cursor = db.rawQuery("SELECT * FROM address, username  INNER JOIN accounts ON hosts.host_id=accounts.host_id WHERE address='" + credentials.get("host")+"' AND username = '"+ credentials.get("username")+"'", null);
        if(cursor.getCount() > 0) {
            return true;
        } else {
            return  false;
        }
    }

    public List<RedmineSession> getSessions() {
        Cursor cursor = db.rawQuery("SELECT address, label, username, api_key, host_id FROM accounts INNER JOIN hosts ON hosts.host_id=accounts.host_id ", null);
        cursor.moveToFirst();
        List<RedmineSession> sessions = new ArrayList<RedmineSession>();
        while(!cursor.isAfterLast()){
            RedmineSession session = new RedmineSession(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getInt(4));
            sessions.add(session);
            cursor.moveToNext();
        }
    return sessions;
    }
}
