package cn.com.jinke.wh_drugcontrol.persion.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.persion.model.AcceptDrugsEntity;

/**
 * Created by xkr on 2017/10/31.
 */

public class AcceptDrugsAdapter extends ProjectBaseAdapter<AcceptDrugsEntity> {
    public AcceptDrugsAdapter(Activity context) {
        super(context);
    }

    @Override
    public View getViewEx(int position, View v, AcceptDrugsEntity t) {
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

        String levelname = t.getExit_date();
        if (TextUtils.isEmpty(levelname) || levelname.equals("null"))
        {
            levelname = "无";
        }

        String Clinic = t.getClinic();
        if (TextUtils.isEmpty(Clinic) || Clinic.equals("null"))
        {
            Clinic = "无";
        }

        String reason = t.getExit_reason();
        if (TextUtils.isEmpty(reason) || reason.equals("null"))
        {
            reason = "无";
        }

        String leader = t.getLeader();
        if (TextUtils.isEmpty(leader) || leader.equals("null"))
        {
            leader = "无";
        }

        String leaderphone = t.getLeaderphone();
        if (TextUtils.isEmpty(leaderphone) || leaderphone.equals("null"))
        {
            leaderphone = "无";
        }

        String therapyname = t.getTherapyname();
        if (TextUtils.isEmpty(therapyname) || therapyname.equals("null"))
        {
            therapyname = "无";
        }

        String input_date = t.getInput_date();
        String first_dose_date = t.getFirst_dose_date();
        String last_dose_date = t.getLast_dose_date();
        String clinic_date = t.getClinic_date();
//		isNull(clinic_date);
//		isNull(last_dose_date);
//		isNull(first_dose_date);
//		isNull(input_date);

        if (TextUtils.isEmpty(clinic_date) || "null".equals(clinic_date.trim()))
        {
            clinic_date = "无";
        }
        if (TextUtils.isEmpty(input_date) || "null".equals(input_date.trim()))
        {
            input_date = "无";
        }
        if (TextUtils.isEmpty(first_dose_date) || "null".equals(first_dose_date.trim()))
        {
            first_dose_date = "无";
        }
        if (TextUtils.isEmpty(last_dose_date) || "null".equals(last_dose_date.trim()))
        {
            last_dose_date = "无";
        }

        holder.therapyname.setText(therapyname);
        holder.input_date.setText(input_date);
        holder.first_dose_date.setText(first_dose_date);
        holder.last_dose_date.setText(last_dose_date);
        holder.clinic_date.setText(clinic_date);
        holder.clinic.setText(Clinic);
        holder.exit_date.setText(levelname);
        holder.exit_reason.setText(reason);
        holder.leader.setText(leader);
        holder.leaderphone.setText(leaderphone);
        holder.id_card.setText(t.getId_card());
        holder.name.setText(t.getName());
        holder.zsbh.setText(t.getClinic_no());

        Intent intent = mContext.getIntent();
//        final String name = intent.getStringExtra(B_TITLE);
        holder.fyqk.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
//                A_MedicationUI.startActivity(mContext, t, name);
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
