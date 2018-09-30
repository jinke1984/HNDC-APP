package cn.com.jinke.wh_drugcontrol.me.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;

/**
 * @author jinke
 * @date 2018/9/10
 * @description
 */
public class SigninAdapter extends ProjectBaseAdapter<String> {


    public SigninAdapter(Activity activity){
        super(activity);
    }

    @Override
    public View getViewEx(int position, View v, String string) {
        Holder holder = null;
        if(v == null){
            v = inflater.inflate(R.layout.item_signin, null, false);
            holder = new Holder(v);
            v.setTag(holder);
        }else{
            holder = (Holder)v.getTag();
        }
        holder.setItem(string);
        return v;
    }

    private class Holder{

        @ViewInject(R.id.signin_name)
        private TextView mNameTV;

        @ViewInject(R.id.signin_time)
        private TextView mTimeTV;

        public Holder(View view){
            x.view().inject(this, view);
        }

        public void setItem(String string){

            String name = String.format(mContext.getString(R.string.qdr), MasterManager.getInstance().getUserCard().getInfoName());
            String time = String.format(mContext.getString(R.string.qdsj), string);

            mNameTV.setText(name);
            mTimeTV.setText(time);
        }
    }

}
