package cn.com.jinke.wh_drugcontrol.persion.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.persion.constants.PersionUtil;
import cn.com.jinke.wh_drugcontrol.persion.model.MedicationEntity;
import cn.com.jinke.wh_drugcontrol.utils.TimeUtil;

/**
 * Created by xkr on 2017/10/31.
 */

public class MedicationAdapter extends ProjectBaseAdapter<MedicationEntity> {
    public MedicationAdapter(Activity context) {
        super(context);
    }

    @Override
    public View getViewEx(int position, View v, MedicationEntity t) {
        ViewHolder holder = null;
        if (v == null || v.getTag() == null)
        {
            holder = new ViewHolder();
            v = inflater.inflate(R.layout.item_medication, null);
            holder.brbh = (TextView) v.findViewById(R.id.brbh_tv);
            holder.zsbh = (TextView) v.findViewById(R.id.zsbh_tv);
            holder.zljl = (TextView) v.findViewById(R.id.zljl_tv);
            holder.fyrq = (TextView) v.findViewById(R.id.fyrq_tv);
            holder.brxm = (TextView) v.findViewById(R.id.brxm_tv);
            holder.zsmc = (TextView) v.findViewById(R.id.zsxm_tv);
            v.setTag(holder);
        }else{
            holder = (ViewHolder) v.getTag();
        }
        holder.brbh.setText(PersionUtil.chekParamsAndReturnStr(t.getPersonNo()));
        holder.zsbh.setText(PersionUtil.chekParamsAndReturnStr(t.getHospitalNo()));
        holder.zljl.setText(PersionUtil.chekParamsAndReturnStr(t.getMedicineDose()));
        holder.fyrq.setText(TimeUtil.removeHour_Min_Seconds(PersionUtil.chekParamsAndReturnStr(t.getMedicineDate())));
        holder.brxm.setText(PersionUtil.chekParamsAndReturnStr(t.getRealName()));
        holder.zsmc.setText(PersionUtil.chekParamsAndReturnStr(t.getHospitalName()));
        return v;
    }

    private final class ViewHolder{
        //TextView numb;
        TextView brbh;
        TextView zsbh;
        TextView zljl;
        TextView fyrq;
        TextView brxm;
        TextView zsmc;
    }
}
