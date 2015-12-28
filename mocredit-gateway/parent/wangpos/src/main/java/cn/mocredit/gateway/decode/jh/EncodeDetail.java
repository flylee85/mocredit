package cn.mocredit.gateway.decode.jh;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.BitSet;

import org.apache.log4j.Logger;
import org.jdom.Element;

import cn.mocredit.gateway.posp.bc.binding.Constant;
import cn.mocredit.gateway.posp.bc.binding.UP_LoadSchema;
import cn.mocredit.gateway.posp.bc.binding.ValidateFFormat8583;
import cn.mocredit.gateway.posp.bc.binding.ValidateField;
import cn.mocredit.gateway.posp.bc.binding.ValidateVFormat8583;
import cn.mocredit.gateway.posp.bc.util.BCD;
import cn.mocredit.gateway.posp.bc.util.BindingException;
import cn.mocredit.gateway.posp.bc.util.BitMap;
import cn.mocredit.gateway.posp.bc.util.CodecUtil;
import cn.mocredit.gateway.posp.bc.util.FormatException;
import cn.mocredit.gateway.posp.bc.util.HexBinary;
import cn.mocredit.gateway.posp.bc.util.MessageObject;
import cn.mocredit.gateway.posp.bc.util.RidFillBlank;

/**
 * EncodeDetail ISO8583 for pos
 * @author Superdo
 *
 */
