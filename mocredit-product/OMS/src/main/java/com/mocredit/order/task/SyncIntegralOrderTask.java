package com.mocredit.order.task;

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
import com.mocredit.order.util.HttpRequestUtil;

public class SyncIntegralOrderTask {
	@Autowired
	private ApiUrlService apiUrlService;
	@Autowired
	private OrderService orderService;

	public void exec() {
		ApiUrl apiUrl = apiUrlService.selectByCode(ApiUrlCodeType.INTEGRAL
				.getValue());
		apiUrl.setStartTime(new Date());
		apiUrl.setStatus(ApiUrlStatusType.RUNNING.getValue());
		apiUrlService.updateByCode(apiUrl);
		boolean isFinishFlag = false;
		while (!isFinishFlag) {
			try {
				apiUrl = apiUrlService.selectByCode(ApiUrlCodeType.INTEGRAL
						.getValue());
				Integer offset = apiUrl.getOffset();
				int pagesize = apiUrl.getPagesize();
				Map parameterMap = new HashMap();
				parameterMap.put("offset", offset);
				parameterMap.put("count", pagesize);
				String response = HttpRequestUtil.doPost(apiUrl.getUrl(),
						parameterMap);
				System.out.println("response:" + response);
				OrderDataDto orderDataDto = JSON.parseObject(response,
						OrderDataDto.class);
				if (orderDataDto.getData().isEmpty()) {
					isFinishFlag = true;
					apiUrl.setEndTime(new Date());
					apiUrl.setStatus(ApiUrlStatusType.FINISH.getValue());
				} else {
					for (OrderDto orderDto : orderDataDto.getData()) {
						Order order = new Order();
						convert(orderDto, order);
						orderService.save(order);
					}
					String maxId = orderDataDto.getData()
							.get(orderDataDto.getData().size() - 1).getId();
					apiUrl.setOffset(Integer.valueOf(maxId));
				}
				apiUrlService.updateByCode(apiUrl);
			} catch (Exception e) {
				isFinishFlag = true;
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
		order.setType(OrderType.INTEGRAL_ORDER.getValue());
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