package cn.com.jinke.wh_drugcontrol.persion.adapter;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectApplication;
import cn.com.jinke.wh_drugcontrol.booter.ProjectExpandAdapter;
import cn.com.jinke.wh_drugcontrol.persion.constants.PersionUtil;
import cn.com.jinke.wh_drugcontrol.persion.model.Abs_IQ_Result;
import cn.com.jinke.wh_drugcontrol.persion.model.Result_AcceptDrugsEntity;
import cn.com.jinke.wh_drugcontrol.persion.model.Result_CommunityDrugDetoxificationOrRecovery;
import cn.com.jinke.wh_drugcontrol.persion.model.Result_DetoxificationSuperviseEntity;
import cn.com.jinke.wh_drugcontrol.persion.model.Result_EmploymentEntity;
import cn.com.jinke.wh_drugcontrol.persion.model.Result_PublicSecurityEntity;
import cn.com.jinke.wh_drugcontrol.persion.model.Result_SocialRehabilitationServiceForDrugAddicts;
import cn.com.jinke.wh_drugcontrol.persion.model.Result_SpecialWardEntity;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.utils.TimeUtil;

/**
 * Created by xkr on 2017/11/1.
 */

public class Abs_IQ_Adapter extends ProjectExpandAdapter<Abs_IQ_Result, Abs_IQ_Result> implements CodeConstants{
    @Override
    public View getParentView(int groupPosition, boolean isExpanded, View convertView, Abs_IQ_Result result) {
        ParentViewHolder holder = null;
        if (convertView == null || convertView.getTag() == null) {
            holder = new ParentViewHolder();
            convertView = inflater.inflate(R.layout.item_parent, null);
            holder.title = (TextView) convertView.findViewById(R.id.parent_title);
            convertView.setTag(holder);
        }
        holder = (ParentViewHolder) convertView.getTag();
        holder.title.setText(result.getItem_title_name());

        return convertView;
    }

    @Override
    public View getChildViewEx(int groupPosition, int childPosition, boolean isLastChild, View convertView, Abs_IQ_Result result) {
        ChildViewHolder holder = null;
        if (convertView == null || convertView.getTag() == null) {
            holder = new ChildViewHolder();
            convertView = inflater.inflate(R.layout.item_child, null);

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
            holder.ckxq = (TextView) convertView.findViewById(R.id.ckxx);
            holder.gkkzrr_layout = (RelativeLayout) convertView.findViewById(R.id.gkkzrr_layout);
        }
        if(result instanceof Result_CommunityDrugDetoxificationOrRecovery){
            Result_CommunityDrugDetoxificationOrRecovery cddr = (Result_CommunityDrugDetoxificationOrRecovery) result;
            showCddrInfo(holder,cddr);
        }else if(result instanceof Result_SocialRehabilitationServiceForDrugAddicts){
            Result_SocialRehabilitationServiceForDrugAddicts ssfd = (Result_SocialRehabilitationServiceForDrugAddicts) result;
            showSsdfInfo(holder,ssfd);
        }else if(result instanceof Result_EmploymentEntity){
            Result_EmploymentEntity eif = (Result_EmploymentEntity) result;
            showEifInfo(holder,eif);
        }else if(result instanceof Result_AcceptDrugsEntity){
            Result_AcceptDrugsEntity ade = (Result_AcceptDrugsEntity) result;
            showAdeInfo(holder,ade);
        }else if(result instanceof Result_SpecialWardEntity){
            Result_SpecialWardEntity swe = (Result_SpecialWardEntity) result;
            showSweInfo(holder,swe);
        }else if(result instanceof Result_DetoxificationSuperviseEntity){
            Result_DetoxificationSuperviseEntity dse = (Result_DetoxificationSuperviseEntity) result;
            showDseInfo(holder,dse);
        }else if(result instanceof Result_PublicSecurityEntity){
            Result_PublicSecurityEntity pse = (Result_PublicSecurityEntity) result;
            showPseInfo(holder,pse);
        }
        return convertView;
    }

