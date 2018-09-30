package cn.com.jinke.wh_drugcontrol.message.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.message.model.MessageEntity;
import cn.com.jinke.wh_drugcontrol.utils.DateUtil;
import cn.com.jinke.wh_drugcontrol.utils.RequestHelper;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;

/**
 * @author jinke
 * @date 2018/4/12
 * @description  学习园地的数据适配器
 */
public class StudyMsgListAdapter extends ProjectBaseAdapter<MessageEntity> {

    public StudyMsgListAdapter(Activity activity) {
        super(activity);
    }

    @Override
    public View getViewEx(int position, View convertView, MessageEntity messageEntity) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_study_message, null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.setItem(position, messageEntity);
        return convertView;
    }

    private class ViewHolder{

        @ViewInject(R.id.send_time)
        private TextView mSendTime;

        @ViewInject(R.id.layout_first_message)
        private View mLayoutFirstMsg;

        @ViewInject(R.id.first_msg_img)
        private ImageView mFirstMsgImg;

        @ViewInject(R.id.first_msg_text)
        private TextView mFirstMsgText;

        @ViewInject(R.id.layout_second_message)
        private View mLayoutSecondMsg;

        @ViewInject(R.id.second_msg_img)
        private ImageView mSecondMsgImg;

        @ViewInject(R.id.second_msg_text)
        private TextView mSecondMsgText;

        @ViewInject(R.id.layout_third_message)
        private View mLayoutThirdMsg;

        @ViewInject(R.id.third_msg_img)
        private ImageView mThirdMsgImg;

        @ViewInject(R.id.third_msg_text)
        private TextView mThirdMsgText;

        private MessageEntity mData;


        public ViewHolder(View view) {
            x.view().inject(this, view);
        }

        public void setItem(int position, MessageEntity data){

            if(!TextUtils.isEmpty(data.getAppNewsList())){
                mData = data;
                try{
                    if (position == 0) {
                        mSendTime.setVisibility(View.VISIBLE);
                        mSendTime.setText(DateUtil.getInstance().friendlyDT2(mContext,
                                DateUtil.getInstance().stringToLong(data.getCreateTime())));
                    }
                    else {
                        if (DateUtil.getInstance().isSameDay(DateUtil.getInstance().stringToDate(data.getCreateTime()),
                                DateUtil.getInstance().stringToDate(getItem(position - 1).getCreateTime()))){
                            mSendTime.setVisibility(View.GONE);
                        }
                        else {
                            mSendTime.setVisibility(View.VISIBLE);
                            mSendTime.setText(DateUtil.getInstance().friendlyDT2(mContext,
                                    DateUtil.getInstance().stringToLong(data.getCreateTime())));
                        }
                    }
                    JSONArray array = new JSONArray(data.getAppNewsList());
                    int length = array.length();
                    if (length > 2) {
                        mLayoutFirstMsg.setVisibility(View.VISIBLE);
                        mLayoutSecondMsg.setVisibility(View.VISIBLE);
                        mLayoutThirdMsg.setVisibility(View.VISIBLE);
                    }
                    else if (length > 1) {
                        mLayoutFirstMsg.setVisibility(View.VISIBLE);
                        mLayoutSecondMsg.setVisibility(View.VISIBLE);
                        mLayoutThirdMsg.setVisibility(View.GONE);
                    }
                    else if (length > 0) {
                        mLayoutFirstMsg.setVisibility(View.VISIBLE);
                        mLayoutSecondMsg.setVisibility(View.GONE);
                        mLayoutThirdMsg.setVisibility(View.GONE);
                    }
                    if (length > 0) {
                        JSONObject jsonObj = array.getJSONObject(0);
                        String path = jsonObj.optString(MSG_IMG_URL);
                        if(!TextUtils.isEmpty(path)){
                            StringBuilder url = new StringBuilder();
                            url.append(RequestHelper.getInstance().STUDY_URL);
                            url.append(path);
                            x.image().bind(mFirstMsgImg, url.toString());
                        }else {
                            mFirstMsgImg.setBackgroundResource(R.color.white);
                        }
                        mFirstMsgText.setText(jsonObj.optString(MSG_TITLE));
                        mLayoutFirstMsg.setTag(jsonObj.optString(MSG_LINK_URL));
                    }
                    if (length > 1) {
                        JSONObject jsonObj = array.getJSONObject(1);
                        String path = jsonObj.optString(MSG_IMG_URL);
                        if(!TextUtils.isEmpty(path)){
                            StringBuilder url = new StringBuilder();
                            url.append(RequestHelper.getInstance().STUDY_URL);
                            url.append(path);
                            x.image().bind(mSecondMsgImg, url.toString());
                        }else{
                            mSecondMsgImg.setBackgroundResource(R.color.white);
                        }
                        mSecondMsgText.setText(jsonObj.optString(MSG_TITLE));
                        mLayoutSecondMsg.setTag(jsonObj.optString(MSG_LINK_URL));
                    }
                    if (length > 2) {
                        JSONObject jsonObj = array.getJSONObject(2);
                        String path = jsonObj.optString(MSG_IMG_URL);
                        if(!TextUtils.isEmpty(path)){
                            StringBuilder url = new StringBuilder();
                            url.append(RequestHelper.getInstance().STUDY_URL);
                            url.append(path);
                            x.image().bind(mThirdMsgImg, url.toString());
                        }else {
                            mThirdMsgImg.setBackgroundResource(R.color.white);
                        }
                        mThirdMsgText.setText(jsonObj.optString(MSG_TITLE));
                        mLayoutThirdMsg.setTag(jsonObj.optString(MSG_LINK_URL));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        @Event(value = {R.id.layout_first_message, R.id.layout_second_message, R.id.layout_third_message})
        private void onClick(View v){
            String linkUrl = (String) v.getTag();
            if (!TextUtils.isEmpty(linkUrl)) {
                String userId = MasterManager.getInstance().getUserCard().getUserId();
                StringBuilder linkurl = new StringBuilder();
                linkurl.append(RequestHelper.getInstance().OUT_CHAT_URL);
                linkurl.append(linkUrl);
                linkurl.append("&userId=");
                linkurl.append(userId);
                ARouter.getInstance().build(RouteUtils.R_WEB_UI)
                        .withString(TITLE, mContext.getString(R.string.xxyd))
                        .withString(URL,linkurl.toString())
                        .navigation();
            }
        }


    }
}
