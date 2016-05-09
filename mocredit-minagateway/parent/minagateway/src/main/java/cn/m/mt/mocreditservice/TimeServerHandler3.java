package cn.m.mt.mocreditservice;

import static cn.m.mt.mocreditservice.MinaTcpClient.send;
import static cn.m.mt.mocreditservice.util.BcdUtils.bcd2Str;
import static cn.m.mt.mocreditservice.util.BcdUtils.str2Bcd;
import static java.lang.Integer.toHexString;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.IsoType;
import com.solab.iso8583.IsoValue;
import com.solab.iso8583.MessageFactory;
import com.solab.iso8583.impl.SimpleTraceGenerator;
import com.solab.iso8583.parse.ConfigParser;

import cn.m.mt.barcodeservice.service.impl.IntegralServiceImpl;
import cn.m.mt.mocreditservice.service.ManageService;
import cn.m.mt.mocreditservice.util.IntegerUtil;
import cn.m.mt.mocreditservice.util.MacEcbUtils;
import cn.m.mt.mocreditservice.util.StringUtils;
import cn.m.mt.util.ASCIIUtil;
import cn.m.mt.util.DateTimeUtils;

public class TimeServerHandler3 extends IoHandlerAdapter {
	@Autowired
	ManageService manageService;
//	@Autowired
//	BarcodeService barcodeservice;
//	private byte[] key = { 0x32, 0x32, 0x32, 0x32, 0x32, 0x32, 0x32, 0x32 };
    @Value("${oldMinaIP}")
    String oldMinaIP;
    @Value("${oldMinaPort}")
    int oldMinaPort;
	@Value("${newGatewayIP}")
	String newGatewayIP;
	@Value("${newGatewayPort}")
	int newGatewayPort;
    @Value("${timeoutMillisOfWaitOldMina}")
    int timeoutMillisOfWaitOldMina;
	@Value("${needToNewGatewayEitemids}")
	String needToNewGatewayEitemids;

	static int TEST = 0;
	@Override
	public void sessionCreated(IoSession session) {
		// 显示客户端的ip和端口
		// System.out.println(session.getRemoteAddress().toString());
	}

