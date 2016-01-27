package com.mocredit.verifyCode.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.mocredit.base.datastructure.impl.AjaxResponseData;
import com.mocredit.base.enums.ActActivityCodeOperType;
import com.mocredit.base.enums.ErrorCode;
import com.mocredit.base.enums.SysCodeOper;
import com.mocredit.base.util.DateUtil;
import com.mocredit.base.util.UUIDUtils;
import com.mocredit.log.task.VerifyCodeLogTask;
import com.mocredit.verifyCode.model.ActActivitySynLog;
import com.mocredit.verifyCode.model.TActivityCode;
import com.mocredit.verifyCode.model.TVerifiedCode;
import com.mocredit.verifyCode.model.TVerifyLog;
import com.mocredit.verifyCode.service.ActivityCodeService;
import com.mocredit.verifyCode.vo.ActActivityCodeVO;
import com.mocredit.verifyCode.vo.VerifyCodeVO;

/**
 * Created by YHL on 2015/7/7 13:58 .
 *
 * @author YHL
 * @version 1.0
 * @package com.mocredit.verifyCode.controller
 * @email yanghongli@mocredit.cn
 */
@RestController
@RequestMapping("/ActivityCode")
public class ActivityCodeController {

	private Logger logger = Logger.getLogger(ActivityCodeController.class.getName());

	@Autowired
	private ActivityCodeService activityCodeService;

	/** 查询券码 **/
	@ResponseBody
	@RequestMapping(value = "/get/{code}", produces = { "application/json;charset=UTF-8" })
	public String getActivityCodeDetail(HttpServletRequest request, HttpServletResponse response,
			@PathVariable(value = "code") String code) {
		AjaxResponseData ard = new AjaxResponseData();
		List<TActivityCode> list = new ArrayList<TActivityCode>();
		list = activityCodeService.findByCode(code);
		if (null != list && list.size() > 0) {
			ard.setSuccess(true);
			ard.setData(list);
		} else {
			ard.setSuccess(false);
			ard.setErrorCode(ErrorCode.CODE_15.getCode());
			ard.setErrorMsg("无法根据该码号找到对应记录!");
		}
		// System.out.println(JSON.toJSONString(ard));
		// 排除特殊字段
		// SimplePropertyPreFilter spfilter=new
		// SimplePropertyPreFilter(TActivityCode.class);
		// spfilter.getExcludes().add("effective");
		return JSON.toJSONStringWithDateFormat(ard, DateUtil.FORMAT_YYYYMMDD_HHMMSS);
	}

	/**
	 * 验码核销 注意：如果金额为Null 或者空，则说明消费的是定额的
	 * 
	 * <pre>
	 *     1.采用Restful的方式，POST JSON数据，提交类型为：Content-Type: application/json;charset=UTF-8
	 *     数据格式如下：
	 *     {
	        // "amount": 6,
	        // "useCount": 2,
	         "device": "acaaaa",
	         "requestSerialNumber": "111111",
	         "code": "ssssss"
	         }
	        2.响应数据：
	         {     "data":
	             {
	               // "remainTimes": 97,  //剩余次数
	                //"amount": 6,      //此次使用对应的金额
	                //"remainAmount": 568, //剩余金额
	                //"useCount": 3      //此次使用的次数
	             },
	             "errorCode": -1,
	             "errorMsg": "",    //错误信息
	             "success": true     //成功或者失败标示。 false-失败， true-成功
	         }
	 *
	 * 
	 * </pre>
	 **/
	@ResponseBody
	@RequestMapping(value = "/verifyCode", produces = { "application/json;charset=UTF-8" })
	public String verifyCode(HttpServletRequest request, HttpServletResponse response, @RequestBody String param) {
		AjaxResponseData ard = new AjaxResponseData();
		TVerifiedCode verifiedCode;
		try {
			verifiedCode = JSON.parseObject(param, TVerifiedCode.class);
		} catch (Exception e) {
			ard.setSuccess(false);
			ard.setErrorMsg("非法的请求参数格式！");
			ard.setErrorCode(ErrorCode.CODE_30.getCode());
			return JSON.toJSONString(ard);
		}
		// System.out.println(verifiedCode);
		// System.out.println(JSON.toJSONString(verifiedCode));
		// 排除金额为 空/NULL的时候，引起空指针异常问题
		if (null == verifiedCode.getAmount()) {
			verifiedCode.setAmount(new BigDecimal(0).setScale(2));
		}

		if (StringUtils.isEmpty(verifiedCode.getCode()) || verifiedCode.getCode().length() > 30
				|| StringUtils.isEmpty(verifiedCode.getDevice())
				|| StringUtils.isEmpty(verifiedCode.getRequestSerialNumber())) {
			ard.setSuccess(false);
			ard.setErrorCode(ErrorCode.CODE_30.getCode());
			ard.setErrorMsg("参数数据不合法!");
		} else {
			try {
				ard = this.activityCodeService.verifyCodeForNewPos(verifiedCode.getDevice(),
						verifiedCode.getRequestSerialNumber(), verifiedCode.getCode());

			} catch (Exception e) {
				ard.setSuccess(false);
				ard.setErrorMsg("请求过程发生事务异常!");
				ard.setErrorCode(ErrorCode.CODE_98.getCode());
				e.printStackTrace();
			}
			// 日志
			TVerifyLog log = this.buildVerifyLog(ard, verifiedCode);
			VerifyCodeLogTask.verifyogList.add(log);
		}

		// SimplePropertyPreFilter spfilter=new
		// SimplePropertyPreFilter(TActivityCode.class);
		// spfilter.getExcludes().add("effective");
		return JSON.toJSONStringWithDateFormat(ard, DateUtil.FORMAT_YYYYMMDD_HHMMSS);
	}

