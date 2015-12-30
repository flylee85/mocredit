package com.mocredit.order.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mocredit.base.pagehelper.PageHelper;
import com.mocredit.base.web.BaseController;
import com.mocredit.order.constant.BaseExportTitle;
import com.mocredit.order.constant.ExportType;
import com.mocredit.order.constant.OrderType;
import com.mocredit.order.entity.Order;
import com.mocredit.order.service.OrderService;
import com.mocredit.order.util.CSVUtil;
import com.mocredit.order.util.ExcelTool;
import com.mocredit.order.vo.OrderVo;

@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {
	@Autowired
	private OrderService orderService;
	private final static Integer EXPOET_PAGESIZE = 5000;
	private final static String FILE_NAME = "订单数据";

	@RequestMapping("/show")
	@ResponseBody
	public ModelAndView showOrder(OrderVo orderVo,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
		PageHelper.startPage(pageNum, PAGE_SIZE);
		orderService.findOrderList(orderVo);
		ModelAndView modelAndView = new ModelAndView("login");
		return modelAndView;
	}

	@RequestMapping("/export")
	public void exportOrder(OrderVo orderVo, HttpServletResponse response) {
		boolean isFinal = false;
		int pageNum = 1;
		List<String> keyList = new ArrayList<String>();
		List<String> titleList = new ArrayList<String>();
		Map<String, String> mapCsvTitle = new LinkedHashMap<String, String>();
		setTitleAndKey(titleList, keyList, mapCsvTitle, orderVo.getType());
		List<Map> orderListMap = new ArrayList<Map>();
		List<Map> exportCsvData = new ArrayList<Map>();
		String fileName = FILE_NAME + ".xlsx";
		if (ExportType.CSV.getValue().equals(orderVo.getExportType())) {
			fileName = FILE_NAME + ".csv";
		}
		response.reset();
		response.setContentType("application/x-download");
		try {
			response.setHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes("UTF-8"), "iso-8859-1"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		while (!isFinal) {
			PageHelper.startPage(pageNum, EXPOET_PAGESIZE);
			List<Order> orderList = orderService.findOrderList(orderVo);
			if (orderList.isEmpty()) {
				isFinal = true;
			} else {
				for (Order order : orderList) {
					Map dataMap = new HashMap();
					dataMap.put(BaseExportTitle.ORDER_ID.getText(),
							order.getOrderId());
					dataMap.put(BaseExportTitle.PUB_ENTERPRISE.getText(),
							order.getPubEnterpriseName());
					dataMap.put(BaseExportTitle.SUP_ENTERPRISE.getText(),
							order.getSupEnterpriseName());
					dataMap.put(BaseExportTitle.STORE.getText(),
							order.getStoreName());
					dataMap.put(BaseExportTitle.ACTIVITY.getText(),
							order.getActivityName());
					dataMap.put(BaseExportTitle.START_TIME.getText(),
							order.getStartTime());
					dataMap.put(BaseExportTitle.END_TIME.getText(),
							order.getEndTime());
					dataMap.put(BaseExportTitle.STATUS.getText(),
							order.getStatus());
					dataMap.put(BaseExportTitle.CODE.getText(), order.getCode());
					dataMap.put(BaseExportTitle.BANK.getText(), order.getBank());
					dataMap.put(BaseExportTitle.CARD_NUM.getText(),
							order.getCardNum());
					dataMap.put(BaseExportTitle.INTEGRAL.getText(),
							order.getIntegral());
					if (ExportType.CSV.getValue().equals(
							orderVo.getExportType())) {
						exportCsvData.add(dataMap);
					} else {
						orderListMap.add(dataMap);
					}
					pageNum += 1;
				}
			}
		}
		try {

			if (ExportType.CSV.getValue().equals(orderVo.getExportType())) {
				CSVUtil.download(exportCsvData, mapCsvTitle,
						response.getOutputStream());
			} else {
				ExcelTool.download(fileName,
						titleList.toArray(new String[titleList.size()]),
						keyList.toArray(new String[keyList.size()]),
						orderListMap, response.getOutputStream());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void setTitleAndKey(List<String> titleList, List<String> keyList,
			Map<String, String> mapCsvTitle, String orderType) {
		titleList.add(BaseExportTitle.ORDER_ID.getText());
		titleList.add(BaseExportTitle.PUB_ENTERPRISE.getText());
		titleList.add(BaseExportTitle.SUP_ENTERPRISE.getText());
		titleList.add(BaseExportTitle.STORE.getText());
		titleList.add(BaseExportTitle.ACTIVITY.getText());
		titleList.add(BaseExportTitle.START_TIME.getText());
		titleList.add(BaseExportTitle.END_TIME.getText());
		titleList.add(BaseExportTitle.STATUS.getText());

		keyList.add(BaseExportTitle.ORDER_ID.getText());
		keyList.add(BaseExportTitle.PUB_ENTERPRISE.getText());
		keyList.add(BaseExportTitle.SUP_ENTERPRISE.getText());
		keyList.add(BaseExportTitle.STORE.getText());
		keyList.add(BaseExportTitle.ACTIVITY.getText());
		keyList.add(BaseExportTitle.START_TIME.getText());
		keyList.add(BaseExportTitle.END_TIME.getText());
		keyList.add(BaseExportTitle.STATUS.getText());

		mapCsvTitle.put(BaseExportTitle.ORDER_ID.getText(),
				BaseExportTitle.ORDER_ID.getText());
		mapCsvTitle.put(BaseExportTitle.PUB_ENTERPRISE.getText(),
				BaseExportTitle.PUB_ENTERPRISE.getText());
		mapCsvTitle.put(BaseExportTitle.SUP_ENTERPRISE.getText(),
				BaseExportTitle.SUP_ENTERPRISE.getText());
		mapCsvTitle.put(BaseExportTitle.STORE.getText(),
				BaseExportTitle.STORE.getText());
		mapCsvTitle.put(BaseExportTitle.ACTIVITY.getText(),
				BaseExportTitle.ACTIVITY.getText());
		mapCsvTitle.put(BaseExportTitle.START_TIME.getText(),
				BaseExportTitle.START_TIME.getText());
		mapCsvTitle.put(BaseExportTitle.END_TIME.getText(),
				BaseExportTitle.END_TIME.getText());
		mapCsvTitle.put(BaseExportTitle.STATUS.getText(),
				BaseExportTitle.STATUS.getText());

		switch (orderType + "") {
		case "01":

			titleList.add(BaseExportTitle.CODE.getText());
			keyList.add(BaseExportTitle.CODE.getText());
			mapCsvTitle.put(BaseExportTitle.CODE.getText(),
					BaseExportTitle.CODE.getText());
			break;
		case "02":
			titleList.add(BaseExportTitle.BANK.getText());
			titleList.add(BaseExportTitle.CARD_NUM.getText());
			titleList.add(BaseExportTitle.INTEGRAL.getText());
			keyList.add(BaseExportTitle.BANK.getText());
			keyList.add(BaseExportTitle.CARD_NUM.getText());
			keyList.add(BaseExportTitle.INTEGRAL.getText());

			mapCsvTitle.put(BaseExportTitle.BANK.getText(),
					BaseExportTitle.BANK.getText());
			mapCsvTitle.put(BaseExportTitle.CARD_NUM.getText(),
					BaseExportTitle.CARD_NUM.getText());
			mapCsvTitle.put(BaseExportTitle.INTEGRAL.getText(),
					BaseExportTitle.INTEGRAL.getText());
			break;

		}

	}
}
