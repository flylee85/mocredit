package com.mocredit.integral.service;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.mocredit.integral.constant.ErrorCodeType;
import com.mocredit.integral.constant.OrderStatus;
import com.mocredit.integral.dto.PaymentDto;
import com.mocredit.integral.entity.Activity;
import com.mocredit.integral.entity.ActivityTransRecord;
import com.mocredit.integral.entity.Order;
import com.mocredit.integral.entity.OutRequestLog;
import com.mocredit.integral.entity.OutResponseLog;
import com.mocredit.integral.entity.Response;
import com.mocredit.integral.entity.ResponseData;
import com.mocredit.integral.entity.Store;
import com.mocredit.integral.util.DateTimeUtils;
import com.mocredit.integral.util.HttpRequestUtil;
import com.mocredit.integral.vo.ConfirmInfoVo;

@Service
public class HttpRequestService extends LogService {
	@Autowired
	private OutRequestLogService outRequestLogService;
	@Autowired
	private OutResponseLogService outResponseLogService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private ActivityService activityService;
	ObjectMapper objectMapper = new ObjectMapper();

	public boolean doGetAndSaveOrder(Integer requestId, String url, Order order) {
		String response = doGet(requestId, url);
		// TODO 解析reponse 如果成功就保存order

		return true;
	}

	public Integer getActTRCount(String maxType) {
		// 暂定01代表每日，02代表每周，03代表每月，空代表不限制',
		Integer count = null;
		switch (maxType) {
		case "01":
			Date now = new Date();
			ActivityTransRecord atr01 = activityService.statCountByTime(now,
					now);
			if (atr01 != null) {
				count = atr01.getTransCount();
			} else {
				count = 0;
			}

			break;
		case "02":
			Calendar cal02S = Calendar.getInstance();
			cal02S.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			Calendar cal02E = Calendar.getInstance();
			cal02E.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			cal02E.add(Calendar.DATE, 6);
			ActivityTransRecord atr02 = activityService.statCountByTime(
					cal02S.getTime(), cal02E.getTime());
			if (atr02 != null) {
				count = atr02.getTransCount();
			} else {
				count = 0;
			}
			break;
		case "03":
			Calendar cal03S = Calendar.getInstance();
			cal03S.set(Calendar.DAY_OF_MONTH, 1);

			Calendar cal03E = Calendar.getInstance();
			cal03E.set(Calendar.DAY_OF_MONTH,
					cal03E.getActualMaximum(Calendar.DAY_OF_MONTH));

			ActivityTransRecord atr03 = activityService.statCountByTime(
					cal03S.getTime(), cal03E.getTime());
			if (atr03 != null) {
				count = atr03.getTransCount();
			} else {
				count = 0;
			}
			break;
		}
		return count;
	}

