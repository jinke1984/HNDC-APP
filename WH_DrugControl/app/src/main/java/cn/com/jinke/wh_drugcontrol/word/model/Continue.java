package cn.com.jinke.wh_drugcontrol.word.model;

import java.io.Serializable;

/**
 * Continue
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/9/11
 */

public class Continue implements Serializable {

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
    private String breakID;
    private String community;
    private String breakDate;
    private String continueDate;
    private String breakReason;
    private String continueReason;

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

    public void setBreakID(String breakID) {
        this.breakID = breakID;
    }

    public String getBreakID() {
        return breakID;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getCommunity() {
        return community;
    }

    public void setBreakDate(String breakDate) {
        this.breakDate = breakDate;
    }

    public String getBreakDate() {
        return breakDate;
    }

    public void setContinueDate(String continueDate) {
        this.continueDate = continueDate;
    }

    public String getContinueDate() {
        return continueDate;
    }

    public void setBreakReason(String breakReason) {
        this.breakReason = breakReason;
    }

    public String getBreakReason() {
        return breakReason;
    }

    public void setContinueReason(String continueReason) {
        this.continueReason = continueReason;
    }

    public String getContinueReason() {
        return continueReason;
    }

}
