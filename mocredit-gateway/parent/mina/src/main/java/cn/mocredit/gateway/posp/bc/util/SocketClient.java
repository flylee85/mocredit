package cn.mocredit.gateway.posp.bc.util;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import org.apache.log4j.Logger;

/**
 * 
 * @author Superdo
 *
 */
public class SocketClient {
	private static Logger logger = Logger.getLogger(SocketClient.class);
	
    public static String request(String url,int port,String sendCommand) throws Exception
    {
    	String str;
    	InputStream is = null;
    	Socket socket = null ;
		try {
			System.out.println(url+"===="+port+"===socket=="+socket);
			socket = new Socket(url, port);
			System.out.println("===socket=="+socket);
			socket.setSoTimeout(30000);
			System.out.println("=====sendCommand==="+sendCommand);
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(BCD.str2Bcd(sendCommand));
			outputStream.flush();
			is = socket.getInputStream();
			byte[] bytes = new byte[1024];
			is.read(bytes);
//			str = ASCIIUtil.bytesToHexString(bytes);;
			str = HexBinary.encode(bytes);
			String strlen = str.substring(0,4);
//			str = str.substring(0,IntegerUtil.String16ToInt(strlen)*2+4);
			str = str.substring(0,Integer.parseInt(strlen,16)*2+4);
			return str;
		} catch (UnknownHostException e) {
			return "Error:无法识别服务器的主机名";
		  } catch (ConnectException e) {
			  e.printStackTrace();
			  return "Error:没有服务器监听指定的端口或者服务器拒绝连接";
		  }catch (Exception e) {
//			  e.printStackTrace();
//			  return "Error:访问卡商服务器异常";
//			  return "96";
			  throw new Exception();
		  }finally{
			if(is!=null)
				is.close();
			if(socket!=null)
				socket.close();
		}
    }
    
    public static String sentMessage(String url,int port,byte[] sendByte) throws Exception
    {
    	String str;
    	InputStream is = null;
    	Socket socket = null ;
		try {
			socket = new Socket(url, port);
			logger.info("client start success..."+socket);
			socket.setSoTimeout(3000000);
			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(sendByte);
			outputStream.flush();
			is = socket.getInputStream();
			byte[] bytes = new byte[1024];
			is.read(bytes);
			str = HexBinary.encode(bytes);
			String strlen = str.substring(0,4);
			str = str.substring(0,Integer.parseInt(strlen,16)*2+4);
			return str;
		} catch (UnknownHostException e) {
			return "Error:无法识别服务器的主机名";
		  } catch (ConnectException e) {   
			  e.printStackTrace();
			  return "Error:没有服务器监听指定的端口或者服务器拒绝连接";
		  }catch (Exception e) {
			  e.printStackTrace();
			  return "Error:访问卡商服务器异常";
		  }finally{
			if(is!=null)
				is.close();
			if(socket!=null)
				socket.close();
		}
    }
    public static void main(String[] args) {
//    	String request="0033600003000060310031120308000000000000C000103130343030303637303030303030303030303030303034001100000100400";
//    	String request = "003c600003000060210000000008000020000000C00012000084313034303030363730303030303030303030303030303400110000000100100003303031";//签到
//   	String request = "0034600005000060210000000008000000000000C0001031303430303037353030303030303030303030303030340011000000013800";//下载kek
    	//余额查询
    	String request="0036600032000008000020000000C00012000012313233343536373131323334353637383930313233343500110000000100100003303120";
    
//    	String request = "0073600032000002007020048020C09811160000010000525435000000000000000001000029021000370000010000525435D00000017580010000000031323334353637313132333435363738393031323334353135366478E2B43E312A1220000000000000000008220000023546303543374543";
    	try {
			String response = SocketClient.sentMessage("119.253.49.196", 9002, HexBinary.decode(request));
			System.out.println("雅高-response["+response+"]");
		} catch (Exception e) {
			logger.info("卡商返回异常");
		}
    	
//    	byte [] dataSource = HexBinary.decode("02007020048020C09811160000010000525435000000000000000001000055021000370000010000525435D00000017580010000000031323334353637313132333435363738393031323334353135366478E2B43E312A122000000000000000000822000002");
//    	System.out.println(MacECBUtils.clacMac(HexBinary.decode("3131313131313131"), dataSource));
    	
    }
}