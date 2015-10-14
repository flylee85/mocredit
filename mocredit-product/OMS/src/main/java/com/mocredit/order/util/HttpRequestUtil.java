package com.mocredit.order.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class HttpRequestUtil {
	private final static String CHARSET = "utf-8";
	private final static Integer CONNECTIMREOUT = 5000;
	private final static Integer SOCKETTIMROUT = 5000;

	/**
	 * Do GET request
	 * 
	 * @param url
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	public static String doGet(String url) throws Exception {

		URL localURL = new URL(url);

		URLConnection connection = openConnection(localURL);
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

		httpURLConnection.setRequestProperty("Accept-Charset", CHARSET);
		httpURLConnection
				.setRequestProperty("Content-Type", "application/json");
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;

		if (httpURLConnection.getResponseCode() >= 300) {
			throw new Exception(
					"HTTP Request is not success, Response code is "
							+ httpURLConnection.getResponseCode());
		}

		try {
			inputStream = httpURLConnection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream, CHARSET);
			reader = new BufferedReader(inputStreamReader);
			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine);
			}
		} finally {

			if (reader != null) {
				reader.close();
			}
			if (inputStreamReader != null) {
				inputStreamReader.close();
			}

			if (inputStream != null) {
				inputStream.close();
			}
		}
		return resultBuffer.toString();
	}

	/**
	 * Do POST request
	 * 
	 * @param url
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String url, Map<?, ?> parameterMap)
			throws Exception {

		/* Translate parameter map to parameter date string */
		URL localURL = new URL(url);
		URLConnection connection = openConnection(localURL);
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
		StringBuffer parameterBuffer = new StringBuffer();
		if (parameterMap != null) {
			Iterator<?> iterator = parameterMap.keySet().iterator();
			String key = null;
			String value = null;
			while (iterator.hasNext()) {
				key = (String) iterator.next();
				if (parameterMap.get(key) != null) {
					value = parameterMap.get(key) + "";
				} else {
					value = "";
				}

				parameterBuffer.append(key).append("=").append(value);
				if (iterator.hasNext()) {
					parameterBuffer.append("&");
				}
			}
		}
		String parameterData = parameterBuffer.toString();
		System.out.println("URL：" + url + " POST parameter : " + parameterData);

		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
		httpURLConnection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		httpURLConnection.setRequestProperty("Content-Length",
				String.valueOf(parameterData.length()));

		OutputStream outputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;

		try {
			outputStream = httpURLConnection.getOutputStream();
			outputStreamWriter = new OutputStreamWriter(outputStream, CHARSET);

			outputStreamWriter.write(parameterData.toString());
			outputStreamWriter.flush();

			if (httpURLConnection.getResponseCode() >= 300) {
				throw new Exception(
						"HTTP Request is not success, Response code is "
								+ httpURLConnection.getResponseCode());
			}

			inputStream = httpURLConnection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream, CHARSET);
			reader = new BufferedReader(inputStreamReader);

			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine);
			}

		} finally {

			if (outputStreamWriter != null) {
				outputStreamWriter.close();
			}

			if (outputStream != null) {
				outputStream.close();
			}

			if (reader != null) {
				reader.close();
			}

			if (inputStreamReader != null) {
				inputStreamReader.close();
			}

			if (inputStream != null) {
				inputStream.close();
			}

		}

		return resultBuffer.toString();
	}

	public static String doPostJson(String url, String param) throws Exception {
		URL localURL = new URL(url);
		URLConnection connection = openConnection(localURL);
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setRequestProperty("Accept-Charset", CHARSET);
		httpURLConnection
				.setRequestProperty("Content-Type", "application/json");
		httpURLConnection.setRequestProperty("Content-Length",
				String.valueOf(param.length()));
		OutputStream outputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;
		try {
			outputStream = httpURLConnection.getOutputStream();
			outputStreamWriter = new OutputStreamWriter(outputStream, CHARSET);
			outputStreamWriter.write(param);
			outputStreamWriter.flush();
			if (httpURLConnection.getResponseCode() >= 300) {
				throw new Exception(
						"HTTP Request is not success, Response code is "
								+ httpURLConnection.getResponseCode());
			}
			inputStream = httpURLConnection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream, CHARSET);
			reader = new BufferedReader(inputStreamReader);
			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine);
			}
		} finally {
			if (outputStreamWriter != null) {
				outputStreamWriter.close();
			}
			if (outputStream != null) {
				outputStream.close();
			}
			if (reader != null) {
				reader.close();
			}
			if (inputStreamReader != null) {
				inputStreamReader.close();
			}
			if (inputStream != null) {
				inputStream.close();
			}
		}
		return resultBuffer.toString();
	}

	private static URLConnection openConnection(URL localURL)
			throws IOException {
		URLConnection connection;
		connection = localURL.openConnection();
		connection.setConnectTimeout(CONNECTIMREOUT);
		connection.setReadTimeout(SOCKETTIMROUT);
		return connection;
	}

	public static String doPost() throws Exception {
		String parameterData = "offset=0&pagesize=2";

		URL localURL = new URL("http://127.0.0.1:8081/integral/downloadOrder");
		URLConnection connection = localURL.openConnection();
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

		httpURLConnection.setDoOutput(true);
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
		httpURLConnection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		httpURLConnection.setRequestProperty("Content-Length",
				String.valueOf(parameterData.length()));

		OutputStream outputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;

		try {
			outputStream = httpURLConnection.getOutputStream();
			outputStreamWriter = new OutputStreamWriter(outputStream);

			outputStreamWriter.write(parameterData.toString());
			outputStreamWriter.flush();

			if (httpURLConnection.getResponseCode() >= 300) {
				throw new Exception(
						"HTTP Request is not success, Response code is "
								+ httpURLConnection.getResponseCode());
			}

			inputStream = httpURLConnection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream);
			reader = new BufferedReader(inputStreamReader);

			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine);
			}

		} finally {

			if (outputStreamWriter != null) {
				outputStreamWriter.close();
			}

			if (outputStream != null) {
				outputStream.close();
			}

			if (reader != null) {
				reader.close();
			}

			if (inputStreamReader != null) {
				inputStreamReader.close();
			}

			if (inputStream != null) {
				inputStream.close();
			}

		}

		return resultBuffer.toString();
	}

	public static void main(String[] args) throws Exception {
		
		UUID uuid = UUID.randomUUID();
		System.out.println (uuid);
		// System.out.println(doPostJson("http://localhost:8080/integral/interface/activityImport",
		// "{\"activityId\":\"1\",\"startTime\":\"2015-08-12 11:11:11\",\"storeList\":\"[{\"shopId\":\"1\",\"storeId\":\"3\"}]\"}")
		// );
		Map parameterMap = new HashMap();
		parameterMap.put("offset", 0);
		parameterMap.put("pagesize", 2);
		//
		// System.out.println(doPost());

		System.out.println(doPost(
				"http://127.0.0.1:8081/integral/downloadOrder", parameterMap));

		/*
		 * System.out .println(doGet(
		 * "http://localhost:8081/integral/downloadOrder?offset=0&pagesize=2"));
		 */
	}
}
