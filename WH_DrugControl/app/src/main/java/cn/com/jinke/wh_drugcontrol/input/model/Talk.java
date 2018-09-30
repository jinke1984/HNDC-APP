package cn.com.jinke.wh_drugcontrol.input.model;

import java.io.Serializable;

/**
 * Created by jinke on 2017/8/7.
 */

public class Talk implements Serializable {

    private String id;//id
    private String docId;//档案ID
    private String remindId;//谈话提醒id
    private String talker;// 谈话人
    private String talkDate;// 谈话时间
    private String talkObject;// 谈话对象（家属）
    private Integer talkCounts;// 谈话次数
    private String talkPlace;// 谈话地点
    private String faceToFace;// 是否有附件 1:是;0:否
    private String talkContent;// 家访（谈话记录）
    private String isCamera;//是否面对面谈话 0:否;1:是
    private String cameraPicAdd;// 摄像头拍照 附件地址
    private String fileAdd;//附件地址

    public String getId() {
        return id;
    }

    public void setId(String id) {
        id = id;
    }

    public String getRemindId() {
        return remindId;
    }

    public void setRemindId(String remindId) {
        this.remindId = remindId;
    }

    public String getTalker() {
        return talker;
    }

    public void setTalker(String talker) {
        this.talker = talker;
    }

    public String getTalkDate() {
        return talkDate;
    }

    public void setTalkDate(String talkDate) {
        this.talkDate = talkDate;
    }

    public String getTalkObject() {
        return talkObject;
    }

    public void setTalkObject(String talkObject) {
        this.talkObject = talkObject;
    }

    public Integer getTalkCounts() {
        return talkCounts;
    }

    public void setTalkCounts(Integer talkCounts) {
        this.talkCounts = talkCounts;
    }

    public String getTalkPlace() {
        return talkPlace;
    }

    public void setTalkPlace(String talkPlace) {
        this.talkPlace = talkPlace;
    }

    public String getFaceToFace() {
        return faceToFace;
    }

    public void setFaceToFace(String faceToFace) {
        this.faceToFace = faceToFace;
    }

    public String getTalkContent() {
        return talkContent;
    }

    public void setTalkContent(String talkContent) {
        this.talkContent = talkContent;
    }

    public String getIsCamera() {
        return isCamera;
    }

    public void setIsCamera(String isCamera) {
        this.isCamera = isCamera;
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
}
