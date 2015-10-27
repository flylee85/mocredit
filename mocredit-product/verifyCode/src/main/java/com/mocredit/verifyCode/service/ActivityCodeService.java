package com.mocredit.verifyCode.service;

import java.util.List;

import com.mocredit.base.datastructure.impl.AjaxResponseData;
import com.mocredit.base.enums.VerifyCodeStatus;
import com.mocredit.verifyCode.model.TActivityCode;
import com.mocredit.verifyCode.vo.ActActivityCodeVO;

/**
 *
 * Created by YHL on 2015/7/7 11:58 .
 * @author YHL
 * @email yanghongli@mocredit.cn
 * @version 1.0
 */
public interface ActivityCodeService {

    /**
     * 保存券码对象
     * @param activityCode 券码对象
     * @return
     *      return true ;保存成功
     *      return false; 保存失败
     */
    public boolean save(TActivityCode activityCode);

    /**
     * 根据券码查询对象
     * @param code 券码
     * @return
     *      return List 返回根据券码查询到的对象列表
     */
    public List<TActivityCode> findByCode(String code);

    /**
     *  验码核销
     *  <pre>
     *      验码核销采用如下原则：
     *      1.如果券码对象中的 max_num字段是一个很大的数 @Constant ，则认为是不限制使用次数。
     *          则，插入一条验码记录，并且保存金额等
     *      2.每次验码核销，需要记录一次使用记录以及请求使用的金额。
     *      3.判断码使用次数或者金额的时候，需要处理冲正与撤销的记录。（可用次数与金额）
     *      4.校验 券码是否适用此门店的时候，优先通过门店编码校验
     *  </pre>
     * @param device   机具ID
     * @param store_id 兑换的门店ID
     * @param store_code 门店编码
     * @param store_name 兑换的门店名称
     * @param request_serial_number POS请求的序列号
     * @param shop_id 商户 ID
     * @param shop_name 商户名称
     * @param code                   券码
     * @return
     */
    public AjaxResponseData useActivityCodeWithTran(
                                                    String device,
                                                    String store_id,
                                                    String store_code,
                                                    String store_name,
                                                    String request_serial_number,
                                                    String shop_id,
                                                    String shop_name,
                                                    String code);

    /**
     *
     * 冲正 或者撤销 某个券码
     * @modify: 2015-08-17 根据需求，修改冲正或撤销的时候，采用参数：流水号、机具号、当天日期
     *
     * @param request_serial_number  POS序列号
     * @param code                   券码
     * @param device                 机具号
     * @param verifyCodeStatus       验码记录状态 这里接受2种类型：冲正和撤销   参见<code>VerifyCodeStatus</code>
     * @return
     */
    public AjaxResponseData correctActivityCodeWithTran(String request_serial_number, String code, String device , VerifyCodeStatus verifyCodeStatus);


    /**
     * 解析导入数据，放入码表中。
     * @param actActivityCodeVO
     * @return
     */
    public AjaxResponseData importActActivityCodeWithTran(ActActivityCodeVO actActivityCodeVO);

    /**
     * 根据活动ID，删除该活动下地券码
     * @param activity_id
     * @return
     */
    public AjaxResponseData deleteActActivityCodeWithTran(String activity_id);

    /**
     * 更新活动：
     * <pre>
     *     由活动管理模块发起，调用验码模块的更新活动接口，主要更新说明如下：
     *     1.如果活动变更了 如下内容，需要更新验码表
     *          活动发行商、 合同、价格、开始时间、结束时间、选择日期、最大次数、
     *     2.每次更新活动的时候，会删除 原先表中存放的此活动对应的门店，重新根据传入的门店列表，保存数据。用于进行验码操作的依据（适用门店）

     * </pre>
     * @param actActivityCodeVO
     * @return
     */
    public AjaxResponseData updateActActivityWithTran(ActActivityCodeVO actActivityCodeVO);

    /**
     * 启用活动（活动下的券码）
     * @param activity_id
     * @return
     */
    public AjaxResponseData startUsingActivityWithTran(String activity_id);

}
