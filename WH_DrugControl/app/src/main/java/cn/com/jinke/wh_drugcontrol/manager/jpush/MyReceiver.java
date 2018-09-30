package cn.com.jinke.wh_drugcontrol.manager.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import cn.com.jinke.wh_drugcontrol.chat.manager.ChatManager;
import cn.com.jinke.wh_drugcontrol.home.FrameworkUI;
import cn.com.jinke.wh_drugcontrol.manager.CommonManager;
import cn.com.jinke.wh_drugcontrol.message.manager.MessageManager;
import cn.com.jinke.wh_drugcontrol.message.model.MessageEntity;
import cn.com.jinke.wh_drugcontrol.password.GesturePwdUI;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils.ESHARE;
import cn.com.jinke.wh_drugcontrol.utils.AppLogger;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.GestureARouterUtil;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.jpush.android.api.JPushInterface;

public class MyReceiver extends BroadcastReceiver implements CodeConstants {

    private static MessageEntity mMessage;

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            Bundle bundle = intent.getExtras();
            AppLogger.e("[JPushReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));
            if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
                String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
                AppLogger.e("[JPushReceiver] 接收Registration Id : " + regId);
                CommonManager.getInstance().setPushId(regId);
                ShareUtils.getInstance().commit(ESHARE.SYS, PUSH_ID, regId);
                CommonManager.getInstance().uploadPushId();
            } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                AppLogger.e("[JPushReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
                processCustomMessage(context, bundle);
            } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
                AppLogger.e("[JPushReceiver] 接收到推送下来的通知");
                int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
                AppLogger.e("[JPushReceiver] 接收到推送下来的通知的ID: " + notifactionId);

            } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
                AppLogger.e("[JPushReceiver] 用户点击打开了通知");
                processNotificationMessage(context, mMessage);

//				//打开自定义的Activity
//				Intent i = new Intent(context, TestActivity.class);
//				i.putExtras(bundle);
//				//i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP );
//				context.startActivity(i);

            } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
                AppLogger.e("[JPushReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
                //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

            } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
                boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
                AppLogger.e("[JPushReceiver]" + intent.getAction() + " connected state change to " + connected);
            } else {
                AppLogger.e("[JPushReceiver] Unhandled intent - " + intent.getAction());
            }
        } catch (Exception e) {

        }

    }

    private void processNotificationMessage(Context context, MessageEntity message) {
        if (message == null) return;
        switch (message.getType()) {
            case MessageEntity.Type.TYPE_ADD:
                if (FrameworkUI.isRunning) {
                    if (GesturePwdUI.isRunning) {
                        GestureARouterUtil.navToGesturePwdUi(false, false, RouteUtils.R_INFORM_NOTICE_UI);
                    } else {
                        ARouter.getInstance().build(RouteUtils.R_INFORM_NOTICE_UI).navigation();
                    }
                } else {
                    GestureARouterUtil.navToGesturePwdUi(false, true, RouteUtils.R_INFORM_NOTICE_UI);
                }
                break;
            case MessageEntity.Type.TYPE_CHAT:
                ChatManager.getInstance().processChatMessageNoticeClick(message,true);
                break;
        }
    }

    private void processCustomMessage(Context context, Bundle bundle) {
        String content = bundle.getString(JPushInterface.EXTRA_MESSAGE);

        try {
            if (TextUtils.isEmpty(content)) {
                content = bundle.getString(JPushInterface.EXTRA_EXTRA);
            }
            mMessage = new Gson().fromJson(content, MessageEntity.class);
            JSONObject jsonObj;
            try {
                jsonObj = new JSONObject(content);
            } catch (Exception e) {
                e.printStackTrace();
                content = bundle.getString(JPushInterface.EXTRA_EXTRA);
                jsonObj = new JSONObject(content);
            }
            int msgType = jsonObj.optInt(TYPE);
            switch (msgType) {
                case MessageEntity.Type.TYPE_SYSTEM:
                case MessageEntity.Type.TYPE_WORK:
                case MessageEntity.Type.TYPE_TAKE:
                case MessageEntity.Type.TYPE_SPECIAL:
                case MessageEntity.Type.TYPE_STUDY:
                    String msgText = jsonObj.optString(CONTENT);
                    String msgId = jsonObj.optString(ID);
                    String userId = jsonObj.optString(USER_ID);
                    String createTime = jsonObj.optString(CREATETIME);
                    String sendNo = jsonObj.optString(SENDNO);
                    String persionId = jsonObj.optString(PERSONID);
                    String extraData = jsonObj.optString(APPNEWLIST);
                    MessageEntity messageEntity = new MessageEntity();
                    messageEntity.setId(msgId);
                    messageEntity.setType(msgType);
                    messageEntity.setContent(msgText);
                    messageEntity.setUserId(userId);
                    messageEntity.setCreateTime(createTime);
                    messageEntity.setSendNo(sendNo);
                    messageEntity.setPersonId(persionId);
                    messageEntity.setAppNewsList(extraData);

                    MessageManager.onReceiveMessage(messageEntity);

                    if (/*HomeFragment.isForeground&&*/msgType == MessageEntity.Type.TYPE_SPECIAL) {
                        ShareUtils.getInstance().commit(ESHARE.SYS, SP_KEY_SPE_NOTICE, true);

                        Intent msgIntent = new Intent(ACTION_MESSAGE_RECEIVED);
                        msgIntent.putExtra(JPushInterface.EXTRA_MESSAGE, mMessage);

                        LocalBroadcastManager.getInstance(context).sendBroadcast(msgIntent);
                    }

                    break;
                case MessageEntity.Type.TYPE_ADD:
                    break;
                case MessageEntity.Type.TYPE_PIC:
                case MessageEntity.Type.TYPE_VOICE:
                case MessageEntity.Type.TYPE_CHAT:
                case MessageEntity.Type.TYPE_GROUP:
                    ChatManager.getInstance().processJpushChatMessage(content);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            AppLogger.e("processCustomMessage" + e.toString());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
                if (TextUtils.isEmpty(bundle.getString(JPushInterface.EXTRA_EXTRA))) {
                    AppLogger.e("This message has no Extra data");
                    continue;
                }

                try {
                    JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
                    Iterator<String> it = json.keys();

                    while (it.hasNext()) {
                        String myKey = it.next().toString();
                        sb.append("\nkey:" + key + ", value: [" +
                                myKey + " - " + json.optString(myKey) + "]");
                    }
                } catch (JSONException e) {
                    AppLogger.e("Get message extra JSON error!");
                }

            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

}
