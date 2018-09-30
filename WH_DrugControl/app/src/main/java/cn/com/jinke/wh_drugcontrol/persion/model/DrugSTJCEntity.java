package cn.com.jinke.wh_drugcontrol.persion.model;

import java.io.Serializable;

/**
 * Created by xkr on 2017/10/31.
 */

public class DrugSTJCEntity implements Serializable {
    private String operate_date; //日期
    private String circle;       //周期
    private String dx_result;    //诊断结果
    private String dx_org;       //机构名称
    private String diagnostic_records;  //详情说明

    public String getOperate_date() {
        return operate_date;
    }

    public void setOperate_date(String operate_date) {
        this.operate_date = operate_date;
    }

    public String getCircle() {
        return circle;
    }

    public void setCircle(String circle) {
        this.circle = circle;
    }

    public String getDx_result() {
        return dx_result;
    }

    public void setDx_result(String dx_result) {
        this.dx_result = dx_result;
    }

    public String getDx_org() {
        return dx_org;
    }

    public void setDx_org(String dx_org) {
        this.dx_org = dx_org;
    }

    public String getDiagnostic_records() {
        return diagnostic_records;
    }

    public void setDiagnostic_records(String diagnostic_records) {
        this.diagnostic_records = diagnostic_records;
    }
}
