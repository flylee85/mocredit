package com.mocredit.bank.util;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.BitSet;

import org.apache.log4j.Logger;
import org.jdom.Element;
import org.jdom.Document;
import org.jdom.Namespace;

import com.mocredit.bank.constant.Constant;
import com.mocredit.bank.entity.UP_LoadSchema;
import com.mocredit.bank.entity.ValidateFFormat8583;
import com.mocredit.bank.entity.ValidateField;
import com.mocredit.bank.entity.ValidateVFormat8583;


/**
 * DecodeDetail ISO8583
 * @author superdo
 */
public class PointsDecodeDetail {
	private static Logger logger = Logger.getLogger(PointsDecodeDetail.class);
	private String field60_3;
	/**
	 * deCode
	 * @param message
	 * @param length
	 * @param charset
	 * @param rootEleName
	 * @return
	 * @throws FormatException
	 */
	public Element deCode(byte[] message, int length, String charset, String rootEleName) 
			throws FormatException {
		logger.info(">>>>>>POS Decoder begin");
		if(length > message.length)
			throw new FormatException("parameter length value bigger then real buffer length error!");
		ByteBuffer msgBuf = ByteBuffer.wrap(message);
		msgBuf.limit(length);
		Element root = null;
		try {
			String pathRoot =PointsDecodeDetail.class.getResource("/conf/").getPath();
			UP_LoadSchema.loadSchema(pathRoot);
			Document schemaDoc = UP_LoadSchema.getSchema();
			if(null==schemaDoc)
				throw new FormatException("the POSP8583 schema is null!");
			Element schemaRootEle = schemaDoc.getRootElement();
			Namespace xsd = schemaRootEle.getNamespace();
			root = new Element(rootEleName);
			decodeMessage(msgBuf, schemaRootEle, charset, root, xsd);
		} catch (FormatException fe) {
			logger.debug(fe.getMessage());
			fe.printStackTrace();
			throw new FormatException("96");
		} catch (Exception e) {
			logger.debug(e.getMessage());
			throw new FormatException(e.getMessage(), e);
		}
		return root;
	}
	/**
	 * decode Message
	 * @param recvBuf
	 * @param schemaRoot
	 * @param charset
	 * @param root
	 * @param xsd
	 * @throws FormatException
	 */
	protected void decodeMessage(ByteBuffer recvBuf, Element schemaRoot,
			String charset, Element root, Namespace xsd) throws FormatException {
		try {
			/*get messageTypeValue BCD N4 2字节*/
			byte[] msgTypeBuf = new byte[2];
			String messageTypeValue = "";
			recvBuf.get(msgTypeBuf);
			messageTypeValue = HexBinary.encode(msgTypeBuf);
			logger.info("messageTypeValue :" + messageTypeValue);
			/*将消息类型添加到报文体中,做为它的零号域 */
			String messTypeName = UP_LoadSchema.getElementNameById(0);
			Element messType = new Element(messTypeName);
			messType.setText(messageTypeValue);
			root.addContent(messType);
			
			/**
			 * 获取放置bitmap的bitset
			 * POS规范为64位的bitmap
			 */
			BitSet bs = BitMap.getBitset(recvBuf);
			Element bitmap = new Element(UP_LoadSchema.getElementNameById(1));
			root.addContent(bitmap);
			logger.info("BitMap:"+bs.toString());
			/**
			 * 以BitMap为准遍历元素
			 */
			for(int i=1;i<bs.size();i++) {
				/*当前域不存在*/
				if(!bs.get(i)) {
					continue;
				} else{
					/*获取当前域的Element*/
					Element element = UP_LoadSchema.getElementById(i);
					if(null==element) {
						throw new FormatException("in xsdDoc,can get the element with id:[" + (i + 1) + "]");
					}
					/*get attribute name*/
					String elementName = element.getAttributeValue("name");
					if("".equals(elementName)||null==elementName) {
						throw new FormatException("in xsdDoc,hava not define the attribute [name] or it has no value!");
					}
					Element childOfElementMessage = new Element(elementName);
					/*get attribute type*/
					String elementType = element.getAttributeValue("type");
					if("".equals(elementType)||null==elementType) {
						throw new FormatException("in xsdDoc,element:[" + elementName + "],have not dedine the attribute[type] or it has no vlaue!");
					}
					/*get child element format*/
					Element format = UP_LoadSchema.getFormatElementByName(elementName);
					if (null == format) {
						throw new FormatException("can not get the child element:[format] from the element:[" + elementName + "]!");
					}
					String kind = format.getAttributeValue("kind");
					ValidateField.validate8583Kind(elementName, kind);
					if(Constant.KIND_F.equals(kind)) {/*处理定长域*/
						ValidateFFormat8583 vFFormat = new ValidateFFormat8583(element, format);
						if("string".equals(elementType)) {
							/*get format attribute length*/
							int len = vFFormat.getLen();
							/*get format attribute align*/
							String align = vFFormat.getAlign();
							/*get format attribute blank*/
							int blank = vFFormat.getBlank();
							/*get format attribute compress*/
							int compress = vFFormat.getCompress();
							String elementValue = getFField(recvBuf, len, blank, compress, align, charset, elementType);
							childOfElementMessage.addContent(elementValue);
						}else if("int".equals(elementType)) {
							// get format element endian
							String endian = vFFormat.getEndian();
							int len = Constant.int_;
							byte[] temp = new byte[len];
							recvBuf.get(temp);
							ByteBuffer buf = ByteBuffer.allocate(len);
							CodecUtil.setByteOrder(endian, buf);
							buf.put(temp);
							buf.flip();
							childOfElementMessage.addContent("" + buf.getInt());
						}else if("hexBinary".equals(elementType)){
							// get format attribute length
							int len = vFFormat.getLen();
							byte[] temp = new byte[len];
							recvBuf.get(temp);
							childOfElementMessage.addContent(HexBinary.encode(temp));
						}
					} else if(Constant.KIND_V.equals(kind)) {/*处理变长域*/
						/*变长域*/
						ValidateVFormat8583 vVFormat = new ValidateVFormat8583(element, format, xsd);
						/*头长度*/
						int headLen = vVFormat.getHeadLen();
						/*get head attribute compress 头压缩方式*/
						int headCompress = vVFormat.getHeadCompress();
						/*头填充符*/
						int headBlank = vVFormat.getHeadBlank();
						/*头对齐方式*/
						String headAlign = vVFormat.getHeadAlign();
						/*获取压缩*/
						int compress = vVFormat.getCompress();
						/*头进制*/
						int headRadix = vVFormat.getHeadRadix();
						//System.out.println("###变长域### head detail = 头长度：" + headLen + "|头压缩方式：" + headCompress + "|headBlank：" + headBlank + "|头对象方式：" + headAlign + "|头进制：" + headRadix);
						String eleValue = getVField(recvBuf, headLen, headBlank, compress, headAlign, headCompress, headRadix, elementType, charset, elementName, (i + 1));

						childOfElementMessage.addContent(eleValue);
					} else {
						throw new FormatException("element format,attribute kind value is:[" + kind + "]error");
					}
					logger.info("[" + (i + 1) + "]" + "[" + childOfElementMessage.getName() + "]" + "=" + "[" + childOfElementMessage.getText().length() + "]" + "[" + childOfElementMessage.getText() + "]");
					root.addContent(childOfElementMessage);
				}
			}
		}catch (FormatException fe) {
			logger.debug(fe.getMessage(),fe);
			throw fe;
		}catch (Exception e) {
			logger.debug(e.getMessage(),e);
			throw new FormatException(e.getMessage(), e);
		}catch (Throwable te) {
			logger.debug(te.getMessage(), te);
			throw new FormatException(te.getMessage(), te);
		}
	}
	/**
	 * decode v length field
	 * @param buf
	 * @param len
	 * @param blank
	 * @param compress
	 * @param align
	 * @param charset
	 * @param elementType
	 * @return
	 * @throws FormatException
	 */
	public String getFField(ByteBuffer buf,int len,int blank,int compress,
			String align,String charset,String elementType)throws FormatException {
		String result = null;
		byte[] temp = null;
		try {
			temp = BCD.getBinaryBuf(len, compress);
			buf.get(temp);
			/*修改 定长域压缩后，已经可以正常解出对应长度的字节数组了 因此对非压缩继续执行刷空白字符的功能*/
			if(Constant.COMPRESS_ON == compress) {
				temp =  BCD.bcd2str(temp, len, align).getBytes(charset);
			}else {
				if(Constant.ALIGN_L==align) {
					result = RidFillBlank.ridRightBlank(temp, blank, elementType, charset);
				}else {
					result = RidFillBlank.ridLeftBlank(temp, blank, elementType, charset);
				}
			}
			result = new String(temp,charset);
		} catch (UnsupportedEncodingException e) {
			throw new FormatException(e.getMessage(),e);
		}
		return result;
	}
	/**
	 * decode getVField
	 * @param buf
	 * @param headLen
	 * @param headBlank
	 * @param compress
	 * @param headAlign
	 * @param headCompress
	 * @param headRadix
	 * @param elementType
	 * @param charset
	 * @param elementName
	 * @param id
	 * @return
	 * @throws FormatException
	 */
	public String getVField(ByteBuffer buf,int headLen,int headBlank,
			int compress,String headAlign,int headCompress,
			int headRadix,String elementType,String charset,String elementName,
			int id)throws FormatException {
		String result = null;
		try {
			/*get Head value*/
			String realHeadValue = getHeadValue(buf, headLen, headBlank, headCompress, headAlign, headRadix, charset);
			int eleLen=0;
			if(realHeadValue.length()>0) {
				if(headRadix!=2) {
					eleLen=Integer.parseInt(realHeadValue, headRadix);
				}else {
					eleLen=Integer.parseInt(result, 16);
				}
			} else {
				
			}
			/**
			 * 解决48、36、60、32域VAR部分与头部分对齐方式不一致的问题
			 */
			if (id==48||id==36||id==32||id==60) {
				result = getEleValue(buf, compress, "L", elementType, charset, result, eleLen, elementName, id);
				if (id == 60 && result.length() >= 10) {
					this.field60_3 = result.substring(8, 11);
					if (this.field60_3 == null)
						this.field60_3 = "";
				}
			} else {
				result = getEleValue(buf, compress, headAlign, elementType, charset, result, eleLen, elementName, id);
			}
		} catch (Exception e) {
			throw new FormatException(e.getMessage(),e);
		}
		return result==null?"":result;
	}
	/**
	 * decode getHeadValue
	 * @param buf
	 * @param headLen
	 * @param headBlank
	 * @param headCompress
	 * @param headAlign
	 * @param headRadix
	 * @param charset
	 * @return
	 * @throws FormatException
	 */
	private String getHeadValue(ByteBuffer buf,int headLen,int headBlank,
			int headCompress,String headAlign,int headRadix,String charset)throws FormatException {
		String result = null;
		byte[] temp = null;
		try {
			temp = BCD.getBinaryBuf(headLen, headCompress);
			buf.get(temp);
			String elementType = null;
			if(headRadix!=2) {
				elementType="string";
			}else{
				elementType="hexBinary";
			}
			if(Constant.COMPRESS_ON==headCompress) {
				temp=BCD.bcd2str(temp, headLen, headAlign).getBytes(charset);
			}
			result = new String(temp, "utf-8");
//			if(Constant.ALIGN_L.equals(headAlign)) {
//				result=RidFillBlank.ridRightBlank(temp, headBlank, elementType, charset);
//				System.out.println("into ridRightBlank");
//				System.out.println(result);
//			}else{
//				result=RidFillBlank.ridLeftBlank(temp, headBlank, elementType, charset);
//				System.out.println("into ridLeftBlank");
//				System.out.println(result);
//			}
		} catch (UnsupportedEncodingException e) {
			throw new FormatException(e.getMessage(),e);
		}
		return result;
	}
	/**
	 * decode getElementValue
	 * @param buf
	 * @param compress
	 * @param align
	 * @param elementType
	 * @param charset
	 * @param result
	 * @param eleLen
	 * @param elementName
	 * @param id
	 * @return
	 * @throws FormatException
	 * @throws UnsupportedEncodingException
	 */
	private String getEleValue(ByteBuffer buf,int compress,String align,
			String elementType,String charset,String result,int eleLen,
			String elementName,int id)throws FormatException, UnsupportedEncodingException{
		if("string".equals(elementType)) {
			if(id==5 || id==62){
				byte[] eleBuf = BCD.getBinaryBuf(eleLen*2, compress);
				buf.get(eleBuf);
				if (Constant.COMPRESS_ON == compress)
					result = BCD.bcd2str(eleBuf, eleLen, align);
				else
					result = new String(eleBuf, charset);
			}else{
				byte[] eleBuf = BCD.getBinaryBuf(eleLen, compress);
				buf.get(eleBuf);
				if(Constant.COMPRESS_ON == compress)
					result=BCD.bcd2str(eleBuf, eleLen, align);
				else
					result=new String(eleBuf,charset);
			}
		}else if("hexBinary".equals(elementType)) {
			byte[] temp = new byte[eleLen];
			buf.get(temp);
			if (id == 48) {
				byte[] temp2 = new byte[eleLen - 2];
				System.arraycopy(temp, 2, temp2, 0, temp2.length);
				result = HexBinary.encode(temp2);
			} else {
				result = HexBinary.encode(temp);
			}
		}else {
			throw new FormatException("element:[" + elementName + "],kind is:[V],type is:[" + elementType + "]error!");
		}
		return result;
	}
}