	/**
	 * post json and save order
	 * 
	 * @param requestId
	 * @param url
	 * @param param
	 * @param order
	 * @param resp
	 * @return
	 */
	public boolean doPostJsonAndSaveOrder(Integer requestId, String url,
			String param, Order order, Response resp) {
		try {
			Activity activity = activityService.getByActivityId(order
					.getActivityId());
			if (activity != null) {

				// 验证该商户，该门店是否能参加该活动
				Store store = activityService.getByShopIdStoreIdAcId(
						order.getShopId() + "", order.getStoreId(),
						order.getActivityId());
				if (store == null) {
					resp.setErrorCode(ErrorCodeType.ACTIVITY_NOT_EXIST_SHOP_STORE
							.getValue());
					resp.setErrorMsg(ErrorCodeType.ACTIVITY_NOT_EXIST_SHOP_STORE
							.getText());
					return false;
				}

				order.setActivityName(activity.getActivityName());
				Date now = new Date();
				// 1，先判断当前时间在活动时间范围内
				// 2，再判断当前时间的所属的星期范围内
				if (activity.getStartTime().getTime() <= now.getTime()
						&& now.getTime() <= activity.getEndTime().getTime()
						&& (activity.getSelectDate().contains(DateTimeUtils
								.getWeekOfDate(now)))) {
				} else {
					resp.setErrorCode(ErrorCodeType.ACTIVITY_OUT_DATE
							.getValue());
					resp.setErrorMsg(ErrorCodeType.ACTIVITY_OUT_DATE.getText());
					return false;
				}
				// 3,判断使用次数是否超过最大使用次数限制
				if (activity.getMaxType() != null
						&& !"".equals(activity.getMaxType())) {
					// 4,判断最大类型参数是否正确并返回当前类型目前消费次数
					Integer count = getActTRCount(activity.getMaxType());
					if (null == count) {
						resp.setErrorCode(ErrorCodeType.PARAM_ERROR.getValue());
						resp.setErrorMsg(ErrorCodeType.PARAM_ERROR.getText());
						return false;
					}
					if (activity.getMaxNumber() != null
							&& activity.getMaxNumber() < count) {
						resp.setErrorCode(ErrorCodeType.ACTIVITY_OUT_COUNT
								.getValue());
						resp.setErrorMsg(ErrorCodeType.ACTIVITY_OUT_COUNT
								.getText());
						return false;
					}
				}
				// 5,条件判断订单是否已经存在
				if (orderService.isExistOrder(order.getDevice(),
						order.getOrderId(), DateTimeUtils.getDate("yyyyMMdd"))) {
					resp.setErrorCode(ErrorCodeType.EXIST_ORDER_ERROR
							.getValue());
					resp.setErrorMsg(ErrorCodeType.EXIST_ORDER_ERROR.getText());
					return false;
				}
			} else {
				resp.setErrorCode(ErrorCodeType.NOT_EXIST_ACTIVITY_ERROR
						.getValue());
				resp.setErrorMsg(ErrorCodeType.NOT_EXIST_ACTIVITY_ERROR
						.getText());
				return false;
			}
			String response = doPostJson(requestId, url,
					getPaymentDto(activity, order));
			boolean anaFlag = analyJsonReponse(requestId, url, param, response,
					resp);
			if (anaFlag) {
				// 设置订单reuestId和交易完成状态
				order.setTransDate(DateTimeUtils.getDate("yyyyMMdd"));
				order.setRequestId(requestId);
				order.setStatus(OrderStatus.FINISH.getValue());
				if (!orderService.save(order)) {
					resp.setErrorCode(ErrorCodeType.SAVE_DATEBASE_ERROR
							.getValue());
					resp.setErrorMsg(ErrorCodeType.SAVE_DATEBASE_ERROR
							.getText());
					return false;
				} else {
					return true;
				}
			} else {
				return anaFlag;
			}
		} catch (Exception e) {
			resp.setErrorCode(ErrorCodeType.SYSTEM_ERROR.getValue());
			resp.setErrorMsg(ErrorCodeType.SYSTEM_ERROR.getText());
			LOGGER.error(
					"### doPostJsonAndSaveOrder url={}, requestId={},param={}, error={}",
					url, requestId, param, e);
			return false;
		}
	}

	/**
	 * 构建请求payment接口参数
	 * 
	 * @param activity
	 * @param order
	 * @return
	 */
	private String getPaymentDto(Activity activity, Order order) {
		PaymentDto paymentDto = new PaymentDto();
		paymentDto.setProductType(activity.getProductType());
		paymentDto.setCardNum(order.getCardNum() + "");
		paymentDto.setDevice(order.getDevice());
		paymentDto.setTransAmt(order.getAmount() + "");
		paymentDto.setShopId(order.getShopId());
		return JSON.toJSONString(paymentDto);
	}

