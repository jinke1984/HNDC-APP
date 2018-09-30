package cn.com.jinke.wh_drugcontrol.chat.model;

/**
 * Created by admin on 2017/10/27.
 */

public class TypeConstants {
    /**
     * 发送类型
     */
    public class SendType{
        /**
         * 1消息 2通知
         */
        public static final int SEND_TYPE_MSG = 1;
        public static final int SEND_TYPE_NOTICE = 2;
    }

    /**
     * 聊天消息类型
     */
    public class ChatMsgType {
        /**
         * 8 文本
         * 12图片 13语音
         */
        public static final int TYPE_TEXT = 8;
        public static final int TYPE_VOICE = 13;
        public static final int TYPE_PICTURE = 12;
        public static final int TYPE_GROUP = 15;

        // 聊天图片上传1图片 2语音
        public static final int FILE_TYPE_VOICE = 2;
        public static final int FILE_TYPE_PICTURE = 1;

        /**
         * 聊天消息 方向    20 发出去  21 进来
         */
        public static final int DIRECTION_OUT = 20;
        public static final int DIRECTION_IN = 21;
    }

}
