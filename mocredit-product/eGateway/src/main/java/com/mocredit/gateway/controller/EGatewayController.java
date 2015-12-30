package com.mocredit.gateway.controller;

import com.alibaba.fastjson.JSON;
import com.mocredit.base.datastructure.ResponseData;
import com.mocredit.base.datastructure.impl.AjaxResponseData;
import com.mocredit.base.pagehelper.PageInfo;
import com.mocredit.gateway.constant.CardRule;
import com.mocredit.gateway.constant.ErrorCodeType;
import com.mocredit.gateway.constant.ReqType;
import com.mocredit.gateway.entity.Activity;
import com.mocredit.gateway.entity.Record;
import com.mocredit.gateway.entity.Response;
import com.mocredit.gateway.service.ActivityService;
import com.mocredit.gateway.service.RecordService;
import com.mocredit.gateway.vo.ActivityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 对网关接口
 *
 * @author ytq 2015年8月25日
 */

@RestController
@RequestMapping("/")
public class EGatewayController extends EGatewayBaseController {
    private final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory
            .getLogger("EGatewayController");
    @Autowired
    private ActivityService activityService;

    @RequestMapping("/queryActivityPage")
    @ResponseBody
    public String queryActivityPage(@RequestParam Map<String, Object> reqMap, Integer draw, Integer start,
                                    Integer length) {
        // 定义返回页面的对象
        ResponseData responseData = new AjaxResponseData();
        // 简单计算页数，当前页数=开始条数/搜索条数+1
        if (start == null) {
            start = 0;
        }
        if (length == null) {
            length = 10;
        }
        int currentPage = start / length + 1;
        try {
            // 根据参数查询分页信息对象
            PageInfo<Activity> pageMap = new PageInfo<Activity>(activityService.queryActivityPage(reqMap, draw, currentPage, length));
            // 重构新的分页对象，为适应前端分页插件
            Map<String, Object> newMap = new HashMap<String, Object>();
            newMap.put("draw", draw);// 查询标示,原值返回
            newMap.put("recordsTotal", pageMap.getTotal());// 总数量
            newMap.put("recordsFiltered", pageMap.getTotal());// 过滤后的总数量，暂未用到
            newMap.put("data", pageMap.getList());// 数据列表
            String resultStr = JSON.toJSONString(newMap);// 将新的分页对象返回页面
            // 返回页面数据
            return resultStr;
        } catch (Exception e) {
            // 如果抛出异常，则将返回页面的对象设置为false
            e.printStackTrace();
            responseData.setSuccess(false);
            responseData.setErrorMsg(e.getMessage(), e);
        }
        // 返回页面数据
        return JSON.toJSONString(responseData);


    }

    @RequestMapping("/updateActivityById")
    @ResponseBody
    public String updateStatus(@RequestBody String body) {
        // 定义返回页面的对象
        ResponseData responseData = new AjaxResponseData();
        try {
            // 将前端传递过来的字符串数据解析为活动对象
            Activity activity = JSON.parseObject(body, Activity.class);
            // 定义影响行数为0
            // 更新活动对象
            activityService.updateActivityById(activity);
            // 如果程序执行到这里没有发生异常，则证明该操作成功执行,将获取到的数据放到返回页面的对象中
            responseData.setData(1);
        } catch (Exception e) {
            // 如果抛出异常，则将返回页面的对象设置为false
            e.printStackTrace();
            responseData.setSuccess(false);
            responseData.setErrorMsg(e.getMessage(), e);
        }
        // 返回页面数据
        return JSON.toJSONString(responseData);
    }

    @RequestMapping("/saveActivity")
    @ResponseBody
    public String saveActivity(ActivityVo activity) {
        // 定义返回页面的对象
        ResponseData responseData = new AjaxResponseData();
        try {
            Activity act = activityService.getByActivityCode(activity.getCode());

            // 更新活动对象
            if (activity.getId() != null) {
                if (act == null || activity.getId().equals(act.getId())) {
                    activityService.updateActivityById(activity);
                } else {
                    responseData.setSuccess(false);
                    responseData.setErrorMsg("活动编码已存在");
                }
            } else {
                if (act == null) {
                    activityService.save(activity);
                } else {
                    responseData.setSuccess(false);
                    responseData.setErrorMsg("活动编码已存在");
                }
            }
            // 如果程序执行到这里没有发生异常，则证明该操作成功执行,将获取到的数据放到返回页面的对象中
            responseData.setData(1);
        } catch (Exception e) {
            // 如果抛出异常，则将返回页面的对象设置为false
            e.printStackTrace();
            responseData.setSuccess(false);
            responseData.setErrorMsg(e.getMessage(), e);
        }
        // 返回页面数据
        return JSON.toJSONString(responseData);
    }

    @RequestMapping("/getActivityById")
    @ResponseBody
    public String getActivityById(Integer id) {
        // 定义返回页面的对象
        ResponseData responseData = new AjaxResponseData();
        try {
            // 更新活动对象
            ActivityVo activityVo = activityService.getActivityById(id);
            if (activityVo != null && activityVo.getRule() != null) {
                for (String rule : activityVo.getRule().split(";")) {
                    String k = rule.split(":")[0];
                    Integer v = Integer.valueOf(rule.split(":")[1]);
                    switch (k) {
                        case CardRule.CardDayMax:
                            activityVo.setCardDayMax(v);
                            break;
                        case CardRule.CardWeekMax:
                            activityVo.setCardWeekMax(v);
                            break;
                        case CardRule.CardMonthMax:
                            activityVo.setCardMonthMax(v);
                            break;
                        case CardRule.CardYearMax:
                            activityVo.setCardYearMax(v);
                            break;
                        case CardRule.CardTotalMax:
                            activityVo.setCardTotalMax(v);
                            break;
                    }
                }
            }
            responseData.setData(activityVo);
        } catch (Exception e) {
            // 如果抛出异常，则将返回页面的对象设置为false
            e.printStackTrace();
            responseData.setSuccess(false);
            responseData.setErrorMsg(e.getMessage(), e);
        }
        // 返回页面数据
        return JSON.toJSONString(responseData);
    }
}
