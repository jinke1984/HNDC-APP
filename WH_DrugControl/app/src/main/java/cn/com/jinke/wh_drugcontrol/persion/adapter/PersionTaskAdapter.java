package cn.com.jinke.wh_drugcontrol.persion.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.persion.model.PersionTask;

/**
 * Created by jinke on 2017/8/3.
 */

public class PersionTaskAdapter extends ProjectBaseAdapter<PersionTask> {


    public PersionTaskAdapter(Activity context){
        super(context);
    }

    @Override
    public View getViewEx(int position, View v, PersionTask persionTask) {
        Holder holder = null;
        if(v == null){
            v = inflater.inflate(R.layout.item_control_infomation, null, false);
            holder = new Holder(v);
            v.setTag(holder);
        }else{
            holder = (Holder)v.getTag();
        }
        holder.setItem(persionTask);
        return v;
    }

    private class Holder{

        @ViewInject(R.id.control_img)
        private ImageView mImageView;

        @ViewInject(R.id.control_tv)
        private TextView mTextView;

        public Holder(View view){
            x.view().inject(this, view);
        }

        public void setItem(PersionTask persionTask){

            mImageView.setBackgroundResource(persionTask.getImgurl());
            mTextView.setText(persionTask.getTitle());

        }
    }
}
