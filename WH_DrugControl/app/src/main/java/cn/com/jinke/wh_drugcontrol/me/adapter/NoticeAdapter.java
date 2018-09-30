package cn.com.jinke.wh_drugcontrol.me.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectApplication;
import cn.com.jinke.wh_drugcontrol.booter.ProjectExpandAdapter;
import cn.com.jinke.wh_drugcontrol.input.manager.PhotoManager;
import cn.com.jinke.wh_drugcontrol.me.model.NoticeChild;
import cn.com.jinke.wh_drugcontrol.me.model.NoticeParent;
import cn.com.jinke.wh_drugcontrol.utils.DateUtil;
import cn.com.jinke.wh_drugcontrol.utils.RequestHelper;
import cn.com.jinke.wh_drugcontrol.utils.UrlSetting;

/**
 * 通知公告
 * Created by jinke on 2017/9/11.
 */

public class NoticeAdapter extends ProjectExpandAdapter<NoticeParent, NoticeChild> {

    @Override
    public View getParentView(int groupPosition, boolean isExpanded, View convertView, NoticeParent noticeParent) {
        ParentHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_notice_parent, null, false);
            holder = new ParentHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ParentHolder) convertView.getTag();
        }
        holder.setItem(noticeParent);
        return convertView;
    }

    @Override
    public View getChildViewEx(int groupPosition, int childPosition, boolean isLastChild, View convertView, NoticeChild noticeChild) {
        ChildHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_notice_child, null, false);
            holder = new ChildHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ChildHolder) convertView.getTag();
        }
        holder.setItem(noticeChild);
        return convertView;
    }

    @Override
    public void selectGroup(int groupP) {

    }

    @Override
    public void selectChild(int groupP, int childP) {

    }

    private static final class ParentHolder {

        @ViewInject(R.id.column_name_tv)
        private TextView mParentNameTV;

        @ViewInject(R.id.check_detail_tv)
        private Button mCheckDetailBtn;

        @ViewInject(R.id.key_name_tv)
        private TextView mParentKeyTV;

        public ParentHolder(View view) {
            x.view().inject(this, view);
        }

        public void setItem(NoticeParent parent) {

            mParentNameTV.setText(parent.getChannelName());
            mParentKeyTV.setText(parent.getKeywords());
        }

    }

    private static final class ChildHolder {

        @ViewInject(R.id.content_iv)
        private ImageView mChildIV;

        @ViewInject(R.id.content_tv)
        private TextView mContentTV;

        @ViewInject(R.id.content_key_tv)
        private TextView mChildKeyTV;

        @ViewInject(R.id.content_time_tv)
        private TextView mChildTimeTV;

        public ChildHolder(View view) {
            x.view().inject(this, view);
        }

        public void setItem(NoticeChild child) {
            Glide.with(ProjectApplication.getContext())
                    .load(RequestHelper.getInstance().getImageRequestHeader() + child.getLitPicture())
                    .placeholder(R.drawable.default_msg)
                    .into(mChildIV);
            mContentTV.setText(child.getTitle());
            mChildKeyTV.setText(child.getKeywords());
            mChildTimeTV.setText(DateUtil.getInstance().changeDate(child.getCreatetime()));
        }
    }
}
