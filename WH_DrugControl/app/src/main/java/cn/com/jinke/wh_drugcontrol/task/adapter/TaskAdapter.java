package cn.com.jinke.wh_drugcontrol.task.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.task.modle.TaskEntity;
import cn.com.jinke.wh_drugcontrol.task.modle.TaskType;

/**
 * Created by xkr on 2017/11/10.
 */

public class TaskAdapter extends ProjectBaseAdapter<TaskEntity> {
    public TaskAdapter(Activity context) {
        super(context);
    }

    @Override
    public View getViewEx(int position, View v, TaskEntity taskEntity) {
        Holder holder = null;
        if (v == null || v.getTag() == null) {
            holder = new Holder();
            v = inflater.inflate(R.layout.item_my_task, null);
            holder.stateIv = (ImageView) v.findViewById(R.id.state_iv);
            holder.stateTv = (TextView) v.findViewById(R.id.state_tv);
			holder.missionName = (TextView)v.findViewById(R.id.tv_mission_name);
			holder.taskTime = (TextView)v.findViewById(R.id.tv_task_time);
            holder.dealState = (TextView) v.findViewById(R.id.deal_state);

            v.setTag(holder);
        }
        holder = (Holder) v.getTag();
        bindData(holder, taskEntity);
        return v;
    }

    private final class Holder {
        ImageView stateIv;//状态图标
        TextView stateTv; //状态文字
        TextView missionName;//任务名称
        TextView taskTime;//任务周期
        TextView dealState; //任务处理情况
    }

    private void bindData(Holder aHolder, TaskEntity t) {
        aHolder.missionName.setText(t.getName());
//        aHolder.stateIv.setBackgroundDrawable(mContext.getResources().getDrawable(TaskType.getTaskTypeResId(t.getTaskType())));
        aHolder.taskTime.setText(t.getCreateTime());
        aHolder.stateTv.setText(TaskType.getTaskTypeText(t.getTaskType()));
        aHolder.dealState.setText(TaskType.getTaskTypeText(t.getTaskType()));
    }
}
