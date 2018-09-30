package cn.com.jinke.wh_drugcontrol.persion.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;

/**
 * Created by xkr on 2017/11/3.
 */

public class GatherInformationAdapter extends ProjectBaseAdapter<String> {
    public GatherInformationAdapter(Activity context) {
        super(context);
    }

    private final class Holder{
        TextView name_textview;
    }

    @Override
    public View getViewEx(int position, View v, String t) {
        // TODO Auto-generated method stub
        Holder holder = null;
        if(v == null || v.getTag() == null){
            holder = new Holder();
            v = inflater.inflate(R.layout.item_pickupinfo, null);
            holder.name_textview = (TextView)v.findViewById(R.id.name_tv);
            v.setTag(holder);
        }
        holder = (Holder) v.getTag();
        bindData(holder, t);
        return v;
    }

    private void bindData(Holder holder, String t){
        holder.name_textview.setText(t);
    }
}
