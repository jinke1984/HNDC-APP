package cn.com.jinke.wh_drugcontrol.input.model;

import java.io.Serializable;

/**
 * @author jinke
 * @date 2018/3/8
 * @description
 */

public class Change implements Serializable{

    /**
     *  ID
     */
    private String id;

    /**
     *  创建时间
     */
    private String createTime;

    /**
     *  创建人ID
     */
    private String createUserId;

    /**
     *  创建人名称
     */
    private String createUserName;

    /**
     *  联系电话
     */
    private String cellPhone;

    /**
     *  档案ID
     */
    private String docId;

    /**
     *  附件地址
     */
    private String fileAdd;

    /**
     *  身份证号
     */
    private String identityCard;

    /**
     *  人员ID
     */
    private String personId;

    /**
     *  人员姓名
     */
    private String realName;

    /**
     *  类型 社区戒毒|社区康复
     */
    private String type;

    /**
     *  现执行地详细地址
     */
    private String currentAddress;

    /**
     *  拟变更执行地详细地址
     */
    private String changeAddress;

    /**
     *  拟变更执行地代码
     */
    private String changeAddressCode;

    /**
     *  拟变更执行地名称
     */
    private String changeAddressName;

    /**
     *  拟变更执行地 社区或组织机构Id
     */
    private String changeCommunityId;

    /**
     *  拟变更执行地 社区或组织机构Code
     */
    private String changeCommunityCode;

    /**
     *  变更原因
     */
    private String changeReason;

    /**
     *  社区戒毒开始日期
     */
    private String drugsBeginDate;

    /**
     *  社区戒毒结束日期
     */
    private String drugsEndDate;

    /**
     *  离开社区日期
     */
    private String leaveDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getCellPhone() {
        return cellPhone;
    }

    public void setCellPhone(String cellPhone) {
        this.cellPhone = cellPhone;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getFileAdd() {
        return fileAdd;
    }

    public void setFileAdd(String fileAdd) {
        this.fileAdd = fileAdd;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getChangeAddress() {
        return changeAddress;
    }

    public void setChangeAddress(String changeAddress) {
        this.changeAddress = changeAddress;
    }

    public String getChangeAddressCode() {
        return changeAddressCode;
    }

    public void setChangeAddressCode(String changeAddressCode) {
        this.changeAddressCode = changeAddressCode;
    }

    public String getChangeAddressName() {
        return changeAddressName;
    }

    public void setChangeAddressName(String changeAddressName) {
        this.changeAddressName = changeAddressName;
    }

    public String getChangeCommunityId() {
        return changeCommunityId;
    }

    public void setChangeCommunityId(String changeCommunityId) {
        this.changeCommunityId = changeCommunityId;
    }

    public String getChangeCommunityCode() {
        return changeCommunityCode;
    }

    public void setChangeCommunityCode(String changeCommunityCode) {
        this.changeCommunityCode = changeCommunityCode;
    }

    public String getChangeReason() {
        return changeReason;
    }

    public void setChangeReason(String changeReason) {
        this.changeReason = changeReason;
    }

    public String getDrugsBeginDate() {
        return drugsBeginDate;
    }

    public void setDrugsBeginDate(String drugsBeginDate) {
        this.drugsBeginDate = drugsBeginDate;
    }

    public String getDrugsEndDate() {
        return drugsEndDate;
    }

    public void setDrugsEndDate(String drugsEndDate) {
        this.drugsEndDate = drugsEndDate;
    }

    public String getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(String leaveDate) {
        this.leaveDate = leaveDate;
    }
}
