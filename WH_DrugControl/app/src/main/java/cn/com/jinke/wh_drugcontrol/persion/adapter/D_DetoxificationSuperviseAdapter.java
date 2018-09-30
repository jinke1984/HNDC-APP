package cn.com.jinke.wh_drugcontrol.persion.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.persion.constants.PersionUtil;
import cn.com.jinke.wh_drugcontrol.persion.model.Result_DetoxificationSuperviseEntity;
import cn.com.jinke.wh_drugcontrol.utils.TimeUtil;

/**
 * Created by xkr on 2017/10/31.
 */

public class D_DetoxificationSuperviseAdapter extends ProjectBaseAdapter<Result_DetoxificationSuperviseEntity> {
    public D_DetoxificationSuperviseAdapter(Activity context) {
        super(context);
    }

    @Override
    public View getViewEx(int position, View v, Result_DetoxificationSuperviseEntity t) {
        ViewHolder holder = null;
        if (v == null || v.getTag() == null) {
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

        String Signout_reason = TimeUtil.removeHour_Min_Seconds(PersionUtil.chekParamsAndReturnStr(t.getActualOutDate()));

        String date = PersionUtil.chekParamsAndReturnStr(t.getReceiveDept());

        String name = PersionUtil.chekParamsAndReturnStr(t.getReceivePerson());

        String phone = PersionUtil.chekParamsAndReturnStr(t.getRehabPhone());

        String leader = PersionUtil.chekParamsAndReturnStr(t.getRehabPerson());

        String inthereason = t.getInReason();
        if ("1".equals(inthereason)) {
            inthereason = "强制隔离戒毒";
        } else if ("2".equals(inthereason)) {
            inthereason = "自愿戒毒";
        } else if ("3".equals(inthereason)) {
            inthereason = "进入康复场所";
        } else if ("4".equals(inthereason)) {
            inthereason = "行政拘留";
        } else if (inthereason.equals("5")) {
            inthereason = "拘留";
        } else if ("5".equals(inthereason)) {
            inthereason = "逮捕";
        } else {
            inthereason = "无";
        }

        String clcreason = t.getOutReason();
        if ("1".equals(clcreason)) {
            clcreason = "解除强制隔离戒毒责令社区康复";
        } else if ("2".equals(clcreason)) {
            clcreason = "强制隔离戒毒变更为社区戒毒";
        } else if ("3".equals(clcreason)) {
            clcreason = "结束自愿戒毒";
        } else if ("4".equals(clcreason)) {
            clcreason = "离开康复场所";
        } else if ("5".equals(clcreason)) {
            clcreason = "行政拘留后责令社区戒毒";
        } else if ("6".equals(clcreason)) {
            clcreason = "行政拘留释放";
        } else if ("7".equals(clcreason)) {
            clcreason = "拘留释放";
        } else if ("8".equals(clcreason)) {
            clcreason = "逮捕释放";
        } else if ("9".equals(clcreason)) {
            clcreason = "涉嫌犯罪转拘留";
        } else if ("10".equals(clcreason)) {
            clcreason = "涉嫌犯罪转逮捕";
        } else if ("11".equals(clcreason)) {
            clcreason = "转监管场所执行刑罚";
        } else if ("12".equals(clcreason)) {
            clcreason = "因伤病出所治疗";
        } else if ("13".equals(clcreason)) {
            clcreason = "分流转所";
        } else if ("14".equals(clcreason)) {
            clcreason = "其他";
        } else {
            clcreason = "无";
        }

        String clc_yesno = t.getSeamlessJoint();
        if ("0".equals(clc_yesno)) {
            clc_yesno = "是";
        } else if ("1".equals(clc_yesno)) {
            clc_yesno = "否";
        } else {
            clc_yesno = " ";
        }

        String inthecrimire = PersionUtil.chekParamsAndReturnStr(t.getCrimeName());
        String intheunite = PersionUtil.chekParamsAndReturnStr(t.getHandlingDepartment());


        holder.crcname.setText(PersionUtil.chekParamsAndReturnStr(t.getOrgName()));
        holder.inther_date.setText(TimeUtil.removeHour_Min_Seconds(t.getInDdate()));
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
        holder.name.setText(t.getRealName());
        holder.id_card.setText(t.getIdentityCard());
        return v;
    }

    private final class ViewHolder {
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

}
