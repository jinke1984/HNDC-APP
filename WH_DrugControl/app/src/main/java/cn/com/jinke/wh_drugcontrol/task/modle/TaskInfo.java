package cn.com.jinke.wh_drugcontrol.task.modle;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xkr on 2017/12/12.
 *
 */

public class TaskInfo implements Serializable{
    /**
     * private List<WorkflowCommand> modDelWorkList;// 删除、修改流程
     private List<ModifyDeleteEntity> modifyEntity;// 修改/删除流程业务表
     private int curTask;// 有流程是1,没流程是0
     private String timeHourSq; // 时间差(社区审批)
     private String approveStepNum = "0"; // 公共的流程步骤判断1是社区审核,2是区市县审核,3是专干临时权限
     */
    List<TaskDetail> modDelWorkList;
    List<TaskRecord>  modifyEntity;
     int curTask;
    int timeHourSq;
    int approveStepNum;

    public int getCurTask() {
        return curTask;
    }

    public void setCurTask(int curTask) {
        this.curTask = curTask;
    }

    public int getTimeHourSq() {
        return timeHourSq;
    }

    public void setTimeHourSq(int timeHourSq) {
        this.timeHourSq = timeHourSq;
    }

    public int getApproveStepNum() {
        return approveStepNum;
    }

    public void setApproveStepNum(int approveStepNum) {
        this.approveStepNum = approveStepNum;
    }

    public List<TaskDetail> getModDelWorkList() {
        return modDelWorkList;
    }

    public void setModDelWorkList(List<TaskDetail> modDelWorkList) {
        this.modDelWorkList = modDelWorkList;
    }

    public List<TaskRecord> getModifyEntity() {
        return modifyEntity;
    }

    public void setModifyEntity(List<TaskRecord> modifyEntity) {
        this.modifyEntity = modifyEntity;
    }
}
