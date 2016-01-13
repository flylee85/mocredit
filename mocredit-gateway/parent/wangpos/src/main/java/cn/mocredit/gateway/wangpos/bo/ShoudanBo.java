package cn.mocredit.gateway.wangpos.bo;

public class ShoudanBo {
	
	public String orderId;
	
	public ShoudanInfo orderInfo;

	public static class ShoudanInfo {
		public String cardBank; // 发卡行
		public String machOrderId; // 交易订单号
		public String referId; // 参考号
		public String transactionTime; // 交易时间
		public String amount; // 交易金额
		public String signatureString; // 签字
		public String cardValidateData; // 卡有效期
		public String authorizationNo; // 授权号
		public String status; // 交易状态
		public String cardId; // 银行卡号
		public String pinpadId; // 终端编号
		public String merchantID; // 商户编号
		public String goods_detail; // 是备注
		public String goods_name; // 房间号
		public String goods_type; // 消费类型
		public String owner_name; // 经营者
		public String income_bank_id; // 收单行
		public String trans_seq; // 交易流水号
		public String merchant_number; // 商户编号
	}

}
