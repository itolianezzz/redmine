package ru.spb.itolia.redmine.api.beans;

/**
 * Created with IntelliJ IDEA.
 * User: itolia
 * Date: 24.08.12
 * Time: 12:59
 *
 */
public class RedmineAccount {
    private String username;
    private String api_key;
    private long host_id;

    public RedmineAccount(String username, String api_key, long host_id) {
        this.username = username;
        this.api_key = api_key;
        this.host_id = host_id;
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

    public long getHost_id() {
        return host_id;
    }

    public void setHost_id(long host_id) {
        this.host_id = host_id;
    }
}
