package cn.com.jinke.wh_drugcontrol.word.model;

import java.io.Serializable;

/**
 * Decision
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/9/8
 */

public class Decision implements Serializable {

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
    private String isDecision;
    private String nonDecisionReason;
    private String decisionNo1;
    private String decisionNo2;
    private String decisionNo3;
    private String decisionNo4;
    private String drugsBeginDate;
    private String drugsEndDate;
    private String handlingDepartment;
    private String registerDate;
    private String actegisterDate;
    private String catchDeatil;
    private String exeComNameAndAddr;

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

    public void setIsDecision(String isDecision) {
        this.isDecision = isDecision;
    }

    public String getIsDecision() {
        return isDecision;
    }

    public void setNonDecisionReason(String nonDecisionReason) {
        this.nonDecisionReason = nonDecisionReason;
    }

    public String getNonDecisionReason() {
        return nonDecisionReason;
    }

    public void setDecisionNo1(String decisionNo1) {
        this.decisionNo1 = decisionNo1;
    }

    public String getDecisionNo1() {
        return decisionNo1;
    }

    public void setDecisionNo2(String decisionNo2) {
        this.decisionNo2 = decisionNo2;
    }

    public String getDecisionNo2() {
        return decisionNo2;
    }

    public void setDecisionNo3(String decisionNo3) {
        this.decisionNo3 = decisionNo3;
    }

    public String getDecisionNo3() {
        return decisionNo3;
    }

    public void setDecisionNo4(String decisionNo4) {
        this.decisionNo4 = decisionNo4;
    }

    public String getDecisionNo4() {
        return decisionNo4;
    }

    public void setDrugsBeginDate(String drugsBeginDate) {
        this.drugsBeginDate = drugsBeginDate;
    }

    public String getDrugsBeginDate() {
        return drugsBeginDate;
    }

    public void setDrugsEndDate(String drugsEndDate) {
        this.drugsEndDate = drugsEndDate;
    }

    public String getDrugsEndDate() {
        return drugsEndDate;
    }

    public void setHandlingDepartment(String handlingDepartment) {
        this.handlingDepartment = handlingDepartment;
    }

    public String getHandlingDepartment() {
        return handlingDepartment;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setActegisterDate(String actegisterDate) {
        this.actegisterDate = actegisterDate;
    }

    public String getActegisterDate() {
        return actegisterDate;
    }

    public void setCatchDeatil(String catchDeatil) {
        this.catchDeatil = catchDeatil;
    }

    public String getCatchDeatil() {
        return catchDeatil;
    }

    public void setExeComNameAndAddr(String exeComNameAndAddr) {
        this.exeComNameAndAddr = exeComNameAndAddr;
    }

    public String getExeComNameAndAddr() {
        return exeComNameAndAddr;
    }

}
