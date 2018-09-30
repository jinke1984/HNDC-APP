package cn.com.jinke.wh_drugcontrol.persion.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by admin on 2017/10/31.
 */

public class RecoveryInfomationEntity implements Serializable {
    private String id;
    private String name;
    private String id_card;
    private String order_dep_name;
    private String decision;
    private String execution_area;
    private String execution_area_dep_name;
    private String type;
    private String create_date;
    private String report_date;
    private String end_date;
    private String currentstate;
    private String drug_type;
    private String drug_method;
    private String first_time_date;
    private String last_time_end_date;
    private String detoxification_count;
    private String testtime;
    private String phone;
    private String control_level;
    private String zgname;
    private String zgphone;
    private ArrayList<UrinetestRecordEntity> urineTestEntity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getOrder_dep_name() {
        return order_dep_name;
    }

    public void setOrder_dep_name(String order_dep_name) {
        this.order_dep_name = order_dep_name;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getExecution_area() {
        return execution_area;
    }

    public void setExecution_area(String execution_area) {
        this.execution_area = execution_area;
    }

    public String getExecution_area_dep_name() {
        return execution_area_dep_name;
    }

    public void setExecution_area_dep_name(String execution_area_dep_name) {
        this.execution_area_dep_name = execution_area_dep_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getReport_date() {
        return report_date;
    }

    public void setReport_date(String report_date) {
        this.report_date = report_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getCurrentstate() {
        return currentstate;
    }

    public void setCurrentstate(String currentstate) {
        this.currentstate = currentstate;
    }

    public String getDrug_type() {
        return drug_type;
    }

    public void setDrug_type(String drug_type) {
        this.drug_type = drug_type;
    }

    public String getDrug_method() {
        return drug_method;
    }

    public void setDrug_method(String drug_method) {
        this.drug_method = drug_method;
    }

    public String getFirst_time_date() {
        return first_time_date;
    }

    public void setFirst_time_date(String first_time_date) {
        this.first_time_date = first_time_date;
    }

    public String getLast_time_end_date() {
        return last_time_end_date;
    }

    public void setLast_time_end_date(String last_time_end_date) {
        this.last_time_end_date = last_time_end_date;
    }

    public String getDetoxification_count() {
        return detoxification_count;
    }

    public void setDetoxification_count(String detoxification_count) {
        this.detoxification_count = detoxification_count;
    }

    public String getTesttime() {
        return testtime;
    }

    public void setTesttime(String testtime) {
        this.testtime = testtime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getControl_level() {
        return control_level;
    }

    public void setControl_level(String control_level) {
        this.control_level = control_level;
    }

    public String getZgname() {
        return zgname;
    }

    public void setZgname(String zgname) {
        this.zgname = zgname;
    }

    public String getZgphone() {
        return zgphone;
    }

    public void setZgphone(String zgphone) {
        this.zgphone = zgphone;
    }

    public ArrayList<UrinetestRecordEntity> getUrineTestEntity() {
        return urineTestEntity;
    }

    public void setUrineTestEntity(ArrayList<UrinetestRecordEntity> urineTestEntity) {
        this.urineTestEntity = urineTestEntity;
    }
}
