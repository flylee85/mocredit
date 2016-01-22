package com.yimeihuijin.codeandbonusapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Looper;
import android.support.v4.widget.DrawerLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.yimeihuijin.codeandbonusapp.App;
import com.yimeihuijin.codeandbonusapp.model.Model;
import com.yimeihuijin.codeandbonusapp.model.SigninModel;
import com.yimeihuijin.codeandbonusapp.model.vo.VO;
import com.yimeihuijin.codeandbonusapp.presenter.SigninPresenter;
import com.yimeihuijin.codeandbonusapp.utils.PostUtil;
import com.yimeihuijin.codeandbonusapp.utils.StringUtils;
import com.yimeihuijin.codeandbonusapp.utils.ThreadMananger;
import com.yimeihuijin.commonlibrary.base.BaseFragment;
import com.yimeihuijin.commonlibrary.constants.URLs;
import com.yimeihuijin.commonlibrary.utils.PreferenceUtil;
import com.yimeihuijin.commonlibrary.web.GsonUtil;

import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 后台服务，负责心跳和定时签到
 */
public class WangposService extends Service implements SigninPresenter.ISigninView{

	private Timer timerHeartbet;

	private TimerTask taskHeartbeat;

	private final String TAG_HEARTBEAT = "heart_beat";

	private PostUtil.PostResponseListener listener;

	private PostUtil postUtil;

	private long heartBeatRate = 3600000l;

	private Timer timerSignin;

	private TimerTask taskSignin;

	private SigninPresenter presenter;

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
		new Thread(){
			@Override
			public void run() {
				super.run();
				init();
			}
		}.start();
	}


	public void init() {
		System.out.println("service is running");
		if (PreferenceUtil.getLong(this,pname, ratekey) > 0) {
			heartBeatRate = PreferenceUtil.getLong(this,pname, ratekey);
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
				presenter.onServiceSignin();
			}
		};

		listener = new PostUtil.PostResponseListener() {

			@Override
			public void onResponse(String response, String tag) {
				// TODO Auto-generated method stub
//				System.out.println("heart beat = "+response);
//				Model.PO ret = new Gson().fromJson(response, Model.PO.class);
				HeartBeatInfo sr = new Gson().fromJson(response,
						HeartBeatInfo.class);
				if (sr != null && "1".equals(sr.action)) {

					updateActivities();
				} else if (sr != null && "2".equals(sr.action)) {
					long rate = StringUtils.getHeartBeatRate(sr.para);
					PreferenceUtil.put(WangposService.this,pname, ratekey,rate);
					heartBeatRate = rate;
				}
			}

			@Override
			public void onErroResponse(VolleyError error, String tag) {
				// TODO Auto-generated method stub

			}
		};

		presenter = new SigninPresenter(this);

		postUtil = new PostUtil(listener);

		timerHeartbet.scheduleAtFixedRate(taskHeartbeat,
				(long) (Math.random() * heartBeatRate), heartBeatRate);
		timerSignin.scheduleAtFixedRate(taskSignin, 24 * 60 * 60 * 1000,
				24 * 60 * 60 * 1000);

		ThreadMananger.get().execute(new CorrectRunnable());
	}

	public void heartBeat() {
		final Model.PO post = new  Model.PO(new Object());
		postUtil.post(URLs.URL_HEARTBEAT, post, TAG_HEARTBEAT);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}



	private void updateActivities(){
		Model.PO post = new Model.PO(new Object());
		new PostUtil(new PostUtil.PostResponseListener() {
			@Override
			public void onResponse(String response, String tag) {
				ThreadMananger.get().execute(new SigninModel.StoreActivitysRunnable(response));
			}

			@Override
			public void onErroResponse(VolleyError error, String tag) {

			}
		}).post(URLs.URL_ACTIVITIES, post);
	}

	@Override
	public void setRunningMsg(String msg) {

	}

	@Override
	public void showKeyBoard() {

	}

	@Override
	public void gotoFragment(BaseFragment fragment) {

	}

	@Override
	public void goBackToFragment(BaseFragment fragment) {

	}

	@Override
	public void goBack() {

	}



	@Override
	public void gotoService() {

	}

	@Override
	public DrawerLayout getDrawer() {
		return null;
	}

	@Override
	public ListView getLeftMenu() {
		return null;
	}

	@Override
	public void clearFragment(BaseFragment fragment) {

	}

	@Override
	public Looper getLooper() {
		return null;
	}

	@Override
	public TextView getVersionTextView() {
		return null;
	}


	@Override
	public void showDialog(String msg) {

	}

	@Override
	public void dismisDialog() {

	}

	private class HeartBeatInfo{
		public String state;
		public String action;
		public String para;
	}

	private class CorrectRunnable implements  Runnable{

		@Override
		public void run() {
			correct();
		}

		private void correct(){
			LinkedList<VO.BonusConsumeObject> list = App.getInstance().getDBHelper().getOrders();
			consumeCorrect(list);
		}

		private void consumeCorrect(final LinkedList<VO.BonusConsumeObject> list){
			if(list.size() < 1){
				return;
			}
			final VO.BonusConsumeObject bco = list.pop();
			new PostUtil(new PostUtil.PostResponseListener() {
				@Override
				public void onResponse(String response, String tag) {
					VO.BonusConsumeResponseObject bcro = GsonUtil.get(response, VO.BonusConsumeResponseObject.class);
					if(bcro != null && bcro.success){
						App.getInstance().getDBHelper().deleteOrder(bco.orgOrderId);
					}
					consumeCorrect(list);
				}

				@Override
				public void onErroResponse(VolleyError error, String tag) {
					consumeCorrect(list);
				}
			});
		}
	}

}
