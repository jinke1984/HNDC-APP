package cn.com.jinke.wh_drugcontrol.word.model;

import java.io.Serializable;

/**
 * Rule
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/9/8
 */

public class Rule implements Serializable {

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
    private String title;
    private String sendDate;
    private String sendLaction;
    private String sendType;
    private String sendTypeDetail;
    private String sendPerson;
    private String isDeny;
    private String denyReason;
    private String isReplace;
    private String replacePerson;
    private String replaceReason;
    private String relation;
    private String witness;

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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendLaction(String sendLaction) {
        this.sendLaction = sendLaction;
    }

    public String getSendLaction() {
        return sendLaction;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendTypeDetail(String sendTypeDetail) {
        this.sendTypeDetail = sendTypeDetail;
    }

    public String getSendTypeDetail() {
        return sendTypeDetail;
    }

    public void setSendPerson(String sendPerson) {
        this.sendPerson = sendPerson;
    }

    public String getSendPerson() {
        return sendPerson;
    }

    public void setIsDeny(String isDeny) {
        this.isDeny = isDeny;
    }

    public String getIsDeny() {
        return isDeny;
    }

    public void setDenyReason(String denyReason) {
        this.denyReason = denyReason;
    }

    public String getDenyReason() {
        return denyReason;
    }

    public void setIsReplace(String isReplace) {
        this.isReplace = isReplace;
    }

    public String getIsReplace() {
        return isReplace;
    }

    public void setReplacePerson(String replacePerson) {
        this.replacePerson = replacePerson;
    }

    public String getReplacePerson() {
        return replacePerson;
    }

    public void setReplaceReason(String replaceReason) {
        this.replaceReason = replaceReason;
    }

    public String getReplaceReason() {
        return replaceReason;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    public String getRelation() {
        return relation;
    }

    public void setWitness(String witness) {
        this.witness = witness;
    }

    public String getWitness() {
        return witness;
    }

}
