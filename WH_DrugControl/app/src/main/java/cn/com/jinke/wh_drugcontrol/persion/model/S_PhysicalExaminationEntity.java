package cn.com.jinke.wh_drugcontrol.persion.model;

/**
 * Created by xkr on 2017/11/2.
 */

public class S_PhysicalExaminationEntity {
    private String personNum;// 吸毒人员编号
    private String realName;// 姓名
    private String identityCard;// 身份证号码
    private String diagnoseCycle;// 诊断周期
    private String diagnoseDate;// 诊断日期
    private String diagnoseResult;// 诊断结果
    private String diagnoseOrg;// 诊断机构
    private String diagnoseDetails;// 诊断详情说明
    private String isMental;// 是否精神病人
    private String id;
    private String createTime;// 添加时间

    public String getPersonNum() {
        return personNum;
    }

    public void setPersonNum(String personNum) {
        this.personNum = personNum;
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

    public String getDiagnoseCycle() {
        return diagnoseCycle;
    }

    public void setDiagnoseCycle(String diagnoseCycle) {
        this.diagnoseCycle = diagnoseCycle;
    }

    public String getDiagnoseDate() {
        return diagnoseDate;
    }

    public void setDiagnoseDate(String diagnoseDate) {
        this.diagnoseDate = diagnoseDate;
    }

    public String getDiagnoseResult() {
        return diagnoseResult;
    }

    public void setDiagnoseResult(String diagnoseResult) {
        this.diagnoseResult = diagnoseResult;
    }

    public String getDiagnoseOrg() {
        return diagnoseOrg;
    }

    public void setDiagnoseOrg(String diagnoseOrg) {
        this.diagnoseOrg = diagnoseOrg;
    }

    public String getDiagnoseDetails() {
        return diagnoseDetails;
    }

    public void setDiagnoseDetails(String diagnoseDetails) {
        this.diagnoseDetails = diagnoseDetails;
    }

    public String getIsMental() {
        return isMental;
    }

    public void setIsMental(String isMental) {
        this.isMental = isMental;
    }

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
}
