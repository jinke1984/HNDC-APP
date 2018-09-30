package cn.com.jinke.wh_drugcontrol.persion.model;

import java.io.Serializable;

/**
 * Created by xkr on 2017/11/1.
 * 社会化戒毒康复服务
 */

public class Result_SocialRehabilitationServiceForDrugAddicts extends Abs_IQ_Result implements Serializable{
    private String realName;// 真实姓名
    private String identityCard;// 身份证号
    private String inStationDate;// 入站时间
    private String stationName;// 站点名称
    private String stationNameId;// 站点id
    private String inStationCounts;// 入站次数
    private String stationPerson;//站点责任人
    private String stationPhone;//联系电话

    private int stationEmployCount = 0; //就业意愿次数
    private int helpingCount = 0;// 帮扶救助情况
    private int physicalCount = 0;// 身体检查情况
    private int mentalityCount = 0;// 心里咨询情况

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getInStationDate() {
        return inStationDate;
    }

    public void setInStationDate(String inStationDate) {
        this.inStationDate = inStationDate;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getStationNameId() {
        return stationNameId;
    }

    public void setStationNameId(String stationNameId) {
        this.stationNameId = stationNameId;
    }

    public String getInStationCounts() {
        return inStationCounts;
    }

    public void setInStationCounts(String inStationCounts) {
        this.inStationCounts = inStationCounts;
    }

    public String getStationPerson() {
        return stationPerson;
    }

    public void setStationPerson(String stationPerson) {
        this.stationPerson = stationPerson;
    }

    public String getStationPhone() {
        return stationPhone;
    }

    public void setStationPhone(String stationPhone) {
        this.stationPhone = stationPhone;
    }

    public int getStationEmployCount() {
        return stationEmployCount;
    }

    public void setStationEmployCount(int stationEmployCount) {
        this.stationEmployCount = stationEmployCount;
    }

    public int getHelpingCount() {
        return helpingCount;
    }

    public void setHelpingCount(int helpingCount) {
        this.helpingCount = helpingCount;
    }

    public int getPhysicalCount() {
        return physicalCount;
    }

    public void setPhysicalCount(int physicalCount) {
        this.physicalCount = physicalCount;
    }

    public int getMentalityCount() {
        return mentalityCount;
    }

    public void setMentalityCount(int mentalityCount) {
        this.mentalityCount = mentalityCount;
    }
}
