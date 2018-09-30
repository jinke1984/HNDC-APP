package cn.com.jinke.wh_drugcontrol.manager.model;

import java.io.Serializable;

/**
 * Created by jinke on 2017/9/8.
 */

public class VersionInfo implements Serializable {

    /**
     * id : 47370f8317ef4ca1825aecd03c87fd86
     * createUserId : ef8187768af74e72ac79e5ddf1775923
     * createUserName : 蔡军
     * createTime : 2017-11-30 12:03:26
     * updateUserId : ef8187768af74e72ac79e5ddf1775923
     * updateUserName : 蔡军
     * updateTime : 2017-11-30 12:03:26
     * optLock : 0
     * dataState : 1
     * dealStatus : 0
     * name : 数据禁毒APP
     * type : 2
     * version : 2
     * description : 1
     * path : /app/5a6338e2814b4199a90e71f405588322.apk
     * downPath : sys/userManage.html?command=download&fileName=5a6338e2814b4199a90e71f405588322.apk
     * size : 29999250
     */

    private String id;
    private String createUserId;
    private String createUserName;
    private String createTime;
    private String updateUserId;
    private String updateUserName;
    private String updateTime;
    private int optLock;
    private int dataState;
    private int dealStatus;
    private String name;
    private String type;
    private int version;
    private String description;
    private String path;
    private String downPath;
    private long size;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getOptLock() {
        return optLock;
    }

    public void setOptLock(int optLock) {
        this.optLock = optLock;
    }

    public int getDataState() {
        return dataState;
    }

    public void setDataState(int dataState) {
        this.dataState = dataState;
    }

    public int getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(int dealStatus) {
        this.dealStatus = dealStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getDownPath() {
        return downPath;
    }

    public void setDownPath(String downPath) {
        this.downPath = downPath;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
