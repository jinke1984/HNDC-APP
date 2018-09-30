package cn.com.jinke.wh_drugcontrol.persion.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.persion.constants.PersionUtil;
import cn.com.jinke.wh_drugcontrol.persion.model.S_HeartCounselingEntity;
import cn.com.jinke.wh_drugcontrol.utils.TimeUtil;

/**
 * Created by xkr on 2017/11/2.
 */

public class S_HeartCounselingAdapter extends ProjectBaseAdapter<S_HeartCounselingEntity> {
    public S_HeartCounselingAdapter(Activity context) {
        super(context);
    }

    @Override
    public View getViewEx(int position, View v, S_HeartCounselingEntity t) {
        ViewHolder holder = null;
        if (v == null || v.getTag() == null) {
            holder = new ViewHolder();
            v = inflater.inflate(R.layout.item_drug_xlzx, null);
            holder.counselo_date = (TextView) v.findViewById(R.id.zxrq_tv);
            holder.counselor = (TextView) v.findViewById(R.id.zxs_tv);
            holder.counselor_orgn = (TextView) v.findViewById(R.id.zxsszjg_tv);
            holder.pgdj = (TextView) v.findViewById(R.id.xlpgdjj_tv);
            v.setTag(holder);
        }else{
            holder = (ViewHolder) v.getTag();
        }
        String scoreLevel = PersionUtil.chekParamsAndReturnStr(t.getScoreZiType());
        if("1".equals(scoreLevel)){
            scoreLevel = "Ⅰ类";
        }else if("2".equals(scoreLevel)){
            scoreLevel = "Ⅱ类";
        }else if("3".equals(scoreLevel)){
            scoreLevel = "Ⅲ类";
        }

        holder.counselor.setText(PersionUtil.chekParamsAndReturnStr(t.getConsulter()));
        holder.counselo_date.setText(TimeUtil.removeHour_Min_Seconds(PersionUtil.chekParamsAndReturnStr(t.getConsultDate())));
        holder.counselor_orgn.setText(PersionUtil.chekParamsAndReturnStr(t.getConsulterOrg()));
        holder.pgdj.setText(scoreLevel);
        return v;
    }

    private final class ViewHolder{
        TextView counselo_date;
        TextView counselor;
        TextView counselor_orgn;
        TextView pgdj;
    }

}
