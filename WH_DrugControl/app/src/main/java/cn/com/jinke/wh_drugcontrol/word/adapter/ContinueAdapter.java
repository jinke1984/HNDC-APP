package cn.com.jinke.wh_drugcontrol.word.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.input.model.Nation;
import cn.com.jinke.wh_drugcontrol.utils.CommUtils;
import cn.com.jinke.wh_drugcontrol.utils.DateUtil;
import cn.com.jinke.wh_drugcontrol.word.model.Continue;

/**
 * ViolateAgreeAdapter
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/9/7
 */

public class ContinueAdapter extends ProjectBaseAdapter<Continue> {

    public ContinueAdapter(Activity context) {
        super(context);
    }

    public ContinueAdapter(Activity context, ArrayList<Continue> list) {
        super(context, list);
    }

    @Override
    public View getViewEx(int position, View v, Continue aContinue) {
        Holder holder;
        if (v == null) {
            v = inflater.inflate(R.layout.item_suspended, null);
            holder = new Holder(mContext, v);
            v.setTag(holder);
        } else {
            holder = (Holder) v.getTag();
        }

        holder.setItem(aContinue);

        return v;
    }

    private static class Holder {
        private Context context;
        private ArrayList<Nation> jczzyyList = CommUtils.getInstance().assetsToList("/assets/jczzyy.json");

        @ViewInject(R.id.suspended_name)
        private TextView name;
        @ViewInject(R.id.suspended_date)
        private TextView level;
        @ViewInject(R.id.suspended_des)
        private TextView description;

        Holder(Context context, View view) {
            this.context = context;
            x.view().inject(this, view);
        }

        void setItem(Continue aContinue) {
            name.setText(aContinue.getRealName());
            level.setText(DateUtil.getInstance().changeDate(aContinue.getBreakDate()));
            for (Nation nation : jczzyyList) {
                if (nation.getId().equals(aContinue.getContinueReason())) {
                    description.setText(nation.getText());
                    break;
                }
            }
        }

    }

}
