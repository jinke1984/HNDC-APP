package cn.com.jinke.wh_drugcontrol.persion.adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectExpandAdapter;
import cn.com.jinke.wh_drugcontrol.me.model.Persion;
import cn.com.jinke.wh_drugcontrol.persion.model.DrugPersonSynthesisEntity;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;

/**
 * Created by admin on 2017/10/31.
 */

public class PersionIntergratedQueryAdapter extends ProjectExpandAdapter<Persion,DrugPersonSynthesisEntity> implements CodeConstants {
    Activity mActivity;
    public PersionIntergratedQueryAdapter(Activity activity){
        mActivity = activity;
    }

    @Override
    public View getParentView(int groupPosition, boolean isExpanded, View convertView, Persion persion) {
        ParentViewHolder holder = null;
        if (convertView == null || convertView.getTag() == null)
        {
            holder = new ParentViewHolder();
            convertView = inflater.inflate(R.layout.item_parent, null);
            holder.title = (TextView) convertView.findViewById(R.id.parent_title);
            convertView.setTag(holder);
        }
        holder = (ParentViewHolder) convertView.getTag();
        holder.title.setText(persion.getRealName());

        return convertView;
    }

    @Override
    public View getChildViewEx(int groupPosition, int childPosition, boolean isLastChild, View convertView,final DrugPersonSynthesisEntity c) {
        ChildViewHolder holder = null;
        Intent intent = mActivity.getIntent();
        Persion mPersion;
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra(BUNDLE);
            mPersion = (Persion) bundle.getSerializable(PERSION);
        }
        if (convertView == null || convertView.getTag() == null)
        {
            holder = new ChildViewHolder();
            convertView = inflater.inflate(R.layout.item_child, null);
            if (c.getType() == 1)
            {
                holder.zxdw = (TextView) convertView.findViewById(R.id.zxdw);
                holder.zxdq = (TextView) convertView.findViewById(R.id.zxdq);
                holder.dqzt = (TextView) convertView.findViewById(R.id.dqzt);
                holder.zxksrq = (TextView) convertView.findViewById(R.id.zxksrq);
                holder.zxbdrq = (TextView) convertView.findViewById(R.id.zxbdrq);
                holder.zxjsrq = (TextView) convertView.findViewById(R.id.zxjsrq);
                holder.gkzrr = (TextView) convertView.findViewById(R.id.gkzrr);
                holder.ckxq = (TextView) convertView.findViewById(R.id.ckxx);
            }else{
                holder.zxdw = (TextView) convertView.findViewById(R.id.zxdw);
                holder.zxdq = (TextView) convertView.findViewById(R.id.zxdq);
                holder.dqzt = (TextView) convertView.findViewById(R.id.dqzt);
                holder.zxksrq = (TextView) convertView.findViewById(R.id.zxksrq);
                holder.zxbdrq = (TextView) convertView.findViewById(R.id.zxbdrq);
                holder.zxjsrq = (TextView) convertView.findViewById(R.id.zxjsrq);
                holder.gkzrr = (TextView) convertView.findViewById(R.id.gkzrr);

                holder.zxdw_tv = (TextView) convertView.findViewById(R.id.zxdw_tv);
                holder.zxdq_tv = (TextView) convertView.findViewById(R.id.zxdq_tv);
                holder.dqzt_tv = (TextView) convertView.findViewById(R.id.dqzt_tv);
                holder.zxksrq_tv = (TextView) convertView.findViewById(R.id.zxksrq_tv);
                holder.zxbdrq_tv = (TextView) convertView.findViewById(R.id.zxbdrq_tv);
                holder.zxjsrq_tv = (TextView) convertView.findViewById(R.id.zxjsrq_tv);
//				holder.gkzrr_tv = (TextView) convertView.findViewById(R.id.gkzrr_tv);
                holder.ckxq = (TextView) convertView.findViewById(R.id.ckxx);
                holder.gkkzrr_layout = (RelativeLayout) convertView.findViewById(R.id.gkkzrr_layout);
            }

        }
        if (c.getType() == 1)
        {
            String currentstate = c.getDqzt();
            String zxdw = c.getZxdw();
            if (TextUtils.isEmpty(zxdw) || zxdw.equals("null"))
            {
                zxdw = "无";
            }

            String zxdq = c.getZxdq();
            if (TextUtils.isEmpty(zxdq) || zxdq.equals("null"))
            {
                zxdq = "无";
            }

            String zxksrq = c.getZxksrq();
            if (TextUtils.isEmpty(zxksrq) || zxksrq.equals("null"))
            {
                zxksrq = "无";
            }else {
                String[] split = zxksrq.split(" ");
                zxksrq = split[0];
            }

            String zxbdrq = c.getZxbdrq();
            if (TextUtils.isEmpty(zxbdrq) || zxbdrq.equals("null"))
            {
                zxbdrq = "无";
            }else {
                String[] split = zxbdrq.split(" ");
                zxbdrq = split[0];
            }

            String zxjsrq = c.getZxjsrq();
            if (TextUtils.isEmpty(zxjsrq) || zxjsrq.equals("null"))
            {
                zxjsrq = "无";
            }else {
                String[] split = zxjsrq.split(" ");
                zxjsrq = split[0];
            }

            String gkzrr = c.getGkzrr();
            if (TextUtils.isEmpty(gkzrr) || gkzrr.equals("null"))
            {
                gkzrr = "无";
            }


            holder.zxdw.setText(zxdw + "");
            holder.zxdq.setText(zxdq + "");
            holder.dqzt.setText(currentstate);
            holder.zxksrq.setText(zxksrq);
            holder.zxbdrq.setText(zxbdrq);
            holder.zxjsrq.setText(zxjsrq);
            holder.gkzrr.setText(gkzrr);
        }else if (c.getType() == 2) {
//			站点名称，入站日期，就业意愿情况，帮扶救助情况，身体检查情况，
//			心理咨询情况
            String dqzt = c.getDqzt() + "次";
            if (TextUtils.isEmpty(c.getDqzt()) || c.getDqzt().equals("null") || c.getDqzt().equals("0"))
            {
                dqzt = "无";
            }

            String zxdw = c.getZxdw();
            if (TextUtils.isEmpty(zxdw) || zxdw.equals("null"))
            {
                zxdw = "无";
            }

            String zxdq = c.getZxdq();
            if (TextUtils.isEmpty(zxdq) || zxdq.equals("null"))
            {
                zxdq = "无";
            }

            String zxksrq = c.getZxksrq() + "次";
            if (TextUtils.isEmpty(c.getZxksrq()) || c.getZxksrq().equals("null") || c.getZxksrq().equals("0"))
            {
                zxksrq = "无";
            }

            String zxbdrq = c.getZxbdrq() + "次";
            if (TextUtils.isEmpty(c.getZxbdrq()) || c.getZxbdrq().equals("null") || c.getZxbdrq().equals("0"))
            {
                zxbdrq = "无";
            }

            String zxjsrq = c.getZxjsrq() + "次";
            if (TextUtils.isEmpty(c.getZxjsrq()) || c.getZxjsrq().equals("null") || c.getZxjsrq().equals("0"))
            {
                zxjsrq = "无";
            }

            String gkzrr = c.getGkzrr();
            if (TextUtils.isEmpty(gkzrr) || gkzrr.equals("null"))
            {
                gkzrr = "无";
            }

            holder.gkkzrr_layout.setVisibility(View.GONE);
            holder.zxdw_tv.setText("站点名称:");
            holder.zxdq_tv.setText("入站日期:");
            holder.dqzt_tv.setText("就业意愿情况:");
            holder.zxksrq_tv.setText("帮扶救助情况:");
            holder.zxbdrq_tv.setText("身体检查情况:");
            holder.zxjsrq_tv.setText("心理咨询情况:");

            holder.zxdw.setText(zxdw);
            holder.zxdq.setText(zxdq);
            holder.dqzt.setText(dqzt);
            holder.zxksrq.setText(zxksrq);
            holder.zxbdrq.setText(zxbdrq);
            holder.zxjsrq.setText(zxjsrq);
//			holder.gkzrr.setText(c.getGkzrr());
        }else if (c.getType() == 3) {
//			就业推荐记录，就业安置记录 当前就业单位，当前月薪情况，单位联系人，
//			联系电话
            String dqzt = c.getDqzt();
            if (TextUtils.isEmpty(dqzt) || dqzt.equals("null"))
            {
                dqzt = "无";
            }

            String zxdw = c.getZxdw();
            if (TextUtils.isEmpty(zxdw) || zxdw.equals("null"))
            {
                zxdw = "无";
            }
            String zxdq = c.getZxdq() + "次";
            if (TextUtils.isEmpty(zxdq) || zxdq.equals("null"))
            {
                zxdq = "无";
            }

            String state = c.getState();

            String zxksrq = c.getZxksrq() + "元/月";
            if (TextUtils.isEmpty(c.getZxksrq()) || c.getZxksrq().equals("null") || state.equals("离职"))
            {
                zxksrq = "无";
            }

            String zxbdrq = c.getZxbdrq();
            if (TextUtils.isEmpty(zxbdrq) || zxbdrq.equals("null"))
            {
                zxbdrq = "无";
            }

            String zxjsrq = c.getZxjsrq();
            if (TextUtils.isEmpty(zxjsrq) || zxjsrq.equals("null"))
            {
                zxjsrq = "无";
            }

            String gkzrr = c.getGkzrr();
            if (TextUtils.isEmpty(gkzrr) || gkzrr.equals("null"))
            {
                gkzrr = "无";
            }

            holder.gkkzrr_layout.setVisibility(View.GONE);
            holder.zxdw_tv.setText("就业推荐记录:");
            holder.zxdq_tv.setText("就业安置记录:");
            holder.dqzt_tv.setText("当前就业单位:");
            holder.zxksrq_tv.setText("当前月薪情况:");
            holder.zxbdrq_tv.setText("单位联系人:");
            holder.zxjsrq_tv.setText("联系电话:");

            holder.zxdw.setText(zxdw + "次");
            holder.zxdq.setText(zxdq);
            holder.dqzt.setText(dqzt);
            holder.zxksrq.setText(zxksrq);
            holder.zxbdrq.setText(zxbdrq);
            holder.zxjsrq.setText(zxjsrq);
        }else if (c.getType() == 4) {

//			治疗门诊名称  入组日期 首次服药日期 最近一次服药日期
//			责任人 联系电话

            String dqzt = c.getDqzt();
            if (TextUtils.isEmpty(dqzt) || dqzt.equals("null"))
            {
                dqzt = "无";
            }

            String zxdw = c.getZxdw();
            if (TextUtils.isEmpty(zxdw) || zxdw.equals("null"))
            {
                zxdw = "无";
            }

            String zxdq = c.getZxdq();
            if (TextUtils.isEmpty(zxdq) || zxdq.equals("null"))
            {
                zxdq = "无";
            }

            String zxksrq = c.getZxksrq();
            if (TextUtils.isEmpty(zxksrq) || zxksrq.equals("null"))
            {
                zxksrq = "无";
            }

            String zxbdrq = c.getZxbdrq();
            if (TextUtils.isEmpty(zxbdrq) || zxbdrq.equals("null"))
            {
                zxbdrq = "无";
            }

            String zxjsrq = c.getZxjsrq();
            if (TextUtils.isEmpty(zxjsrq) || zxjsrq.equals("null"))
            {
                zxjsrq = "无";
            }

            String gkzrr = c.getGkzrr();
            if (TextUtils.isEmpty(gkzrr) || gkzrr.equals("null"))
            {
                gkzrr = "无";
            }

            holder.gkkzrr_layout.setVisibility(View.GONE);
            holder.zxdw_tv.setText("治疗门诊名称:");
            holder.zxdq_tv.setText("入组日期:");
            holder.dqzt_tv.setText("首次服药日期:");
            holder.zxksrq_tv.setText("最近一次服药日期:");
            holder.zxbdrq_tv.setText("责任人:");
            holder.zxjsrq_tv.setText("联系电话:");

            holder.zxdw.setText(zxdw);
            holder.zxdq.setText(zxdq);
            holder.dqzt.setText(dqzt);
            holder.zxksrq.setText(zxksrq);
            holder.zxbdrq.setText(zxbdrq);
            holder.zxjsrq.setText(zxjsrq);
        }else if (c.getType() == 5) {

//			病区名称  入院日期 入院原因，入院途径 责任人 联系电话

            String dqzt = c.getDqzt();
            if (TextUtils.isEmpty(dqzt) || dqzt.equals("null"))
            {
                dqzt = "无";
            }

            String zxdw = c.getZxdw();
            if (TextUtils.isEmpty(zxdw) || zxdw.equals("null"))
            {
                zxdw = "无";
            }

            String zxdq = c.getZxdq();
            if (TextUtils.isEmpty(zxdq) || zxdq.equals("null"))
            {
                zxdq = "无";
            }

            String zxksrq = c.getZxksrq();
            if (TextUtils.isEmpty(zxksrq) || zxksrq.equals("null"))
            {
                zxksrq = "无";
            }

            String zxbdrq = c.getZxbdrq();
            if (TextUtils.isEmpty(zxbdrq) || zxbdrq.equals("null"))
            {
                zxbdrq = "无";
            }

            String zxjsrq = c.getZxjsrq();
            if (TextUtils.isEmpty(zxjsrq) || zxjsrq.equals("null"))
            {
                zxjsrq = "无";
            }

            String gkzrr = c.getGkzrr();
            if (TextUtils.isEmpty(gkzrr) || gkzrr.equals("null"))
            {
                gkzrr = "无";
            }

            holder.gkkzrr_layout.setVisibility(View.GONE);
            holder.zxdw_tv.setText("病区名称:");
            holder.zxdq_tv.setText("入院日期 :");
            holder.dqzt_tv.setText("入院原因:");
            holder.zxksrq_tv.setText("入院途径:");
            holder.zxbdrq_tv.setText("责任人:");
            holder.zxjsrq_tv.setText("联系电话:");

            holder.zxdw.setText(zxdw);
            holder.zxdq.setText(zxdq);
            holder.dqzt.setText(dqzt);
            holder.zxksrq.setText(zxksrq);
            holder.zxbdrq.setText(zxbdrq);
            holder.zxjsrq.setText(zxjsrq);
        }else if (c.getType() == 6) {

//			入所日期  场所名称  入所原因 决定机关 责任人 联系电话

            String dqzt = c.getDqzt();
            if (TextUtils.isEmpty(dqzt) || dqzt.equals("null"))
            {
                dqzt = "无";
            }

            String zxdw = c.getZxdw();
            if (TextUtils.isEmpty(zxdw) || zxdw.equals("null"))
            {
                zxdw = "无";
            }

            String zxdq = c.getZxdq();
            if (TextUtils.isEmpty(zxdq) || zxdq.equals("null"))
            {
                zxdq = "无";
            }

            String zxksrq = c.getZxksrq();
            if (TextUtils.isEmpty(zxksrq) || zxksrq.equals("null"))
            {
                zxksrq = "无";
            }

            String zxbdrq = c.getZxbdrq();
            if (TextUtils.isEmpty(zxbdrq) || zxbdrq.equals("null"))
            {
                zxbdrq = "无";
            }

            String zxjsrq = c.getZxjsrq();
            if (TextUtils.isEmpty(zxjsrq) || zxjsrq.equals("null"))
            {
                zxjsrq = "无";
            }

            String gkzrr = c.getGkzrr();
            if (TextUtils.isEmpty(gkzrr) || gkzrr.equals("null"))
            {
                gkzrr = "无";
            }

            String inthereason = c.getDqzt();
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

            holder.gkkzrr_layout.setVisibility(View.GONE);
            holder.zxdw_tv.setText("入所日期:");
            holder.zxdq_tv.setText("场所名称 :");
            holder.dqzt_tv.setText("入所原因:");
            holder.zxksrq_tv.setText("决定机关:");
            holder.zxbdrq_tv.setText("责任人:");
            holder.zxjsrq_tv.setText("联系电话:");

            holder.zxdw.setText(zxdw);
            holder.zxdq.setText(zxdq);
            holder.dqzt.setText(inthereason);
            holder.zxksrq.setText(zxksrq);
            holder.zxbdrq.setText(zxbdrq);
            holder.zxjsrq.setText(zxjsrq);
        }else if (c.getType() == 7) {

//			查获单位  查获日期 查获地区 处置日期 处置结果
//			案由/涉嫌罪名
            String dqzt = c.getDqzt();
            if (TextUtils.isEmpty(dqzt) || dqzt.equals("null"))
            {
                dqzt = "无";
            }

            String zxdw = c.getZxdw();
            if (TextUtils.isEmpty(zxdw) || zxdw.equals("null"))
            {
                zxdw = "无";
            }

            String zxdq = c.getZxdq();
            if (TextUtils.isEmpty(zxdq) || zxdq.equals("null"))
            {
                zxdq = "无";
            }

            String zxksrq = c.getZxksrq();
            if (TextUtils.isEmpty(zxksrq) || zxksrq.equals("null"))
            {
                zxksrq = "无";
            }

            String zxbdrq = c.getZxbdrq();
            if (TextUtils.isEmpty(zxbdrq) || zxbdrq.equals("null"))
            {
                zxbdrq = "无";
            }

            String zxjsrq = c.getZxjsrq();
            if (TextUtils.isEmpty(zxjsrq) || zxjsrq.equals("null"))
            {
                zxjsrq = "无";
            }

            String gkzrr = c.getGkzrr();
            if (TextUtils.isEmpty(gkzrr) || gkzrr.equals("null"))
            {
                gkzrr = "无";
            }

            String han_res_type = c.getZxbdrq();
            if (han_res_type.equals("1"))
            {
                han_res_type = "不予处罚";
            } else if (han_res_type.equals("2"))
            {
                han_res_type = "警告";
            } else if (han_res_type.equals("3"))
            {
                han_res_type = "罚款";
            } else if (han_res_type.equals("4"))
            {
                han_res_type = "警告+罚款";
            } else if (han_res_type.equals("5"))
            {
                han_res_type = "行政拘留";
            } else if (han_res_type.equals("6"))
            {
                han_res_type = "行政拘留+罚款";
            } else if (han_res_type.equals("7"))
            {
                han_res_type = "行政拘留+社区戒毒";
            } else if (han_res_type.equals("8"))
            {
                han_res_type = "行政拘留+强制隔离戒毒";
            } else if (han_res_type.equals("9"))
            {
                han_res_type = "其他强制性教育措施";
            } else if (han_res_type.equals("10"))
            {
                han_res_type = "拘留";
            } else if (han_res_type.equals("11"))
            {
                han_res_type = "逮捕";
            } else if (han_res_type.equals("100"))
            {
                han_res_type = "其他刑事强制措施";
            }else {
                han_res_type = "无";
            }

            String charge_type = c.getZxjsrq();
            if (charge_type.equals("1"))
            {
                charge_type = "吸食毒品";
            }else if (charge_type.equals("2"))
            {
                charge_type = "其他违法犯罪";
            }else if (charge_type.equals("3"))
            {
                charge_type = "涉毒犯罪";
            }else if (charge_type.equals("4"))
            {
                charge_type = "其他涉毒违法";
            }else {
                charge_type = "无";
            }

            holder.gkkzrr_layout.setVisibility(View.GONE);
            holder.zxdw_tv.setText("查获单位:");
            holder.zxdq_tv.setText("查获日期:");
            holder.dqzt_tv.setText("查获地区:");
            holder.zxksrq_tv.setText("处置日期:");
            holder.zxbdrq_tv.setText("处置结果:");
            holder.zxjsrq_tv.setText("案由/涉嫌罪名:");

            holder.zxdw.setText(zxdw);
            holder.zxdq.setText(zxdq);
            holder.dqzt.setText(dqzt);
            holder.zxksrq.setText(zxksrq);
            holder.zxbdrq.setText(han_res_type);
            holder.zxjsrq.setText(charge_type);
        }

        holder.ckxq.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                int aType = c.getType();
                switch (aType)
                {
                    case 1:
                        break;

                    case 2:
                        break;

                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    default:
                        break;
                }
            }
        });

        return convertView;
    }

    @Override
    public void selectGroup(int groupP) {

    }

    @Override
    public void selectChild(int groupP, int childP) {

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    private final class ParentViewHolder{
        TextView title;
    }

    private final class ChildViewHolder{
        TextView zxdw;
        TextView zxdq;
        TextView dqzt;
        TextView zxksrq;
        TextView zxbdrq;
        TextView zxjsrq;
        TextView gkzrr;

        TextView zxdw_tv;
        TextView zxdq_tv;
        TextView dqzt_tv;
        TextView zxksrq_tv;
        TextView zxbdrq_tv;
        TextView zxjsrq_tv;
        TextView gkzrr_tv;
        RelativeLayout gkkzrr_layout;

        TextView ckxq;
    }
}
