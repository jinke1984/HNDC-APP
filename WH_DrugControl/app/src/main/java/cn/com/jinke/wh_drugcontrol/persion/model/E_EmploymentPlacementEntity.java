package cn.com.jinke.wh_drugcontrol.persion.model;

/**
 * Created by xkr on 2017/11/3.
 */

public class E_EmploymentPlacementEntity {
    private String realName;// 真实姓名
    private String identityCard;// 身份证号
    private String settlementCompany;// 安置单位
    private String settlementPosition;// 安置岗位
    private String settlementType;
    private String settlementDate;
    private Integer salary;// 月薪
    private String resignStatus;// 是否离职：1是0否
    private String resignDate;// 离职日期
    private String resignReason;// 离职原因
    private String companyContact;// 就业单位联系人
    private String companyPhone;// 就业单位联系人电话

    public String getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(String settlementDate) {
        this.settlementDate = settlementDate;
    }

    public String getSettlementType() {
        return settlementType;
    }

    public void setSettlementType(String settlementType) {
        this.settlementType = settlementType;
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

    public String getSettlementCompany() {
        return settlementCompany;
    }

    public void setSettlementCompany(String settlementCompany) {
        this.settlementCompany = settlementCompany;
    }

    public String getSettlementPosition() {
        return settlementPosition;
    }

    public void setSettlementPosition(String settlementPosition) {
        this.settlementPosition = settlementPosition;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getResignStatus() {
        return resignStatus;
    }

    public void setResignStatus(String resignStatus) {
        this.resignStatus = resignStatus;
    }

    public String getResignDate() {
        return resignDate;
    }

    public void setResignDate(String resignDate) {
        this.resignDate = resignDate;
    }

    public String getResignReason() {
        return resignReason;
    }

    public void setResignReason(String resignReason) {
        this.resignReason = resignReason;
    }

    public String getCompanyContact() {
        return companyContact;
    }

    public void setCompanyContact(String companyContact) {
        this.companyContact = companyContact;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }
}
