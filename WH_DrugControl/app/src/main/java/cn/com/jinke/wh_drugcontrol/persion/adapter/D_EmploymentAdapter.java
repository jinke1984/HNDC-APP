package cn.com.jinke.wh_drugcontrol.persion.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.persion.constants.PersionUtil;
import cn.com.jinke.wh_drugcontrol.persion.model.Result_EmploymentEntity;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.utils.TimeUtil;

/**
 * Created by xkr on 2017/11/2.
 */

public class D_EmploymentAdapter extends ProjectBaseAdapter<Result_EmploymentEntity> implements CodeConstants {
    public D_EmploymentAdapter(Activity context) {
        super(context);
    }

    @Override
    public View getViewEx(int position, View v,final Result_EmploymentEntity t) {
        ViewHolder holder = null;
        if (v == null || v.getTag() == null)
        {
            holder = new ViewHolder();
            v = inflater.inflate(R.layout.item_sunshine, null);
            holder.name = (TextView) v.findViewById(R.id.xm_tv);
            holder.id_card = (TextView) v.findViewById(R.id.sfzh_tv);
            holder.stationname = (TextView) v.findViewById(R.id.qymc_tv);
            holder.job = (TextView) v.findViewById(R.id.jyzt_tv);       //就业状态
            holder.monthly_pay = (TextView) v.findViewById(R.id.yxqk_tv);
            holder.attendanceVos = (TextView) v.findViewById(R.id.kqjl_tv);
            holder.out_date = (TextView) v.findViewById(R.id.lzrq_tv);
            holder.out_reason = (TextView) v.findViewById(R.id.lzyy_tv);
            holder.leader = (TextView) v.findViewById(R.id.qyzrr_tv);
            holder.leaderphone = (TextView) v.findViewById(R.id.lzxd_tv);
            holder.jytjjl = (TextView) v.findViewById(R.id.jytjjl_tv);
            holder.jyazjl = (TextView) v.findViewById(R.id.jyazjl_tv);
            v.setTag(holder);
        }
        holder = (ViewHolder) v.getTag();

        String leader = PersionUtil.chekParamsAndReturnStr(t.getCompanyContact());

        String leaderphone = PersionUtil.chekParamsAndReturnStr(t.getCompanyPhone());

        String od = PersionUtil.chekParamsAndReturnStr(TimeUtil.removeHour_Min_Seconds(t.getResignDate()));

        String out_reason = PersionUtil.chekParamsAndReturnStr(PersionUtil.getLeaveReason(t.getResignReason()));

        String month = PersionUtil.getSalaryUnit(t.getSalary());

        holder.stationname.setText(PersionUtil.chekParamsAndReturnStr(t.getSettlementCompany()));
        holder.job.setText(PersionUtil.getPositionStatus(t.getResignStatus()));
        holder.monthly_pay.setText(month);
        holder.out_date.setText(od);
        holder.out_reason.setText(out_reason);
        holder.leader.setText(leader);
        holder.leaderphone.setText(leaderphone);
        holder.name.setText(t.getRealName());
        holder.id_card.setText(t.getIdentityCard());

        holder.attendanceVos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            }
        });

        holder.jytjjl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                buildData(RouteUtils.R_PERSION_EMPLOYMENT_RECOMMENT,t);
            }
        });

        holder.jyazjl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                buildData(RouteUtils.R_PERSION_EMPLOYMENT_PLACEMENT,t);
            }
        });
        return v;
    }

    private void buildData(String path,Result_EmploymentEntity t){
        ARouter.getInstance().build(path)
                .withString(IDCARD,t.getIdentityCard())
                .withString(B_NAME,t.getRealName())
                .navigation();
    }

    private final class ViewHolder{

        TextView stationname;
        TextView job;
        TextView monthly_pay;
        TextView attendanceVos;
        TextView out_date;
        TextView out_reason;
        TextView leader;
        TextView leaderphone;
        TextView name;
        TextView id_card;
        TextView jytjjl;
        TextView jyazjl;
    }
}
