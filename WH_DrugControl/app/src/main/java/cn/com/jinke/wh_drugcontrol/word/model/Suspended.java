package cn.com.jinke.wh_drugcontrol.word.model;

import java.io.Serializable;

/**
 * Suspended
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/9/7
 */

public class Suspended implements Serializable {

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
    private String community;
    private String breakReason;
    private String breakDate;
    private String approveDept;
    private String isolationPeriod;
    private String constraints;
    private String description;
    private String fileAddDesc;
    private String decideDept;
    private String breakStatus;

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

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getCommunity() {
        return community;
    }

    public void setBreakReason(String breakReason) {
        this.breakReason = breakReason;
    }

    public String getBreakReason() {
        return breakReason;
    }

    public void setBreakDate(String breakDate) {
        this.breakDate = breakDate;
    }

    public String getBreakDate() {
        return breakDate;
    }

    public void setApproveDept(String approveDept) {
        this.approveDept = approveDept;
    }

    public String getApproveDept() {
        return approveDept;
    }

    public void setIsolationPeriod(String isolationPeriod) {
        this.isolationPeriod = isolationPeriod;
    }

    public String getIsolationPeriod() {
        return isolationPeriod;
    }

    public void setConstraints(String constraints) {
        this.constraints = constraints;
    }

    public String getConstraints() {
        return constraints;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setFileAddDesc(String fileAddDesc) {
        this.fileAddDesc = fileAddDesc;
    }

    public String getFileAddDesc() {
        return fileAddDesc;
    }

    public void setDecideDept(String decideDept) {
        this.decideDept = decideDept;
    }

    public String getDecideDept() {
        return decideDept;
    }

    public void setBreakStatus(String breakStatus) {
        this.breakStatus = breakStatus;
    }

    public String getBreakStatus() {
        return breakStatus;
    }

}
