package cn.com.jinke.wh_drugcontrol.chat.adapter;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.chat.model.ChatMessageInfo;
import cn.com.jinke.wh_drugcontrol.chat.model.TypeConstants;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.utils.AppLogger;
import cn.com.jinke.wh_drugcontrol.utils.AudioUtils;


public class ChatAdapter extends ProjectBaseAdapter<ChatMessageInfo> {
    private ChatAdapterListenter listener = null;

    public ChatAdapter(Activity context, ChatAdapterListenter listener) {
        super(context);
        this.listener = listener;
    }

    public void updateView(int position, View v, ChatMessageInfo t) {
        AppLogger.d("updateView");
        String userId = MasterManager.getInstance().getUserCard().getUserId();
        ImageView chatImg = (ImageView) v.findViewById(R.id.chat_img);

        // 判断是否处于播放状态
        if (AudioUtils.getInstance().isPlaying()
                && AudioUtils.getInstance().getPlayPosition() == position) {
            // 设置原始图片透明，增加播放动画
            chatImg.setBackgroundResource(R.color.transparent);
            chatImg.setImageResource(R.drawable.message_voice);
            if (chatImg.getDrawable() instanceof AnimationDrawable) {
                AnimationDrawable animationDrawable = (AnimationDrawable) chatImg
                        .getDrawable();
                if (animationDrawable != null) {
                    animationDrawable.start();
                }
            }

        } else {
            if (chatImg.getDrawable() instanceof AnimationDrawable) {
                AnimationDrawable animationDrawable = (AnimationDrawable) chatImg
                        .getDrawable();
                if (animationDrawable != null) {
                    animationDrawable.stop();
                }

                chatImg.setImageResource(R.color.transparent);
                if (t.getSenderId().equals(userId)) {
                    chatImg.setImageResource(R.drawable.message_voice_3);
                } else {
                    chatImg.setImageResource(R.drawable.message_voice_other_3);
                }
            }
        }
    }

