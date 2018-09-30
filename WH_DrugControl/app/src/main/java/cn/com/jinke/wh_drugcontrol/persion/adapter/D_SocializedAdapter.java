package cn.com.jinke.wh_drugcontrol.persion.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.persion.constants.PersionUtil;
import cn.com.jinke.wh_drugcontrol.persion.model.Result_SocialRehabilitationServiceForDrugAddicts;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.utils.TimeUtil;

/**
 * Created by xkr on 2017/10/31.
 */

public class D_SocializedAdapter extends ProjectBaseAdapter<Result_SocialRehabilitationServiceForDrugAddicts> implements CodeConstants {
    public D_SocializedAdapter(Activity context) {
        super(context);
    }

    @Override
    public View getViewEx(int position, View v,final Result_SocialRehabilitationServiceForDrugAddicts t) {
        ViewHolder holder = null;
        if (v == null || v.getTag() == null)
        {
            holder = new ViewHolder();
            v = inflater.inflate(R.layout.item_accept_recovery_info, null);
            holder.name = (TextView) v.findViewById(R.id.xm_tv);
            holder.id_card = (TextView) v.findViewById(R.id.sfzh_tv);
            holder.stationname = (TextView) v.findViewById(R.id.zdmc_tv);
            holder.indate = (TextView) v.findViewById(R.id.rzrq_tv);
            holder.levelname = (TextView) v.findViewById(R.id.jyyyqk_tv);
            holder.jztjcs = (TextView) v.findViewById(R.id.bfjzqk_tv);
            holder.jytjcs = (TextView) v.findViewById(R.id.stjcqk_tv);
            holder.jyazcs = (TextView) v.findViewById(R.id.xlzxqk_tv);
            holder.leader = (TextView) v.findViewById(R.id.zdzrr_tv);
            holder.leaderphone = (TextView) v.findViewById(R.id.lxdh_tv);
            v.setTag(holder);
        }

        holder.name.setText(PersionUtil.chekParamsAndReturnStr(t.getRealName()));
        holder.stationname.setText(PersionUtil.chekParamsAndReturnStr(t.getStationName()));
        holder.indate.setText(TimeUtil.removeHour_Min_Seconds(PersionUtil.chekParamsAndReturnStr(t.getInStationDate())));
        holder.leader.setText(PersionUtil.chekParamsAndReturnStr(t.getStationPerson()));
        holder.leaderphone.setText(PersionUtil.chekParamsAndReturnStr(t.getStationPhone()));
        holder.id_card.setText(PersionUtil.chekParamsAndReturnStr(t.getIdentityCard()));

        holder.levelname.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                buildData(RouteUtils.R_PERSION_EMPLOYMENT_INTENTION,t);
            }
        });
        holder.jztjcs.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                buildData(RouteUtils.R_PERSION_AID_AND_RESCUE,t);
            }
        });
        holder.jytjcs.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                buildData(RouteUtils.R_PERSION_PHYSICAL_EXAMINATION,t);
            }
        });
        holder.jyazcs.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ARouter.getInstance().build(RouteUtils.R_PERSION_HEART_COUNSELING)
                        .withString(IDCARD,t.getIdentityCard())
                        .withString(B_NAME,t.getRealName())
                        .navigation();
            }
        });

        return v;
    }

    private void buildData(String path,Result_SocialRehabilitationServiceForDrugAddicts obj){
        ARouter.getInstance().build(path)
                .withString(IDCARD,obj.getIdentityCard())
                .withString(B_NAME,obj.getRealName())
                .navigation();
    }

    private final class ViewHolder{
        TextView stationname;
        TextView indate;
        TextView levelname;
        TextView jztjcs;
        TextView jytjcs;
        TextView jyazcs;
        TextView leader;
        TextView leaderphone;
        TextView name;
        TextView id_card;
    }

}
