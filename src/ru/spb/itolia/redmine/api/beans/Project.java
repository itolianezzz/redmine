package ru.spb.itolia.redmine.api.beans;

public class Project {
	private Integer project_id;
	private String name;
	private String project_identifier;
	private String created_on;
	private String updated_on;
	private String description;
	private Integer host_id;
	
	public Project(Integer project_id, String name, String project_identifier,
			String created_on, String updated_on, String description, Integer host_id) {
		super();
		this.project_id = project_id;
		this.name = name;
		this.project_identifier = project_identifier;
		this.created_on = created_on;
		this.updated_on = updated_on;
		this.description = description;
		this.host_id = host_id;
	}

	public Project() {
		// TODO Auto-generated constructor stub
	}

	public Integer getId() {
		return project_id;
	}

	public void setId(Integer project_id) {
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
	
	public Integer getHost_id() {
		return host_id;
	}

	public void setHost_id(Integer host_id) {
		this.host_id = host_id;
	}

	@Override
	public String toString() {
		return "Project: " + name + ",id=" + project_id + ", created: " + created_on + ", updated: " + updated_on; 
	}
}
