package ru.spb.itolia.redmine.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ru.spb.itolia.redmine.api.beans.CustomField;
import ru.spb.itolia.redmine.api.beans.Issue;
import ru.spb.itolia.redmine.api.beans.IssueStatus;
import ru.spb.itolia.redmine.api.beans.Project;
import ru.spb.itolia.redmine.api.beans.StatusList;
import ru.spb.itolia.redmine.api.beans.User;

public class RedmineApiManager {
	public static final String CHARSET = "UTF-8";
	private String login;
	private String password;
	private String REDMINE_HOST = null;
	private String api_key;
	
	public RedmineApiManager(String login, String password, String REDMINE_HOST) {
		super();
		this.login = login;
		this.password = password;
		this.REDMINE_HOST = REDMINE_HOST;
	}

	

	public RedmineApiManager(String host) {
		super();
		this.REDMINE_HOST = host;
	}

	public RedmineApiManager(String host, String api_key) {
		super();
		this.REDMINE_HOST = host;
		this.api_key = api_key;
	}


	public String getHostLabel() throws HttpException, IOException {
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet method = new HttpGet(REDMINE_HOST);
		HttpResponse response = client.execute(method);
		
		Source parse = new Source(response.getEntity().getContent());
		String label = parse.getFirstElement(HTMLElementName.TITLE).getContent().toString();
		return label;
	}
	
	
	public String getApiKey() throws Exception { //TODO: Fix exceptions
		String token = null;
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet first = new HttpGet(REDMINE_HOST + "/my/account");
		//first.setFollowRedirects(true);
		HttpResponse response = client.execute(first);
		Source parse = new Source(response.getEntity().getContent());
		//System.out.println("response:" + response.toString() );
		List<Element> elements = parse.getAllElements("meta");
		for (Element el : elements) {
			if (el.getAttributeValue("name") != null) {
				if (el.getAttributeValue("name").equals("csrf-token")) {
					token = el.getAttributeValue("content");
				}
			}
		}
		HttpPost second = new HttpPost(REDMINE_HOST + "/login");
		second.addHeader("Content-Type",
				"application/x-www-form-urlencoded");
		HttpParams params = new BasicHttpParams();
		params.setParameter("authenticity_token", token);
		params.setParameter("back_url", REDMINE_HOST + "/my/account");
		params.setParameter("username", login);
		params.setParameter("password", password);
		client.setParams(params);
		response = client.execute(second);
		parse = new Source(response.getEntity().getContent());

		HttpGet third = new HttpGet(REDMINE_HOST + "/my/account");
		third.addHeader("Referer", REDMINE_HOST + "/login?back_url="
				+ REDMINE_HOST + "/my/account");
		response = client.execute(third);
		parse = new Source(response.getEntity().getContent());
		api_key = parse.getElementById("api-access-key").getContent()
				.toString();

		return api_key;
	}

	public String executeMethod(String method, String api_key, String params) throws HttpException, IOException { //TODO: Fix exceptions
		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(REDMINE_HOST + "/" + method + ".json?" + params);
		get.setHeader("X-Redmine-API-Key", api_key);
		HttpResponse response = client.execute(get);
		String parse = response.getEntity().getContent().toString();
		
		return parse;
	}

	public List<Project> getProjects(String api_key) throws HttpException, JSONException, IOException {
		JSONObject response = new JSONObject(executeMethod("projects", api_key, null));
		JSONArray projects = response.getJSONArray("projects");
		List<Project> projectsList = new ArrayList<Project>();
		for(int i=0;i<projects.length();i++) {
			JSONObject obj = projects.getJSONObject(i);
			Project proj = new Project();
			proj.setId(obj.getString("id"));
			proj.setName(obj.getString("name"));
			proj.setIdentifier(obj.getString("identifier"));
			proj.setCreated_on(obj.getString("created_on"));
			proj.setUpdated_on(obj.getString("updated_on"));
			proj.setDescription(obj.getString("description"));
			proj.setHost(this.REDMINE_HOST);
			projectsList.add(proj);
		}
		return projectsList;
	}
	
