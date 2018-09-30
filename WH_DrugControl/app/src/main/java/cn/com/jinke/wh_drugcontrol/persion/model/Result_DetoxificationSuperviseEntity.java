package cn.com.jinke.wh_drugcontrol.persion.model;

/**
 * Created by xkr on 2017/10/31.
 * 戒毒监管场所入所/出所信息
 */

public class Result_DetoxificationSuperviseEntity extends Abs_IQ_Result{
    private String realName;// 真实姓名
    private String identityCard;// 身份证号
    private String orgName;// 所属组织机构名称
    private String inDdate;// 入所时间
    private String inReason;// 入所原因
    private String crimeName;// 案由/罪名
    private String handlingDepartment;// 决定机关

    /* REHAB_OUT */
    private String actualOutDate;// 实际出所时间
    private String outReason;// 出所原因
    private String seamlessJoint;// 是否无缝衔接
    private String receiveDept;// 接回单位
    private String receivePerson;// 接回人

    private String rehabPerson;//场所责任人
    private String rehabPhone;//联系电话

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

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getInDdate() {
        return inDdate;
    }

    public void setInDdate(String inDdate) {
        this.inDdate = inDdate;
    }

    public String getInReason() {
        return inReason;
    }

    public void setInReason(String inReason) {
        this.inReason = inReason;
    }

    public String getCrimeName() {
        return crimeName;
    }

    public void setCrimeName(String crimeName) {
        this.crimeName = crimeName;
    }

    public String getHandlingDepartment() {
        return handlingDepartment;
    }

    public void setHandlingDepartment(String handlingDepartment) {
        this.handlingDepartment = handlingDepartment;
    }

    public String getActualOutDate() {
        return actualOutDate;
    }

    public void setActualOutDate(String actualOutDate) {
        this.actualOutDate = actualOutDate;
    }

    public String getOutReason() {
        return outReason;
    }

    public void setOutReason(String outReason) {
        this.outReason = outReason;
    }

    public String getSeamlessJoint() {
        return seamlessJoint;
    }

    public void setSeamlessJoint(String seamlessJoint) {
        this.seamlessJoint = seamlessJoint;
    }

    public String getReceiveDept() {
        return receiveDept;
    }

    public void setReceiveDept(String receiveDept) {
        this.receiveDept = receiveDept;
    }

    public String getReceivePerson() {
        return receivePerson;
    }

    public void setReceivePerson(String receivePerson) {
        this.receivePerson = receivePerson;
    }

    public String getRehabPerson() {
        return rehabPerson;
    }

    public void setRehabPerson(String rehabPerson) {
        this.rehabPerson = rehabPerson;
    }

    public String getRehabPhone() {
        return rehabPhone;
    }

    public void setRehabPhone(String rehabPhone) {
        this.rehabPhone = rehabPhone;
    }
}
