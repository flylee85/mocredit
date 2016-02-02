package com.yimeihuijin.codeandbonusapp;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.yimeihuijin.codeandbonusapp.db.DBHelper;
import com.yimeihuijin.commonlibrary.constants.Constants;
import com.yimeihuijin.commonlibrary.constants.URLs;
import java.util.Timer;
import java.util.TimerTask;

import cn.weipass.pos.sdk.Weipos;
import cn.weipass.pos.sdk.impl.WeiposImpl;

public class App extends Application {


	public static String akey;

	public static String tmpkey;

	public static String jieruhao;

	private static App instance;

	public static String rkey;

	private RequestQueue request_queue;

	private Timer timer;

	private TimerTask task;

	private DBHelper dbhelper;


	public static App getInstance() {
		return instance;
	}

	public RequestQueue getRequestQueue() {
		return request_queue;
	}

	public  DBHelper getDBHelper() {
		if (dbhelper == null) {
			synchronized (DBHelper.class) {
				dbhelper = new DBHelper(instance);
			}
		}
		return dbhelper;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
//		deleteDatabase(DBHelper.DB_NAME);
		instance = this;
		akey = getDBHelper().findAkey();
		request_queue = Volley.newRequestQueue(this);
		jieruhao = getDBHelper().findSeriesNumber();
		URLs.initIP(this);
		initActivityListener();
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		WeiposImpl.as().destroy();
	}

	private void initActivityListener() {

	}

}
