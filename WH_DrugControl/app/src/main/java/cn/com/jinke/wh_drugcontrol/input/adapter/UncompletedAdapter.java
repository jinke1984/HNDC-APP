package cn.com.jinke.wh_drugcontrol.input.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectApplication;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.input.model.CachePerson;

/**
 * UncompletedAdapter
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/8/23
 */

public final class UncompletedAdapter extends ProjectBaseAdapter<CachePerson> {

    public UncompletedAdapter(Activity context) {
        super(context);
    }

    public UncompletedAdapter(Activity context, ArrayList<CachePerson> list) {
        super(context, list);
    }

    @Override
    public View getViewEx(int position, View v, CachePerson person) {
        ViewHolder holder = null;
        if (v == null) {
            v = LayoutInflater.from(mContext).inflate(R.layout.item_uncompletedpersion, null);
            holder = new ViewHolder(v);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }

        holder.setItem(person);

        return v;
    }

    private static class ViewHolder {
        private Context context;

        @ViewInject(R.id.uncompleted_name)
        private TextView txtName;

        @ViewInject(R.id.uncompleted_sex)
        private TextView txtSex;

        @ViewInject(R.id.uncompleted_idcard)
        private TextView txtIdCard;

        public ViewHolder(View view) {
            x.view().inject(this, view);
            this.context = ProjectApplication.getContext();
        }

        void setItem(@NonNull CachePerson person) {
            txtName.setText(person.getRealName());
            txtIdCard.setText(context.getString(R.string.sfzhmlable, person.getIdentityCard()));
            if ("1".equals(person.getSex())) {
                txtSex.setText(R.string.nan);
            } else if ("2".equals(person.getSex())) {
                txtSex.setText(R.string.nv);
            }
        }
    }

}
