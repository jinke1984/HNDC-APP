package cn.com.jinke.wh_drugcontrol.input.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.input.model.Urine;
import cn.com.jinke.wh_drugcontrol.utils.CommUtils;
import cn.com.jinke.wh_drugcontrol.utils.DateUtil;

/**
 * Created by jinke on 2017/8/4.
 */

public class UrineAdapter extends ProjectBaseAdapter<Urine> {

    public UrineAdapter(Activity context){
        super(context);
    }

    @Override
    public View getViewEx(int position, View v, Urine urine) {
        Holder holder = null;
        if(v == null){
            v = inflater.inflate(R.layout.item_urine, null, false);
            holder = new Holder(v);
            v.setTag(holder);
        }else{
            holder = (Holder)v.getTag();
        }
        holder.setItem(urine);
        return v;
    }

    private class Holder{

        @ViewInject(R.id.njlx_tv)
        private TextView mTypeTV;

        @ViewInject(R.id.njjg_tv)
        private TextView mResultTV;

        @ViewInject(R.id.njrq_tv)
        private TextView mDateTV;

        @ViewInject(R.id.njdd_tv)
        private TextView mAddressTV;

        @ViewInject(R.id.cjrq_tv)
        private TextView mCreateDateTV;

        @ViewInject(R.id.jcr_tv)
        private TextView mPersionTV;

        public Holder(View view){
            x.view().inject(this, view);
        }

        public void setItem(Urine urine){
            String type = String.format(mContext.getString(R.string.njlx), CommUtils.getInstance().getUrineType(urine.getUrineType()));
            String result = String.format(mContext.getString(R.string.njjg), CommUtils.getInstance().getUrineResult(urine.getUrineResult()));
            String date = String.format(mContext.getString(R.string.njrq), DateUtil.getInstance().changeDate(urine.getUrineDate()));
            String address = String.format(mContext.getString(R.string.njdd), urine.getUrinePlace());
            String createDate = String.format(mContext.getString(R.string.cjrq), DateUtil.getInstance().changeDate(urine.getCreateTime()));
            String persion = String.format(mContext.getString(R.string.jcr), urine.getTestcontactPerson());

            mTypeTV.setText(type);
            mResultTV.setText(result);
            mDateTV.setText(date);
            mAddressTV.setText(address);
            mCreateDateTV.setText(createDate);
            mPersionTV.setText(persion);
        }

    }
}
