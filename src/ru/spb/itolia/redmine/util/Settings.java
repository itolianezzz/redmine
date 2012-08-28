package ru.spb.itolia.redmine.util;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import ru.spb.itolia.redmine.RedmineApp;

/**
 * Created with IntelliJ IDEA.
 * User: itolia
 * Date: 28.08.12
 * Time: 12:36
 */
public class Settings {
    public static final String PREF_HOSTS_AVAILABLE = "hosts_available";


    public static SharedPreferences getPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(RedmineApp.context);
    }

    public static String getString(String name) {
        return getPreferences().getString(name, "");
    }

    public static void setString(String key, String value) {
        getPreferences().edit().putString(key, value).commit();
    }

    public static Boolean getBoolean(String name) {
        return getPreferences().getBoolean(name, false);
    }

    public static void setBoolean(String key, boolean value) {
        getPreferences().edit().putBoolean(key, value).commit();
    }

    public static int getInt(String name) {
        return Integer.parseInt(getPreferences().getString(name, "-1"));
    }
}
