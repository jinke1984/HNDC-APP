package cn.com.jinke.wh_drugcontrol.me.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.me.model.Area;

/**
 * @author jinke
 * @date 2017/10/31
 * @description
 */

public class AreaAdapter extends ProjectBaseAdapter<Area> {

    public AreaAdapter(Activity activity){
        super(activity);
    }

    @Override
    public View getViewEx(int position, View v, Area area) {
        Holder holder = null;
        if(v == null){
            v = inflater.inflate(R.layout.item_area, null, false);
            holder = new Holder(v);
            v.setTag(holder);
        }else{
            holder = (Holder)v.getTag();
        }
        holder.setItem(area);
        return v;
    }

    private class Holder{

        @ViewInject(R.id.qu_tv)
        private TextView mAreaNameTV;

        public Holder(View view){
            x.view().inject(this, view);
        }

        public void setItem(Area area){
            mAreaNameTV.setText(area.getOrgName());
        }
    }
}
