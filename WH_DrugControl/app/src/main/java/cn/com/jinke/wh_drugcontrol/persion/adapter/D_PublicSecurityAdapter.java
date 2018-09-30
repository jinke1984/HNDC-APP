package cn.com.jinke.wh_drugcontrol.persion.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.persion.constants.PersionUtil;
import cn.com.jinke.wh_drugcontrol.persion.model.Result_PublicSecurityEntity;
import cn.com.jinke.wh_drugcontrol.utils.TimeUtil;

/**
 * Created by xkr on 2017/10/31.
 */

public class D_PublicSecurityAdapter extends ProjectBaseAdapter<Result_PublicSecurityEntity> {
    public D_PublicSecurityAdapter(Activity context) {
        super(context);
    }

    @Override
    public View getViewEx(int position, View v, Result_PublicSecurityEntity t) {
        ViewHolder holder = null;
        if (v == null || v.getTag() == null)
        {
            holder = new ViewHolder();
            v = inflater.inflate(R.layout.item_seized_info, null);
            holder.name = (TextView) v.findViewById(R.id.xm_tv);
            holder.id_card = (TextView) v.findViewById(R.id.sfzh_tv);
            holder.crcname = (TextView) v.findViewById(R.id.chdw_tv);
            holder.seized_date = (TextView) v.findViewById(R.id.chrq_tv);
            holder.current_drug = (TextView) v.findViewById(R.id.dqsfxd_tv);
            holder.urine_res = (TextView) v.findViewById(R.id.njjg_tv);
            holder.is_addiction = (TextView) v.findViewById(R.id.sfcy_tv);
            holder.addiction_deg = (TextView) v.findViewById(R.id.cycd_tv);
            holder.seized_area_name = (TextView) v.findViewById(R.id.chdqy_tv);
            holder.seized_field = (TextView) v.findViewById(R.id.chdd_tv);
            holder.is_crime = (TextView) v.findViewById(R.id.sfsdwffz_tv);
            holder.charge_type = (TextView) v.findViewById(R.id.aysxzm_tv);
            holder.han_date = (TextView) v.findViewById(R.id.czrq_tv);
            holder.han_res_type = (TextView) v.findViewById(R.id.czjg_tv);
            holder.seized_where_type = (TextView) v.findViewById(R.id.czqx_tv);
            holder.sup_place_type = (TextView) v.findViewById(R.id.qxcs_tv);
            v.setTag(holder);
        }
        holder = (ViewHolder) v.getTag();
        getTypeData(holder, t);
        return v;
    }

    private final class ViewHolder
    {
        TextView crcname;
        TextView seized_date;
        TextView current_drug;
        TextView urine_res;
        TextView is_addiction;
        TextView addiction_deg;
        TextView seized_area_name;
        TextView seized_field;
        TextView is_crime;
        TextView charge_type;
        TextView han_date;
        TextView han_res_type;
        TextView seized_where_type;;
        TextView sup_place_type;
        TextView id_card;
        TextView name;
    }

    private void getTypeData(ViewHolder holder, Result_PublicSecurityEntity t)
    {
        // TODO Auto-generated method stub
        String han_res_type = PersionUtil.getPublicSecurityDealResult(t.getPunishResult());

        String seized_where_type = DisposalWhereabouts(t.getPunishPlace());

        holder.sup_place_type.setText(seized_where_type);

        String is_addiction = t.getIsAddiction();
        if ("0".equals(is_addiction))
        {
            is_addiction = "是";
        }else if ("1".equals(is_addiction)) {
            is_addiction = "否";
        }else {
            is_addiction = "";
        }

        String addiction_deg = t.getAddictionLevel();
        if ("0".equals(addiction_deg))
        {
            addiction_deg = "成瘾";
        }else if("1".equals(addiction_deg)){
            addiction_deg = "成瘾严重";
        }else {
            addiction_deg = "无";
        }

        String is_crime = t.getDrugCrime();
        if ("0".equals(is_crime))
        {
            is_crime = "否";
        }else if ("1".equals(is_crime)) {
            is_crime = "是";
        }else {
            is_crime = "";
        }

        String charge_type = t.getCrimeName();
        if ("1".equals(charge_type))
        {
            charge_type = "吸食毒品";
        }else if ("2".equals(charge_type))
        {
            charge_type = "其他违法犯罪";
        }else if ("3".equals(charge_type))
        {
            charge_type = "涉毒犯罪";
        }else if ("4".equals(charge_type))
        {
            charge_type = "其他涉毒违法";
        }else {
            charge_type = "无";
        }


        String current_drug = t.getIsCurrentDrug();
        if ("0".equals(current_drug))
        {
            current_drug = "是";
        }else if ("1".equals(current_drug))
        {
            current_drug = "否";
        }else {
            current_drug = "";
        }

        String urine_res = t.getUrineTestResult();
        if ("0".equals(urine_res))
        {
            urine_res = "阴性";
        }else if ("1".equals(urine_res))
        {
            urine_res = "阳性";
        }else {
            urine_res = "";
        }

        String seized_area_name = PersionUtil.chekParamsAndReturnStr(t.getCaughtAreaName());
        String seized_field = PersionUtil.chekParamsAndReturnStr(t.getCaughtLocation());

        holder.name.setText(t.getRealName());
        holder.crcname.setText(t.getHandlingDeptName());
        holder.seized_date.setText(TimeUtil.removeHour_Min_Seconds(t.getCaughtDate()));
        holder.current_drug.setText(current_drug);
        holder.urine_res.setText(urine_res);
        holder.is_addiction.setText(is_addiction);
        holder.addiction_deg.setText(addiction_deg);
        holder.seized_area_name.setText(seized_area_name);
        holder.seized_field.setText(seized_field);
        holder.is_crime.setText(is_crime);
        holder.charge_type.setText(charge_type);
        holder.han_date.setText(TimeUtil.removeHour_Min_Seconds(t.getPunishDate()));
        holder.han_res_type.setText(han_res_type);
        holder.seized_where_type.setText(seized_where_type);
        holder.sup_place_type.setText(t.getOrgName());
        holder.id_card.setText(t.getIdentityCard());
    }

    /**
     * 1释放 2进入戒毒/监管场所 3进入特殊病区 4交社区/乡/镇执行（市内） 5交社区/乡/镇执行（市外）
     * @param status
     * @return
     */
    private String DisposalWhereabouts(String status){
        String result = "";
        if("1".equals(status)){
            result = "释放";
        }else if("2".equals(status)){
            result = "进入戒毒/监管场所";
        }else if("3".equals(status)){
            result = "进入特殊病区";
        }else if("4".equals(status)){
            result = "交社区/乡/镇执行（市内）";
        }else if("5".equals(status)){
            result = "交社区/乡/镇执行（市外）";
        }
        return result;
    }
}
