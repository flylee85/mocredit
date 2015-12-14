package com.mocredit.integral.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

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

        System.out.println("POST parameter : " + parameterBuffer.toString());

        URL localURL = new URL(url);

        URLConnection connection = openConnection(localURL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Accept-Charset", CHARSET);
        httpURLConnection
                .setRequestProperty("Content-Type", "application/json");
        httpURLConnection.setRequestProperty("Content-Length",
                String.valueOf(parameterBuffer.length()));

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

            outputStreamWriter.write(parameterBuffer.toString());
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
        httpURLConnection.setRequestProperty("Accept-Encoding", "gzip");
        httpURLConnection.setRequestProperty("Accept-Charset", CHARSET);
        httpURLConnection
                .setRequestProperty("Content-Type", "application/json");
        httpURLConnection.setRequestProperty("ContentType", CHARSET);
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

    public static void main(String[] args) throws Exception {
        String a = "00\t交易成功\n" +
                "01\t查发卡方\n" +
                "02\t查发卡方的特殊条件\n" +
                "03\t无效商户\n" +
                "04\t没收卡\n" +
                "05\t不予承兑\n" +
                "06\t出错\n" +
                "07\t特殊条件下没收卡\n" +
                "09\t请求正在处理中\n" +
                "10\t部分金额批准\n" +
                "11\t重要人物批准\n" +
                "12\t无效交易\n" +
                "13\t无效金额\n" +
                "14\t无效卡号（无此号）\n" +
                "15\t无此发卡方/拒绝\n" +
                "16\t批准更新第三磁道\n" +
                "17\t拒绝但不没收卡\n" +
                "19\t重新送入交易\n" +
                "20\t无效响应\n" +
                "21\t不能采取行动\n" +
                "22\t故障怀疑\n" +
                "23\t不可接受的交易费\n" +
                "25\t找不到原始交易\n" +
                "30\t格式错误\n" +
                "31\t交换中心不支持的银行\n" +
                "32\t部分完成\n" +
                "33\t过期的卡\n" +
                "34\t有作弊嫌疑\n" +
                "35\t受卡方与代理方联系\n" +
                "36\t受限制的卡\n" +
                "37\t受卡方电话通知代理方安全部门s\n" +
                "38\t超过允许的PIN试输入\n" +
                "39\t无贷记账户\n" +
                "40\t请求的功能尚不支持s\n" +
                "41\t挂失卡\n" +
                "42\t无此账户\n" +
                "43\t被窃卡\n" +
                "44\t无此投资账户\n" +
                "51\t资金不足\n" +
                "52\t无此支票账户\n" +
                "53\t无此储蓄卡账户\n" +
                "54\t过期的卡\n" +
                "55\t不正确的PIN\n" +
                "56\t无此卡记录\n" +
                "57\t不允许持卡人进行的交易\n" +
                "58\t不允许终端进行的交易\n" +
                "59\t有作弊嫌疑\n" +
                "60\t受卡方与代理方联系\n" +
                "61\t超出取款/转账金额限制\n" +
                "62\t受限制的卡\n" +
                "63\t侵犯安全\n" +
                "64\t原始金额错误\n" +
                "65\t超出取款次数限制\n" +
                "66\t受卡方通知受理方安全部门\n" +
                "67\t强行受理（要求在自动会员机上没收此卡）\n" +
                "68\t接收的响应已过时\n" +
                "75\t允许的输入PIN次数超限\n" +
                "76\t无效账户\n" +
                "90\t正在日终处理（系统终止一天的活动，开始第二天的活动，交易在几分钟后可再次发送）\n" +
                "91\t发卡方或交换中心不能操作\n" +
                "92\t金融机构或中间网络设施找不到或无法达到、金融机构签退\n" +
                "93\t交易违法、不能完成\n" +
                "94\t重复交易\n" +
                "95\t核对差错\n" +
                "96\t系统异常、失效\n" +
                "97\tATM/POS终端号找不到\n" +
                "98\t交换中心收不到发卡方应答\n" +
                "99\tPIN 格式错\n" +
                "A0\tMAC鉴别失败";
        for (String b : a.split("\n")) {
            String key = b.split("\t")[0];
            String value = b.split("\t")[1];
            Map<String, String> mapMs = new HashMap<>();
            mapMs.put(key, value);
            System.out.println("BANK_JT_MAP.put(\"key\",\"value\");".replace("key", key.trim()).replace("value", value).trim());
        }
        // System.out.println(doPostJson("http://localhost:8080/integral/interface/activityImport",
        // "{\"activityId\":\"1\",\"startTime\":\"2015-08-12 11:11:11\",\"storeList\":\"[{\"shopId\":\"1\",\"storeId\":\"3\"}]\"}")
        // );

        /*System.out
                .println(doPostJson(
                        "http://117.121.20.137:10000/bank/payment",
                        "{\"cardExpDate\":\"1249\",\"cardNum\":\"6013823100097711014\",\"code\":\"MS0001\",\"orderId\":\"795060113343\",\"tranAmt\":\"23500\"}"));*/
       /* System.out
                .println(doPostJson(
                        "http://192.168.23.123:10001/bank/payment",
                        "{\"cardExpDate\":\"0720\",\"cardNum\":\"6226880053372785\",\"code\":\"TPYKF\",\"orderId\":\"79501604113343\",\"tranAmt\":\"23500\"}"));*/
       /* System.out
                .println(doPostJson(
                        "http://192.168.23.123:10001/bank/payment",
                        "{\"cardExpDate\":\"0320\",\"cardNum\":\"4392260028245056\",\"code\":\"COSTAHB\",\"orderId\":\"01212180262\",\"tranAmt\":\"500\"}"));*/
     /*   System.out
                .println(doPostJson(
                        "http://192.168.100.155:10001/bank/payment",
                        "{\"cardExpDate\":\"1249\",\"cardNum\":\"6013823100097711014\",\"code\":\"MS0001\",\"orderId\":\"7950604113343\",\"tranAmt\":\"23500\"}"));*/

        System.out
                .println(doPostJson(

                        "http://192.168.23.123:10001/bank/paymentRevoke",
                        "{\"orderId\":\"088015345403\",\"oldOrderId\":\"088015345402\"}"));

      /*  System.out
                .println(doPostJson(
                        "http://117.121.20.137:10000/bank/payment",
                        "{\"cardExpDate\":\"0924\",\"cardNum\":\"6214850280354785\",\"code\":\"MS0001\",\"orderId\":\"130051931012469\",\"tranAmt\":\"100\"}"));

        System.out
                .println(doPostJson(

                        "http://117.121.20.137:10000/bank/paymentRevoke",
                        "{\"orderId\":\"13005191012669\",\"oldOrderId\":\"13005191012469\"}"));

        System.out
                .println(doPostJson(

                        "http://117.121.20.137:10000/bank/paymentReserval",
                        "{\"orderId\":\"240059002470\",\"oldOrderId\":\"130059002469\"}"));

        System.out
                .println(doPostJson(

                        "http://117.121.20.137:10000/bank/paymentRevokeReserval",
                        "{\"orderId\":\"250059002470\",\"oldOrderId\":\"240059002470\"}"));

        System.out
                .println(doPostJson(

                        "http://117.121.20.146:9092/integral/activitySyn",
                        "{\"enCode\":\"5773e998\"}"));*/
//        System.out
//                .println(doPostJson(
//                        "http://127.0.0.1:8080/payment",
//                        "{\"activityId\":\"ZSYH11\",\"cardExpDate\":\"2003\",\"cardNum\":\"4392260028467460\",\"enCode\":\"82393007\",\"orderId\":\"5773e998185723513\",\"transAmt\":\"null\"}"));
//        System.out
//                .println(doPostJson(
//                        "http://127.0.0.1:8080/paymentOld?activityId=ZSYH11&cardExpDate=2003&cardNum=4392260028467460&enCode=82393007", ""));
    }
}
