package cn.com.jinke.wh_drugcontrol.input.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.input.manager.PhotoManager;

/**
 * Created by jinke on 2017/8/9.
 */

public class HousePhotoAdapter extends ProjectBaseAdapter<String> {

    public HousePhotoAdapter(Activity context){
        super(context);
    }

    @Override
    public View getViewEx(int position, View v, String s) {
        Holder holder = null;
        if(v == null){
            v = inflater.inflate(R.layout.item_housephoto, null, false);
            holder = new Holder(v);
            v.setTag(holder);
        }else{
            holder = (Holder)v.getTag();
        }
        holder.setItem(s);
        return v;
    }

    private class Holder{

        @ViewInject(R.id.img)
        private ImageView mImageView;

        public Holder(View view){
            x.view().inject(this, view);
        }

        public void setItem(String path){
//            StringBuffer url = new StringBuffer();
//            url.append(IMAGE_URL);
//            url.append(path);
//            Glide.with(mContext).load(url.toString()).placeholder(R.drawable.default_msg)
//                    .error(R.drawable.default_msg).into(mImageView);
//            PhotoManager.getInstance().getPCFile(path, mImageView);

            PhotoManager.getInstance().getPic(path, mImageView);

        }
    }
}
