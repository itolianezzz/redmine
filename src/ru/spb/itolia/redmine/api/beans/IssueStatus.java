package ru.spb.itolia.redmine.api.beans;

public class IssueStatus {
	private String id;
	private String name;
	private Boolean isDefault;
	private Boolean isClosed;
	
	public IssueStatus(String id, String name, Boolean isDefault,
			Boolean isClosed) {
		super();
		this.id = id;
		this.name = name;
		this.isDefault = isDefault;
		this.isClosed = isClosed;
	}
	
	

	public IssueStatus() {
		super();
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public Boolean getIsClosed() {
		return isClosed;
	}

	public void setIsClosed(Boolean isClosed) {
		this.isClosed = isClosed;
	}
	
	
	
	
	

}