	public Project getProject(String project_id, String api_key) throws JSONException, HttpException, IOException {
		JSONObject response = new JSONObject(executeMethod("projects/" + project_id, api_key, null));
		JSONObject obj = response.getJSONObject("project");
		Project proj = new Project();
		proj.setId(obj.getString("id"));
		proj.setName(obj.getString("name"));
		proj.setIdentifier(obj.getString("identifier"));
		proj.setCreated_on(obj.getString("created_on"));
		proj.setUpdated_on(obj.getString("updated_on"));
		proj.setDescription(obj.getString("description"));
		return proj;
	}
	
	public User getCurrentUser(String api_key) throws HttpException, JSONException, IOException{
		JSONObject obj = new JSONObject(executeMethod("users/current", api_key, null)).getJSONObject("user");
		User current = new User();
		current.setId(obj.getString("id"));
		current.setLogin(obj.getString("login"));
		current.setFirstname(obj.getString("firstname"));
		current.setLastname(obj.getString("lastname"));
		current.setCreated_on(obj.getString("created_on"));
		current.setMail(obj.getString("mail"));
		return current;
	}
	
	public List<Issue> getIssues(String api_key, String projectId)
			throws HttpException, JSONException, IOException {
		JSONObject response = new JSONObject(executeMethod("issues", api_key,
				"project_id=" + projectId));
		JSONArray issues = response.getJSONArray("issues");
		//StatusList<IssueStatus> statuses = getStatuses(api_key);
		List<Issue> issues_list = new ArrayList<Issue>();
		for (int i = 0; i < issues.length(); i++) {
			JSONObject tmpIssue = issues.getJSONObject(i);
			List<CustomField> cfList = null;
			if (tmpIssue.optJSONArray("custom_fields") != null) {
				cfList = new ArrayList<CustomField>();
				for (int i1 = 0; i1 < tmpIssue.getJSONArray("custom_fields")
						.length(); i1++) {
					CustomField cf = new CustomField(tmpIssue
							.getJSONArray("custom_fields").getJSONObject(i1)
							.getString("name"), tmpIssue
							.getJSONArray("custom_fields").getJSONObject(i1)
							.optString("id"));
					cfList.add(cf);
				}
			}
			Issue issue = new Issue(issues.getJSONObject(i).getString("id"), // Issue
																				// id
					projectId, // String ProjectId
					tmpIssue.getJSONObject("tracker").getString("id"),// String
																		// tracker,
					tmpIssue.getJSONObject("status").getString("id"),// String
																		// status
																		// (status
																		// id
																		// actually)
					tmpIssue.optJSONObject("assigned_to").getString("id"),// User
																			// assigned_to,
					cfList,// List<CustomField> custom_fields,
					tmpIssue.getString("subject"),// String subject,
					tmpIssue.getString("description"),// String description,
					tmpIssue.getJSONObject("author").getString("id"),// User
																		// author,
					tmpIssue.optString("due_date", null),// String due_date,
					tmpIssue.optString("start_date", null),// String start_date,
					tmpIssue.getString("created_on"),// String created_on,
					tmpIssue.optString("updated_on", null),// String updated_on,
					tmpIssue.optString("done_ratio", "0"),// Integer done_ratio,
					tmpIssue.optString("estimated_hours", null),// String
					// estimated_hours,
					null// IssueCategory category
			);
			issues_list.add(issue);

		}
		return issues_list;
	}

	public StatusList<IssueStatus> getStatuses(String api_key) throws HttpException, JSONException, IOException {
		JSONObject response = new JSONObject(executeMethod("issue_statuses", api_key, null));
		JSONArray issueStatuses = response.getJSONArray("issue_statuses");
		StatusList<IssueStatus> statusList = new StatusList<IssueStatus>();
		for(int i=0;i<issueStatuses.length();i++) {
			JSONObject obj = issueStatuses.getJSONObject(i);
			IssueStatus status = new IssueStatus();
			status.setId(obj.getString("id"));
			status.setName(obj.getString("name"));
			status.setIsClosed(obj.optBoolean("is_closed"));
			status.setIsDefault(obj.optBoolean("is_default"));
			
			statusList.add(status);
			
		}
		
		return statusList;
	}

}
