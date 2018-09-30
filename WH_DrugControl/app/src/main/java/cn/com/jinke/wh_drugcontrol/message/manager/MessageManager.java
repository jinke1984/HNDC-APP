package cn.com.jinke.wh_drugcontrol.message.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.jinke.wh_drugcontrol.database.DbManager;
import cn.com.jinke.wh_drugcontrol.message.model.MessageEntity;
import cn.com.jinke.wh_drugcontrol.utils.AppLogger;
import cn.com.jinke.wh_drugcontrol.utils.DateUtil;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.MsgKey;

/**
 * Created by jinke on 2017/9/26.
 */

public class MessageManager implements MsgKey{

    private static boolean sIsInit = false;
    //private final static SparseArray<List<MessageEntity>> sMessageArray = new SparseArray<>();

    private final static HashMap<String, List<MessageEntity>> sMessageArray = new HashMap<>();

    public static void onReceiveMessage(MessageEntity messageEntity){
        if (messageEntity != null){
            String type = "";
            if(messageEntity.getType() == MessageEntity.Type.TYPE_TAKE ||
                    messageEntity.getType() == MessageEntity.Type.TYPE_WORK ||
                    messageEntity.getType() == MessageEntity.Type.TYPE_SPECIAL ||
                    messageEntity.getType() == MessageEntity.Type.TYPE_GROUP ||
                    messageEntity.getType() == MessageEntity.Type.TYPE_STUDY ||
                    messageEntity.getType() == MessageEntity.Type.TYPE_SYSTEM ) {
                type = String.valueOf(messageEntity.getType());
            }else if(messageEntity.getType() == MessageEntity.Type.TYPE_PIC ||
                    messageEntity.getType() == MessageEntity.Type.TYPE_VOICE ||
                    messageEntity.getType() == MessageEntity.Type.TYPE_CHAT){
                type = messageEntity.getSendNo();

            }

            List<MessageEntity> list = getMessageList(type);
            list.add(messageEntity);
            Collections.sort(list, sDescComparable);

            DbManager.getMessageDb().getTableMessage().saveMessage(messageEntity);
            MessageProxy.sendEmptyMessage(MESSAGE_RECEIVE);
        }
    }

    public static void onReceiveMessage(ArrayList<MessageEntity> alist){
        if (alist != null && alist.size() > 0){
            MessageEntity messageEntity = alist.get(0);
            if(messageEntity.getType() == MessageEntity.Type.TYPE_TAKE ||
                    messageEntity.getType() == MessageEntity.Type.TYPE_WORK ||
                    messageEntity.getType() == MessageEntity.Type.TYPE_SPECIAL ||
                    messageEntity.getType() == MessageEntity.Type.TYPE_CHAT ||
                    messageEntity.getType() == MessageEntity.Type.TYPE_PIC ||
                    messageEntity.getType() == MessageEntity.Type.TYPE_VOICE ||
                    messageEntity.getType() == MessageEntity.Type.TYPE_STUDY ||
                    messageEntity.getType() == MessageEntity.Type.TYPE_GROUP ||
                    messageEntity.getType() == MessageEntity.Type.TYPE_SYSTEM) {
                String type = String.valueOf(messageEntity.getType());
                List<MessageEntity> list = getMessageList(type);
                list.addAll(alist);
                Collections.sort(list, sDescComparable);
            }
            DbManager.getMessageDb().getTableMessage().saveMessage(alist);
        }
        MessageProxy.sendEmptyMessage(MESSAGE_RECEIVE);
    }



    public static List<MessageEntity> getMessageList(String key) {
        List<MessageEntity> list = sMessageArray.get(key);
        if (list == null) {
            list = new ArrayList<>();
            sMessageArray.put(key, list);
        }
        return list;
    }

