package com.mocredit.integral.entity;

import java.util.Date;

/**
 * 积分订单
 * @author ytq
 *
 */
public class Order {
/**
   uuid                 int not null auto_increment comment 'ID',
   request_id           int comment '请求记录ID',
   card_num             varchar(20) comment '帐户',
   bank                 varchar(10) comment '银行',
   shop_id              varchar(20) comment '商户ID',
   shop_name            varchar(200) comment '商户名',
   store_id             varchar(20) comment '门店ID',
   store_name           varchar(200) comment '门店名称',
   activity_id          int comment '活动ID',
   activity_name        varchar(200) comment '活动名称',
   device               varchar(50) comment '终端ID',
   amount               decimal(10,0) comment '金额',
   order_id             varchar(30) comment '订单号',
   status               int(1) comment '1交易完成 2交易撤销'    
   ctime                datetime comment '创建时间',
   primary key (uuid)
 */
	private Integer uuid;
	private Integer requestId;
	private Integer cardNum;
	private String bank;
	private String shopId;
	private String shopName;
	private String storeId;
	private String storeName;
	private Integer activityId;
	private String activityName;
	private String device;
	private Integer amount;
	private String orderId;
	private Integer status;
	private Date ctime;
	public Integer getUuid() {
		return uuid;
	}
	public void setUuid(Integer uuid) {
		this.uuid = uuid;
	}
	public Integer getRequestId() {
		return requestId;
	}
	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}
	public Integer getCardNum() {
		return cardNum;
	}
	public void setCardNum(Integer cardNum) {
		this.cardNum = cardNum;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
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
	public Integer getActivityId() {
		return activityId;
	}
	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
	public String getActivityName() {
		return activityName;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	@Override
	public String toString() {
		return "Order ["
				+ (uuid != null ? "uuid=" + uuid + ", " : "")
				+ (requestId != null ? "requestId=" + requestId + ", " : "")
				+ (cardNum != null ? "cardNum=" + cardNum + ", " : "")
				+ (bank != null ? "bank=" + bank + ", " : "")
				+ (shopId != null ? "shopId=" + shopId + ", " : "")
				+ (shopName != null ? "shopName=" + shopName + ", " : "")
				+ (storeId != null ? "storeId=" + storeId + ", " : "")
				+ (storeName != null ? "storeName=" + storeName + ", " : "")
				+ (activityId != null ? "activityId=" + activityId + ", " : "")
				+ (activityName != null ? "activityName=" + activityName + ", "
						: "")
				+ (device != null ? "device=" + device + ", " : "")
				+ (amount != null ? "amount=" + amount + ", " : "")
				+ (orderId != null ? "orderId=" + orderId + ", " : "")
				+ (status != null ? "status=" + status + ", " : "")
				+ (ctime != null ? "ctime=" + ctime : "") + "]";
	}
		
}