	public boolean doPostAndSaveOrder(Integer requestId, String url,
			Map<?, ?> paramMap, Order order, Response resp) {
		try {
			Activity activity = activityService.getByActivityId(order
					.getActivityId());
			if (activity != null) {
				order.setActivityName(activity.getActivityName());
				Date now = new Date();
				// 1，先判断当前时间在活动时间范围内
				// 2，再判断当前时间的所属的星期范围内
				if (activity.getStartTime().getTime() <= now.getTime()
						&& now.getTime() <= activity.getEndTime().getTime()
						&& (activity.getSelectDate().contains(DateTimeUtils
								.getWeekOfDate(now)))) {
				} else {
					resp.setErrorCode(ErrorCodeType.ACTIVITY_OUT_DATE
							.getValue());
					resp.setErrorMsg(ErrorCodeType.ACTIVITY_OUT_DATE.getText());
					return false;
				}
				// 3,判断使用次数是否超过最大使用次数限制
				if (activity.getMaxType() != null
						&& !"".equals(activity.getMaxType())) {
					if (activity.getMaxNumber() != null
							&& activity.getMaxNumber() < getActTRCount(activity
									.getMaxType())) {
						resp.setErrorCode(ErrorCodeType.ACTIVITY_OUT_COUNT
								.getValue());
						resp.setErrorMsg(ErrorCodeType.ACTIVITY_OUT_COUNT
								.getText());
						return false;
					}
				}
			} else {
				resp.setErrorCode(ErrorCodeType.NOT_EXIST_ACTIVITY_ERROR
						.getValue());
				resp.setErrorMsg(ErrorCodeType.NOT_EXIST_ACTIVITY_ERROR
						.getText());
				return false;
			}
			String response = doPost(requestId, url, paramMap);
			boolean anaFlag = analyReponse(requestId, url, paramMap, response,
					resp);
			if (anaFlag) {
				// 设置订单reuestId和交易完成状态
				order.setRequestId(requestId);
				order.setStatus(OrderStatus.FINISH.getValue());
				if (!orderService.save(order)) {
					resp.setErrorCode(ErrorCodeType.SAVE_DATEBASE_ERROR
							.getValue());
					resp.setErrorMsg(ErrorCodeType.SAVE_DATEBASE_ERROR
							.getText());
					return false;
				} else {
					return true;
				}
			} else {
				return anaFlag;
			}
		} catch (Exception e) {
			resp.setErrorCode(ErrorCodeType.SYSTEM_ERROR.getValue());
			resp.setErrorMsg(ErrorCodeType.SYSTEM_ERROR.getText());
			LOGGER.error(
					"### doPostAndSaveOrder url={}, requestId={},parms={}, error={}",
					url, requestId, paramMap.toString(), e);
			return false;
		}
	}

	public boolean analyJsonReponse(Integer requestId, String url,
			String param, String reponse, Response resp) {
		if (reponse == null) {
			resp.setErrorCode(ErrorCodeType.POST_BANK_ERROR.getValue());
			resp.setErrorMsg(ErrorCodeType.POST_BANK_ERROR.getText());
			return false;
		}
		try {
			ResponseData responseData = JSON.parseObject(reponse,
					ResponseData.class);
			resp.setData(responseData.getData());
			resp.setErrorCode(responseData.getErrorCode());
			resp.setErrorMsg(responseData.getErrorMsg());
			resp.setSuccess(responseData.getSuccess());
			return responseData.getSuccess();
		} catch (Exception e) {
			resp.setErrorCode(ErrorCodeType.ANA_RESPONSE_ERROR.getValue());
			resp.setErrorMsg(ErrorCodeType.ANA_RESPONSE_ERROR.getText());
			LOGGER.error("### doPost url={}, requestId={},param={}, error={}",
					url, requestId, param, e);
			return false;
		}
	}

	public boolean analyReponse(Integer requestId, String url,
			Map<?, ?> paramMap, String reponse, Response resp) {
		if (reponse == null) {
			resp.setErrorCode(ErrorCodeType.POST_BANK_ERROR.getValue());
			return false;
		}
		try {
			return true;
		} catch (Exception e) {
			resp.setErrorCode(ErrorCodeType.ANA_RESPONSE_ERROR.getValue());
			LOGGER.error("### doPost url={}, requestId={},parms={}, error={}",
					url, requestId, paramMap.toString(), e);
			return false;
		}
	}

	/**
	 * 积分消费撤销
	 * 
	 * @param requestId
	 * @param url
	 * @param paramMap
	 * @param resp
	 * @return
	 */
	public boolean paymentRevoke(Integer requestId, String url,
			Map<?, ?> paramMap, Response resp) {
		try {
			String response = doPost(requestId, url, paramMap);
			boolean anaFlag = analyReponse(requestId, url, paramMap, response,
					resp);
			if (anaFlag) {
				if (!orderService.isExistOrderAndUpdate(paramMap.get("device")
						+ "", paramMap.get("orderId") + "")) {
					resp.setErrorCode(ErrorCodeType.SAVE_DATEBASE_ERROR
							.getValue());
					return false;
				} else {
					return true;
				}
			} else {
				return anaFlag;
			}
		} catch (Exception e) {
			resp.setErrorCode(ErrorCodeType.SYSTEM_ERROR.getValue());
			LOGGER.error(
					"### paymentRevoke url={}, requestId={},parms={}, error={}",
					url, requestId, paramMap.toString(), e);
			return false;
		}
	}

