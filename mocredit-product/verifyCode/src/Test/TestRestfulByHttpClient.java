import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

/**
 * 测试用 httpclient 请求验码系统
 * Created by YHL on 15/7/31 11:55.
 *
 * @version 1.0
 * @Auth YangHL
 * @Email yanghongli@mocredit.cn
 */
public class TestRestfulByHttpClient {


    public static void main(String args[]) throws Exception {

        CloseableHttpClient httpclient = HttpClients.createDefault();

//        HttpPost httpPost = new HttpPost("http://192.168.1.114:8080/verify_code/ActivityCode/verifyCode");
        HttpPost httpPost = new HttpPost("http://222.128.38.93:9090/verify_code/ActivityCode/correctOrRevokeActivityCode");
        httpPost.setHeader("Content-Type", "application/json;charset=UTF-8");
//        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//        nvps.add(new BasicNameValuePair("username", "vip"));
//        nvps.add(new BasicNameValuePair("password", "secret"));

        JSONObject jsonObj = new JSONObject();
//        jsonObj.put("amount", "6.03");
//        jsonObj.put("useCount",2);
//        jsonObj.put("device","aaaa");
//        jsonObj.put("storeId","STORE_0");
//        jsonObj.put("storeName","名字001");
//        jsonObj.put("requestSerialNumber","111");
//        jsonObj.put("shopId","SHOP_0");
//        jsonObj.put("shopName","222222");
//        jsonObj.put("code","code_12839");
            jsonObj.put("requestSerialNumber", "111");
            jsonObj.put("verifyType", "2");
            jsonObj.put("code", "ABCDEFG");


        StringEntity entity = new StringEntity(jsonObj.toJSONString(), HTTP.UTF_8);
        httpPost.setEntity( entity );
        CloseableHttpResponse response2 = httpclient.execute(httpPost);

        try {
            System.out.println("code:"+response2.getStatusLine().getStatusCode());
            HttpEntity entity2 = response2.getEntity();
            System.out.println("##"+ EntityUtils.toString(entity2));
            EntityUtils.consume(entity2);
        } finally {
            response2.close();
        }

    }


}
