package ru.spb.itolia.redmine.api.beans;

import java.util.List;

public class Issue {
	private String id;
	private String projectId;
	private String tracker;
	private String status;
	private String assigned_to;
	private List<CustomField> custom_fields;
	private String subject;
	private String description;
	private String author;
	private String due_date;
	private String start_date;
	private String created_on;
	private String updated_on;
	private String done_ratio;
	private String estimated_hours;
	private IssueCategory category;
	
	public Issue(String id, String projectId, String tracker,
			String status, String assigned_to,
			List<CustomField> custom_fields, String subject,
			String description, String author, String due_date,
			String start_date, String created_on, String updated_on,
			String done_ratio, String estimated_hours, IssueCategory category) {
		super();
		this.id = id;
		this.projectId = projectId;
		this.tracker = tracker;
		this.status = status;
		this.assigned_to = assigned_to;
		this.custom_fields = custom_fields;
		this.subject = subject;
		this.description = description;
		this.author = author;
		this.due_date = due_date;
		this.start_date = start_date;
		this.created_on = created_on;
		this.updated_on = updated_on;
		this.done_ratio = done_ratio;
		this.estimated_hours = estimated_hours;
		this.category = category;
	}
	public Issue() {
		// TODO Auto-generated constructor stub
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProject() {
		return projectId;
	}
	public void setProject(String projectId) {
		this.projectId = projectId;
	}
	public String getTracker() {
		return tracker;
	}
	public void setTracker(String tracker) {
		this.tracker = tracker;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAssigned_to() {
		return assigned_to;
	}
	public void setAssigned_to(String assigned_to) {
		this.assigned_to = assigned_to;
	}
	public List<CustomField> getCustom_fields() {
		return custom_fields;
	}
	public void setCustom_fields(List<CustomField> custom_fields) {
		this.custom_fields = custom_fields;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDue_date() {
		return due_date;
	}
	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}
	public String getStart_date() {
		return start_date;
	}
	public void setStart_date(String start_date) {
		this.start_date = start_date;
	}
	public String getCreated_on() {
		return created_on;
	}
	public void setCreated_on(String created_on) {
		this.created_on = created_on;
	}
	public String getUpdated_on() {
		return updated_on;
	}
	public void setUpdated_on(String updated_on) {
		this.updated_on = updated_on;
	}
	public String getDone_ratio() {
		return done_ratio;
	}
	public void setDone_ratio(String done_ratio) {
		this.done_ratio = done_ratio;
	}
	public String getEstimated_hours() {
		return estimated_hours;
	}
	public void setEstimated_hours(String estimated_hours) {
		this.estimated_hours = estimated_hours;
	}
	public IssueCategory getCategory() {
		return category;
	}
	public void setCategory(IssueCategory category) {
		this.category = category;
	}
	
	
	
	

}
