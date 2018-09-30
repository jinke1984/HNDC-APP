package cn.com.jinke.wh_drugcontrol.persion.model;

/**
 * Created by xkr on 2017/10/31.
 * 公安办案部门查获信息
 */

public class Result_PublicSecurityEntity extends Abs_IQ_Result {
    private String realName;// 姓 名
    private String identityCard;// 身份证号
    private String handlingDepartment;// 办案单位
    private String handlingDeptName;// 办案单位名称
    private String caughtDate;// 查获日期
    private String isCurrentDrug;// 当前是否吸毒
    private String urineTestResult;// 尿检结果
    private String isAddiction;// 是否成瘾
    private String addictionLevel;// 成瘾程度
    private String caughtArea;// 查获地所属区域
    private String caughtLocation;// 查获地点
    private String caughtAreaName;// 查获地所属区域名字
    private String drugCrime;// 涉毒违法犯罪
    private String crimeName;// 涉案罪名
    private String punishDate;// 处置日期
    private String punishResult;// 处置结果
    private String punishPlace;// 处置去向
    private String orgName;// 去向机构名称 |其他机构名称


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

    public String getHandlingDepartment() {
        return handlingDepartment;
    }

    public void setHandlingDepartment(String handlingDepartment) {
        this.handlingDepartment = handlingDepartment;
    }

    public String getHandlingDeptName() {
        return handlingDeptName;
    }

    public void setHandlingDeptName(String handlingDeptName) {
        this.handlingDeptName = handlingDeptName;
    }

    public String getCaughtDate() {
        return caughtDate;
    }

    public void setCaughtDate(String caughtDate) {
        this.caughtDate = caughtDate;
    }

    public String getIsCurrentDrug() {
        return isCurrentDrug;
    }

    public void setIsCurrentDrug(String isCurrentDrug) {
        this.isCurrentDrug = isCurrentDrug;
    }

    public String getUrineTestResult() {
        return urineTestResult;
    }

    public void setUrineTestResult(String urineTestResult) {
        this.urineTestResult = urineTestResult;
    }

    public String getIsAddiction() {
        return isAddiction;
    }

    public void setIsAddiction(String isAddiction) {
        this.isAddiction = isAddiction;
    }

    public String getAddictionLevel() {
        return addictionLevel;
    }

    public void setAddictionLevel(String addictionLevel) {
        this.addictionLevel = addictionLevel;
    }

    public String getCaughtArea() {
        return caughtArea;
    }

    public void setCaughtArea(String caughtArea) {
        this.caughtArea = caughtArea;
    }

    public String getCaughtLocation() {
        return caughtLocation;
    }

    public void setCaughtLocation(String caughtLocation) {
        this.caughtLocation = caughtLocation;
    }

    public String getCaughtAreaName() {
        return caughtAreaName;
    }

    public void setCaughtAreaName(String caughtAreaName) {
        this.caughtAreaName = caughtAreaName;
    }

    public String getDrugCrime() {
        return drugCrime;
    }

    public void setDrugCrime(String drugCrime) {
        this.drugCrime = drugCrime;
    }

    public String getCrimeName() {
        return crimeName;
    }

    public void setCrimeName(String crimeName) {
        this.crimeName = crimeName;
    }

    public String getPunishDate() {
        return punishDate;
    }

    public void setPunishDate(String punishDate) {
        this.punishDate = punishDate;
    }

    public String getPunishResult() {
        return punishResult;
    }

    public void setPunishResult(String punishResult) {
        this.punishResult = punishResult;
    }

    public String getPunishPlace() {
        return punishPlace;
    }

    public void setPunishPlace(String punishPlace) {
        this.punishPlace = punishPlace;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
