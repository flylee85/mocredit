package cn.m.mt.util;

import java.util.Date;

import cn.m.mt.common.DateTimeUtils;
import cn.m.mt.common.MMSBO;
import cn.m.mt.po.Barcode;
import cn.m.mt.po.Batch;
import cn.m.mt.po.Eitem;
import cn.m.mt.po.Eorder;
import cn.m.mt.po.Smsmt;
import cn.m.mt.po.Wemmsmt;

public class MMSBOUtil {

	//根据短信 补发短信
	public static MMSBO getMMSBO(Smsmt smsmt,int mttype,String senduser){
		MMSBO mmsbo = new MMSBO();
		if (smsmt.getBatch() != null) {
			mmsbo.setBatchid(smsmt.getBatch().getId());
		}
		mmsbo.setContent(smsmt.getContent());
		mmsbo.setCouponid(smsmt.getCouponid());
		mmsbo.setCreatetime(smsmt.getCreatetime());
		if (smsmt.getEitem() != null) {
			mmsbo.setEitemid(smsmt.getEitem().getId());
		}
		mmsbo.setIsresend(Variable.ISRESEND_YES);
		mmsbo.setMobile(smsmt.getMobile());
		mmsbo.setState(smsmt.getState());
		mmsbo.setTid(smsmt.getTid());
		mmsbo.setMttype(mttype);//
		mmsbo.setType(Variable.MMSBO_TYPE_SMS); //发送类型为短信
		if(senduser!=null){
    		mmsbo.setStatuscode(senduser);
    	}else{
    		mmsbo.setStatuscode(Variable.MTTYPE_SANDTYPE_TAOBAOBUFA);
    	}
		Long entid = null ;
		if(smsmt.getEnterprise()!=null){
			entid = smsmt.getEnterprise().getId();
		}else{
			if(smsmt.getEitem()!=null){
				if(smsmt.getEitem().getEnterprise()!=null){
					entid = smsmt.getEitem().getEnterprise().getId();
				}
			}
		}
		mmsbo.setEntid(entid);
		return mmsbo;
	}
	
	//根据彩信补发彩信
	public static MMSBO getMMSBO(Wemmsmt wemmsmt,int mttype,String senduser){
		MMSBO mmsbo = new MMSBO();
    	if (wemmsmt.getBatch() != null) {
			mmsbo.setBatchid(wemmsmt.getBatch().getId());
		}
		mmsbo.setEorderid(wemmsmt.getEorder().getId());
    	mmsbo.setMobile(wemmsmt.getMobile());
    	mmsbo.setCustomer(wemmsmt.getCustomer());
    	mmsbo.setExtfield1(wemmsmt.getExtfield1());
    	mmsbo.setExtfield2(wemmsmt.getExtfield2());
    	mmsbo.setExtfield3(wemmsmt.getExtfield3());
    	mmsbo.setNumberpwd(wemmsmt.getNumberpwd());
    	mmsbo.setStatus(wemmsmt.getStatus());
    	String channle = wemmsmt.getEnterprise().getMmschannle();
    	mmsbo.setChannleno(channle);
    	mmsbo.setIsresend(Variable.ISRESEND_YES);
    	mmsbo.setBarcodeno(wemmsmt.getBarcodeno());
    	mmsbo.setTid(wemmsmt.getTid());
    	mmsbo.setCreatetime(wemmsmt.getCreatetime());
    	mmsbo.setCharcode(wemmsmt.getCharcode());
    	mmsbo.setPackageid(wemmsmt.getPackageid());
    	mmsbo.setType(Variable.MMSBO_TYPE_MMS); //发送类型为彩信
    	mmsbo.setMttype(mttype);//
    	if(senduser!=null){
    		mmsbo.setStatuscode(senduser);
    	}else{
    		mmsbo.setStatuscode(Variable.MTTYPE_SANDTYPE_TAOBAOBUFA);
    	}
    	Long entid = null ;
		if(wemmsmt.getEnterprise()!=null){
			entid = wemmsmt.getEnterprise().getId();
		}else{
			if(wemmsmt.getEorder()!=null){
				if(wemmsmt.getEorder().getEitem().getEnterprise()!=null){
					entid = wemmsmt.getEorder().getEitem().getEnterprise().getId();
				}
			}
		}
		mmsbo.setEntid(entid);
    	return mmsbo;
	}
	
