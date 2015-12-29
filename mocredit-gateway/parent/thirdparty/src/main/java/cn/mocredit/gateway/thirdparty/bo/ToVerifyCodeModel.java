package cn.mocredit.gateway.thirdparty.bo;

public class ToVerifyCodeModel {
    private String amount;
    private String useCount;
    private String device;
    private String storeId;
    private String storeName;
    private String requestSerialNumber;
    private String shopId;
    private String shopName;
    private String code;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUseCount() {
        return useCount;
    }

    public void setUseCount(String useCount) {
        this.useCount = useCount;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getRequestSerialNumber() {
        return requestSerialNumber;
    }

    public void setRequestSerialNumber(String requestSerialNumber) {
        this.requestSerialNumber = requestSerialNumber;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
