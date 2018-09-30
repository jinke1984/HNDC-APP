package cn.com.jinke.wh_drugcontrol.me.adapter;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.input.manager.PhotoManager;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.Persion;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.persion.manager.PersionManager;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.CommUtils;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.MsgKey;

/**
 * Created by jinke on 2017/8/2.
 */

public class PersionAdapter extends ProjectBaseAdapter<Persion> implements CodeConstants, MsgKey{

    private UserCard mUserCard = null;

    public PersionAdapter(Activity context){
        super(context);
        mUserCard = MasterManager.getInstance().getUserCard();
    }

    @Override
    public View getViewEx(int position, View v, Persion persion) {
        Holder holder = null;
        if(v == null){
            v = inflater.inflate(R.layout.item_persion, null, false);
            holder = new Holder(v);
            v.setTag(holder);
        }else{
            holder = (Holder)v.getTag();
        }
        holder.setItem(persion);
        return v;
    }

    private class Holder{

        @ViewInject(R.id.persion_img)
        private ImageView mHeadIV;

        @ViewInject(R.id.persion_name_tv)
        private TextView mNameTV;

        @ViewInject(R.id.persion_sex_tv)
        private TextView mSexTV;

        @ViewInject(R.id.id_card_tv)
        private TextView mIdCardTV;

        @ViewInject(R.id.pre_source_tv)
        private TextView mPrvSourceTV;

        @ViewInject(R.id.now_source_tv)
        private TextView mNowSourceTV;

        @ViewInject(R.id.now_source_mark)
        private ImageView mSourceIV;

        @ViewInject(R.id.collect_iv)
        private ImageView mCollectIV;



        public Holder(View view){
            x.view().inject(this, view);
        }

        public void setItem(final Persion persion){

            final String path = persion.getPhotoAddress();

//            StringBuffer url = new StringBuffer();
//            url.append(IMAGE_URL);
//            url.append(path);
//            Glide.with(mContext).load(url.toString()).placeholder(R.drawable.item_default_head)
//                    .error(R.drawable.item_default_head).into(mHeadIV);
//            PhotoManager.getInstance().getPCFile(path, mHeadIV);

            PhotoManager.getInstance().getPic(path, mHeadIV);

            int isCollect = persion.getIsCollection();
            String name = String.format(mContext.getString(R.string.xm), persion.getRealName());
            String sex = String.format(mContext.getString(R.string.xb), CommUtils.getInstance().getSexZw(persion.getSex()));
            String idCard = String.format(mContext.getString(R.string.sfzhm), persion.getIdentityCard());
            int prv = persion.getLastTimeScore();
            int now = persion.getScore();

            mNameTV.setText(name);
            mSexTV.setText(sex);
            mIdCardTV.setText(idCard);
            mPrvSourceTV.setText(prv+"");
            mNowSourceTV.setText(now+"");

            if(now < prv){
                if(mSourceIV.getVisibility() == View.GONE){
                    mSourceIV.setVisibility(View.VISIBLE);
                }
                mSourceIV.setBackgroundResource(R.drawable.jf_down);
            }else if(now > prv){
                if(mSourceIV.getVisibility() == View.GONE){
                    mSourceIV.setVisibility(View.VISIBLE);
                }
                mSourceIV.setBackgroundResource(R.drawable.jf_up);
            }else{
                if(mSourceIV.getVisibility() == View.VISIBLE){
                    mSourceIV.setVisibility(View.GONE);
                }
            }

            if(GZRU.equals(mUserCard.getUserType())){
                if(mCollectIV.getVisibility() == View.GONE){
                    mCollectIV.setVisibility(View.VISIBLE);
                }

                switch (isCollect){
                    case YES_COLLECTION:
                        mCollectIV.setBackgroundResource(R.drawable.collect_light);
                        break;
                    case NO_COLLECTION:
                        mCollectIV.setBackgroundResource(R.drawable.collect_normal);
                        break;
                    default:
                        break;
                }
            }else{
                if(mCollectIV.getVisibility() == View.VISIBLE){
                    mCollectIV.setVisibility(View.GONE);
                }
            }

            mCollectIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int type = 0;
                    int isCollection = persion.getIsCollection();
                    if(isCollection == NO_COLLECTION){
                        //0是未收藏
                        type = YES_COLLECTION;
                    }else if(isCollection == YES_COLLECTION){
                        //1是已收藏
                        type = 2;
                    }
                    PersionManager.getInstance().collection(persion.getIdentityCard(), type, new PersionManager.onCollectNotiyUILister(){
                        @Override
                        public void onCollectNotiyUI(int state, String msg, int type) {
                            if(state == SUCCESS){
                                if(type == YES_COLLECTION){
                                    //1是已收藏
                                    persion.setIsCollection(YES_COLLECTION);
                                    mCollectIV.setBackgroundResource(R.drawable.collect_light);
                                    MessageProxy.sendMessage(COLLECTION_MSG_OK_SUC, state);
                                }else if(type == 2){
                                    //0是未收藏
                                    persion.setIsCollection(NO_COLLECTION);
                                    mCollectIV.setBackgroundResource(R.drawable.collect_normal);
                                    MessageProxy.sendMessage(COLLECTION_MSG_CANCEL_SUC, state);
                                }
                            }else{
                                MessageProxy.sendMessage(COLLECTION_MSG_FAIL, state);
                            }
                        }
                    });
                }
            });
        }
    }
}