public class EncodeDetail {
	private static Logger logger = Logger.getLogger(EncodeDetail.class);
	private String field60_3;
	/**
	 * encode 
	 * @param xmlElement
	 * @param schema
	 * @param output
	 * @param charset
	 * @throws BindingException
	 */
	public void encode(MessageObject request,
			OutputStream output,String charset,String flag) throws BindingException{
		logger.info(">>>>>>Encoder begin");
		try {
			if(null==request)
				throw new FormatException(" the parameter encode request is null");
			if(null==charset)
				throw new FormatException(" the parameter encodeCharset is null");
			encodeMessage(request, output, charset,flag);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * encode Message
	 * @param complexType
	 * @param xmlElement
	 * @param output
	 * @param encodeCharset
	 * @param schema
	 * @throws FormatException
	 */
	private void encodeMessage(MessageObject request,
			OutputStream output, String encodeCharset,String flag)throws FormatException {
		System.out.println("encodeMessage"); 
		System.out.println(request);
		try {
			BitSet bs = new BitSet(64);
			encodeBitSet(request, bs);
			logger.info("BitMAP:"+bs.toString());
			logger.info("校验mac返回："+isVerifyMAC(request));
			if(request.getMesstype().equals("0820"))
				request.setField64_MAC(null);
			byte[] bitMapBuf = new BitMap().encodeBitMap(bs);
			String messageTypeName = UP_LoadSchema.getElementNameById(0);
			String messageType = request.getMesstype();
			System.out.println("messType:" + messageType);
			/*将消息类型和位图添加到要生成的buffer中*/
			if (null == messageType) {
				throw new FormatException("message type is empty!");
			}
			output.write(HexBinary.decode(messageType));
			output.write(bitMapBuf);
			
			for (int i = 1; i < bs.length(); i++) {
				if (!bs.get(i)) {
					continue;
				}
				Element element = UP_LoadSchema.getElementById(i);
				/*get attribute name*/
				String elementName = element.getAttributeValue("name");
				if(flag.equals("0") && elementName.equals(UP_LoadSchema.getElementNameById(64))) {
					//if (elementName.equals(messageTypeName)) {
						continue;
					//}
				}
				/*get attribute type*/
				String elementType = element.getAttributeValue("type");
				if (null == elementType || "".equals(elementType)) {
					throw new FormatException("in xsdDoc,elementName:[" + elementName + "]have not define the attribute[type] or it has no vlaue!");
				}
				/*针对签到交易的62域进行特殊处理*/
				if (messageType.equals("0810") && field60_3 != null && field60_3.equals("003")) {
					if ((i + 1) == 62) {
						elementType = "hexBinary";
					}
				}
				/*取域值*/
				String elementValue = null;//xmlElement1.getChildText(elementName);
				
				java.lang.reflect.Field[] fields=request.getClass().getDeclaredFields();
				for(int j=0;j<fields.length;j++) {
					if(fields[j].getName().equals(elementName))
						elementValue = (String) getFieldValueByName(fields[j].getName(),request);
				}
				
				System.out.println("field[" + (i + 1) + "]=" + elementValue);
//				if(i+1==61) {
//					System.out.println("六十域：["+xmlElement1.getChildText(elementName)+"]");
//				}
				/*if empty element ,do not deal with*/
				if (null == elementValue || elementValue.trim().equals("")) {
					continue;
				}
				/*取出60.3域的值,为签到交易做特殊处理*/
				if ((i + 1) == 60 && elementValue.length() >= 10) {
					this.field60_3 = elementValue.substring(8, 11);
					if (this.field60_3 == null) {
						this.field60_3 = "";
					}
				}
				Element format = UP_LoadSchema.getFormatElementByName(elementName);
				String kind = format.getAttributeValue("kind");
				ValidateField.validate8583Kind(elementName, kind);
				if (Constant.KIND_F.equals(kind)) {
					ValidateFFormat8583 vFFormat = new ValidateFFormat8583(elementName, format, elementType);
					encodeFValue(output, encodeCharset, elementType, elementValue, elementName, vFFormat);
				} else if (Constant.KIND_V.equals(kind)) {
					ValidateVFormat8583 vVFormat = new ValidateVFormat8583(elementName, elementType, format, UP_LoadSchema.getNamespace());
					/*修改 传入域号 为了60域特殊处理*/
					encodeVValue(output, encodeCharset, elementType, elementValue, elementName, vVFormat, i + 1);
				}
			}
		} catch (FormatException fe) {
			fe.printStackTrace();
			throw fe;
		} catch (Exception e) {
			e.printStackTrace();
			throw new FormatException(e.getMessage(), e);
		}
	}
	
	/**
	 * Encode BitSet
	 * @param request
	 * @param bs
	 * @throws FormatException
	 */
	private void encodeBitSet(MessageObject request,BitSet bs) throws FormatException {
		
		java.lang.reflect.Field[] fields=request.getClass().getDeclaredFields();
		for(int i=0;i<fields.length;i++) {
			/*get element name*/
			String elementName = fields[i].getName();
			if("tranCode".equals(elementName))
				continue;
			if(null==elementName||elementName.length()<1)
				throw new FormatException("element do not define attribute: [name] or the attribute do not have value");
			/*get element name*/
			String elementValue = (String) getFieldValueByName(fields[i].getName(),request);
			if(null==elementValue||elementValue.length()<1)
				continue;
			try {
				/*get format*/
				Element format = UP_LoadSchema.getFormatElementByName(elementName);
				if(null==format)
					throw new FormatException("can not find child element [format]");
				/*get format attribute id*/
				if (Integer.parseInt(format.getAttributeValue("id")) < 2)
					continue;
				int id = ValidateField.validateEId(format.getAttributeValue("id"), elementName);
				
				if(bs.get(id))
					throw new FormatException("in,xsdDoc, name is:[" + elementName + "],bit map error! maybe two element have same id!");
				bs.set(id - 1);
			}catch (FormatException e) {
				e.printStackTrace();
				throw e;
			} catch (Exception e) {
				e.printStackTrace();
				throw new FormatException(e.getMessage(), e);
			}
		}
	}
	
	/**
	 * verifyMAC
	 * @param request
	 * @return
	 * @throws FormatException
	 */
	public boolean isVerifyMAC(MessageObject request) throws FormatException {
		try {
			String msgType = request.getMesstype();
			System.out.println("MSGTYPE by isVerifyMAC:" + msgType);

			/* 管理类不验MAC */
			/* messageType为空 不校验mac */
			if (msgType == null) {
				return false;
			} else if (msgType.startsWith("03") || msgType.startsWith("11")|| msgType.startsWith("06")  || msgType.startsWith("11")) {
				return false;
			} else if (msgType.startsWith("01") || msgType.startsWith("02") || msgType.startsWith("04")|| msgType.startsWith("12")
					|| msgType.startsWith("13")|| msgType.startsWith("14")||msgType.startsWith("08")) {
				/* 非01XX,02XX,04XX 拒绝应答, 不验 MAC */
				String field39 = request.getField39_Response_Code();
				System.out.println("isVerifyMAC field39 :" + field39);
				if(msgType.equals("0820"))
					return true;
				if (field39 == null || !"00".equals(field39)) {
					return false;
				}
			} else {
				throw new FormatException("96");
			}
		} catch (FormatException e) {
			e.printStackTrace();
			throw new FormatException("96", e);
		}
		return true;
	}
	
	/**
	 * encode Fixed length Value
	 * @param output
	 * @param encodeCharset
	 * @param elementType
	 * @param elementValue
	 * @param elementName
	 * @param vFFormat
	 * @throws FormatException
	 */
	protected void encodeFValue(OutputStream output, String encodeCharset, String elementType, String elementValue, String elementName, ValidateFFormat8583 vFFormat) throws FormatException {
		try {
			if ("string".equals(elementType)) {
				String realValue = null;
				/*get format attribute length*/
				int len = vFFormat.getLen();
				/*get format attribute align*/
				String align = vFFormat.getAlign();
				/*get format attribute blank*/
				char blank = vFFormat.getBlank();
				/*get format attribute compress*/
				int compress = vFFormat.getCompress();
				
				realValue = RidFillBlank.fillBlank(elementValue, len, blank, align, encodeCharset, elementName);
				if (Constant.COMPRESS_ON == compress) {
					output.write(BCD.str2bcd(realValue, align, blank));
				} else {
					try {
						output.write(realValue.getBytes(encodeCharset));
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			} else if ("int".equals(elementType)) {
				/*get format attribute endian*/
				if ("".equals(elementValue))
					elementValue = "0";
				
				String endian = vFFormat.getEndian();
				int len = Constant.int_;
				ByteBuffer buffer = ByteBuffer.allocate(len);
				CodecUtil.setByteOrder(endian, buffer);
				buffer.putInt(Integer.parseInt(elementValue));
				buffer.flip();
				// int value = buffer.getInt();
				byte[] array = buffer.array();
				output.write(array);
			} else if ("hexBinary".equals(elementType)) {
				/*hexbinary 空值如何填充*/
				/*get attribute length*/
				int len = vFFormat.getLen();
				byte[] temp = HexBinary.decode(elementValue);
				if (temp.length == len) {
					output.write(temp);
				} else if (temp.length > len)
					throw new FormatException("hexBinary length over-longer!");
			} else {
				throw new FormatException("element:[" + elementName + "],attribute kind is:[F],attribute type is:[" + elementType + "]error!");
			}
		} catch (FormatException fe) {
			fe.printStackTrace();
			throw fe;
		} catch (Exception e) {
			e.printStackTrace();
			throw new FormatException(e.getMessage(), e);
		}
	}
	
	
	/**
	 * encode Varlena Value
	 * @param output
	 * @param encodeCharset
	 * @param elementType
	 * @param elementValue
	 * @param elementName
	 * @param vVFormat
	 * @param id
	 * @throws FormatException
	 */
	protected void encodeVValue(OutputStream output, String encodeCharset, String elementType, String elementValue, String elementName, ValidateVFormat8583 vVFormat, int id) throws FormatException {

		/*get head attribute length*/
		int headLen = vVFormat.getHeadLen();
//		if(id==61) {
//			System.out.println("六十域头长度["+headLen+"]");
//			System.out.println("处理六十域头长度["+headLen/2+"]");
//		}
		
		/*get head attribute compress*/
		int headCompress = vVFormat.getHeadCompress();
		/*get head attribute blank*/
		char headBlank = vVFormat.getHeadBlank();
		/*get head attribute align*/
		String headAlign = vVFormat.getHeadAlign();
		/*get head attribute radix*/
		int headRadix = vVFormat.getHeadRadix();
		/*format attribute compress*/
		int compress = vVFormat.getCompress();
		
		String headValue = "";
		try {
			if ("string".equals(elementType)) {
				headValue = Integer.toString(elementValue.getBytes(encodeCharset).length, headRadix);
				if(id==62|| id==5) {
					int headTemp =	Integer.valueOf(headValue);
					headValue = String.valueOf(headTemp/2);
				}
				if (headRadix == 2) {
					headValue = Integer.toString(elementValue.getBytes(encodeCharset).length, 16);
					if (headValue.length() % 2 != 0) {
						headValue = "0" + headValue;
					}
					headValue = new String(HexBinary.decode(headValue));
				}
			} else if ("hexBinary".equals(elementType)) {
				if (elementValue.getBytes(encodeCharset).length % 2 != 0) {
					throw new FormatException("element:[" + elementName + "],type:[" + elementType + "],value length is:[" + elementValue.getBytes(encodeCharset).length + " ]error! it length must can divide by 2");
				}
				headValue = Integer.toString(elementValue.getBytes(encodeCharset).length / 2, headRadix);
				if (headRadix == 2) {
					headValue = Integer.toString(elementValue.getBytes(encodeCharset).length / 2, 16);
					if (headValue.length() % 2 != 0) {
						headValue = "0" + headValue;
					}
					headValue = new String(HexBinary.decode(headValue));
				}
			} else {
				throw new FormatException("element:[" + elementName + "],kind is [V],type is:[" + elementType + "]error!");
			}
		} catch (FormatException fe) {
			throw fe;
		} catch (Exception e1) {

			new FormatException(e1.getMessage(), e1);
		}
		String realHeadValue = null;
		realHeadValue = RidFillBlank.fillBlank(headValue, headLen, headBlank, headAlign, encodeCharset, elementName);

		/*put headValue*/
		try {
			if (Constant.COMPRESS_ON == headCompress) {
				byte[] value = BCD.str2bcd(realHeadValue, headAlign, headBlank);
				output.write(value);
			} else {
				try {
					byte[] value = realHeadValue.getBytes(encodeCharset);
					output.write(value);
				} catch (Exception e) {
					throw new FormatException(e.getMessage(), e);
				}
			}
			/*put elementValue*/
			if ("string".equals(elementType)) {
				if (Constant.COMPRESS_ON == compress) {
					/*36、48、60域特殊处理 头靠右对齐，域靠左对齐*/
					byte[] value;
					if (id == 22 ||id == 60 || id == 48 || id == 36 || id == 32 || id == 62|| id == 62) {
						value = BCD.str2bcd(elementValue, "L", headBlank);
						// value = HexBinary.decode(elementValue);
					} else if(id == 2) {
						if(elementValue.length()%2==1) {
							elementValue=elementValue+"0";
						}
						value = BCD.str2bcd(elementValue, headAlign, headBlank);
					}else {
						value = BCD.str2bcd(elementValue, headAlign, headBlank);
					}
					output.write(value);
				} else {
					try {
						byte[] value = elementValue.getBytes(encodeCharset);
						output.write(value);
					} catch (UnsupportedEncodingException e) {
						throw new FormatException(e.getMessage(), e);
					}
				}
			} else if ("hexBinary".equals(elementType)) {

				byte[] temp = HexBinary.decode(elementValue);
				output.write(temp);

			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new FormatException(e.getMessage(), e);
		}
	}
	
	/**
	 * get field value by.name
	 * @param fieldName
	 * @param o
	 * @return
	 */
	private Object getFieldValueByName(String fieldName, Object o) {
		 try {
		     String firstLetter = fieldName.substring(0, 1).toUpperCase();  
		     String getter = "get" + firstLetter + fieldName.substring(1);
		     Method method = o.getClass().getMethod(getter, new Class[] {});
		     Object value = method.invoke(o, new Object[] {});
		     return value;
		 } catch (Exception e) {
		     logger.error(e.getMessage(),e);
		     return null;
		 }
    }
}
