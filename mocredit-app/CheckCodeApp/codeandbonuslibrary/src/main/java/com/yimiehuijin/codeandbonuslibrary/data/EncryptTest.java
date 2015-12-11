package com.yimiehuijin.codeandbonuslibrary.data;

import com.yimiehuijin.codeandbonuslibrary.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;


public class EncryptTest {
	public String uuid;
	public String time;

	public EncryptTest() {
		uuid = StringUtils.getRKey(16);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		Date date = new Date(System.currentTimeMillis());
		time = sdf.format(date);
	}
}