	//淘宝手动补发 彩信
	public static MMSBO getMMSBO(Barcode barcode,Smsmt smsmt){
		MMSBO mmsbo = new MMSBO();
		Eorder eorder = barcode.getEorder();
		Batch batch = null;
		if (smsmt != null) {
			batch = smsmt.getBatch();
		}
		Eitem eitem = barcode.getEitem();
    	if (batch != null) {
			mmsbo.setBatchid(batch.getId());
		}
		mmsbo.setEorderid(eorder.getId());
    	mmsbo.setMobile(eorder.getPhone());
    	mmsbo.setCustomer(eorder.getBuynick());
    	mmsbo.setExtfield1(eitem.getName());
    	mmsbo.setExtfield2(String.valueOf(eorder.getNum()));
    	mmsbo.setExtfield3(DateTimeUtils.formateCNDate(barcode.getEndate()));
    	mmsbo.setNumberpwd(barcode.getNumcode());
    	mmsbo.setStatus(0);//待发
    	mmsbo.setIsresend(Variable.ISRESEND_YES);// 首发
    	mmsbo.setBarcodeno(eitem.getCodeno());
    	mmsbo.setTid(eorder.getTid());
    	mmsbo.setCreatetime(DateTimeUtils.getDate("yyyy-MM-dd HH:mm:ss"));
    	mmsbo.setCharcode(barcode.getCharcode());
    	mmsbo.setPackageid(eitem.getMmspackageid());
    	mmsbo.setType(Variable.MMSBO_TYPE_MMS); //发送类型为彩信
    	mmsbo.setMttype(Variable.MTTYPE_RESEND);//
    	mmsbo.setStatuscode(Variable.MTTYPE_SANDTYPE_TAOBAOBUFA);
    	mmsbo.setEntid(eitem.getEnterprise().getId());
    	mmsbo.setEitemid(barcode.getEitem().getId());
    	return mmsbo;
    }
	
	//系统发送彩信
	public static MMSBO getMMSBO(Barcode barcode,Eorder eorder,String username, Integer mmtype,Long contactsid,Integer... isresend){
		MMSBO mmsbo = new MMSBO();
		if (barcode.getBatch() != null) {
			mmsbo.setBatchid(barcode.getBatch().getId());
		}
		mmsbo.setCharcode(barcode.getCharcode());
		mmsbo.setNumberpwd(barcode.getNumcode());
		mmsbo.setBarcodeno(barcode.getEitem().getCodeno());
		mmsbo.setTid(eorder.getTid());
		mmsbo.setEorderid(eorder.getId());
		mmsbo.setChannleno(eorder.getEitem().getEnterprise().getMmschannle());
		mmsbo.setPackageid(barcode.getEitem().getMmspackageid());
		mmsbo.setStatus(Variable.MMSSTATUS_WAITE);
		mmsbo.setType(Variable.MMSBO_TYPE_MMS); // 发送类型为彩信
		//mmsbo.setMttype(Variable.MMSBO_MTTYPE_DEFAULT);// 默认发送级别为 实时
		mmsbo.setCreatetime(DateTimeUtils.getDate("yyyy-MM-dd HH:mm:ss"));
		mmsbo.setMobile(eorder.getPhone());
		mmsbo.setCustomer(eorder.getBuynick());
		mmsbo.setExtfield1(eorder.getExtfield1());
		mmsbo.setExtfield2(eorder.getExtfield2());
		mmsbo.setExtfield3(eorder.getExtfield3());
		if(isresend!=null &&isresend.length>0){
			mmsbo.setIsresend(isresend[0]);
		}else{
			mmsbo.setIsresend(Variable.ISRESEND_NO);
		}
		if(mmtype != null){
			mmsbo.setMttype(mmtype);//发送类型
		}
		mmsbo.setStatuscode(username);//发送者用户名
		mmsbo.setEntid(barcode.getEitem().getEnterprise().getId());
		mmsbo.setEitemid(barcode.getEitem().getId());
		if(contactsid != null && contactsid.intValue() > 0){
			mmsbo.setContatsid(contactsid);
		}
		if(barcode != null)
		mmsbo.setBarcodeid(barcode.getId());
		return mmsbo;
	}
	
	//系统发送短信
	public static MMSBO getSMSBO(Barcode barcode,Eorder eorder,String username,Integer mmtype,Long contactsid, Integer... isresend){
		MMSBO smsbo = new MMSBO();
		if (barcode.getBatch() != null) {
			smsbo.setBatchid(barcode.getBatch().getId());
		}
		smsbo.setCharcode(barcode.getCharcode());
		smsbo.setNumberpwd(barcode.getNumcode());
		smsbo.setBarcodeno(barcode.getEitem().getCodeno());
		smsbo.setTid(eorder.getTid());
		smsbo.setEorderid(eorder.getId());
		smsbo.setPackageid(barcode.getEitem().getMmspackageid());
		smsbo.setStatus(Variable.MMSSTATUS_WAITE);
		smsbo.setType(Variable.MMSBO_TYPE_SMS); // 发送类型为短信
		//smsbo.setMttype(Variable.MMSBO_MTTYPE_DEFAULT);// 默认发送级别为 实时
		smsbo.setCreatetime(DateTimeUtils.getDate("yyyy-MM-dd HH:mm:ss"));
		smsbo.setMobile(eorder.getPhone());
		smsbo.setCustomer(eorder.getBuynick());
		if(isresend!=null &&isresend.length>0){
			smsbo.setIsresend(isresend[0]);
		}else{
			smsbo.setIsresend(Variable.ISRESEND_NO);
		}
		String smscontent = barcode.getEitem().getSmscontent();
		String contactName = eorder.getBuynick();
		String f1 = eorder.getExtfield1();
		String f2 = eorder.getExtfield2();
		String f3 = eorder.getExtfield3();
		if (contactName != null)
			smscontent = smscontent.replace("$name", contactName);
		if (barcode.getNumcode() != null)
			smscontent = smscontent.replace("$pwd", barcode.getNumcode());
		if (f1 != null)
			smscontent = smscontent.replace("$f1", f1);
		if (f2 != null)
			smscontent = smscontent.replace("$f2", f2);
		if (f3 != null)
			smscontent = smscontent.replace("$f3", f3);
		smsbo.setContent(smscontent);
		if(mmtype != null){
			smsbo.setMttype(mmtype);//发送类型
		}
		smsbo.setStatuscode(username);//发送者用户名
		smsbo.setEntid(barcode.getEitem().getEnterprise().getId());
		smsbo.setEitemid(barcode.getEitem().getId());
		if(contactsid != null && contactsid.intValue() > 0){
			smsbo.setContatsid(contactsid);
		}
		if(barcode != null)
			smsbo.setBarcodeid(barcode.getId());
		return smsbo;
	}
	
