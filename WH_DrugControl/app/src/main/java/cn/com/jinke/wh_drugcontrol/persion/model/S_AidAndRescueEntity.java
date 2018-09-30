package cn.com.jinke.wh_drugcontrol.persion.model;

/**
 * Created by xkr on 2017/11/2.
 */

public class S_AidAndRescueEntity  {
    private String speciality;// 特长技能
    private String education;// 文化程度
    private String helpDate;// 救助日期
    private String helper;// 救助组织或个人
    private String lowSecurity;// 是否办理低保 1是，0否
    private Double lowMoney;// 低保金
    private String lowAccounts;// 低保金帐号
    private String hopeHelp;// 希望获得的救助
    private String helpCondition;// 救助情况
    private String helpesult;// 救助效果
    private String fileAdd;// 附件
    private String personId;// 吸毒人员id
    private String personNum;// 吸毒人员编号

    private String realName;// 姓 名
    private String sex;// 性 别
    private String identityCard;// 身份证号
    private String createTime;// 添加时间

    private String id;

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getHelpDate() {
        return helpDate;
    }

    public void setHelpDate(String helpDate) {
        this.helpDate = helpDate;
    }

    public String getHelper() {
        return helper;
    }

    public void setHelper(String helper) {
        this.helper = helper;
    }

    public String getLowSecurity() {
        return lowSecurity;
    }

    public void setLowSecurity(String lowSecurity) {
        this.lowSecurity = lowSecurity;
    }

    public Double getLowMoney() {
        return lowMoney;
    }

    public void setLowMoney(Double lowMoney) {
        this.lowMoney = lowMoney;
    }

    public String getLowAccounts() {
        return lowAccounts;
    }

    public void setLowAccounts(String lowAccounts) {
        this.lowAccounts = lowAccounts;
    }

    public String getHopeHelp() {
        return hopeHelp;
    }

    public void setHopeHelp(String hopeHelp) {
        this.hopeHelp = hopeHelp;
    }

    public String getHelpCondition() {
        return helpCondition;
    }

    public void setHelpCondition(String helpCondition) {
        this.helpCondition = helpCondition;
    }

    public String getHelpesult() {
        return helpesult;
    }

    public void setHelpesult(String helpesult) {
        this.helpesult = helpesult;
    }

    public String getFileAdd() {
        return fileAdd;
    }

    public void setFileAdd(String fileAdd) {
        this.fileAdd = fileAdd;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
