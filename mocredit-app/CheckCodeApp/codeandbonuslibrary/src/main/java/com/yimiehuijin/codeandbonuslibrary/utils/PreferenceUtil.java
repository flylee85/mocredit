package com.yimiehuijin.codeandbonuslibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.yimiehuijin.codeandbonuslibrary.App;


public class PreferenceUtil {

	public static final String DEFAULT_IP = "defualt_ip";

	public static final String DEFAULT_PORT = "defualt_port";

	public static final String SIGN_IN_INFO = "sign_in_info";
	public static final String SIGN_IN_AKEY = "akey";

	private static SharedPreferences preference;

	public static SharedPreferences getPreference(String pName) {
		return getPreference(pName, Context.MODE_PRIVATE);
	}

	public static SharedPreferences getPreference(String pName, int pMode) {
		preference = App.getInstance().getApplicationContext()
				.getSharedPreferences(pName, pMode);
		return preference;
	}

	public static void put(String pName, String key, String value) {
		getPreference(pName, Context.MODE_PRIVATE).edit().putString(key, value)
				.commit();
	}

	public static void put(String pName, String key, int value) {
		getPreference(pName, Context.MODE_PRIVATE).edit().putInt(key, value)
				.commit();
	}

	public static void put(String pName, String key, boolean value) {
		getPreference(pName, Context.MODE_PRIVATE).edit()
				.putBoolean(key, value).commit();
	}

	public static void put(String pName, String key, long value) {
		getPreference(pName, Context.MODE_PRIVATE).edit().putLong(key, value)
				.commit();
	}

	public static void put(String pName, String key, float value) {
		getPreference(pName, Context.MODE_PRIVATE).edit().putFloat(key, value)
				.commit();
	}

	public static String getString(String pName, String key) {
		return getPreference(pName, Context.MODE_PRIVATE).getString(key, null);
	}

	public static int getInt(String pName, String key) {
		return getPreference(pName, Context.MODE_PRIVATE).getInt(key, -1);
	}
	
	public static long getLong(String pName, String key) {
		return getPreference(pName, Context.MODE_PRIVATE).getLong(key, -1);
	}

}