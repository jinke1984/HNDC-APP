package cn.com.jinke.wh_drugcontrol.chat.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/** 聊天消息信息
 * Created by admin on 2017/10/25.
 */
@Table(name = "chat_msg_record")
public class ChatMessageInfo {
    public ChatMessageInfo() {
    }

    @Column(name = "chatIndex",isId = true)
    public long chatIndex;

    @Column(name = "_id")
    public String id;//id

    @Column(name = "senderId")
    public String senderId;


    @Column(name = "msgContent")
    public String msgContent;

    @Column(name = "msgTime")
    public String msgTime;

    @Column(name = "senderName")
    public String senderName;//吸毒人名称

    @Column(name = "sex")
    public String sex;

    @Column(name = "msgType")
    public int msgType;

    @Column(name = "extraUrl")
    public String extraUrl;

    @Column(name = "direction")
    public int direction;

    @Column(name = "receiverId")
    public String receiverId;

    @Column(name = "receiverName")
    public String receiverName;


    public String getExtraUrl() {
        return extraUrl;
    }

    public void setExtraUrl(String extraUrl) {
        this.extraUrl = extraUrl;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public String getMsgTime() {
        return msgTime;
    }

    public void setMsgTime(String msgTime) {
        this.msgTime = msgTime;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public long getChatIndex() {
        return chatIndex;
    }

    public void setChatIndex(long chatIndex) {
        this.chatIndex = chatIndex;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    @Override
    public String toString() {
        return "ChatMessageInfo{" +
                "chatIndex=" + chatIndex +
                ", id='" + id + '\'' +
                ", senderId='" + senderId + '\'' +
                ", msgContent='" + msgContent + '\'' +
                ", msgTime='" + msgTime + '\'' +
                ", senderName='" + senderName + '\'' +
                ", sex='" + sex + '\'' +
                ", msgType=" + msgType +
                ", extraUrl='" + extraUrl + '\'' +
                ", direction=" + direction +
                ", receiverId='" + receiverId + '\'' +
                ", receiverName='" + receiverName + '\'' +
                '}';
    }
}