	public static String decode(String bytes) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(
				bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		String hexString = "0123456789ABCDEF";
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
					.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray());
	}

	private String[] delQianZhiJiHead(String s) {
		int from = 4, to = 14, minLength = 26;
		String ret[] = { s, null };
		if (s != null && s.length() >= minLength && to == s.indexOf("6031")) {
			String tail = s.substring(from, to) + s.substring(minLength);
			ret[0] = "00" + Integer.toHexString(str2Bcd(tail).length) + tail;
			ret[1] = s.substring(to, minLength);
		}
		return ret;
	}

	private String addQianZhiJiHead(String s, String head) {
		int from = 4, to = 14;
		if (head == null || s == null || s.length() < to) {
			return s;
		}
		String tail = s.substring(from, to) + head + s.substring(to);
		return headBuLing(toHexString(str2Bcd(tail).length), 4) + tail;
	}

	private String headBuLing(String s, int totleLength) {
		while (s.length() < totleLength) {
			s = "0" + s;
		}
		return s;
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		try {
			String str = message.toString();
			String strToSendToOtherIP = str;
			System.out.println("str===" + str);
            String[] delQianZhiJiHead = delQianZhiJiHead(str);
			str = delQianZhiJiHead[0];
			System.out.println("delQianZhiJiHead===" + str);
			str = str.substring(4, str.length());
			String tpdu = str.substring(0, 10);
//		String head = str.substring(10, 22);
			String reqstr = str.substring(10, str.length() - 16);
			str = tpdu + "00" + str.substring(10, str.length());
			System.out.println("-------1----"+str);
			MessageFactory mfact = ConfigParser
					.createFromClasspathConfig("config.xml");
			mfact.setAssignDate(true);
			mfact.setTraceNumberGenerator(new SimpleTraceGenerator((int) (System
					.currentTimeMillis() % 100000)));
			IsoMessage m = mfact.parseMessage(str.getBytes(), 12);
			String rtpdu = StringUtils.moveFirst2Last(str);// 返回报文tpdu
			IsoMessage response = mfact.createResponse(m);
			IsoValue<String> objmac = response.getField(64);
			for(int i =0;i<129;i++){
				IsoValue<Object> objmac1 = response.getField(i);
				if(objmac1!=null){
						System.out.println(i+"==reuqest=="+objmac1.getValue().toString());
				}
			}
//			if (true) {
//				boolean equals = Integer.toHexString(m.getType()).equals("1280");
//				String reqtype = response.getField(60).getValue().toString().substring(4, 6);
//				if (equals && "02".equals(reqtype)) {//二维码验证
//					int length = 0;
//					Object charcodeobj = response.getField(6);
//					if (charcodeobj != null && charcodeobj instanceof IsoValue) {
//						String value = ((IsoValue<String>) charcodeobj).getValue();
//						length = value == null ? 0 : ASCIIUtil.decode(value.toString()).length();
//					}
//					int maxLength = 12;
//					if (length > maxLength) {
//						System.out.println("码长度超过"+maxLength+"转发新网关");
//						sendToNewGateway(session, strToSendToOtherIP);
//						return;
//					}else{
//						sendToOldMina(session, strToSendToOtherIP);
//						return;
//					}
//				}
////				if (equals && "03".equals(reqtype)) {// 积分验证
//				if (false) {// 积分验证
//					String theEitemId = "";
//					IsoValue<Object> field = response.getField(9);
//					if (field != null) {
//						Object value = field.getValue();
//						theEitemId = value == null ? "" : ASCIIUtil.decode(value.toString()).replace(" ", "");
//					}
//					if (isNeedToSendToNewGateway(theEitemId)) {
//						System.out.println("刷卡活动号" + theEitemId + "在配置的列表之中，转发新网关");
//						sendToNewGateway(session, strToSendToOtherIP);
//						return;
//					}else{
//						sendToOldMina(session, strToSendToOtherIP);
//						return;
//					}
//				}
////				System.out.println("除验老码、刷中信民生卡外的所有请求一律转发新网关");
////				sendToNewGateway(session, strToSendToOtherIP);
//                System.out.println("除验新码，一律转发老系统" + strToSendToOtherIP);
//                sendToOldMina(session, strToSendToOtherIP);
//				return;
//			}
			String mackey = "3131313131313131";
			if (objmac != null) {
				String reqmac = objmac.getValue().toString();
//			System.out.println("===reqmac==="+reqmac);
//			String imei = response.getField(41).getValue().toString();
//			imei = ASCIIUtil.decode(imei);
//			String rutstr = barcodeservice.getBatchnoByImei(imei);
//			List<LoginBo> loginlist= (List<LoginBo>) XmlUtil.getBO(
//					new LoginBo().getClass(), rutstr);
//			LoginBo eb = loginlist.get(0);
//			mackey = eb.getMackey();
				System.out.println("===mackey==="+mackey);
//			System.out.println("===reqstr==="+reqstr);
				try {
					reqstr = MacEcbUtils
							.clacMac(ASCIIUtil.hexStringToByte(mackey), ASCIIUtil.hexStringToByte(reqstr));
				} catch (Exception e) {
					response.setValue(39, ASCIIUtil.String2HexString("57"),
							IsoType.NUMERIC, 4);
					String returnstr = new String(response.writeData());
					returnstr = rtpdu  + returnstr;
					returnstr = "00"
							+ IntegerUtil.intToString16(returnstr.length() / 2)
							+ returnstr;
					System.out.println("=========in exp return ==========="+returnstr);
					returnstr = addQianZhiJiHead(returnstr, delQianZhiJiHead[1]);
					System.out.println("====addQianZhiJiHead====" + returnstr);
					session.write(str2Bcd(returnstr));
					return;
				}
//			System.out.println("===reqstr11==="+reqstr);
				if (!reqstr.equals(reqmac)) {
					response.setValue(39, ASCIIUtil.String2HexString("57"),
							IsoType.NUMERIC, 4);
					String returnstr = new String(response.writeData());
					returnstr = rtpdu  + returnstr;
					returnstr = "00"
							+ IntegerUtil.intToString16(returnstr.length() / 2)
							+ returnstr;
					System.out.println("====returnstr====" + returnstr);
					returnstr = addQianZhiJiHead(returnstr, delQianZhiJiHead[1]);
					System.out.println("====addQianZhiJiHead====" + returnstr);
					session.write(str2Bcd(returnstr));
					return;
				}
			}else{
				response.setValue(64, null, IsoType.ALPHA, 0);
			}
			String type = Integer.toHexString(m.getType());
			System.out.println("====type==="+type);
			if (type.equals("1100")) {
				response = manageService.signIn(response);// 签到
			} else if (type.equals("1120")) {
				response = manageService.signOut(response);// 签退
			} else if (type.equals("1260")) {
				String eitemtype = response.getField(60).getValue().toString();
				System.out.println("===eitemtype====="+eitemtype);
				String reqtype = eitemtype.substring(4, 6);
				if ("01".equals(reqtype)){
//					response = manageService.getActivityList(response);// 获得验码活动列表
					sendToOldMina(session, strToSendToOtherIP);//转发老系统，新系统没有此接口
					return;
				}
				if ("02".equals(reqtype))
					response = manageService.getBankList(response);// 获得积分银行
				if ("03".equals(reqtype))
					response = manageService.getBankEitemList(response);// 获得积分银行活动
			}else if (type.equals("1280")) {
				String eitemtype = response.getField(60).getValue().toString();
				String reqtype = eitemtype.substring(4, 6);
				System.out.println("===reqtype===="+reqtype);
				if ("02".equals(reqtype)){
					int length = 0;
					//新增老码转发老网关代码
					Object charcodeobj = response.getField(6);
					if (charcodeobj != null && charcodeobj instanceof IsoValue) {
						String value = ((IsoValue<String>) charcodeobj).getValue();
						length = value == null ? 0 : ASCIIUtil.decode(value.toString()).length();
					}
					int maxLength = 12;
					if (length > maxLength) {
						System.out.println("码长度超过"+maxLength+"，调用新验码系统");
						response = manageService.verifyBarcode(response);//二维码（新码）验证
					}else{
						System.out.println("码长度小于等于"+maxLength+"，调用老验码系统");//老码，转发老网关
						sendToOldMina(session, strToSendToOtherIP);
						return;
					}
				}else if ("03".equals(reqtype)){
					Set<String> offlineActivityIdSet = IntegralServiceImpl.LI_XIAN_HUO_DONG;
					String eitemid = response.getField(9).getValue().toString();
					eitemid = ASCIIUtil.decode(eitemid);
					eitemid = eitemid.replace(" ", "");
					if (offlineActivityIdSet.contains(eitemid)) {
						sendToOldMina(session, strToSendToOtherIP);//离线活动，转发老网关
						return;
					} else {
						response = manageService.verifyIntegration(response);// 积分验证
					}
				}else if ("04".equals(reqtype)){
//					response = manageService.codepayment(response);// 储值卡消费
				}
				response.setValue(6,null, IsoType.ALPHA, 0);
				response.setValue(22, null, IsoType.ALPHA, 0);
				response.setValue(35, null, IsoType.ALPHA, 0);
				response.setValue(52, null, IsoType.ALPHA, 0);
			}else if (type.equals("1200")) {
//				response = manageService.billing(response);//二维码收款-请款
			}else if(type.equals("1300")){
//				response = manageService.showCardBalance(response);//查询 储值卡余额
				response.setValue(6, null, IsoType.ALPHA, 0);  
				response.setValue(22, null, IsoType.ALPHA, 0);
				response.setValue(26, null, IsoType.ALPHA, 0);
				response.setValue(52, null, IsoType.ALPHA, 0);
			}else if(type.equals("1240")){
//				response = manageService.showIntegral(response);//查询积分
				response.setValue(22,null, IsoType.ALPHA, 0);
				response.setValue(35,null, IsoType.ALPHA, 0);
			}else if(type.equals("1220")){//二维码收银-查询订单状态
//				response = manageService.showOrder(response);
			}else if(type.equals("1420")){
//				response = manageService.quryTodayOrder(response);//二维码收银-查询未打印订单
			}else if(type.equals("1320")){//冲正
				response = manageService.Revocation(response, 1);
				response.setValue(35, null, IsoType.ALPHA, 0);
			}else if(type.equals("1400")){//撤销
				String revocationType = response.getField(60).getValue().toString();
				revocationType = revocationType.substring(4,6);
				if(revocationType.equals("02")){
					System.out.println("to new gateway(Revocation)");
					response = manageService.Revocation(response, 2);//验码撤销，先调新网关
					System.out.println("response.getField(63):" + response.getField(63));
					if (response.getField(63) != null 
							&& response.getField(63).getValue().toString().equals("0")) {
						System.out.println("to old gateway(Revocation)");
						response.setValue(63, null, IsoType.ALPHA, 0);
						sendToOldMina(session, strToSendToOtherIP);//新网关验码撤销失败，转发老网关
						return;
					}
					response.setValue(35, null, IsoType.ALPHA, 0);
				} else {
					response = manageService.Revocation(response, 2);
					response.setValue(35, null, IsoType.ALPHA, 0);
				}
			}else if(type.equals("1340")){//退款
				response = manageService.Revocation(response, 3);
				response.setValue(22,null, IsoType.ALPHA, 0);
				response.setValue(35, null, IsoType.ALPHA, 0);
			}else if(type.equals("1500")){
//				response = manageService.todaySettle(response);//批计算
			}else if(type.equals("1520")){
//				response = manageService.reconciliation(response);//批上送
			}else if(type.equals("1820")){
//				response = manageService.healthCheckByImei(response);
			}else if(type.equals("1440")){
//				response = manageService.getEitemAdvByImei(response);
			}
			String ctime = DateTimeUtils.getDate("yyyyMMddhhmmss");
			response.setValue(12, ASCIIUtil.String2HexString(ASCIIUtil
					.String2BCD(ctime.substring(8, 14))), IsoType.NUMERIC, 6);
			response.setValue(13, ASCIIUtil.String2HexString(ASCIIUtil
					.String2BCD(ctime.substring(4, 8))), IsoType.NUMERIC, 4);
			String retcode = response.getField(39).getValue().toString();
			retcode = ASCIIUtil.decode(retcode);
			String mac = "";
			if (objmac != null && retcode!=null&& retcode.equals("00")) {
				String returnstr = new String(response.writeData());
				returnstr = returnstr.substring(0,returnstr.length()-16);
				mac = MacEcbUtils.clacMac(ASCIIUtil.hexStringToByte(mackey),
						ASCIIUtil.hexStringToByte(returnstr.toUpperCase()));
				System.out.println("===mac==="+mac);
				response.setValue(64, mac, IsoType.ALPHA, mac.length());
			}else{
				response.setValue(64, null, IsoType.ALPHA, 0);
			}
			String returnstr = rtpdu + new String(response.writeData()) ;
			String strlen = IntegerUtil.intToString16(returnstr.length() / 2);
			strlen = StringUtils.flushLeft(strlen);
			returnstr = strlen + returnstr;
			returnstr = returnstr.toUpperCase();
			for(int i =0;i<129;i++){
				IsoValue<String> objmac1 = response.getField(i);
				if(objmac1!=null){
					System.out.println(i+"==respose=="+objmac1.getValue().toString());
				}
			}
			System.out.println(returnstr+"===returnstr===");
			returnstr = addQianZhiJiHead(returnstr, delQianZhiJiHead[1]);
			System.out.println("====addQianZhiJiHead====" + returnstr);
			session.write(str2Bcd(returnstr));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            session.close(true);
        }

	}

	private void sendToOldMina(IoSession session, String strToSendToOtherIP) {
        try {
            System.out.println("To old mina:" + oldMinaIP + ":" + oldMinaPort);
            byte[] o = str2Bcd(send(oldMinaIP, oldMinaPort, timeoutMillisOfWaitOldMina, strToSendToOtherIP));
            System.out.println("receive from old mina:" + bcd2Str(o));
            session.write(o);
        } finally {
            session.close(true);
        }
    }

	private List<String> needToNewGatewayEitemidsList;
	private boolean isNeedToSendToNewGateway(String theEitemId) {
		if (needToNewGatewayEitemidsList==null){
			needToNewGatewayEitemidsList = new ArrayList<String>();
			for (String s : needToNewGatewayEitemids.split(",")) {
				needToNewGatewayEitemidsList.add(s);
			}
		}
		return needToNewGatewayEitemidsList.contains(theEitemId);
	}

	private void sendToNewGateway(IoSession session, String strToSendToOtherIP) {
		System.out.println("To new gateway:" + newGatewayIP + ":" + newGatewayPort);
		byte[] o = str2Bcd(send(newGatewayIP, newGatewayPort, timeoutMillisOfWaitOldMina, strToSendToOtherIP));
		System.out.println("receive from gateway:" + bcd2Str(o));
		session.write(o);
	}

}
