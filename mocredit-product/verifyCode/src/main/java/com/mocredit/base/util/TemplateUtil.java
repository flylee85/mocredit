package com.mocredit.base.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * 主要负责 短信、小票内容的模板的替换
 * Created by YHL on 15/7/31 16:17.
 *
 * @version 1.0
 * @Auth YangHL
 * @Email yanghongli@mocredit.cn
 */
public class TemplateUtil {

    /** 前缀 **/
    private static final String PREFIX="{";

    /** 后缀 **/
    private static final String SUFFIX="}";


    /** 定义POS验证成功提示**/
    private static final String POS_SUCCESS_MSG="";

    /**
     * 定义成功后发送短信的内容
     */
    private static final String SMS_SUCCESS_MSG="";


    /**
     * 根据模板，填充变量的值。
     * @param valuesMap
     * @param templateString
     * @return
     */
    public static  String buildStringFromTemplate(Map<TemplateField ,String> valuesMap,String templateString){
        String retString=templateString;
        valuesMap=checkDate(valuesMap);
        for(TemplateField key :TemplateField.values()){
            retString = retString.replaceAll( "\\"+PREFIX+ key.name +"\\"+SUFFIX , null!=valuesMap.get(key) ?valuesMap.get(key):"" );
        }
        return retString;
    }


    /**
     * 检查Map是否存在默认时间日期的key。如果不存在，则添加进去
     * @param valuesMap
     * @return
     */
    private static Map<TemplateField ,String> checkDate(Map<TemplateField ,String> valuesMap){
//        Calendar cal = Calendar.getInstance();
//        String day = cal.get( Calendar.YEAR )+"-"+ (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.DATE);
//        String time= cal.get( Calendar.Ho )+":"+ cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
        Date date = new Date();
        String day = (new SimpleDateFormat("yyyy-MM-dd")).format(date);
        String time = (new SimpleDateFormat("HH:mm:ss")).format(date);
        if( null == valuesMap.get(TemplateField.SYSDATE)) {
            valuesMap.put(TemplateField.SYSDATE,day);
        }
        if( null == valuesMap.get(TemplateField.SYSTIME)) {
            valuesMap.put(TemplateField.SYSTIME,time);
        }

        return valuesMap;
    }


    /**
     * 定义要替换的模板中的字段。
     */
    public enum TemplateField{
        ACTIVITY_NAME("activityName","活动名称"),
        ACTIVITY_CODE("activityCode","活动编码"),
        ACTIVITY_OUT_CODE("outCode","外部编码"),
        ENTERPRISE_CODE("enterpriseCode","发行商编号"),
        ENTERPRISE_NAME("enterpriseName","发行商名称"),
        STORE_CODE("storeCode","门店编号"),
        STORE_NAME("storeName","门店名称"),
        SHOP_NAME("shopName","商户名称"),
        SHOP_CODE("shopCode","商户编码"),
        DEVICE_CODE("deviceCode","机具编号"),
        MAX_NUM("maxNum","最大使用次数"),
        ORDER_CODE("orderCode","发码批次"),
        START_TIME("startTime","活动开始时间"),
        END_TIME("endTime","活动结束时间"),
        SELECT_DATE("selectDate","活动指定日期"),
        CODE_SERIAL_NUMBER("codeSerialNumber","兑换码流水号（唯一标识）"),
        CODE("code","兑换码"),
        AMOUNT("amount","价格"),
        CUSTOM_MOBILE("customMobile","联系人手机"),
        CUSTOM_NAME("customName","联系人名称"),
        REMARK("remark","备注"),
        SYSDATE("sysdate","年月日（yyyy-mm-dd）"),
        SYSTIME("systime","时分秒(hh:MM:ss)"),
        NEW_LINE("newLine","换行")
        ;



        /**
         * 字段名字
         */
        private String name ;

        /**
         * 描述
         */
        private String description;

        TemplateField(String _name , String _desc){
            this.name=_name;
            this.description=_desc;
        }


        public TemplateField getByName(String _name){
            for ( TemplateField f :TemplateField.values() ){
                if( f.name.equals(_name)){
                    return f;
                }
            }

            return null;
        }
    }


}
