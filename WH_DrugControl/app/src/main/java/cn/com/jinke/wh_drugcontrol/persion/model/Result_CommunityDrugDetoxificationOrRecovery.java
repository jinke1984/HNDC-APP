package cn.com.jinke.wh_drugcontrol.persion.model;

/**
 * Created by xkr on 2017/11/1.
 * 社区戒毒/社区康复信息
 */

public class Result_CommunityDrugDetoxificationOrRecovery extends Abs_IQ_Result {
    private String isDecision;// 是否有公安决定书
    private String nonDecisionReason;// 无公安决定书原因
    private String decisionNo1;// 决定书文号1
    private String decisionNo2;// 决定书文号2
    private String decisionNo3;// 决定书文号3
    private String decisionNo4;// 决定书文号4
    private String drugsBeginDate;// 社区戒毒开始日期
    private String drugsEndDate;// 社区戒毒结束日期
    private String handlingDepartment;// 公安机关名称
    private String registerDate;// 报到日期
    private String actegisterDate;// 实际报道日期
    private String catchDeatil;// 查获详情
    private String exeComNameAndAddr;// 执行地社区名称及地址

    private String personId;// 吸毒人员ID
    private String docId;// 档案ID
    private String realName;// 真实姓名
    private String cellphone;// 移动电话
    private String identityCard;// 身份证号
    private String fileAdd;// 附件地址
    private String type;// 类型 社区戒毒|社区康复

    /* DOCUMENT */
    private Integer dealStatus;// 档案状态

    /* BASE_PERSON_DRUGS_CONDITION */
    private String drugTypeName;// 滥用毒品种类
    private String drugTypes;//吸毒种类
    private String drugStyle;// 滥用毒品方式
    private String firstChartDate;// 初次吸毒日期
    private String lastDrugsEndDate;// 最后一次吸毒被拘留/戒毒结束日期
    private String chartCount;// 吸毒累计被查获次数
    /* SCORE_INFO */
    private Integer controlLevel; // 管控级别(1:1级，2：二级，3：三级) 当前月份

    /* BASE_SPECIALLY_ALLOT SYS_USER SYS_USER_INFO */
    private String controlPerson;// 管控责任人
    private String controlPhone;// 管控责任人联系电话


    private String executeDepartment;// 执行单位
    private String executePlace;// 执行地区

    public String getExecuteDepartment() {
        return executeDepartment;
    }

    public void setExecuteDepartment(String executeDepartment) {
        this.executeDepartment = executeDepartment;
    }

    public String getExecutePlace() {
        return executePlace;
    }

    public void setExecutePlace(String executePlace) {
        this.executePlace = executePlace;
    }

    public String getDrugTypes() {
        return drugTypes;
    }

    public void setDrugTypes(String drugTypes) {
        this.drugTypes = drugTypes;
    }

    public String getIsDecision() {
        return isDecision;
    }

    public void setIsDecision(String isDecision) {
        this.isDecision = isDecision;
    }

    public String getNonDecisionReason() {
        return nonDecisionReason;
    }

    public void setNonDecisionReason(String nonDecisionReason) {
        this.nonDecisionReason = nonDecisionReason;
    }

    public String getDecisionNo1() {
        return decisionNo1;
    }

    public void setDecisionNo1(String decisionNo1) {
        this.decisionNo1 = decisionNo1;
    }

    public String getDecisionNo2() {
        return decisionNo2;
    }

    public void setDecisionNo2(String decisionNo2) {
        this.decisionNo2 = decisionNo2;
    }

    public String getDecisionNo3() {
        return decisionNo3;
    }

    public void setDecisionNo3(String decisionNo3) {
        this.decisionNo3 = decisionNo3;
    }

    public String getDecisionNo4() {
        return decisionNo4;
    }

    public void setDecisionNo4(String decisionNo4) {
        this.decisionNo4 = decisionNo4;
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

    public String getHandlingDepartment() {
        return handlingDepartment;
    }

    public void setHandlingDepartment(String handlingDepartment) {
        this.handlingDepartment = handlingDepartment;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getActegisterDate() {
        return actegisterDate;
    }

    public void setActegisterDate(String actegisterDate) {
        this.actegisterDate = actegisterDate;
    }

    public String getCatchDeatil() {
        return catchDeatil;
    }

    public void setCatchDeatil(String catchDeatil) {
        this.catchDeatil = catchDeatil;
    }

    public String getExeComNameAndAddr() {
        return exeComNameAndAddr;
    }

    public void setExeComNameAndAddr(String exeComNameAndAddr) {
        this.exeComNameAndAddr = exeComNameAndAddr;
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

    public Integer getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(Integer dealStatus) {
        this.dealStatus = dealStatus;
    }

    public String getDrugTypeName() {
        return drugTypeName;
    }

    public void setDrugTypeName(String drugTypeName) {
        this.drugTypeName = drugTypeName;
    }

    public String getDrugStyle() {
        return drugStyle;
    }

    public void setDrugStyle(String drugStyle) {
        this.drugStyle = drugStyle;
    }

    public String getFirstChartDate() {
        return firstChartDate;
    }

    public void setFirstChartDate(String firstChartDate) {
        this.firstChartDate = firstChartDate;
    }

    public String getLastDrugsEndDate() {
        return lastDrugsEndDate;
    }

    public void setLastDrugsEndDate(String lastDrugsEndDate) {
        this.lastDrugsEndDate = lastDrugsEndDate;
    }

    public String getChartCount() {
        return chartCount;
    }

    public void setChartCount(String chartCount) {
        this.chartCount = chartCount;
    }

    public Integer getControlLevel() {
        return controlLevel;
    }

    public void setControlLevel(Integer controlLevel) {
        this.controlLevel = controlLevel;
    }

    public String getControlPerson() {
        return controlPerson;
    }

    public void setControlPerson(String controlPerson) {
        this.controlPerson = controlPerson;
    }

    public String getControlPhone() {
        return controlPhone;
    }

    public void setControlPhone(String controlPhone) {
        this.controlPhone = controlPhone;
    }
}
