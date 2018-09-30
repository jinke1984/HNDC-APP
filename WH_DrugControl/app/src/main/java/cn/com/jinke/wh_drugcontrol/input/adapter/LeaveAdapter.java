package cn.com.jinke.wh_drugcontrol.input.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.input.model.Leave;
import cn.com.jinke.wh_drugcontrol.utils.CommUtils;

/**
 * @author jinke
 * @date 2018/3/7
 * @description
 */

public class LeaveAdapter extends ProjectBaseAdapter<Leave> {

    public LeaveAdapter(Activity context){
        super(context);
    }

    @Override
    public View getViewEx(int position, View v, Leave leave) {
        Holder holder = null;
        if(v == null){
            v = inflater.inflate(R.layout.item_leave, null, false);
            holder = new Holder(v);
            v.setTag(holder);
        }else{
            holder = (Holder)v.getTag();
        }
        holder.setItem(leave);
        return v;
    }

    private class Holder{

        @ViewInject(R.id.leave_applyDate)
        private TextView applyDateTV;

        @ViewInject(R.id.leave_approveDate)
        private TextView approveDateTV;

        @ViewInject(R.id.leave_beginDate)
        private TextView beginDateTV;

        @ViewInject(R.id.leave_endDate)
        private TextView endDateTV;

        @ViewInject(R.id.leave_destination)
        private TextView destinationTV;

        @ViewInject(R.id.leave_leaveReason)
        private TextView leaveReasonTV;


        public Holder(View view){
            x.view().inject(this, view);
        }

        public void setItem(Leave leave){

            String applyDate =  String.format(mContext.getString(R.string.qjsqrq), CommUtils.getInstance().getWU(leave.getApplyDate()));
            String approveDate =  String.format(mContext.getString(R.string.qjpzrq), CommUtils.getInstance().getWU(leave.getApproveDate()));
            String beginDate =  String.format(mContext.getString(R.string.qjkssj), CommUtils.getInstance().getWU(leave.getBeginDate()));
            String endDate =  String.format(mContext.getString(R.string.qjjssj), CommUtils.getInstance().getWU(leave.getEndDate()));
            String destination =  String.format(mContext.getString(R.string.qjqwdz), CommUtils.getInstance().getWU(leave.getDestination()));
            String leaveReason =  String.format(mContext.getString(R.string.qjqjsy), CommUtils.getInstance().getWU(leave.getLeaveReason()));

            applyDateTV.setText(applyDate);
            approveDateTV.setText(approveDate);
            beginDateTV.setText(beginDate);
            endDateTV.setText(endDate);
            destinationTV.setText(destination);
            leaveReasonTV.setText(leaveReason);
        }
    }
}
