package cn.com.jinke.wh_drugcontrol.persion.model;

/**
 * Created by xkr on 2017/10/31.
 * 特殊病区出院/入院信息
 */

public class Result_SpecialWardEntity extends Abs_IQ_Result {
    private String realName;// 真实姓名
    private String identityCard;// 身份证号
    private String hospitalName;// 医院名称
    private String inHospitalDate;// 入院时间
    private String inHospitalReason;// 入院原因
    private String inHospitalReasonDetail;// 入院原因描述
    private String inHospitalWay;// 入院途径

    /* HOSPITAL_OUT */
    private String outHospitalDate;// 实际出院时间
    private String outHospitalReason;// 出院原因
    private String isSeamlessConnection;// 是否无缝衔接，1是2否
    private String outHospitalGone;// 出院去向

    private String hospitaPerson;// 病区责任人
    private String hospitaPhone;// 联系电话

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

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getInHospitalDate() {
        return inHospitalDate;
    }

    public void setInHospitalDate(String inHospitalDate) {
        this.inHospitalDate = inHospitalDate;
    }

    public String getInHospitalReason() {
        return inHospitalReason;
    }

    public void setInHospitalReason(String inHospitalReason) {
        this.inHospitalReason = inHospitalReason;
    }

    public String getInHospitalReasonDetail() {
        return inHospitalReasonDetail;
    }

    public void setInHospitalReasonDetail(String inHospitalReasonDetail) {
        this.inHospitalReasonDetail = inHospitalReasonDetail;
    }

    public String getInHospitalWay() {
        return inHospitalWay;
    }

    public void setInHospitalWay(String inHospitalWay) {
        this.inHospitalWay = inHospitalWay;
    }

    public String getOutHospitalDate() {
        return outHospitalDate;
    }

    public void setOutHospitalDate(String outHospitalDate) {
        this.outHospitalDate = outHospitalDate;
    }

    public String getOutHospitalReason() {
        return outHospitalReason;
    }

    public void setOutHospitalReason(String outHospitalReason) {
        this.outHospitalReason = outHospitalReason;
    }

    public String getIsSeamlessConnection() {
        return isSeamlessConnection;
    }

    public void setIsSeamlessConnection(String isSeamlessConnection) {
        this.isSeamlessConnection = isSeamlessConnection;
    }

    public String getOutHospitalGone() {
        return outHospitalGone;
    }

    public void setOutHospitalGone(String outHospitalGone) {
        this.outHospitalGone = outHospitalGone;
    }

    public String getHospitaPerson() {
        return hospitaPerson;
    }

    public void setHospitaPerson(String hospitaPerson) {
        this.hospitaPerson = hospitaPerson;
    }

    public String getHospitaPhone() {
        return hospitaPhone;
    }

    public void setHospitaPhone(String hospitaPhone) {
        this.hospitaPhone = hospitaPhone;
    }
}
