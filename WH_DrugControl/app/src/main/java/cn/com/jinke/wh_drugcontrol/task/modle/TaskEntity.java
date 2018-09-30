package cn.com.jinke.wh_drugcontrol.task.modle;

import java.io.Serializable;

/**
 * Created by xkr on 2017/11/10.
 */

public class TaskEntity implements Serializable{
    private String id;

    /**
     *  任务名称
     */
    private String name;

    /**
     *  发起时间
     */
    private String createTime;

    /**
     *  流程id
     */
    private String processInstanceId;

    /**
     *  任务id
     */
    private String processDefinitionId;

    /**
     *  任务节点
     */
    private String taskDefinitionKey;

    /**
     *  任务类型
     */
    private String taskType;

    /**
     *  吸毒人员id
     */
    private String personId;

    /**
     *  档案id
     */
    private String docId;

    /**
     *  吸毒人员姓名
     */
    private String realName;

    /**
     *  处理任务人
     */
    private String dealName;

    /**
     *  修改模块
     */
    private String updateModel;

    /**
     * 删除模块
     */
    private String deleteModel;

    /**
     *  资源id
     */
    private String entityId;

    /**
     *  请假天数
     */
    private int leaveDate;

    /**
     *  数据状态
     */
    private int dealStatus;


    /**
     * 变更执行地->同级别标识  同社区：0；同区县：1；同市：2；同省：3；不同省：4；
     */
    private int sameArea;

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getTaskDefinitionKey() {
        return taskDefinitionKey;
    }

    public void setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getDealName() {
        return dealName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
    }

    public String getUpdateModel() {
        return updateModel;
    }

    public void setUpdateModel(String updateModel) {
        this.updateModel = updateModel;
    }

    public String getDeleteModel() {
        return deleteModel;
    }

    public void setDeleteModel(String deleteModel) {
        this.deleteModel = deleteModel;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public int getLeaveDate() {
        return leaveDate;
    }

    public void setLeaveDate(int leaveDate) {
        this.leaveDate = leaveDate;
    }

    public int getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(int dealStatus) {
        this.dealStatus = dealStatus;
    }

    public int getSameArea() {
        return sameArea;
    }

    public void setSameArea(int sameArea) {
        this.sameArea = sameArea;
    }
}
