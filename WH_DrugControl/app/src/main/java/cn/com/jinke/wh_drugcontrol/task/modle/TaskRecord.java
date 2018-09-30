package cn.com.jinke.wh_drugcontrol.task.modle;

import java.io.Serializable;

public class TaskRecord implements Serializable{

    private String id;
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
    private String auditDate;
    private String auditDept;
    private String auditPerson;
    private String currentId;
    private String modifyItem;
    private String deleteReason;
    private String leaderComment;
    private String processInstanceId;
    private String updateModel;
    private String deleteModel;
    private String zhuanGanProcessing;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

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

    public void setCurrentId(String currentId) {
        this.currentId = currentId;
    }

    public String getCurrentId() {
        return currentId;
    }

    public void setModifyItem(String modifyItem) {
        this.modifyItem = modifyItem;
    }

    public String getModifyItem() {
        return modifyItem;
    }

    public void setDeleteReason(String deleteReason) {
        this.deleteReason = deleteReason;
    }

    public String getDeleteReason() {
        return deleteReason;
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

    public void setUpdateModel(String updateModel) {
        this.updateModel = updateModel;
    }

    public String getUpdateModel() {
        return updateModel;
    }

    public void setDeleteModel(String deleteModel) {
        this.deleteModel = deleteModel;
    }

    public String getDeleteModel() {
        return deleteModel;
    }

    public String getZhuanGanProcessing() {
        return zhuanGanProcessing;
    }

    public void setZhuanGanProcessing(String zhuanGanProcessing) {
        this.zhuanGanProcessing = zhuanGanProcessing;
    }
}
