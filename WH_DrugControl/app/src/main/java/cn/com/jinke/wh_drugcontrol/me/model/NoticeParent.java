package cn.com.jinke.wh_drugcontrol.me.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 通知公告-父级
 * Created by jinke on 2017/9/11.
 */

public class NoticeParent implements Serializable {

    private String id;
    private String parentId;
    private String channelName;
    private String parentChannelName;
    private String keywords;
    private String description;
    private String isTopChannel;
    private int sortRank;
    private String picturePath;
    private String indexShow;
    private String browseRank;
    private String tempIndex;
    private String sitePath;
    private String siteUrl;
    private String createUserName;
    private String createTime;
    private ArrayList<NoticeChild> articles;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setParentChannelName(String parentChannelName) {
        this.parentChannelName = parentChannelName;
    }

    public String getParentChannelName() {
        return parentChannelName;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setIsTopChannel(String isTopChannel) {
        this.isTopChannel = isTopChannel;
    }

    public String getIsTopChannel() {
        return isTopChannel;
    }

    public void setSortRank(int sortRank) {
        this.sortRank = sortRank;
    }

    public int getSortRank() {
        return sortRank;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setIndexShow(String indexShow) {
        this.indexShow = indexShow;
    }

    public String getIndexShow() {
        return indexShow;
    }

    public void setBrowseRank(String browseRank) {
        this.browseRank = browseRank;
    }

    public String getBrowseRank() {
        return browseRank;
    }

    public void setTempIndex(String tempIndex) {
        this.tempIndex = tempIndex;
    }

    public String getTempIndex() {
        return tempIndex;
    }

    public void setSitePath(String sitePath) {
        this.sitePath = sitePath;
    }

    public String getSitePath() {
        return sitePath;
    }

    public void setSiteUrl(String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setArticles(ArrayList<NoticeChild> articles) {
        this.articles = articles;
    }

    public ArrayList<NoticeChild> getArticles() {
        return articles;
    }

}
