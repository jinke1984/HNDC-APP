package cn.com.jinke.wh_drugcontrol.persion.adapter;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.utils.StringUtils;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.persion.model.SeizedInfoEntity;

/**
 * Created by xkr on 2017/10/31.
 */

public class SeizedInfoAdapter extends ProjectBaseAdapter<SeizedInfoEntity> {
    public SeizedInfoAdapter(Activity context) {
        super(context);
    }

    @Override
    public View getViewEx(int position, View v, SeizedInfoEntity t) {
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

    private void getTypeData(ViewHolder holder, SeizedInfoEntity t)
    {
        // TODO Auto-generated method stub

        String han_res_type = t.getHan_res_type();
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

        String seized_where_type = t.getSeized_where_type();
        if (seized_where_type.equals("1"))
        {
            seized_where_type = "释放";
        } else if (seized_where_type.equals("2"))
        {
            seized_where_type = "进入戒毒/监管场所";
        } else if (seized_where_type.equals("3"))
        {
            seized_where_type = "进入特殊病区";
        } else if (seized_where_type.equals("4"))
        {
            seized_where_type = "交社区/乡/镇执行(贵阳内)";
        } else if (seized_where_type.equals("5"))
        {
            seized_where_type = "交社区/乡/镇执行(贵阳市外)";
        }else {
            seized_where_type = "无";
        }

        //String sup_place_type = "";
//		if (sup_place_type.equals("1"))
//		{
//			sup_place_type = "拘留所";
//		} else if (sup_place_type.equals("2"))
//		{
//			sup_place_type = "戒毒所";
//		} else if (sup_place_type.equals("3"))
//		{
//			sup_place_type = "看守所";
//		} else if (sup_place_type.equals("4"))
//		{
//			sup_place_type = "其他场所";
//		}else {
//			sup_place_type = "无";
//		}

        //去向场所逻辑
        String sup_place_type = "";
        if(Integer.valueOf(t.getHan_res_type())<7){
            sup_place_type = "无";
        }

        if(Integer.valueOf(t.getHan_res_type())>7&&Integer.valueOf(t.getSeized_where_type())==2){
            if(Integer.valueOf(t.getSup_place_type())==1){
                sup_place_type=t.getSup_place_type();
            }else if(Integer.valueOf(t.getSup_place_type())==2){
                sup_place_type=t.getSup_place_det();
            }else if(Integer.valueOf(t.getSup_place_type())==3){
                sup_place_type=t.getSup_place_gua();
            }else if(Integer.valueOf(t.getSup_place_type())==4){
                sup_place_type=t.getSup_place_other();
            }
        }

        if(Integer.valueOf(t.getHan_res_type())>7&&Integer.valueOf(t.getSeized_where_type())==3){
            //"进入"+d.spec_name;
            sup_place_type=t.getSpec_name();
        }
        if(Integer.valueOf(t.getHan_res_type())==7){
            //贵阳内
            if(Integer.valueOf(t.getSeized_where_type())==4){
                sup_place_type=t.getGoto_area_name();
            }
            //贵阳外
            if(Integer.valueOf(t.getSeized_where_type())==5){
                sup_place_type=t.getGoto_other_area_name();
            }
        }

        sup_place_type = StringUtils.null2Length0(sup_place_type);
        holder.sup_place_type.setText(sup_place_type);

        String is_addiction = t.getIs_addiction();
        if (is_addiction.equals("0"))
        {
            is_addiction = "否";
        }else if (is_addiction.equals("1")) {
            is_addiction = "是";
        }else {
            is_addiction = "无";
        }

        String addiction_deg = t.getAddiction_deg();
        if (addiction_deg.equals("0"))
        {
            addiction_deg = "成瘾严重";
        }else if(addiction_deg.equals("1")){
            addiction_deg = "成瘾";
        }else {
            addiction_deg = "无";
        }

        String is_crime = t.getIs_crime();
        if (is_crime.equals("0"))
        {
            is_crime = "否";
        }else if (is_crime.equals("1")) {
            is_crime = "是";
        }else {
            is_crime = "无";
        }

        String charge_type = t.getCharge_type();
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


        String current_drug = t.getCurrent_drug();
        if (current_drug.equals("0"))
        {
            current_drug = "否";
        }else if (current_drug.equals("1"))
        {
            current_drug = "是";
        }else {
            current_drug = "无";
        }

        String urine_res = t.getUrine_res();
        if (urine_res.equals("0"))
        {
            urine_res = "阴性";
        }else if (urine_res.equals("1"))
        {
            urine_res = "阳性";
        }else {
            urine_res = "无";
        }

        String seized_area_name = t.getSeized_area_name();
        if (TextUtils.isEmpty(seized_area_name) || seized_area_name.equals("null"))
        {
            seized_area_name = "无";
        }

        String seized_field = t.getSeized_field();
        if (TextUtils.isEmpty(seized_field) || seized_field.equals("null"))
        {
            seized_field = "无";
        }

        String crcname = t.getCrcname();
        String seized_date = t.getSeized_date();
        String han_date = t.getHan_date();
        isNull(crcname);
        isNull(seized_date);
        isNull(han_date);

        holder.crcname.setText(crcname);
        holder.seized_date.setText(seized_date);
        holder.current_drug.setText(current_drug);
        holder.urine_res.setText(urine_res);
        holder.is_addiction.setText(is_addiction);
        holder.addiction_deg.setText(addiction_deg);
        holder.seized_area_name.setText(seized_area_name);
        holder.seized_field.setText(seized_field);
        holder.is_crime.setText(is_crime);
        holder.charge_type.setText(charge_type);
        holder.han_date.setText(han_date);
        holder.han_res_type.setText(han_res_type);
        holder.seized_where_type.setText(seized_where_type);
        holder.sup_place_type.setText(sup_place_type);
        holder.name.setText(t.getName());
        holder.id_card.setText(t.getId_card());
    }

    private void isNull(String aStr){
        if (TextUtils.isEmpty(aStr) || aStr.equals(aStr))
        {
            aStr = "无";
        }
    }
}
