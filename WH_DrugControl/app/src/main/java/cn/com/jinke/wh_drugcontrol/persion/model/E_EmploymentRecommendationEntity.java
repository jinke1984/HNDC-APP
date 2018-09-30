package cn.com.jinke.wh_drugcontrol.persion.model;

/**
 * Created by xkr on 2017/11/3.
 */

public class E_EmploymentRecommendationEntity {
    private String isRecomment; // 是否推荐：1是，2已安置
    private String recommendDate;// 推荐日期
    private String niSettlementCompany;// 拟安置单位
    private String recommendUnit;// 推荐单位
    private String recommendJob;// 推荐岗位
    private Integer reSalary;// 推荐月薪
    private String sex;// 性别
    private String education;// 教育程度

    public String getIsRecomment() {
        return isRecomment;
    }

    public void setIsRecomment(String isRecomment) {
        this.isRecomment = isRecomment;
    }

    public String getRecommendDate() {
        return recommendDate;
    }

    public void setRecommendDate(String recommendDate) {
        this.recommendDate = recommendDate;
    }

    public String getNiSettlementCompany() {
        return niSettlementCompany;
    }

    public void setNiSettlementCompany(String niSettlementCompany) {
        this.niSettlementCompany = niSettlementCompany;
    }

    public String getRecommendUnit() {
        return recommendUnit;
    }

    public void setRecommendUnit(String recommendUnit) {
        this.recommendUnit = recommendUnit;
    }

    public String getRecommendJob() {
        return recommendJob;
    }

    public void setRecommendJob(String recommendJob) {
        this.recommendJob = recommendJob;
    }

    public Integer getReSalary() {
        return reSalary;
    }

    public void setReSalary(Integer reSalary) {
        this.reSalary = reSalary;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }
}
