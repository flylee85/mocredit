package cn.mocredit.gateway.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;
import static org.apache.http.client.utils.URLEncodedUtils.format;
import static org.apache.http.impl.client.HttpClients.createDefault;

public final class HttpUtil {
    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * @param url 地址
     */
    public static String doGet(String url, Map<String, String> map) {
        try {
            List<BasicNameValuePair> list = map.keySet().stream().sorted().map(k -> new BasicNameValuePair(k, map.get(k))).collect(toList());
            String p = format(list, "UTF-8");
            String uri = url + (p != null && !p.isEmpty() ? ("?" + p) : "");
            logger.debug("58DFBF9807B240B095D3CC48EF57DFF3" + uri);
            HttpResponse response = createDefault().execute(new HttpGet(uri));
            int statusCode = response.getStatusLine().getStatusCode();
            return statusCode == 200 ? EntityUtils.toString(response.getEntity()) : ("" + statusCode);
        } catch (Exception e) {
            String st = getStackTrace(e);
            logger.error(st);
            return st;
        }
    }

    /**
     * @param url 地址
     * @param map 参数
     */
    public static String doPost(String url, Map<String, String> map) {
        try {
            Function<String, BasicNameValuePair> f = key -> new BasicNameValuePair(key, map.get(key));
            List<BasicNameValuePair> parameters = map.keySet().stream().sorted().map(f).collect(toList());
            HttpPost post = new HttpPost(url);
            post.setEntity(new UrlEncodedFormEntity(parameters, "UTF-8"));
            HttpResponse response = createDefault().execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            return statusCode == 200 ? EntityUtils.toString(response.getEntity()) : ("" + statusCode);
        } catch (Exception e) {
            String st = getStackTrace(e);
            logger.error(st);
            return st;
        }
    }

    /**
     * @param url         地址
     * @param requestBody 请求体
     * @return
     */
    public static String doPost(String url, String requestBody) {
        try {
            HttpPost post = new HttpPost(url);
            post.setEntity(new StringEntity(requestBody, "UTF-8"));
            HttpResponse response = createDefault().execute(post);
            int statusCode = response.getStatusLine().getStatusCode();
            return statusCode == 200 ? EntityUtils.toString(response.getEntity()) : ("" + statusCode);
        } catch (Exception e) {
            String st = getStackTrace(e);
            logger.error(st);
            return st;
        }
    }

    //	public static CloseableHttpResponse httpForJson(String url,JSONObject jSONObject){
    public static String httpForJson(String url, JSONObject jSONObject) {
        CloseableHttpResponse responseForJson = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
        String postString = jSONObject.toJSONString();
        logger.info("92BFE348FB9E4F51A5D44339344DCEA2 发送请求到： " + url + " 内容为： " + postString);
        StringEntity entity = new StringEntity(postString, HTTP.UTF_8);
        httpPost.setEntity(entity);
        String response = null;
        try {
            responseForJson = httpclient.execute(httpPost);
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            System.out.println("code:" + responseForJson.getStatusLine().getStatusCode());
            HttpEntity entity2 = responseForJson.getEntity();
            try {
                response = EntityUtils.toString(entity2, "GBK");
                System.out.println("httpUtiltoSting======" + response);


            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            try {
                EntityUtils.consume(entity2);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } finally {
            try {
                responseForJson.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        return response;
    }
}