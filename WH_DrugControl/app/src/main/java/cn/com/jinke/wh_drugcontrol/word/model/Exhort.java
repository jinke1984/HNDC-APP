package cn.com.jinke.wh_drugcontrol.word.model;

import java.io.Serializable;

/**
 * Exhort
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/9/8
 */

public class Exhort implements Serializable {

    private String createUserId;
    private String createUserName;
    private String createTime;
    private String updateUserId;
    private String updateUserName;
    private String updateTime;
    private String optLock;
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
    private String violationId;
    private String noticeRemindID;
    private String noticeReason;
    private String noticeReasonRemark;
    private String noticeType;
    private String violationUserId;
    private String violationUser;
    private String occurDate;
    private String occurPlace;
    private String punishDept;
    private String witness;
    private String witnessPaperType;
    private String witnessPaperNO;
    private int leaveDays;
    private int leaveCounts;
    private int denyCounts;
    private String denyDate;
    private String sendDate;
    private String sendReason;

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

    public void setOptLock(String optLock) {
        this.optLock = optLock;
    }

    public String getOptLock() {
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

    public void setViolationId(String violationId) {
        this.violationId = violationId;
    }

    public String getViolationId() {
        return violationId;
    }

    public void setNoticeRemindID(String noticeRemindID) {
        this.noticeRemindID = noticeRemindID;
    }

    public String getNoticeRemindID() {
        return noticeRemindID;
    }

    public void setNoticeReason(String noticeReason) {
        this.noticeReason = noticeReason;
    }

    public String getNoticeReason() {
        return noticeReason;
    }

    public void setNoticeReasonRemark(String noticeReasonRemark) {
        this.noticeReasonRemark = noticeReasonRemark;
    }

    public String getNoticeReasonRemark() {
        return noticeReasonRemark;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setViolationUserId(String violationUserId) {
        this.violationUserId = violationUserId;
    }

    public String getViolationUserId() {
        return violationUserId;
    }

    public void setViolationUser(String violationUser) {
        this.violationUser = violationUser;
    }

    public String getViolationUser() {
        return violationUser;
    }

    public void setOccurDate(String occurDate) {
        this.occurDate = occurDate;
    }

    public String getOccurDate() {
        return occurDate;
    }

    public void setOccurPlace(String occurPlace) {
        this.occurPlace = occurPlace;
    }

    public String getOccurPlace() {
        return occurPlace;
    }

    public void setPunishDept(String punishDept) {
        this.punishDept = punishDept;
    }

    public String getPunishDept() {
        return punishDept;
    }

    public void setWitness(String witness) {
        this.witness = witness;
    }

    public String getWitness() {
        return witness;
    }

    public void setWitnessPaperType(String witnessPaperType) {
        this.witnessPaperType = witnessPaperType;
    }

    public String getWitnessPaperType() {
        return witnessPaperType;
    }

    public void setWitnessPaperNO(String witnessPaperNO) {
        this.witnessPaperNO = witnessPaperNO;
    }

    public String getWitnessPaperNO() {
        return witnessPaperNO;
    }

    public void setLeaveDays(int leaveDays) {
        this.leaveDays = leaveDays;
    }

    public int getLeaveDays() {
        return leaveDays;
    }

    public void setLeaveCounts(int leaveCounts) {
        this.leaveCounts = leaveCounts;
    }

    public int getLeaveCounts() {
        return leaveCounts;
    }

    public void setDenyCounts(int denyCounts) {
        this.denyCounts = denyCounts;
    }

    public int getDenyCounts() {
        return denyCounts;
    }

    public void setDenyDate(String denyDate) {
        this.denyDate = denyDate;
    }

    public String getDenyDate() {
        return denyDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendReason(String sendReason) {
        this.sendReason = sendReason;
    }

    public String getSendReason() {
        return sendReason;
    }

}
