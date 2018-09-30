package cn.com.jinke.wh_drugcontrol.persion.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.persion.model.HospitalInfoEntity;

/**
 * Created by xkr on 2017/10/31.
 */

public class HospitalInfoAdapter extends ProjectBaseAdapter<HospitalInfoEntity> {
    public HospitalInfoAdapter(Activity context) {
        super(context);
    }

    @Override
    public View getViewEx(int position, View v, HospitalInfoEntity t) {
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
        }
        holder = (ViewHolder) v.getTag();

        String Signout_reason = t.getSignout_reason();
        if (TextUtils.isEmpty(Signout_reason) || Signout_reason.equals("null"))
        {
            Signout_reason = "无";
        }

        String date = t.getSignout_date_fact();
        if (TextUtils.isEmpty(date) || date.equals("null"))
        {
            date = "无";
        }

        String name = t.getPlace_name();
        if (TextUtils.isEmpty(name) || name.equals("null"))
        {
            name = "无";
        }

        String phone = t.getLeaderphone();
        if (TextUtils.isEmpty(phone) || phone.equals("null"))
        {
            phone = "无";
        }

        String leader = t.getLeader();
        if (TextUtils.isEmpty(leader) || leader.equals("null"))
        {
            leader = "无";
        }

        String other = t.getSignout_to_other();
        if (TextUtils.isEmpty(other) || other.equals("null"))
        {
            other = "无";
        }

        String signout_join = t.getSignout_join();
        if (TextUtils.isEmpty(signout_join) || signout_join.equals("null"))
        {
            signout_join = "无";
        }

        String register_pathway = t.getRegister_pathway();
        if (TextUtils.isEmpty(register_pathway) || register_pathway.equals("null"))
        {
            register_pathway = "无";
        }

        String register_cause = t.getRegister_cause();
        if (TextUtils.isEmpty(register_cause) || register_cause.equals("null"))
        {
            register_cause = "无";
        }

        String register_cause_other = t.getRegister_cause_other();
        if (TextUtils.isEmpty(register_cause_other) || register_cause_other.equals("null"))
        {
            register_cause_other = "无";
        }

        String register_date = t.getRegister_date();
        isNull(register_date);

        holder = (ViewHolder) v.getTag();
        holder.place_name.setText(name);
        holder.register_date.setText(register_date);
        holder.register_cause.setText(register_cause);
        holder.register_cause_other.setText(register_cause_other);
        holder.register_pathway.setText(register_pathway);
        holder.signout_date_fact.setText(date);
        holder.signout_reason.setText(Signout_reason);
        holder.signout_join.setText(signout_join);
        holder.signout_to_other.setText(other);
        holder.leader.setText(leader);
        holder.leaderphone.setText(phone);
        holder.id_card.setText(t.getId_card());
        holder.name.setText(t.getName());

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

    private void isNull(String aStr){
        if (TextUtils.isEmpty(aStr) || aStr.equals(aStr))
        {
            aStr = "无";
        }
    }
}
