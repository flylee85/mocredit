package cn.mocredit.posp.impl.cmsb.points;

import cn.mocredit.posp.bc.util.MessageObject;

/**
 * 
 * @author Superdo
 *
 */
public interface PointsHandle {
	public MessageObject signIn(MessageObject request,String trancode);
	public MessageObject signOut(MessageObject request,String trancode);
	public MessageObject activtyDown(MessageObject request,String trancode);
	public MessageObject purchase(MessageObject request,String trancode,MessageObject termRequest);
	public MessageObject revokePurchase(MessageObject request,String trancode,MessageObject termRequest);
	public MessageObject reversalPurchase(MessageObject request,String trancode,MessageObject termRequest);
	public MessageObject reversalRevokePurchase(MessageObject request,String trancode,MessageObject termRequest);
	/**
	 * 领奖
	 * @param request
	 * @param trancode
	 * @return
	 */
	public MessageObject prize(MessageObject request,String trancode,MessageObject termRequest);
	/**
	 * 领奖冲正
	 * @param request
	 * @param trancode
	 * @return
	 */
	public MessageObject reversalPrize(MessageObject request,String trancode,MessageObject termRequest);
	/**
	 * 领奖签到
	 * @param request
	 * @param trancode
	 * @return
	 */
	public MessageObject prizeSignIn(MessageObject request,String trancode);
	/**
	 * 
	 * @param request
	 * @param trancode
	 * @return
	 */
	public MessageObject prizeSignOut(MessageObject request,String trancode);
	
	/**
	 * 领奖KEK下载
	 * @param request
	 * @param trancode
	 * @return
	 */
	public MessageObject prizeKekDown(MessageObject request,String trancode);
	/**
	 * 领奖活动参数下载
	 * @param request
	 * @param trancode
	 * @return
	 */
	public MessageObject prizeDown(MessageObject request,String trancode);
	public MessageObject batchSettlement(MessageObject request,String trancode);
	public MessageObject parameterQuery(MessageObject request,String tranCode);
	public MessageObject parameterDown(MessageObject request,String tranCode);
	public MessageObject prizeQuery(MessageObject request,String tranCode,MessageObject termRequest);
}
