package cn.com.jinke.wh_drugcontrol.input.model;

import java.io.Serializable;

/**
 * 居住位置DataClass
 * Created by jinke on 2017/8/9.
 */

public class HousePhoto implements Serializable {
    private String makePerson;// 制作人
    private String makeDate;// 制作日期
    private String currentAddress;// 居住地址
    private String pictureTitle;// 位置图名称
    private String pictureAdd;// 房屋图照片
    private String positionAdd;// 位置图照片

    private String personId;// 吸毒人员ID
    private String docId;// 档案ID
    private String realName;// 真实姓名
    private String cellphone;// 移动电话
    private String identityCard;// 身份证号
    private String fileAdd;// 附件地址
    private String type;// 类型 社区戒毒|社区康复

    public String getMakePerson() {
        return makePerson;
    }

    public void setMakePerson(String makePerson) {
        this.makePerson = makePerson;
    }

    public String getMakeDate() {
        return makeDate;
    }

    public void setMakeDate(String makeDate) {
        this.makeDate = makeDate;
    }

    public String getCurrentAddress() {
        return currentAddress;
    }

    public void setCurrentAddress(String currentAddress) {
        this.currentAddress = currentAddress;
    }

    public String getPictureTitle() {
        return pictureTitle;
    }

    public void setPictureTitle(String pictureTitle) {
        this.pictureTitle = pictureTitle;
    }

    public String getPictureAdd() {
        return pictureAdd;
    }

    public void setPictureAdd(String pictureAdd) {
        this.pictureAdd = pictureAdd;
    }

    public String getPositionAdd() {
        return positionAdd;
    }

    public void setPositionAdd(String positionAdd) {
        this.positionAdd = positionAdd;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
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

    public String getFileAdd() {
        return fileAdd;
    }

    public void setFileAdd(String fileAdd) {
        this.fileAdd = fileAdd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
