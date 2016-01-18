package com.mocredit.activitysys.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mocredit.base.datastructure.ResponseData;
import com.mocredit.base.datastructure.impl.AjaxResponseData;
import com.mocredit.base.util.CSVUtil;
import com.mocredit.base.util.ExcelTool;
import com.mocredit.base.web.BaseController;
import com.mocredit.order.constant.BaseExportTitle;
import com.mocredit.order.constant.ExportType;
import com.mocredit.order.constant.OrderStatusType;
import com.mocredit.order.dto.OrderDto;
import com.mocredit.order.entity.Order;
import com.mocredit.order.entity.OrderData;
import com.mocredit.order.entity.OrderRespData;
import com.mocredit.order.service.OrderService;
import com.mocredit.sendcode.constant.ActivityStatus;
import com.mocredit.sendcode.service.SendCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/order/")
public class OrderController extends BaseController {
    @Autowired
    private OrderService orderService;
    private final static Integer EXPOET_PAGESIZE = 5000;
    private final static Integer EXPORT_TOTAL = 50000;//最大导出5万条
    private final static String ORDER_FILE_NAME = "订单数据";
    private final static String RECORD_FILE_NAME = "兑换记录数据";
    @Autowired
    private SendCodeService sendCodeService;
   /* @RequestMapping("/queryOrderPage")
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
    }*/

