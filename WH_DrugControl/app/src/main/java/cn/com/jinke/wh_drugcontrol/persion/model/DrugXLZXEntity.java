package cn.com.jinke.wh_drugcontrol.persion.model;

import java.io.Serializable;

/**
 * Created by xkr on 2017/10/31.
 */

public class DrugXLZXEntity implements Serializable {
    private String counselo_date;//咨询日期
    private String counselor;//咨询师
    private String counselor_orgn;//咨询师所在机构
    private String first_type;//I类
    private String second_type;//II类
    private String third_type;//III类
    private String pgdj;      //评估等级
    private String score;     //得分

    public String getCounselo_date() {
        return counselo_date;
    }

    public void setCounselo_date(String counselo_date) {
        this.counselo_date = counselo_date;
    }

    public String getCounselor() {
        return counselor;
    }

    public void setCounselor(String counselor) {
        this.counselor = counselor;
    }

    public String getCounselor_orgn() {
        return counselor_orgn;
    }

    public void setCounselor_orgn(String counselor_orgn) {
        this.counselor_orgn = counselor_orgn;
    }

    public String getFirst_type() {
        return first_type;
    }

    public void setFirst_type(String first_type) {
        this.first_type = first_type;
    }

    public String getSecond_type() {
        return second_type;
    }

    public void setSecond_type(String second_type) {
        this.second_type = second_type;
    }

    public String getThird_type() {
        return third_type;
    }

    public void setThird_type(String third_type) {
        this.third_type = third_type;
    }

    public String getPgdj() {
        return pgdj;
    }

    public void setPgdj(String pgdj) {
        this.pgdj = pgdj;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
