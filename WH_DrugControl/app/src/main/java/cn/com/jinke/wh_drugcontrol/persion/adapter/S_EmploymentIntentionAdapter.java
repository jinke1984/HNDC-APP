package cn.com.jinke.wh_drugcontrol.persion.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.persion.constants.PersionUtil;
import cn.com.jinke.wh_drugcontrol.persion.model.S_EmploymentIntentionEntity;
import cn.com.jinke.wh_drugcontrol.utils.TimeUtil;

/**
 * Created by xkr on 2017/11/2.
 */

public class S_EmploymentIntentionAdapter extends ProjectBaseAdapter<S_EmploymentIntentionEntity> {
    public S_EmploymentIntentionAdapter(Activity context) {
        super(context);
    }

    @Override
    public View getViewEx(int position, View v, S_EmploymentIntentionEntity t) {
        ViewHolder holder = null;
        if (v == null || v.getTag() == null) {
            holder = new ViewHolder();
            v = inflater.inflate(R.layout.item_drug_jyyx, null);
            holder.whcd = (TextView) v.findViewById(R.id.whcd_tv);
            holder.jnzc = (TextView) v.findViewById(R.id.jnzc_tv);
            holder.zs = (TextView) v.findViewById(R.id.zs_tv);
            holder.jyyx = (TextView) v.findViewById(R.id.jyyx_tv);
            holder.yxfw = (TextView) v.findViewById(R.id.yxfw_tv);
            holder.sjly = (TextView) v.findViewById(R.id.sjly_tv);
            holder.cjsj = (TextView) v.findViewById(R.id.cjsj_tv);
            v.setTag(holder);
        }else{
            holder = (ViewHolder) v.getTag();
        }
        holder.whcd.setText(PersionUtil.getEducation(t.getEducation()));
        holder.jnzc.setText(PersionUtil.chekParamsAndReturnStr(t.getSpeciality()));
        holder.zs.setText(PersionUtil.chekParamsAndReturnStr(t.getCredentialName()));
        holder.jyyx.setText(PersionUtil.chekParamsAndReturnStr(t.getEmployWill()));
        holder.yxfw.setText(PersionUtil.chekParamsAndReturnStr(t.getHopeSalary()));
        holder.sjly.setText(PersionUtil.chekParamsAndReturnStr(t.getSkillCondition()));
        holder.cjsj.setText(TimeUtil.removeHour_Min_Seconds(PersionUtil.chekParamsAndReturnStr(t.getCreateTime())));
        return v;
    }

    private final class ViewHolder{
        TextView whcd;
        TextView jnzc;
        TextView zs;
        TextView jyyx;
        TextView yxfw;
        TextView sjly;
        TextView cjsj;
    }
}
