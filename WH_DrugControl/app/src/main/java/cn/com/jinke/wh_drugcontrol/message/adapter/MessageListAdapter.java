package cn.com.jinke.wh_drugcontrol.message.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.message.model.MessageEntity;

/**
 * Created by jinke on 2017/9/27.
 */

public class MessageListAdapter extends ProjectBaseAdapter<MessageEntity> {

    public MessageListAdapter(Activity activity) {
        super(activity);
    }

    @Override
    public View getViewEx(int position, View convertView, MessageEntity messageEntity) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_message, null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setItem(messageEntity);
        return convertView;
    }

    private class ViewHolder {

        @ViewInject(R.id.layout_message_textview)
        private TextView mMsgContentTV;

        @ViewInject(R.id.layout_message_root)
        private LinearLayout mMsgContentLY;

        public ViewHolder(View view) {
            x.view().inject(this, view);
        }

        public void setItem(MessageEntity data) {

            String content = "";
            switch (data.getType()) {
                case MessageEntity.Type.TYPE_PIC:
                    content = mContext.getString(R.string.xxtp);
                    break;
                case MessageEntity.Type.TYPE_VOICE:
                    content = mContext.getString(R.string.xxyy);
                    break;
                default:
                    content = data.getContent();
                    break;
            }

            mMsgContentTV.setText(content);
        }
    }
}
