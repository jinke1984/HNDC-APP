package cn.com.jinke.wh_drugcontrol.persion.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.persion.constants.PersionUtil;
import cn.com.jinke.wh_drugcontrol.persion.model.E_EmploymentPlacementEntity;
import cn.com.jinke.wh_drugcontrol.utils.TimeUtil;

/**
 * Created by xkr on 2017/11/3.
 */

public class E_EmploymentPlacementAdapter extends ProjectBaseAdapter<E_EmploymentPlacementEntity> {
    public E_EmploymentPlacementAdapter(Activity context) {
        super(context);
    }

    @Override
    public View getViewEx(int position, View v, E_EmploymentPlacementEntity t) {
        Holder holder = null;
        if(v == null || v.getTag() == null){
            holder = new Holder();
            v = inflater.inflate(R.layout.item_leave_detail, null);
            holder.kqsj = (TextView)v.findViewById(R.id.kqsj_tv);
            holder.kqqk = (TextView)v.findViewById(R.id.kqqk_tv);
            holder.kqsm = (TextView)v.findViewById(R.id.kqsm_tv);
            holder.wgqk = (TextView)v.findViewById(R.id.wgqk_tv);
            holder.jyrq = (TextView)v.findViewById(R.id.jyrq_tv);
            holder.jyzt = (TextView)v.findViewById(R.id.jyzt_tv);

            holder.kqsjj = (TextView)v.findViewById(R.id.kqsj);
            holder.kqqkk = (TextView)v.findViewById(R.id.kqqk);
            holder.kqsmm = (TextView)v.findViewById(R.id.kqsm);
            holder.wgqkk = (TextView)v.findViewById(R.id.wgqk);
            holder.jyrqq = (TextView)v.findViewById(R.id.jyrq);
            holder.jyztt = (TextView)v.findViewById(R.id.jyzt);

            holder.jyrq_layout = (LinearLayout)v.findViewById(R.id.jyrq_layout);
            holder.jyzt_layout = (LinearLayout)v.findViewById(R.id.jyzt_layout);
            v.setTag(holder);
        }
        holder.kqsjj.setText("安置单位:");
        holder.kqqkk.setText("安置岗位:");
        holder.kqsmm.setText("安置类型:");
        holder.wgqkk.setText("月薪:");
        holder.jyrq_layout.setVisibility(View.VISIBLE);
        holder.jyzt_layout.setVisibility(View.VISIBLE);

        holder.kqsj.setText(PersionUtil.chekParamsAndReturnStr(t.getSettlementCompany()));
        holder.kqqk.setText(PersionUtil.chekParamsAndReturnStr(t.getSettlementPosition()));
        holder.kqsm.setText(PersionUtil.getSettlementType(t.getSettlementType()));
        holder.wgqk.setText(PersionUtil.getSalaryUnit(t.getSalary()));
        holder.jyrq.setText(TimeUtil.removeHour_Min_Seconds(PersionUtil.chekParamsAndReturnStr(t.getSettlementDate())));
        holder.jyzt.setText(PersionUtil.getPositionStatus(t.getResignStatus()));

        return v;
    }
    private final class Holder{
        TextView kqsj;
        TextView kqqk;
        TextView kqsm;
        TextView wgqk;
        TextView jyrq;
        TextView jyzt;

        TextView kqsjj;
        TextView kqqkk;
        TextView kqsmm;
        TextView wgqkk;
        TextView jyrqq;
        TextView jyztt;
        LinearLayout jyrq_layout;
        LinearLayout jyzt_layout;
    }
}