	/**
	 * 积分消费撤销
	 * 
	 * @param requestId
	 * @param url
	 * @param paramMap
	 * @param resp
	 * @return
	 */
	public boolean paymentRevokeJson(Integer requestId, String url,
			String param, String device, String orderId, Response resp) {
		try {
			String response = doPostJson(requestId, url, param);
			boolean anaFlag = analyJsonReponse(requestId, url, param, response,
					resp);
			if (anaFlag) {
				if (!orderService.isExistOrderAndUpdate(device, orderId)) {
					resp.setErrorCode(ErrorCodeType.SAVE_DATEBASE_ERROR
							.getValue());
					resp.setErrorMsg(ErrorCodeType.SAVE_DATEBASE_ERROR
							.getText());
					return false;
				} else {
					return true;
				}
			} else {
				return anaFlag;
			}
		} catch (Exception e) {
			resp.setErrorCode(ErrorCodeType.SYSTEM_ERROR.getValue());
			resp.setErrorMsg(ErrorCodeType.SYSTEM_ERROR.getText());
			LOGGER.error(
					"### paymentRevokeJson url={}, requestId={},param={}, error={}",
					url, requestId, param, e);
			return false;
		}
	}

	public boolean paymentReserval(Integer requestId, String url,
			Map<?, ?> paramMap, Response resp) {
		try {
			String response = doPost(requestId, url, paramMap);
			return analyReponse(requestId, url, paramMap, response, resp);
		} catch (Exception e) {
			resp.setErrorCode(ErrorCodeType.SYSTEM_ERROR.getValue());
			LOGGER.error(
					"### paymentReserval url={}, requestId={},parms={}, error={}",
					url, requestId, paramMap.toString(), e);
			return false;
		}
	}

	public boolean paymentReservalJson(Integer requestId, String url,
			String param, Response resp) {
		try {
			String response = doPostJson(requestId, url, param);
			return analyJsonReponse(requestId, url, param, response, resp);
		} catch (Exception e) {
			resp.setErrorCode(ErrorCodeType.SYSTEM_ERROR.getValue());
			LOGGER.error(
					"### paymentReservalJson url={}, requestId={},param={}, error={}",
					url, requestId, param, e);
			return false;
		}
	}

	public boolean paymentRevokeReserval(Integer requestId, String url,
			Map<?, ?> paramMap, Response resp) {
		try {
			String response = doPost(requestId, url, paramMap);
			return analyReponse(requestId, url, paramMap, response, resp);
		} catch (Exception e) {
			resp.setErrorCode(ErrorCodeType.SYSTEM_ERROR.getValue());
			LOGGER.error(
					"### paymentRevokeReserval url={}, requestId={},parms={}, error={}",
					url, requestId, paramMap.toString(), e);
			return false;
		}
	}

	public boolean paymentRevokeReservalJson(Integer requestId, String url,
			String param, Response resp) {
		try {
			String response = doPostJson(requestId, url, param);
			return analyJsonReponse(requestId, url, param, response, resp);
		} catch (Exception e) {
			resp.setErrorCode(ErrorCodeType.SYSTEM_ERROR.getValue());
			resp.setErrorMsg(ErrorCodeType.SYSTEM_ERROR.getText());
			LOGGER.error(
					"### paymentRevokeReserval url={}, requestId={},param={}, error={}",
					url, requestId, param, e);
			return false;
		}
	}

	/**
	 * 查询积分
	 * 
	 * @param requestId
	 * @param url
	 * @param paramMap
	 * @param resp
	 * @return
	 */
	public boolean confirmInfo(Integer requestId, String url,
			Map<?, ?> paramMap, Response resp) {
		try {
			String response = doPost(requestId, url, paramMap);
			return analyReponse(requestId, url, paramMap, response, resp);
		} catch (Exception e) {
			resp.setErrorCode(ErrorCodeType.SYSTEM_ERROR.getValue());
			LOGGER.error(
					"### confirmInfo url={}, requestId={},parms={}, error={}",
					url, requestId, paramMap.toString(), e);
			return false;
		}
	}