    @RequestMapping("/queryOrderPage")
    @ResponseBody
    public String showOrder(OrderDto orderDto,
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
            orderDto.setPageNum(currentPage);
            orderDto.setPageSize(PAGE_SIZE);
            ResponseData repData = orderService.findOrderList(orderDto);
            OrderRespData orderRespData = JSON.parseObject(repData.getData() + "", OrderRespData.class);
            //重构新的分页对象，为适应前端分页插件
            Map<String, Object> newMap = new HashMap<String, Object>();
            newMap.put("draw", draw);//查询标示,原值返回
            newMap.put("recordsTotal", orderRespData.getPageCount());//总数量
            newMap.put("recordsFiltered", orderRespData.getPageCount());//过滤后的总数量，暂未用到
            newMap.put("data", orderRespData.getData());//数据列表
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
     * @return json obj string
     */
    @RequestMapping("/updateOrderByOrderId")
    @ResponseBody
    public String updateOrderByOrderId(@RequestParam String orderId, @RequestParam String enCode) {
        //定义返回页面的对象id
        ResponseData responseData = new AjaxResponseData();
        try {
            orderService.revokeOrderByOrderId(orderId, enCode, responseData);
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
     * 监测订单状态id
     *
     * @return json obj string
     */
    @RequestMapping("/checkOrderById")
    @ResponseBody
    public String checkOrderByOrderId(@RequestParam String id) {
        //定义返回页面的对象
        ResponseData responseData = new AjaxResponseData();
        try {
            Integer affectCount = orderService.checkOrderById(id);
            //如果程序执行到这里没有发生异常，则证明该操作成功执行,将获取到的数据放到返回页面的对象中
            responseData.setData(affectCount);
        } catch (Exception e) {
            //如果抛出异常，则将返回页面的对象设置为false
            e.printStackTrace();
            responseData.setSuccess(false);
            responseData.setErrorMsg("监测订单失败");
        }
        //返回页面数据
        return JSON.toJSONString(responseData, SerializerFeature.WriteMapNullValue);
    }

    @RequestMapping("/queryCodeOrderPage")
    @ResponseBody
    public String showCodeOrder(OrderDto orderDto,
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
            orderDto.setPageNum(currentPage);
            orderDto.setPageSize(PAGE_SIZE);
            ResponseData repData = orderService.findCodeOrderList(orderDto);
            OrderRespData orderRespData = JSON.parseObject(repData.getData() + "", OrderRespData.class);
            //重构新的分页对象，为适应前端分页插件
            Map<String, Object> newMap = new HashMap<String, Object>();
            newMap.put("draw", draw);//查询标示,原值返回
            newMap.put("recordsTotal", orderRespData.getPageCount());//总数量
            newMap.put("recordsFiltered", orderRespData.getPageCount());//过滤后的总数量，暂未用到
            newMap.put("data", orderRespData.getData());//数据列表
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
     * 补发码到用户手机
     *
     * @param id
     * @return
     */
    @RequestMapping("/reissueByCodeId")
    @ResponseBody
    public String reissueByCodeId(String id) {
        //定义返回页面的对象
        ResponseData responseData = new AjaxResponseData();
        try {
            Map<String, Object> mapResult = sendCodeService.getSendSmsTypeByCodeId(id);
            if (mapResult != null && mapResult.containsKey("id")) {
                String actId = mapResult.get("id") + "";
                String sendSmsType = mapResult.get("send_sms_type") + "";
                System.out.println("###################actId：" + actId + " id：" + id);
                if (sendSmsType.contains(ActivityStatus.MMS) && sendSmsType.contains(ActivityStatus.SMS)) {
                    sendCodeService.sendCodeById(actId, id, ActivityStatus.SMS);
                    sendCodeService.sendCodeById(actId, id, ActivityStatus.MMS);
                } else {
                    if (sendSmsType.contains(ActivityStatus.SMS)) {
                        sendCodeService.sendCodeById(actId, id, ActivityStatus.SMS);
                    }
                    if (sendSmsType.contains(ActivityStatus.MMS)) {
                        sendCodeService.sendCodeById(actId, id, ActivityStatus.MMS);
                    }
                }
            } else {
                System.out.println("##################error=" + mapResult);
            }
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

    @RequestMapping("/delayOrderById")
    @ResponseBody
    public String delayOrderById(String id, String delayTime) {
        //定义返回页面的对象
        ResponseData responseData = new AjaxResponseData();
        try {
            orderService.delayOrderById(responseData, id, delayTime);
        } catch (Exception e) {
            //如果抛出异常，则将返回页面的对象设置为false
            e.printStackTrace();
            responseData.setSuccess(false);
            responseData.setErrorMsg(e.getMessage(), e);
        }
        //返回页面数据
        return JSON.toJSONString(responseData, SerializerFeature.WriteMapNullValue);
    }

    @RequestMapping("/abolishByCodeId")
    @ResponseBody
    public String abolishByCodeId(String id) {
        //定义返回页面的对象
        ResponseData responseData = new AjaxResponseData();
        try {
            orderService.abolishCodeById(responseData, id);
        } catch (Exception e) {
            //如果抛出异常，则将返回页面的对象设置为false
            e.printStackTrace();
            responseData.setSuccess(false);
            responseData.setErrorMsg(e.getMessage(), e);
        }
        //返回页面数据
        return JSON.toJSONString(responseData, SerializerFeature.WriteMapNullValue);
    }


    @RequestMapping(value = "/exportCodeCount", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String exportCodeOrderCount(OrderDto orderDto, HttpServletResponse response) {
        ResponseData responseData = new AjaxResponseData();
        orderDto.setPageNum(1);
        orderDto.setPageSize(1);
        ResponseData repData = orderService.findCodeOrderList(orderDto);
        OrderRespData orderRespData = JSON.parseObject(repData.getData() + "", OrderRespData.class);
        if (orderRespData.getPageCount() > EXPORT_TOTAL) {
            responseData.setSuccess(false);
            responseData.setErrorMsg("导出数量超过50000条");
        }
        return JSON.toJSONString(responseData, SerializerFeature.WriteMapNullValue);
    }

    @RequestMapping("/exportCodeOrder")
    public void exportCodeOrder(OrderDto orderDto, HttpServletResponse response) {
        boolean isFinal = false;
        int pageNum = 1;
        List<String> keyList = new ArrayList<String>();
        List<String> titleList = new ArrayList<String>();
        Map<String, String> mapCsvTitle = new LinkedHashMap<String, String>();
        setCodeTitleAndKey(titleList, keyList, mapCsvTitle);
        List<Map> orderListMap = new ArrayList<Map>();
        List<Map> exportCsvData = new ArrayList<Map>();
        String fileName = ORDER_FILE_NAME + ".xls";
        if (ExportType.CSV.getValue().equals(orderDto.getExportType())) {
            fileName = ORDER_FILE_NAME + ".csv";
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
            orderDto.setDownload(true);
            orderDto.setPageNum(pageNum);
            orderDto.setPageSize(EXPOET_PAGESIZE);
            ResponseData repData = orderService.findCodeOrderList(orderDto);
            OrderRespData orderRespData = JSON.parseObject(repData.getData() + "", OrderRespData.class);
            if (orderRespData == null || orderRespData.getData() == null || orderRespData.getData().isEmpty()) {
                isFinal = true;
            } else {
                for (OrderData order : orderRespData.getData()) {
                    Map dataMap = new HashMap();
                    dataMap.put(BaseExportTitle.PUB_ENTERPRISE.getText(),
                            order.getEnterpriseName());
                    dataMap.put(BaseExportTitle.SUP_ENTERPRISE.getText(),
                            order.getShopName());
                    dataMap.put(BaseExportTitle.STORE.getText(),
                            order.getStoreName());
                    dataMap.put(BaseExportTitle.ACTIVITY.getText(),
                            order.getActivityName());
                    if (order.getOrderTime() != null && order.getVerifyTime().length() == 13) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        dataMap.put(BaseExportTitle.ORDER_TIME.getText(),
                                format.format(Long.valueOf(order.getVerifyTime())));
                    } else {
                        dataMap.put(BaseExportTitle.ORDER_TIME.getText(),
                                order.getVerifyTime());
                    }
                    if ("01".equals(order.getStatus())) {
                        dataMap.put(BaseExportTitle.STATUS.getText(),
                                "未验证");
                    }
                    if ("02".equals(order.getStatus())) {
                        dataMap.put(BaseExportTitle.STATUS.getText(),
                                "已验证");
                    }
                    if ("03".equals(order.getStatus())) {
                        dataMap.put(BaseExportTitle.STATUS.getText(),
                                "已废除");
                    }
                    dataMap.put(BaseExportTitle.CODE.getText(), order.getCode());
                    dataMap.put(BaseExportTitle.CARD_NUM.getText(),
                            order.getCardNo());
                    dataMap.put(BaseExportTitle.TEL.getText(), order.getMobile());
                    if (ExportType.CSV.getValue().equals(
                            orderDto.getExportType())) {
                        exportCsvData.add(dataMap);
                    } else {
                        orderListMap.add(dataMap);
                    }
                }
                pageNum += 1;
            }
        }
        try {

            if (ExportType.CSV.getValue().equals(orderDto.getExportType())) {
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

    @RequestMapping(value = "/exportCount", produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String exportOrderCount(OrderDto orderDto, HttpServletResponse response) {
        ResponseData responseData = new AjaxResponseData();
        orderDto.setPageNum(1);
        orderDto.setPageSize(1);
        ResponseData repData = orderService.findOrderList(orderDto);
        OrderRespData orderRespData = JSON.parseObject(repData.getData() + "", OrderRespData.class);
        if (orderRespData.getPageCount() > EXPORT_TOTAL) {
            responseData.setSuccess(false);
            responseData.setErrorMsg("导出数量超过50000条");
        }
        return JSON.toJSONString(responseData, SerializerFeature.WriteMapNullValue);
    }

    @RequestMapping(value = "/export", produces = {"application/json;charset=UTF-8"})
    public void exportOrder(OrderDto orderDto, HttpServletResponse response) {
        boolean isFinal = false;
        int pageNum = 1;
        List<String> keyList = new ArrayList<String>();
        List<String> titleList = new ArrayList<String>();
        Map<String, String> mapCsvTitle = new LinkedHashMap<String, String>();
        setTitleAndKey(titleList, keyList, mapCsvTitle, orderDto.getType());
        List<Map> orderListMap = new ArrayList<Map>();
        List<Map> exportCsvData = new ArrayList<Map>();
        String fileName = RECORD_FILE_NAME + ".xls";
        if (ExportType.CSV.getValue().equals(orderDto.getExportType())) {
            fileName = RECORD_FILE_NAME + ".csv";
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
            orderDto.setDownload(true);
            orderDto.setPageNum(pageNum);
            orderDto.setPageSize(EXPOET_PAGESIZE);
            ResponseData repData = orderService.findOrderList(orderDto);
            OrderRespData orderRespData = JSON.parseObject(repData.getData() + "", OrderRespData.class);
            if (orderRespData == null || orderRespData.getData() == null || orderRespData.getData().isEmpty()) {
                isFinal = true;
            } else {
                for (OrderData order : orderRespData.getData()) {
                    Map dataMap = new HashMap();
                    dataMap.put(BaseExportTitle.ORDER_ID.getText(),
                            order.getOrderId());
                    dataMap.put(BaseExportTitle.PUB_ENTERPRISE.getText(),
                            order.getEnterpriseName());
                    dataMap.put(BaseExportTitle.SUP_ENTERPRISE.getText(),
                            order.getShopName());
                    dataMap.put(BaseExportTitle.STORE.getText(),
                            order.getStoreName());
                    dataMap.put(BaseExportTitle.ACTIVITY.getText(),
                            order.getActivityName());
                    if (order.getOrderTime().length() == 13) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        dataMap.put(BaseExportTitle.ORDER_TIME.getText(),
                                format.format(Long.valueOf(order.getOrderTime())));
                    } else {
                        dataMap.put(BaseExportTitle.ORDER_TIME.getText(),
                                order.getOrderTime());
                    }
                    if (OrderStatusType.EXCHANGE.getValue().equals(order.getStatus())) {
                        dataMap.put(BaseExportTitle.STATUS.getText(),
                                OrderStatusType.EXCHANGE.getText());
                    }
                    if (OrderStatusType.REVOCATION.getValue().equals(order.getStatus())) {
                        dataMap.put(BaseExportTitle.STATUS.getText(),
                                OrderStatusType.REVOCATION.getText());
                    }
                    if (OrderStatusType.PAYMENT.getValue().equals(order.getStatus())) {
                        dataMap.put(BaseExportTitle.STATUS.getText(),
                                OrderStatusType.PAYMENT.getText());
                    }
                    if (OrderStatusType.PAYMENT_REVOKE.getValue().equals(order.getStatus())) {
                        dataMap.put(BaseExportTitle.STATUS.getText(),
                                OrderStatusType.PAYMENT_REVOKE.getText());
                    }
                    if (OrderStatusType.PAYMENT_REVERSAL.getValue().equals(order.getStatus())) {
                        dataMap.put(BaseExportTitle.STATUS.getText(),
                                OrderStatusType.PAYMENT_REVERSAL.getText());
                    }
                    if (OrderStatusType.PAYMENT_REVERSAL_REVOKE.getValue().equals(order.getStatus())) {
                        dataMap.put(BaseExportTitle.STATUS.getText(),
                                OrderStatusType.PAYMENT_REVERSAL_REVOKE.getText());
                    }

                    dataMap.put(BaseExportTitle.CODE.getText(), order.getCode());
                    dataMap.put(BaseExportTitle.CARD_NUM.getText(),
                            order.getCardNo());
                    dataMap.put(BaseExportTitle.INTEGRAL.getText(),
                            order.getAmt());
                    dataMap.put(BaseExportTitle.TEL.getText(), order.getMobile());
                    if (ExportType.CSV.getValue().equals(
                            orderDto.getExportType())) {
                        exportCsvData.add(dataMap);
                    } else {
                        orderListMap.add(dataMap);
                    }
                }
                pageNum += 1;
            }
        }
        try {

            if (ExportType.CSV.getValue().equals(orderDto.getExportType())) {
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

    public void setCodeTitleAndKey(List<String> titleList, List<String> keyList,
                                   Map<String, String> mapCsvTitle) {
        titleList.add(BaseExportTitle.PUB_ENTERPRISE.getText());
        titleList.add(BaseExportTitle.SUP_ENTERPRISE.getText());
        titleList.add(BaseExportTitle.STORE.getText());
        titleList.add(BaseExportTitle.ACTIVITY.getText());
        titleList.add(BaseExportTitle.ORDER_TIME.getText());
        titleList.add(BaseExportTitle.STATUS.getText());


        keyList.add(BaseExportTitle.PUB_ENTERPRISE.getText());
        keyList.add(BaseExportTitle.SUP_ENTERPRISE.getText());
        keyList.add(BaseExportTitle.STORE.getText());
        keyList.add(BaseExportTitle.ACTIVITY.getText());
        keyList.add(BaseExportTitle.ORDER_TIME.getText());
        keyList.add(BaseExportTitle.STATUS.getText());


        mapCsvTitle.put(BaseExportTitle.PUB_ENTERPRISE.getText(),
                BaseExportTitle.PUB_ENTERPRISE.getText());
        mapCsvTitle.put(BaseExportTitle.SUP_ENTERPRISE.getText(),
                BaseExportTitle.SUP_ENTERPRISE.getText());
        mapCsvTitle.put(BaseExportTitle.STORE.getText(),
                BaseExportTitle.STORE.getText());
        mapCsvTitle.put(BaseExportTitle.ACTIVITY.getText(),
                BaseExportTitle.ACTIVITY.getText());
        mapCsvTitle.put(BaseExportTitle.ORDER_TIME.getText(),
                BaseExportTitle.ORDER_TIME.getText());
        mapCsvTitle.put(BaseExportTitle.STATUS.getText(),
                BaseExportTitle.STATUS.getText());

        titleList.add(BaseExportTitle.CODE.getText());
        titleList.add(BaseExportTitle.TEL.getText());
        keyList.add(BaseExportTitle.CODE.getText());
        keyList.add(BaseExportTitle.TEL.getText());
        mapCsvTitle.put(BaseExportTitle.CODE.getText(),
                BaseExportTitle.CODE.getText());
        mapCsvTitle.put(BaseExportTitle.TEL.getText(),
                BaseExportTitle.TEL.getText());
    }

    public void setTitleAndKey(List<String> titleList, List<String> keyList,
                               Map<String, String> mapCsvTitle, String orderType) {
        titleList.add(BaseExportTitle.ORDER_ID.getText());
        titleList.add(BaseExportTitle.PUB_ENTERPRISE.getText());
        titleList.add(BaseExportTitle.SUP_ENTERPRISE.getText());
        titleList.add(BaseExportTitle.STORE.getText());
        titleList.add(BaseExportTitle.ACTIVITY.getText());
        titleList.add(BaseExportTitle.ORDER_TIME.getText());
        titleList.add(BaseExportTitle.STATUS.getText());


        keyList.add(BaseExportTitle.ORDER_ID.getText());
        keyList.add(BaseExportTitle.PUB_ENTERPRISE.getText());
        keyList.add(BaseExportTitle.SUP_ENTERPRISE.getText());
        keyList.add(BaseExportTitle.STORE.getText());
        keyList.add(BaseExportTitle.ACTIVITY.getText());
        keyList.add(BaseExportTitle.ORDER_TIME.getText());
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
        mapCsvTitle.put(BaseExportTitle.ORDER_TIME.getText(),
                BaseExportTitle.ORDER_TIME.getText());
        mapCsvTitle.put(BaseExportTitle.STATUS.getText(),
                BaseExportTitle.STATUS.getText());
        switch (orderType + "") {
            case "02":
                titleList.add(BaseExportTitle.CODE.getText());
                titleList.add(BaseExportTitle.TEL.getText());
                keyList.add(BaseExportTitle.CODE.getText());
                keyList.add(BaseExportTitle.TEL.getText());
                mapCsvTitle.put(BaseExportTitle.CODE.getText(),
                        BaseExportTitle.CODE.getText());
                mapCsvTitle.put(BaseExportTitle.TEL.getText(),
                        BaseExportTitle.TEL.getText());
                break;
            case "01":
                titleList.add(BaseExportTitle.CARD_NUM.getText());
                titleList.add(BaseExportTitle.INTEGRAL.getText());
                keyList.add(BaseExportTitle.CARD_NUM.getText());
                keyList.add(BaseExportTitle.INTEGRAL.getText());

                mapCsvTitle.put(BaseExportTitle.CARD_NUM.getText(),
                        BaseExportTitle.CARD_NUM.getText());
                mapCsvTitle.put(BaseExportTitle.INTEGRAL.getText(),
                        BaseExportTitle.INTEGRAL.getText());
                break;

        }

    }
}
