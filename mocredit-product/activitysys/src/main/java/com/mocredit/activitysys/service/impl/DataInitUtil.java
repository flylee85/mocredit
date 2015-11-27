package com.mocredit.activitysys.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.mocredit.activity.model.Activity;
import com.mocredit.base.exception.BusinessException;
import com.mocredit.base.util.DateUtil;
import com.mocredit.base.util.HttpUtil;
import com.mocredit.base.util.PropertiesUtil;
import com.mocredit.manage.model.Store;
import com.mocredit.manage.model.Terminal;
import com.mocredit.manage.persitence.StoreMapper;
import com.mocredit.manage.persitence.TerminalMapper;

/**
 * 新平台数据初始化工具类，实现数据从老平台到新平台的迁移，完成新平台数据初始化。
 * 
 * @author liaoy
 * @date 2015年11月25日
 */
@Service("dataInit")
public class DataInitUtil {
	private static final String IN_URL = "jdbc:mysql://localhost:3306/mcntong_init?useUnicode=true&characterEncoding=utf-8";
	private static final String IN_UNAME = "root";
	private static final String IN_PWD = "root";
	private static final String OUT_URL = "jdbc:mysql://localhost:3306/activity_init?useUnicode=true&characterEncoding=utf-8";
	private static final String OUT_UNAME = "root";
	private static final String OUT_PWD = "root";
	private Connection conIn;
	private Connection conOut;
	@Autowired
	private StoreMapper storeMapper;
	@Autowired
	private TerminalMapper terminalMapper;