	/**
	 * 老机具验码
	 * 
	 * 请求参数格式 { code, device, batchNo, searchNo }
	 * 
	 * @param request
	 * @param response
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/verifyCodeForOld", produces = { "application/json;charset=UTF-8" })
	public String verifyCodeForOld(HttpServletRequest request, HttpServletResponse response, String code, String device,
			String batchNo, String searchNo) {
		String returnStr = null;
		AjaxResponseData ard = new AjaxResponseData();
		if (StringUtils.isEmpty(code) || code.length() > 30 || StringUtils.isEmpty(device)
				|| StringUtils.isEmpty(batchNo) || StringUtils.isEmpty(searchNo)) {
			ard.setSuccess(false);
			ard.setErrorMsg("参数数据不合法!");
			returnStr = "<?xml version='1.0' encoding='UTF-8'?><NewDataSet><Table><isSuccess>false</isSuccess><error>参数数据不合法!</error></Table></NewDataSet>";
		} else {
			try {
				returnStr = this.activityCodeService.verifyCodeForOldPos(batchNo, searchNo, device, code);
			} catch (Exception e) {
				returnStr = "<?xml version='1.0' encoding='UTF-8'?><NewDataSet><Table><isSuccess>false</isSuccess><error>请求过程发生事务异常!</error></Table></NewDataSet>";
				ard.setSuccess(false);
				ard.setErrorMsg("请求过程发生事务异常!");
				e.printStackTrace();
			}

			TVerifiedCode verifiedCode = new TVerifiedCode();
			verifiedCode.setBatchNo(batchNo);
			verifiedCode.setSearchNo(searchNo);
			verifiedCode.setDevice(device);
			verifiedCode.setCode(code);
			// 日志
			TVerifyLog log = this.buildVerifyLog(ard, verifiedCode);
			VerifyCodeLogTask.verifyogList.add(log);
		}

		// SimplePropertyPreFilter spfilter=new
		// SimplePropertyPreFilter(TActivityCode.class);
		// spfilter.getExcludes().add("effective");
		return returnStr;
	}

	/**
	 * 充值验码
	 * 
	 * 请求参数格式 { code,orderId,phone}
	 * 
	 * @param request
	 * @param response
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/verifyCodeForRecharge", produces = { "application/json;charset=UTF-8" })
	public String verifyCodeForRecharge(HttpServletRequest request, HttpServletResponse response, String code,
			String orderId, String phone) {
		Map<String, Object> returnMap = new HashMap<String, Object>();
		AjaxResponseData ard = new AjaxResponseData();
		if (StringUtils.isEmpty(code) || code.length() > 30 || StringUtils.isEmpty(orderId)
				|| StringUtils.isEmpty(phone)) {
			ard.setSuccess(false);
			ard.setErrorMsg("参数数据不合法!");
			returnMap.put("isSuccess", false);
			returnMap.put("errorMsg", "参数数据不合法!");
		} else {
			try {
				ard = this.activityCodeService.verifyCodeForRecharge(orderId, code, phone);
				returnMap = (Map<String, Object>) ard.getData();
			} catch (Exception e) {
				ard.setSuccess(false);
				ard.setErrorMsg("请求过程发生事务异常!");
				returnMap.put("isSuccess", false);
				returnMap.put("errorMsg", "请求过程发生事务异常!");
				e.printStackTrace();
			}

			TVerifiedCode verifiedCode = new TVerifiedCode();
			verifiedCode.setCode(code);
			verifiedCode.setRequestSerialNumber(orderId);
			// 日志
			TVerifyLog log = this.buildVerifyLog(ard, verifiedCode);
			VerifyCodeLogTask.verifyogList.add(log);
		}

		return JSON.toJSONString(returnMap);
	}

	/**
	 * 撤销
	 * 
	 * <pre>
	 *     采用RESTFUL的POST请求，请求数据格式为：
	 *     {
	         "requestSerialNumber":"123",
	         "device":"85746475"
	       }
	 *
	 * </pre>
	 **/
	@ResponseBody
	@RequestMapping(value = "/revokeActivityCode", produces = { "application/json;charset=UTF-8" })
	public String revokeActivityCode(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String param) {
		AjaxResponseData ard = new AjaxResponseData();
		// 判断请求的param是否为空，并且是一个json格式
		TVerifiedCode verifiedCode = null;
		try {
			verifiedCode = JSON.parseObject(param, TVerifiedCode.class);
		} catch (Exception e) {
			ard.setSuccess(false);
			ard.setErrorMsg("非法的请求参数格式！");
			ard.setErrorCode(ErrorCode.CODE_30.getCode());
			return JSON.toJSONString(ard);
		}
		// 判断是否传递了POS序列号与券码
		if (StringUtils.isEmpty(verifiedCode.getRequestSerialNumber())) {
			ard.setSuccess(false);
			ard.setErrorMsg("POS序列号或券码为空！");
			ard.setErrorCode(ErrorCode.CODE_15.getCode());
			return JSON.toJSONString(ard);
		}
		try {
			ard = this.activityCodeService.revokeForNewPos(verifiedCode.getRequestSerialNumber(),
					verifiedCode.getDevice());
		} catch (Exception e) {
			ard.setSuccess(false);
			ard.setErrorMsg("请求过程发生事务异常!");
			ard.setErrorCode(ErrorCode.CODE_30.getCode());
			e.printStackTrace();
		}
		return JSON.toJSONStringWithDateFormat(ard, DateUtil.FORMAT_YYYYMMDD_HHMMSS);
	}

