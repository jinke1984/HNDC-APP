package cn.com.jinke.wh_drugcontrol.persion.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.input.manager.PhotoManager;
import cn.com.jinke.wh_drugcontrol.persion.model.WorkPersion;
import cn.com.jinke.wh_drugcontrol.utils.CommUtils;

/**
 * Created by jinke on 2017/9/18.
 */

public class WorkAdapter extends ProjectBaseAdapter<WorkPersion> {

    public WorkAdapter(Activity activity){
        super(activity);
    }

    @Override
    public View getViewEx(int position, View v, WorkPersion workPersion) {
        Holder holder = null;
        if(v == null){
            v = inflater.inflate(R.layout.item_work_persion, null, false);
            holder = new Holder(v);
            v.setTag(holder);
        }else{
            holder = (Holder)v.getTag();
        }
        holder.setItem(workPersion);
        return v;
    }

    private class Holder{

        @ViewInject(R.id.work_persion_img)
        private ImageView mHeadIV;

        @ViewInject(R.id.work_persion_name_tv)
        private TextView mNameTV;

        @ViewInject(R.id.work_persion_sex_tv)
        private TextView mSexTV;

        @ViewInject(R.id.work_id_card_tv)
        private TextView mIdCardTV;

        public Holder(View view){
            x.view().inject(this, view);
        }

        public void setItem(WorkPersion workPersion){
            final String path = workPersion.getPhotoAddress();
            if(path != null){
//                StringBuffer url = new StringBuffer();
//                url.append(PhotoManager.getInstance().IMAGE_URL);
//                url.append(path);
//                Glide.with(mContext).load(url.toString()).placeholder(R.drawable.item_default_head).into(mHeadIV);

                PhotoManager.getInstance().getPic(path, mHeadIV);
            }

            String name = String.format(mContext.getString(R.string.xm), workPersion.getRealName());
            String sex = String.format(mContext.getString(R.string.xb), CommUtils.getInstance().getSexZw(workPersion.getSex()));
            String idCard = String.format(mContext.getString(R.string.sfzhm), workPersion.getIdentityCard());

            mNameTV.setText(name);
            mSexTV.setText(sex);
            mIdCardTV.setText(idCard);

        }

    }
}
