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
import cn.com.jinke.wh_drugcontrol.utils.CommUtils;
import cn.com.jinke.wh_drugcontrol.utils.DateUtil;
import cn.com.jinke.wh_drugcontrol.word.model.Suspended;

/**
 * ViolateAgreeAdapter
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/9/7
 */

public class SuspendedAdapter extends ProjectBaseAdapter<Suspended> {

    public SuspendedAdapter(Activity context) {
        super(context);
    }

    public SuspendedAdapter(Activity context, ArrayList<Suspended> list) {
        super(context, list);
    }

    @Override
    public View getViewEx(int position, View v, Suspended suspended) {
        Holder holder;
        if (v == null) {
            v = inflater.inflate(R.layout.item_suspended, null);
            holder = new Holder(mContext, v);
            v.setTag(holder);
        } else {
            holder = (Holder) v.getTag();
        }

        holder.setItem(suspended);

        return v;
    }

    private static class Holder {
        private Context context;

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

        void setItem(Suspended suspended) {
            name.setText(suspended.getRealName());
            level.setText(DateUtil.getInstance().changeDate(suspended.getBreakDate()));
            description.setText(CommUtils.getInstance().getBreakReasonById(suspended.getBreakReason()));
        }

    }

}
