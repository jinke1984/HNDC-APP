package cn.com.jinke.wh_drugcontrol.message.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;
/**
 * Created by jinke on 2017/9/26.
 */

@Table(name = "t_message")
public class MessageEntity implements Serializable {

    @Column(name = "msg_id", isId = true, autoGen = false)
    private String id;

    /**
     *  用户id
     */
    @Column(name = "userId")
    private String userId;

    /**
     * 推送用户
     */
    @Column(name = "sendNo")
    private String sendNo;

    /**
     *  消息类型 1新增公告 2特殊公告 3工作提醒 4 监管信息
     */
    @Column(name = "type")
    private int type;

    /**
     *  消息内容
     */
    @Column(name = "content")
    private String content;

    /**
     *  创建时间
     */
    @Column(name = "createTime")
    private String createTime;

    @Column(name = "personId")
    private String personId;

    // ---  聊天专用
    /**
     *  发送者名称
     */
    @Column(name = "sendName")
    private String sendName;

    @Column(name = "sex")
    private String sex;

    /**
     * 学习园地的扩展字段
     */
    @Column(name = "appNewsList")
    private String appNewsList;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSenderName() {
        return sendName;
    }

    public void setSenderName(String sendName) {
        this.sendName = sendName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSendNo() {
        return sendNo;
    }

    public void setSendNo(String sendNo) {
        this.sendNo = sendNo;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getAppNewsList() {
        return appNewsList;
    }

    public void setAppNewsList(String appNewsList) {
        this.appNewsList = appNewsList;
    }

    public static final class Type{

        /**
         *  新增公告
         */
        public final static int TYPE_ADD = 1;

        /**
         * 特殊公告
         */
        public final static int TYPE_SPECIAL = 2;

        /**
         *  工作提醒
         */
        public final static int TYPE_WORK = 3;

        /**
         *  监管信息
         */
        public final static int TYPE_TAKE = 4;

        /**
         *  聊天信息
         */
        public final static int TYPE_CHAT = 8;

        /**
         *  图片消息
         */
        public final static int TYPE_PIC = 12;

        /**
         * 语音消息
         */
        public final static int TYPE_VOICE = 13;

        /**
         * 学习园地
         */
        public final static int TYPE_STUDY = 14;

        /**
         * 消息群发
         */
        public final static int TYPE_GROUP = 15;

        /**
         * 系统消息
         */
        public final static int TYPE_SYSTEM = 11;
    }
}
