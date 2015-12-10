package com.yimiehuijin.codeandbonuslibrary.web;

import android.content.Context;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.yimiehuijin.codeandbonuslibrary.App;
import com.yimiehuijin.codeandbonuslibrary.constants.Constants;
import com.yimiehuijin.codeandbonuslibrary.constants.URLs;
import com.yimiehuijin.codeandbonuslibrary.data.ActivitiesResponse;
import com.yimiehuijin.codeandbonuslibrary.data.EncryptTest;
import com.yimiehuijin.codeandbonuslibrary.data.GetActivities;
import com.yimiehuijin.codeandbonuslibrary.data.PostData;
import com.yimiehuijin.codeandbonuslibrary.data.SigninReceipt;
import com.yimiehuijin.codeandbonuslibrary.data.SigninResponse;

public class SigninUtil {

	private SigninListener signListener;

	private PostUtil.PostResponseListener postListener;

	private PostUtil postUtil;

	private static final String TAG_SIGNIN = "su_signin";

	private static final String TAG_RECEIPT = "su_receipt";

	private static final String TAG_ENCRYPT_TEST = "su_encrypt_test";

	private static final String TAG_COMM_TEST = "su_comm_test";

	private static final String TAG_ACTIVITIES = "su_activities";

	private Context context;

	public static interface SigninListener {

		public void onSigninSuccess();

		public void onSigninFailure();
	}

	public SigninUtil(SigninListener listener, Context c) {
		this.signListener = listener;
		this.context = c;
		init();
	}

	private void init() {
		if (postListener == null) {
			postListener = new PostUtil.PostResponseListener() {

				@Override
				public void onResponse(String response, String tag) {
					// TODO Auto-generated method stub
					if (TAG_SIGNIN.equals(tag)) {
						PostData ret = new Gson().fromJson(response,
								PostData.class);
						SigninResponse sr = new Gson().fromJson(ret.jData,
								SigninResponse.class);
						System.out.println("new akey=" + sr.newakey);
						if ("ok".equals(sr.state)) {
							App.tmpkey = sr.newakey;
							App.jieruhao = sr.jieruhao;
							signInReceipt();
						} else {
							signIn();
						}
					} else if (TAG_RECEIPT.equals(tag)) {
						App.getInstance().getDBHelper()
								.updateSigninInfo(App.tmpkey, App.akey);
						App.akey = App.tmpkey;
						signListener.onSigninSuccess();
						updateActivities();
					} else if (TAG_ENCRYPT_TEST.equals(tag)) {
						signListener.onSigninSuccess();
						updateActivities();
					} else if (TAG_COMM_TEST.equals(tag)) {
						signInReceipt();
					} else if (TAG_ACTIVITIES.equals(tag)) {
						final PostData  ret = new Gson().fromJson(response,
								PostData.class);
						if (ret == null) {
							return;
						}
						ActivitiesResponse ar = null;
						try {
							ar = new Gson().fromJson(ret.jData,
									ActivitiesResponse.class);
						} catch (Exception e) {

						}
						if (ar == null) {
							return;
						}
						new Thread(){
							@Override
							public void run() {
								super.run();
								App.getInstance().getDBHelper().insertActivitiesJson(ret.jData);
							}
						}.start();
					}
				}

				@Override
				public void onErroResponse(VolleyError error, String tag) {
					// TODO Auto-generated method stub
					if (TAG_SIGNIN.equals(tag)) {
						signListener.onSigninFailure();
					} else if (TAG_RECEIPT.equals(tag)) {
						encryptTest();
					} else if (TAG_ENCRYPT_TEST.equals(tag)) {
						communicationTest();
					} else if (TAG_COMM_TEST.equals(tag)) {
						signListener.onSigninFailure();
					} else {
						signListener.onSigninFailure();
					}
				}
			};

			postUtil = new PostUtil(postListener, context);
		}
	}

	/**
	 * 签到
	 */
	public void signIn() {
		if (App.akey == null) {
			App.akey = Constants.INIT_AKEY;
			postSignIn();
		} else {
			Long date = App.getInstance().getDBHelper().findAkeyTime(App.akey);
			Long interval = System.currentTimeMillis() - date;
			if ((interval / (1000 * 60 * 60)) >= 24) {
				postSignIn();
			} else {
				signListener.onSigninSuccess();
				updateActivities();
			}
		}

	}

	public void postSignIn() {
		PostData post = new PostData(new Object());
		post(URLs.URL_SIGNIN, post, TAG_SIGNIN);
	}

	private void post(String url, PostData data, String tag) {
		postUtil.post(url, data, tag);
	}

	/**
	 * 签到回执
	 */
	private void signInReceipt() {
		SigninReceipt enpost = new SigninReceipt();
		enpost.oldAkey = App.akey;
		enpost.newAkey = App.tmpkey;
		enpost.state = "ok";
		PostData post = new PostData(enpost);
		post(URLs.URL_SIGNIN_RECEIPT, post, TAG_RECEIPT);
	}

	private void encryptTest() {
		String tmp = App.akey;
		App.akey = App.tmpkey;
		App.tmpkey = tmp;
		EncryptTest enpost = new EncryptTest();
		PostData post = new PostData(enpost);
		post(URLs.URL_ENCRYPT_TEST, post, TAG_ENCRYPT_TEST);
		App.tmpkey = App.akey;
		App.akey = tmp;
	}

	private void communicationTest() {
		PostUtil.commTest(TAG_COMM_TEST, postListener);
	}

	public void updateActivities() {
		PostData post = new PostData(new Object());
		post(URLs.URL_ACTIVITIES, post, TAG_ACTIVITIES);
	}

}
