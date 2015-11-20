package com.mocredit.base.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils; 

/** 
* http工具类
* 用来模拟一些http的请求或者处理http相关的对象
* @author lishoukun
* @date 2015/05/08
*/ 
public final class HttpUtil { 
	private static Log log = LogFactory.getLog(HttpUtil.class); 
	
	/** 
	 * 执行一个HTTP GET请求，返回请求响应的HTML 
	 * 
	 * @param url	请求的URL地址 
	 * @param queryString 	请求的查询参数,可以为null 
	 * @return 返回请求响应的HTML 
	 */ 
	public static String doGet(String url, String queryString) { 
		//定义URL和接收返回的字段
	 	String uriAPI = url+"?"+queryString;
		String result= "";
		
		//创建HttpGet或HttpPost对象，将要请求的URL通过构造方法传入HttpGet或HttpPost对象。
		//HttpGet httpRequst = new HttpGet(URI uri);
		//HttpGet httpRequst = new HttpGet(String uri);
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(uriAPI);
		httpGet.setHeader("Content-type" , "text/html; charset=utf-8");
		try {
			//使用DefaultHttpClient类的execute方法发送HTTP GET请求，并返回HttpResponse对象。
			//new DefaultHttpClient().execute(HttpUriRequst requst);
			HttpResponse httpResponse = httpClient.execute(httpGet);//其中HttpGet是HttpUriRequst的子类
			int statusCode = httpResponse.getStatusLine().getStatusCode();
   			if (statusCode == 200) {
   				result = EntityUtils.toString(httpResponse.getEntity(),"utf-8");
   				if (result != null) {
					return result.trim();
   				}else{
   					return null;
   				}
   			}else{
   				return null;
   			}
		}catch (ClientProtocolException e) {
			e.printStackTrace();
			result = e.getMessage().toString();
		} catch (IOException e) {
			e.printStackTrace();
			result = e.getMessage().toString();
		}finally{
			httpClient.getConnectionManager().shutdown();
			httpClient.close();
		}
		return result;
	} 
	
	/** 
	 * 执行一个HTTP POST请求，返回请求响应的HTML 
	 * 
	 * @param url	请求的URL地址 
	 * @param params	请求的查询参数,可以为null 
	 * @param charset 字符集 
	 * @return 返回请求响应的HTML 
	 */ 
	public static String doPost(String url, Map<String, String> params1) {
		//定义URL和接收返回的字段
		String uriAPI = url;//Post方式没有参数在这里
    	String result = "";
    	//创建HttpPost对象
    	DefaultHttpClient httpClient = new DefaultHttpClient();
    	HttpPost httpPost = new HttpPost(uriAPI);
    	//拼凑参数
    	List <NameValuePair> params = new ArrayList<NameValuePair>();
    	if(params1!=null){
    		Iterator<String> it = params1.keySet().iterator();
        	while(it.hasNext()){
        		String key = it.next();
        		String value = String.valueOf(params1.get(key));
        		params.add(new BasicNameValuePair(key,value));
        	}
    	}
    	
    	try {
    		httpPost.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
    		System.out.println("请求参数："+EntityUtils.toString(httpPost.getEntity()));
			HttpResponse httpResponse = httpClient.execute(httpPost);
		    if(httpResponse.getStatusLine().getStatusCode() == 200)
		    {
		    	HttpEntity httpEntity = httpResponse.getEntity();
		    	result = EntityUtils.toString(httpEntity);//取出应答字符串
		    }
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			result = e.getMessage().toString();
		}
		catch (ClientProtocolException e) {
			e.printStackTrace();
			result = e.getMessage().toString();
		}
		catch (IOException e) {
			e.printStackTrace();
			result = e.getMessage().toString();
		}finally{
			httpClient.getConnectionManager().shutdown();
			httpClient.close();
		}
    	return result;
	} 
	
