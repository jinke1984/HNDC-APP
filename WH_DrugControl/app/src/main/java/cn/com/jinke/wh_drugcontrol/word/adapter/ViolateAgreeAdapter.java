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
import cn.com.jinke.wh_drugcontrol.utils.DateUtil;
import cn.com.jinke.wh_drugcontrol.word.model.ViolateAgreement;

/**
 * ViolateAgreeAdapter
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/9/7
 */

public class ViolateAgreeAdapter extends ProjectBaseAdapter<ViolateAgreement> {

    public ViolateAgreeAdapter(Activity context) {
        super(context);
    }

    public ViolateAgreeAdapter(Activity context, ArrayList<ViolateAgreement> list) {
        super(context, list);
    }

    @Override
    public View getViewEx(int position, View v, ViolateAgreement violateAgreement) {
        Holder holder;
        if (v == null) {
            v = inflater.inflate(R.layout.item_violate_agreement, null);
            holder = new Holder(mContext, v);
            v.setTag(holder);
        } else {
            holder = (Holder) v.getTag();
        }

        holder.setItem(violateAgreement);

        return v;
    }

    private static class Holder {
        private Context context;

        @ViewInject(R.id.violate_name)
        private TextView name;
        @ViewInject(R.id.violate_level)
        private TextView level;
        @ViewInject(R.id.violate_date)
        private TextView date;

        Holder(Context context, View view) {
            this.context = context;
            x.view().inject(this, view);
        }

        void setItem(ViolateAgreement violateAgreement) {
            String levelStr = context.getString(R.string.violate_level, violateAgreement.getViolateLevel());
            String time = context.getString(R.string.violate_date,
                    DateUtil.getInstance().changeDate(violateAgreement.getViolateDate()));
            name.setText(violateAgreement.getRealName());
            level.setText(levelStr);
            date.setText(time);
        }

    }

}
