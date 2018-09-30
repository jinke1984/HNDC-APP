package cn.com.jinke.wh_drugcontrol.home.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Locale;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectApplication;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.home.model.HomeTask;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;

/**
 * Created by jinke on 2017/9/7.
 */

public class HomeTaskAdapter extends ProjectBaseAdapter<HomeTask> {

    public HomeTaskAdapter(Activity context) {
        super(context);
    }

    @Override
    public View getViewEx(int position, View v, HomeTask homeTask) {
        Holder holder = null;
        if (v == null) {
            v = inflater.inflate(R.layout.item_home_task, null, false);
            holder = new Holder(v);
            v.setTag(holder);
        } else {
            holder = (Holder) v.getTag();
        }
        holder.setItem(homeTask);
        return v;
    }

    private class Holder {

        private View itemView;

        @ViewInject(R.id.my_task_imageview)
        private ImageView mTaskIV;

        @ViewInject(R.id.my_task_num)
        private TextView mNumTV;

        @ViewInject(R.id.my_task_name)
        private TextView mNameTV;

        public Holder(View view) {
            x.view().inject(this, view);
            this.itemView = view;
        }

        public void setItem(final HomeTask homeTask) {
            mTaskIV.setBackgroundResource(homeTask.getTaskImage());
            int num = homeTask.getTaskNum();
            if (num == 0) {
                if (mNumTV.getVisibility() == View.VISIBLE) {
                    mNumTV.setVisibility(View.GONE);
                }
            } else {
                if (mNumTV.getVisibility() == View.GONE) {
                    mNumTV.setVisibility(View.VISIBLE);
                }
            }
            mNumTV.setText(String.format(Locale.CHINESE, "%d", homeTask.getTaskNum()));
            mNameTV.setText(homeTask.getTaskName());
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (homeTask.getTaskNum() == 0) {
                        ProjectApplication.showToast(R.string.gzyjwc);
                        return;
                    }
                    ARouter.getInstance().build(RouteUtils.R_TASK_DISTRIBUTE_UI)
                            .withSerializable("hometask", homeTask)
                            .navigation();
                }
            });
        }
    }
}
