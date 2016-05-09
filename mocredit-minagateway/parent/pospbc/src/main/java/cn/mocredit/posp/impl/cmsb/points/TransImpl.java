package cn.mocredit.posp.impl.cmsb.points;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.m.mt.dao.SD_POINTS_INFO_Dao;
import cn.m.mt.dao.SD_POS_TERM_Dao;
import cn.m.mt.dao.SD_TERM_KEY_Dao;
import cn.m.mt.dao.SD_TRAN_LS_Dao;
import cn.mocredit.posp.bc.util.MessageObject;

/**
 * 民生交易分流
 * @author Administrator
 *
 */
@Service
@Transactional
public class TransImpl implements Trans {
	@Autowired
	SD_TRAN_LS_Dao SD_TRAN_LS_dao;
	@Autowired
	SD_TERM_KEY_Dao SD_TERM_KEY_dao;
	@Autowired
	SD_POS_TERM_Dao SD_POS_TERM_dao;
	@Autowired
	SD_POINTS_INFO_Dao SD_POINTS_INFO_dao;
	@Autowired
	PointsHandle handle;
//	PointsHandleImpl handle = new PointsHandleImpl();
	private static Logger logger = Logger.getLogger(Trans.class);
	/**
	 * 交易入口
	 */
	public MessageObject action(MessageObject request,String flag,MessageObject termRequest){
		MessageObject response = new MessageObject();
		String tranCode = "";
		/**
		 * 商户类型
		 * 1-costa
		 * 2-太平洋
		 */
//		String merchType = request.getTranCode();
		if(flag.equals("0")){
			if(request.getMesstype().equals("0700")||request.getMesstype().equals("0720")){
				tranCode=request.getMesstype()+request.getField03_Processing_Code().substring(0,2);
			}else {
				tranCode = request.getMesstype()+request.getField03_Processing_Code().substring(0,2)+
						request.getField25_Point_Of_Service_Condition_Mode()+
						request.getField60_Reserved_Private().substring(0,2);
			}
		} else if(flag.equals("1")){
			tranCode = request.getTranCode();
		}
		logger.info("request tranCode:["+tranCode+"]");
		if(null==tranCode||"".equals(tranCode)) {
			return response;
		} else {
			if("0200000422".equals(tranCode) || "0210000022".equals(tranCode))//积分扣减活动消费
				return handle.purchase(request,tranCode,termRequest);
			if("0200200423".equals(tranCode) || "0210200023".equals(tranCode))//积分扣减活动消费撤销
				return handle.revokePurchase(request,tranCode,termRequest);
			if("0400000422".equals(tranCode) || "0410000022".equals(tranCode))//积分扣减活动消费冲正0410000022
				return handle.reversalPurchase(request,tranCode,termRequest);
			if("0400000423".equals(tranCode) || "0410000023".equals(tranCode))//积分扣减活动消费撤销冲正（终端暂不支持）
				return handle.reversalRevokePurchase(request,tranCode,termRequest);
			if("070001".equals(tranCode) || "071001".equals(tranCode))//领奖
				return handle.prize(request, tranCode,termRequest);
			if("070000".equals(tranCode) || "071000".equals(tranCode))//领奖查询
				return handle.prizeQuery(request, tranCode,termRequest);
			if("072001".equals(tranCode) || "073001".equals(tranCode))//领奖冲正
				return handle.reversalPrize(request, tranCode,termRequest);
			if("090000".equals(tranCode) || "091000".equals(tranCode))//领奖签到
				return handle.prizeSignIn(request, tranCode);
			if("090002".equals(tranCode) || "091002".equals(tranCode))//领奖KEK下载
				return handle.prizeKekDown(request, tranCode);
			if("090001".equals(tranCode) || "091001".equals(tranCode))//领奖活动参数下载
				return handle.prizeDown(request, tranCode);
			if("092000".equals(tranCode) || "093000".equals(tranCode))//领奖结算
				return null;
			if("080000".equals(tranCode) || "0810001".equals(tranCode))//签到
				return handle.signIn(request, tranCode);
			if("0500201".equals(tranCode) || "0510201".equals(tranCode))//批结算
				return handle.batchSettlement(request, tranCode);
			if("082000002".equals(tranCode) || "0830002".equals(tranCode))//签退
				return handle.signOut(request, tranCode);
			if("082000384".equals(tranCode)||"0830384".equals(tranCode))//积分消费活动参数查询
				return handle.parameterQuery(request, tranCode);
			if("080000373".equals(tranCode)||"0810373".equals(tranCode))//积分消费活动参数查询
				return handle.parameterDown(request, tranCode);
			if("080000373".equals(tranCode)){
				return handle.activtyDown(request, tranCode);
			}
			else
				return response;
		}
	}
	public static void main(String[] args) {
	}
}
