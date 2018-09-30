package cn.com.jinke.wh_drugcontrol.persion.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.persion.constants.PersionUtil;
import cn.com.jinke.wh_drugcontrol.persion.model.E_EmploymentRecommendationEntity;
import cn.com.jinke.wh_drugcontrol.utils.TimeUtil;

/**
 * Created by xkr on 2017/11/3.
 */

public class E_EmploymentRecommentAdapter extends ProjectBaseAdapter<E_EmploymentRecommendationEntity> {
    public E_EmploymentRecommentAdapter(Activity context) {
        super(context);
    }

    @Override
    public View getViewEx(int position, View v, E_EmploymentRecommendationEntity t) {
        Holder holder = null;
        if(v == null || v.getTag() == null){
            holder = new Holder();
            v = inflater.inflate(R.layout.item_leave_detail, null);
            holder.kqsj = (TextView)v.findViewById(R.id.kqsj_tv);
            holder.kqqk = (TextView)v.findViewById(R.id.kqqk_tv);
            holder.kqsm = (TextView)v.findViewById(R.id.kqsm_tv);
            holder.wgqk = (TextView)v.findViewById(R.id.wgqk_tv);
            holder.jyrq = (TextView)v.findViewById(R.id.jyrq_tv);
            holder.jyzt = (TextView)v.findViewById(R.id.jyzt_tv);

            holder.kqsjj = (TextView)v.findViewById(R.id.kqsj);
            holder.kqqkk = (TextView)v.findViewById(R.id.kqqk);
            holder.kqsmm = (TextView)v.findViewById(R.id.kqsm);
            holder.wgqkk = (TextView)v.findViewById(R.id.wgqk);
            holder.jyrqq = (TextView)v.findViewById(R.id.jyrq);
            holder.jyztt = (TextView)v.findViewById(R.id.jyzt);

            holder.jyrq_layout = (LinearLayout)v.findViewById(R.id.jyrq_layout);
            holder.jyzt_layout = (LinearLayout)v.findViewById(R.id.jyzt_layout);
            v.setTag(holder);
        }
        holder.kqsjj.setText("推荐单位:");
        holder.kqqkk.setText("推荐岗位:");
        holder.kqsmm.setText("月薪:");
        holder.wgqkk.setText("推荐日期:");

        String comment_content = t.getReSalary() + "元/月";    //考勤说明

        holder.kqsj.setText(PersionUtil.chekParamsAndReturnStr(t.getRecommendUnit()));
        holder.kqqk.setText(PersionUtil.chekParamsAndReturnStr(t.getRecommendJob()));
        holder.kqsm.setText(comment_content);
        holder.wgqk.setText(TimeUtil.removeHour_Min_Seconds(PersionUtil.chekParamsAndReturnStr(t.getRecommendDate())));

        return v;
    }
    private final class Holder{
        TextView kqsj;
        TextView kqqk;
        TextView kqsm;
        TextView wgqk;
        TextView jyrq;
        TextView jyzt;

        TextView kqsjj;
        TextView kqqkk;
        TextView kqsmm;
        TextView wgqkk;
        TextView jyrqq;
        TextView jyztt;
        LinearLayout jyrq_layout;
        LinearLayout jyzt_layout;
    }
}
