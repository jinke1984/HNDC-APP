package cn.com.jinke.wh_drugcontrol.input.model;

import java.io.Serializable;

/**
 * @author jinke
 * @date 2018/3/6
 * @description
 */

public class Leave implements Serializable {

    private String id;
    private String createTime;
    private String createUserId;
    private String createUserName;

    /**
     *  档案ID
     */
    private String docId;

    /**
     *  附件地址
     */
    private String fileAdd;

    /**
     *  身份证号
     */
    private String identityCard;

    /**
     *  吸毒人员ID
     */
    private String personId;

    /**
     *  吸毒人员姓名
     */
    private String realName;

    /**
     *  类型 社区戒毒|社区康复
     */
    private String type;

    /**
     *  申请日期
     */
    private String applyDate;

    /**
     *  批准日期
     */
    private String approveDate;

    /**
     *  请假开始时间
     */
    private String beginDate;

    /**
     *  请假结束时间
     */
    private String endDate;

    /**
     *  销假日期
     */
    private String cancelDate;

    /**
     *  是否延期销假
     */
    private String delay;

    /**
     *  延期原因
     */
    private String delayReason;

    /**
     *  前往地址
     */
    private String destination;

    /**
     *  请假事由
     */
    private String leaveReason;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getFileAdd() {
        return fileAdd;
    }

    public void setFileAdd(String fileAdd) {
        this.fileAdd = fileAdd;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getApproveDate() {
        return approveDate;
    }

    public void setApproveDate(String approveDate) {
        this.approveDate = approveDate;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(String cancelDate) {
        this.cancelDate = cancelDate;
    }

    public String getDelay() {
        return delay;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public String getDelayReason() {
        return delayReason;
    }

    public void setDelayReason(String delayReason) {
        this.delayReason = delayReason;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getLeaveReason() {
        return leaveReason;
    }

    public void setLeaveReason(String leaveReason) {
        this.leaveReason = leaveReason;
    }
}
