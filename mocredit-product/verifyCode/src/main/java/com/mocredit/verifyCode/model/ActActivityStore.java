package com.mocredit.verifyCode.model;

/**
 *
 * 活动绑定门店
 * Created by YHL on 15/7/16 10:32.
 *
 * @version 1.0
 * @Auth YangHL
 * @Email yanghongli@mocredit.cn
 */
public class ActActivityStore {

    /** 活动ID **/
    public String activityId ;

    /** 门店ID **/
    public String storeId;

    /** 活动所属企业ID **/
    public String enterpriseId;

    /**门店所属商户ID 该字段为冗余字段**/
    public String shopId;

    /**
     * 门店编码
     */
    public String storeCode;

    //-----------GETTER/SETTER------------\\

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getStoreCode() {
        return storeCode;
    }

    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }
}
