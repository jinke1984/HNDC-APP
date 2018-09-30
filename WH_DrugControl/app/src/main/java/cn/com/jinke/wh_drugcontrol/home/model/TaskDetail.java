package cn.com.jinke.wh_drugcontrol.home.model;

import java.io.Serializable;

/**
 * TaskDetail
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/10/19
 */

public class TaskDetail implements Serializable {

    private String id;
    private String name;
    private String createTime;
    private String processInstanceId;
    private String processDefinitionId;
    private String taskDefinitionKey;
    private String taskType;
    private String personId;
    private String docId;
    private String realName;
    private String dealName;
    private String updateModel;
    private String deleteModel;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessDefinitionId(String processDefinitionId) {
        this.processDefinitionId = processDefinitionId;
    }

    public String getProcessDefinitionId() {
        return processDefinitionId;
    }

    public void setTaskDefinitionKey(String taskDefinitionKey) {
        this.taskDefinitionKey = taskDefinitionKey;
    }

    public String getTaskDefinitionKey() {
        return taskDefinitionKey;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getDocId() {
        return docId;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRealName() {
        return realName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
    }

    public String getDealName() {
        return dealName;
    }

    public void setUpdateModel(String updateModel) {
        this.updateModel = updateModel;
    }

    public String getUpdateModel() {
        return updateModel;
    }

    public void setDeleteModel(String deleteModel) {
        this.deleteModel = deleteModel;
    }

    public String getDeleteModel() {
        return deleteModel;
    }

}
