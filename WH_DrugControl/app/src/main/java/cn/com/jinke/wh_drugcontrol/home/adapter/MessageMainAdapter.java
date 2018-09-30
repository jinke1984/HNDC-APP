package cn.com.jinke.wh_drugcontrol.home.adapter;

import android.app.Activity;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.message.model.MessageEntity;

/**
 * Created by jinke on 2017/9/27.
 */

public class MessageMainAdapter extends ProjectBaseAdapter<MessageEntity> {

    public MessageMainAdapter(Activity activity) {
        super(activity);
    }

    @Override
    public View getViewEx(int position, View convertView, MessageEntity messageEntity) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_message_main, null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setItem(messageEntity);
        return convertView;
    }

    private class ViewHolder {
        @ViewInject(R.id.msg_icon)
        private ImageView mMsgIcon;

        @ViewInject(R.id.msg_type)
        private TextView mMsgType;

        @ViewInject(R.id.send_time)
        private TextView mSendTime;

        @ViewInject(R.id.msg_text)
        private TextView mMsgText;

        public ViewHolder(View view) {
            x.view().inject(this, view);
        }

        public void setItem(MessageEntity data) {
            int msgType = data.getType();
            String msgText = "";
            switch (msgType) {
                case MessageEntity.Type.TYPE_WORK:
                    //工作提醒
                    mMsgType.setText(R.string.gztx);
                    mMsgIcon.setBackgroundResource(R.drawable.work_message);
                    msgText = data.getContent();
                    mMsgText.setText(msgText);
                    break;
                case MessageEntity.Type.TYPE_TAKE:
                    //监管消息
                    mMsgType.setText(R.string.jgxx);
                    mMsgIcon.setBackgroundResource(R.drawable.take_message);
                    msgText = data.getContent();
                    mMsgText.setText(msgText);
                    break;
                case MessageEntity.Type.TYPE_GROUP:
                    mMsgType.setText(data.getSenderName());
                    mMsgIcon.setBackgroundResource(R.drawable.group_message);
                    msgText = data.getContent();
                    mMsgText.setText(msgText);
                    break;
                case MessageEntity.Type.TYPE_CHAT:
                    mMsgType.setText(data.getSenderName());
                    mMsgIcon.setBackgroundResource(R.drawable.take_message);
                    msgText = data.getContent();
                    mMsgText.setText(msgText);
                    break;
                case MessageEntity.Type.TYPE_PIC:
                    mMsgType.setText(data.getSenderName());
                    mMsgIcon.setBackgroundResource(R.drawable.take_message);
                    msgText = mContext.getString(R.string.xxtp);
                    mMsgText.setText(msgText);
                    break;
                case MessageEntity.Type.TYPE_VOICE:
                    mMsgType.setText(data.getSenderName());
                    mMsgIcon.setBackgroundResource(R.drawable.take_message);
                    msgText = mContext.getString(R.string.xxyy);
                    mMsgText.setText(msgText);
                    break;
                case MessageEntity.Type.TYPE_STUDY:
                    mMsgType.setText(R.string.xxyd);
                    mMsgIcon.setBackgroundResource(R.drawable.study_message);
                    msgText = data.getContent();
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                        mMsgText.setText(Html.fromHtml(msgText, Html.FROM_HTML_MODE_COMPACT));
                    } else {
                        mMsgText.setText(Html.fromHtml(msgText));
                    }
                    break;
                case MessageEntity.Type.TYPE_SYSTEM:
                    mMsgType.setText(R.string.xtxx);
                    mMsgIcon.setBackgroundResource(R.drawable.work_message);
                    msgText = data.getContent();
                    mMsgText.setText(msgText);
                    break;
                default:
                    break;
            }

            String time = data.getCreateTime();
            mSendTime.setText(time);

        }
    }


}
