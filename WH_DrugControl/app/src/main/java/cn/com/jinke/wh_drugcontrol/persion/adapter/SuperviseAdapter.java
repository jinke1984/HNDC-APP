package cn.com.jinke.wh_drugcontrol.persion.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.persion.model.SuperviseEntity;

/**
 * Created by xkr on 2017/10/31.
 */

public class SuperviseAdapter extends ProjectBaseAdapter<SuperviseEntity> {
    public SuperviseAdapter(Activity context) {
        super(context);
    }

    @Override
    public View getViewEx(int position, View v, SuperviseEntity t) {
        ViewHolder holder = null;
        if (v == null || v.getTag() == null)
        {
            holder = new ViewHolder();
            v = inflater.inflate(R.layout.item_supervise, null);
            holder.name = (TextView) v.findViewById(R.id.xm_tv);
            holder.id_card = (TextView) v.findViewById(R.id.sfzh_tv);
            holder.crcname = (TextView) v.findViewById(R.id.csmc_tv);
            holder.inther_date = (TextView) v.findViewById(R.id.rsrq_tv);
            holder.inthereason = (TextView) v.findViewById(R.id.rsyy_tv);
            holder.inthecrimire = (TextView) v.findViewById(R.id.ayzm_tv);
            holder.intheunite = (TextView) v.findViewById(R.id.jdjg_tv);
            holder.clc_date_end = (TextView) v.findViewById(R.id.csrq_tv);
            holder.clc_yesno = (TextView) v.findViewById(R.id.sfwflj_tv);
            holder.clc_units = (TextView) v.findViewById(R.id.jhdw_tv);
            holder.clc_person = (TextView) v.findViewById(R.id.jhr_tv);
            holder.leader = (TextView) v.findViewById(R.id.cszrr_tv);
            holder.leaderphone = (TextView) v.findViewById(R.id.lxdh_tv);
            holder.clcreason = (TextView) v.findViewById(R.id.csyy_tv);
            v.setTag(holder);
        }
        holder = (ViewHolder) v.getTag();

        String Signout_reason = t.getClc_date_end();
        if (TextUtils.isEmpty(Signout_reason) || Signout_reason.equals("null"))
        {
            Signout_reason = "无";
        }

        String date = t.getClc_units();
        if (TextUtils.isEmpty(date) || date.equals("null"))
        {
            date = "无";
        }

        String name = t.getClc_person();
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


        String inthereason = t.getInthereason();
        if (inthereason.equals("1"))
        {
            inthereason = "强制隔离戒毒";
        } else if (inthereason.equals("2"))
        {
            inthereason = "自愿戒毒";
        } else if (inthereason.equals("3"))
        {
            inthereason = "进入康复场所";
        } else if (inthereason.equals("4"))
        {
            inthereason = "行政拘留";
        } else if (inthereason.equals("5"))
        {
            inthereason = "拘留";
        } else if (inthereason.equals("6"))
        {
            inthereason = "逮捕";
        }else {
            inthereason = "无";
        }

        String clcreason = t.getClcreason();
        if (clcreason.equals("1"))
        {
            clcreason = "解除强制隔离戒毒责令社区康复";
        } else if (clcreason.equals("2"))
        {
            clcreason = "强制隔离戒毒变更为社区戒毒";
        } else if (clcreason.equals("3"))
        {
            clcreason = "结束自愿戒毒";
        } else if (clcreason.equals("4"))
        {
            clcreason = "离开康复场所";
        } else if (clcreason.equals("5"))
        {
            clcreason = "行政拘留后责令社区戒毒";
        } else if (clcreason.equals("6"))
        {
            clcreason = "行政拘留释放";
        } else if (clcreason.equals("7"))
        {
            clcreason = "拘留释放";
        } else if (clcreason.equals("8"))
        {
            clcreason = "逮捕释放";
        } else if (clcreason.equals("9"))
        {
            clcreason = "涉嫌犯罪转拘留";
        } else if (clcreason.equals("10"))
        {
            clcreason = "涉嫌犯罪转逮捕";
        } else if (clcreason.equals("11"))
        {
            clcreason = "转监管场所执行刑罚";
        } else if (clcreason.equals("12"))
        {
            clcreason = "因伤病出所治疗";
        } else if (clcreason.equals("13"))
        {
            clcreason = "分流转所";
        } else if (clcreason.equals("14"))
        {
            clcreason = "其他";
        }else {
            clcreason = "无";
        }

        String clc_yesno = t.getClc_yesno();
        if (clc_yesno.equals("0"))
        {
            clc_yesno = "否";
        } else if (clc_yesno.equals("1"))
        {
            clc_yesno = "是";
        }else {
            clc_yesno = "无";
        }

        String inthecrimire = t.getInthecrimire();
        if (TextUtils.isEmpty(inthecrimire) || inthecrimire.equals("null"))
        {
            inthecrimire = "无";
        }

        String intheunite = t.getIntheunite();
        if (TextUtils.isEmpty(intheunite) || intheunite.equals("null"))
        {
            intheunite = "无";
        }

        String crcname = t.getCrcname();
        String inther_date = t.getInther_date();
        isNull(crcname);
        isNull(inther_date);

        holder.crcname.setText(crcname);
        holder.inther_date.setText(inther_date);
        holder.inthereason.setText(inthereason);
        holder.inthecrimire.setText(inthecrimire);
        holder.intheunite.setText(intheunite);
        holder.clc_date_end.setText(Signout_reason);
        holder.clcreason.setText(clcreason);
        holder.clc_yesno.setText(clc_yesno);
        holder.clc_units.setText(date);
        holder.clc_person.setText(name);
        holder.leader.setText(leader);
        holder.leaderphone.setText(phone);
        holder.name.setText(t.getName());
        holder.id_card.setText(t.getId_card());
        return v;
    }

    private final class ViewHolder
    {
        TextView crcname;
        TextView inther_date;
        TextView inthereason;
        TextView inthecrimire;
        TextView intheunite;
        TextView clc_date_end;
        TextView clcreason;
        TextView clc_yesno;
        TextView clc_units;
        TextView clc_person;
        TextView leader;
        TextView leaderphone;
        TextView id_card;
        TextView name;
    }

    private void isNull(String aStr){
        if (TextUtils.isEmpty(aStr) || aStr.equals("null"))
        {
            aStr = "无";
        }
    }
}
