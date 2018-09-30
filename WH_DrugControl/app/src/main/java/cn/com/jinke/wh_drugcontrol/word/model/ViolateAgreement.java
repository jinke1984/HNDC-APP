package cn.com.jinke.wh_drugcontrol.word.model;

import java.io.Serializable;

/**
 * ViolateAgreement
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/9/6
 */

public class ViolateAgreement implements Serializable {

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
    private String punishCondition;
    private String repapse;
    private String violateConditino;
    private String violateLevel;
    private String violateDate;
    private String community;
    private String remark;

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

    public void setPunishCondition(String punishCondition) {
        this.punishCondition = punishCondition;
    }

    public String getPunishCondition() {
        return punishCondition;
    }

    public void setRepapse(String repapse) {
        this.repapse = repapse;
    }

    public String getRepapse() {
        return repapse;
    }

    public void setViolateConditino(String violateConditino) {
        this.violateConditino = violateConditino;
    }

    public String getViolateConditino() {
        return violateConditino;
    }

    public void setViolateLevel(String violateLevel) {
        this.violateLevel = violateLevel;
    }

    public String getViolateLevel() {
        return violateLevel;
    }

    public void setViolateDate(String violateDate) {
        this.violateDate = violateDate;
    }

    public String getViolateDate() {
        return violateDate;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getCommunity() {
        return community;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }
}