	/**
	 * 老机具撤销 {batchNo,searchNo,device}
	 * 
	 * @param request
	 * @param response
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/revokeActivityCodeForOld", produces = { "application/json;charset=UTF-8" })
	public String revokeActivityCodeForOld(HttpServletRequest request, HttpServletResponse response, String batchNo,
			String searchNo, String device) {
		String returnStr = null;
		AjaxResponseData ard = new AjaxResponseData();
		// 判断是否传递了POS序列号与券码
		if (StringUtils.isEmpty(batchNo) || StringUtils.isEmpty(searchNo) || StringUtils.isEmpty(device)) {
			ard.setSuccess(false);
			ard.setErrorMsg("POS序列号或券码为空！");
			ard.setErrorCode(ErrorCode.CODE_15.getCode());
			return "<?xml version='1.0' encoding='UTF-8'?><NewDataSet><Table><isSuccess>false</isSuccess><error>参数数据不合法!</error></Table></NewDataSet>";
		}
		try {
			returnStr = this.activityCodeService.revokeForOldPos(batchNo, searchNo, device);
		} catch (Exception e) {
			returnStr = "<?xml version='1.0' encoding='UTF-8'?><NewDataSet><Table><isSuccess>false</isSuccess><error>请求过程发生事务异常!</error></Table></NewDataSet>";
			e.printStackTrace();
		}
		return returnStr;
	}

	/**
	 * 用于系统管理员操作码 <br />
	 * { <br />
	 * oper :1撤销 2延期 3码禁用 默认为撤销<br />
	 * requestSerialNumber 订单号 <br />
	 * device 机具号 <br />
	 * endTime 结束时间 <br />
	 * id 码ID <br />
	 * .............. <br />
	 * }
	 * 
	 * @param request
	 * @param response
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sysOperate", produces = { "application/json;charset=UTF-8" })
	public String revokeActivityCodeForSys(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String param) {
		AjaxResponseData ard = new AjaxResponseData();
		// 判断请求的param是否为空，并且是一个json格式
		TVerifiedCode verifiedCode = null;
		try {
			verifiedCode = JSON.parseObject(param, TVerifiedCode.class);
		} catch (Exception e) {
			ard.setSuccess(false);
			ard.setErrorMsg("非法的请求参数格式！");
			ard.setErrorCode(ErrorCode.CODE_30.getCode());
			return JSON.toJSONString(ard);
		}
		/* 判断操作类型 不同操作码走不同业务 */
		SysCodeOper oper = SysCodeOper.getEnumTypeByValue(verifiedCode.getOper());
		switch (null == oper ? SysCodeOper.DEFAULT : oper) {
		default:
		case REVOKE:
			// 判断是否传递了POS序列号与券码
			if (StringUtils.isEmpty(verifiedCode.getRequestSerialNumber())) {
				ard.setSuccess(false);
				ard.setErrorMsg("POS序列号或券码为空！");
				ard.setErrorCode(ErrorCode.CODE_30.getCode());
				return JSON.toJSONString(ard);
			}
			try {
				ard = this.activityCodeService.revokeForSys(verifiedCode.getRequestSerialNumber(),
						verifiedCode.getDevice());
			} catch (Exception e) {
				ard.setSuccess(false);
				ard.setErrorMsg("请求过程发生事务异常!");
				ard.setErrorCode(ErrorCode.CODE_30.getCode());
				e.printStackTrace();
			}
			break;
		case DELAY:
			// 判断是参数
			if (null == verifiedCode.getEndTime() || StringUtils.isEmpty(verifiedCode.getId())) {
				ard.setSuccess(false);
				ard.setErrorMsg("劵码或延期时间为空");
				ard.setErrorCode(ErrorCode.CODE_30.getCode());
				return JSON.toJSONString(ard);
			}
			try {
				ard = this.activityCodeService.delayForSys(verifiedCode.getEndTime(), verifiedCode.getId());
			} catch (Exception e) {
				ard.setSuccess(false);
				ard.setErrorMsg("请求过程发生事务异常!");
				ard.setErrorCode(ErrorCode.CODE_30.getCode());
				e.printStackTrace();
			}
			break;
		case DISABLE:
			// 判断是参数
			if (StringUtils.isEmpty(verifiedCode.getId())) {
				ard.setSuccess(false);
				ard.setErrorMsg("劵码为空");
				ard.setErrorCode(ErrorCode.CODE_30.getCode());
				return JSON.toJSONString(ard);
			}
			try {
				ard = this.activityCodeService.disableForSys(verifiedCode.getId());
			} catch (Exception e) {
				ard.setSuccess(false);
				ard.setErrorMsg("请求过程发生事务异常!");
				ard.setErrorCode(ErrorCode.CODE_30.getCode());
				e.printStackTrace();
			}
			break;
		}
		return JSON.toJSONStringWithDateFormat(ard, DateUtil.FORMAT_YYYYMMDD_HHMMSS);
	}

	/**
	 * 接口： 码导入
	 * 
	 * <pre>
	 *     调用接口需要采用restful格式，具体post过来的格式为：
	 *     <code>
	 *         {
	             "activityId": "11111",  //活动主键
	             "operType": "IMPORT",   //操作类型 ，参见 ActActivityCodeOperType
	             "actActivityStores": [  //存放活动-门店映射
	                 {
	                     "activityId": "3333333",  //活动主键
	                     "enterpriseId": "222222",  //活动所属企业的ID
	                     "shopId": "4444",          //门店
	                     "storeId": "11111"        //门店所属商户ID
	                     *****新增字段
	                     "shopName", 商户名
	                     "shopCode", 商户编码
	                     "storeName":"", 门店名
	                     "storeCode":"" 门店编码
	                 },
	                 {
	                     "activityId": "3333333",
	                     "enterpriseId": "222222",
	                     "shopId": "4444",
	                     "storeId": "11111"
	                 }
	             ],
	             "activityCodeList": [
	                 {
	                     "activityId": "00001",       //活动编号
	                     "activityName": "活动111",    //活动名称
	                     "amount": 20,                //券码的金额。 如果是按照金额计算，则存在金额。如果是按照次数，则金额为0
	                     "code": "bc1d507d2ec049e0b51ad5132d042a", //券码
	                     "codeSerialNumber": "200001012",  //券序列号
	                     "createTime": "2015-07-17 14:36:53", //创建时间
	                     "customMobile": "18899998888",  //手机号
	                     "endTime": "2015-07-17 14:36:53", //有效期结束时间
	                     "issueEnterpriseId": "0000001",  //发行商ID
	                     "issueEnterpriseName": "味多美",  //发行商名称
	                     "maxNum": 1,  //允许使用次数。 如果是按照金额计算的，则此处为一个比较大的数据，例如：大于登陆 65535
	                     "orderId": "HY039200002",    //订单号
	                     "releaseTime": "2015-07-17 14:36:53" , //发布时间
	                     "startTime": "2015-07-17 14:36:53",    //有效期开始时间
	                     "contractId": "HT0001"           //合同id
	                      *****新增字段
	                     "outCode", //外部编码
	                     "orderCode",//发码批次
	                     "enterpriseCode",//发行企业编码
	                     "customName"//客户名
	                     "activityCode" //活动编码
	                 },
	                ......
	             ]
	         }
	 *     </code>
	 * </pre>
	 * 
	 * @param request
	 * @param response
	 * @param param
	 *            采用restful post过来的JSON格式数据
	 * @return 返回一个json格式的 ajaxResponseData 对象数据。代表操作结果
	 */
	@ResponseBody
	@RequestMapping(value = "/importActActivityCode", produces = { "application/json;charset=UTF-8" })
	public String importActivityCode(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String param) {
		AjaxResponseData ard = new AjaxResponseData();

		// 判断请求的param是否为空，并且是一个json格式
		ActActivityCodeVO actActivityCodeVO = null;
		try {
			actActivityCodeVO = JSON.parseObject(param, ActActivityCodeVO.class);
		} catch (Exception e) {
			ard.setSuccess(false);
			ard.setErrorMsg("非法的请求参数格式！");
			ard.setErrorCode(ErrorCode.CODE_30.getCode());
			VerifyCodeLogTask.actActivitySynLogList.add(this.buildActActivitySynLog(ard, actActivityCodeVO));

			return JSON.toJSONString(ard);
		}

		// 判断请求类型
		if (actActivityCodeVO.getOperType() != ActActivityCodeOperType.IMPORT) {
			ard.setSuccess(false);
			ard.setErrorMsg("请求操作类型错误！");
			ard.setErrorCode(ErrorCode.CODE_30.getCode());
			VerifyCodeLogTask.actActivitySynLogList.add(this.buildActActivitySynLog(ard, actActivityCodeVO));

			return JSON.toJSONString(ard);
		}

		// 调用导入
		try {
			Date beginDate = new Date();
			ard = activityCodeService.importActActivityCodeWithTran(actActivityCodeVO);
			Date endDate = new Date();
			if (logger.isDebugEnabled()) {
				logger.debug("导入全码耗时:" + (endDate.getTime() - beginDate.getTime()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			ard.setSuccess(false);
			ard.setErrorMsg("请求过程发生事务异常!");
			ard.setErrorCode(ErrorCode.CODE_98.getCode());
		}
		VerifyCodeLogTask.actActivitySynLogList.add(this.buildActActivitySynLog(ard, actActivityCodeVO));

		return JSON.toJSONStringWithDateFormat(ard, DateUtil.FORMAT_YYYYMMDD_HHMMSS);
	}

	/**
	 * 删除活动券码（码删除）
	 * 
	 * <pre>
	 *     采用restful POST 一个JSON格式的参数，格式如下：
	       {
	         "activityId": "AC0001", //活动主键
	         "activityName":"活动001", //活动名称，冗余字段
	         "operType": "CANCEL"    //此次活动请求的操作类型：  IMPORT 为"码导入" ;UPDATE 为"活动更新" ;CANCEL 为"活动取消"
	       }
	 * </pre>
	 * 
	 * @param request
	 * @param response
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteActivity", produces = { "application/json;charset=UTF-8" })
	public String deleteActivity(HttpServletRequest request, HttpServletResponse response, @RequestBody String param) {
		AjaxResponseData ard = new AjaxResponseData();
		// 判断请求的param是否为空，并且是一个json格式
		ActActivityCodeVO actActivityCodeVO = null;
		try {
			actActivityCodeVO = JSON.parseObject(param, ActActivityCodeVO.class);
		} catch (Exception e) {
			ard.setSuccess(false);
			ard.setErrorMsg("非法的请求参数格式！");
			ard.setErrorCode(ErrorCode.CODE_30.getCode());
			VerifyCodeLogTask.actActivitySynLogList.add(this.buildActActivitySynLog(ard, actActivityCodeVO));
			return JSON.toJSONString(ard);
		}

		// 判断请求类型
		if (actActivityCodeVO.getOperType() != ActActivityCodeOperType.CANCEL) {
			ard.setSuccess(false);
			ard.setErrorMsg("请求操作类型错误！");
			ard.setErrorCode(ErrorCode.CODE_30.getCode());
			VerifyCodeLogTask.actActivitySynLogList.add(this.buildActActivitySynLog(ard, actActivityCodeVO));
			return JSON.toJSONString(ard);
		}

		// 判断是否有参数：活动ID
		if (StringUtils.isEmpty(actActivityCodeVO.getActivityId())) {
			ard.setSuccess(false);
			ard.setErrorMsg("活动ID为空!");
			ard.setErrorCode(ErrorCode.CODE_16.getCode());
			VerifyCodeLogTask.actActivitySynLogList.add(this.buildActActivitySynLog(ard, actActivityCodeVO));
			return JSON.toJSONString(ard);
		}
		try {
			ard = this.activityCodeService.deleteActActivityCodeWithTran(actActivityCodeVO.getActivityId());
		} catch (Exception e) {
			ard.setSuccess(false);
			ard.setErrorMsg("请求过程发生事务异常!");
			ard.setErrorCode(ErrorCode.CODE_98.getCode());
			logger.error(e.toString());
			e.printStackTrace();
		}
		VerifyCodeLogTask.actActivitySynLogList.add(this.buildActActivitySynLog(ard, actActivityCodeVO));

		return JSON.toJSONStringWithDateFormat(ard, DateUtil.FORMAT_YYYYMMDD_HHMMSS);
	}

	/**
	 * 更新活动信息。 根据活动信息，更新对应的信息。
	 * 
	 * <pre>
	 *     采用Restful POST 发送JSON格式参数的方式
	 *     请求数据格式如下：
	 *     {
	         "actActivityStores": [  //存放适用门店列表
	             {
	                 "activityId": "0001",   //活动ID
	                 "enterpriseId": "QY0001",//发行企业ID ，冗余字段
	                 "shopId": "SHOP001",    //商户ID  ，冗余字段
	                 "storeId": "STORE001"    //门店ID
	                 *****新增字段
	                 "shopName", 商户名
	                 "shopCode", 商户编码
	                 "storeName":"", 门店名
	                 "storeCode":"" 门店编码
	             }
	         ],
	         "activityId": "0001",  //活动ID
	         "activityName": "HD001",  //活动名称 ，冗余字段
	         "operType": "UPDATE", //操作方式 。IMPORT("码导入",1), UPDATE("活动更新",2), CANCEL("活动取消",3)
	         "amount": 128,      //价格
	         "contractId": "HT0001",  //合同号
	         "enterpriseId": "QY0001", //发行企业ID
	         "enterpriseName": "企业1", //发行企业名称，冗余字段
	         "selectDate": "6,7" //选择的日期
	         *****新增字段
	         "outCode", //外部编码
	         "enterpriseCode",//发行企业编码
	         "activityCode" //活动编号
	         }
	 *
	 * 
	 * </pre>
	 * 
	 * @param request
	 * @param response
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/updateActivity", produces = { "application/json;charset=UTF-8" })
	public String updateActivity(HttpServletRequest request, HttpServletResponse response, @RequestBody String param) {
		AjaxResponseData ard = new AjaxResponseData();

		ActActivityCodeVO actActivityCodeVO = null;
		try {
			actActivityCodeVO = JSON.parseObject(param, ActActivityCodeVO.class);
		} catch (Exception e) {
			ard.setSuccess(false);
			ard.setErrorMsg("非法的请求参数格式！");
			ard.setErrorCode(ErrorCode.CODE_30.getCode());
			VerifyCodeLogTask.actActivitySynLogList.add(this.buildActActivitySynLog(ard, actActivityCodeVO));
			return JSON.toJSONString(ard);
		}

		// 判断请求类型
		if (actActivityCodeVO.getOperType() != ActActivityCodeOperType.UPDATE) {
			ard.setSuccess(false);
			ard.setErrorMsg("请求操作类型错误！");
			ard.setErrorCode(ErrorCode.CODE_30.getCode());
			VerifyCodeLogTask.actActivitySynLogList.add(this.buildActActivitySynLog(ard, actActivityCodeVO));
			return JSON.toJSONString(ard);
		}

		// 判断是否有参数：活动ID
		if (StringUtils.isEmpty(actActivityCodeVO.getActivityId())) {
			ard.setSuccess(false);
			ard.setErrorMsg("活动ID为空!");
			ard.setErrorCode(ErrorCode.CODE_16.getCode());
			VerifyCodeLogTask.actActivitySynLogList.add(this.buildActActivitySynLog(ard, actActivityCodeVO));
			return JSON.toJSONString(ard);
		}
		try {
			ard = this.activityCodeService.updateActActivityWithTran(actActivityCodeVO);
		} catch (Exception e) {
			ard.setSuccess(false);
			ard.setErrorMsg("请求过程发生事务异常!");
			ard.setErrorCode(ErrorCode.CODE_98.getCode());
			logger.error(e.toString());
			e.printStackTrace();
		}
		VerifyCodeLogTask.actActivitySynLogList.add(this.buildActActivitySynLog(ard, actActivityCodeVO));

		String json = JSON.toJSONStringWithDateFormat(ard, DateUtil.FORMAT_YYYYMMDD_HHMMSS);
		return json;
	}

	/**
	 * 启用活动。
	 * 
	 * <pre>
	  采用Restful POST 发送JSON格式参数的方式
	  请求数据格式如下：：
	 {
	    "activityId": "AC0001", //活动主键
	    "activityName":"活动001", //活动名称，冗余字段
	    "operType": "START_USING"  START_USING为启用
	 }
	 * 
	 * </pre>
	 * 
	 * @param request
	 * @param response
	 * @param param
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/startUsingActivity", produces = { "application/json;charset=UTF-8" })
	public String startUsingActivity(HttpServletRequest request, HttpServletResponse response,
			@RequestBody String param) {
		AjaxResponseData ard = new AjaxResponseData();

		ActActivityCodeVO actActivityCodeVO = null;
		try {
			actActivityCodeVO = JSON.parseObject(param, ActActivityCodeVO.class);
		} catch (Exception e) {
			ard.setSuccess(false);
			ard.setErrorMsg("非法的请求参数格式！");
			ard.setErrorCode(ErrorCode.CODE_30.getCode());
			VerifyCodeLogTask.actActivitySynLogList.add(this.buildActActivitySynLog(ard, actActivityCodeVO));
			return JSON.toJSONString(ard);
		}

		// 判断请求类型
		if (actActivityCodeVO.getOperType() != ActActivityCodeOperType.START_USING) {
			ard.setSuccess(false);
			ard.setErrorMsg("请求操作类型错误！");
			ard.setErrorCode(ErrorCode.CODE_30.getCode());
			VerifyCodeLogTask.actActivitySynLogList.add(this.buildActActivitySynLog(ard, actActivityCodeVO));
			return JSON.toJSONString(ard);
		}

		// 判断是否有参数：活动ID
		if (null == actActivityCodeVO.getActivityId() || actActivityCodeVO.getActivityId().trim().equals("")) {
			ard.setSuccess(false);
			ard.setErrorMsg("活动ID为空!");
			ard.setErrorCode(ErrorCode.CODE_16.getCode());
			VerifyCodeLogTask.actActivitySynLogList.add(this.buildActActivitySynLog(ard, actActivityCodeVO));
			return JSON.toJSONString(ard);
		}
		try {
			ard = this.activityCodeService.startUsingActivityWithTran(actActivityCodeVO.getActivityId());
		} catch (Exception e) {
			ard.setSuccess(false);
			ard.setErrorMsg("请求过程发生事务异常!");
			ard.setErrorCode(ErrorCode.CODE_98.getCode());
			logger.error(e.toString());
			e.printStackTrace();
		}
		VerifyCodeLogTask.actActivitySynLogList.add(this.buildActActivitySynLog(ard, actActivityCodeVO));

		return JSON.toJSONStringWithDateFormat(ard, DateUtil.FORMAT_YYYYMMDD_HHMMSS);
	}

	/**
	 * 查询码订单
	 * 
	 * @return
	 */
	@RequestMapping(value = "getCodePage", produces = { "application/json;charset=UTF-8" })
	public String getPage(@RequestBody String body) {
		AjaxResponseData ard = new AjaxResponseData();
		try {
			VerifyCodeVO verifyCode = JSON.parseObject(body, VerifyCodeVO.class);
			if (null == verifyCode.getPageNum() || 0 == verifyCode.getPageNum()) {
				verifyCode.setPageNum(1);
			}
			if (null == verifyCode.getPageSize() || 0 == verifyCode.getPageSize()) {
				verifyCode.setPageSize(15);
			}
			ard.setData(activityCodeService.getCodeList(verifyCode, verifyCode.getPageSize(), verifyCode.getPageNum()));
		} catch (Exception e) {
			ard.setSuccess(false);
			ard.setErrorMsg("请求时发生异常！");
			logger.error("getCodePage发生异常：" + e.getMessage());
			e.printStackTrace();
		}
		return JSON.toJSONString(ard);
	}

	/**
	 * 查询码信息
	 * 
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "checkCode", produces = { "application/json;charset=UTF-8" })
	public String checkCode(@RequestBody String param) {
		AjaxResponseData ard = new AjaxResponseData();
		//返回结果
		Map<String, Object> retMap=new HashMap<String, Object>();
		TVerifiedCode verifiedCode;
		try {
			verifiedCode = JSON.parseObject(param, TVerifiedCode.class);
		} catch (Exception e) {
			retMap.put("amt", "0");
			retMap.put("avaliable", "1");
			retMap.put("errMsg", "非法的请求参数格式！");
			return JSON.toJSONString(retMap);
		}
		// System.out.println(verifiedCode);
		// System.out.println(JSON.toJSONString(verifiedCode));
		// 排除金额为 空/NULL的时候，引起空指针异常问题
		if (null == verifiedCode.getAmount()) {
			verifiedCode.setAmount(new BigDecimal(0).setScale(2));
		}

		if (StringUtils.isEmpty(verifiedCode.getCode()) || verifiedCode.getCode().length() > 30
				|| StringUtils.isEmpty(verifiedCode.getDevice())) {
			ard.setSuccess(false);
			ard.setErrorCode(ErrorCode.CODE_30.getCode());
			ard.setErrorMsg("参数数据不合法!");
		} else {
			try {
				ard = this.activityCodeService.checkCode(verifiedCode.getDevice(), verifiedCode.getCode());
			} catch (Exception e) {
				ard.setSuccess(false);
				ard.setErrorMsg("请求过程发生事务异常!");
				ard.setErrorCode(ErrorCode.CODE_98.getCode());
				e.printStackTrace();
			}
			if (ard.isSuccess()) {
				TActivityCode code = (TActivityCode) ard.getData();
				retMap.put("amt", code.getAmount());
				retMap.put("avaliable", "0");
				retMap.put("codeName", code.getActivityName());
				retMap.put("errMsg", "");
			}else{
				retMap.put("amt", "0");
				retMap.put("avaliable", "1");
				retMap.put("codeName", "");
				retMap.put("errMsg", ard.getErrorMsg());
			}
			// 日志
			TVerifyLog log = this.buildVerifyLog(ard, verifiedCode);
			VerifyCodeLogTask.verifyogList.add(log);
		}

		// SimplePropertyPreFilter spfilter=new
		// SimplePropertyPreFilter(TActivityCode.class);
		// spfilter.getExcludes().add("effective");
		return JSON.toJSONString(retMap);
	}

	/**
	 * 私有方法：构造验码日志
	 * 
	 * @param ard
	 *            验码返回的结果封装数据 验码成功后，需要将 券码对象放入 redundancyData 属性中，作为冗余数据使用
	 * @param verifiedCode
	 *            请求的封装数据
	 * @return
	 */
	private TVerifyLog buildVerifyLog(AjaxResponseData ard, TVerifiedCode verifiedCode) {
		TVerifyLog log = new TVerifyLog();

		log.setId(UUIDUtils.UUID32());
		if (ard.isSuccess()) {
			TActivityCode vObj = (TActivityCode) ard.getRedundancyData();
			log.setSuccess(true);
			log.setCode(verifiedCode.getCode());
			log.setMessage(ard.getErrorMsg());
			log.setErrorCode(String.valueOf(ard.getErrorCode()));
			log.setVerifyTime(new Date());
			log.setCodeSerialNumber(verifiedCode.getRequestSerialNumber());
			log.setDevice(verifiedCode.getDevice());
			log.setStoreId(verifiedCode.getStoreId());
			log.setStoreName(verifiedCode.getStoreName());

			if (null != vObj) {
				log.setActivityId(vObj.getActivityId());
				log.setActivityName(vObj.getActivityName());
			}

		} else {
			log.setSuccess(false);
			log.setCode(verifiedCode.getCode());
			log.setMessage(ard.getErrorMsg());
			log.setErrorCode(String.valueOf(ard.getErrorCode()));
			log.setVerifyTime(new Date());
			log.setCodeSerialNumber(verifiedCode.getRequestSerialNumber());
			log.setDevice(verifiedCode.getDevice());
			log.setStoreId(verifiedCode.getStoreId());
			log.setStoreName(verifiedCode.getStoreName());
		}

		return log;
	}

	/**
	 * 构造活动同步日志对象。
	 * 
	 * @param ard
	 * @param actActivityCodeVO
	 * @return
	 */
	private ActActivitySynLog buildActActivitySynLog(AjaxResponseData ard, ActActivityCodeVO actActivityCodeVO) {
		ActActivitySynLog actActivitySynLog = new ActActivitySynLog();

		actActivitySynLog.setId(UUIDUtils.UUID32());
		actActivitySynLog.setActivityId(null == actActivityCodeVO ? "" : actActivityCodeVO.getActivityId());
		actActivitySynLog.setActivityName(null == actActivityCodeVO ? "" : actActivityCodeVO.getActivityName());
		actActivitySynLog.setOperType(null == actActivityCodeVO ? null : actActivityCodeVO.getOperType());
		actActivitySynLog.setOperTime(new Date());

		if (ard.isSuccess()) {
			actActivitySynLog.setSuccess(ard.getSuccess());
			if (null != actActivityCodeVO && actActivityCodeVO.getOperType() == ActActivityCodeOperType.IMPORT) {
				actActivitySynLog.setSynNum(actActivityCodeVO.getActivityCodeList().size());
			}
		} else {
			actActivitySynLog.setSuccess(ard.getSuccess());
			actActivitySynLog.setErrorCode(String.valueOf(ard.getErrorCode()));
			actActivitySynLog.setMessage(ard.getErrorMsg());
		}

		return actActivitySynLog;
	}

	public static String getRemoteIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static void main(String[] args) {
		char[] c = new char[100];
		c[0] = 'a';
		c[1] = 'b';
		System.out.println(new String(c));
	}
}
