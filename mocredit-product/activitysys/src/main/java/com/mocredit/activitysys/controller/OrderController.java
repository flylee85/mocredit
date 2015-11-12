package com.mocredit.activitysys.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mocredit.base.datastructure.ResponseData;
import com.mocredit.base.datastructure.impl.AjaxResponseData;
import com.mocredit.base.pagehelper.PageHelper;
import com.mocredit.base.pagehelper.PageInfo;
import com.mocredit.base.util.CSVUtil;
import com.mocredit.base.util.ExcelTool;
import com.mocredit.base.web.BaseController;
import com.mocredit.order.constant.BaseExportTitle;
import com.mocredit.order.constant.ExportType;
import com.mocredit.order.constant.OrderStatusType;
import com.mocredit.order.entity.Order;
import com.mocredit.order.service.OrderService;
import com.mocredit.order.vo.OrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping("/order/")
public class OrderController extends BaseController {
    @Autowired
    private OrderService orderService;
    private final static Integer EXPOET_PAGESIZE = 5000;
    private final static String FILE_NAME = "订单数据";

    @RequestMapping("/queryOrderPage")
    @ResponseBody
    public String showOrder(OrderVo orderVo,
                            @RequestParam Map<String, Object> reqMap, Integer draw, Integer start, Integer length) {
        //定义返回页面的对象
        ResponseData responseData = new AjaxResponseData();
        //简单计算页数，当前页数=开始条数/搜索条数+1
        if (start == null) {
            start = 0;
        }
        if (length == null) {
            length = 10;
        }
        int currentPage = start / length + 1;
        try {
            //根据参数查询分页信息对象
            PageHelper.startPage(currentPage, PAGE_SIZE);
            List<Order> orderList = orderService.findOrderList(orderVo);
            PageInfo<Order> page = new PageInfo<Order>(orderList);
            //重构新的分页对象，为适应前端分页插件
            Map<String, Object> newMap = new HashMap<String, Object>();
            newMap.put("draw", draw);//查询标示,原值返回
            newMap.put("recordsTotal", page.getTotal());//总数量
            newMap.put("recordsFiltered", page.getTotal());//过滤后的总数量，暂未用到
            newMap.put("data", page.getList());//数据列表
            String resultStr = JSON.toJSONString(newMap, SerializerFeature.WriteMapNullValue);//将新的分页对象返回页面
            //返回页面数据
            return resultStr;
        } catch (Exception e) {
            //如果抛出异常，则将返回页面的对象设置为false
            e.printStackTrace();
            responseData.setSuccess(false);
            responseData.setErrorMsg(e.getMessage(), e);
        }
        //返回页面数据
        return JSON.toJSONString(responseData, SerializerFeature.WriteMapNullValue);
    }

    /**
     * 根据订单id更新订单状态
     *
     * @param orderId
     * @return json obj string
     */
    @RequestMapping("/updateOrderByOrderId")
    @ResponseBody
    public String deleteActivityById(@RequestParam String orderId) {
        //定义返回页面的对象
        ResponseData responseData = new AjaxResponseData();
        try {
            Integer affectCount = orderService.updateOrderStatusByOrderId(orderId, OrderStatusType.REVOCATION.getValue());
            //如果程序执行到这里没有发生异常，则证明该操作成功执行,将获取到的数据放到返回页面的对象中
            responseData.setData(affectCount);
        } catch (Exception e) {
            //如果抛出异常，则将返回页面的对象设置为false
            e.printStackTrace();
            responseData.setSuccess(false);
            responseData.setErrorMsg("撤销订单失败");
        }
        //返回页面数据
        return JSON.toJSONString(responseData, SerializerFeature.WriteMapNullValue);
    }

    /**
     * @param storeId
     * @param storeName
     * @param code
     * @return
     */
    @RequestMapping("/updateOrderByCode")
    @ResponseBody
    public String updateOrderByCode(String storeId, String storeName, String code, String actId) {
        //定义返回页面的对象
        ResponseData responseData = new AjaxResponseData();
        try {
            Order order = new Order();
            order.setStoreId(storeId);
            order.setStoreName(storeName);
            order.setCode(code);
            order.setStatus(OrderStatusType.EXCHANGE.getValue());
            Integer affectCount = orderService.updateOrderByCode(order);
            //如果程序执行到这里没有发生异常，则证明该操作成功执行,将获取到的数据放到返回页面的对象中
            responseData.setData(affectCount);
        } catch (Exception e) {
            //如果抛出异常，则将返回页面的对象设置为false
            e.printStackTrace();
            responseData.setSuccess(false);
            responseData.setErrorMsg(e.getMessage(), e);
        }
        //返回页面数据
        return JSON.toJSONString(responseData, SerializerFeature.WriteMapNullValue);
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
                    if (OrderStatusType.EXCHANGE.getValue().equals(order.getStatus())) {
                        dataMap.put(BaseExportTitle.STATUS.getText(),
                                OrderStatusType.EXCHANGE.getText());
                    }
                    if (OrderStatusType.REVOCATION.getValue().equals(order.getStatus())) {
                        dataMap.put(BaseExportTitle.STATUS.getText(),
                                OrderStatusType.REVOCATION.getText());
                    }
                    if (OrderStatusType.SEND.getValue().equals(order.getStatus())) {
                        dataMap.put(BaseExportTitle.STATUS.getText(),
                                OrderStatusType.SEND.getText());
                    }
                    dataMap.put(BaseExportTitle.CODE.getText(), order.getCode());
                    dataMap.put(BaseExportTitle.BANK.getText(), order.getBank());
                    dataMap.put(BaseExportTitle.CARD_NUM.getText(),
                            order.getCardNum());
                    dataMap.put(BaseExportTitle.INTEGRAL.getText(),
                            order.getIntegral());
                    dataMap.put(BaseExportTitle.TEL.getText(), order.getTel());
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
        titleList.add(BaseExportTitle.TEL.getText());

        keyList.add(BaseExportTitle.ORDER_ID.getText());
        keyList.add(BaseExportTitle.PUB_ENTERPRISE.getText());
        keyList.add(BaseExportTitle.SUP_ENTERPRISE.getText());
        keyList.add(BaseExportTitle.STORE.getText());
        keyList.add(BaseExportTitle.ACTIVITY.getText());
        keyList.add(BaseExportTitle.START_TIME.getText());
        keyList.add(BaseExportTitle.END_TIME.getText());
        keyList.add(BaseExportTitle.STATUS.getText());
        keyList.add(BaseExportTitle.TEL.getText());

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
        mapCsvTitle.put(BaseExportTitle.TEL.getText(),
                BaseExportTitle.TEL.getText());

        switch (orderType + "") {
            case "02":

                titleList.add(BaseExportTitle.CODE.getText());
                keyList.add(BaseExportTitle.CODE.getText());
                mapCsvTitle.put(BaseExportTitle.CODE.getText(),
                        BaseExportTitle.CODE.getText());
                break;
            case "01":
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
