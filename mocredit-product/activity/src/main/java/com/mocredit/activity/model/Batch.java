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
    private int importNumber;
    //导入成功联系人数量,import_success_number
    private int importSuccessNumber;
    //导入失败联系人数量,import_fail_number
    private int importFailNumber;
    //提码数量,pick_number
    private int pickNumber;
    //提码成功数量,pick_success_number
    private int pickSuccessNumber;
    //提码失败数量,pick_fail_number
    private int pickFailNumber;
    //送码数量,carry_number
    private int carryNumber;
    //送码成功数量,carry_success_number
    private int carrySuccessNumber;
    //送码失败数量,carray_fail_number
    private int carryFailNumber;
    //发码数量,send_number
    private int sendNumber;
    //发码成功数量,send_success_number
    private int sendSuccessNumber;
    //发码失败数量,send_fail_number
    private int sendFailNumber;
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

    public int getImportNumber() {
        return importNumber;
    }

    public void setImportNumber(int importNumber) {
        this.importNumber = importNumber;
    }

    public int getImportSuccessNumber() {
        return importSuccessNumber;
    }

    public void setImportSuccessNumber(int importSuccessNumber) {
        this.importSuccessNumber = importSuccessNumber;
    }

    public int getImportFailNumber() {
        return importFailNumber;
    }

    public void setImportFailNumber(int importFailNumber) {
        this.importFailNumber = importFailNumber;
    }

    public int getPickNumber() {
        return pickNumber;
    }

    public void setPickNumber(int pickNumber) {
        this.pickNumber = pickNumber;
    }

    public int getPickSuccessNumber() {
        return pickSuccessNumber;
    }

    public void setPickSuccessNumber(int pickSuccessNumber) {
        this.pickSuccessNumber = pickSuccessNumber;
    }

    public int getPickFailNumber() {
        return pickFailNumber;
    }

    public void setPickFailNumber(int pickFailNumber) {
        this.pickFailNumber = pickFailNumber;
    }

    public int getCarryNumber() {
        return carryNumber;
    }

    public void setCarryNumber(int carryNumber) {
        this.carryNumber = carryNumber;
    }

    public int getCarrySuccessNumber() {
        return carrySuccessNumber;
    }

    public void setCarrySuccessNumber(int carrySuccessNumber) {
        this.carrySuccessNumber = carrySuccessNumber;
    }

    public int getCarryFailNumber() {
        return carryFailNumber;
    }

    public void setCarryFailNumber(int carryFailNumber) {
        this.carryFailNumber = carryFailNumber;
    }

    public int getSendNumber() {
        return sendNumber;
    }

    public void setSendNumber(int sendNumber) {
        this.sendNumber = sendNumber;
    }

    public int getSendSuccessNumber() {
        return sendSuccessNumber;
    }

    public void setSendSuccessNumber(int sendSuccessNumber) {
        this.sendSuccessNumber = sendSuccessNumber;
    }

    public int getSendFailNumber() {
        return sendFailNumber;
    }

    public void setSendFailNumber(int sendFailNumber) {
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