    private void setData(String sex,
                         ViewHolder holder, int position, View v, ChatMessageInfo t) {
        holder.content = (TextView) v.findViewById(R.id.me_tv);
        holder.iv = (ImageView) v.findViewById(R.id.head_zg);
        holder.chatImg = (ImageView) v.findViewById(R.id.chat_img);
        holder.head_layout = (LinearLayout) v.findViewById(R.id.head_layout);
        holder.time = (TextView) v.findViewById(R.id.time);
        holder.chatImgIc = (ImageView) v.findViewById(R.id.chat_img_ic);

        // 添加头部点击事件
        holder.head_layout.setTag(position);
        holder.head_layout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                listener.clickLayout(
                        Integer.parseInt(view.getTag().toString()), view);
            }
        });

        // 添加泡泡长按删除
        holder.head_layout
                .setOnLongClickListener(new View.OnLongClickListener() {

                    @Override
                    public boolean onLongClick(View view) {
                        listener.longClickLayout(
                                Integer.parseInt(view.getTag().toString()),
                                view);
                        return true;
                    }
                });

        if (t.getDirection() == TypeConstants.ChatMsgType.DIRECTION_OUT) {
            holder.iv
                    .setBackgroundResource(R.drawable.common_legal_male_avatar);
        } else if (t.getDirection() == TypeConstants.ChatMsgType.DIRECTION_IN) {
            holder.iv
                    .setBackgroundResource(R.drawable.common_female_avatar_default);
        }
        holder.content.setText(t.getMsgContent());
        int length = holder.content.length();
        if (length > 5) {
            holder.content.setGravity(Gravity.CENTER_VERTICAL);
        }
        int type = t.getMsgType();

        if (type == TypeConstants.ChatMsgType.TYPE_VOICE) {
            holder.chatImg.setVisibility(View.VISIBLE);
            holder.chatImgIc.setVisibility(View.GONE);
            holder.content.setVisibility(View.GONE);
            holder.head_layout.setVisibility(View.VISIBLE);
            holder.time.setVisibility(View.VISIBLE);

            // 判断是否处于播放状态
            if (AudioUtils.getInstance().isPlaying()
                    && AudioUtils.getInstance().getPlayPosition() == position) {
                // 设置原始图片透明，增加播放动画
                holder.chatImg.setBackgroundResource(R.color.transparent);
                holder.chatImg.setImageResource(R.drawable.message_voice);
                if (holder.chatImg.getDrawable() instanceof AnimationDrawable) {
                    AnimationDrawable animationDrawable = (AnimationDrawable) holder.chatImg
                            .getDrawable();
                    if (animationDrawable != null) {
                        animationDrawable.start();
                    }
                }
            } else {
                if (holder.chatImg.getDrawable() instanceof AnimationDrawable) {
                    AnimationDrawable animationDrawable = (AnimationDrawable) holder.chatImg
                            .getDrawable();
                    if (animationDrawable != null) {
                        animationDrawable.stop();
                    }

                    holder.chatImg.setImageResource(R.color.transparent);
                    holder.chatImg
                            .setImageResource(R.drawable.message_voice_3);
                }
            }

            // 开启新线程获取语音时间，防止UI出现卡顿现象
            final String url = t.getExtraUrl();
            final ViewHolder tempHolder = holder;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    AppLogger.d("to set audio time :" + url);
                    final String time = AudioUtils.getInstance().gettime(url);
                    AppLogger.d("time :" + time);

                    tempHolder.time.post(new Runnable() {

                        @Override
                        public void run() {
                            String durtion = time;
                            if (Math.abs(Integer.parseInt(time)) > 60) {
                                durtion = "60";
                            }
                            int i = Math.abs(Integer.parseInt(durtion)) % 2 == 0 ? Math
                                    .abs(Integer.parseInt(durtion)) / 2 : Math
                                    .abs(Integer.parseInt(durtion)) / 2 + 1;

                            String space = "";
                            for (int j = 0; j < i; j++) {
                                space += " ";
                            }
                            tempHolder.time.setText(space + durtion + "''");
                        }
                    });
                }
            }).start();

        } else if (type == TypeConstants.ChatMsgType.TYPE_PICTURE) {
            holder.chatImg.setVisibility(View.GONE);
            holder.chatImgIc.setVisibility(View.VISIBLE);
            holder.content.setVisibility(View.GONE);
            String message = t.getExtraUrl();
            if (!TextUtils.isEmpty(message) && !message.equals("null")) {
                Glide.with(mContext).load(message)
                        .placeholder(R.drawable.message_default_img)
                        .into(holder.chatImgIc);
            }
        } else {
            holder.chatImg.setVisibility(View.GONE);
            holder.chatImgIc.setVisibility(View.GONE);
            holder.content.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public View getViewEx(int position, View v, ChatMessageInfo t) {
        // TODO Auto-generated method stub
        ViewHolder holder = null;
        if (t.getDirection() == TypeConstants.ChatMsgType.DIRECTION_OUT) {
            if (v == null) {
                holder = new ViewHolder();
                v = inflater.inflate(R.layout.item_chat_me, null);
                v.setTag(holder);
            } else {
                holder = (ViewHolder) v.getTag();
            }
        } else if (t.getDirection() == TypeConstants.ChatMsgType.DIRECTION_IN) {
            if (v == null) {
                holder = new ViewHolder();
                v = inflater.inflate(R.layout.item_chat, null);
                v.setTag(holder);
            } else {
                holder = (ViewHolder) v.getTag();
            }
        }
        String sex = t.getSex();
        setData(sex, holder, position, v, t);
        return v;
    }

    private final class ViewHolder {
        TextView content;
        ImageView iv;
        ImageView chatImg;
        ImageView chatImgIc;
        LinearLayout head_layout;
        TextView time;
    }
}