	/** 
	 * 调用Restful请求
	 * 
	 * @param url	请求的URL地址 
	 * @param params	请求的查询参数,可以为null 
	 * @param charset 字符集 
	 * @return 返回请求响应的HTML 
	 */ 
	public static String doRestful(String url, String jsonStr) {
		//定义URL和接收返回的字段
		String uriAPI = url;//Post方式没有参数在这里
    	String result = "";
    	//创建HttpPost对象
    	CloseableHttpClient httpClient = HttpClientBuilder.create().build();
    	HttpPost httpPost = new HttpPost(uriAPI);
    	
    	try {
    		StringEntity entity = new StringEntity(jsonStr);
        	entity.setContentType("application/json;charset=UTF-8");
        	httpPost.setEntity(entity);
        	HttpResponse response = httpClient.execute(httpPost);
        	if(response.getStatusLine().getStatusCode() == 200)
    	    {
    	    	HttpEntity httpEntity = response.getEntity();
    	    	result = EntityUtils.toString(httpEntity);//取出应答字符串
    	    }
        	httpClient.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			result = e.getMessage().toString();
		}
		catch (ClientProtocolException e) {
			e.printStackTrace();
			result = e.getMessage().toString();
		}
		catch (IOException e) {
			e.printStackTrace();
			result = e.getMessage().toString();
		}finally{
			
		}
    	return result;
	} 
	
	/** 
	 * 调用Restful请求
	 * 
	 * @param urlPath	请求的URL地址 
	 * @param params	请求的查询参数,可以为null 
	 * @param charset 字符集 
	 * @return 返回请求响应的HTML 
	 */ 
	public static String doRestfulByHttpConnection(String urlPath, String jsonStr) {
		PrintWriter printWriter = null;  
        BufferedReader bufferedReader = null;
        HttpURLConnection httpConnection = null;  
		StringBuffer output = new StringBuffer();
		URL url;
		try {
			url = new URL(urlPath);
			//设置超时时间，这两句好像有用
			//System.setProperty("sun.net.client.defaultConnectTimeout", "300000");  
			//System.setProperty("sun.net.client.defaultReadTimeout", "300000");  
			httpConnection = (HttpURLConnection) url.openConnection();
			//设置超时时间，这两句好像不起作用
			//httpConnection.setConnectTimeout(300000);  
			//httpConnection.setReadTimeout(300000);   
		    httpConnection.setDoOutput(true);
		    httpConnection.setRequestMethod("POST");
		    httpConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");//头文件必须这样写，否则无效
		   
//		    printWriter = new PrintWriter(new OutputStreamWriter(httpConnection.getOutputStream(),"utf-8")); 
//		    printWriter.write(jsonStr);
//		    //flush输出流的缓冲  
//            printWriter.flush();  
		    OutputStream out = httpConnection.getOutputStream();
		    out.write(jsonStr.getBytes("utf-8"));
		    out.flush();
//		    OutputStreamWriter out = new OutputStreamWriter(httpConnection.getOutputStream());
//		    out.write(jsonStr);
//		    out.flush();
//		    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(httpConnection.getOutputStream(),"UTF-8"));  
//		    writer.write(jsonStr);
//		    writer.flush();

		    if (httpConnection.getResponseCode() != 200) {
		        throw new RuntimeException("Failed : HTTP error code : " + httpConnection.getResponseCode());
		    }

		    BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(),"utf-8"));
		    //读取返回结果
		    char[] b = new char[1024];
		    for (int n; (n = responseBuffer.read(b)) != -1;) {
		    	output.append(new String(b));
		    }
		    httpConnection.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {  
			httpConnection.disconnect();  
            try {  
                if (printWriter != null) {  
                    printWriter.close();  
                }  
                if (bufferedReader != null) {  
                    bufferedReader.close();  
                }  
            } catch (IOException ex) {  
                ex.printStackTrace();  
            }  
  
        }  
        return output.toString();
	} 
	/** 
	 * 执行一个HTTP POST请求，返回请求响应的HTML 
	 * 
	 * @param url	请求的URL地址 
	 * @param params	请求的查询参数,可以为null 
	 * @param charset 字符集 
	 * @return 返回请求响应的HTML 
	 */ 
	public static String doPostByHttpConnection(String url, Map<String, Object> requestParamsMap) {
	        PrintWriter printWriter = null;  
	        BufferedReader bufferedReader = null;  
	        StringBuffer responseResult = new StringBuffer();  
	        StringBuffer params = new StringBuffer();  
	        HttpURLConnection httpURLConnection = null;  
	        // 组织请求参数  
	        Iterator it = requestParamsMap.entrySet().iterator();  
	        while (it.hasNext()) {  
	            Map.Entry element = (Map.Entry) it.next();  
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
	            httpURLConnection = (HttpURLConnection) realUrl.openConnection();  
	            // 设置通用的请求属性  
	            httpURLConnection.setRequestProperty("accept", "*/*");  
	            httpURLConnection.setRequestProperty("connection", "Keep-Alive");  
	            httpURLConnection.setRequestProperty("Content-Length", String.valueOf(params.length()));  
	            httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
	            httpURLConnection.setRequestProperty("contentType", "application/json");
	            httpURLConnection.setRequestProperty("Charset", "utf-8");
	            // 发送POST请求必须设置如下两行  
	            httpURLConnection.setDoOutput(true);  
	            httpURLConnection.setDoInput(true);  
	            // 获取URLConnection对象对应的输出流  
	            printWriter = new PrintWriter(httpURLConnection.getOutputStream());  
	            // 发送请求参数  
	            printWriter.write(params.toString());  
	            // flush输出流的缓冲  
	            printWriter.flush();  
	            // 根据ResponseCode判断连接是否成功  
	            int responseCode = httpURLConnection.getResponseCode();  
	            if (responseCode != 200) {  
	                log.error(" Error===" + responseCode);  
	            } else {  
	                log.info("Post Success!");  
	            }  
	            // 定义BufferedReader输入流来读取URL的ResponseData  
	            bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(),"utf-8"));
