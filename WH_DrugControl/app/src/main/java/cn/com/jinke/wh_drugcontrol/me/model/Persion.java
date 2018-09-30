package cn.com.jinke.wh_drugcontrol.me.model;

import java.io.Serializable;

/**
 * Created by jinke on 2017/8/2.
 * 吸毒人员实体类
 */

public class Persion implements Serializable {


    /**
     * id : 61196d600e544eebabf936f6cd6849eb
     * realName : 5+6
     * sex : 1
     * birthdate : 1992-10-25 00:00:00
     * identityCard : 370300199210250130
     * communityID : 520008001008008008005008003
     * communityName : 友谊居委会
     * householdAddressCode : 13a6e7caf8fc4a9aaa44705087d841b3
     * householdAddressName : 贵阳市开阳县紫兴社区东兴居委会
     * householdAddress : 贵阳市开阳县紫兴社区东兴居委会(xxx)号
     * currentAddressCode : 13a6e7caf8fc4a9aaa44705087d841b3
     * currentAddressName : 贵阳市开阳县紫兴社区东兴居委会
     * currentAddress : 贵阳市开阳县紫兴社区东兴居委会(xxx)号
     * cellphone : 13123231232
     * controlId : ba27246c596b492780ba32859ed95dbc
     * controlName : 牟丽娜
     * photoAddress : images/defaultImg.jpg
     * allotAreaId : null
     * personNum : R20170912115610543
     * allotStatus : 1
     * createUserId : 1f3f60e1ab254dc59cf7a6bab3d9386c
     * createUserName : 干
     * createTime : 2017-09-12 11:56:25
     * updateUserId : ef8187768af74e72ac79e5ddf1775923
     * updateUserName : 蔡军
     * updateTime : 2017-09-28 09:44:55
     * optLock : 23
     * dataState : 1
     * dealStatus : 0
     * type : 1
     */

    private String id;
    private String realName;
    private String sex;
    private String birthdate;
    private String identityCard;
    private String communityID;
    private String communityName;
    private String householdAddressCode;
    private String householdAddressName;
    private String householdAddress;
    private String currentAddressCode;
    private String currentAddressName;
    private String currentAddress;
    private String cellphone;
    private String controlId;
    private String controlName;
    private String photoAddress;
    private String allotAreaId;
    private String personNum;
    private String allotStatus;
    private String createUserId;
    private String createUserName;
    private String createTime;
    private String updateUserId;
    private String updateUserName;
    private String updateTime;
    private String preRealName;
    private String education;
    private int optLock;
    private int dataState;
    private int dealStatus;
    private String type;
    private String personID;

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    /**
     * 本月积分
     */
    private int score;

    /**
     * 上月积分
     */
    private int lastTimeScore;

    /**
     * 管控等级
     */
    private int controlLevel;

    /**
     * 登记表照片路径
     */
    private String photoPath;

    /**
     * 吸毒人员是否被收藏 0是未收藏 1是已收藏
     */
    private int isCollection;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getCommunityID() {
        return communityID;
    }

    public void setCommunityID(String communityID) {
        this.communityID = communityID;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getHouseholdAddressCode() {
        return householdAddressCode;
    }

    public void setHouseholdAddressCode(String householdAddressCode) {
        this.householdAddressCode = householdAddressCode;
    }

    public String getHouseholdAddressName() {
        return householdAddressName;
    }

    public void setHouseholdAddressName(String householdAddressName) {
        this.householdAddressName = householdAddressName;
    }

    public String getHouseholdAddress() {
        return householdAddress;
    }

    public void setHouseholdAddress(String householdAddress) {
        this.householdAddress = householdAddress;
    }

    public String getCurrentAddressCode() {
        return currentAddressCode;
    }

    public void setCurrentAddressCode(String currentAddressCode) {
        this.currentAddressCode = currentAddressCode;
    }

    public String getCurrentAddressName() {
        return currentAddressName;
    }

    public void setCurrentAddressName(String currentAddressName) {
        this.currentAddressName = currentAddressName;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getControlId() {
        return controlId;
    }

    public void setControlId(String controlId) {
        this.controlId = controlId;
    }

    public String getControlName() {
        return controlName;
    }

    public void setControlName(String controlName) {
        this.controlName = controlName;
    }

    public String getPhotoAddress() {
        return photoAddress;
    }

    public void setPhotoAddress(String photoAddress) {
        this.photoAddress = photoAddress;
    }

    public String getAllotAreaId() {
        return allotAreaId;
    }

    public void setAllotAreaId(String allotAreaId) {
        this.allotAreaId = allotAreaId;
    }

    public String getPersonNum() {
        return personNum;
    }

    public void setPersonNum(String personNum) {
        this.personNum = personNum;
    }

    public String getAllotStatus() {
        return allotStatus;
    }

    public void setAllotStatus(String allotStatus) {
        this.allotStatus = allotStatus;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getOptLock() {
        return optLock;
    }

    public void setOptLock(int optLock) {
        this.optLock = optLock;
    }

    public int getDataState() {
        return dataState;
    }

    public void setDataState(int dataState) {
        this.dataState = dataState;
    }

    public int getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(int dealStatus) {
        this.dealStatus = dealStatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPreRealName() {
        return preRealName;
    }

    public void setPreRealName(String preRealName) {
        this.preRealName = preRealName;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLastTimeScore() {
        return lastTimeScore;
    }

    public void setLastTimeScore(int lastTimeScore) {
        this.lastTimeScore = lastTimeScore;
    }

    public int getControlLevel() {
        return controlLevel;
    }

    public void setControlLevel(int controlLevel) {
        this.controlLevel = controlLevel;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public int getIsCollection() {
        return isCollection;
    }

    public void setIsCollection(int isCollection) {
        this.isCollection = isCollection;
    }
}
