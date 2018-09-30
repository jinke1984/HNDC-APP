package cn.com.jinke.wh_drugcontrol.input.model;

import java.io.Serializable;

/**
 * Created by jinke on 2017/8/8.
 */

public class Report implements Serializable {

    private String id;//id;
    private String docId;//档案ID
    private String reportDate;// 报告时间
    private String reportPlache;// 报告地点
    private String reportContent;// 报告内容
    private String recordPerson;// 记录人
    private Integer reportCounts;// 报告次数
    private String isCamera;//是否面对面谈话 0:否;1:是
    private String cameraPicAdd;// 摄像头拍照 附件地址
    private String fileAdd;//附件地址

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getReportPlache() {
        return reportPlache;
    }

    public void setReportPlache(String reportPlache) {
        this.reportPlache = reportPlache;
    }

    public String getReportContent() {
        return reportContent;
    }

    public void setReportContent(String reportContent) {
        this.reportContent = reportContent;
    }

    public String getRecordPerson() {
        return recordPerson;
    }

    public void setRecordPerson(String recordPerson) {
        this.recordPerson = recordPerson;
    }

    public Integer getReportCounts() {
        return reportCounts;
    }

    public void setReportCounts(Integer reportCounts) {
        this.reportCounts = reportCounts;
    }

    public String getCameraPicAdd() {
        return cameraPicAdd;
    }

    public void setCameraPicAdd(String cameraPicAdd) {
        this.cameraPicAdd = cameraPicAdd;
    }

    public String getFileAdd() {
        return fileAdd;
    }

    public void setFileAdd(String fileAdd) {
        this.fileAdd = fileAdd;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getIsCamera() {
        return isCamera;
    }

    public void setIsCamera(String isCamera) {
        this.isCamera = isCamera;
    }
}
