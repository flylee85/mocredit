package com.yimeihuijin.commonlibrary.utils;

public class PasswordTagDivider {
	private static final String split = "-";

	public static String getTag(String response) {
		if (response == null || response.length() < 1
				|| !response.contains(split)) {
			return null;
		}
		return response.split(split)[0];
	}

	public static String getPassword(String response) {
		if (response == null || response.length() < 1
				|| !response.contains(split)) {
			return null;
		}
		return response.split(split)[1];
	}

	public static String getPostTag(String tag, String password) {
		return tag + split + password;
	}
}
