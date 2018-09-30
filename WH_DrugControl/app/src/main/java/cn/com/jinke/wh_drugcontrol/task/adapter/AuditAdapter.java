package cn.com.jinke.wh_drugcontrol.task.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.view.View;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.word.model.Audit;

/**
 */

public class AuditAdapter extends ProjectBaseAdapter<Audit> {

    private ColorStateList mCsl = null;

    public AuditAdapter(Activity context) {
        super(context);

        Resources resource = (Resources)mContext.getResources();
        mCsl = (ColorStateList) resource.getColorStateList(R.color.black);
    }

    @Override
    public View getViewEx(int position, View v, Audit audit) {
        Holder holder = null;
        if (v == null) {
            v = inflater.inflate(android.R.layout.simple_spinner_item, null);
            holder = new Holder(mContext, v);
            v.setTag(holder);
        } else {
            holder = (Holder) v.getTag();
        }
        
        holder.bindData(audit);
        return v;
    }

    private final class Holder {
        private Context context;

        @ViewInject(android.R.id.text1)
        TextView name;

        Holder(Context context, View view) {
            this.context = context;
            x.view().inject(this, view);
        }

        void bindData(Audit audit) {
            if (mCsl != null) {
                name.setTextColor(mCsl);
            }
            name.setText(audit.getUserName());
        }
    }

}
