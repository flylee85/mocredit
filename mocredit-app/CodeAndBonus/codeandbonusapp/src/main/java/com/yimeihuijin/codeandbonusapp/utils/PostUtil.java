package com.yimeihuijin.codeandbonusapp.utils;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.yimeihuijin.codeandbonusapp.App;
import com.yimeihuijin.codeandbonusapp.model.Model;
import com.yimeihuijin.commonlibrary.constants.URLs;
import com.yimeihuijin.commonlibrary.utils.AESHelper;
import com.yimeihuijin.commonlibrary.utils.NetworkUtil;
import com.yimeihuijin.commonlibrary.utils.PasswordTagDivider;

import java.util.HashMap;
import java.util.Map;

/**
 * 负责发送请求，完成加密和解密操作
 */
public class PostUtil {

	private PostResponseListener listener;

	private static final String DEFAULT_TAG = "tag";
	
	private static final int TIME_OUT = 10 * 1000;

	private Context context;

	public PostUtil(PostResponseListener l) {
		this.listener = l;
	}

	public void post(String url,Model.PO data){
		post(url,data,DEFAULT_TAG);
	}

	public void post(String url, Model.PO data, String tag) {
		if (!NetworkUtil.isNetworkAvailable(App.getInstance())) {
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
					String retData = getResult(ret);
					if(retData == null){
						listener.onErroResponse(new VolleyError("服务器返回数据异常"),tag);
					}else {
						listener.onResponse(retData, tag);
					}
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

	private String getResult(String ret){
		if(ret == null){
			return null;
		}
		Model.PO retData = new Gson().fromJson(ret,Model.PO.class);
		if(retData == null || retData.jData == null){
			return null;
		}
		return retData.jData;
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

	public void triblePost(final String url, final Model.PO data,
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

	public static class CustomVolleyError extends VolleyError {

	}
}