package ru.spb.itolia.redmine.api.beans;

public class RedmineHost {
	
	private Integer id;
	private String address;
	private User user;
	private String label;
	
	public RedmineHost(Integer id, String host, String label) {
		super();
		this.id = id;
		this.address = host;
		this.label = label;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String host) {
		this.address = host;
	}

	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	

}
