package cn.com.jinke.wh_drugcontrol.input.model;

import java.io.Serializable;

/**
 * Created by jinke on 2017/8/4.
 */

public class Urine implements Serializable {

    private String id;//ID
    private String createUserId;//创建人ID
    private String createUserName;//创建人
    private String createTime;//创建时间
    private String personID;//吸毒人员ID
    private String docId;//档案ID
    private String realName;//真实姓名（被检测人）
    private String cellphone;//移动电话;
    private String identityCard;//身份证号
    private String type;//类型
    private String fileAdd;//附件地址
    private String urineDate;//尿检日期
    private String urinePlace;//尿检地点
    private String testcontactPerson;//检测人
    private int urineCounts;//尿检次数
    private String urineResult;//尿检结果
    private String urineType;//尿检类型

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileAdd() {
        return fileAdd;
    }

    public void setFileAdd(String fileAdd) {
        this.fileAdd = fileAdd;
    }

    public String getUrineDate() {
        return urineDate;
    }

    public void setUrineDate(String urineDate) {
        this.urineDate = urineDate;
    }

    public String getUrinePlace() {
        return urinePlace;
    }

    public void setUrinePlace(String urinePlace) {
        this.urinePlace = urinePlace;
    }

    public String getTestcontactPerson() {
        return testcontactPerson;
    }

    public void setTestcontactPerson(String testcontactPerson) {
        this.testcontactPerson = testcontactPerson;
    }

    public int getUrineCounts() {
        return urineCounts;
    }

    public void setUrineCounts(int urineCounts) {
        this.urineCounts = urineCounts;
    }

    public String getUrineResult() {
        return urineResult;
    }

    public void setUrineResult(String urineResult) {
        this.urineResult = urineResult;
    }

    public String getUrineType() {
        return urineType;
    }

    public void setUrineType(String urineType) {
        this.urineType = urineType;
    }
}