	/**
	 * 查询积分
	 * 
	 * @param requestId
	 * @param url
	 * @param paramMap
	 * @param resp
	 * @return
	 */
	public boolean confirmInfoJson(Integer requestId, String url, String param,
			ConfirmInfoVo confirmInfo, Response resp) {
		try {
			Activity activity = activityService.getByActivityId(confirmInfo
					.getActivityId());
			if (activity == null) {
				resp.setErrorCode(ErrorCodeType.NOT_EXIST_ACTIVITY_ERROR
						.getValue());
				resp.setErrorMsg(ErrorCodeType.NOT_EXIST_ACTIVITY_ERROR
						.getText());
				return false;
			}
			Store store = activityService.getByShopIdStoreIdAcId(
					confirmInfo.getShopId() + "", null,
					confirmInfo.getActivityId());
			if (store == null) {
				resp.setErrorCode(ErrorCodeType.NOT_EXIST_ACTIVITY_ERROR
						.getValue());
				resp.setErrorMsg(ErrorCodeType.NOT_EXIST_ACTIVITY_ERROR
						.getText());
				return false;
			}
			String response = doPostJson(requestId, url, param);
			return analyJsonReponse(requestId, url, param, response, resp);
		} catch (Exception e) {
			resp.setErrorCode(ErrorCodeType.SYSTEM_ERROR.getValue());
			resp.setErrorMsg(ErrorCodeType.SYSTEM_ERROR.getText());
			LOGGER.error(
					"### confirmInfo url={}, requestId={},param={}, error={}",
					url, requestId, param.toString(), e);
			return false;
		}
	}

	/**
	 * 活动同步
	 * 
	 * @param requestId
	 * @param url
	 * @param paramMap
	 * @param resp
	 * @return
	 */
	public boolean activityImport(Integer requestId, String url,
			Map<?, ?> paramMap, Response resp) {
		try {
			String response = doPost(requestId, url, paramMap);
			return analyReponse(requestId, url, paramMap, response, resp);
		} catch (Exception e) {
			resp.setErrorCode(ErrorCodeType.SYSTEM_ERROR.getValue());
			LOGGER.error(
					"### activityImport url={}, requestId={},parms={}, error={}",
					url, requestId, paramMap.toString(), e);
			return false;
		}
	}

	private String doGet(Integer requestId, String url) {
		try {
			LOGGER.info("### doGet url={},requestId={} ###", url, requestId);
			outRequestLogService.save(new OutRequestLog(requestId, url, ""));
			String response = HttpRequestUtil.doGet(url);
			outResponseLogService.save(new OutResponseLog(requestId, response));
			return response;
		} catch (Exception e) {
			LOGGER.error("### doGet url={},requestId={},error={}", url,
					requestId, e);
			return null;
		}
	}

	/**
	 * post方式请求接口
	 * 
	 * @param requestId
	 * @param url
	 * @param paramMap
	 * @return
	 */

	private String doPost(Integer requestId, String url, Map<?, ?> paramMap) {
		try {
			LOGGER.info("### doPost url={},requestId={},params={} ###", url,
					requestId, paramMap.toString());
			outRequestLogService.save(new OutRequestLog(requestId, url,
					paramMap.toString()));
			String response = HttpRequestUtil.doPost(url, paramMap);
			outResponseLogService.save(new OutResponseLog(requestId, response));
			return response;
		} catch (Exception e) {
			LOGGER.error("### doPost url={}, requestId={},parms={}, error={}",
					url, requestId, paramMap.toString(), e);
			return null;
		}
	}

	/**
	 * 以json方式post请求接口
	 * 
	 * @param requestId
	 * @param url
	 * @param paramMap
	 * @return
	 */

	private String doPostJson(Integer requestId, String url, String param) {
		try {
			LOGGER.info("### doPost url={},requestId={},param={} ###", url,
					requestId, param);
			String response = HttpRequestUtil.doPostJson(url, param);
			outRequestLogService.save(new OutRequestLog(requestId, url, param));
			outResponseLogService.save(new OutResponseLog(requestId, response));
			return response;
		} catch (Exception e) {
			LOGGER.error("### doPost url={}, requestId={},param={}, error={}",
					url, requestId, param, e);
			outRequestLogService.save(new OutRequestLog(requestId, url, param));
			outResponseLogService.save(new OutResponseLog(requestId, e
					.getMessage()));
			return null;
		}
	}
}
