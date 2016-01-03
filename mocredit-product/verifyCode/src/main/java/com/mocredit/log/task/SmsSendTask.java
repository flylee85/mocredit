package com.mocredit.log.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mocredit.base.util.DateUtil;
import com.mocredit.base.util.TemplateUtil;
import com.mocredit.verifyCode.model.ActActivitySynLog;
import com.mocredit.verifyCode.model.TVerifyLog;
import com.mocredit.verifyCode.vo.SmsVO;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * Created by YHL on 15/7/31 21:56.
 *
 * @version 1.0
 * @Auth YangHL
 * @Email yanghongli@mocredit.cn
 */
@Component
public class SmsSendTask {
    private Logger logger=Logger.getLogger(SmsSendTask.class.getName());

    /**  存放发送短信的地址。 **/
    private String SMS_URL="SMS.URL";

    /** 存放短信发送来源， **/
    private String SMS_FROM="SMS.FROM";
    /** 请求资源类型**/
    private  String SMS_REQUEST_TYPE="SMS.REQUEST_TYPE";

    /**  请求主题内容 **/
    private String SMS_CONTENT_TYPE="SMS.CONTENT_TYPE";

    private boolean SMS_ACTIVITED=false;
    /**
     * 存放读取到的配置文件
     */
    private Properties prop = new Properties();


    public static List<SmsVO> smsList = new ArrayList<SmsVO>();


    public void init(){

//        System.out.println("-------------\n\n\n----------------");
        Resource resource = new ClassPathResource("config.properties");
        try {
            EncodedResource encRes = new EncodedResource(resource,"UTF-8");
            prop= PropertiesLoaderUtils.loadProperties(encRes);
            SMS_ACTIVITED= Boolean.parseBoolean( prop.getProperty("SMS.ACTIVITED","false") );
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println("******* sms-url:"+prop.get(SMS_URL));
//        System.out.println(prop.toString());
    }


    @Transactional
    @Scheduled(cron = "0/15 * * * * ?")
    public void doSend() throws Exception{
        //TODO 发送短信
        logger.debug("##### ----- log----------#### 短信缓冲池数量：" + smsList.size());
        if( SMS_ACTIVITED ) {
            if (smsList.size() > 0) {
                List<SmsVO> copyList = smsList;
                if (sendSms(copyList)) {
                    smsList.removeAll(copyList);
                }
            }
        }
    }

    public  void cleanUp(){
        logger.info(" 销毁短信缓冲池，缓冲池数量："+smsList.size());
        //如果还有日志没有记录，则记录日志
        if( smsList.size()>0){
            List<SmsVO> copyList = smsList;
            if( sendSms(copyList) ){
                smsList.removeAll(copyList);
            }else{
                logger.error("#### 存在待发短信，没有发送完毕....####");
                logger.error(JSON.toJSONStringWithDateFormat(smsList, DateUtil.FORMAT_YYYYMMDD_HHMMSS));
            }
        }
    }

    /**
     * 调用restful方法，发送短信
     * @return
     */
    private boolean sendSms( List<SmsVO> copyList){
        boolean result=false;

        Map<String ,Object> m1=new HashMap<String,Object>();
        List<Map<String,Object>> data= new ArrayList<Map<String, Object>>();
        Map<String ,Object> m_msg=new HashMap<String,Object>();

        for( SmsVO s : copyList ){
            m_msg=new HashMap<String,Object>();
            m_msg.put("name",s.getCustormName());
            m_msg.put("mobile",s.getCustormMobile());
            m_msg.put("content", TemplateUtil.buildStringFromTemplate( s.getTemplateMap(),s.getTemplateString()));
            data.add(m_msg);
        }
        m1.put("fromCode","验码系统");
        m1.put("data",data);

        try {
            String json = JSON.toJSONStringWithDateFormat(m1,DateUtil.FORMAT_YYYYMMDD_HHMMSS );
            URL url = new URL( prop.getProperty(SMS_URL) );
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod( prop.getProperty(SMS_REQUEST_TYPE ));
            httpConnection.setRequestProperty("Content-Type", prop.getProperty(SMS_CONTENT_TYPE) );

            OutputStream outputStream = httpConnection.getOutputStream();
            outputStream.write(json.getBytes());
            outputStream.flush();

            if (httpConnection.getResponseCode() != 200) {
                throw new Exception("Failed : HTTP error code : "
                        + httpConnection.getResponseCode());
            }

            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                    (httpConnection.getInputStream())));

            String output="";
            String tmp="";
            logger.debug("Output from Server:\n");
            while ( !StringUtils.isEmpty( tmp = responseBuffer.readLine()) ) {
                 output+=tmp;
                logger.debug(output);
            }

            httpConnection.disconnect();

            logger.debug("发送短信响应内容："+output);
            JSONObject jobj=JSON.parseObject(output);

            if( jobj.getBoolean("success")){
                result=true;
            }else{
                logger.debug("发送短信内容失败：errorCode="+jobj.getString("errorCode")+" \t 错误消息："+jobj.getString("errorMsg"));
            }

        }catch(Exception e){
            logger.error("发送短信发送异常："+e.toString());
            e.printStackTrace();
        }


        return  result;
    }

}
