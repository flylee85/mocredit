package com.mocredit.activitysys.controller;

import com.alibaba.fastjson.JSON;
import com.mocredit.activity.model.BatchBvo;
import com.mocredit.activity.model.BatchCode;
import com.mocredit.activity.model.BatchCodeBvo;
import com.mocredit.base.datastructure.ResponseData;
import com.mocredit.base.datastructure.impl.AjaxResponseData;
import com.mocredit.base.pagehelper.PageInfo;
import com.mocredit.base.util.ExcelTool;
import com.mocredit.base.util.IDUtil;
import com.mocredit.sendcode.constant.DownloadType;
import com.mocredit.sendcode.service.SendCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ytq on 2015/10/24.
 */
@Controller
@RequestMapping("/sendCode/")
public class SendCodeController {
    @Autowired
    private SendCodeService sendCodeService;

    @RequestMapping("/delBatchById")
    @ResponseBody
    public String delBatchById(String batchId) {
        sendCodeService.delBatchById(batchId);
        return JSON.toJSONString(new AjaxResponseData());
    }

    @RequestMapping("/downloadTemplate")
    @ResponseBody
    public void downloadTemplate(HttpServletResponse response) {
        byte[] data = getBytes(this.getClass().getResource("/template/").getPath() + "发码模板.xls");
        response.reset();
        response.setContentType("application/x-download");
        try {
            response.setHeader("Content-Disposition", "attachment;filename="
                    + new String("发码模板.xls".getBytes("UTF-8"), "iso-8859-1"));
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            outputStream.write(data);
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequestMapping("/download")
    @ResponseBody
    public String download(String type, String id, String name, Integer count, HttpServletResponse response) {
        //定义返回页面的对象
        ResponseData responseData = new AjaxResponseData();
        try {
            List<String> keyList = new ArrayList<String>();
            List<String> titleList = new ArrayList<String>();
            setTitleAndKey(titleList, keyList);
            List<Map> codeMap = new ArrayList<Map>();
            String fileName = name + "码.xlsx";
            response.reset();
            response.setContentType("application/x-download");
            try {
                response.setHeader("Content-Disposition", "attachment;filename="
                        + new String(fileName.getBytes("UTF-8"), "iso-8859-1"));
            } catch (Exception e) {
                e.printStackTrace();
            }
            List<String> codeList = sendCodeService.downloadList(type, id, count);
            for (String code : codeList) {
                Map dataMap = new HashMap();
                dataMap.put("码", code);
                codeMap.add(dataMap);
            }
            try {
                ExcelTool.download(fileName,
                        titleList.toArray(new String[titleList.size()]),
                        keyList.toArray(new String[keyList.size()]),
                        codeMap, response.getOutputStream());

            } catch (Exception e) {
                e.printStackTrace();
            }
            responseData.setData(0);
        } catch (Exception e) {
            //如果抛出异常，则将返回页面的对象设置为false
            e.printStackTrace();
            responseData.setSuccess(false);
            responseData.setErrorMsg(e.getMessage(), e);
        }
        //返回页面数据
        return JSON.toJSONString(responseData);
    }

    public void setTitleAndKey(List<String> titleList, List<String> keyList) {
        titleList.add("码");
        keyList.add("码");
    }

    /**
     * 提码列表页
     *
     * @param reqMap
     * @param draw
     * @param start
     * @param length
     * @return
     */
    @RequestMapping("/queryPickCodePage")
    @ResponseBody
    public String queryPickCodePage(@RequestParam Map<String, Object> reqMap, String actId, Integer draw, Integer start, Integer length) {
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
            reqMap.put("actId", actId);
            PageInfo<BatchBvo> pageMap = new PageInfo<BatchBvo>(sendCodeService.getActBatchList(reqMap, draw, currentPage, length));
            //重构新的分页对象，为适应前端分页插件
            Map<String, Object> newMap = new HashMap<String, Object>();
            newMap.put("draw", draw);//查询标示,原值返回
            newMap.put("recordsTotal", pageMap.getTotal());//总数量
            newMap.put("recordsFiltered", pageMap.getTotal());//过滤后的总数量，暂未用到
            newMap.put("data", pageMap.getList());//数据列表
            String resultStr = JSON.toJSONString(newMap);//将新的分页对象返回页面
            //返回页面数据
            return resultStr;
        } catch (Exception e) {
            //如果抛出异常，则将返回页面的对象设置为false
            e.printStackTrace();
            responseData.setSuccess(false);
            responseData.setErrorMsg(e.getMessage(), e);
        }
        //返回页面数据
        return JSON.toJSONString(responseData);
    }

    /**
     * 提码列表页
     *
     * @param reqMap
     * @param draw
     * @param start
     * @param length
     * @return
     */
    @RequestMapping("/queryBatchDetailPage")
    @ResponseBody
    public String queryBatchDetailPage(@RequestParam Map<String, Object> reqMap, String batchId, Integer draw, Integer start, Integer length) {
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
            reqMap.put("batchId", batchId);
            PageInfo<BatchCodeBvo> pageMap = new PageInfo<BatchCodeBvo>(sendCodeService.getActBatchCodeList(reqMap, draw, currentPage, length));
            //重构新的分页对象，为适应前端分页插件
            Map<String, Object> newMap = new HashMap<String, Object>();
            newMap.put("draw", draw);//查询标示,原值返回
            newMap.put("recordsTotal", pageMap.getTotal());//总数量
            newMap.put("recordsFiltered", pageMap.getTotal());//过滤后的总数量，暂未用到
            newMap.put("data", pageMap.getList());//数据列表
            String resultStr = JSON.toJSONString(newMap);//将新的分页对象返回页面
            //返回页面数据
            return resultStr;
        } catch (Exception e) {
            //如果抛出异常，则将返回页面的对象设置为false
            e.printStackTrace();
            responseData.setSuccess(false);
            responseData.setErrorMsg(e.getMessage(), e);
        }
        //返回页面数据
        return JSON.toJSONString(responseData);
    }

