package cn.mocredit.gateway.service.impl;

import static cn.mocredit.gateway.util.AESCoder.decrypt;
import static cn.mocredit.gateway.util.AESCoder.encrypt;
import static cn.mocredit.gateway.util.JacksonJsonMapper.jsonToObject;
import static cn.mocredit.gateway.util.JacksonJsonMapper.objectToJson;
import static cn.mocredit.gateway.util.RSACoder.decryptByPrivateKey;
import static cn.mocredit.gateway.util.Util.fmtDate2Str;
import static cn.mocredit.gateway.util.Util.qianZhui;
import static cn.mocredit.gateway.util.Util.uuid;
import static java.lang.Integer.parseInt;
import static java.util.UUID.randomUUID;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.codec.digest.DigestUtils.md5Hex;
import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;
import static org.slf4j.LoggerFactory.getLogger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;

import cn.mocredit.gateway.data.entities.Activity;
import cn.mocredit.gateway.data.entities.CheckCodeRecord;
import cn.mocredit.gateway.data.entities.Device;
import cn.mocredit.gateway.data.entities.JieSuan;
import cn.mocredit.gateway.data.entities.LiuShui;
import cn.mocredit.gateway.data.repository.ActivityRepository;
import cn.mocredit.gateway.data.repository.CheckCodeRecordRepository;
import cn.mocredit.gateway.data.repository.ChongZhengRepository;
import cn.mocredit.gateway.data.repository.DeviceRepository;
import cn.mocredit.gateway.data.repository.JieSuanRepository;
import cn.mocredit.gateway.data.repository.LiuShuiRepository;
import cn.mocredit.gateway.message.HxData;
import cn.mocredit.gateway.message.HxForJson;
import cn.mocredit.gateway.service.ControllerService;
import cn.mocredit.gateway.util.HttpUtil;
import cn.mocredit.gateway.util.JacksonJsonMapper;
import cn.mocredit.gateway.util.XmlUtil;
import cn.mocredit.gateway.wangpos.bo.ActivityBo;
import cn.mocredit.gateway.wangpos.bo.Args;
import cn.mocredit.gateway.wangpos.bo.BankBo;
import cn.mocredit.gateway.wangpos.bo.CheXiaoQingQiuData;
import cn.mocredit.gateway.wangpos.bo.CodeRevokeBo;
import cn.mocredit.gateway.wangpos.bo.EitemBo;
import cn.mocredit.gateway.wangpos.bo.EitemListBo;
import cn.mocredit.gateway.wangpos.bo.EitemRevertBo;
import cn.mocredit.gateway.wangpos.bo.Huodongliebiao;
import cn.mocredit.gateway.wangpos.bo.JiaMiCeShiData;
import cn.mocredit.gateway.wangpos.bo.JieSuanXiangYingData;
import cn.mocredit.gateway.wangpos.bo.JsonData;
import cn.mocredit.gateway.wangpos.bo.ObjectAgrs;
import cn.mocredit.gateway.wangpos.bo.QianDaoData;
import cn.mocredit.gateway.wangpos.bo.QianDaoHuiZhiData;
import cn.mocredit.gateway.wangpos.bo.ShoudanBo;
import cn.mocredit.gateway.wangpos.bo.ShoudanResponse;
import cn.mocredit.gateway.wangpos.bo.XiaoFeiQingQiuData;
import cn.mocredit.gateway.wangpos.bo.XiaoFeiXiangYingData;
import cn.mocredit.gateway.wangpos.bo.XinTiaoData;
import cn.mocredit.gateway.wangpos.bo.YanMaQingQiuData;
import cn.mocredit.gateway.wangpos.bo.YanMaXiangYingData;

@Service
@Transactional
public class ControllerServiceImpl implements ControllerService {
    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    ActivityRepository activityRepository;
    @Autowired
    LiuShuiRepository liuShuiRepository;
    @Autowired
    JieSuanRepository jieSuanRepository;
    @Autowired
    ChongZhengRepository chongZhengRepository;
    @Autowired
    CheckCodeRecordRepository checkCodeRecordRepository;
    
    /**
     * 活动id的前缀，当活动是中信权益活动时使用此前缀
     */
    private static final String ZXQY = "ZXQY";
    private Logger logger = getLogger(this.getClass());
    @Value("${rsaPrivateKey}")
    private String rsaPrivateKey;
    @Value("${sendToVerifyCodeModelUrl}")
    private String sendToVerifyCodeModelUrl;
    @Value("${restBarcodeServiceAddress}")
    private String restBarcodeServiceAddress;

    @Value("${getActivitiesServiceUrl}")
    private String getActivitiesServiceUrl;
    @Value("${consumeServiceUrl}")
    private String consumeServiceUrl;
    @Value("${consumeCancelUrl}")
    private String consumeCancelUrl;
    @Value("${consumeCorrectUrl}")
    private String consumeCorrectUrl;
    @Value("${consumeCancelCorrectUrl}")
    private String consumeCancelCorrectUrl;
    @Value("${checkcodeCancelNewUrl}")
    private String checkcodeCancelNewUrl;
	@Value("${shoudantongbuUrl}")
	private String shoudantongbuUrl;
    @Value("${fakeDemo}")
    private boolean fakeDemo;

    private static final String OPER_ADD = "1";
    private static final String OPER_MDF = "2";
    private static final String OPER_DEL = "3";

