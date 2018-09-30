package cn.com.jinke.wh_drugcontrol.input.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.input.model.Talk;
import cn.com.jinke.wh_drugcontrol.utils.CommUtils;
import cn.com.jinke.wh_drugcontrol.utils.DateUtil;

/**
 * Created by jinke on 2017/8/7.
 */

public class TalkAdapter extends ProjectBaseAdapter<Talk> {

    public TalkAdapter(Activity context){
        super(context);

    }

    @Override
    public View getViewEx(int position, View v, Talk talk) {
        Holder holder = null;
        if(v == null){
            v = inflater.inflate(R.layout.item_interview, null, false);
            holder = new Holder(v);
            v.setTag(holder);
        }else{
            holder = (Holder)v.getTag();
        }
        holder.setItem(talk);
        return v;
    }

    private class Holder{

        @ViewInject(R.id.title_tv)
        private TextView mObjectTV;

        @ViewInject(R.id.un_tv)
        private TextView mCountTV;

        @ViewInject(R.id.face_tv)
        private TextView mFaceTV;

        @ViewInject(R.id.time_tv)
        private TextView mTimeTV;

        public Holder(View view){
            x.view().inject(this, view);
        }

        public void setItem(Talk talk){

            String object = String.format(mContext.getString(R.string.thdx), talk.getTalkObject());
            String count = String.format(mContext.getString(R.string.thcs), String.valueOf(talk.getTalkCounts()));
            String face = String.format(mContext.getString(R.string.sfmdm), CommUtils.getInstance().getIsFace(talk.getCameraPicAdd()));
            String time = String.format(mContext.getString(R.string.thrq), DateUtil.getInstance().changeDate(talk.getTalkDate()));

            mObjectTV.setText(object);
            mCountTV.setText(count);
            mFaceTV.setText(face);
            mTimeTV.setText(time);
        }

    }
}