//	            char[] b = new char[1024];
//			    for (int n; (n = bufferedReader.read(b)) != -1;) {
//			    	responseResult.append(new String(b));
//			    }
	            String line = null;  
	            while ((line = bufferedReader.readLine()) != null) {
			    	responseResult.append(line);
			    }
	        } catch (Exception e) {  
	            log.error("send post request error!" + e);  
	        } finally {  
	            httpURLConnection.disconnect();  
	            try {  
	                if (printWriter != null) {  
	                    printWriter.close();  
	                }  
	                if (bufferedReader != null) {  
	                    bufferedReader.close();  
	                }  
	            } catch (IOException ex) {  
	                ex.printStackTrace();  
	            }  
	  
	        }  
	        return responseResult.toString();
	    }  
	/** 
	 * 执行一个下载请求，返回请求响应的文件流 
	 * 
	 * @param url	请求的URL地址 
	 * @param params	请求的查询参数,可以为null 
	 * @param charset 字符集 
	 * @return 返回请求响应的HTML 
	 */ 
	public static InputStream doDownloadByHttpConnection(String url) {
        
		try {
			URL getUrl = new URL(url);
			// 根据拼凑的URL，打开连接，URL.openConnection函数会根据URL的类型，
	        // 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
	        HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
	        // 进行连接，但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发到
	        // 服务器
	        connection.connect();
	        // 取得输入流，并使用Reader读取
	        InputStream  in = connection.getInputStream();
	        // 断开连接
	    	//connection.disconnect();
	        return in;
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}  
	public static void main(String[] args) { 
		String user = "shcdwlw";
		//原密码为B9Wp802j，md5 32位小写后为76a36271631306061c31b36d375a330c
		String pwd = "76a36271631306061c31b36d375a330c";
		Map<String,String> param = new HashMap<String,String>();
		param.put("username", "shcdwlw");
		param.put("pwd", "76a36271631306061c31b36d375a330c");
		param.put("p", "18653978235");
		param.put("msg", "【领带科技】您好！欢迎使用餐道云管理平台，以下是您在本系统的账户信息：账号：9999002密码：690959。此为系统消息，请勿直接回复。");
		param.put("charSetStr","utf");
	    String y = doPost("http://api.app2e.com/smsBigSend.api.php", param); 
	    System.out.println(y); 
	} 
}