    public String qiandao(String h, String t) {
        TongXin tx = new TongXin(h, t).invoke();
        JsonData jsonData = tx.getJsonData();
        if (jsonData == null) return "-1";
        QianDaoData ret = new QianDaoData();
        ret.setState("ok");
        String newakey = randomUUID().toString().replaceAll("-", "").toUpperCase().substring(16);
        ret.setNewakey(newakey);
        tx.getDevice().setTemppassword(newakey);
        jsonData.setjData(objectToJson(ret));
        jsonData.setTimestamp(fmtDate2Str(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
        String content = objectToJson(jsonData);
        logger.info(content);
        return encrypt(content, tx.getMd5Hex());
    }

    @Override
    public String qiandaohuizhi(String h, String t) {
        TongXin tx = new TongXin(h, t).invoke();
        JsonData jsonData = tx.getJsonData();
        if (jsonData == null) return "-1";
        QianDaoHuiZhiData req = jsonToObject(jsonData.getjData(), QianDaoHuiZhiData.class);
        Device device = tx.getDevice();
        if (req.getOldAkey().equals(device.getPassword()) && req.getNewAkey().equals(device.getTemppassword())) {
            device.setPassword(device.getTemppassword());
            req.setNewAkey(null);
            req.setOldAkey(null);
            req.setState("ok");
            jsonData.setjData(objectToJson(req));
            jsonData.setTimestamp(fmtDate2Str(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
            String content = objectToJson(jsonData);
            logger.info(content);
            return encrypt(content, tx.getMd5Hex());
        } else {
            req.setNewAkey(null);
            req.setOldAkey(null);
            req.setState("error");
            jsonData.setjData(objectToJson(req));
            jsonData.setTimestamp(fmtDate2Str(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
            String content = objectToJson(jsonData);
            logger.info(content);
            return encrypt(content, tx.getMd5Hex());
        }
    }

    @Override
    public String xintiao(String h, String t) {
        TongXin tx = new TongXin(h, t).invoke();
        JsonData jsonData = tx.getJsonData();
        if (jsonData == null) return "-1";
        XinTiaoData xinTiaoData = new XinTiaoData();
        xinTiaoData.setState("ok");
        xinTiaoData.setAction(tx.getDevice().getXintiaohouxu());
        jsonData.setjData(objectToJson(xinTiaoData));
        jsonData.setTimestamp(fmtDate2Str(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
        String content = objectToJson(jsonData);
        logger.info(content);
        return encrypt(content, tx.getMd5Hex());
    }
    public String fakeYanma(String h, String t) {
        TongXin tx = new TongXin(h, t).invoke();
        JsonData jsonData = tx.getJsonData();
        if (jsonData == null) return "-1";
        YanMaQingQiuData req = jsonToObject(jsonData.getjData(), YanMaQingQiuData.class);
        YanMaXiangYingData ret = new YanMaXiangYingData();
        String code = req.getCode();
        ret.setRtnFlag("0");
        ret.setErrorMes("");
        String orderId = req.getOrderId();
        ret.setOrderId(orderId);
        ret.setYmOrderId(uuid());
        ret.setDes("测试活动");
        ret.setErweima(orderId);
        ret.setTitle("活动标题");
        jsonData.setjData(objectToJson(ret));
        jsonData.setTimestamp(fmtDate2Str(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
        String content = objectToJson(jsonData);
        logger.info(content);
        return encrypt(content, tx.getMd5Hex());
    }
    @Override
    public String yanma(String h, String t) {
        if (fakeDemo) {
            return fakeYanma(h, t);
        }
        TongXin tx = new TongXin(h, t).invoke();
        JsonData jsonData = tx.getJsonData();
        if (jsonData == null) return "-1";
        YanMaQingQiuData req = jsonToObject(jsonData.getjData(), YanMaQingQiuData.class);
        YanMaXiangYingData ret = new YanMaXiangYingData();
        ret.setTitle("福码");//TODO 如何获得新老活动的名称？先写死。。。
        String code = req.getCode();
        Device device = tx.getDevice();
        if (code.length() <= 12) {
            //去老系统验码
            String searchno = uuid();
            String batchno = req.getOrderId();
            EitemBo eitem = barcodeserviceYanma(device.getDevcode(), req.getCode(), "0", "1", batchno, searchno);
            String isSuccess = eitem.getIsSuccess();

            ret.setRtnFlag("true".equals(isSuccess) ? "0" : "1");
            ret.setErrorMes(eitem.getError());
            ret.setOrderId(batchno);
            ret.setYmOrderId(searchno);
            ret.setDes(eitem.getDescription());
            ret.setPosno(eitem.getPosno());
            ret.setMmsid(eitem.getMmsId());
            ret.setBatchno(eitem.getBacthNo());
            ret.setErweima(req.getCode());
            ret.setPosno(batchno);
            ret.setPrintInfo(eitem.getXiaoTiao().replace("\\n", "\n"));
            jsonData.setjData(objectToJson(ret));
            jsonData.setTimestamp(fmtDate2Str(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
            String content = objectToJson(jsonData);
            logger.info(content);
            CheckCodeRecord ccr = new CheckCodeRecord();
            ccr.setId(uuid());
            ccr.setImei(device.getDevcode());
            ccr.setCode(req.getCode());
            ccr.setBatchno(batchno);
            ccr.setSearchno(searchno);
            checkCodeRecordRepository.save(ccr);
            return encrypt(content, tx.getMd5Hex());
        } else {
            //去杨宏利的验码模块验码
            StringBuffer bUrl = new StringBuffer();
            bUrl.append("?bType=01");
            //活动编号
            bUrl.append("&aId=");
            bUrl.append("&sId=222");
            //卡bin
            bUrl.append("&cBin=");
            //ip
            bUrl.append("&ip=219.238.232.139");
            //code
            bUrl.append("&code=" + code);
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("amount", "0");
            jSONObject.put("useCount", "1");
            jSONObject.put("device", device.getDevcode());
            jSONObject.put("storeCode", device.getStoreCode());
            jSONObject.put("requestSerialNumber", req.getOrderId());
            jSONObject.put("shopId", device.getShopid());
            jSONObject.put("code", code);
            String ss = HttpUtil.httpForJson(sendToVerifyCodeModelUrl + "/ActivityCode/verifyCode", jSONObject);
            logger.info("1DAA487F859F423BABC6C58441238706 验码模块的返回值："+ss);
            HxForJson resJson = JacksonJsonMapper.jsonToObject(ss, HxForJson.class);

            if (resJson.getSuccess().equals("true")) {
                ret.setRtnFlag("0");
                ret.setErrorMes(resJson.getErrorMsg());
                ret.setOrderId(ret.getOrderId());
                ret.setYmOrderId(ret.getOrderId());
                HxData data = resJson.getData();
                ret.setExpData(data.getEndTime());
                ret.setDes(data.getActivityName());
                ret.setAmount(data.getAmount());
                ret.setPrintInfo("---------------------------\n" +
                        data.getTicketTitle() + "\n\n" +
                        data.getTicketContent() + "\n\n" +
                        "---------------------------");
                ret.setErweima(ret.getOrderId());
                ret.setPosSuccessMsg(data.getPosSuccessMsg());
            } else {
                ret.setRtnFlag("1");
                ret.setErrorMes(resJson.getErrorMsg());
            }
            jsonData.setjData(objectToJson(ret));
            jsonData.setTimestamp(fmtDate2Str(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
            String content = objectToJson(jsonData);
            logger.info(content);
            return encrypt(content, tx.getMd5Hex());
        }
    }

    @Override
    public String yanmachexiao(String h,String t){
        TongXin tx = new TongXin(h, t).invoke();
        JsonData jsonData = tx.getJsonData();
        if (jsonData == null) return "-1";
        YanMaXiangYingData ret = new YanMaXiangYingData();
        if(!jsonData.getjData().contains("posno")) {
            String result = callCheckcodeCancelNew(jsonData.getjData());
            HxForJson resJson = JacksonJsonMapper.jsonToObject(result, HxForJson.class);
            ret.setTitle("福码");
            if (resJson != null && "true".equals(resJson.getSuccess())) {
                ret.setRtnFlag("0");
                ret.setErrorMes(resJson.getErrorMsg());
                ret.setOrderId(ret.getOrderId());
                ret.setYmOrderId(ret.getOrderId());
                ret.setErweima(ret.getOrderId());
            } else {
                ret.setRtnFlag("1");
                ret.setErrorMes(resJson.getErrorMsg());
                ret.setOrderId(ret.getOrderId());
                ret.setYmOrderId(ret.getOrderId());
                ret.setErweima(ret.getOrderId());
            }
        }else{
            CodeRevokeBo crb = jsonToObject(jsonData.getjData(),CodeRevokeBo.class);
            if(crb != null){
            	CheckCodeRecord ccr = checkCodeRecordRepository.getCheckCodeRecordByBatchno(crb.requestSerialNumber);
            	EitemRevertBo eitem = codeRevokeOld(ccr.getImei(),ccr.getBatchno(),ccr.getSearchno(),crb.posno);
                String isSuccess = eitem.getIsSuccess();

                ret.setRtnFlag("true".equals(isSuccess) ? "0" : "1");
                ret.setErrorMes(eitem.getResultInfo());
                ret.setDes(eitem.getDescription());
                ret.setPrintInfo(eitem.getXiaoTiao().replace("\\n", "\n"));
                jsonData.setjData(objectToJson(ret));
                jsonData.setTimestamp(fmtDate2Str(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
                String content = objectToJson(jsonData);
                logger.info(content);
                checkCodeRecordRepository.delete(ccr);
                return encrypt(content, tx.getMd5Hex());
            }else{
                ret.setRtnFlag("1");
                ret.setErrorMes("无法撤销该订单");
            }
        }
        jsonData.setjData(objectToJson(ret));
        jsonData.setTimestamp(fmtDate2Str(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
        String content = objectToJson(jsonData);
        logger.info(content);
        return encrypt(content, tx.getMd5Hex());
    }

    private EitemBo barcodeserviceYanma(String imei, String charcode, String eitmid, String amcout, String batchno, String searchno) {
        Args a = new Args();
        a.setMethodName("barcodeVerify6");
        a.setImei(imei);
        a.setCharcode(charcode);
        a.setEitmid("0".equals(eitmid) ? "0" : activityRepository.getOneByActivitId(eitmid).getEitemid());
        a.setAmcout(amcout);
        a.setBatchno(batchno);
        a.setSearchno(searchno);
        String retxml = callBarcodeservice(a, String.class);
        logger.info("xml from barcodeservice http yanma" + retxml);
        List<EitemBo> eitemlist = (List<EitemBo>) XmlUtil.getBO(new EitemBo().getClass(), retxml);
        return eitemlist.get(0);
    }

    private EitemRevertBo codeRevokeOld(String imei,String batchno,String searchno,String posno){
        Args a = new Args();
        a.setMethodName("redVBarcode");
//        a.setImei("test1234");
//        a.setEitmid("test1234");
//        a.setBatchno("test1234189109158");
//        a.setSearchno("90E473A7886C4647A373A00B6928E221");
        a.setImei(imei);
        a.setEitmid(imei);
        a.setBatchno(batchno);
        a.setSearchno(searchno);
        String retxml = callBarcodeservice(a, String.class);
        logger.info("xml from barcodeservice http yanma" + retxml);
        List<EitemRevertBo> eitemlist = (List<EitemRevertBo>) XmlUtil.getBO(new EitemRevertBo().getClass(), retxml);
        return eitemlist.get(0);
    }

    public String fakehuodongliebiao(String h, String t) {
        TongXin tx = new TongXin(h, t).invoke();
        JsonData jsonData = tx.getJsonData();
        if (jsonData == null) return "-1";
        Args args = new Args();
        args.setMethodName("getEitemByBank");
        args.setImei(tx.getDevice().getDevcode());
//        String rutstr = callBarcodeservice(args, String.class);
//        List<BankBo> list = (List<BankBo>) XmlUtil.getBO(
//                new BankBo().getClass(), rutstr);
        List<BankBo> list = new ArrayList<>();
        BankBo e = new BankBo();
        e.setOuterid("123456");
        e.setEitemname("测试活动");
        list.add(e);
        BankBo tempbank = list.get(0);
        Huodongliebiao ret = new Huodongliebiao();
        ret.setState("ok");
        if (!"false".equals(tempbank.getIsSuccess())) {
            List<String> ids = list.stream().map(act -> act.getOuterid()).collect(toList());
            Map<String, Activity> map = activityRepository.getByActivitId(ids).stream().collect(toMap(Activity::getActivitId, a -> a));
            ret.setActivityList(list.stream().map(b -> mapBankBoToActivityBo(b, map)).toArray(ActivityBo[]::new));
        }
        jsonData.setjData(objectToJson(ret));
        jsonData.setTimestamp(fmtDate2Str(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
        String content = objectToJson(jsonData);
        logger.info(content);
        return encrypt(content, tx.getMd5Hex());
    }
    @Override
    public String huodongliebiao(String h, String t) {
        if (fakeDemo) {
            return fakehuodongliebiao(h, t);
        }
        TongXin tx = new TongXin(h, t).invoke();
        JsonData jsonData = tx.getJsonData();
        if (jsonData == null) return "-1";
        String devcode = tx.getDevice().getDevcode();
//        Huodongliebiao ret = new Huodongliebiao();
        String activity_ret = callActivityService(devcode);
//        ActivityBo[] bos1 = getActivityFromMinSheng(devcode);
//        ActivityBo[] bos2 = getActivityFromYanMa(devcode);

//        ret.setActivityList(addAll(bos1, bos2));
//        ret.setState("ok");
        jsonData.setjData(activity_ret);
        jsonData.setTimestamp(fmtDate2Str(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
        String content = objectToJson(jsonData);
        logger.info(content);
        return encrypt(content, tx.getMd5Hex());
    }

    private ActivityBo[] getActivitys(String devcode){
        return null;
    }

    private ActivityBo[] getActivityFromYanMa(String devcode) {
        Args args = new Args();
        args.setMethodName("getEitemListByImei");// cn.m.mt.mocreditservice.service.impl.ManageServiceImpl#getActivityList
        args.setImei(devcode);
        String rutstr = callBarcodeservice(args, String.class);
        List<EitemListBo> eitemlist = (List<EitemListBo>) XmlUtil.getBO(
                new EitemListBo().getClass(), rutstr);
        EitemListBo eb = eitemlist.get(0);
        if ("false".equals(eb.getIsSuccess()) && !"没有兑换的活动".equals(eb.getResultInfo())) return null;
        List<String> ids = eitemlist.stream().map(bo -> bo.getEitemid()).collect(toList());
        Map<String, Activity> map = activityRepository.getByEitemId(ids).stream().collect(toMap(Activity::getEitemid, a -> a));
        return eitemlist.stream().map(bo ->mapEitemListBoToActivityBo(bo, map)).toArray(ActivityBo[]::new);
    }

    private ActivityBo mapEitemListBoToActivityBo(EitemListBo bo, Map<String, Activity> map) {
        ActivityBo ab = new ActivityBo();
        String id = bo.getEitemid();
        ab.setActivit(ZXQY + id);
        ab.setActivitName(bo.getEitemname());
        Activity a = map.get(id);
        if (a != null) {
            ab.setsTime(a.getStartdate());
            ab.seteTime(a.getEnddate());
            ab.setCardBin(a.getCardbin());
            ab.setWeek(a.getWeek());
            ab.setAmt(a.getAmt());
        }
        return ab;
    }

    private ActivityBo[] getActivityFromMinSheng(String devcode) {
        Args args = new Args();
        args.setMethodName("getEitemByBank");
        args.setImei(devcode);
        String rutstr = callBarcodeservice(args, String.class);
        List<BankBo> list = (List<BankBo>) XmlUtil.getBO(new BankBo().getClass(), rutstr);

        if ("false".equals(list.get(0).getIsSuccess())) return null;

        List<String> ids = list.stream().map(act -> act.getOuterid()).collect(toList());
        Map<String, Activity> map = activityRepository.getByActivitId(ids).stream().collect(toMap(Activity::getActivitId, a -> a));
        return list.stream().map(b -> mapBankBoToActivityBo(b, map)).toArray(ActivityBo[]::new);
    }

    private ActivityBo mapBankBoToActivityBo(BankBo bankBo, Map<String, Activity> map) {
        ActivityBo ab = new ActivityBo();
        ab.setActivitId(bankBo.getOuterid());
        ab.setActivitName(bankBo.getEitemname());
        Activity a = map.get(bankBo.getOuterid());
        if (a != null) {
            ab.setsTime(a.getStartdate());
            ab.seteTime(a.getEnddate());
            ab.setCardBin(a.getCardbin());
            ab.setWeek(a.getWeek());
            ab.setAmt(a.getAmt());
        }
        return ab;
    }

    public String fakeXiaofei(String h, String t) {
        TongXin tx = new TongXin(h, t).invoke();
        JsonData jsonData = tx.getJsonData();
        if (jsonData == null) return "-1";
        XiaoFeiQingQiuData req = jsonToObject(jsonData.getjData(), XiaoFeiQingQiuData.class);
        String devcode = tx.getDevice().getDevcode();
        XiaoFeiXiangYingData ret = new XiaoFeiXiangYingData();
        ret.setState("ok");
        String orderId = req.getOrderId();
        ret.setQr(orderId);
        jsonData.setjData(objectToJson(ret));
        jsonData.setTimestamp(fmtDate2Str(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
        String content = objectToJson(jsonData);
        logger.info(content);
        return encrypt(content, tx.getMd5Hex());
    }
    @Override
    public String xiaofei(String h, String t) {
        if (fakeDemo) {
            return fakeXiaofei(h, t);
        }
        TongXin tx = new TongXin(h, t).invoke();
        JsonData jsonData = tx.getJsonData();
        if (jsonData == null) return "-1";
        XiaoFeiQingQiuData req = jsonToObject(jsonData.getjData(), XiaoFeiQingQiuData.class);
        String devcode = tx.getDevice().getDevcode();
        LiuShui liuShui = addLiuShui(devcode, req.getOrderId());
        String activitId = req.getActivitId();
        String cardNo = req.getCardNo();
        String exp_date = req.getExp_date();//TODO 有效期 6位数字截取前4位 YYMM
//        XiaoFeiXiangYingData ret = new XiaoFeiXiangYingData();

        ObjectAgrs ret = new ObjectAgrs();
        ret.cardNum = cardNo;
        ret.cardExpDate = exp_date;
        ret.activityId =  "771657cc0c674d33bbed5cedbd98838a";
        ret.activityId = req.getActivitId();
        ret.enCode = "12312321r";
        ret.enCode = devcode;
        ret.amt = req.getAmt();
        ret.orderId = req.getOrderId();
        String rt = objectToJson(ret);
        String jret = callConsumeService(rt);
/*
        if (activitId.startsWith(ZXQY)) {
            String eitmid = activitId.substring(4);
//            String charcode = "1001888888888801145D991252192600000000@1001888888888801145D991252192600000000";
            String charcode = cardNo + "D" + exp_date;
            EitemBo eitem = barcodeserviceYanma(devcode, charcode, eitmid, "1", liuShui.getBatchno(), liuShui.getSearchno());
            String isSuccess = eitem.getIsSuccess();

            ret.setState("true".equals(isSuccess) ? "ok" : "error");
            ret.setErrorMes(eitem.getError());
//            ret.setOrderId(liuShui.getBatchno());
//            ret.setYmOrderId(liuShui.getSearchno());
//            ret.setDes(eitem.getDescription());
            ret.setQr(liuShui.getWangposorderid());
            ret.setPrintInfo("---------------------------\n" +
                    "验码" + "\n\n" +
                    "交易类型：卷码核销\n" +
                    "------------------------\n" +
                    "商户名称：测试商户\n" +
                    "门店名称：测试门店\n" +
                    "商户编号：111111111111119\n" +
                    "终端编号：55550004\n" +
                    "订单号：" + liuShui.getWangposorderid() + "\n" +
                    "数量：1\n" +
                    "------------------------\n" +
                    "备注\n" +
                    "------------------------\n" +
                    "持卡人签字\n\n\n" +
                    "---------------------------");
            jsonData.setjData(objectToJson(ret));
            jsonData.setTimestamp(fmtDate2Str(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
            String content = objectToJson(jsonData);
            logger.info(content);
            return encrypt(content, tx.getMd5Hex());
        }

        Args a = new Args();
        a.setMethodName("exchangeIntegral");
        a.setImei(devcode);
        a.setAccount(cardNo + "@=" + exp_date);
        a.setAmcout(req.getAmt());
        a.setEitmid(activitId);
        a.setBatchno(liuShui.getBatchno());
        a.setSearchno(liuShui.getSearchno());

        String retxml = callBarcodeservice(a, String.class);
        logger.info("xml from barcodeservice http xiaofei" + retxml);
        List<IntegralBo> integralbolist = (List<IntegralBo>) XmlUtil.getBO(
                new IntegralBo().getClass(), retxml);
        IntegralBo integralbo = integralbolist.get(0);

        if ("false".equals(integralbo.getIsSuccess())) {
            ret.setState("error");
            ret.setErrorCode(integralbo.getErrorCode());
            ret.setErrorMes(integralbo.getErrorMsg());
        } else {
            ret.setState("ok");
        }
        ret.setPrintInfo("---------------------------\n" +
                "验码" + "\n\n" +
                "交易类型：卷码核销\n" +
                "------------------------\n" +
                "商户名称：测试商户\n" +
                "门店名称：测试门店\n" +
                "商户编号：111111111111119\n" +
                "终端编号：55550004\n" +
                "订单号：" + liuShui.getWangposorderid() + "\n" +
                "数量：1\n" +
                "------------------------\n" +
                "备注\n" +
                "------------------------\n" +
                "持卡人签字\n\n\n" +
                "---------------------------");
        ret.setQr(liuShui.getWangposorderid());
*/
        jsonData.setjData(jret);
        jsonData.setTimestamp(fmtDate2Str(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
        String content = objectToJson(jsonData);
        logger.info(content);
        return encrypt(content, tx.getMd5Hex());
    }

    private LiuShui addLiuShui(String devcode, String wangposorderid) {
        LiuShui liuShui = new LiuShui();
        liuShui.setId(uuid());
        liuShui.setDevcode(devcode);
        liuShui.setWangposorderid(wangposorderid);
        liuShui.setYimeiorderid(uuid());
        liuShui.setTimestamp(fmtDate2Str(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
        LiuShui lastLiuShui = liuShuiRepository.getLastLiuShui(devcode);
        if (lastLiuShui == null) {
            liuShui.setBatchno("000000");
            liuShui.setSearchno("000000");
        } else {
            int batchno = parseInt(lastLiuShui.getBatchno());
            int searchno = parseInt(lastLiuShui.getSearchno());
            boolean liuWeiZuiDaZhi = searchno == 999999;//是否已经达到6位数的最大值
            batchno = liuWeiZuiDaZhi ? batchno + 1 : batchno;
            searchno = liuWeiZuiDaZhi ? 0 : (searchno + 1);
            liuShui.setBatchno(qianZhui(batchno + "", 6, '0'));
            liuShui.setSearchno(qianZhui(searchno + "", 6, '0'));
        }
        liuShuiRepository.save(liuShui);
        return liuShui;
    }
    public String fakeChexiao(String h, String t) {
        TongXin tx = new TongXin(h, t).invoke();
        JsonData jsonData = tx.getJsonData();
        if (jsonData == null) return "-1";
        CheXiaoQingQiuData req = jsonToObject(jsonData.getjData(), CheXiaoQingQiuData.class);
        XiaoFeiXiangYingData ret = new XiaoFeiXiangYingData();
        ret.setPrintInfo("成功");
        String wangposorderid = req.getOrgOrderId();
        ret.setQr(wangposorderid);
        jsonData.setjData(objectToJson(ret));
        jsonData.setTimestamp(fmtDate2Str(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
        String content = objectToJson(jsonData);
        logger.info(content);
        return encrypt(content, tx.getMd5Hex());
    }
    @Override
    public String chexiao(String h, String t) {
        if (fakeDemo) {
            return fakeChexiao(h, t);
        }
        TongXin tx = new TongXin(h, t).invoke();
        JsonData jsonData = tx.getJsonData();
        if (jsonData == null) return "-1";
        CheXiaoQingQiuData req = jsonToObject(jsonData.getjData(), CheXiaoQingQiuData.class);
        XiaoFeiXiangYingData ret = new XiaoFeiXiangYingData();

        ObjectAgrs a = new ObjectAgrs();
        a.oldOrderId = req.getOrgOrderId();
        a.orderId = req.getOrderId();
        a.cardNum = req.getCardNo();

//        a.setMethodName("revIntegral");
        String devcode = tx.getDevice().getDevcode();
        a.enCode = devcode;
        a.cardExpDate = req.getExp_date();

        String json = callConsumeCancelService(objectToJson(a));
//        a.setImei(devcode);
//        a.setOrderid(flowno);
//        String cardNo = req.getCardNo();
//        a.setAccount(cardNo);
//        String wangposorderid = req.getOrgOrderId();
//        LiuShui ls = liuShuiRepository.getLiuShuiByWangposorderid(wangposorderid);

//        a.setBatchno(ls.getBatchno());
//        a.setSearchno(ls.getSearchno());
//        a.setPosno(posno);
//        a.setTime(time);
//        String rutstr = callBarcodeservice(a, String.class);
//
//        System.out.println("=====rutstr===" + rutstr);
//        List<IntegralBo> returnlist = (List<IntegralBo>) XmlUtil.getBO(new IntegralBo().getClass(), rutstr);
//        IntegralBo retrunbo = returnlist.get(0);
//        if (!"true".equals(retrunbo.getIsSuccess())) {
//            ret.setState("error");
//            String errorMsg = retrunbo.getErrorMsg();
//            ret.setErrorMes(errorMsg);
//            String errorCode = getErrorCode(errorMsg.replace("!", ""));
//            ret.setErrorCode(errorCode);
//        } else {
//            String orderId = req.getOrderId();
//            LiuShui liuShui = addLiuShui(devcode, orderId);
//            ret.setPrintInfo("成功");
//            ret.setQr(orderId);
//        }
        jsonData.setjData(json);
        jsonData.setTimestamp(fmtDate2Str(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
        String content = objectToJson(jsonData);
        logger.info(content);
        return encrypt(content, tx.getMd5Hex());
    }

    @Override
    public String jiamiceshi(String h, String t) {
        TongXin tx = new TongXin(h, t).invoke();
        JsonData jsonData = tx.getJsonData();
        if (jsonData == null) return "-1";
        JiaMiCeShiData req = jsonToObject(jsonData.getjData(), JiaMiCeShiData.class);
        req.setTime(fmtDate2Str(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
        jsonData.setjData(objectToJson(req));
        jsonData.setTimestamp(fmtDate2Str(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
        String content = objectToJson(jsonData);
        logger.info(content);
        return encrypt(content, tx.getMd5Hex());
    }

    @Override
    public String jiesuan(String h, String t) {
        TongXin tx = new TongXin(h, t).invoke();
        JsonData jsonData = tx.getJsonData();
        if (jsonData == null) return "-1";
        JieSuanXiangYingData ret = new JieSuanXiangYingData();

        JieSuan js = new JieSuan();
        js.setId(uuid());
        js.setDevcode(tx.getDevice().getDevcode());
        js.setStoreid(jsonData.getStorId());
        js.setInfo("结算失败");
        jieSuanRepository.save(js);
        ret.setPrintInfo(js.getInfo());
        ret.setState("error");
        ret.setSettleId(js.getId());

        jsonData.setjData(objectToJson(ret));
        jsonData.setTimestamp(fmtDate2Str(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
        String content = objectToJson(jsonData);
        logger.info(content);
        return encrypt(content, tx.getMd5Hex());
    }

    @Override
    public String xiaofeichongzheng(String h, String t) {
        TongXin tx = new TongXin(h, t).invoke();
        JsonData jsonData = tx.getJsonData();
        if (jsonData == null) return "-1";
        CheXiaoQingQiuData req = jsonToObject(jsonData.getjData(), CheXiaoQingQiuData.class);

        String devcode = tx.getDevice().getDevcode();
        String orderId = req.getOrderId();

        ObjectAgrs a = new ObjectAgrs();
        a.enCode = devcode;
        a.amt = req.getAmt();
        a.cardExpDate = req.getExp_date();
        a.oldOrderId = req.getOrgOrderId();
        a.orderId = req.getOrderId();
        a.cardNum = req.getCardNo();

        String ret = callConsumeCorrectService(objectToJson(a));
//        ChongZheng cz = new ChongZheng();
//        cz.setId(uuid());
//        cz.setDevcode(devcode);
//        cz.setOrderId(orderId);
//        cz.setCztype("xf");
//        cz.setOrgOrderId(req.getOrgOrderId());
//        cz.setState("error");
//        cz.setErrorMsg("错误");
//        chongZhengRepository.save(cz);
//        addLiuShui(devcode, orderId);
//
//        ChongZhengXiangYingData ret = new ChongZhengXiangYingData();
//        ret.setState(cz.getState());
//        ret.setErrorMes(cz.getErrorMsg());
//        ret.setErrorCode("error");
        jsonData.setjData(ret);
        jsonData.setTimestamp(fmtDate2Str(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
        String content = objectToJson(jsonData);
        logger.info(content);
        return encrypt(content, tx.getMd5Hex());
    }

    @Override
    public String chexiaochongzheng(String h, String t) {
        TongXin tx = new TongXin(h, t).invoke();
        JsonData jsonData = tx.getJsonData();
        if (jsonData == null) return "-1";
        CheXiaoQingQiuData req = jsonToObject(jsonData.getjData(), CheXiaoQingQiuData.class);

        String devcode = tx.getDevice().getDevcode();
        String orderId = req.getOrderId();

        ObjectAgrs a = new ObjectAgrs();
        a.enCode = devcode;
        a.cardExpDate = req.getExp_date();
        a.oldOrderId = req.getOrgOrderId();
        a.orderId = req.getOrderId();
        a.cardNum = req.getCardNo();

        String ret = callService(consumeCancelCorrectUrl,objectToJson(a));
//        ChongZheng cz = new ChongZheng();
//        cz.setId(uuid());
//        cz.setDevcode(devcode);
//        cz.setOrderId(orderId);
//        cz.setCztype("cx");
//        cz.setOrgOrderId(req.getOrgOrderId());
//        cz.setState("error");
//        cz.setErrorMsg("错误");
//        chongZhengRepository.save(cz);
//        addLiuShui(devcode, orderId);
//
//        ChongZhengXiangYingData ret = new ChongZhengXiangYingData();
//        ret.setState(cz.getState());
//        ret.setErrorMes(cz.getErrorMsg());
//        ret.setErrorCode("error");
        jsonData.setjData(ret);
        jsonData.setTimestamp(fmtDate2Str(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
        String content = objectToJson(jsonData);
        logger.info(content);
        return encrypt(content, tx.getMd5Hex());
    }

    @Override
    public String resetDevpassword(String json) {
        ObjectAgrs a = jsonToObject(json,ObjectAgrs.class);
        if(a == null || a.enCode == null){
            return "参数错误";
        }
        List<Device> devices = deviceRepository.getDeviceByMD5(md5Hex(a.enCode));
        if (devices.size() != 1) {
            return "查不到该en（" + a.enCode + "）对应的机具";
        }
        for(Device dev:devices){
            dev.setPassword("0000000000000000");
        }
        return "0";
    }

    @Override
    public String syncdevcode(String json) {
        ObjectAgrs a = jsonToObject(json,ObjectAgrs.class);

        if(OPER_ADD.equals(a.oper)){
            if(a == null || a.enCode == null || a.oper == null || a.id == null){
            return "参数错误";
            }
            Device dev = new Device();
            dev.setId(a.id);
            dev.setDevcode(a.enCode);
            dev.setDevcodemd5(md5Hex(a.enCode));
            dev.setPassword("0000000000000000");
            deviceRepository.save(dev);
        }else {

            if (OPER_DEL.equals(a.oper)) {
                if(a == null || a.oper == null || a.id == null){
                    return "参数错误";
                }
                String[] ids = a.id.split(",");
                for(String id:ids){
                    List<Device> devices = deviceRepository.getDeviceById(a.id);
                    if (devices.size() != 1) {
                        return "查不到该en（" + a.enCode + "）对应的机具";
                    }
                    deviceRepository.delete(devices);
                }
            } else if (OPER_MDF.equals(a.oper)) {
                if(a == null || a.enCode == null || a.oper == null || a.id == null){
                    return "参数错误";
                }
                List<Device> devices = deviceRepository.getDeviceById(a.id);
                if(devices == null || devices.isEmpty()){
                    Device dev = new Device();
                    dev.setId(a.id);
                    dev.setDevcode(a.enCode);
                    dev.setDevcodemd5(md5Hex(a.enCode));
                    dev.setPassword("0000000000000000");
                    deviceRepository.save(dev);
                    return "0";
                }
                devices.get(0).setDevcode(a.enCode);
                devices.get(0).setId(a.id);
                devices.get(0).setDevcodemd5(md5Hex(a.enCode));
                devices.get(0).setPassword("0000000000000000");
            } else {
                return "无法识别的操作码";
            }
        }
        return "0";
    }

    
    @Override
	public String shoudantongbu(String h, String t) {
    	TongXin tx = new TongXin(h, t).invoke();
		JsonData jsonData = tx.getJsonData();
		if (jsonData == null)
			return "-1";
		ShoudanBo shoudan = jsonToObject(jsonData.getjData(), ShoudanBo.class);
		ShoudanResponse response = new ShoudanResponse();
		if (shoudan == null) {
			response.success = "false";
			response.errorMsg = "收单信息格式错误";
		} else {
			String ret = callShoudanService(objectToJson(shoudan));
			if ("0".equals(ret)) {
				response.success = "true";
				response.errorMsg = "收单同步成功";
			}else{
				response.success = "false";
				response.errorMsg = "收单同步失败";
			}
		}
		jsonData.setjData(objectToJson(response));
		jsonData.setTimestamp(fmtDate2Str(new Date(), "yyyy-MM-dd HH:mm:ss:SSS"));
		String content = objectToJson(jsonData);
		logger.info(content);
		return encrypt(content, tx.getMd5Hex());
	}

    /**
     * 此方法会向别处发送HTTP请求，用来代替直接调用barcodeservice的方法
     * @param a 参数
     * @param expectType 返回类型
     * @param <T>
     * @return
     */
    private <T> T callBarcodeservice(Args a, Class<T> expectType) {
        try {
            MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
            map.add("arg_json", new ObjectMapper().writeValueAsString(a));
            logger.info("post to old gateway info:"+new ObjectMapper().writeValueAsString(a));
            return new RestTemplate().postForObject(restBarcodeServiceAddress, map, expectType);
        } catch (Exception e) {
            logger.error(getStackTrace(e));
            return null;
        }
    }

    private String callActivityService(String a) {
        try {
            String json = "{\"enCode\":\""+a+"\"}";
            return new RestTemplate().postForObject(getActivitiesServiceUrl, json, String.class);
        } catch (Exception e) {
            logger.error(getStackTrace(e));
            return null;
        }
    }

    private String callConsumeCorrectService(String json){
        try {
            return new RestTemplate().postForObject(consumeCorrectUrl, json, String.class);
        } catch (Exception e) {
            logger.error(getStackTrace(e));
            return null;
        }
    }

    private String callCheckcodeCancelNew(String json){
        try {
            return new RestTemplate().postForObject(checkcodeCancelNewUrl, json, String.class);
        } catch (Exception e) {
            logger.error(getStackTrace(e));
            return null;
        }
    }

    private String callService(String url,String json){
        try {
            return new RestTemplate().postForObject(url, json, String.class);
        } catch (Exception e) {
            logger.error(getStackTrace(e));
            return null;
        }
    }

    private String callConsumeService(String json){
        try {
            return new RestTemplate().postForObject(consumeServiceUrl, json, String.class);
        } catch (Exception e) {
            logger.error(getStackTrace(e));
            return null;
        }
    }
    private String callConsumeCancelService(String json){
        try {
            return new RestTemplate().postForObject(consumeCancelUrl, json, String.class);
        } catch (Exception e) {
            logger.error(getStackTrace(e));
            return null;
        }
    }
    
	private String callShoudanService(String json) {
		try {
			return new RestTemplate().postForObject(shoudantongbuUrl, json, String.class);
		} catch (Exception e) {
			logger.error(getStackTrace(e));
			return null;
		}
	}
    
    private class TongXin {
        private String h;
        private String t;
        private String md5Hex;
        private String decrypt;
        private JsonData jsonData;
        private Device device;

        public TongXin(String h, String t) {
            this.h = h;
            this.t = t;
        }

        public Device getDevice() {
            return device;
        }

        public JsonData getJsonData() {
            return jsonData;
        }

        public String getMd5Hex() {
            return md5Hex;
        }

        public String getDecrypt() {
            return decrypt;
        }

        public TongXin invoke() {
            String s = decryptByPrivateKey(h, rsaPrivateKey);
            String aid = s.substring(0, 32);
            logger.info("请求者aid：" + aid);
            String rkey = s.substring(32, 48);
            logger.info("rkey：" + rkey);
            List<Device> devices = deviceRepository.getDeviceByMD5(aid);
            if (devices.size() != 1) {
                logger.error("机具号的md5值不正确" + aid);
                return this;
            }
            device = devices.get(0);
            String akey = device.getPassword();
            logger.info("akey：" + akey);
            md5Hex = md5Hex(akey + rkey).toUpperCase();
            logger.info("key：" + md5Hex);
            decrypt = decrypt(t, md5Hex);
            logger.info("请求者报文：" + decrypt);
            jsonData = jsonToObject(decrypt, JsonData.class);
            return this;
        }
    }
    
}