    /**
     * 显示 公安办案部门查获信息
     * @param holder
     * @param pse
     */
    private void showPseInfo(ChildViewHolder holder,final Result_PublicSecurityEntity pse) {
        String han_res_type = PersionUtil.getPublicSecurityDealResult(pse.getPunishResult());


        String charge_type = pse.getCrimeName();
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

        holder.zxdw.setText(PersionUtil.chekParamsAndReturnStr(pse.getHandlingDepartment()));
        holder.zxdq.setText(TimeUtil.removeHour_Min_Seconds(PersionUtil.chekParamsAndReturnStr(pse.getCaughtDate())));
        holder.dqzt.setText(PersionUtil.chekParamsAndReturnStr(pse.getCaughtLocation()));
        holder.zxksrq.setText(TimeUtil.removeHour_Min_Seconds(PersionUtil.chekParamsAndReturnStr(pse.getPunishDate())));
        holder.zxbdrq.setText(han_res_type);
        holder.zxjsrq.setText(charge_type);
        holder.ckxq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RouteUtils.R_PERSION_SEIZED_INFORMATION)
                        .withString(IDCARD,pse.getIdentityCard())
                        .navigation();
            }
        });
    }

    /**
     * 显示 戒毒场所 入所/出所 信息
     * @param holder
     * @param dse
     */
    private void showDseInfo(ChildViewHolder holder,final Result_DetoxificationSuperviseEntity dse) {
        String inthereason = dse.getInReason();
        if ("1".equals(inthereason))
        {
            inthereason = "强制隔离戒毒";
        } else if ("2".equals(inthereason))
        {
            inthereason = "自愿戒毒";
        } else if ("3".equals(inthereason))
        {
            inthereason = "进入康复场所";
        } else if ("4".equals(inthereason))
        {
            inthereason = "行政拘留";
        } else if ("5".equals(inthereason))
        {
            inthereason = "拘留";
        } else if ("6".equals(inthereason))
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

        holder.zxdw.setText(TimeUtil.removeHour_Min_Seconds(PersionUtil.chekParamsAndReturnStr(dse.getInDdate())));
        holder.zxdq.setText(PersionUtil.chekParamsAndReturnStr(dse.getOrgName()));
        holder.dqzt.setText(inthereason);
        holder.zxksrq.setText(PersionUtil.chekParamsAndReturnStr(dse.getHandlingDepartment()));
        holder.zxbdrq.setText(PersionUtil.chekParamsAndReturnStr(dse.getRehabPerson()));
        holder.zxjsrq.setText(PersionUtil.chekParamsAndReturnStr(dse.getRehabPhone()));
        holder.ckxq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RouteUtils.R_PERSION_SUPERVISE)
                        .withString(IDCARD,dse.getIdentityCard())
                        .navigation();
            }
        });
    }

    /**
     * 显示 特殊病区 入院/出院信息
     * @param holder
     * @param swe
     */
    private void showSweInfo(ChildViewHolder holder,final Result_SpecialWardEntity swe) {
        holder.gkkzrr_layout.setVisibility(View.GONE);
        holder.zxdw_tv.setText("病区名称:");
        holder.zxdq_tv.setText("入院日期 :");
        holder.dqzt_tv.setText("入院原因:");
        holder.zxksrq_tv.setText("入院途径:");
        holder.zxbdrq_tv.setText("责任人:");
        holder.zxjsrq_tv.setText("联系电话:");

        holder.zxdw.setText(PersionUtil.chekParamsAndReturnStr(swe.getHospitalName()));
        holder.zxdq.setText(TimeUtil.removeHour_Min_Seconds(PersionUtil.chekParamsAndReturnStr(swe.getInHospitalDate())));
        holder.dqzt.setText(PersionUtil.chekParamsAndReturnStr(swe.getInHospitalReason()));
        holder.zxksrq.setText(PersionUtil.chekParamsAndReturnStr(swe.getInHospitalWay()));
        holder.zxbdrq.setText(PersionUtil.chekParamsAndReturnStr(swe.getHospitaPerson()));
        holder.zxjsrq.setText(PersionUtil.chekParamsAndReturnStr(swe.getHospitaPhone()));
        holder.ckxq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RouteUtils.R_PERSION_HOSPITAL_INFORMATION)
                        .withString(IDCARD,swe.getIdentityCard())
                        .navigation();
            }
        });
    }

    /**
     * 显示接收药物维持治疗
     * @param holder
     * @param ade
     */
    private void showAdeInfo(ChildViewHolder holder,final Result_AcceptDrugsEntity ade) {
        holder.gkkzrr_layout.setVisibility(View.GONE);
        holder.zxdw_tv.setText("治疗门诊名称:");
        holder.zxdq_tv.setText("入组日期:");
        holder.dqzt_tv.setText("首次服药日期:");
        holder.zxksrq_tv.setText("最近一次服药日期:");
        holder.zxbdrq_tv.setText("责任人:");
        holder.zxjsrq_tv.setText("联系电话:");

        holder.zxdw.setText(PersionUtil.chekParamsAndReturnStr(ade.getHospitalName()));
        holder.zxdq.setText(TimeUtil.removeHour_Min_Seconds(PersionUtil.chekParamsAndReturnStr(ade.getGroupInDate())));
        holder.dqzt.setText(TimeUtil.removeHour_Min_Seconds(PersionUtil.chekParamsAndReturnStr(ade.getFirstDrugsDate())));
        holder.zxksrq.setText(TimeUtil.removeHour_Min_Seconds(PersionUtil.chekParamsAndReturnStr(ade.getLastDrugsDate())));
        holder.zxbdrq.setText(PersionUtil.chekParamsAndReturnStr(ade.getHospitalPerson()));
        holder.zxjsrq.setText(PersionUtil.chekParamsAndReturnStr(ade.getHospitalPhone()));
        holder.ckxq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RouteUtils.R_PERSION_ACCEPT_DRUGS)
                        .withString(IDCARD,ade.getIdentityCard())
                        .navigation();
            }
        });
    }

    /**
     * 显示就业信息
     * @param holder
     * @param eif
     */
    private void showEifInfo(ChildViewHolder holder, final Result_EmploymentEntity eif) {
        holder.gkkzrr_layout.setVisibility(View.GONE);
        holder.zxdw_tv.setText("就业推荐记录:");
        holder.zxdq_tv.setText("就业安置记录:");
        holder.dqzt_tv.setText("当前就业单位:");
        holder.zxksrq_tv.setText("当前月薪情况:");
        holder.zxbdrq_tv.setText("单位联系人:");
        holder.zxjsrq_tv.setText("联系电话:");

        holder.zxdw.setText(PersionUtil.getTimesCount(eif.getRecommentCount()));
        holder.zxdq.setText(PersionUtil.getTimesCount(eif.getSettlementCount()));
        holder.dqzt.setText(PersionUtil.chekParamsAndReturnStr(eif.getSettlementCompany()));
        holder.zxksrq.setText(PersionUtil.getSalaryUnit(eif.getSalary()));
        holder.zxbdrq.setText(PersionUtil.chekParamsAndReturnStr(eif.getCompanyContact()));
        holder.zxjsrq.setText(PersionUtil.chekParamsAndReturnStr(eif.getCompanyPhone()));

        holder.ckxq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RouteUtils.R_PERSION_SUNSHINE)
                        .withString(IDCARD,eif.getIdentityCard())
                        .navigation();
            }
        });
    }

    /**
     * 显示 社会化戒毒康复服务信息
     * @param holder
     * @param ssfd
     */
    private void showSsdfInfo(ChildViewHolder holder,final Result_SocialRehabilitationServiceForDrugAddicts ssfd) {
        holder.gkkzrr_layout.setVisibility(View.GONE);
        holder.zxdw_tv.setText("站点名称:");
        holder.zxdq_tv.setText("入站日期:");
        holder.dqzt_tv.setText("就业意愿情况:");
        holder.zxksrq_tv.setText("帮扶救助情况:");
        holder.zxbdrq_tv.setText("身体检查情况:");
        holder.zxjsrq_tv.setText("心理咨询情况:");

        holder.zxdw.setText(PersionUtil.chekParamsAndReturnStr(ssfd.getStationName()));
        holder.zxdq.setText(TimeUtil.removeHour_Min_Seconds(PersionUtil.chekParamsAndReturnStr(ssfd.getInStationDate())));
        holder.dqzt.setText(getTimesCount(ssfd.getInStationCounts()+""));
        holder.zxksrq.setText(getTimesCount(ssfd.getHelpingCount() +""));
        holder.zxbdrq.setText(getTimesCount(ssfd.getPhysicalCount()+""));
        holder.zxjsrq.setText(getTimesCount(ssfd.getMentalityCount() +""));
        holder.ckxq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(PERSION,ssfd);
                ARouter.getInstance().build(RouteUtils.R_PERSION_ACCEPT_RECOVVERY_INFORMATION)
                        .withBundle(BUNDLE,bundle)
                        .navigation();
            }
        });
    }

    private String getTimesCount(String str){
        String preStr = PersionUtil.chekParamsAndReturnStr(str);
        String judge = ProjectApplication.getContext().getString(R.string.params_check_null);
        if(judge.equals(preStr)){
            return preStr;
        }
        return preStr + "次";
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


    /**
     * 显示 社区戒毒/社区康复信息
     * @param holder
     * @param cddr
     */
    private void showCddrInfo(ChildViewHolder holder, final Result_CommunityDrugDetoxificationOrRecovery cddr){
        holder.zxdw.setText(PersionUtil.chekParamsAndReturnStr(cddr.getExecuteDepartment()));
        holder.zxdq.setText(PersionUtil.chekParamsAndReturnStr(cddr.getExecutePlace()));
        holder.dqzt.setText(PersionUtil.chekParamsAndReturnStr(PersionUtil.getStatusStr(cddr.getDealStatus())));
        holder.zxksrq.setText(TimeUtil.removeHour_Min_Seconds(PersionUtil.chekParamsAndReturnStr(cddr.getDrugsBeginDate())));
        holder.zxbdrq.setText(TimeUtil.removeHour_Min_Seconds(PersionUtil.chekParamsAndReturnStr(cddr.getActegisterDate())));
        holder.zxjsrq.setText(TimeUtil.removeHour_Min_Seconds(PersionUtil.chekParamsAndReturnStr(cddr.getDrugsEndDate())));
        holder.gkzrr.setText(PersionUtil.chekParamsAndReturnStr(cddr.getControlPerson()));
        holder.ckxq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(RouteUtils.R_PERSION_RECOVERY_INFORMATION)
                        .withString(IDCARD,cddr.getIdentityCard())
                        .navigation();
            }
        });
    }

    private final class ParentViewHolder {
        TextView title;
    }

    private final class ChildViewHolder {
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
