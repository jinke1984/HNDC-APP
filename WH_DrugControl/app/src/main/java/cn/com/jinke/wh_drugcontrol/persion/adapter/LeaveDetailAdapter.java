package cn.com.jinke.wh_drugcontrol.persion.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.persion.model.LeaveDetailEntity;

/**
 * Created by xkr on 2017/10/31.
 */

public class LeaveDetailAdapter extends ProjectBaseAdapter<LeaveDetailEntity> {
    public LeaveDetailAdapter(Activity context) {
        super(context);
    }
    String mIntentType;
    String mType;

    @Override
    public View getViewEx(int position, View v, LeaveDetailEntity t) {
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
        holder = (Holder) v.getTag();

        if (mType.equals("1")&&mIntentType.equals("1")) {
            String work_time = t.getWork_time();                //考勤时间
            String work_status = t.getWork_status();            //考勤情况
            String comment_content = t.getComment_content();    //考勤说明
            String out_content = t.getOut_content();            //违规情况

            if (TextUtils.isEmpty(work_time) || work_time.equals("null"))
            {
                work_time = "无";
            }

            if (TextUtils.isEmpty(work_status) || work_status.equals("null"))
            {
                work_status = "无";
            }

            if (TextUtils.isEmpty(comment_content) || comment_content.equals("null"))
            {
                comment_content = "无";
            }

            if (TextUtils.isEmpty(out_content) || out_content.equals("null"))
            {
                out_content = "无";
            }

            holder.kqsj.setText(work_time);
            holder.kqqk.setText(work_status);
            holder.kqsm.setText(comment_content);
            holder.wgqk.setText(out_content);
        }else if (mType.equals("2")&&mIntentType.equals("2")) {

            holder.kqsjj.setText("推荐单位:");
            holder.kqqkk.setText("推荐岗位:");
            holder.kqsmm.setText("月薪:");
            holder.wgqkk.setText("推荐日期:");

            String work_time = t.getWork_time();                //考勤时间
            String work_status = t.getWork_status();
            String jyztt = t.getJyztt();
            String comment_content = t.getOut_content() + "元/月";    //考勤说明
            String out_content = t.getJyrqq();            //违规情况

            if (TextUtils.isEmpty(work_time) || work_time.equals("null"))
            {
                work_time = "无";
            }

            if (TextUtils.isEmpty(work_status) || work_status.equals("null"))
            {
                work_status = "无";
            }

            if (TextUtils.isEmpty(t.getOut_content()) || t.getOut_content().equals("null"))
            {
                comment_content = "无";
            }

            if (TextUtils.isEmpty(out_content) || out_content.equals("null"))
            {
                out_content = "无";
            }
            if (TextUtils.isEmpty(t.getOut_content()) || t.getOut_content().equals("null"))
            {
                comment_content = "无";
            }

            holder.kqsj.setText(work_time);
            holder.kqqk.setText(work_status);
            holder.kqsm.setText(comment_content);
            holder.wgqk.setText(out_content);
        }else if (mType.equals("3")&&mIntentType.equals("3")) {
            holder.kqsjj.setText("安置单位:");
            holder.kqqkk.setText("安置岗位:");
            holder.kqsmm.setText("安置类型:");
            holder.wgqkk.setText("月薪:");
            holder.jyrq_layout.setVisibility(View.VISIBLE);
            holder.jyzt_layout.setVisibility(View.VISIBLE);

            String work_time = t.getWork_time();
            String work_status = t.getWork_status();
            String comment_content = t.getComment_content();
            String out_content = t.getOut_content() + "元/月";
            String jyrqq = t.getJyrqq();
            String jyztt = t.getJyztt();
            if (TextUtils.isEmpty(jyztt) || jyztt.equals("null"))
            {
                jyztt = "无";
            }
            if (TextUtils.isEmpty(jyrqq) || jyrqq.equals("null"))
            {
                jyrqq = "无";
            }

            if (TextUtils.isEmpty(work_time) || work_time.equals("null"))
            {
                work_time = "无";
            }

            if (TextUtils.isEmpty(work_status) || work_status.equals("null"))
            {
                work_status = "无";
            }

            if (TextUtils.isEmpty(comment_content) || comment_content.equals("null"))
            {
                comment_content = "无";
            }

            if (TextUtils.isEmpty(out_content) || out_content.equals("null"))
            {
                out_content = "无";
            }

            holder.kqsj.setText(work_time);
            holder.kqqk.setText(work_status);
            holder.kqsm.setText(comment_content);
            holder.wgqk.setText(out_content);
            holder.jyrq.setText(jyrqq);
            holder.jyzt.setText(jyztt);
        }

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
