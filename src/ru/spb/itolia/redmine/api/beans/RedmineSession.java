package ru.spb.itolia.redmine.api.beans;

/**
 * Created with IntelliJ IDEA.
 * User: itolia
 * Date: 31.08.12
 * Time: 15:53
 */
public class RedmineSession {
    private String address;
    private String label;
    private String username;
    private String api_key;

    public RedmineSession(String address, String label, String username, String api_key) {
        this.address = address;
        this.label = label;
        this.username = username;
        this.api_key = api_key;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }
}
