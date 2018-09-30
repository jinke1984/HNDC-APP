package cn.com.jinke.wh_drugcontrol.persion.model;

import java.io.Serializable;

/**
 * @author jinke
 * @date 2017/10/26
 * @description
 */

public class Source implements Serializable {

    private String id;

    /**
     * 月份,格式yyyymm
     */
    private String month;

    /**
     * 登记表id
     */
    private String drugsId;

    /**
     * 档案id
     */
    private String docid;

    /**
     * 采集表id
     */
    private String personId;

    /**
     * 身份证号
     */
    private String identitycard;

    /**
     * 所得积分
     */
    private Integer score;

    /**
     * 类别子项目名称
     */
    private String categItemName;

    /**
     * 积分类别子项目编码
     */
    private String categItemCode;

    /**
     * 得分描述
     */
    private String describe;

    /**
     * 积分类型 1，普通积分项， 2高危积分项目
     */
    private Integer scoreType;

    /**
     * 添加人ID
     */
    private String createUserId;

    /**
     * 创建人用户名称
     */
    private String createUserName;

    /**
     * 添加时间
     */
    private String createTime;

    /**
     * 最后一次操作用户ID
     */
    private String updateUserId;

    /**
     * 最后一次操作用户名称
     */
    private String updateUserName;

    /**
     * 最后一次更新时间
     */
    private String updateTime;

    /**
     * 乐观锁
     */
    private Integer optLock;

    /**
     * 数据状态
     */
    private Integer dataState;

    /**
     * 处理状态 : 默认 0 新建
     */
    private Integer dealStatus;

    /**
     * 备注
     */
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDrugsId() {
        return drugsId;
    }

    public void setDrugsId(String drugsId) {
        this.drugsId = drugsId;
    }

    public String getDocid() {
        return docid;
    }

    public void setDocid(String docid) {
        this.docid = docid;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getIdentitycard() {
        return identitycard;
    }

    public void setIdentitycard(String identitycard) {
        this.identitycard = identitycard;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getCategItemCode() {
        return categItemCode;
    }

    public void setCategItemCode(String categItemCode) {
        this.categItemCode = categItemCode;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Integer getScoreType() {
        return scoreType;
    }

    public void setScoreType(Integer scoreType) {
        this.scoreType = scoreType;
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

    public Integer getOptLock() {
        return optLock;
    }

    public void setOptLock(Integer optLock) {
        this.optLock = optLock;
    }

    public Integer getDataState() {
        return dataState;
    }

    public void setDataState(Integer dataState) {
        this.dataState = dataState;
    }

    public Integer getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(Integer dealStatus) {
        this.dealStatus = dealStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCategItemName() {
        return categItemName;
    }

    public void setCategItemName(String categItemName) {
        this.categItemName = categItemName;
    }
}
