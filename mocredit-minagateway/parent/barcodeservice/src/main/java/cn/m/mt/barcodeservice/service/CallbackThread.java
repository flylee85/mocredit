package cn.m.mt.barcodeservice.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.m.mt.barcodeservice.util.MD5Util;
import cn.m.mt.barcodeservice.util.PostUtils;
import cn.m.mt.util.MD5Helper;

public class CallbackThread implements Runnable {
		protected Logger log = LoggerFactory.getLogger(getClass());
		private String tid;
		private String entcode;
		private String callbackurl;
		private String num;
		public CallbackThread(String tid, String entcode, String callbackurl,String num) {
			this.tid = tid;
			this.entcode = entcode;
			this.callbackurl = callbackurl;
			this.num = num;
		}

		
		public void run() {
			log.info(Thread.currentThread().getName()+"开始... ");
			String mac = null;
			try {
				mac = MD5Util.getMD5(tid+entcode);
			}catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			StringBuffer url = new StringBuffer();
			url.append(callbackurl);
			url.append("tid=");
			url.append(tid);
			url.append("&mac=");
			url.append(mac);
			url.append("&num=");
			url.append(num);
			try {
				System.out.println("====url.toString()====="+url.toString());
				String retstr = PostUtils.sendBarcodeStatus(url.toString());
				System.out.println(retstr);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	public static void main(String args[]){
		CallbackThread cb = new CallbackThread("20130726114501660216371","29b9cd287697083ed1d4ce5715654641","http://114.242.187.254:4024/consume/consume.htm?action=ymhjConsume&","1");
		cb.run();
 	}
}
