package cn.com.jinke.wh_drugcontrol.persion.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.persion.constants.PersionUtil;
import cn.com.jinke.wh_drugcontrol.persion.model.Result_CommunityDrugDetoxificationOrRecovery;
import cn.com.jinke.wh_drugcontrol.utils.TimeUtil;

/**
 * Created by xkr on 2017/11/1.
 */

public class D_CommunityAdapter extends ProjectBaseAdapter<Result_CommunityDrugDetoxificationOrRecovery> {
    public D_CommunityAdapter(Activity context) {
        super(context);
    }

    @Override
    public View getViewEx(int position, View v, Result_CommunityDrugDetoxificationOrRecovery t) {
        ViewHolder holder = null;
        if (v == null || v.getTag() == null)
        {
            holder = new ViewHolder();
            v = inflater.inflate(R.layout.item_recovery_infomation, null);
            holder.name = (TextView) v.findViewById(R.id.xm_tv);
            holder.id_card = (TextView) v.findViewById(R.id.sfzh_tv);
            holder.order_dep_name = (TextView) v.findViewById(R.id.zldw_tv);
            holder.decision = (TextView) v.findViewById(R.id.jdswh_tv);
            holder.execution_area = (TextView) v.findViewById(R.id.zxdq_tv);
            holder.execution_area_dep_name = (TextView) v.findViewById(R.id.zxdw_tv);
            holder.type = (TextView) v.findViewById(R.id.zxcs_tv);
            holder.report_date = (TextView) v.findViewById(R.id.zxbdrq_tv);
            holder.create_date = (TextView) v.findViewById(R.id.zxksrq_tv);
            holder.end_date = (TextView) v.findViewById(R.id.zxjsrq_tv);
            holder.currentstate = (TextView) v.findViewById(R.id.dqzt_tv);
            holder.drug_type = (TextView) v.findViewById(R.id.lydpzl_tv);
            holder.drug_method = (TextView) v.findViewById(R.id.lydpfs_tv);
            holder.first_time_date = (TextView) v.findViewById(R.id.ccxdrq_tv);
            holder.last_time_end_date = (TextView) v.findViewById(R.id.zhycbccjsrq_tv);
            holder.detoxification_count = (TextView) v.findViewById(R.id.ljjdcs_tv);
            holder.testtime = (TextView) v.findViewById(R.id.njcs_tv);
            holder.control_level = (TextView) v.findViewById(R.id.dqgkjb_tv);
            holder.zgname = (TextView) v.findViewById(R.id.gkzrr_tv);
            holder.zgphone = (TextView) v.findViewById(R.id.lxdh_tv);
            holder.njjl_tv = (Button) v.findViewById(R.id.njjl_tv);
            v.setTag(holder);
        }
        String type = t.getType();

        if ("1".equals(type))
        {
            type = "社区戒毒";
        } else if ("0".equals(type))
        {
            type = "社区康复";
        } else
        {
            type = "无";
        }
        holder = (ViewHolder) v.getTag();
        holder.name.setText(t.getRealName());
        holder.id_card.setText(t.getIdentityCard());
        holder.order_dep_name.setText(PersionUtil.chekParamsAndReturnStr(t.getHandlingDepartment()));
        holder.decision.setText(PersionUtil.mosaicNo(t.getDecisionNo1(),t.getDecisionNo2(),t.getDecisionNo3(),t.getDecisionNo4()));
        holder.execution_area.setText(PersionUtil.chekParamsAndReturnStr(t.getExeComNameAndAddr()));
        holder.execution_area_dep_name.setText(PersionUtil.chekParamsAndReturnStr(t.getExeComNameAndAddr()));
        holder.type.setText(PersionUtil.chekParamsAndReturnStr(type));
        holder.create_date.setText(TimeUtil.removeHour_Min_Seconds(t.getDrugsBeginDate()));
        holder.report_date.setText(TimeUtil.removeHour_Min_Seconds(t.getActegisterDate()));
        holder.end_date.setText(TimeUtil.removeHour_Min_Seconds(t.getDrugsEndDate()));
        holder.currentstate.setText(PersionUtil.getStatusStr(t.getDealStatus()));
        holder.drug_type.setText(PersionUtil.getDrugName(t.getDrugTypes()));
        holder.drug_method.setText(PersionUtil.getDrugType(t.getDrugStyle()));
        holder.first_time_date.setText(TimeUtil.removeHour_Min_Seconds(t.getFirstChartDate()));
        holder.last_time_end_date.setText(TimeUtil.removeHour_Min_Seconds(t.getLastDrugsEndDate()));
        holder.detoxification_count.setText(PersionUtil.getTimesCount(t.getChartCount()));
//        holder.testtime.setText(t.getChartCount() + "次");
        holder.control_level.setText(PersionUtil.getCurrentControlLevel(t.getControlLevel()));
        holder.zgname.setText(PersionUtil.chekParamsAndReturnStr(t.getControlPerson()));
        holder.zgphone.setText(PersionUtil.chekParamsAndReturnStr(t.getControlPhone()));

        return v;
    }

    private final class ViewHolder
    {
        TextView name;
        TextView id_card;
        TextView order_dep_name;
        TextView decision;
        TextView execution_area;
        TextView execution_area_dep_name;
        TextView type;
        TextView report_date;
        TextView create_date;
        TextView end_date;
        TextView currentstate;
        TextView drug_type;
        TextView drug_method;
        TextView first_time_date;
        TextView last_time_end_date;
        TextView detoxification_count;
        TextView testtime;
        TextView control_level;
        TextView zgname;
        TextView zgphone;
        Button njjl_tv;
    }
}
