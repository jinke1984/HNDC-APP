package cn.com.jinke.wh_drugcontrol.input.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.input.model.Change;
import cn.com.jinke.wh_drugcontrol.utils.CommUtils;

/**
 * @author jinke
 * @date 2018/3/9
 * @description
 */

public class ChangeAddressAdapter extends ProjectBaseAdapter<Change> {

    public ChangeAddressAdapter(Activity context){
        super(context);
    }

    @Override
    public View getViewEx(int position, View v, Change change) {
        Holder holder = null;
        if(v == null){
            v = inflater.inflate(R.layout.item_change_address, null, false);
            holder = new Holder(v);
            v.setTag(holder);
        }else{
            holder = (Holder)v.getTag();
        }
        holder.setItem(change);
        return v;
    }

    private class Holder{

        @ViewInject(R.id.now_address_tv)
        private TextView currentAddressTV;

        @ViewInject(R.id.change_address_detail_tv)
        private TextView changeAddressTV;

        @ViewInject(R.id.change_address_tv)
        private TextView changeAddressNameTV;

        @ViewInject(R.id.change_reason_tv)
        private TextView changeReasonTV;

        @ViewInject(R.id.leave_date_tv)
        private TextView leaveDateTV;

        public Holder(View view){
            x.view().inject(this, view);
        }

        public void setItem(Change change){

            String reason = CommUtils.getInstance().changeReason(change.getChangeReason());
            String currentAddress = String.format(mContext.getString(R.string.xzxdxxdz), CommUtils.getInstance().getWU(change.getCurrentAddress()));
            String changeAddress = String.format(mContext.getString(R.string.nbgzxdxxdz), CommUtils.getInstance().getWU(change.getChangeAddress()));
            String changeAddressName = String.format(mContext.getString(R.string.nbgzxdmc), CommUtils.getInstance().getWU(change.getChangeAddressName()));
            String changeReason = String.format(mContext.getString(R.string.bgyy), CommUtils.getInstance().getWU(reason));
            String leaveDate = String.format(mContext.getString(R.string.lksqrq), CommUtils.getInstance().getWU(change.getLeaveDate()));

            currentAddressTV.setText(currentAddress);
            changeAddressTV.setText(changeAddress);
            changeAddressNameTV.setText(changeAddressName);
            changeReasonTV.setText(changeReason);
            leaveDateTV.setText(leaveDate);
        }
    }
}
