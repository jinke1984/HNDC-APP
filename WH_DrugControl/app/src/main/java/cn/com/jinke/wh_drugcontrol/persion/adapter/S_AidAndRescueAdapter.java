package cn.com.jinke.wh_drugcontrol.persion.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.persion.constants.PersionUtil;
import cn.com.jinke.wh_drugcontrol.persion.model.S_AidAndRescueEntity;
import cn.com.jinke.wh_drugcontrol.utils.TimeUtil;

/**
 * Created by xkr on 2017/11/2.
 */

public class S_AidAndRescueAdapter extends ProjectBaseAdapter<S_AidAndRescueEntity> {
    public S_AidAndRescueAdapter(Activity context) {
        super(context);
    }

    @Override
    public View getViewEx(int position, View v, S_AidAndRescueEntity t) {
        ViewHolder holder = null;
        if (v == null || v.getTag() == null) {
            holder = new ViewHolder();
            v = inflater.inflate(R.layout.item_drug_bf, null);
            holder.service_provider = (TextView) v.findViewById(R.id.jzzzhgr_tv);
            holder.is_low_premium = (TextView) v.findViewById(R.id.sfbldb_tv);
            holder.low_premium = (TextView) v.findViewById(R.id.dbj_tv);
            holder.accept_content = (TextView) v.findViewById(R.id.xwhqdbz_tv);
            holder.accept_situation = (TextView) v.findViewById(R.id.jzqk_tv);
            holder.accept_effect = (TextView) v.findViewById(R.id.jzdxg_tv);
            holder.service_date = (TextView) v.findViewById(R.id.bfrq_tv);
            v.setTag(holder);
        }else{
            holder = (ViewHolder) v.getTag();
        }
        String low_premium = PersionUtil.chekParamsAndReturnStr(t.getLowSecurity());
        if("1".equals(low_premium)){
            low_premium = "是";
        }else if("0".equals(low_premium)){
            low_premium = "否";
        }

        holder.service_provider.setText(PersionUtil.chekParamsAndReturnStr(t.getHelper()));
        holder.is_low_premium.setText(low_premium);
        holder.low_premium.setText(PersionUtil.chekParamsAndReturnStr(t.getLowMoney()+""));
        holder.accept_content.setText(PersionUtil.chekParamsAndReturnStr(t.getHopeHelp()));
        holder.accept_situation.setText(PersionUtil.chekParamsAndReturnStr(t.getHelpCondition()));
        holder.accept_effect.setText(PersionUtil.chekParamsAndReturnStr(t.getHelpesult()));
        holder.service_date.setText(TimeUtil.removeHour_Min_Seconds(PersionUtil.chekParamsAndReturnStr(t.getHelpDate())));
        return v;
    }

    private final class ViewHolder{
        TextView service_provider;
        TextView accept_content;
        TextView low_premium;
        TextView is_low_premium;
        TextView accept_situation;
        TextView accept_effect;
        TextView service_date;
    }

}
