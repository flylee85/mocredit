package cn.m.mt.mocreditservice.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HttpClientUtil {
	private static final Log log = LogFactory.getLog(HttpClientUtil.class);

	/**
	 * @param args
	 */
	public static String post(String url, Map<String, Object> paramsMap) {
		PrintWriter printWriter = null;
		BufferedReader reader = null;
		StringBuffer result = new StringBuffer();
		StringBuffer params = new StringBuffer();
		HttpURLConnection connection = null;
		// 组织请求参数
		Iterator<?> it = paramsMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<?,?> element = (Map.Entry<?,?>) it.next();
			params.append(element.getKey());
			params.append("=");
			params.append(element.getValue());
			params.append("&");
		}
		if (params.length() > 0) {
			params.deleteCharAt(params.length() - 1);
		}

		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			connection = (HttpURLConnection) realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("Content-Length", String.valueOf(params.length()));
			// 发送POST请求必须设置如下两行
			connection.setDoOutput(true);
			connection.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			printWriter = new PrintWriter(connection.getOutputStream());
			// 发送请求参数
			printWriter.write(params.toString());
			// flush输出流的缓冲
			printWriter.flush();
			// 根据ResponseCode判断连接是否成功
			int responseCode = connection.getResponseCode();
			if (responseCode != 200) {
				log.error(" Error===" + responseCode);
			} else {
				log.info("Post Success!");
			}
			// 定义BufferedReader输入流来读取URL的ResponseData
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			log.error("send post request error!" + e);
		} finally {
			connection.disconnect();
			try {
				if (printWriter != null) {
					printWriter.close();
				}
				if (reader != null) {
					reader.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		}
		return result.toString();
	}
}
