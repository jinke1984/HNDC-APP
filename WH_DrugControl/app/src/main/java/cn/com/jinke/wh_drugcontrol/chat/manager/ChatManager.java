package cn.com.jinke.wh_drugcontrol.chat.manager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.utils.EmptyUtils;
import com.blankj.utilcode.utils.StringUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.common.util.KeyValue;
import org.xutils.db.Selector;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import cn.com.jinke.wh_drugcontrol.chat.model.ChatMessageInfo;
import cn.com.jinke.wh_drugcontrol.chat.model.RoomInfo;
import cn.com.jinke.wh_drugcontrol.chat.model.TypeConstants;
import cn.com.jinke.wh_drugcontrol.database.ProjectDBManager;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.manager.MeManager;
import cn.com.jinke.wh_drugcontrol.me.model.Persion;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.message.manager.MessageManager;
import cn.com.jinke.wh_drugcontrol.message.model.MessageEntity;
import cn.com.jinke.wh_drugcontrol.utils.AppLogger;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.MsgKey;
import cn.com.jinke.wh_drugcontrol.utils.RequestHelper;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.utils.UrlSetting;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CommonRequestParams;

import static cn.com.jinke.wh_drugcontrol.chat.model.TypeConstants.ChatMsgType.DIRECTION_IN;
import static cn.com.jinke.wh_drugcontrol.chat.model.TypeConstants.ChatMsgType.DIRECTION_OUT;

/**
 * Created by admin on 2017/10/23.
 */

public class ChatManager extends UrlSetting implements CodeConstants, MsgKey {
    String TAG = ChatManager.class.getSimpleName();
    ProjectDBManager projectDBManager;

    private ChatManager() {
        projectDBManager = ProjectDBManager.getInstance();
    }

    private static ChatManager instance;

    public static ChatManager getInstance() {
        if (instance == null) {
            synchronized (MeManager.class) {
                if (instance == null) {
                    instance = new ChatManager();
                }
            }
        }
        return instance;
    }

    public DbManager getDbManager() {
        return projectDBManager.getDbManager();
    }

    int mLimitCount = 10;

    /**
     * 生成聊天消息对象
     *
     * @param userCard   发送者信息
     * @param persion    接收者信息
     * @param msgType    消息类型
     * @param msgContent 消息内容
     * @return 消息对象
     */
    public ChatMessageInfo generateChatMessageInfo(UserCard userCard, Persion persion, int msgType, String msgContent) {
        String id = UUID.randomUUID().toString().replaceAll("-", "");
        SimpleDateFormat sdf = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String chatTime = sdf.format(new Date());
        ChatMessageInfo chatMessageInfo = new ChatMessageInfo();
        chatMessageInfo.setId(id);
        chatMessageInfo.setSenderId(userCard.getUserId());
        chatMessageInfo.setSenderName(persion.getRealName());
        chatMessageInfo.setMsgTime(chatTime);
        chatMessageInfo.setMsgType(msgType);
        chatMessageInfo.setDirection(TypeConstants.ChatMsgType.DIRECTION_OUT);

        if (msgType == TypeConstants.ChatMsgType.TYPE_TEXT) {
            chatMessageInfo.setMsgContent(msgContent);
        } else {
            chatMessageInfo.setExtraUrl(msgContent);
        }

        chatMessageInfo.setReceiverId(persion.getPersonID());
        chatMessageInfo.setReceiverName(persion.getRealName());

        return chatMessageInfo;
    }

