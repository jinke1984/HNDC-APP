package cn.com.jinke.wh_drugcontrol.input.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.MyTextUtils;

/**
 * CachePerson
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/9/14
 */

@Table(name = "t_cache_person")
public class CachePerson implements Serializable, CodeConstants {

    @Column(name = "_id", isId = true, property = "NOT NULL")
    private int _id;  // 默认表id

    @Column(name = "userId")
    private String userId = "";

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public CachePerson() {
    }

    public CachePerson(String userId) {
        this.userId = userId;
    }

    @Column(name = "createUserId")
    private String createUserId;
    @Column(name = "createUserName")
    private String createUserName;
    @Column(name = "createTime")
    private String createTime;
    @Column(name = "updateUserId")
    private String updateUserId;
    @Column(name = "updateUserName")
    private String updateUserName;
    @Column(name = "updateTime")
    private String updateTime;
    @Column(name = "optLock")
    private int optLock;
    @Column(name = "dataState")
    private int dataState;
    @Column(name = "dealStatus")
    private int dealStatus;
    @Column(name = "nation")
    private String nation;
    @Column(name = "height")
    private double height;
    @Column(name = "employmentCondition")
    private String employmentCondition;
    @Column(name = "firstDrugsDate")
    private String firstDrugsDate;
    @Column(name = "drugTypes")
    private String drugTypes;
    @Column(name = "drugTypesDetail")
    private String drugTypesDetail;
    @Column(name = "telePhone")
    private String telePhone;
    @Column(name = "qq")
    private String qq;
    @Column(name = "weixin")
    private String weixin;
    @Column(name = "mail")
    private String mail;
    @Column(name = "preRealName")
    private String preRealName;
    @Column(name = "nickname")
    private String nickname;
    @Column(name = "education")
    private String education;
    @Column(name = "marriage")
    private String marriage;
    @Column(name = "livingWithParents")
    private String livingWithParents;
    @Column(name = "vocationalTraining")
    private String vocationalTraining;
    @Column(name = "skills")
    private String skills;
    @Column(name = "skillsDetail")
    private String skillsDetail;
    @Column(name = "personalResume")
    private String personalResume;
    @Column(name = "politicalLandscape")
    private String politicalLandscape;
    @Column(name = "politicalLandscapeDetail")
    private String politicalLandscapeDetail;
    @Column(name = "jobCondition")
    private String jobCondition;
    @Column(name = "photoAddress")
    private String photoAddress;
    @Column(name = "fillingPerson")
    private String fillingPerson;
    @Column(name = "fillingDate")
    private String fillingDate;
    @Column(name = "gatherDepartment")
    private String gatherDepartment;
    @Column(name = "gatherDate")
    private String gatherDate;
    @Column(name = "oftenContactsPerson")
    private String oftenContactsPerson;
    @Column(name = "currentCondition")
    private String currentCondition;
    @Column(name = "crimeCondition")
    private String crimeCondition;
    @Column(name = "healthCondition")
    private String healthCondition;
    @Column(name = "healthConditionDetail")
    private String healthConditionDetail;
    @Column(name = "familyCondition")
    private String familyCondition;
    @Column(name = "familyConditionDetail")
    private String familyConditionDetail;
    @Column(name = "socialSecurity")
    private String socialSecurity;
    @Column(name = "socialSecurityDetail")
    private String socialSecurityDetail;
    @Column(name = "income")
    private String income;
    @Column(name = "controlId")
    private String controlId;
    @Column(name = "controlName")
    private String controlName;
    @Column(name = "longitude")
    private String longitude;
    @Column(name = "latitude")
    private String latitude;
    @Column(name = "gridPerson")
    private String gridPerson;
    @Column(name = "communityPolice")
    private String communityPolice;
    @Column(name = "deadRemark")
    private String deadRemark;
    @Column(name = "deadFileAdd")
    private String deadFileAdd;
    @Column(name = "docId")
    private String docId;
    @Column(name = "docNo")
    private String docNo;
    @Column(name = "weibo")
    private String weibo;
    @Column(name = "workingJob")
    private String workingJob;
    @Column(name = "dnaGather")
    private String dnaGather;
    @Column(name = "carInfo")
    private String carInfo;
    @Column(name = "drugYear")
    private String drugYear;
    @Column(name = "workTime")
    private String workTime;
    @Column(name = "insulateTime")
    private String insulateTime;
    @Column(name = "punishTime")
    private String punishTime;
    @Column(name = "householdAddresspcs")
    private String householdAddresspcs;
    @Column(name = "isHIV")
    private String isHIV;
    @Column(name = "otherHIV")
    private String otherHIV;
    @Column(name = "gatherDepartmentId")
    private String gatherDepartmentId;
    @Column(name = "gatherDepart")
    private String gatherDepart;
    @Column(name = "departmentcode")
    private String departmentcode;
    @Column(name = "localPoliceStation")
    private String localPoliceStation;
    @Column(name = "ploiceSuboffice")
    private String ploiceSuboffice;
    @Column(name = "policePhone")
    private String policePhone;
    @Column(name = "gridPersonPhone")
    private String gridPersonPhone;
    @Column(name = "policeFillDate")
    private String policeFillDate;
    @Column(name = "verifier")
    private String verifier;
    @Column(name = "verifierPhone")
    private String verifierPhone;
    @Column(name = "verifierFillDate")
    private String verifierFillDate;
    @Column(name = "houseInfo")
    private String houseInfo;
    @Column(name = "drivingLicence")
    private String drivingLicence;
    @Column(name = "fingerprintNumber")
    private String fingerprintNumber;
    @Column(name = "dnaNumber")
    private String dnaNumber;
    @Column(name = "meetPersonSign")
    private String meetPersonSign;
    @Column(name = "allotAreaId")
    private String allotAreaId;
    @Column(name = "workUint")
    private String workUint;
    @Column(name = "workStuDocPlace")
    private String workStuDocPlace;
    @Column(name = "communityIdCode")
    private String communityIdCode;
    @Column(name = "communityCode")
    private String communityCode;
    @Column(name = "communityID")
    private String communityID;
    @Column(name = "communityName")
    private String communityName;
    @Column(name = "realName")
    private String realName;
    @Column(name = "sex")
    private String sex;
    @Column(name = "birthdate")
    private String birthdate;
    @Column(name = "identityCard")
    private String identityCard;
    @Column(name = "householdProvince")
    private String householdProvince;
    @Column(name = "householdCity")
    private String householdCity;
    @Column(name = "householdCounty")
    private String householdCounty;
    @Column(name = "householdTown")
    private String householdTown;
    @Column(name = "householdVillage")
    private String householdVillage;
    @Column(name = "userIhouseholdAddressCoded")
    private String householdAddressCode;
    @Column(name = "householdAddressName")
    private String householdAddressName;
    @Column(name = "householdAddress")
    private String householdAddress;
    @Column(name = "currentProvince")
    private String currentProvince;
    @Column(name = "currentCity")
    private String currentCity;
    @Column(name = "currentCounty")
    private String currentCounty;
    @Column(name = "currentTown")
    private String currentTown;
    @Column(name = "currentVillage")
    private String currentVillage;
    @Column(name = "currentAddressCode")
    private String currentAddressCode;
    @Column(name = "currentAddressName")
    private String currentAddressName;
    @Column(name = "currentAddress")
    private String currentAddress;
    @Column(name = "cellphone")
    private String cellphone;
    @Column(name = "id")
    private String id;

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

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getNation() {
        return nation;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getHeight() {
        return height;
    }

    public void setEmploymentCondition(String employmentCondition) {
        this.employmentCondition = employmentCondition;
    }

    public String getEmploymentCondition() {
        return employmentCondition;
    }

    public void setFirstDrugsDate(String firstDrugsDate) {
        this.firstDrugsDate = firstDrugsDate;
    }

    public String getFirstDrugsDate() {
        return firstDrugsDate;
    }

    public void setDrugTypes(String drugTypes) {
        this.drugTypes = drugTypes;
    }

    public String getDrugTypes() {
        return drugTypes;
    }

    public void setDrugTypesDetail(String drugTypesDetail) {
        this.drugTypesDetail = drugTypesDetail;
    }

    public String getDrugTypesDetail() {
        return drugTypesDetail;
    }

    public void setTelePhone(String telePhone) {
        this.telePhone = telePhone;
    }

    public String getTelePhone() {
        return telePhone;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getQq() {
        return qq;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getMail() {
        return mail;
    }

    public void setPreRealName(String preRealName) {
        this.preRealName = preRealName;
    }

    public String getPreRealName() {
        return preRealName;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getEducation() {
        return education;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setLivingWithParents(String livingWithParents) {
        this.livingWithParents = livingWithParents;
    }

    public String getLivingWithParents() {
        return livingWithParents;
    }

    public void setVocationalTraining(String vocationalTraining) {
        this.vocationalTraining = vocationalTraining;
    }

    public String getVocationalTraining() {
        return vocationalTraining;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkillsDetail(String skillsDetail) {
        this.skillsDetail = skillsDetail;
    }

    public String getSkillsDetail() {
        return skillsDetail;
    }

    public void setPersonalResume(String personalResume) {
        this.personalResume = personalResume;
    }

    public String getPersonalResume() {
        return personalResume;
    }

    public void setPoliticalLandscape(String politicalLandscape) {
        this.politicalLandscape = politicalLandscape;
    }

    public String getPoliticalLandscape() {
        return politicalLandscape;
    }

    public void setPoliticalLandscapeDetail(String politicalLandscapeDetail) {
        this.politicalLandscapeDetail = politicalLandscapeDetail;
    }

    public String getPoliticalLandscapeDetail() {
        return politicalLandscapeDetail;
    }

    public void setJobCondition(String jobCondition) {
        this.jobCondition = jobCondition;
    }

    public String getJobCondition() {
        return jobCondition;
    }

    public void setPhotoAddress(String photoAddress) {
        this.photoAddress = photoAddress;
    }

    public String getPhotoAddress() {
        return photoAddress;
    }

    public void setFillingPerson(String fillingPerson) {
        this.fillingPerson = fillingPerson;
    }

    public String getFillingPerson() {
        return fillingPerson;
    }

    public void setFillingDate(String fillingDate) {
        this.fillingDate = fillingDate;
    }

    public String getFillingDate() {
        return fillingDate;
    }

    public void setGatherDepartment(String gatherDepartment) {
        this.gatherDepartment = gatherDepartment;
    }

    public String getGatherDepartment() {
        return gatherDepartment;
    }

    public void setGatherDate(String gatherDate) {
        this.gatherDate = gatherDate;
    }

    public String getGatherDate() {
        return gatherDate;
    }

    public void setOftenContactsPerson(String oftenContactsPerson) {
        this.oftenContactsPerson = oftenContactsPerson;
    }

    public String getOftenContactsPerson() {
        return oftenContactsPerson;
    }

    public void setCurrentCondition(String currentCondition) {
        this.currentCondition = currentCondition;
    }

    public String getCurrentCondition() {
        return currentCondition;
    }

    public void setCrimeCondition(String crimeCondition) {
        this.crimeCondition = crimeCondition;
    }

    public String getCrimeCondition() {
        return crimeCondition;
    }

    public void setHealthCondition(String healthCondition) {
        this.healthCondition = healthCondition;
    }

    public String getHealthCondition() {
        return healthCondition;
    }

    public void setHealthConditionDetail(String healthConditionDetail) {
        this.healthConditionDetail = healthConditionDetail;
    }

    public String getHealthConditionDetail() {
        return healthConditionDetail;
    }

    public void setFamilyCondition(String familyCondition) {
        this.familyCondition = familyCondition;
    }

    public String getFamilyCondition() {
        return familyCondition;
    }

    public void setFamilyConditionDetail(String familyConditionDetail) {
        this.familyConditionDetail = familyConditionDetail;
    }

    public String getFamilyConditionDetail() {
        return familyConditionDetail;
    }

    public void setSocialSecurity(String socialSecurity) {
        this.socialSecurity = socialSecurity;
    }

    public String getSocialSecurity() {
        return socialSecurity;
    }

    public void setSocialSecurityDetail(String socialSecurityDetail) {
        this.socialSecurityDetail = socialSecurityDetail;
    }

    public String getSocialSecurityDetail() {
        return socialSecurityDetail;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getIncome() {
        return income;
    }

    public void setControlId(String controlId) {
        this.controlId = controlId;
    }

    public String getControlId() {
        return controlId;
    }

    public void setControlName(String controlName) {
        this.controlName = controlName;
    }

    public String getControlName() {
        return controlName;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setGridPerson(String gridPerson) {
        this.gridPerson = gridPerson;
    }

    public String getGridPerson() {
        return gridPerson;
    }

    public void setCommunityPolice(String communityPolice) {
        this.communityPolice = communityPolice;
    }

    public String getCommunityPolice() {
        return communityPolice;
    }

    public void setDeadRemark(String deadRemark) {
        this.deadRemark = deadRemark;
    }

    public String getDeadRemark() {
        return deadRemark;
    }

    public void setDeadFileAdd(String deadFileAdd) {
        this.deadFileAdd = deadFileAdd;
    }

    public String getDeadFileAdd() {
        return deadFileAdd;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocNo(String docNo) {
        this.docNo = docNo;
    }

    public String getDocNo() {
        return docNo;
    }

    public void setWeibo(String weibo) {
        this.weibo = weibo;
    }

    public String getWeibo() {
        return weibo;
    }

    public void setWorkingJob(String workingJob) {
        this.workingJob = workingJob;
    }

    public String getWorkingJob() {
        return workingJob;
    }

    public void setDnaGather(String dnaGather) {
        this.dnaGather = dnaGather;
    }

    public String getDnaGather() {
        return dnaGather;
    }

    public void setCarInfo(String carInfo) {
        this.carInfo = carInfo;
    }

    public String getCarInfo() {
        return carInfo;
    }

    public void setDrugYear(String drugYear) {
        this.drugYear = drugYear;
    }

    public String getDrugYear() {
        return drugYear;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setInsulateTime(String insulateTime) {
        this.insulateTime = insulateTime;
    }

    public String getInsulateTime() {
        return insulateTime;
    }

    public void setPunishTime(String punishTime) {
        this.punishTime = punishTime;
    }

    public String getPunishTime() {
        return punishTime;
    }

    public void setHouseholdAddresspcs(String householdAddresspcs) {
        this.householdAddresspcs = householdAddresspcs;
    }

    public String getHouseholdAddresspcs() {
        return householdAddresspcs;
    }

    public void setIsHIV(String isHIV) {
        this.isHIV = isHIV;
    }

    public String getIsHIV() {
        return isHIV;
    }

    public void setOtherHIV(String otherHIV) {
        this.otherHIV = otherHIV;
    }

    public String getOtherHIV() {
        return otherHIV;
    }

    public void setGatherDepartmentId(String gatherDepartmentId) {
        this.gatherDepartmentId = gatherDepartmentId;
    }

    public String getGatherDepartmentId() {
        return gatherDepartmentId;
    }

    public void setGatherDepart(String gatherDepart) {
        this.gatherDepart = gatherDepart;
    }

    public String getGatherDepart() {
        return gatherDepart;
    }

    public void setDepartmentcode(String departmentcode) {
        this.departmentcode = departmentcode;
    }

    public String getDepartmentcode() {
        return departmentcode;
    }

    public void setLocalPoliceStation(String localPoliceStation) {
        this.localPoliceStation = localPoliceStation;
    }

    public String getLocalPoliceStation() {
        return localPoliceStation;
    }

    public void setPloiceSuboffice(String ploiceSuboffice) {
        this.ploiceSuboffice = ploiceSuboffice;
    }

    public String getPloiceSuboffice() {
        return ploiceSuboffice;
    }

    public void setPolicePhone(String policePhone) {
        this.policePhone = policePhone;
    }

    public String getPolicePhone() {
        return policePhone;
    }

    public void setGridPersonPhone(String gridPersonPhone) {
        this.gridPersonPhone = gridPersonPhone;
    }

    public String getGridPersonPhone() {
        return gridPersonPhone;
    }

    public void setPoliceFillDate(String policeFillDate) {
        this.policeFillDate = policeFillDate;
    }

    public String getPoliceFillDate() {
        return policeFillDate;
    }

    public void setVerifier(String verifier) {
        this.verifier = verifier;
    }

    public String getVerifier() {
        return verifier;
    }

    public void setVerifierPhone(String verifierPhone) {
        this.verifierPhone = verifierPhone;
    }

    public String getVerifierPhone() {
        return verifierPhone;
    }

    public void setVerifierFillDate(String verifierFillDate) {
        this.verifierFillDate = verifierFillDate;
    }

    public String getVerifierFillDate() {
        return verifierFillDate;
    }

    public void setHouseInfo(String houseInfo) {
        this.houseInfo = houseInfo;
    }

    public String getHouseInfo() {
        return houseInfo;
    }

    public void setDrivingLicence(String drivingLicence) {
        this.drivingLicence = drivingLicence;
    }

    public String getDrivingLicence() {
        return drivingLicence;
    }

    public void setFingerprintNumber(String fingerprintNumber) {
        this.fingerprintNumber = fingerprintNumber;
    }

    public String getFingerprintNumber() {
        return fingerprintNumber;
    }

    public void setDnaNumber(String dnaNumber) {
        this.dnaNumber = dnaNumber;
    }

    public String getDnaNumber() {
        return dnaNumber;
    }

    public void setMeetPersonSign(String meetPersonSign) {
        this.meetPersonSign = meetPersonSign;
    }

    public String getMeetPersonSign() {
        return meetPersonSign;
    }

    public void setAllotAreaId(String allotAreaId) {
        this.allotAreaId = allotAreaId;
    }

    public String getAllotAreaId() {
        return allotAreaId;
    }

    public void setWorkUint(String workUint) {
        this.workUint = workUint;
    }

    public String getWorkUint() {
        return workUint;
    }

    public void setWorkStuDocPlace(String workStuDocPlace) {
        this.workStuDocPlace = workStuDocPlace;
    }

    public String getWorkStuDocPlace() {
        return workStuDocPlace;
    }

    public void setCommunityIdCode(String communityIdCode) {
        this.communityIdCode = communityIdCode;
    }

    public String getCommunityIdCode() {
        return communityIdCode;
    }

    public void setCommunityCode(String communityCode) {
        this.communityCode = communityCode;
    }

    public String getCommunityCode() {
        return communityCode;
    }

    public void setCommunityID(String communityID) {
        this.communityID = communityID;
    }

    public String getCommunityID() {
        return communityID;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    public String getCommunityName() {
        return communityName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRealName() {
        return realName;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSex() {
        return sex;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setHouseholdProvince(String householdProvince) {
        this.householdProvince = householdProvince;
    }

    public String getHouseholdProvince() {
        return householdProvince;
    }

    public void setHouseholdCity(String householdCity) {
        this.householdCity = householdCity;
    }

    public String getHouseholdCity() {
        return householdCity;
    }

    public void setHouseholdCounty(String householdCounty) {
        this.householdCounty = householdCounty;
    }

    public String getHouseholdCounty() {
        return householdCounty;
    }

    public void setHouseholdTown(String householdTown) {
        this.householdTown = householdTown;
    }

    public String getHouseholdTown() {
        return householdTown;
    }

    public void setHouseholdVillage(String householdVillage) {
        this.householdVillage = householdVillage;
    }

    public String getHouseholdVillage() {
        return householdVillage;
    }

    public void setHouseholdAddressCode(String householdAddressCode) {
        this.householdAddressCode = householdAddressCode;
    }

    public String getHouseholdAddressCode() {
        return householdAddressCode;
    }

    public void setHouseholdAddressName(String householdAddressName) {
        this.householdAddressName = householdAddressName;
    }

    public String getHouseholdAddressName() {
        return householdAddressName;
    }

    public void setHouseholdAddress(String householdAddress) {
        this.householdAddress = householdAddress;
    }

    public String getHouseholdAddress() {
        return householdAddress;
    }

    public void setCurrentProvince(String currentProvince) {
        this.currentProvince = currentProvince;
    }

    public String getCurrentProvince() {
        return currentProvince;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCounty(String currentCounty) {
        this.currentCounty = currentCounty;
    }

    public String getCurrentCounty() {
        return currentCounty;
    }

    public void setCurrentTown(String currentTown) {
        this.currentTown = currentTown;
    }

    public String getCurrentTown() {
        return currentTown;
    }

    public void setCurrentVillage(String currentVillage) {
        this.currentVillage = currentVillage;
    }

    public String getCurrentVillage() {
        return currentVillage;
    }

    public void setCurrentAddressCode(String currentAddressCode) {
        this.currentAddressCode = currentAddressCode;
    }

    public String getCurrentAddressCode() {
        return currentAddressCode;
    }

    public void setCurrentAddressName(String currentAddressName) {
        this.currentAddressName = currentAddressName;
    }

    public String getCurrentAddressName() {
        return currentAddressName;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void copyFrom(CachePerson from) {
        this.userId = from.getUserId();
        this.createUserId = from.getCreateUserId();
        this.createUserName = from.getCreateUserName();
        this.createTime = from.getCreateTime();
        this.updateUserId = from.getUpdateUserId();
        this.updateUserName = from.getUpdateUserName();
        this.updateTime = from.getUpdateTime();
        this.optLock = from.getOptLock();
        this.dataState = from.getDataState();
        this.dealStatus = from.getDealStatus();
        this.nation = from.getNation();
        this.height = from.getHeight();
        this.employmentCondition = from.getEmploymentCondition();
        this.firstDrugsDate = from.getFirstDrugsDate();
        this.drugTypes = from.getDrugTypes();
        this.drugTypesDetail = from.getDrugTypesDetail();
        this.telePhone = from.getTelePhone();
        this.qq = from.getQq();
        this.weixin = from.getWeixin();
        this.mail = from.getMail();
        this.preRealName = from.getPreRealName();
        this.nickname = from.getNickname();
        this.education = from.getEducation();
        this.marriage = from.getMarriage();
        this.livingWithParents = from.getLivingWithParents();
        this.vocationalTraining = from.getVocationalTraining();
        this.skills = from.getSkills();
        this.skillsDetail = from.getSkillsDetail();
        this.personalResume = from.getPersonalResume();
        this.politicalLandscape = from.getPoliticalLandscape();
        this.politicalLandscapeDetail = from.getPoliticalLandscapeDetail();
        this.jobCondition = from.getJobCondition();
        this.photoAddress = from.getPhotoAddress();
        this.fillingPerson = from.getFillingPerson();
        this.fillingDate = from.getFillingDate();
        this.gatherDepartment = from.getGatherDepartment();
        this.gatherDate = from.getGatherDate();
        this.oftenContactsPerson = from.getOftenContactsPerson();
        this.currentCondition = from.getCurrentCondition();
        this.crimeCondition = from.getCrimeCondition();
        this.healthCondition = from.getHealthCondition();
        this.healthConditionDetail = from.getHealthConditionDetail();
        this.familyCondition = from.getFamilyCondition();
        this.familyConditionDetail = from.getFamilyConditionDetail();
        this.socialSecurityDetail = from.getSocialSecurityDetail();
        this.socialSecurity = from.getSocialSecurity();
        this.income = from.getIncome();
        this.controlId = from.getControlId();
        this.controlName = from.getControlName();
        this.longitude = from.getLongitude();
        this.latitude = from.getLatitude();

        this.gridPerson = from.getLatitude();
        this.communityPolice = from.getLatitude();
        this.deadRemark = from.getLatitude();
        this.deadFileAdd = from.getLatitude();
        this.latitude = from.getLatitude();
        this.latitude = from.getLatitude();
        this.latitude = from.getLatitude();
        this.latitude = from.getLatitude();
        this.latitude = from.getLatitude();
        this.latitude = from.getLatitude();
        this.latitude = from.getLatitude();
        this.latitude = from.getLatitude();
        this.latitude = from.getLatitude();
        this.latitude = from.getLatitude();
        this.latitude = from.getLatitude();
        this.latitude = from.getLatitude();
        this.latitude = from.getLatitude();
        this.latitude = from.getLatitude();
        this.latitude = from.getLatitude();
        this.latitude = from.getLatitude();
        this.latitude = from.getLatitude();
        this.latitude = from.getLatitude();
        this.latitude = from.getLatitude();
        this.latitude = from.getLatitude();
        this.latitude = from.getLatitude();
        this.latitude = from.getLatitude();
        this.latitude = from.getLatitude();
        this.latitude = from.getLatitude();
        this.latitude = from.getLatitude();
        this.latitude = from.getLatitude();
        this.latitude = from.getLatitude();
        this.latitude = from.getLatitude();
        this.latitude = from.getLatitude();
        this.latitude = from.getLatitude();
        this.latitude = from.getLatitude();
        this.latitude = from.getLatitude();
        this.latitude = from.getLatitude();
        this.latitude = from.getLatitude();
        this.latitude = from.getLatitude();
        this.latitude = from.getLatitude();
        this.latitude = from.getLatitude();
    }

    public int getCacheState1() {
        if (MyTextUtils.isAllNotEmpty(identityCard, realName, sex, birthdate, communityName,
                nation, livingWithParents, marriage, healthCondition, familyCondition)) {
            return COMPLETED_DONE;
        } else if (MyTextUtils.isAllEmpty(identityCard, realName, sex, birthdate, communityName,
                nation, livingWithParents, marriage, healthCondition, familyCondition)) {
            return COMPLETED_NONE;
        } else {
            return COMPLETED_DOING;
        }
    }

    public int getCacheState2() {
        if (MyTextUtils.isAllNotEmpty(householdAddressName, householdAddress, currentAddressName, currentAddress, cellphone)) {
            return COMPLETED_DONE;

        } else if (MyTextUtils.isAllEmpty(householdAddressName, householdAddress, currentAddressName, currentAddress, cellphone)) {
            return COMPLETED_NONE;

        } else {
            return COMPLETED_DOING;
        }
    }

    public int getCacheState3() {
        if (MyTextUtils.isAllNotEmpty(politicalLandscape, education, skills, employmentCondition,
                income, socialSecurity)) {
            return COMPLETED_DONE;

        } else if (MyTextUtils.isAllEmpty(politicalLandscape, education, skills, employmentCondition,
                income, socialSecurity)) {
            return COMPLETED_NONE;

        } else {
            return COMPLETED_DOING;
        }
    }

    public int getCacheState4() {
        if (MyTextUtils.isAllNotEmpty(firstDrugsDate, drugTypes, crimeCondition)) {
            return COMPLETED_DONE;

        } else if (MyTextUtils.isAllEmpty(firstDrugsDate, drugTypes, crimeCondition)) {
            return COMPLETED_NONE;

        } else {
            return COMPLETED_DOING;
        }
    }

    public int getCacheState5() {
        if (MyTextUtils.isAllNotEmpty(gridPerson, communityPolice, photoAddress)) {
            return COMPLETED_DONE;

        } else if (MyTextUtils.isAllEmpty(gridPerson, communityPolice, photoAddress)) {
            return COMPLETED_NONE;

        } else {
            return COMPLETED_DOING;
        }
    }

    public int getCacheState() {
        if (getCacheState1() == COMPLETED_NONE &&
                getCacheState2() == COMPLETED_NONE &&
                getCacheState3() == COMPLETED_NONE &&
                getCacheState4() == COMPLETED_NONE &&
                getCacheState5() == COMPLETED_NONE) {
            return COMPLETED_NONE;

        } else if (getCacheState1() == COMPLETED_DONE &&
                getCacheState2() == COMPLETED_DONE &&
                getCacheState3() == COMPLETED_DONE &&
                getCacheState4() == COMPLETED_DONE &&
                getCacheState5() == COMPLETED_DONE) {
            return COMPLETED_DONE;

        } else {
            return COMPLETED_DOING;
        }
    }

}
