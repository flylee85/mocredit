package com.yimiehuijin.checkcodeapp.service;

import java.util.Timer;
import java.util.TimerTask;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.yimiehuijin.codeandbonuslibrary.constants.URLs;
import com.yimiehuijin.codeandbonuslibrary.data.HeartBeatInfo;
import com.yimiehuijin.codeandbonuslibrary.data.PostData;
import com.yimiehuijin.codeandbonuslibrary.utils.PreferenceUtil;
import com.yimiehuijin.codeandbonuslibrary.utils.StringUtils;
import com.yimiehuijin.codeandbonuslibrary.web.PostUtil;
import com.yimiehuijin.codeandbonuslibrary.web.SigninUtil;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class WangposService extends Service {

	private Timer timerHeartbet;

	private TimerTask taskHeartbeat;

	private final String TAG_HEARTBEAT = "heart_beat";

	private PostUtil.PostResponseListener listener;

	private PostUtil postUtil;

	private long heartBeatRate = 3600000l;

	private Timer timerSignin;

	private TimerTask taskSignin;

	private SigninUtil.SigninListener signListener;

	private SigninUtil signUtil;

	private final String pname = "heartbeat";

	private final String ratekey = "ratekey";

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		init();
	}

	public void init() {
		System.out.println("service is running");
		if (PreferenceUtil.getLong(pname, ratekey) > 0) {
			heartBeatRate = PreferenceUtil.getLong(pname, ratekey);
		}
		timerHeartbet = new Timer();
		taskHeartbeat = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				heartBeat();
			}
		};

		timerSignin = new Timer();
		taskSignin = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				signUtil.postSignIn();
			}
		};

		listener = new PostUtil.PostResponseListener() {

			@Override
			public void onResponse(String response, String tag) {
				// TODO Auto-generated method stub
				PostData ret = new Gson().fromJson(response, PostData.class);
				HeartBeatInfo sr = new Gson().fromJson(ret.jData,
						HeartBeatInfo.class);
				if (sr != null && "1".equals(sr.action)) {
					return;
				} else if (sr != null && "2".equals(sr.action)) {
					long rate = StringUtils.getHeartBeatRate(sr.para);
					PreferenceUtil.put(pname, ratekey,rate);
					heartBeatRate = rate;
				}
			}

			@Override
			public void onErroResponse(VolleyError error, String tag) {
				// TODO Auto-generated method stub

			}
		};

		signListener = new SigninUtil.SigninListener() {

			@Override
			public void onSigninSuccess() {
				// TODO Auto-generated method stub
				Toast.makeText(WangposService.this, "签到成功", Toast.LENGTH_LONG)
						.show();
			}

			@Override
			public void onSigninFailure() {
				// TODO Auto-generated method stub
				Toast.makeText(WangposService.this, "签到失败", Toast.LENGTH_LONG)
						.show();

			}
		};

		postUtil = new PostUtil(listener, this);

		signUtil = new SigninUtil(signListener, this);

		timerHeartbet.scheduleAtFixedRate(taskHeartbeat,
				(long) (Math.random() * heartBeatRate), heartBeatRate);
		timerSignin.scheduleAtFixedRate(taskSignin, 24 * 60 * 60 * 1000,
				24 * 60 * 60 * 1000);
	}

	public void heartBeat() {
		final PostData post = new PostData(new Object());
		postUtil.post(URLs.URL_HEARTBEAT, post, TAG_HEARTBEAT);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

}
