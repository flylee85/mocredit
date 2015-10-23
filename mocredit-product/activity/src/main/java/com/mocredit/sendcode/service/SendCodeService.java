package com.mocredit.sendcode.service;

import com.mocredit.sendcode.BaseService;

import java.util.List;

/**
 * Created by ytq on 2015/10/23.
 * 发码组建接口
 */
public interface SendCodeService extends BaseService {
    /**
     * 根据type,活动，批次，码和id
     * 提取codeCount数量的码
     *
     * @param type      活动，批次，码
     * @param id        指定type的id
     * @param codeCount 提码数量
     * @return
     */
    List<Object> downloadList(String type, String id, Integer codeCount);

    /**
     * 查询活动id的批次列表
     *
     * @param actId    活动id
     * @param pageNum  页数
     * @param pageSize 页面数量
     * @return
     */
    List<Object> getActBatchList(String actId, Integer pageNum, Integer pageSize);

    /**
     * 查询指定批次的详情信息
     *
     * @param batchId  批次id
     * @param pageNum  页数
     * @param pageSize 页面数量
     * @return
     */
    List<Object> getBatchDetailList(String batchId, Integer pageNum, Integer pageSize);

    /**
     * 上传并发送短信到用户
     *
     * @param type               1，合并重复号码，并只发一个码 2，不合并重复号码，发送多个码
     * @param customerMobileList 用户手机列表信息
     * @return
     */
    Object uploadAndSend(String type, List<Object> customerMobileList);

    /**
     * 查询相应类型的用户并发码
     *
     * @param type 1,批次 2，码
     * @param id   对应类型的id
     * @return
     */
    Object sendCode(String type, String id);
}
