package cn.com.jinke.wh_drugcontrol.persion.model;

import java.io.Serializable;

/**
 * Created by jinke on 2017/9/13.
 */

public class WorkPersion implements Serializable {


    /**
     * id : null
     * realName : null
     * identityCard : null
     * sex : null
     * photoAddress : null
     * conversationShould : null
     * conversationAlready : null
     * urineShould : null
     * urineAlready : null
     * informationShould : null
     * informationAlready : null
     * appraisalShould : null
     * appraisalAlready : null
     * reportShould : null
     * reportAlready : null
     * njFinishStatus : null
     * pgFinishStatus : null
     * bgFinishStatus : null
     * dtFinishStatus : null
     * ftFinishStatus : null
     * conversationNo : null
     * urineNo : null
     * informationNo : null
     * appraisalNo : null
     * reportNO : null
     */

    private String personId;
    private String realName;
    private String identityCard;
    private int sex;
    private String photoAddress;
    private String docId;
    private String type;
    private String conversationShould;
    private String conversationAlready;
    private String urineShould;
    private String urineAlready;
    private String informationShould;
    private String informationAlready;
    private String appraisalShould;
    private String appraisalAlready;
    private String reportShould;
    private String reportAlready;
    private String njFinishStatus;
    private String pgFinishStatus;
    private String bgFinishStatus;
    private String dtFinishStatus;
    private String ftFinishStatus;
    private String conversationNo;
    private String urineNo;
    private String informationNo;
    private String appraisalNo;
    private String reportNO;

    public String getId() {
        return personId;
    }

    public void setId(String id) {
        this.personId = id;
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

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getPhotoAddress() {
        return photoAddress;
    }

    public void setPhotoAddress(String photoAddress) {
        this.photoAddress = photoAddress;
    }

    public String getConversationShould() {
        return conversationShould;
    }

    public void setConversationShould(String conversationShould) {
        this.conversationShould = conversationShould;
    }

    public String getConversationAlready() {
        return conversationAlready;
    }

    public void setConversationAlready(String conversationAlready) {
        this.conversationAlready = conversationAlready;
    }

    public String getUrineShould() {
        return urineShould;
    }

    public void setUrineShould(String urineShould) {
        this.urineShould = urineShould;
    }

    public String getUrineAlready() {
        return urineAlready;
    }

    public void setUrineAlready(String urineAlready) {
        this.urineAlready = urineAlready;
    }

    public String getInformationShould() {
        return informationShould;
    }

    public void setInformationShould(String informationShould) {
        this.informationShould = informationShould;
    }

    public String getInformationAlready() {
        return informationAlready;
    }

    public void setInformationAlready(String informationAlready) {
        this.informationAlready = informationAlready;
    }

    public String getAppraisalShould() {
        return appraisalShould;
    }

    public void setAppraisalShould(String appraisalShould) {
        this.appraisalShould = appraisalShould;
    }

    public String getAppraisalAlready() {
        return appraisalAlready;
    }

    public void setAppraisalAlready(String appraisalAlready) {
        this.appraisalAlready = appraisalAlready;
    }

    public String getReportShould() {
        return reportShould;
    }

    public void setReportShould(String reportShould) {
        this.reportShould = reportShould;
    }

    public String getReportAlready() {
        return reportAlready;
    }

    public void setReportAlready(String reportAlready) {
        this.reportAlready = reportAlready;
    }

    public String getNjFinishStatus() {
        return njFinishStatus;
    }

    public void setNjFinishStatus(String njFinishStatus) {
        this.njFinishStatus = njFinishStatus;
    }

    public String getPgFinishStatus() {
        return pgFinishStatus;
    }

    public void setPgFinishStatus(String pgFinishStatus) {
        this.pgFinishStatus = pgFinishStatus;
    }

    public String getBgFinishStatus() {
        return bgFinishStatus;
    }

    public void setBgFinishStatus(String bgFinishStatus) {
        this.bgFinishStatus = bgFinishStatus;
    }

    public String getDtFinishStatus() {
        return dtFinishStatus;
    }

    public void setDtFinishStatus(String dtFinishStatus) {
        this.dtFinishStatus = dtFinishStatus;
    }

    public String getFtFinishStatus() {
        return ftFinishStatus;
    }

    public void setFtFinishStatus(String ftFinishStatus) {
        this.ftFinishStatus = ftFinishStatus;
    }

    public String getConversationNo() {
        return conversationNo;
    }

    public void setConversationNo(String conversationNo) {
        this.conversationNo = conversationNo;
    }

    public String getUrineNo() {
        return urineNo;
    }

    public void setUrineNo(String urineNo) {
        this.urineNo = urineNo;
    }

    public String getInformationNo() {
        return informationNo;
    }

    public void setInformationNo(String informationNo) {
        this.informationNo = informationNo;
    }

    public String getAppraisalNo() {
        return appraisalNo;
    }

    public void setAppraisalNo(String appraisalNo) {
        this.appraisalNo = appraisalNo;
    }

    public String getReportNO() {
        return reportNO;
    }

    public void setReportNO(String reportNO) {
        this.reportNO = reportNO;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
