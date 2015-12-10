package com.yimiehuijin.codeandbonuslibrary;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Application;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.yimiehuijin.codeandbonuslibrary.constants.Constants;
import com.yimiehuijin.codeandbonuslibrary.constants.URLs;
import com.yimiehuijin.codeandbonuslibrary.data.PostData;
import com.yimiehuijin.codeandbonuslibrary.data.WeiposDeviceInfo;
import com.yimiehuijin.codeandbonuslibrary.db.DBHelper;
import com.yimiehuijin.codeandbonuslibrary.utils.StringUtils;

import cn.weipass.pos.sdk.Weipos;
import cn.weipass.pos.sdk.impl.WeiposImpl;

public class App extends Application {

	public static String aid;

	public static String akey;

	public static String tmpkey;

	public static String jieruhao;

	public static String rkey;

	private static App instance;

	private RequestQueue request_queue;

	public  WeiposDeviceInfo deviceInfo;

	private Timer timer;

	private TimerTask task;

	private  DBHelper dbhelper;

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
		request_queue = Volley.newRequestQueue(this);
		akey = getDBHelper().findAkey();
		jieruhao = getDBHelper().findSeriesNumber();
		URLs.initIP();
		WeiposImpl.as().init(this, new Weipos.OnInitListener() {
			@Override
			public void onInitOk() {
				WeiposDeviceInfo info = new Gson().fromJson(WeiposImpl.as()
						.getDeviceInfo(), WeiposDeviceInfo.class);
				info.cleanEN();
				deviceInfo = info;
				aid = StringUtils.MD5(deviceInfo.en);
			}

			@Override
			public void onError(String s) {

			}
		});
		initActivityListener();
	}

	public void resetAKey() {
		akey = Constants.INIT_AKEY;
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		WeiposImpl.as().destroy();
	}

	private void initActivityListener() {

	}

	public void getActivities() {
		Listener<String> response = new Listener<String>() {

			@Override
			public void onResponse(String arg0, Object arg1) {
				// TODO Auto-generated method stub

			}

		};
		ErrorListener response_err = new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0, Object arg1) {
				// TODO Auto-generated method stub

			}
		};
		final PostData post = new PostData(new Object());
		final Gson gson = new Gson();
		StringRequest jsnObjRqst = new StringRequest(Request.Method.POST,
				URLs.URL_ACTIVITIES, response, response_err) {

			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				// TODO Auto-generated method stub
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("h", StringUtils.getH());
				map.put("t", StringUtils.getT(gson.toJson(post)));
				return map;
			}

		};

		request_queue.add(jsnObjRqst);
	}

}
