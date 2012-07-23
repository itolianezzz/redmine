package ru.spb.itolia.redmine.api.beans;

public class Project {
	private String project_id;
	private String name;
	private String project_identifier;
	private String created_on;
	private String updated_on;
	private String description;
	private String host;
	
	public Project(String project_id, String name, String project_identifier,
			String created_on, String updated_on, String description, String host) {
		super();
		this.project_id = project_id;
		this.name = name;
		this.project_identifier = project_identifier;
		this.created_on = created_on;
		this.updated_on = updated_on;
		this.description = description;
		this.host = host;
	}

	public Project() {
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return project_id;
	}

	public void setId(String project_id) {
		this.project_id = project_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdentifier() {
		return project_identifier;
	}

	public void setIdentifier(String project_identifier) {
		this.project_identifier = project_identifier;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	@Override
	public String toString() {
		return "Project: " + name + ",id=" + project_id + ", created: " + created_on + ", updated: " + updated_on; 
	}
}