	public static void main(String[] args) {
		String configLocation = "classpath*:conf/spring/*.xml"; 
		ApplicationContext ctx = new ClassPathXmlApplicationContext(configLocation);
		DataInitUtil util = (DataInitUtil) ctx.getBean("dataInit");
		// 加载MySql的驱动类
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("==========创建读取库链接=========");
			util.conIn = DriverManager.getConnection(IN_URL, IN_UNAME, IN_PWD);
			System.out.println("==========创建写入库链接=========");
			util.conOut = DriverManager.getConnection(OUT_URL, OUT_UNAME, OUT_PWD);
			System.out.println("==========开启事务=========");
			util.conOut.setAutoCommit(false);
			System.out.println("==========开始导入=========");
			// 导入企业
			System.out.println("==========start 导入企业=========");
			util.importEnteprise();
			System.out.println("==========end 导入企业=========");
			// 导入商户
			System.out.println("==========start 导入商户=========");
			util.importShop();
			System.out.println("==========end 导入商户=========");
			// 导入门店
			System.out.println("==========start 导入门店=========");
			util.importStore();
			System.out.println("==========end 导入门店=========");
			// 导入机具
			System.out.println("==========start 导入设备=========");
			util.importDevice();
			System.out.println("==========end 导入设备=========");
			// 导入活动
			System.out.println("==========start 导入活动=========");
			util.importActivity();
			System.out.println("==========end 导入活动=========");
			// 导入活动店铺关系
			System.out.println("==========start 导入活动门店关系=========");
			util.importaActivityStore();
			System.out.println("==========end 导入活动门店关系=========");
			// 导入民生活动
			System.out.println("==========start 导入民生活动=========");
			util.importMinShengActivity();
			System.out.println("==========end 导入民生活动=========");
			System.out.println("==========提交事务=========");
			util.conOut.commit();

			util.conIn.close();
			util.conOut.close();
		} catch (Exception e) {
			System.out.println("==========导入时发生异常=========");
			try {
				System.out.println("==========回滚操作=========");
				util.conOut.rollback();
			} catch (SQLException e1) {
				System.out.println("==========回滚时发生异常=========");
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		System.out.println("==========结束导入=========");
	}

	private void importEnteprise() throws SQLException {
		ResultSet rs = query("select * from enterprise", true);
		StringBuilder sb = new StringBuilder("INSERT INTO `t_enterprise` VALUES");
		while (rs.next()) {
			sb.append("(");
			sb.append(getColumn(rs.getString("id")));
			sb.append(getColumn(rs.getString("entname")));
			sb.append(getColumn(rs.getString("entcode")));
			sb.append(getColumn(rs.getString("linkman")));
			sb.append(getColumn(rs.getString("linkphone")));
			sb.append(getColumn(rs.getString("linkman2")));
			sb.append(getColumn(rs.getString("linkphone2")));
			sb.append(getColumn(rs.getString("descr")));
			sb.append(getColumn(rs.getString("status")));
			sb.append(StringUtils.isEmpty(rs.getString("createtime")) ? "now(),"
					: "STR_TO_DATE('" + rs.getString("createtime") + "','%Y-%m-%d %H:%i:%s'),");
			sb.append(getColumn(StringUtils.isEmpty(rs.getString("smsbalance")) ? "0" : rs.getString("smsbalance")));
			sb.append(getColumn(StringUtils.isEmpty(rs.getString("smstotal")) ? "0" : rs.getString("smstotal")));
			sb.append(getColumn(StringUtils.isEmpty(rs.getString("mmsbalance")) ? "0" : rs.getString("mmsbalance")));
			sb.append(getColumn(StringUtils.isEmpty(rs.getString("mmstotal")) ? "0" : rs.getString("mmstotal")));
			sb.append(getColumn(StringUtils.isEmpty(rs.getString("smswarning")) ? "0" : rs.getString("smswarning")));
			sb.append(getColumn(StringUtils.isEmpty(rs.getString("mmswarning")) ? "0" : rs.getString("mmswarning")));
			sb.append(getColumn(rs.getString("mmschannle")));
			sb.append(getColumn("0"));// audit
			sb.append(getColumn(""));// linkaddress
			sb.deleteCharAt(sb.length() - 1);
			sb.append("),");
		}
		rs.close();
		sb.deleteCharAt(sb.length() - 1);
		System.out.println(sb.toString());
		execute(sb.toString(), false);
	}

	private void importShop() throws SQLException {
		// 读取活动表
		ResultSet rs = query("select * from shop", true);
		StringBuilder sb = new StringBuilder("INSERT INTO `t_merchant` VALUES");
		while (rs.next()) {
			sb.append("(");
			sb.append(getColumn(rs.getString("id")));
			sb.append(getColumn(rs.getString("name")));
			sb.append(getColumn(""));// code
			sb.append(getColumn(rs.getString("wapurl")));
			sb.append(getColumn(rs.getString("weburl")));
			sb.append(getColumn(0));// province
			sb.append(getColumn(0));// city
			sb.append(getColumn(0));// area
			sb.append(getColumn(rs.getString("address")));
			sb.append(getColumn(rs.getString("contact")));
			sb.append(getColumn(rs.getString("phone")));
			sb.append(getColumn(rs.getString("descr")));
			sb.append(getColumn(rs.getString("shopphone")));
			sb.append(getColumn(rs.getString("fax")));
			sb.append(getColumn(0));// jobtype
			sb.append(getColumn(0));// sub_job_type
			sb.append(getColumn(1));// status
			sb.append(StringUtils.isEmpty(rs.getString("createtime")) ? "now(),"
					: "STR_TO_DATE('" + rs.getString("createtime") + "','%Y-%m-%d %H:%i:%s'),");
			sb.append(getColumn(StringUtils.isEmpty(rs.getString("smsbalance")) ? "0" : rs.getString("smsbalance")));
			sb.append(getColumn(StringUtils.isEmpty(rs.getString("smstotal")) ? "0" : rs.getString("smstotal")));
			sb.append(getColumn(StringUtils.isEmpty(rs.getString("mmsbalance")) ? "0" : rs.getString("mmsbalance")));
			sb.append(getColumn(StringUtils.isEmpty(rs.getString("mmstotal")) ? "0" : rs.getString("mmstotal")));
			sb.append(getColumn(0));// smswarning
			sb.append(getColumn(0));// mmswarning
			sb.append(getColumn(0));// mmschannle
			sb.append(getColumn(rs.getString("email")));
			sb.deleteCharAt(sb.length() - 1);
			sb.append("),");
		}
		rs.close();
		sb.deleteCharAt(sb.length() - 1);
		System.out.println(sb.toString());
		execute(sb.toString(), false);
	}

	private void importStore() throws SQLException {
		// 读取活动表
		ResultSet rs = query("select * from store", true);
		StringBuilder sb = new StringBuilder("INSERT INTO `t_store` VALUES");
		while (rs.next()) {
			sb.append("(");
			sb.append(getColumn(rs.getString("id")));
			sb.append(getColumn(rs.getString("name")));
			sb.append(getColumn(""));// code
			sb.append(getColumn(rs.getString("shopid")));
			sb.append(getColumn(0));// province
			sb.append(getColumn(0));// city
			sb.append(getColumn(0));// area
			sb.append(getColumn(rs.getString("address")));
			sb.append(getColumn(StringUtils.isEmpty(rs.getString("longitude")) ? 0 : rs.getString("longitude")));
			sb.append(getColumn(StringUtils.isEmpty(rs.getString("latitude")) ? 0 : rs.getString("latitude")));
			sb.append(getColumn(0 == rs.getInt("status") ? 1 : 2));// status
			sb.append(getColumn(rs.getString("mobile")));
			sb.append(getColumn(rs.getString("phone")));
			sb.append(StringUtils.isEmpty(rs.getString("createtime")) ? "now(),"
					: "STR_TO_DATE('" + rs.getString("createtime") + "','%Y-%m-%d %H:%i:%s'),");
			sb.append(getColumn(""));// linkman
			sb.deleteCharAt(sb.length() - 1);
			sb.append("),");
		}
		rs.close();
		sb.deleteCharAt(sb.length() - 1);
		System.out.println(sb.toString());
		execute(sb.toString(), false);
	}

	private void importaActivityStore() throws SQLException {
		// 读取活动表
		ResultSet rs = query(
				"SELECT distinct es.eitemid, es.storeid, s.shopid FROM eitemstore es LEFT JOIN store s ON es.storeid = s.id",
				true);
		StringBuilder sb = new StringBuilder("INSERT INTO `act_activity_store`(activity_id,store_id,shop_id) VALUES");
		while (rs.next()) {
			sb.append("(");
			sb.append(getColumn(rs.getString("eitemid")));
			sb.append(getColumn(rs.getString("storeid")));
			sb.append(getColumn(rs.getString("shopid")));
			sb.deleteCharAt(sb.length() - 1);
			sb.append("),");
		}
		rs.close();
		sb.deleteCharAt(sb.length() - 1);
		System.out.println(sb.toString());
		execute(sb.toString(), false);
	}

	private void importDevice() throws SQLException {
		// 读取活动表
		ResultSet rs = query("select * from device where status=0", true);
		StringBuilder sb = new StringBuilder(
				"INSERT INTO `t_terminal`(id,store_id,create_time,status,deskey,mackey,sn_code,supplier_id,type,gateway) VALUES");
		while (rs.next()) {
			sb.append("(");
			sb.append(getColumn(rs.getString("id")));
			sb.append(getColumn(rs.getString("storeid")));
			sb.append(StringUtils.isEmpty(rs.getString("createtime")) ? "now(),"
					: "STR_TO_DATE('" + rs.getString("createtime") + "','%Y-%m-%d %H:%i:%s'),");
			sb.append(getColumn(rs.getString("status")));
			sb.append(getColumn(rs.getString("deskey")));
			sb.append(getColumn(rs.getString("mackey")));
			sb.append(getColumn(rs.getString("devcode")));
			sb.append(getColumn(0));// supplier_id
			sb.append(getColumn("001"));
			sb.append(getColumn("02"));
			sb.deleteCharAt(sb.length() - 1);
			sb.append("),");
		}
		rs.close();
		sb.deleteCharAt(sb.length() - 1);
		System.out.println(sb.toString());
		execute(sb.toString(), false);
	}

	/**
	 * 非民生银行活动
	 * 
	 * @throws SQLException
	 */
	private void importActivity() throws SQLException {
		// 读取活动表
		ResultSet rs = query(
				"select id,`name`,productcode,outerid,entid,(select e.entname from enterprise e where id =i.entid) as enterprise_name,expointType,price,num,endtime,printTitle,printstr,weeklimit,smscontent,checkinfo,expenseSms,status from eitem i where status=1",
				true);
		StringBuilder sb = new StringBuilder(
				"INSERT INTO act_activity(id,enterprise_id,enterprise_name,name,type,CODE,out_code,receipt_title,receipt_print,pos_success_msg,success_sms_msg,start_time,end_time,select_date,amount,integral,createtime,exchange_type,status,max_type,max_number,contract_id ) values ");
		while (rs.next()) {
			sb.append("(");
			sb.append(getColumn(rs.getString("id")));
			sb.append(getColumn(rs.getString("entid")));
			sb.append(getColumn(rs.getString("enterprise_name")));
			sb.append(getColumn(rs.getString("name")));
			sb.append(getColumn("0".equals(rs.getString("expointType")) ? "02" : "01"));
			sb.append(getColumn(rs.getString("productcode")));
			sb.append(getColumn(rs.getString("outerid")));
			sb.append(getColumn(rs.getString("printTitle")));
			sb.append(getColumn(rs.getString("printstr")));
			sb.append(getColumn(rs.getString("checkinfo")));
			sb.append(getColumn(rs.getString("smscontent")));
			sb.append("now(),");
			sb.append("STR_TO_DATE('"
					+ (StringUtils.isEmpty(rs.getString("endtime")) ? "2199-12-31" : rs.getString("endtime"))
					+ "','%Y-%m-%d'),");// 结束时间
			sb.append(getColumn(rs.getString("weeklimit")));
			sb.append(getColumn(StringUtils.isEmpty(rs.getString("price")) ? "0" : rs.getString("price")));// 金额
			sb.append(getColumn("0"));// 积分
			sb.append("now(),");
			// 兑换类型转换
			String exchangeType = "0";
			if ("1".equals(rs.getString("expointType"))) {
				exchangeType = "1";
			} else if ("3".equals(rs.getString("expointType"))) {
				exchangeType = "2";
			}
			sb.append(getColumn(exchangeType));// exchange_type
			sb.append(getColumn("01"));// status
			sb.append(getColumn(""));// max_type
			sb.append(getColumn("1"));// max_number
			sb.append(getColumn("0"));// contract_id
			sb.deleteCharAt(sb.length() - 1);
			sb.append("),");
		}
		rs.close();
		sb.deleteCharAt(sb.length() - 1);
		System.out.println(sb.toString());
		execute(sb.toString(), false);
	}

	/**
	 * 民生银行活动
	 * 
	 * @throws SQLException
	 */
	private void importMinShengActivity() throws SQLException {
		// 读取活动表
		ResultSet rs = query("SELECT * FROM sd_points_info WHERE BANK_ID = 3 AND `STATUS` = 0", true);
		ResultSet msrs = query("SELECT id FROM enterprise where entname='民生银行'", true);
		msrs.next();
		String minshengId = msrs.getString("id");
		while (rs.next()) {
			StringBuilder sb = new StringBuilder(
					"INSERT INTO act_activity(enterprise_id,enterprise_name,name,type,CODE,out_code,start_time,end_time,select_date,bins,max_type,max_number,amount,integral,createtime,exchange_type,status,contract_id,channel ) values ");
			sb.append("(");
			sb.append(getColumn(minshengId));// enterpriseId
			sb.append(getColumn("民生银行"));// enterpriseName
			sb.append(getColumn(rs.getString("PRODUCT_NAME")));
			sb.append(getColumn("01"));// type
			sb.append(getColumn(rs.getString("ACTIVTY_ID")));
			sb.append(getColumn(rs.getString("PRODUCT_ID")));

			String rule = rs.getString("RULES");
			Map<String, String> ruleMap = handleRules(rule);
			// start_time
			if (null == ruleMap || StringUtils.isEmpty(ruleMap.get("StartDate"))) {
				sb.append("now(),");
			} else {
				sb.append(getColumn(ruleMap.get("StartDate")));
			}
			// end_time
			if (null == ruleMap || StringUtils.isEmpty(ruleMap.get("ExpiryDate"))) {
				sb.append("STR_TO_DATE('2199-12-31','%Y-%m-%d'),");
			} else {
				sb.append("STR_TO_DATE('" + ruleMap.get("ExpiryDate") + "','%Y-%m-%d'),");
			}
			// select_date
			if (null == ruleMap || StringUtils.isEmpty(ruleMap.get("OnlyTheseWeekDays"))) {
				sb.append(getColumn("1,2,3,4,5,6,7"));
			} else {
				sb.append(getColumn(ruleMap.get("OnlyTheseWeekDays")));
			}
			// bins
			if (null == ruleMap || StringUtils.isEmpty(ruleMap.get("InCardBin"))) {
				sb.append(getColumn(""));
			} else {
				if ("yes".equals(ruleMap.get("InCardBin"))) {
					ResultSet cardBins = query("SELECT * FROM sd_card_type WHERE BANK_ID = 3 AND VALIAD_FLAG = 0",
							true);
					StringBuilder binsSb = new StringBuilder();
					while (cardBins.next()) {
						binsSb.append(cardBins.getString("CARD_BIN")).append(",");
					}
					binsSb.deleteCharAt(binsSb.length() - 1);
					sb.append(getColumn(binsSb.toString()));
				} else {
					sb.append(getColumn(""));
				}
			}

			sb.append(getColumn(""));// max_type
			sb.append(getColumn("1"));// max_number
			sb.append(getColumn(0));// amount
			sb.append(getColumn(StringUtils.isEmpty(rs.getString("POINTS")) ? 0 : rs.getString("POINTS")));
			sb.append("now(),");

			// 兑换类型转换
			String exchangeType = "0";
			if ("1".equals(rs.getString("ACTIVTY_TYPE"))) {
				exchangeType = "1";
			} else {
				exchangeType = "2";
			}
			sb.append(getColumn(exchangeType));// exchange_type
			sb.append(getColumn("01"));// status
			sb.append(getColumn("0"));// contract_id
			sb.append(getColumn("2"));// channel
			sb.deleteCharAt(sb.length() - 1);
			sb.append(")");
			System.out.println(sb.toString());
			int activityId = execute(sb.toString(), false);

			// 导入活动门店关系
			String shopSql = "SELECT shopid FROM sd_pos_term WHERE MERCH_ID = '" + rs.getString("MERCH_ID")
					+ "' AND TERM_ID = '" + rs.getString("TREM_ID") + "'";
			ResultSet shop = query(shopSql, true);
			if (shop.next()) {
				String shopId = shop.getString(1);
				String sql = "SELECT id FROM store WHERE shopid = '" + shopId + "'";
				ResultSet storeIds = query(sql, true);
				while (storeIds.next()) {
					execute("INSERT INTO `act_activity_store`(activity_id,store_id,shop_id) VALUES(" + activityId + ",'"
							+ storeIds.getString(1) + "','" + shopId + "')", false);
				}
			}
		}
		rs.close();
	}

	private int execute(String sql, boolean isIn) {
		try {
			Statement stmt = isIn ? conIn.createStatement() : conOut.createStatement();
			int count = stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			if (count > 0) {
				ResultSet generatedKeys = stmt.getGeneratedKeys();
				if (null == generatedKeys) {
					return count;
				} else {
					if (generatedKeys.next()) {
						return generatedKeys.getInt(1);
					} else {
						return count;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	private ResultSet query(String sql, boolean isIn) {
		Statement stmt;
		try {
			stmt = isIn ? conIn.createStatement() : conOut.createStatement();
			return stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private String getColumn(Object column) {
		return null == column ? "''," : "'" + column.toString() + "',";
	}

	private Map<String, String> handleRules(String rules) {
		if (StringUtils.isEmpty(rules)) {
			return null;
		}
		Map<String, String> map = new HashMap<String, String>();
		String[] ruleArray = rules.split(";");
		for (String rule : ruleArray) {
			String[] ruleKV = rule.split(":");
			map.put(ruleKV[0], ruleKV[1]);
		}
		return map;
	}

	private void sysnActivity(Activity activity) {

		// 获取验码系统中-修改活动，启动活动，停止活动的ＵＲＬ，并定义一个请求这些地址时，所需要的参数Map,将活动Id和活动名称都放在这个Map中
		String changeActivityUrl = PropertiesUtil.getValue("integral.activityImport");
		Map<String, Object> httpPostMap = new HashMap<String, Object>();
		httpPostMap.put("activityId", activity.getId());// 活动Id
		httpPostMap.put("activityName", activity.getName());// 活动Id
		// 定义一个修改内容描述
		StringBuffer changeDescribe = new StringBuffer();
		// 活动启用或停止
		if ("02".equals(activity.getStatus())) {
			// 验码模块-活动停止
			httpPostMap.put("status", "02");
			changeDescribe.append("活动状态：停止；");
		} else if ("01".equals(activity.getStatus())) {
			// 验码模块-活动启用
			httpPostMap.put("status", "01");
			changeDescribe.append("活动状态：启用；");
		}

		// 开始时间，结束时间
		httpPostMap.put("startTime", DateUtil.dateToStr(activity.getStartTime(), "yyyy-MM-dd HH:mm:ss"));// 开始时间
		httpPostMap.put("endTime", DateUtil.dateToStr(activity.getEndTime(), "yyyy-MM-dd HH:mm:ss"));// 结束时间
		changeDescribe.append("开始时间：" + httpPostMap.get("startTime") + ";");
		changeDescribe.append("结束时间：" + httpPostMap.get("endTime") + ";");

		// 活动指定日期修改，该处临时这样判断
		httpPostMap.put("selectDate", activity.getSelectDate());// 指定日期
		changeDescribe.append("指定日期：" + activity.getSelectDate() + ";");

		// 活动积分修改
		httpPostMap.put("integral", activity.getIntegral());
		changeDescribe.append("积分：" + activity.getIntegral() + ";");

		// 活动最大类型修改
		httpPostMap.put("maxType", activity.getMaxType().toString());
		changeDescribe.append("最大类型：" + activity.getMaxType() + ";");

		// 活动使用次数
		httpPostMap.put("maxNumber", activity.getMaxNumber().toString());
		changeDescribe.append("最大次数：" + activity.getMaxNumber() + ";");

		// 活动使用次数
		httpPostMap.put("productCode", activity.getOutCode().toString());
		changeDescribe.append("外部编码：" + activity.getOutCode() + ";");

		// BIN码
		httpPostMap.put("bins", activity.getBins());
		changeDescribe.append("BIN码列表：" + activity.getBins() + ";");

		// 积分通道
		httpPostMap.put("channel", activity.getChannel());
		changeDescribe.append("积分通道：" + activity.getChannel() + ";");

		// 兑换类型
		httpPostMap.put("exchangeType", activity.getExchangeType());
		changeDescribe.append("兑换类型：" + activity.getExchangeType() + ";");

		// 将活动的门店关联信息添加到修改描述中和调用接口的请求参数中
		List<Store> selectAllofActivity = storeMapper.selectAllofActivity(activity.getId());

		changeDescribe.append("门店信息：[");
		for (Store as : selectAllofActivity) {
			changeDescribe.append("{门店名称：" + as.getName() + ";");
			changeDescribe.append("{门店编码：" + as.getCode() + ";");
			changeDescribe.append("门店id：" + as.getId() + ";}");
			// 机具
			Terminal terminal = new Terminal();
			terminal.setStoreId(as.getId());
			List<Terminal> terminals = terminalMapper.selectAllEncode(terminal);
			as.setTerminals(terminals);
		}
		changeDescribe.append("]");

		httpPostMap.put("storeList", selectAllofActivity);

		// 将修改信息发送至验码系统
		httpPostMap.put("operCode", "1");
		// System.out.println(JSON.toJSONString(httpPostMap));
		String returnJson = HttpUtil.doRestfulByHttpConnection(changeActivityUrl, JSON.toJSONString(httpPostMap));
		Map<String, Object> returnMap = JSON.parseObject(returnJson, Map.class);
		boolean isSuccess = Boolean.parseBoolean(String.valueOf(returnMap.get("success")));
		if (!isSuccess) {
			throw new BusinessException("向积分核销系统同步信息失败");
		}
	}
}
