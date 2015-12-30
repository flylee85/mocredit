package cn.mocredit.gateway.thirdparty.controller;

import cn.mocredit.gateway.thirdparty.bo.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Md5Crypt;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static cn.mocredit.gateway.util.AESCoder.decrypt;
import static cn.mocredit.gateway.util.AESCoder.encrypt;
import static cn.mocredit.gateway.util.HttpUtil.doPost;
import static cn.mocredit.gateway.util.JacksonJsonMapper.jsonToObject;
import static cn.mocredit.gateway.util.JacksonJsonMapper.objectToJson;
import static cn.mocredit.gateway.util.RSACoder.decryptByPrivateKey;
import static cn.mocredit.gateway.util.RSACoder.encryptByPublicKey;
import static java.util.UUID.randomUUID;
import static org.apache.commons.codec.digest.DigestUtils.md5Hex;
import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class Rest {
    private Logger logger = getLogger(this.getClass());

    @Value("#{PropertiesStringParser.stringToMap('${aesPasswordMap}')}")
    private Map<String, String> aesPasswordMap;
    @Value("${sendToVerifyCodeModelUrl}")
    private String sendToVerifyCodeModelUrl;
    @Value("${rsaPrivateKey}")
    private String rsaPrivateKey;

    public static void main1(String[] args) throws ParseException {
        String input = "20150911";
        String format = "yyyyMMdd";

        Calendar cal = Calendar.getInstance();
        cal.setTime(new SimpleDateFormat(format).parse(input));
        int week = cal.get(Calendar.DAY_OF_WEEK);
        System.out.println(week);
    }

    public static void main(String[] args) {
        String url = "http://222.128.38.93:9300/thirdparty/verifyCode";
        HashMap<String, String> map = new HashMap<>();
        String aid = "65043A6B229B4A74";
        String rkey = randomUUID().toString().replaceAll("-","").toUpperCase().substring(16);
        String data = aid + rkey;
        String publicKey = "30820122300d06092a864886f70d01010105000382010f003082010a0282010100b4f30cab754ba89765a1d8d6420f27b9e798a7d2752149df1e33760133aa9f75378dd1914b31d88afa3bb9b42c8a6385ff85abfb8f827b7466221291d632059e6aec7f3d12e5f36c81d6280f7ec2d25ffef8187bf1cfd6a226cafe8864a9479b0164d7f02b14b11b949e631d4bc3e7d8b572363f4acf9df3246dcf4dee18978fbdbc95668abec149aef0170473b745c507b801fade61205646e3c297d9f80efc14a5749ca1f174e94dba68fb634b5f2b018ce5f099490b5d2f0d406c84a6d828dcdaddbe6005d5cff1506b533a448775efc3560e86e024db0bb5b099c8761e4a1114c1b9e229fe4147222e3afcf4a26ad0a260863374cfa1fe2b89f68ed60fb90203010001";

        map.put("h", encryptByPublicKey(data, publicKey));
        VerifyCodeReq v = new VerifyCodeReq();
        v.setAcountNo("123");
        v.setDeviceCode("12345678");
        String akey = "B656E2B369F76EB1";
        map.put("t", encrypt(objectToJson(v), akey +rkey));
        doPost(url, map);
    }

    @RequestMapping(value = "/verifyCodeOld")
    public String verifyCodeOld(String postData) {
        PostData postDataObj = jsonToObject(postData, PostData.class);
        String decrypt = decrypt(postDataObj.getData(), aesPasswordMap.get(postDataObj.getEnterpriseId()));
        logger.info("外部系统请求验码报文：" + decrypt);
        return doStuff(decrypt);
    }

    @RequestMapping(value = "/verifyCode")
    public String verifyCode(String h, String t) {
        String s = decryptByPrivateKey(h, rsaPrivateKey);
        if (s == null || s.length() != 32) {
            return "报文格式错误";
        }
        String aid = s.substring(0, 16);
        logger.info("请求者aid：" + aid);
        String rkey = s.substring(16, 32);
        logger.info("rkey：" + rkey);
        String akey = aesPasswordMap.get(aid);
        logger.info("akey：" + akey);
        String md5Hex = md5Hex(akey + rkey).toUpperCase();
        logger.info("key：" + md5Hex);
        String decrypt = decrypt(t, md5Hex);
        logger.info("请求者报文：" + decrypt);
        String doStuff = doStuff(decrypt);
        return encrypt(doStuff,md5Hex);
    }

    private String doStuff(String decrypt) {
        VerifyCodeReq req = jsonToObject(decrypt, VerifyCodeReq.class);
        ToVerifyCodeModel tvcm = new ToVerifyCodeModel();
        tvcm.setCode(req.getCode());
        tvcm.setDevice(req.getDeviceCode());
        tvcm.setRequestSerialNumber(req.getOrderId());
        tvcm.setStoreId(req.getStoreCode());
        tvcm.setUseCount("1");
        tvcm.setAmount("6");
        String url = sendToVerifyCodeModelUrl + "/ActivityCode/verifyCode";
        String requestBody = objectToJson(tvcm);
        logger.info("URL:" + url + "\nPOST内容：\n" + requestBody);
        String resFromVerifyCodeModel = doPost(url, requestBody);
        logger.info("验码模块的返回值：" + resFromVerifyCodeModel);
        ReceiveFromVerifyCodeModel receive = jsonToObject(resFromVerifyCodeModel, ReceiveFromVerifyCodeModel.class);
        VerifyCodeResponse vcr = new VerifyCodeResponse();
        vcr.setRtnFlag("true".equalsIgnoreCase(receive.getSuccess()) ? "0" : receive.getErrorCode());
        vcr.setErrorMes(receive.getErrorMsg());
        ReceiveFromVerifyCodeModelDate data = receive.getData();
        vcr.setAmount(data.getAmount());
        vcr.setExpData(data.getEndTime());
        vcr.setIssueEnterpriseId(data.getIssueEnterpriseId());
        vcr.setIssueEnterpriseName(data.getIssueEnterpriseName());
        vcr.setOrderId(req.getOrderId());
        vcr.setTicketType("03");
        return objectToJson(vcr);
    }
}