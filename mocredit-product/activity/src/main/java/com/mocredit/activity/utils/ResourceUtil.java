package com.mocredit.activity.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ResourceUtil {
	
	public static String NOTINSENDMAIL=null;
	// 默认每页数据数
//	public static int DEFPAGESIZE = 0;
	// 通过ip获取城市数据目录
//	public static String IPCITYDATAPATH = null;
	// 手机号码正在表达式
	public static String MOBILEREG = null;
	//每个IP每天最多更换手机号码数
	public static int MAXMOBILEHOUR = 0;
	//每个手机号一天内获取验证码次数
	public static int MAXGETCAPTCHADAY = 0;
	// 图片资源服务器REST接口地址
	public static String RESSERVERURL = null;
	//图片合成服务器hessian接口地址
//	public static String IMGMAGICSERVURL = null;
	//图片合成服务器显示合成图片servlet地址
//	public static String IMGMAGICSERVLET = null;
	// 索引服务器hesian接口地址
	public static String INDEXMANAGERURL = null;
	// 搜索索引文件目录
	public static String INDEXPATH = null;
	// 二维码webservice接口地址
	public static String CODEWEBSERVICURL = null;
	// 二维码接口中用到的代理商name
	public static String AGENTNAME = null;
	// 二维码接口中用到的代理商密码
	public static String AGENTPWD = null;
	// 二维码接口中所有商家默认密码
	public static String FAMILYPWD = null;
	//谷歌地图根据地址查询经纬度
	public static String LATITUDE = null;
	//谷歌地图
	public static String FINDMAPURL = null;
	//10690999短信发送序列号
	public static String SN999 = null;
	//10690999短信发送密码
	public static String PWD999 = null;
	//营销类短信发送序列号
	public static String SMSSN = null;
	//营销类短信短信发送密码
	public static String SMSPWD = null;
	//易惠通接口地址
//	public static String CODEWEBSERVICURL=null;
	//下载优惠单价
	public static int DOWLOAD_PRICE=2;
	//商户开通赠送下载量
	public static int ZHENGSONG_DOWLOAD=10;
	//商户开通赠送短信量
	public static int ZHENGSONG_SMS=20;
	//联惠店手机号前缀
//	public static long UNIONSHOPMOBILE;
	//web首页数据路径
//	public static String WEBCOUPONDATEURL=null;
	//web首页优惠总数
//	public static int WEBCOUPONTOTAL=100;
	//web首页优惠每页个数
//	public static int WEBCOUPONPAGESIZE=20;
	//远程存储数据接口
	public static String DOWNLOADCOUPONREMOTE;
	//飘窗地址
	public static String FLOATINGURL;
	//加密key
	public static String MCNCRYKEY;	
	//会员码长度
	public static int HUIYUAN_CODE_LENGTH;
	//联会店码长度
	public static int LIANHUIDIAN_CODE_LENGTH;
	//一次性码长度
	public static int YICIXING_CODE_LENGTH;
	
	//直营接收邮件的邮箱
	public static String EMAIL_ZHIYING;
	
	public static String WAPBARCODE = null;
	
	//================================================
	public static String MMSSAVEPATH = null;
	public static String MMSNAME = null;
	public static String MMSPASSWORD = null;
	public static int MMSTYPE;
	public static String MMSTITLE = null;
	//================================================
	public static String APPKEY=null;
	public static String APPSECRET=null;
	public static String MMSSIGNATURE=null;
	public static int FACTORAGE;//全部退款时收取的手续费
	//淘宝新增商品
	public static String ITEMADD=null;
	//淘宝商品上架
	public static String ITEMUPSHELF=null;
	//淘宝商品下架
	public static String ITEMDOWNSHELF=null;
	//删除淘宝商品
	public static String ITEMDELETE=null;
	//淘宝小二删除商品
	public static String ITEMPUNISHDELETE=null;
	//淘宝买家付款购买
	public static String TRADEBUYERPAY=null;
	//淘宝交易成功
	public static String TRADESUCCESS= null;
	//淘宝退款成功
	public static String REFUNDSUCCESS=null;
	//智惠城市淘宝卖家昵称
	public static String SELLERNICK=null;
	
	//加密密钥
	public static String SHAREKEY =null;
	
	//远程验证二维码接口
	public static String MCNCODEREMOTEUTL=null;
	
	//远程废除二维码
	public static String MCNCODEREPEAL=null;
	//是否是测试服务器
	public static boolean ISTEST=false;
	public  static String YICHONGBAODEALERID=null;
	public  static String YICHONGBAODEALERKEY=null;
	public static String OFCAED_USER= null;
	public static String OFCAED_PASSWORD= null;
	public static Map<String,String> MMSTOKENMAP=null;
	static {
		Properties properties = new Properties();
		try {
			properties.load(ResourceUtil.class.getResourceAsStream("/system.properties"));
		} catch (Exception e) {
			System.out.println("读取配置文件失败");
		}
//		WAPBARCODE =  properties.getProperty("sys.wapbarcodeaction");
//		LATITUDE = properties.getProperty("sys.googlelatitude");
//		IPCITYDATAPATH = properties.getProperty("sys.ipcitydatapath");
//		FINDMAPURL = properties.getProperty("sys.googlefindmapurl");//谷歌地图
//		DOWLOAD_PRICE=Integer.parseInt(properties.getProperty("sys.dowload_price"));
//		ZHENGSONG_DOWLOAD=Integer.parseInt(properties.getProperty("sys.zhengsong_dowload","10"));
//		ZHENGSONG_SMS = Integer.parseInt(properties.getProperty("sys.zhengsong_sms","20"));
		
//		UNIONSHOPMOBILE = Long.valueOf(properties.getProperty("sys.unionshopmobile"));
		SN999=properties.getProperty("sys.sms.sdksn999");
		PWD999=properties.getProperty("sys.sms.sdkpwd999");
		SMSSN=properties.getProperty("sys.sms.sdksn");
		SMSPWD=properties.getProperty("sys.sms.sdkpwd");
//		DEFPAGESIZE = Integer.parseInt(properties
//				.getProperty("sys.defpagesize"));
//		MOBILEREG = properties.getProperty("sys.mobilereg");
//		MAXMOBILEHOUR = Integer.parseInt(properties
//				.getProperty("sys.maxmobilehour"));
//		MAXGETCAPTCHADAY = Integer.parseInt(properties
//				.getProperty("sys.maxgetcaptchaday"));
//		RESSERVERURL = properties.getProperty("intf.resserv.resturl");
//		IMGMAGICSERVURL = properties.getProperty("intf.imgmagic.hessianurl");
//		IMGMAGICSERVLET = properties.getProperty("intf.imgmagic.servlet");
//		INDEXMANAGERURL = properties.getProperty("intf.search.hessianurl");
//		INDEXPATH = properties.getProperty("intf.search.indexpath");
//		CODEWEBSERVICURL = properties.getProperty("intf.code.webservicurl");
//		AGENTNAME = properties.getProperty("intf.code.agentidname");
//		AGENTPWD = properties.getProperty("intf.code.agentpwd");
//		FAMILYPWD = properties.getProperty("intf.code.familypwd");
//		WEBCOUPONDATEURL = properties.getProperty("sys.webcoupondateurl");
//		WEBCOUPONTOTAL = Integer.parseInt(properties.getProperty("sys.webindextotal"));
//		WEBCOUPONPAGESIZE = Integer.parseInt(properties.getProperty("sys.webindexpagesize"));
//		DOWNLOADCOUPONREMOTE = properties.getProperty("intf.downloadcoupon.hessianurl");
//		FLOATINGURL = properties.getProperty("intf.floatingurl");
		MCNCRYKEY = properties.getProperty("sys.mcncry.key");
		HUIYUAN_CODE_LENGTH=Integer.parseInt(properties.getProperty("huiyuan.code.length"));
		LIANHUIDIAN_CODE_LENGTH=Integer.parseInt(properties.getProperty("lianhuidian.code.length"));
		YICIXING_CODE_LENGTH=Integer.parseInt(properties.getProperty("yicixing.code.length"));
		EMAIL_ZHIYING = properties.getProperty("sys.zhiying.email");
		
		MMSSAVEPATH=properties.getProperty("mms.save.temp.path");
		MMSNAME=properties.getProperty("mms.username");
		MMSPASSWORD=properties.getProperty("mms.password");
		MMSTYPE=Integer.parseInt(properties.getProperty("mms.type"));
		MMSTITLE=properties.getProperty("mms.title");
		MMSSIGNATURE = properties.getProperty("mms.signature");
		APPKEY=properties.getProperty("TaoBao.appkey");
		APPSECRET=properties.getProperty("TaoBao.appSecret");
		ITEMADD=properties.getProperty("TaoBao.ItemAdd");
		ITEMDOWNSHELF=properties.getProperty("TaoBao.ItemDownshelf");
		ITEMUPSHELF=properties.getProperty("TaoBao.ItemUpshelf");
		TRADEBUYERPAY=properties.getProperty("TaoBao.TradeBuyerPay");
		TRADESUCCESS = properties.getProperty("TaoBao.TradeSuccess");
		REFUNDSUCCESS=properties.getProperty("TaoBao.RefundSuccess");
		ITEMDELETE=properties.getProperty("TaoBao.ItemDelete");
		ITEMPUNISHDELETE = properties.getProperty("TaoBao.ItemPunishDelete");
		FACTORAGE=Integer.parseInt(properties.getProperty("TaoBao.Factorage","2"));
		SELLERNICK = properties.getProperty("TaoBao.sellernick");
		SHAREKEY = properties.getProperty("sys.sharekey");
		MCNCODEREMOTEUTL = properties.getProperty("intf.mcncode.hessianurl");
		MCNCODEREPEAL = properties.getProperty("intf.repealcode.hessianurl");
		NOTINSENDMAIL = properties.getProperty("sys.sms.notinsendmail");
		
		String istest = properties.getProperty("sys.istest");
		String tokens = properties.getProperty("mms.token");
		String channlenos = properties.getProperty("mms.channleno");
		String[] tokenarry = tokens.split(",");
		String[] channlenoarry = channlenos.split(",");
		MMSTOKENMAP = new HashMap<String, String>(); 
		for(int i=0;i<channlenoarry.length;i++){
			MMSTOKENMAP.put(channlenoarry[i],tokenarry[i]);
		}
		if(istest!=null && "true".equals(istest)){
			ISTEST = true; 
		}
		YICHONGBAODEALERID=properties.getProperty("rechange.dealerid");
		YICHONGBAODEALERKEY=properties.getProperty("rechange.dealerkey");
		OFCAED_USER  = properties.getProperty("rechange.ofcaeduser");
		OFCAED_PASSWORD = properties.getProperty("rechange.ofcaedpassword");
	}

}