    public static List<MessageEntity> getMessageList(){
        List<MessageEntity> list = new ArrayList<>();
        for(Map.Entry<String, List<MessageEntity>> entry: sMessageArray.entrySet()){
            List<MessageEntity> value = entry.getValue();
            if(value.size() > 0){
                MessageEntity messageEntity = value.get(0);
                if(messageEntity.getType() == MessageEntity.Type.TYPE_TAKE ||
                        messageEntity.getType() == MessageEntity.Type.TYPE_WORK ||
                        messageEntity.getType() == MessageEntity.Type.TYPE_CHAT ||
                        messageEntity.getType() == MessageEntity.Type.TYPE_SPECIAL||
                        messageEntity.getType() == MessageEntity.Type.TYPE_PIC ||
                        messageEntity.getType() == MessageEntity.Type.TYPE_VOICE ||
                        messageEntity.getType() == MessageEntity.Type.TYPE_STUDY ||
                        messageEntity.getType() == MessageEntity.Type.TYPE_GROUP ||
                        messageEntity.getType() == MessageEntity.Type.TYPE_SYSTEM){
                    list.add(messageEntity);
                }
            }
        }
        Collections.sort(list, sDescComparable);
        return list;
    }

    /**
     *  消息的初始化(1. 在登录完成初始化，2. 在APP起来的时候初始化)
     */
    public static void init(){
        if (!sIsInit){
            sMessageArray.clear();
            List<MessageEntity> list = DbManager.getMessageDb().getTableMessage().getMessageList();
            AppLogger.e(list.size()+"");
            for (MessageEntity item : list){
                if(item.getType() == MessageEntity.Type.TYPE_TAKE ||
                        item.getType() == MessageEntity.Type.TYPE_WORK ||
                        item.getType() == MessageEntity.Type.TYPE_SPECIAL||
                        item.getType() == MessageEntity.Type.TYPE_STUDY ||
                        item.getType() == MessageEntity.Type.TYPE_GROUP ||
                        item.getType() == MessageEntity.Type.TYPE_SYSTEM){
                    String type = String.valueOf(item.getType());
                    getMessageList(type).add(item);
                }else if(item.getType() == MessageEntity.Type.TYPE_CHAT ||
                        item.getType() == MessageEntity.Type.TYPE_PIC ||
                        item.getType() == MessageEntity.Type.TYPE_VOICE){
                    String id = item.getSendNo();
                    getMessageList(id).add(item);
                }
            }

            //排序
            for(Map.Entry<String, List<MessageEntity>> entry: sMessageArray.entrySet()){
                List<MessageEntity> value = entry.getValue();
                if(value.size() > 0){
                    MessageEntity messageEntity = value.get(0);
                    if(messageEntity.getType() == MessageEntity.Type.TYPE_TAKE ||
                            messageEntity.getType() == MessageEntity.Type.TYPE_WORK ||
                            messageEntity.getType() == MessageEntity.Type.TYPE_SPECIAL||
                            messageEntity.getType() == MessageEntity.Type.TYPE_STUDY ||
                            messageEntity.getType() == MessageEntity.Type.TYPE_GROUP ||
                            messageEntity.getType() == MessageEntity.Type.TYPE_SYSTEM ||
                            messageEntity.getType() == MessageEntity.Type.TYPE_CHAT ||
                            messageEntity.getType() == MessageEntity.Type.TYPE_PIC ||
                            messageEntity.getType() == MessageEntity.Type.TYPE_VOICE){
                        Collections.sort(value, sDescComparable);
                    }
                }
            }
            sIsInit = true;
        }
    }

    public static void reset() {
        sMessageArray.clear();
        sIsInit = false;
    }

    private static Comparator sDescComparable = new Comparator() {

        @Override
        public int compare(Object lhs, Object rhs) {
            MessageEntity left = (MessageEntity) lhs;
            MessageEntity right = (MessageEntity) rhs;
            Date left_date = DateUtil.getInstance().parseDate(left.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
            Date right_date = DateUtil.getInstance().parseDate(right.getCreateTime(), "yyyy-MM-dd HH:mm:ss");
            if (left_date.before(right_date)) {
                return 1;
            }
            else if (right_date.before(left_date)) {
                return -1;
            }
            else {
                return 0;
            }
        }
    };
}
