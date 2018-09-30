package cn.com.jinke.wh_drugcontrol.persion.model;

import java.io.Serializable;

/**
 * Created by xkr on 2017/10/31.
 */

public class DrugQueryJYYYEntity implements Serializable {
    private String jobs_eoi_content;//就业意向
    private String skill_content;//技能专长
    private String edu_degree_content;//文化程度
    private String cert_name;//证书名称
    private String wages;//月薪范围
    private String data_source;//数据来源
    private String create_data;

    public String getJobs_eoi_content() {
        return jobs_eoi_content;
    }

    public void setJobs_eoi_content(String jobs_eoi_content) {
        this.jobs_eoi_content = jobs_eoi_content;
    }

    public String getSkill_content() {
        return skill_content;
    }

    public void setSkill_content(String skill_content) {
        this.skill_content = skill_content;
    }

    public String getEdu_degree_content() {
        return edu_degree_content;
    }

    public void setEdu_degree_content(String edu_degree_content) {
        this.edu_degree_content = edu_degree_content;
    }

    public String getCert_name() {
        return cert_name;
    }

    public void setCert_name(String cert_name) {
        this.cert_name = cert_name;
    }

    public String getWages() {
        return wages;
    }

    public void setWages(String wages) {
        this.wages = wages;
    }

    public String getData_source() {
        return data_source;
    }

    public void setData_source(String data_source) {
        this.data_source = data_source;
    }

    public String getCreate_data() {
        return create_data;
    }

    public void setCreate_data(String create_data) {
        this.create_data = create_data;
    }
}
