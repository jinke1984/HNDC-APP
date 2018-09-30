package cn.com.jinke.wh_drugcontrol.input.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.input.model.Report;
import cn.com.jinke.wh_drugcontrol.utils.CommUtils;
import cn.com.jinke.wh_drugcontrol.utils.DateUtil;

/**
 * Created by jinke on 2017/8/8.
 */

public class ReportAdapter extends ProjectBaseAdapter<Report> {

    public ReportAdapter(Activity context){
        super(context);
    }

    @Override
    public View getViewEx(int position, View v, Report report) {
        Holder holder = null;
        if(v == null){
            v = inflater.inflate(R.layout.item_report, null, false);
            holder = new Holder(v);
            v.setTag(holder);
        }else{
            holder = (Holder)v.getTag();
        }
        holder.setItem(report);
        return v;
    }

    private class Holder{

        @ViewInject(R.id.time_tv)
        private TextView mDateTV;

        @ViewInject(R.id.area_tv)
        private TextView mAddressTV;

        @ViewInject(R.id.count_tv)
        private TextView mCountTV;

        @ViewInject(R.id.face_tv)
        private TextView mFaceTV;

        public Holder(View view){
            x.view().inject(this, view);
        }

        public void setItem(Report report){

            String data = String.format(mContext.getString(R.string.bgrq), DateUtil.getInstance().changeDate(report.getReportDate()));
            String address = String.format(mContext.getString(R.string.bgdd),report.getReportPlache());
            String count = String.format(mContext.getString(R.string.bgcs),String.valueOf(report.getReportCounts()));
            String face = String.format(mContext.getString(R.string.sfmdm), CommUtils.getInstance().getIsFace(report.getCameraPicAdd()));

            mDateTV.setText(data);
            mAddressTV.setText(address);
            mCountTV.setText(count);
            mFaceTV.setText(face);

        }
    }
}