	//系统发送短彩
	public static MMSBO getALLBO(Barcode barcode,Eorder eorder,String sysname,Integer mmtype,Long contactsid,Integer... isresend){
		MMSBO allbo = new MMSBO();
		if (barcode.getBatch() != null) {
			allbo.setBatchid(barcode.getBatch().getId());
		}
		allbo.setChannleno(eorder.getEitem().getEnterprise().getMmschannle());
		allbo.setCharcode(barcode.getCharcode());
		allbo.setNumberpwd(barcode.getNumcode());
		allbo.setBarcodeno(barcode.getEitem().getCodeno());
		allbo.setTid(eorder.getTid());
		allbo.setEorderid(eorder.getId());
		allbo.setPackageid(barcode.getEitem().getMmspackageid());
		allbo.setStatus(Variable.MMSSTATUS_WAITE);
		allbo.setState(Variable.MMSSTATUS_WAITE);
		allbo.setType(Variable.MMSBO_TYPE_SSMMMS); // 发送类型为彩信
		//allbo.setMttype(Variable.MMSBO_MTTYPE_DEFAULT);// 默认发送级别为 实时
		allbo.setCreatetime(DateTimeUtils.getDate("yyyy-MM-dd HH:mm:ss"));
		allbo.setMobile(eorder.getPhone());
		String contactName = eorder.getBuynick();
		String f1 = eorder.getExtfield1();
		String f2 = eorder.getExtfield2();
		String f3 = eorder.getExtfield3();
		allbo.setCustomer(contactName);
		String smscontent = barcode.getEitem().getSmscontent();
		if (contactName != null)
			smscontent = smscontent.replace("$name", contactName);
		if (barcode.getNumcode() != null)
			smscontent = smscontent.replace("$pwd", barcode.getNumcode());
		if (f1 != null)
			smscontent = smscontent.replace("$f1", f1);
		if (f2 != null)
			smscontent = smscontent.replace("$f2", f2);
		if (f3 != null)
			smscontent = smscontent.replace("$f3", f3);
		allbo.setContent(smscontent);
		allbo.setExtfield1(f1);
		allbo.setExtfield2(f2);
		allbo.setExtfield3(f3);
		if(isresend!=null &&isresend.length>0){
			allbo.setIsresend(isresend[0]);
		}else{
			allbo.setIsresend(Variable.ISRESEND_NO);
		}
		if(mmtype != null){
			allbo.setMttype(mmtype);//发送类型
		}
		allbo.setStatuscode(sysname);//发送者用户名
		allbo.setEntid(barcode.getEitem().getEnterprise().getId());
		allbo.setEitemid(barcode.getEitem().getId());
		if(contactsid != null && contactsid.intValue() > 0){
			allbo.setContatsid(contactsid);
		}
		if(barcode != null){
			allbo.setBarcodeid(barcode.getId());
		}
		return allbo;
	}
	
	//淘宝接口短信
	public static MMSBO getTaoBaoSMSBO(Eorder eorder, String content, Long eitemid,int isresend){
		MMSBO smsbo = new MMSBO();
		smsbo.setMobile(eorder.getPhone());
		smsbo.setCreatetime(DateTimeUtils.dateToStr(new Date(),
				"yyyy-MM-dd HH:mm:ss"));
		smsbo.setContent(content.toString());
		smsbo.setType(Variable.MMSBO_TYPE_SMS);
		smsbo.setStatus(Variable.MT_WAITING_SEND);
		smsbo.setMttype(0);
		smsbo.setTid(eorder.getTid());
		smsbo.setEitemid(eitemid);
		smsbo.setIsresend(isresend);
		smsbo.setStatuscode(Variable.MTTYPE_SANDTYPE_TAOBAOBUFA);
		smsbo.setEntid(eorder.getEitem().getEnterprise().getId());
		return smsbo;
	}
	
}
