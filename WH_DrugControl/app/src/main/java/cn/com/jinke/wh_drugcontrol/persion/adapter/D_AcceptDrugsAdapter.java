package cn.com.jinke.wh_drugcontrol.persion.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.persion.constants.PersionUtil;
import cn.com.jinke.wh_drugcontrol.persion.model.Result_AcceptDrugsEntity;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.utils.TimeUtil;

/**
 * Created by xkr on 2017/10/31.
 */

public class D_AcceptDrugsAdapter extends ProjectBaseAdapter<Result_AcceptDrugsEntity> {
    public D_AcceptDrugsAdapter(Activity context) {
        super(context);
    }

    @Override
    public View getViewEx(int position, View v,final Result_AcceptDrugsEntity t) {
        ViewHolder holder = null;
        if (v == null || v.getTag() == null)
        {
            holder = new ViewHolder();
            v = inflater.inflate(R.layout.item_accept_drugs, null);
            holder.name = (TextView) v.findViewById(R.id.xm_tv);
            holder.id_card = (TextView) v.findViewById(R.id.sfzh_tv);
            holder.therapyname = (TextView) v.findViewById(R.id.wczlmzmc_tv);
            holder.input_date = (TextView) v.findViewById(R.id.rzurq_tv);
            holder.first_dose_date = (TextView) v.findViewById(R.id.scfyrq_tv);
            holder.last_dose_date = (TextView) v.findViewById(R.id.zjycfyrq_tv);
            holder.clinic_date = (TextView) v.findViewById(R.id.zzrq_tv);
            holder.clinic = (TextView) v.findViewById(R.id.zrzsmc_tv);
            holder.exit_date = (TextView) v.findViewById(R.id.tzrq_tv);
            holder.exit_reason = (TextView) v.findViewById(R.id.tzyy_tv);
            holder.leader = (TextView) v.findViewById(R.id.mzzrr_tv);
            holder.leaderphone = (TextView) v.findViewById(R.id.lxdh_tv);
            holder.fyqk = (Button) v.findViewById(R.id.fyqk_tv);
            holder.zsbh = (TextView)v.findViewById(R.id.zsbh_tv);
            v.setTag(holder);
        }
        holder = (ViewHolder) v.getTag();

        String Clinic = PersionUtil.chekParamsAndReturnStr(t.getReferraHospitalName());

        String reason =PersionUtil.chekParamsAndReturnStr(t.getOutTreatmentReason());
        String leader = PersionUtil.chekParamsAndReturnStr(t.getHospitalPerson());

        String leaderphone = PersionUtil.chekParamsAndReturnStr(t.getHospitalPhone());

        String therapyname = PersionUtil.chekParamsAndReturnStr(t.getHospitalName());

        holder.therapyname.setText(therapyname);
        holder.input_date.setText(TimeUtil.removeHour_Min_Seconds(t.getGroupInDate()));
        holder.first_dose_date.setText(TimeUtil.removeHour_Min_Seconds(t.getFirstDrugsDate()));
        holder.last_dose_date.setText(TimeUtil.removeHour_Min_Seconds(t.getLastDrugsDate()));
        holder.clinic_date.setText(TimeUtil.removeHour_Min_Seconds(t.getReferralDate()));
        holder.clinic.setText(Clinic);
        holder.exit_date.setText(TimeUtil.removeHour_Min_Seconds(t.getOutGroupDate()));
        holder.exit_reason.setText(reason);
        holder.leader.setText(leader);
        holder.leaderphone.setText(leaderphone);
        holder.id_card.setText(t.getIdentityCard());
        holder.name.setText(PersionUtil.chekParamsAndReturnStr(t.getRealName()));
        holder.zsbh.setText(PersionUtil.chekParamsAndReturnStr(t.getHospitalNo()));

        holder.fyqk.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                ARouter.getInstance().build(RouteUtils.R_PERSION_MEDICATION)
                        .withString(IDCARD,t.getIdentityCard())
                        .navigation();
            }
        });
        return v;
    }

    private final class ViewHolder{
        Button fyqk;
        TextView therapyname;
        TextView input_date;
        TextView first_dose_date;
        TextView last_dose_date;
        TextView clinic_date;
        TextView clinic;
        TextView exit_date;
        TextView exit_reason;
        TextView leader;
        TextView leaderphone;
        TextView id_card;
        TextView name;
        TextView zsbh;
    }
}