    /**
     * 处理极光推送 的聊天消息
     *
     * @param json
     */
    public void processJpushChatMessage(String json) throws JSONException {
        JSONObject jsonObj = new JSONObject(json);
        String msgText = jsonObj.optString(CONTENT);
        String msgId = jsonObj.optString(ID);
        String createTime = jsonObj.optString(CREATETIME);
        String content = StringUtils.toDBC(msgText).trim();

        //吸毒人员的id
        String userId = jsonObj.optString(USER_ID);

        //专干自己的id
        String persionId = jsonObj.optString(PERSONID);

        String sendNo = jsonObj.optString(SENDNO);
        String sex = jsonObj.getString(CHAT_PERSION_SEX);
        int msgType = jsonObj.getInt(TYPE);
        String name = jsonObj.optString("sendName");

        // note : 这里的ID 是反向存储的   便于以后查询时方便使用
        ChatMessageInfo chatMessageInfo = new ChatMessageInfo();
        chatMessageInfo.setId(msgId);

        if(msgType == MessageEntity.Type.TYPE_GROUP){
            String id = MasterManager.getInstance().getUserCard().getUserId();
            chatMessageInfo.setSenderId(id);
            chatMessageInfo.setReceiverId(id);
        }else {
            chatMessageInfo.setSenderId(persionId);
            chatMessageInfo.setReceiverId(userId);
        }

        chatMessageInfo.setMsgTime(createTime);
        chatMessageInfo.setMsgType(msgType);
        if (msgType == TypeConstants.ChatMsgType.TYPE_TEXT || msgType == TypeConstants.ChatMsgType.TYPE_GROUP) {
            chatMessageInfo.setMsgContent(content);
        } else {
            StringBuffer url = new StringBuffer();
            url.append(RequestHelper.getInstance().OUT_IMAGE_URL);
            url.append(content);
            chatMessageInfo.setExtraUrl(url.toString());
        }
        chatMessageInfo.setDirection(DIRECTION_IN);
        chatMessageInfo.setSenderName(name);
        chatMessageInfo.setSex(sex);

        //
        saveTableItemInfo(chatMessageInfo);
        MessageProxy.sendEmptyMessage(CHAT_FRESH);

        notifyChatMessage(chatMessageInfo);

    }

    /**
     * 处理聊天通知解析
     * @param messageEntity
     * @param isGroup  true代表聊天消息，false代表群发消息
     */
    public void processChatMessageNoticeClick(MessageEntity messageEntity, boolean isGroup) {
        Persion persion = new Persion();
        persion.setRealName(messageEntity.getSenderName());
        String persionId = "";
        if(messageEntity.getType() == TypeConstants.ChatMsgType.TYPE_GROUP){
            persionId = MasterManager.getInstance().getUserCard().getUserId();
        }else {
            persionId = messageEntity.getUserId();
        }

        persion.setPersonID(persionId);
        persion.setSex(messageEntity.getSex());
        persion.setId(messageEntity.getSendNo());
        ARouter.getInstance().build(RouteUtils.R_CHAT_UI).withBoolean(BOOLEAN, isGroup)
                .withSerializable(CHAT_PERSION, persion).navigation();
    }

    /**
     * 存储表 条目信息
     *
     * @param entity
     */
    public void saveTableItemInfo(ChatMessageInfo entity) {
        try {
            getDbManager().save(entity);
            AppLogger.d(TAG, "saveTableItemInfo success ");
        } catch (DbException e) {
            AppLogger.e(TAG, "saveTableItemInfo exception " + e.getMessage(), true);
            e.printStackTrace();
        }
    }

    /**
     * 删除指定聊天
     *
     * @param entity
     */
    public void deleteTableEntity(ChatMessageInfo entity) {
        try {
            getDbManager().delete(entity);
        } catch (DbException e) {
            AppLogger.e(TAG, "deleteTableEntity " + e.getMessage(), true);
            e.printStackTrace();
        }
    }

    /**
     * 获取最新的那条聊天消息
     *
     * @param senderId
     * @param reciverId
     * @return
     */
    public ChatMessageInfo getNewestChatItem(String senderId, String reciverId) {
        ChatMessageInfo chatMessageInfo = null;
        try {
            chatMessageInfo = getChatSelector(senderId, reciverId).findFirst();
            if(EmptyUtils.isNotEmpty(chatMessageInfo)){
                AppLogger.d(TAG, "getNewestChatItem :" + chatMessageInfo.toString());
            }
        } catch (Exception e) {
            AppLogger.d(TAG, "getNewestChatItem : " + e.getMessage());
            e.printStackTrace();
        }
        return chatMessageInfo;
    }

    private Selector<ChatMessageInfo> getChatSelector(String senderId, String reciverId) throws Exception {
        WhereBuilder whereBuilder = WhereBuilder.b("senderId", "=", senderId)
                .and("receiverId", "=", reciverId);
        Selector<ChatMessageInfo> selector = getDbManager().selector(ChatMessageInfo.class)
                .where(whereBuilder)
                .orderBy("chatIndex",true);
        AppLogger.d(TAG, "getChatSelector " + selector.toString());
        return selector;
    }

