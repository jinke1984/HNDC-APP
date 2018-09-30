package cn.com.jinke.wh_drugcontrol.persion.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.persion.constants.PersionUtil;
import cn.com.jinke.wh_drugcontrol.persion.model.Result_SpecialWardEntity;
import cn.com.jinke.wh_drugcontrol.utils.TimeUtil;

/**
 * Created by xkr on 2017/10/31.
 */

public class D_SpecialWardAdapter extends ProjectBaseAdapter<Result_SpecialWardEntity> {
    public D_SpecialWardAdapter(Activity context) {
        super(context);
    }

    @Override
    public View getViewEx(int position, View v, Result_SpecialWardEntity t) {
        ViewHolder holder = null;
        if (v == null || v.getTag() == null)
        {
            holder = new ViewHolder();
            v = inflater.inflate(R.layout.item_hospital_infomation, null);
            holder.name = (TextView) v.findViewById(R.id.xm_tv);
            holder.id_card = (TextView) v.findViewById(R.id.sfzh_tv);
            holder.place_name = (TextView) v.findViewById(R.id.bqmc_tv);
            holder.register_date = (TextView) v.findViewById(R.id.ryrq_tv);
            holder.register_cause = (TextView) v.findViewById(R.id.ryyy_tv);
            holder.register_cause_other = (TextView) v.findViewById(R.id.jtby_tv);
            holder.register_pathway = (TextView) v.findViewById(R.id.rytj_tv);
            holder.signout_date_fact = (TextView) v.findViewById(R.id.cyrq_tv);
            holder.signout_reason = (TextView) v.findViewById(R.id.cyyy_tv);
            holder.signout_join = (TextView) v.findViewById(R.id.sfwflj_tv);
            holder.signout_to_other = (TextView) v.findViewById(R.id.cyqx_tv);
            holder.leader = (TextView) v.findViewById(R.id.bqzrr_tv);
            holder.leaderphone = (TextView) v.findViewById(R.id.lxdh_tv);
            v.setTag(holder);
        }else{
            holder = (ViewHolder) v.getTag();
        }

        String Signout_reason = PersionUtil.getOutHospitalReason(t.getOutHospitalReason());
        String date = PersionUtil.chekParamsAndReturnStr(t.getOutHospitalDate());

        String name = PersionUtil.chekParamsAndReturnStr(t.getHospitalName());

        String phone = PersionUtil.chekParamsAndReturnStr(t.getHospitaPhone());

        String leader = PersionUtil.chekParamsAndReturnStr(t.getHospitaPerson());

        String other = PersionUtil.getOutHospitalDirection(t.getOutHospitalGone());

        String signout_join = t.getIsSeamlessConnection();
        if("1".equals(signout_join)){
            signout_join = "是";
        }else if("2".equals(signout_join)){
            signout_join = "否";
        }else{
            signout_join = "";
        }

        String register_pathway = PersionUtil.getInHospitalWay(t.getInHospitalWay());

        String register_cause = PersionUtil.chekParamsAndReturnStr(t.getInHospitalReason());

        String register_cause_other = PersionUtil.chekParamsAndReturnStr(t.getInHospitalReasonDetail());

        String register_date = TimeUtil.removeHour_Min_Seconds(t.getInHospitalDate());

        holder = (ViewHolder) v.getTag();
        holder.place_name.setText(name);
        holder.register_date.setText(register_date);
        holder.register_cause.setText(register_cause);
        holder.register_cause_other.setText(register_cause_other);
        holder.register_pathway.setText(register_pathway);
        holder.signout_date_fact.setText(TimeUtil.removeHour_Min_Seconds(date));
        holder.signout_reason.setText(Signout_reason);
        holder.signout_join.setText(signout_join);
        holder.signout_to_other.setText(other);
        holder.leader.setText(leader);
        holder.leaderphone.setText(phone);
        holder.id_card.setText(t.getIdentityCard());
        holder.name.setText(t.getRealName());

        return v;
    }

    private final class ViewHolder{
        TextView place_name;
        TextView register_date;
        TextView register_cause;
        TextView register_cause_other;
        TextView register_pathway;
        TextView signout_date_fact;
        TextView signout_reason;
        TextView signout_join;
        TextView signout_to_other;
        TextView leader;
        TextView leaderphone;
        TextView id_card;
        TextView name;
    }

}
