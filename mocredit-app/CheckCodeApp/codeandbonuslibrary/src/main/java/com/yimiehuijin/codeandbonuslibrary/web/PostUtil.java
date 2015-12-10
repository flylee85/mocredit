package com.yimiehuijin.codeandbonuslibrary.web;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.yimiehuijin.codeandbonuslibrary.App;
import com.yimiehuijin.codeandbonuslibrary.constants.URLs;
import com.yimiehuijin.codeandbonuslibrary.data.PostData;
import com.yimiehuijin.codeandbonuslibrary.utils.AESHelper;
import com.yimiehuijin.codeandbonuslibrary.utils.NetworkUtil;
import com.yimiehuijin.codeandbonuslibrary.utils.PasswordTagDivider;
import com.yimiehuijin.codeandbonuslibrary.utils.StringUtils;


public class PostUtil {

	private PostResponseListener listener;

	private static final String DEFAULT_TAG = "tag";
	
	private static final int TIME_OUT = 10 * 1000;

	private Context context;

	public PostUtil(PostResponseListener l, Context c) {
		this.listener = l;
		this.context = c;
	}

	public void post(String url, PostData data, String tag) {
		if (!NetworkUtil.isNetworkAvailable()) {
			VolleyError error = new VolleyError(new NetworkError());
			listener.onErroResponse(error, tag);
			return;
		}

		System.setProperty("http.keepAlive", "false");
		Gson gson = new Gson();
		String json = gson.toJson(data);
		String rkey = StringUtils.getRKey(16);
		String h = StringUtils.getH(rkey);
		String t = StringUtils.getT(json, rkey);
		final HashMap<String, String> params = new HashMap<String, String>();
		params.put("h", h);
		params.put("t", t);
		String ptag = "";
		if (tag == null) {
			ptag = PasswordTagDivider.getPostTag(DEFAULT_TAG,
					StringUtils.MD5(App.akey + rkey));
		} else {
			ptag = PasswordTagDivider.getPostTag(tag,
					StringUtils.MD5(App.akey + rkey));
		}
		Response.Listener<String> response = new Response.Listener<String>() {

			@Override
			public void onResponse(String arg0, Object arg1) {
				// TODO Auto-generated method stub
				if ("-1".equals(arg0)) {
					String tag = PasswordTagDivider.getTag(arg1.toString());
					listener.onErroResponse(new VolleyError("服务器解密失败"), tag);
				} else {
					String password = PasswordTagDivider.getPassword(arg1
							.toString());
					String tag = PasswordTagDivider.getTag(arg1.toString());
					String ret = AESHelper.decrypt(arg0, password);
					listener.onResponse(ret, tag);
				}
			}

		};
		Response.ErrorListener response_err = new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0, Object arg1) {
				// TODO Auto-generated method stub
				String tag = PasswordTagDivider.getTag(arg1.toString());
				listener.onErroResponse(arg0, tag);
			}
		};
		StringRequest jsnObjRqst = new StringRequest(Request.Method.POST, url,
				response, response_err) {

			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				// TODO Auto-generated method stub
				return params;
			}

		};

		jsnObjRqst.setTag(ptag);
		jsnObjRqst.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		App.getInstance().getRequestQueue().add(jsnObjRqst);

	}

	public static void commTest(String tag, final PostResponseListener l) {
		Response.Listener<String> response = new Response.Listener<String>() {

			@Override
			public void onResponse(String arg0, Object arg1) {
				// TODO Auto-generated method stub

				l.onResponse(arg0, arg1.toString());
			}

		};
		Response.ErrorListener response_err = new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError arg0, Object arg1) {
				// TODO Auto-generated method stub
				l.onErroResponse(arg0, arg1.toString());
			}
		};
		StringRequest jsnObjRqst = new StringRequest(Request.Method.POST,
				URLs.URL_COMM_TEST, response, response_err) {

			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				// TODO Auto-generated method stub
				return new HashMap<String, String>();
			}

		};

		jsnObjRqst.setTag(tag);
		App.getInstance().getRequestQueue().add(jsnObjRqst);
	}

	public void triblePost(final String url, final PostData data,
						   final String tag) {
		new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				try {
					post(url, data, tag);
					Thread.sleep(1000);
					post(url, data, tag);
					Thread.sleep(1000);
					post(url, data, tag);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
	}

	public interface PostResponseListener {

		public void onResponse(String response, String tag);

		public void onErroResponse(VolleyError error, String tag);
	}

	public static class CustomVolleyError extends VolleyError{

	}
}