    /**
     * 导入联系人(客户)
     *
     * @param selectExcel，前端传递到后台的文件流对象，在form表单中，name必须是selectExcel
     * @throws IOException
     */
    @RequestMapping("/importCustomer")
    @ResponseBody
    public String importCustomer(@RequestParam MultipartFile selectExcel, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //获取前端传递过来的活动id，和导入联系人备注
        String actId = request.getParameter("actId");
        String downloadChannel = request.getParameter("downloadChannel");
        String name = request.getParameter("name");
        //定义返回页面的对象
        ResponseData responseData = new AjaxResponseData();
        /*
         * 处理文件,处理excel数据
		 */
        if (selectExcel.getSize() > 0) {
            //如果文件大小大于0，说明文件上传成功
            //调用导入联系人方法
            Map<String, Object> operMap = sendCodeService.importCustomor(actId, IDUtil.getID(), name, downloadChannel, selectExcel.getInputStream());
            ;
            //获取导入联系人方法执行结果
            if (Boolean.parseBoolean(String.valueOf(operMap.get("success")))) {
                //如果导入结果为true，则只需要将导入消息设置为data就可以，因为返回页面的对象中，默认为true。
                responseData.setData(operMap.get("msg"));
            } else {
                //如果导入结果为false，则需要将返回页面的对象中设置为false，并且需要将导入消息设置为data就可以。
                responseData.setSuccess(false);
                responseData.setData(operMap.get("msg"));
            }
        } else {
            //如果文件大小为0，则将返回页面的对象设置为false
            responseData.setSuccess(false);
            responseData.setData("请上传格式正确的文件！");
        }
        //返回页面数据
        return JSON.toJSONString(responseData);
    }

    public static byte[] getBytes(String filePath) {
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
}