    /**
     * 查询聊天记录列表
     * 将表数据 根据chat_index 降序 排列  取 x 条  偏移量 y 然后将查询到的数据 再升序
     *
     * @param senderId
     * @param reciverId
     * @param offSet
     * @return
     */
    public List<ChatMessageInfo> queryChatRecordList(String senderId, String reciverId, int offSet) throws Exception {
        Selector<ChatMessageInfo> selector = getChatSelector(senderId, reciverId)
                .limit(mLimitCount)
                .offset(offSet);
        List<ChatMessageInfo> msgList = selector.findAll();//getDbManager().findAll(ChatMessageInfo.class);// selector.findAll();
        //如果消息不为空   需要将消息  reverse
        if (msgList != null) {
            Collections.reverse(msgList);
            AppLogger.d(TAG, "queryChatRecordList result size :" + msgList.size());
            for (ChatMessageInfo info : msgList) {
                AppLogger.d(TAG, info.toString());
            }
        } else {
            List<ChatMessageInfo> testList =  getDbManager().findAll(ChatMessageInfo.class);
            if(EmptyUtils.isNotEmpty(testList)){
                AppLogger.d(TAG, "queryChatRecordList test is  "+testList.size());
                for (ChatMessageInfo info : testList) {
                    AppLogger.d(TAG, info.toString());
                }
            }
        }
        return msgList;
    }

    /**
     * 发送 聊天消息
     *
     * @param msg_outer
     */
    public void sendMsgWithHttp(ChatMessageInfo msg_outer) {
        CommonRequestParams.Builder builder = new CommonRequestParams.Builder(CHAT_SEND_CHAT);
        //0吸毒人员 1专干
        builder.putParams(CHAT_USER_TYPE, "0");
        builder.putParams(SEND_TYPE, TypeConstants.SendType.SEND_TYPE_MSG);
        //requestParams.addParameter(CHAT_MSG_TYPE, TypeConstants.ChatMsgType.TYPE_TEXT);
        builder.putParams(CHAT_MSG_TYPE, msg_outer.getMsgType());
        if (msg_outer.getMsgType() == TypeConstants.ChatMsgType.TYPE_TEXT) {
            builder.putParams(CHAT_MSG_CONTENT, msg_outer.getMsgContent());
        } else {
            builder.putParams(CHAT_MSG_CONTENT, msg_outer.getExtraUrl());
        }
        builder.putParams(FROM_NO, msg_outer.getSenderId());
        builder.putParams(TO_NOS, msg_outer.getReceiverId());
        builder.putParams(CHAT_PERSION_SEX, msg_outer.getSex());
        builder.putParams("sendName", msg_outer.getSenderName());
        x.http().post(builder.build(), new CallbackWrapper<Void>(MESSAGE_COUNT, 2) {
            @Override
            public void onSuccess(int state, String msg, Void object, int total) {
                super.onSuccess(state, msg, object, total);
                MessageProxy.sendMessage(mMsgCode, state, object);
            }

        });

        notifyChatMessage(msg_outer);
    }

    /** 需要在我的里面显示
     * 通知有消息到了
     * @param chatMsg
     */
    private void notifyChatMessage(ChatMessageInfo chatMsg){
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setId(chatMsg.getId());
        messageEntity.setType(chatMsg.getMsgType());
        String content = chatMsg.getMsgContent();
        if(EmptyUtils.isEmpty(content)){
            content = chatMsg.getExtraUrl();
        }
        messageEntity.setContent(content);
        //这里的serid 设置为 吸毒人员的userId 在 processChatMessageNoticeClick()中会用到
        messageEntity.setUserId(chatMsg.getReceiverId());
        messageEntity.setSenderName(chatMsg.getSenderName());
        messageEntity.setCreateTime(chatMsg.getMsgTime());
        messageEntity.setSendNo(chatMsg.getReceiverId());
        messageEntity.setPersonId(chatMsg.getSenderId());

        MessageManager.onReceiveMessage(messageEntity);
    }


    public void clear() {
        try {
            String sql = "DELETE * FROM chat_msg_record";
            getDbManager().execQuery(sql);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


    /**
     * 视频聊天房间号推送
     * @param persionId  吸毒人员id
     */
    public void getVideoChatRoomNumber(String persionId){
        UserCard userCard = MasterManager.getInstance().getUserCard();
        if(null == userCard){
            return;
        }

        CommonRequestParams.Builder builder = new CommonRequestParams.Builder(CHAT_ROOM_NO);
        builder.putParams("userId", userCard.getUserId());
        builder.putParams("personId", persionId);
        x.http().post(builder.build(),new CallbackWrapper<RoomInfo>(CHAT_ROOM_NUMBER,2){
            @Override
            public void onSuccess(int state, String msg, RoomInfo object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, object);
            }
        });
    }

    public String chatUploadFile(){
        return RequestHelper.getInstance().OUT_CHAT_URL + CHAT_UPLOAD;
    }
}
