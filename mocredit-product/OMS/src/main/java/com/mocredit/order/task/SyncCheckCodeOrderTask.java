package com.mocredit.order.task;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.mocredit.order.constant.ApiUrlCodeType;
import com.mocredit.order.constant.ApiUrlStatusType;
import com.mocredit.order.constant.OrderType;
import com.mocredit.order.dto.OrderDataDto;
import com.mocredit.order.dto.OrderDto;
import com.mocredit.order.entity.ApiUrl;
import com.mocredit.order.entity.Order;
import com.mocredit.order.service.ApiUrlService;
import com.mocredit.order.service.OrderService;
import com.mocredit.order.util.DateTimeUtils;
import com.mocredit.order.util.HttpRequestUtil;

public class SyncCheckCodeOrderTask {
	@Autowired
	private ApiUrlService apiUrlService;
	@Autowired
	private OrderService orderService;

	public void exec() {
		ApiUrl apiUrl = apiUrlService.selectByCode(ApiUrlCodeType.CODE
				.getValue());
		apiUrl.setStartTime(new Date());
		apiUrl.setStatus(ApiUrlStatusType.RUNNING.getValue());
		apiUrlService.updateByCode(apiUrl);
		boolean isFinishFlag = false;
		while (!isFinishFlag) {
			try {
				apiUrl = apiUrlService.selectByCode(ApiUrlCodeType.CODE
						.getValue());
				if (apiUrl.getOffsetStartTime().getTime() > new Date()
						.getTime()) {
					isFinishFlag = true;
					apiUrl.setOffsetStartTime(new Date());
					apiUrl.setStatus(ApiUrlStatusType.FINISH.getValue());
				} else {
					offsetWhile();
				}
				apiUrlService.updateByCode(apiUrl);
			} catch (Exception e) {
				isFinishFlag = true;
				e.printStackTrace();
			}
		}
	}

	public void offsetWhile() {
		boolean isFinishOffset = false;
		while (!isFinishOffset) {
			ApiUrl apiUrl = apiUrlService.selectByCode(ApiUrlCodeType.CODE
					.getValue());
			Integer offset = apiUrl.getOffset();
			int pagesize = apiUrl.getPagesize();
			Date offsetStartTime = apiUrl.getOffsetStartTime();
			Calendar c = Calendar.getInstance();
			c.setTime(offsetStartTime);
			c.add(Calendar.HOUR, apiUrl.getOffsetHour());
			Date offsetEndTime = c.getTime();
			Map parameterMap = new HashMap();
			parameterMap.put("count", pagesize);
			parameterMap.put("starTime",
					DateTimeUtils.parseYYYYMMDDHHmmss(offsetStartTime));
			parameterMap.put("endTime",
					DateTimeUtils.parseYYYYMMDDHHmmss(offsetEndTime));
			parameterMap.put("offset", offset);
			try {
				String response = HttpRequestUtil.doPost(apiUrl.getUrl(),
						parameterMap);
				System.out.println("response:" + response);
				OrderDataDto orderDataDto = JSON.parseObject(response,
						OrderDataDto.class);
				if (orderDataDto.getData().isEmpty()) {
					apiUrl.setOffset(0);
					apiUrl.setOffsetStartTime(offsetEndTime);
				} else {
					for (OrderDto orderDto : orderDataDto.getData()) {
						Order order = new Order();
						convert(orderDto, order);
						orderService.save(order);
					}
					Integer total = orderDataDto.getTotal();
					if (null == total || total > apiUrl.getPagesize()) {
						offset += apiUrl.getPagesize();
						apiUrl.setOffset(offset);
					} else {
						isFinishOffset = true;
						apiUrl.setOffset(0);
						apiUrl.setOffsetStartTime(offsetEndTime);
					}
				}
				apiUrlService.updateByCode(apiUrl);
			} catch (Exception e) {
				isFinishOffset = true;
				e.printStackTrace();
			}
		}
	}

	public void convert(OrderDto orderDto, Order order) {
		order.setActivityId(orderDto.getActivityId());
		order.setActivityName(orderDto.getActivityName());
		order.setPubEnterpriseId(orderDto.getEnterpriseId());
		order.setPubEnterpriseName(orderDto.getEnterpriseName());
		order.setSupEnterpriseId(orderDto.getSupEnterpriseId());
		order.setSupEnterpriseName(orderDto.getSupEnterpriseName());
		order.setStoreId(orderDto.getStoreId());
		order.setStoreName(orderDto.getStoreName());
		order.setType(OrderType.CHECK_CODE_ORDER.getValue());
		order.setBank(orderDto.getBank());
		order.setCardNum(orderDto.getCardNum());
		order.setCreateTime(orderDto.getCreateTime());
		order.setIntegral(orderDto.getIntegral());
		order.setStatus(orderDto.getStatus());
		order.setStartTime(orderDto.getStartTime());
		order.setEndTime(orderDto.getEndTime());
		order.setOrderId(orderDto.getOrderId());
	}
}