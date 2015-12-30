package com.yimeihuijin.commonlibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {

	public static final String DEFAULT_IP = "defualt_ip";

	public static final String DEFAULT_PORT = "defualt_port";

	public static final String SIGN_IN_INFO = "sign_in_info";
	public static final String SIGN_IN_AKEY = "akey";

	private static SharedPreferences preference;

	public static SharedPreferences getPreference(Context context,String pName) {
		return getPreference(context,pName, Context.MODE_PRIVATE);
	}

	public static SharedPreferences getPreference(Context context,String pName, int pMode) {
		preference = context
				.getSharedPreferences(pName, pMode);
		return preference;
	}

	public static void put(Context context,String pName, String key, String value) {
		getPreference(context,pName, Context.MODE_PRIVATE).edit().putString(key, value)
				.commit();
	}

	public static void put(Context context,String pName, String key, int value) {
		getPreference(context,pName, Context.MODE_PRIVATE).edit().putInt(key, value)
				.commit();
	}

	public static void put(Context context,String pName, String key, boolean value) {
		getPreference(context,pName, Context.MODE_PRIVATE).edit()
				.putBoolean(key, value).commit();
	}

	public static void put(Context context,String pName, String key, long value) {
		getPreference(context,pName, Context.MODE_PRIVATE).edit().putLong(key, value)
				.commit();
	}

	public static void put(Context context,String pName, String key, float value) {
		getPreference(context,pName, Context.MODE_PRIVATE).edit().putFloat(key, value)
				.commit();
	}

	public static String getString(Context context,String pName, String key) {
		return getPreference(context,pName, Context.MODE_PRIVATE).getString(key, null);
	}

	public static int getInt(Context context,String pName, String key) {
		return getPreference(context,pName, Context.MODE_PRIVATE).getInt(key, -1);
	}
	
	public static long getLong(Context context,String pName, String key) {
		return getPreference(context,pName, Context.MODE_PRIVATE).getLong(key, -1);
	}

}