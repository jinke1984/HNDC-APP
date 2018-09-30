package cn.com.jinke.wh_drugcontrol.persion.model;

/**
 *
 * 接受药物维持治疗信息
 */

public class Result_AcceptDrugsEntity extends Abs_IQ_Result {

    private String hospitalName;// 诊所名称
    private String groupInDate;// 入组日期
    private String firstDrugsDate;// 首次服药日期
    private String lastDrugsDate;// 最近一次服药日期

    /* Treat_Group_out */
    private String inGroupDate;// 入组日期
    private String outGroupHospitalName;// 转入诊所名称
    private String outGroupDate;// 退诊日期
    private String outTreatmentReason;// 退诊原因

    private String hospitalPerson;//门诊责任人
    private String hospitalPhone;//联系电话

    private String identityCard;// 身份证号


    private String referraHospitalName;
    private String referralDate;

    private String realName;//姓名
    private String hospitalNo;//诊所编号

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getHospitalNo() {
        return hospitalNo;
    }

    public void setHospitalNo(String hospitalNo) {
        this.hospitalNo = hospitalNo;
    }

    public String getReferraHospitalName() {
        return referraHospitalName;
    }

    public void setReferraHospitalName(String referraHospitalName) {
        this.referraHospitalName = referraHospitalName;
    }

    public String getReferralDate() {
        return referralDate;
    }

    public void setReferralDate(String referralDate) {
        this.referralDate = referralDate;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getGroupInDate() {
        return groupInDate;
    }

    public void setGroupInDate(String groupInDate) {
        this.groupInDate = groupInDate;
    }

    public String getFirstDrugsDate() {
        return firstDrugsDate;
    }

    public void setFirstDrugsDate(String firstDrugsDate) {
        this.firstDrugsDate = firstDrugsDate;
    }

    public String getLastDrugsDate() {
        return lastDrugsDate;
    }

    public void setLastDrugsDate(String lastDrugsDate) {
        this.lastDrugsDate = lastDrugsDate;
    }

    public String getInGroupDate() {
        return inGroupDate;
    }

    public void setInGroupDate(String inGroupDate) {
        this.inGroupDate = inGroupDate;
    }

    public String getOutGroupHospitalName() {
        return outGroupHospitalName;
    }

    public void setOutGroupHospitalName(String outGroupHospitalName) {
        this.outGroupHospitalName = outGroupHospitalName;
    }

    public String getOutGroupDate() {
        return outGroupDate;
    }

    public void setOutGroupDate(String outGroupDate) {
        this.outGroupDate = outGroupDate;
    }

    public String getOutTreatmentReason() {
        return outTreatmentReason;
    }

    public void setOutTreatmentReason(String outTreatmentReason) {
        this.outTreatmentReason = outTreatmentReason;
    }

    public String getHospitalPerson() {
        return hospitalPerson;
    }

    public void setHospitalPerson(String hospitalPerson) {
        this.hospitalPerson = hospitalPerson;
    }

    public String getHospitalPhone() {
        return hospitalPhone;
    }

    public void setHospitalPhone(String hospitalPhone) {
        this.hospitalPhone = hospitalPhone;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }
}
