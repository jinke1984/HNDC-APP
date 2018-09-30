package cn.com.jinke.wh_drugcontrol.home.model;

import java.io.Serializable;

/**
 * TaskCount
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/10/19
 */

public class TaskCount implements Serializable {
    private int assignment;// 派发任务
    private int dynamicAtt;// 动态关注

    public int getAssignment() {
        return assignment;
    }

    public void setAssignment(int assignment) {
        this.assignment = assignment;
    }

    public int getDynamicAtt() {
        return dynamicAtt;
    }

    public void setDynamicAtt(int dynamicAtt) {
        this.dynamicAtt = dynamicAtt;
    }

}
