package cn.com.jinke.wh_drugcontrol.word.model;

import java.io.Serializable;

/**
 * Plan
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/9/8
 */

public class Plan implements Serializable {

    private String createUserId;
    private String createUserName;
    private String createTime;
    private String updateUserId;
    private String updateUserName;
    private String updateTime;
    private int optLock;
    private int dataState;
    private int dealStatus;
    private String personId;
    private String docId;
    private String realName;
    private String cellphone;
    private String identityCard;
    private String fileAdd;
    private String type;
    private String id;
    private String signDate;
    private String stampDate;
    private String planComment;
    private String auditDate;
    private String auditDept;
    private String auditPerson;
    private String auditPersonId;
    private String leaderComment;
    private String processInstanceId;

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setOptLock(int optLock) {
        this.optLock = optLock;
    }

    public int getOptLock() {
        return optLock;
    }

    public void setDataState(int dataState) {
        this.dataState = dataState;
    }

    public int getDataState() {
        return dataState;
    }

    public void setDealStatus(int dealStatus) {
        this.dealStatus = dealStatus;
    }

    public int getDealStatus() {
        return dealStatus;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDocId() {
        return docId;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRealName() {
        return realName;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setFileAdd(String fileAdd) {
        this.fileAdd = fileAdd;
    }

    public String getFileAdd() {
        return fileAdd;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setStampDate(String stampDate) {
        this.stampDate = stampDate;
    }

    public String getStampDate() {
        return stampDate;
    }

    public void setPlanComment(String planComment) {
        this.planComment = planComment;
    }

    public String getPlanComment() {
        return planComment;
    }

    public void setAuditDate(String auditDate) {
        this.auditDate = auditDate;
    }

    public String getAuditDate() {
        return auditDate;
    }

    public void setAuditDept(String auditDept) {
        this.auditDept = auditDept;
    }

    public String getAuditDept() {
        return auditDept;
    }

    public void setAuditPerson(String auditPerson) {
        this.auditPerson = auditPerson;
    }

    public String getAuditPerson() {
        return auditPerson;
    }

    public void setAuditPersonId(String auditPersonId) {
        this.auditPersonId = auditPersonId;
    }

    public String getAuditPersonId() {
        return auditPersonId;
    }

    public void setLeaderComment(String leaderComment) {
        this.leaderComment = leaderComment;
    }

    public String getLeaderComment() {
        return leaderComment;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

}
