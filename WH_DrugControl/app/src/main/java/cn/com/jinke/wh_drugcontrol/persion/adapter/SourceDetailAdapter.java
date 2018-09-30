package cn.com.jinke.wh_drugcontrol.persion.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.persion.model.Source;

/**
 * @author jinke
 * @date 2017/10/26
 * @description
 */

public class SourceDetailAdapter extends ProjectBaseAdapter<Source> {

    public SourceDetailAdapter(Activity activity){
        super(activity);
    }

    @Override
    public View getViewEx(int position, View v, Source source) {
        Holder holder = null;
        if(v == null){
            v = inflater.inflate(R.layout.item_point_detail, null, false);
            holder = new Holder(v);
            v.setTag(holder);
        }else{
            holder = (Holder)v.getTag();
        }
        holder.setItem(source);
        return v;
    }

    private class Holder{

        @ViewInject(R.id.jflx_tv)
        private TextView jflx_tv;

        @ViewInject(R.id.dqjf_tv)
        private TextView dqjf_tv;

        @ViewInject(R.id.jfjsrq_tv)
        private TextView jfjsrq_tv;

        @ViewInject(R.id.jfmx_tv)
        private TextView jfmx_tv;

        public Holder(View view){
            x.view().inject(this, view);
        }

        public void setItem(Source source){

            jflx_tv.setText(mContext.getString(R.string.jflx) + source.getCategItemName());
            dqjf_tv.setText(mContext.getString(R.string.dqjf) + source.getScore());
            jfjsrq_tv.setText(mContext.getString(R.string.jfjsrq)+ source.getMonth());
            jfmx_tv.setText(source.getDescribe());
        }
    }
}
