package com.mocredit.activity.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;


/**
 * 发码批次-发码批次-实体类
 *
 * @author lishoukun
 * @date 2015/07/13
 */
public class Batch implements Serializable {
    //序列化
    private static final long serialVersionUID = 6905308258132311722L;
    //id,id
    private String id;
    //活动id,activity_id
    private String activityId;
    //批次号,batch
    private String batch;
    //导入联系人数量,import_number
    private Integer importNumber;
    //导入成功联系人数量,import_success_number
    private Integer importSuccessNumber;
    //导入失败联系人数量,import_fail_number
    private Integer importFailNumber;
    //提码数量,pick_number
    private Integer pickNumber;
    //提码成功数量,pick_success_number
    private Integer pickSuccessNumber;
    //提码失败数量,pick_fail_number
    private Integer pickFailNumber;
    //送码数量,carry_number
    private Integer carryNumber;
    //送码成功数量,carry_success_number
    private Integer carrySuccessNumber;
    //送码失败数量,carray_fail_number
    private Integer carryFailNumber;
    //发码数量,send_number
    private Integer sendNumber;
    //发码成功数量,send_success_number
    private Integer sendSuccessNumber;
    //发码失败数量,send_fail_number
    private Integer sendFailNumber;
    //备注,remark
    private String remark;
    //状态,status,暂时定为   00：未导入联系人，01：已导入联系人，待提码  02：已提码，待送码  03：已送码，待发码 04：已发码
    private String status;
    //创建时间,createtime
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createtime;
    //创建人,creator
    private String creator;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public Integer getImportNumber() {
        return importNumber;
    }

    public void setImportNumber(Integer importNumber) {
        this.importNumber = importNumber;
    }

    public Integer getImportSuccessNumber() {
        return importSuccessNumber;
    }

    public void setImportSuccessNumber(Integer importSuccessNumber) {
        this.importSuccessNumber = importSuccessNumber;
    }

    public Integer getImportFailNumber() {
        return importFailNumber;
    }

    public void setImportFailNumber(Integer importFailNumber) {
        this.importFailNumber = importFailNumber;
    }

    public Integer getPickNumber() {
        return pickNumber;
    }

    public void setPickNumber(Integer pickNumber) {
        this.pickNumber = pickNumber;
    }

    public Integer getPickSuccessNumber() {
        return pickSuccessNumber;
    }

    public void setPickSuccessNumber(Integer pickSuccessNumber) {
        this.pickSuccessNumber = pickSuccessNumber;
    }

    public Integer getPickFailNumber() {
        return pickFailNumber;
    }

    public void setPickFailNumber(Integer pickFailNumber) {
        this.pickFailNumber = pickFailNumber;
    }

    public Integer getCarryNumber() {
        return carryNumber;
    }

    public void setCarryNumber(Integer carryNumber) {
        this.carryNumber = carryNumber;
    }

    public Integer getCarrySuccessNumber() {
        return carrySuccessNumber;
    }

    public void setCarrySuccessNumber(Integer carrySuccessNumber) {
        this.carrySuccessNumber = carrySuccessNumber;
    }

    public Integer getCarryFailNumber() {
        return carryFailNumber;
    }

    public void setCarryFailNumber(Integer carryFailNumber) {
        this.carryFailNumber = carryFailNumber;
    }

    public Integer getSendNumber() {
        return sendNumber;
    }

    public void setSendNumber(Integer sendNumber) {
        this.sendNumber = sendNumber;
    }

    public Integer getSendSuccessNumber() {
        return sendSuccessNumber;
    }

    public void setSendSuccessNumber(Integer sendSuccessNumber) {
        this.sendSuccessNumber = sendSuccessNumber;
    }

    public Integer getSendFailNumber() {
        return sendFailNumber;
    }

    public void setSendFailNumber(Integer sendFailNumber) {
        this.sendFailNumber = sendFailNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }
}
