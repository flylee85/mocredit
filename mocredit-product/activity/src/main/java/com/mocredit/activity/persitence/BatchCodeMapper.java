package com.mocredit.activity.persitence;

import java.util.List;
import java.util.Map;

import com.mocredit.activity.model.BatchCode;

/**
 * 发码批次码-Dao 接口
 *
 * @author lishoukun
 * @date 2015/07/13
 */
public interface BatchCodeMapper {
    /*
     * 发码批次码
     */
    //获取一条发码批次码，根据主键
    BatchCode getBatchCodeById(String id);

    //获取一条发码批次码，随机获取
    BatchCode getBatchCodeByRand();

    //查询发码批次码列表，根据条件
    List<BatchCode> queryBatchCodeList(Map<String, Object> batchCodeMap);

    //查询发码批次码列表，根据条件
    List<BatchCode> queryBPBatchCodeList(Map<String, Object> batchCodeMap);

    //获取发码批次码总数量，根据条件
    int getBatchCodeTotal(Map<String, Object> batchCodeMap);

    //添加一条发码批次码记录
    int addBatchCode(BatchCode batchCode);

    //批量添加码批次记录
    int batchAddBatchCode(List<BatchCode> batchCodeList);

    //修改一条发码批次码记录
    int updateBatchCode(BatchCode batchCode);

    //删除一条发码批次码记录 ，根据主键
    int deleteBatchCodeById(String id);

    //删除发码批次码记录 ，根据条件
    int deleteBatchCode(Map<String, Object> batchCodeMap);

    //更新码
    int updateBatchCodeByBatchId(Map<String, Object> batchCodeMap);

    //按照批次表id更新码的状态
    int updateBatchCodeById(Map<String, Object> batchCodeMap);
}
