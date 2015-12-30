package cn.mocredit.gateway.wangpos.controller;

import cn.mocredit.gateway.posp.xnzd.Entrance;
import cn.mocredit.gateway.service.ControllerService;
import cn.mocredit.gateway.util.Util;
import cn.mocredit.gateway.wangpos.bo.DemoData;
import cn.mocredit.gateway.wangpos.bo.DemoRetData;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static cn.mocredit.gateway.util.JacksonJsonMapper.jsonToObject;
import static cn.mocredit.gateway.util.JacksonJsonMapper.objectToJson;
import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.joining;
import static org.apache.commons.codec.digest.DigestUtils.md5Hex;
import static org.slf4j.LoggerFactory.getLogger;

@RestController
public class Rest {
    @Autowired
    Entrance entrance;
    @Autowired
    ControllerService controllerService;

    private Logger logger = getLogger(this.getClass());

    @Value("#{PropertiesStringParser.stringToMap('${aesPasswordMap}')}")
    private Map<String, String> aesPasswordMap;
    @Value("${sendToVerifyCodeModelUrl}")
    private String sendToVerifyCodeModelUrl;
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    @RequestMapping(value = "/tongxinceshi")
    public String test() {
        return sdf.format(new Date());
    }

    @RequestMapping(value = "/jiamiceshi")
    public String jiamiceshi(String h, String t) {
        return controllerService.jiamiceshi(h, t);
    }

    @RequestMapping(value = "/qiandao")
    public String qiandao(String h, String t) {
        return controllerService.qiandao(h, t);
    }

    @RequestMapping(value = "/qiandaohuizhi")
    public String qiandaohuizhi(String h, String t) {
        return controllerService.qiandaohuizhi(h, t);
    }

    @RequestMapping(value = "/jiesuan")
    public String jiesuan(String h, String t) {
        return controllerService.jiesuan(h, t);
    }

    @RequestMapping(value = "/huodongliebiao")
    public String huodongliebiao(String h, String t) {
        return controllerService.huodongliebiao(h, t);
    }

    @RequestMapping(value = "/xintiao")
    public String xintiao(String h, String t) {
        return controllerService.xintiao(h, t);
    }

    @RequestMapping(value = "/yanma")
    public String yanma(String h, String t) {
        return controllerService.yanma(h, t);
    }

    @RequestMapping(value = "/yanmachexiao")
    public String yanmachexiao(String h, String t) {
        return controllerService.yanmachexiao(h, t);
    }

    @RequestMapping(value = "/xiaofei")
    public String xiaofei(String h, String t) {
        return controllerService.xiaofei(h, t);
    }

    @RequestMapping(value = "/xiaofeichongzheng")
    public String xiaofeichongzheng(String h, String t) {
        return controllerService.xiaofeichongzheng(h, t);
    }

    @RequestMapping(value = "/chexiaochongzheng")
    public String chexiaochongzheng(String h, String t) {
        return controllerService.chexiaochongzheng(h, t);
    }

    @RequestMapping(value = "/chexiao")
    public String chexiao(String h, String t) {
        return controllerService.chexiao(h, t);
    }

    @RequestMapping(value="/syncdevcode")
    public String syncdevcode(@RequestBody String json) {return controllerService.syncdevcode(json);}

    @RequestMapping(value="/resetdevpassword")
    public String resetDevpassword(@RequestBody String json) {return controllerService.resetDevpassword(json);}

    @RequestMapping(value = "/xiaofeiOld")
    public String xiaofeiOld(String postData) {
        DemoData demoData = jsonToObject(postData, DemoData.class);
        DemoRetData ret = entrance.xf(demoData);
//        ret.setErrorCode("no_error");
//        ret.setErrorMes("no_msg");
        String cardNo = demoData.getCardNo();
        if ("error".equals(ret.getState())) {
            ret.setPrintInfo("交易失败！\n" + "---------------------------\n\n\n");
        } else {
            ret.setPrintInfo("---------------------------\n" +
                    "交通银行积分兑换签购单\n\n" +
                    "交易类型：积分消费\n" +
                    "------------------------\n" +
                    "商户名称：交行积分测试商户\n" +
                    "门店名称：交易积分测试门店\n" +
                    "商户编号：111111111111119\n" +
                    "终端编号：55550004\n" +
                    "订单号：" + demoData.getOrderId() + "\n" +
                    "卡号：" + Util.hide(cardNo, 6, "*", 4) + "\n" +

                    "商品名称：电风扇\n" +
                    "数量：1\n" +
                    "积分：400分\n" +
                    "------------------------\n" +
                    "备注\n" +
                    "------------------------\n" +
                    "持卡人签字\n\n\n" +
                    "---------------------------");
        }
        ret.setQr("qr");
        ret.setSaltValue(randomUUID().toString().replaceAll("-", "").toUpperCase());
        if (!"error".equals(ret.getState())) {
            ret.setState("ok");
        }
        return objectToJson(ret);
    }


}