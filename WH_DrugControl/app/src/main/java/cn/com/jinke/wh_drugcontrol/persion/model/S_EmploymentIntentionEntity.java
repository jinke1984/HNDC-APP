package cn.com.jinke.wh_drugcontrol.persion.model;

import java.io.Serializable;

/**
 * Created by xkr on 2017/11/2.
 * 就业意愿
 */

public class S_EmploymentIntentionEntity implements Serializable {
    private String personNum;// 人员编号
    private String healthCondition;// 身体健康状况
    private String education;// 文化程度
    private String speciality;// 专长
    private String characteristic;// 是否有特征
    private String credentialName;// 证书名称
    private String employWill;// 就业意愿
    private String hopeSalary;// 期望薪资
    private String position; // 就业岗位
    private String employEnterDate;// 就业意愿录入时间
    private String skillCondition;// 技能培训情况
    private String isRecomment; // 是否推荐：1是，2否
    private String realName;// 姓 名
    private String sex;// 性 别
    private String identityCard;// 身份证号
    private String createTime;// 添加时间
    private String id;

    public String getPersonNum() {
        return personNum;
    }

    public void setPersonNum(String personNum) {
        this.personNum = personNum;
    }

    public String getHealthCondition() {
        return healthCondition;
    }

    public void setHealthCondition(String healthCondition) {
        this.healthCondition = healthCondition;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getCharacteristic() {
        return characteristic;
    }

    public void setCharacteristic(String characteristic) {
        this.characteristic = characteristic;
    }

    public String getCredentialName() {
        return credentialName;
    }

    public void setCredentialName(String credentialName) {
        this.credentialName = credentialName;
    }

    public String getEmployWill() {
        return employWill;
    }

    public void setEmployWill(String employWill) {
        this.employWill = employWill;
    }

    public String getHopeSalary() {
        return hopeSalary;
    }

    public void setHopeSalary(String hopeSalary) {
        this.hopeSalary = hopeSalary;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getEmployEnterDate() {
        return employEnterDate;
    }

    public void setEmployEnterDate(String employEnterDate) {
        this.employEnterDate = employEnterDate;
    }

    public String getSkillCondition() {
        return skillCondition;
    }

    public void setSkillCondition(String skillCondition) {
        this.skillCondition = skillCondition;
    }

    public String getIsRecomment() {
        return isRecomment;
    }

    public void setIsRecomment(String isRecomment) {
        this.isRecomment = isRecomment;
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

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
