package cn.com.jinke.wh_drugcontrol.me.model;

import java.io.Serializable;

/**
 * 通知公告-子级
 * Created by jinke on 2017/9/11.
 */

public class NoticeChild implements Serializable {

    private String id;
    private String channelId;
    private String channelName;
    private String parentChannelId;
    private String parentChannelName;
    private String articleDetailId;
    private String title;
    private String shortTitle;
    private String keywords;
    private String description;
    private String writer;
    private String source;
    private String sourceUrl;
    private String sortRank;
    private String flag;
    private String isCheck;
    private String readPower;
    private String click;
    private String consume;
    private String color;
    private String litPicture;
    private String lastReply;
    private String scores;
    private String positiveComment;
    private String negativeComment;
    private String isFlash;
    private String pubDate;
    private String sendDate;
    private String createtime;
    private String updateTime;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setParentChannelId(String parentChannelId) {
        this.parentChannelId = parentChannelId;
    }

    public String getParentChannelId() {
        return parentChannelId;
    }

    public void setParentChannelName(String parentChannelName) {
        this.parentChannelName = parentChannelName;
    }

    public String getParentChannelName() {
        return parentChannelName;
    }

    public void setArticleDetailId(String articleDetailId) {
        this.articleDetailId = articleDetailId;
    }

    public String getArticleDetailId() {
        return articleDetailId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getShortTitle() {
        return shortTitle;
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

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getWriter() {
        return writer;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSortRank(String sortRank) {
        this.sortRank = sortRank;
    }

    public String getSortRank() {
        return sortRank;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }

    public void setIsCheck(String isCheck) {
        this.isCheck = isCheck;
    }

    public String getIsCheck() {
        return isCheck;
    }

    public void setReadPower(String readPower) {
        this.readPower = readPower;
    }

    public String getReadPower() {
        return readPower;
    }

    public void setClick(String click) {
        this.click = click;
    }

    public String getClick() {
        return click;
    }

    public void setConsume(String consume) {
        this.consume = consume;
    }

    public String getConsume() {
        return consume;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setLitPicture(String litPicture) {
        this.litPicture = litPicture;
    }

    public String getLitPicture() {
        return litPicture;
    }

    public void setLastReply(String lastReply) {
        this.lastReply = lastReply;
    }

    public String getLastReply() {
        return lastReply;
    }

    public void setScores(String scores) {
        this.scores = scores;
    }

    public String getScores() {
        return scores;
    }

    public void setPositiveComment(String positiveComment) {
        this.positiveComment = positiveComment;
    }

    public String getPositiveComment() {
        return positiveComment;
    }

    public void setNegativeComment(String negativeComment) {
        this.negativeComment = negativeComment;
    }

    public String getNegativeComment() {
        return negativeComment;
    }

    public void setIsFlash(String isFlash) {
        this.isFlash = isFlash;
    }

    public String getIsFlash() {
        return isFlash;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

}
