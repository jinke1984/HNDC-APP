package cn.com.jinke.wh_drugcontrol.task.modle;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xkr on 2017/11/18.
 */

public class TaskDetail implements Serializable{

    /**
     *  进度
     */
    private String activityName;

    /**
     *  发起人 申请人
     */
    private String applyUser;

    /**
     *  任务开始时间
     */
    private String startTime;

    /**
     *  任务结束时间
     */
    private String endTime;

    /**
     *  任务办理人
     */
    private String assignee;

    /**
     *  意见
     */
    private String comment;

    /**
     *  步骤
     */
    private String flowTurnTransition;

    /**
     *  附件信息
     */
    private List<String> attachmentInfoList;

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getApplyUser() {
        return applyUser;
    }

    public void setApplyUser(String applyUser) {
        this.applyUser = applyUser;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getFlowTurnTransition() {
        return flowTurnTransition;
    }

    public void setFlowTurnTransition(String flowTurnTransition) {
        this.flowTurnTransition = flowTurnTransition;
    }

    public List<String> getAttachmentInfoList() {
        return attachmentInfoList;
    }

    public void setAttachmentInfoList(List<String> attachmentInfoList) {
        this.attachmentInfoList = attachmentInfoList;
    }
}
