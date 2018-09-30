package cn.com.jinke.wh_drugcontrol.persion.model;

import java.io.Serializable;

/**
 * Created by xkr on 2017/10/31.
 */

public class LeaveDetailEntity implements Serializable {
    private String out_content;
    private String work_status;
    private String comment_content;
    private String work_time;
    private String jyrqq;
    private String jyztt;
    private String type;

    public String getOut_content() {
        return out_content;
    }

    public void setOut_content(String out_content) {
        this.out_content = out_content;
    }

    public String getWork_status() {
        return work_status;
    }

    public void setWork_status(String work_status) {
        this.work_status = work_status;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public String getWork_time() {
        return work_time;
    }

    public void setWork_time(String work_time) {
        this.work_time = work_time;
    }

    public String getJyrqq() {
        return jyrqq;
    }

    public void setJyrqq(String jyrqq) {
        this.jyrqq = jyrqq;
    }

    public String getJyztt() {
        return jyztt;
    }

    public void setJyztt(String jyztt) {
        this.jyztt = jyztt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
