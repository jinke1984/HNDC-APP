package cn.com.jinke.wh_drugcontrol.persion.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.persion.constants.PersionUtil;
import cn.com.jinke.wh_drugcontrol.persion.model.S_PhysicalExaminationEntity;
import cn.com.jinke.wh_drugcontrol.utils.TimeUtil;

/**
 * Created by xkr on 2017/11/2.
 */

public class S_PhysicalExaminationAdapter extends ProjectBaseAdapter<S_PhysicalExaminationEntity> {
    public S_PhysicalExaminationAdapter(Activity context) {
        super(context);
    }

    @Override
    public View getViewEx(int position, View v, S_PhysicalExaminationEntity t) {
        ViewHolder holder = null;
        if (v == null || v.getTag() == null) {
            holder = new ViewHolder();
            v = inflater.inflate(R.layout.item_drug_stjc, null);
            holder.rq = (TextView) v.findViewById(R.id.zdsj_tv);
            holder.zq = (TextView) v.findViewById(R.id.zdzq_tv);
            holder.zdjg = (TextView) v.findViewById(R.id.zdjg_tv);
            holder.jgmc = (TextView) v.findViewById(R.id.zdjgou_tv);
            holder.xqsm = (TextView) v.findViewById(R.id.zdxqsm_tv);
            v.setTag(holder);
        }else{
            holder = (ViewHolder) v.getTag();
        }

        holder.rq.setText(TimeUtil.removeHour_Min_Seconds(PersionUtil.chekParamsAndReturnStr(t.getDiagnoseDate())));
        holder.zq.setText(PersionUtil.getDiagnoseWeek(t.getDiagnoseCycle()));
        holder.zdjg.setText(PersionUtil.getDiagnoseResult(t.getDiagnoseResult()));
        holder.jgmc.setText(PersionUtil.chekParamsAndReturnStr(t.getDiagnoseOrg()));
        holder.xqsm.setText(PersionUtil.chekParamsAndReturnStr(t.getDiagnoseDetails()));
        return v;
    }

    private final class ViewHolder{
        TextView rq;
        TextView zq;
        TextView zdjg;
        TextView jgmc;
        TextView xqsm;
    }

}
