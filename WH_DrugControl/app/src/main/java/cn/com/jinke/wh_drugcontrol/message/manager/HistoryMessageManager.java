package cn.com.jinke.wh_drugcontrol.message.manager;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.com.jinke.wh_drugcontrol.chat.manager.ChatManager;
import cn.com.jinke.wh_drugcontrol.chat.model.ChatMessageInfo;
import cn.com.jinke.wh_drugcontrol.chat.model.TypeConstants;
import cn.com.jinke.wh_drugcontrol.input.manager.InputManager;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.message.model.HistoryMessage;
import cn.com.jinke.wh_drugcontrol.message.model.MessageEntity;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.DateUtil;
import cn.com.jinke.wh_drugcontrol.utils.MsgKey;
import cn.com.jinke.wh_drugcontrol.utils.UrlSetting;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CommonRequestParams;

import static cn.com.jinke.wh_drugcontrol.chat.model.TypeConstants.ChatMsgType.DIRECTION_IN;

/**
 * @author jinke
 * @date 2018/6/5
 * @description
 */
public class HistoryMessageManager extends UrlSetting implements CodeConstants, MsgKey {

    private static HistoryMessageManager instance;

    private HistoryMessageManager() {

    }

    public static HistoryMessageManager getInstance() {
        if (instance == null) {
            synchronized (HistoryMessageManager.class) {
                if (instance == null) {
                    instance = new HistoryMessageManager();
                }
            }
        }
        return instance;
    }

    /**
     * 获取信息推送历史类型(最近的信息)
     */
    public void getHistoryMessage(){
        String userId = MasterManager.getInstance().getUserCard().getUserId();
        CommonRequestParams.Builder builder = new CommonRequestParams.Builder(HISTORYMESSAGE);
        builder.putParams(USER_ID, userId);
        x.http().post(builder.build(), new CallbackWrapper<List<HistoryMessage>>(MESSAGE_HISTORY_MANAGER,2){

            @Override
            public void onSuccess(int state, String msg, List<HistoryMessage> object, int total) {
                if(state == SUCCESS){
                    if (object != null){
                        for(HistoryMessage historyMessage : object){
                            MessageEntity messageEntity = new MessageEntity();
                            messageEntity.setId(historyMessage.getId());
                            messageEntity.setType(Integer.parseInt(historyMessage.getType()));
                            messageEntity.setContent(historyMessage.getContent());
                            messageEntity.setCreateTime(historyMessage.getCreateTime());
                            messageEntity.setPersonId(historyMessage.getUserId());
                            messageEntity.setAppNewsList(historyMessage.getAppNewsList());
                            messageEntity.setSenderName(historyMessage.getSendName());
                            messageEntity.setSendNo(historyMessage.getSendNo());
                            if( Integer.parseInt(historyMessage.getType()) == TypeConstants.ChatMsgType.TYPE_GROUP){
                                saveGroupMsg(historyMessage);
                            }
                            MessageManager.onReceiveMessage(messageEntity);
                        }
                    }
                }
            }
        });
    }

    private void saveGroupMsg(HistoryMessage aHistoryMessage){
        ChatMessageInfo chatMessageInfo = new ChatMessageInfo();
        chatMessageInfo.setId(aHistoryMessage.getId());
        chatMessageInfo.setSenderId(MasterManager.getInstance().getUserCard().getUserId());
        chatMessageInfo.setReceiverId(aHistoryMessage.getPersonId());
        chatMessageInfo.setMsgTime(aHistoryMessage.getCreateTime());
        chatMessageInfo.setMsgType(Integer.parseInt(aHistoryMessage.getType()));
        chatMessageInfo.setMsgContent(aHistoryMessage.getContent());
        chatMessageInfo.setDirection(DIRECTION_IN);
        chatMessageInfo.setSenderName(aHistoryMessage.getSendName());
        ChatManager.getInstance().saveTableItemInfo(chatMessageInfo);
    }

    /**
     * 根据类型获取历史推送信息
     * @param type 信息类型
     * @param beginTime 时间
     * @param flag 向上或向下取标识 传入参数true或false 若果是true查询按>= beginTime查,如果是false就按 < beginTime
     */
    public void getHistoryMessageDetail(int type, String beginTime, boolean flag){
        String userId = MasterManager.getInstance().getUserCard().getUserId();
        CommonRequestParams.Builder builder = new CommonRequestParams.Builder(HISTORYMESSAGEDETAIL);
        builder.putParams(USER_ID, userId);
        builder.putParams(TYPE, type);
        builder.putParams(BEGINTIME, beginTime);
        builder.putParams(FLAG, flag);
        builder.putParams(P_SIZE, 10);
        x.http().post(builder.build(), new CallbackWrapper<List<HistoryMessage>>(MESSAGE_HISTORY_MANAGER_DETAIL,2){

            @Override
            public void onSuccess(int state, String msg, List<HistoryMessage> object, int total) {
                ArrayList<MessageEntity> list = new ArrayList<>();
                if(state == CodeConstants.SUCCESS){
                    if (object != null){
                        for(HistoryMessage historyMessage : object){
                            MessageEntity messageEntity = new MessageEntity();
                            messageEntity.setId(historyMessage.getId());
                            messageEntity.setType(Integer.parseInt(historyMessage.getType())+1);
                            messageEntity.setContent(historyMessage.getContent());
                            messageEntity.setCreateTime(historyMessage.getCreateTime());
                            messageEntity.setPersonId(historyMessage.getUserId());
                            messageEntity.setAppNewsList(historyMessage.getAppNewsList());
                            messageEntity.setSenderName(historyMessage.getSendName());
                            messageEntity.setSendNo(historyMessage.getSendNo());
                            list.add(messageEntity);
                        }
                    }
                }
                MessageManager.onReceiveMessage(list);
            }
        });
    }
}
