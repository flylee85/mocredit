package cn.mocredit.gateway.message;

public class MessageObject {
	/**
	 * 报文类型
	 */
	private String messtype;
	/**
	 * 交易码
	 */
	private String tranCode;
	/**
	 * 主账号
	 */
	private String field02_Primary_Account_Number;
	/**
	 * 交易处理码
	 */
	private String field03_Processing_Code;
	/**
	 * 交易金额
	 */
	private String field04_Amount_Of_Transactions;
	/**
	 * 手机号
	 */
	private String field05_Mobile_Number;
	private String field06;
	public String getField06() {
		return field06;
	}
	public void setField06(String field06) {
		this.field06 = field06;
	}
	private String field09;
	public void setField09(String field09) {
		this.field09 = field09;
	}
	public String getField09() {
		return field09;
	}

	/**
	 * 系统跟踪号
	 */
	private String field11_System_Trace_Audit_Number;
	/**
	 * 受卡方所在地时间
	 */
	private String field12_Time_Of_Local_Transaction;
	/**
	 * 受卡方所在地日期
	 */
	private String field13_Date_Of_Local_Transaction;
	/**
	 * 卡有效期
	 */
	private String field14_Date_Of_Expired;
	/**
	 * 清算日期
	 */
	private String field15_Date_Of_Settlement;
	/**
	 * 服务点输入方式码
	 */
	private String field22_Point_Of_Service_Entry_Mode;
	/**
	 * 卡序列号
	 */
	private String field23_Card_SN;
	/**
	 * 服务点条件码
	 */
	private String field25_Point_Of_Service_Condition_Mode;
	/**
	 * 服务点PIN获取码
	 */
	private String field26_POS_PIN_Capture_Code;
	/**
	 * 受理方标识码
	 */
	private String field32_Acquiring_Institution;
	/**
	 * 第二磁道数据
	 */
	private String field35_Track2_Data;
	/**
	 * 第三磁道数据
	 */
	private String field36_Track3_Data;
	/**
	 * 检索参考号
	 */
	private String field37_Retrieval_Reference_Number;
	/**
	 * 授权标识应答码
	 */
	private String field38_Authorization_Identification_Response;
	/**
	 * 应答码
	 */
	private String field39_Response_Code;	
	/**
	 * 受卡机终端标识码
	 */
	private String field41_Card_Acceptor_Terminal_ID;
	/**
	 * 受卡方标识码
	 */
	private String field42_Card_Acceptor_ID;
	/**
	 * 附加响应数据
	 */
	private String field44_Additional_Response_Data;
	/**
	 * 附加数据－私有
	 */
	private String field48_Additional_Data;
	/**
	 * 交易货币代码
	 */
	private String field49_Currency_Code_Of_Transaction;
	/**
	 * 个人标识码数据
	 */
	private String field52_PIN_Data;
	/**
	 * 安全控制信息
	 */
	private String field53_Security_Control_Information;
	/**
	 * 附加金额
	 */
	private String field54_Balance_Amount;
	/**
	 * IC卡数据域
	 */
	private String field55_IC_DATA;
	/**
	 * 附件交易信息
	 */
	private String field57_ADDITIONAL_DATA_PRIVATE;
	public String getField57_ADDITIONAL_DATA_PRIVATE() {
		return field57_ADDITIONAL_DATA_PRIVATE;
	}
	public void setField57_ADDITIONAL_DATA_PRIVATE(String field57_ADDITIONAL_DATA_PRIVATE) {
		this.field57_ADDITIONAL_DATA_PRIVATE = field57_ADDITIONAL_DATA_PRIVATE;
	}
	/**
	 * PBOC电子钱包标准的交易信息
	 */
	private String field58_PBOC_ELECTRONIC_DATA;
	/**
	 * 自定义域
	 */
	private String field60_Reserved_Private;
	/**
	 * 原始信息域
	 */
	private String field61_Original_Message;
	/**
	 * 自定义域
	 */
	private String field62_Reserved_Private;
	/**
	 * 自定义域
	 */
	private String field63_Reserved_Private;
	/**
	 * 报文鉴别码
	 */
	private String field64_MAC;
	@Override
	public String toString() {
		return "POSRequest [messtype=" + messtype + ", tranCode=" + tranCode
				+ ", field02_Primary_Account_Number="
				+ field02_Primary_Account_Number + ", field03_Processing_Code="
				+ field03_Processing_Code + ", field04_Amount_Of_Transactions="
				+ field04_Amount_Of_Transactions + ", field05_Mobile_Number="
				+ field05_Mobile_Number
				+ ", field11_System_Trace_Audit_Number="
				+ field11_System_Trace_Audit_Number
				+ ", field12_Time_Of_Local_Transaction="
				+ field12_Time_Of_Local_Transaction
				+ ", field13_Date_Of_Local_Transaction="
				+ field13_Date_Of_Local_Transaction
				+ ", field14_Date_Of_Expired=" + field14_Date_Of_Expired
				+ ", field15_Date_Of_Settlement=" + field15_Date_Of_Settlement
				+ ", field22_Point_Of_Service_Entry_Mode="
				+ field22_Point_Of_Service_Entry_Mode + ", field23_Card_SN="
				+ field23_Card_SN
				+ ", field25_Point_Of_Service_Condition_Mode="
				+ field25_Point_Of_Service_Condition_Mode
				+ ", field26_POS_PIN_Capture_Code="
				+ field26_POS_PIN_Capture_Code
				+ ", field32_Acquiring_Institution="
				+ field32_Acquiring_Institution + ", field35_Track2_Data="
				+ field35_Track2_Data + ", field36_Track3_Data="
				+ field36_Track3_Data + ", field37_Retrieval_Reference_Number="
				+ field37_Retrieval_Reference_Number
				+ ", field38_Authorization_Identification_Response="
				+ field38_Authorization_Identification_Response
				+ ", field39_Response_Code=" + field39_Response_Code
				+ ", field41_Card_Acceptor_Terminal_ID="
				+ field41_Card_Acceptor_Terminal_ID
				+ ", field42_Card_Acceptor_ID=" + field42_Card_Acceptor_ID
				+ ", field44_Additional_Response_Data="
				+ field44_Additional_Response_Data
				+ ", field48_Additional_Data=" + field48_Additional_Data
				+ ", field49_Currency_Code_Of_Transaction="
				+ field49_Currency_Code_Of_Transaction + ", field52_PIN_Data="
				+ field52_PIN_Data + ", field53_Security_Control_Information="
				+ field53_Security_Control_Information
				+ ", field54_Balance_Amount=" + field54_Balance_Amount
				+ ", field55_IC_DATA=" + field55_IC_DATA
				+ ", field58_PBOC_ELECTRONIC_DATA="
				+ field58_PBOC_ELECTRONIC_DATA + ", field60_Reserved_Private="
				+ field60_Reserved_Private + ", field61_Original_Message="
				+ field61_Original_Message + ", field62_Reserved_Private="
				+ field62_Reserved_Private + ", field63_Reserved_Private="
				+ field63_Reserved_Private + ", field64_MAC=" + field64_MAC
				+ "]";
	}
	//setters & getters
	public String getMesstype() {
		return messtype;
	}
	public void setMesstype(String messtype) {
		this.messtype = messtype;
	}
	public String getTranCode() {
		return tranCode;
	}
	public void setTranCode(String tranCode) {
		this.tranCode = tranCode;
	}
	public String getField02_Primary_Account_Number() {
		return field02_Primary_Account_Number;
	}
	public void setField02_Primary_Account_Number(
			String field02_Primary_Account_Number) {
		this.field02_Primary_Account_Number = field02_Primary_Account_Number;
	}
	public String getField03_Processing_Code() {
		return field03_Processing_Code;
	}
	public void setField03_Processing_Code(String field03_Processing_Code) {
		this.field03_Processing_Code = field03_Processing_Code;
	}
	public String getField04_Amount_Of_Transactions() {
		return field04_Amount_Of_Transactions;
	}
	public void setField04_Amount_Of_Transactions(
			String field04_Amount_Of_Transactions) {
		this.field04_Amount_Of_Transactions = field04_Amount_Of_Transactions;
	}
	public String getField05_Mobile_Number() {
		return field05_Mobile_Number;
	}
	public void setField05_Mobile_Number(String field05_Mobile_Number) {
		this.field05_Mobile_Number = field05_Mobile_Number;
	}
	public String getField11_System_Trace_Audit_Number() {
		return field11_System_Trace_Audit_Number;
	}
	public void setField11_System_Trace_Audit_Number(
			String field11_System_Trace_Audit_Number) {
		this.field11_System_Trace_Audit_Number = field11_System_Trace_Audit_Number;
	}
	public String getField12_Time_Of_Local_Transaction() {
		return field12_Time_Of_Local_Transaction;
	}
	public void setField12_Time_Of_Local_Transaction(
			String field12_Time_Of_Local_Transaction) {
		this.field12_Time_Of_Local_Transaction = field12_Time_Of_Local_Transaction;
	}
	public String getField13_Date_Of_Local_Transaction() {
		return field13_Date_Of_Local_Transaction;
	}
	public void setField13_Date_Of_Local_Transaction(
			String field13_Date_Of_Local_Transaction) {
		this.field13_Date_Of_Local_Transaction = field13_Date_Of_Local_Transaction;
	}
	public String getField14_Date_Of_Expired() {
		return field14_Date_Of_Expired;
	}
	public void setField14_Date_Of_Expired(String field14_Date_Of_Expired) {
		this.field14_Date_Of_Expired = field14_Date_Of_Expired;
	}
	public String getField15_Date_Of_Settlement() {
		return field15_Date_Of_Settlement;
	}
	public void setField15_Date_Of_Settlement(String field15_Date_Of_Settlement) {
		this.field15_Date_Of_Settlement = field15_Date_Of_Settlement;
	}
	public String getField22_Point_Of_Service_Entry_Mode() {
		return field22_Point_Of_Service_Entry_Mode;
	}
	public void setField22_Point_Of_Service_Entry_Mode(
			String field22_Point_Of_Service_Entry_Mode) {
		this.field22_Point_Of_Service_Entry_Mode = field22_Point_Of_Service_Entry_Mode;
	}
	public String getField23_Card_SN() {
		return field23_Card_SN;
	}
	public void setField23_Card_SN(String field23_Card_SN) {
		this.field23_Card_SN = field23_Card_SN;
	}
	public String getField25_Point_Of_Service_Condition_Mode() {
		return field25_Point_Of_Service_Condition_Mode;
	}
	public void setField25_Point_Of_Service_Condition_Mode(
			String field25_Point_Of_Service_Condition_Mode) {
		this.field25_Point_Of_Service_Condition_Mode = field25_Point_Of_Service_Condition_Mode;
	}
	public String getField26_POS_PIN_Capture_Code() {
		return field26_POS_PIN_Capture_Code;
	}
	public void setField26_POS_PIN_Capture_Code(String field26_POS_PIN_Capture_Code) {
		this.field26_POS_PIN_Capture_Code = field26_POS_PIN_Capture_Code;
	}
	public String getField32_Acquiring_Institution() {
		return field32_Acquiring_Institution;
	}
	public void setField32_Acquiring_Institution(
			String field32_Acquiring_Institution) {
		this.field32_Acquiring_Institution = field32_Acquiring_Institution;
	}
	public String getField35_Track2_Data() {
		return field35_Track2_Data;
	}
	public void setField35_Track2_Data(String field35_Track2_Data) {
		this.field35_Track2_Data = field35_Track2_Data;
	}
	public String getField36_Track3_Data() {
		return field36_Track3_Data;
	}
	public void setField36_Track3_Data(String field36_Track3_Data) {
		this.field36_Track3_Data = field36_Track3_Data;
	}
	public String getField37_Retrieval_Reference_Number() {
		return field37_Retrieval_Reference_Number;
	}
	public void setField37_Retrieval_Reference_Number(
			String field37_Retrieval_Reference_Number) {
		this.field37_Retrieval_Reference_Number = field37_Retrieval_Reference_Number;
	}
	public String getField38_Authorization_Identification_Response() {
		return field38_Authorization_Identification_Response;
	}
	public void setField38_Authorization_Identification_Response(
			String field38_Authorization_Identification_Response) {
		this.field38_Authorization_Identification_Response = field38_Authorization_Identification_Response;
	}
	public String getField39_Response_Code() {
		return field39_Response_Code;
	}
	public void setField39_Response_Code(String field39_Response_Code) {
		this.field39_Response_Code = field39_Response_Code;
	}
	public String getField41_Card_Acceptor_Terminal_ID() {
		return field41_Card_Acceptor_Terminal_ID;
	}
	public void setField41_Card_Acceptor_Terminal_ID(
			String field41_Card_Acceptor_Terminal_ID) {
		this.field41_Card_Acceptor_Terminal_ID = field41_Card_Acceptor_Terminal_ID;
	}
	public String getField42_Card_Acceptor_ID() {
		return field42_Card_Acceptor_ID;
	}
	public void setField42_Card_Acceptor_ID(String field42_Card_Acceptor_ID) {
		this.field42_Card_Acceptor_ID = field42_Card_Acceptor_ID;
	}
	public String getField44_Additional_Response_Data() {
		return field44_Additional_Response_Data;
	}
	public void setField44_Additional_Response_Data(
			String field44_Additional_Response_Data) {
		this.field44_Additional_Response_Data = field44_Additional_Response_Data;
	}
	public String getField48_Additional_Data() {
		return field48_Additional_Data;
	}
	public void setField48_Additional_Data(String field48_Additional_Data) {
		this.field48_Additional_Data = field48_Additional_Data;
	}
	public String getField49_Currency_Code_Of_Transaction() {
		return field49_Currency_Code_Of_Transaction;
	}
	public void setField49_Currency_Code_Of_Transaction(
			String field49_Currency_Code_Of_Transaction) {
		this.field49_Currency_Code_Of_Transaction = field49_Currency_Code_Of_Transaction;
	}
	public String getField52_PIN_Data() {
		return field52_PIN_Data;
	}
	public void setField52_PIN_Data(String field52_PIN_Data) {
		this.field52_PIN_Data = field52_PIN_Data;
	}
	public String getField53_Security_Control_Information() {
		return field53_Security_Control_Information;
	}
	public void setField53_Security_Control_Information(
			String field53_Security_Control_Information) {
		this.field53_Security_Control_Information = field53_Security_Control_Information;
	}
	public String getField54_Balance_Amount() {
		return field54_Balance_Amount;
	}
	public void setField54_Balance_Amount(String field54_Balance_Amount) {
		this.field54_Balance_Amount = field54_Balance_Amount;
	}
	public String getField55_IC_DATA() {
		return field55_IC_DATA;
	}
	public void setField55_IC_DATA(String field55_IC_DATA) {
		this.field55_IC_DATA = field55_IC_DATA;
	}
	public String getField58_PBOC_ELECTRONIC_DATA() {
		return field58_PBOC_ELECTRONIC_DATA;
	}
	public void setField58_PBOC_ELECTRONIC_DATA(String field58_PBOC_ELECTRONIC_DATA) {
		this.field58_PBOC_ELECTRONIC_DATA = field58_PBOC_ELECTRONIC_DATA;
	}
	public String getField60_Reserved_Private() {
		return field60_Reserved_Private;
	}
	public void setField60_Reserved_Private(String field60_Reserved_Private) {
		this.field60_Reserved_Private = field60_Reserved_Private;
	}
	public String getField61_Original_Message() {
		return field61_Original_Message;
	}
	public void setField61_Original_Message(String field61_Original_Message) {
		this.field61_Original_Message = field61_Original_Message;
	}
	public String getField62_Reserved_Private() {
		return field62_Reserved_Private;
	}
	public void setField62_Reserved_Private(String field62_Reserved_Private) {
		this.field62_Reserved_Private = field62_Reserved_Private;
	}
	public String getField63_Reserved_Private() {
		return field63_Reserved_Private;
	}
	public void setField63_Reserved_Private(String field63_Reserved_Private) {
		this.field63_Reserved_Private = field63_Reserved_Private;
	}
	public String getField64_MAC() {
		return field64_MAC;
	}
	public void setField64_MAC(String field64_MAC) {
		this.field64_MAC = field64_MAC;
	}
}