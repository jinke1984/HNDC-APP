package cn.com.jinke.wh_drugcontrol.task.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.utils.EmptyUtils;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.task.modle.TaskDetail;

/**
 * Created by xkr on 2017/11/18.
 */

public class TaskDetailAdapter extends ProjectBaseAdapter<TaskDetail> {
    public TaskDetailAdapter(Activity context) {
        super(context);
    }

    @Override
    public View getViewEx(int position, View v, TaskDetail t) {
        Holder holder = null;
        if (v == null || v.getTag() == null){
            holder = new Holder();
            v = inflater.inflate(R.layout.item_mission, null);
            holder.taskNameTV = (TextView) v.findViewById(R.id.tv_task_name);
            holder.assigneeTV = (TextView) v.findViewById(R.id.tv_task_assignee);
            holder.startTimeTV = (TextView) v.findViewById(R.id.tv_task_start_time);
            holder.statusDrawable_iv = (ImageView) v.findViewById(R.id.iv_task_state);
            holder.endTimeTV = (TextView) v.findViewById(R.id.tv_task_end_time);
            holder.commitTV = (TextView) v.findViewById(R.id.tv_task_end_content);
            v.setTag(holder);
        }
        holder = (Holder) v.getTag();
        bindData(holder, t);
        return v;
    }

    private void bindData(Holder aHolder, TaskDetail t){
        StepStatus stepStatus = getDrawableId(t);
        String activityName = t.getActivityName();
        String assignee = t.getAssignee();
        String start = t.getStartTime();
        String end = t.getEndTime();
        String comment = t.getComment();

        aHolder.statusDrawable_iv.setBackgroundResource(stepStatus.DrawableRes);

        if(activityName != null){
            if(aHolder.taskNameTV.getVisibility() == View.GONE){
                aHolder.taskNameTV.setVisibility(View.VISIBLE);
            }
            aHolder.taskNameTV.setText(String.format(mContext.getString(R.string.rwm), activityName));
        }else{
            if(aHolder.taskNameTV.getVisibility() == View.VISIBLE){
                aHolder.taskNameTV.setVisibility(View.GONE);
            }
        }

        if(assignee != null){
            if(aHolder.assigneeTV.getVisibility() == View.GONE){
                aHolder.assigneeTV.setVisibility(View.VISIBLE);
            }
            aHolder.assigneeTV.setText(String.format(mContext.getString(R.string.blrm), assignee));
        }else{
            if(aHolder.assigneeTV.getVisibility() == View.VISIBLE){
                aHolder.assigneeTV.setVisibility(View.GONE);
            }
        }

        if(start != null){
            if(aHolder.startTimeTV.getVisibility() == View.GONE){
                aHolder.startTimeTV.setVisibility(View.VISIBLE);
            }
            aHolder.startTimeTV.setText(String.format(mContext.getString(R.string.kssjm), start));
        }else{
            if(aHolder.startTimeTV.getVisibility() == View.VISIBLE){
                aHolder.startTimeTV.setVisibility(View.GONE);
            }
        }

        if(end != null){
            if(aHolder.endTimeTV.getVisibility() == View.GONE){
                aHolder.endTimeTV.setVisibility(View.VISIBLE);
            }
            aHolder.endTimeTV.setText(String.format(mContext.getString(R.string.jssjm), end));
        }else{
            if(aHolder.endTimeTV.getVisibility() == View.VISIBLE){
                aHolder.endTimeTV.setVisibility(View.GONE);
            }
        }

        if(comment != null){
            if(aHolder.commitTV.getVisibility() == View.GONE){
                aHolder.commitTV.setVisibility(View.VISIBLE);
            }
            aHolder.commitTV.setText(String.format(mContext.getString(R.string.pzm), comment));
        }else{
            if(aHolder.commitTV.getVisibility() == View.VISIBLE){
                aHolder.commitTV.setVisibility(View.GONE);
            }
        }
    }

    /**
     * 1打回  2已处理 其他(flowTurnTransition = null && endTime=null) 待处理
     * @param t
     * @return
     */
    private StepStatus getDrawableId(TaskDetail t){
        StepStatus stepStatus = new StepStatus();
        if("1".equals(t.getFlowTurnTransition())){
            stepStatus.name = "打回";
            stepStatus.DrawableRes = R.mipmap.task_tansaction;
            stepStatus.timeDesc = "打回时间：";
            stepStatus.peopleDesc = "处理人：";
        }else if("2".equals(t.getFlowTurnTransition())){
            stepStatus.name = "已处理";
            stepStatus.DrawableRes = R.mipmap.task_deliver;
            stepStatus.timeDesc = "处理时间：";
            stepStatus.peopleDesc = "处理人：";
        }else if(EmptyUtils.isEmpty(t.getFlowTurnTransition())
                && EmptyUtils.isEmpty(t.getEndTime())){
            stepStatus.name = "待处理";
            stepStatus.DrawableRes = R.mipmap.task_reciever;
            stepStatus.timeDesc = "创建时间：";
            stepStatus.peopleDesc = "待处理人：";
        }else{
            stepStatus.name = "创建";
            stepStatus.DrawableRes = R.mipmap.task_create;
            stepStatus.timeDesc = "创建时间：";
            stepStatus.peopleDesc = "分配人：";
        }
        return stepStatus;
    }
    class StepStatus{
        public String name;
        public int DrawableRes;
        public String timeDesc;
        public String peopleDesc;

    }

    private final class Holder{
        TextView taskNameTV;
        TextView assigneeTV;
        TextView startTimeTV;
        TextView endTimeTV;
        TextView commitTV;
        ImageView statusDrawable_iv;
    }
}
