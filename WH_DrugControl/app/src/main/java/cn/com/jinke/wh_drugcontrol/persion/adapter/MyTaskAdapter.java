package cn.com.jinke.wh_drugcontrol.persion.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.persion.model.MyTaskEntity;

/**
 * Created by admin on 2017/10/31.
 */

public class MyTaskAdapter extends ProjectBaseAdapter<MyTaskEntity> {
    public MyTaskAdapter(Activity context) {
        super(context);
    }

    @Override
    public View getViewEx(int position, View v, MyTaskEntity t) {
        ViewHolder holder = null;
        if (v == null || v.getTag() == null)
        {
            holder = new ViewHolder();
            v = inflater.inflate(R.layout.item_mytask, null);
            holder.name = (TextView) v.findViewById(R.id.mytask_name);
            holder.taskNub = (TextView) v.findViewById(R.id.mytask_numb);
            v.setTag(holder);
        }
        holder = (ViewHolder) v.getTag();
        holder.name.setText(t.getName());
        int taskNub = t.getTaskNub();
        if (taskNub == 0)
        {
            holder.taskNub.setVisibility(View.GONE);
        }else {
            holder.taskNub.setVisibility(View.GONE);
            holder.taskNub.setText(taskNub + "");
        }

        return v;
    }

    private final class ViewHolder{
        TextView name;
        TextView taskNub;
    }
}
