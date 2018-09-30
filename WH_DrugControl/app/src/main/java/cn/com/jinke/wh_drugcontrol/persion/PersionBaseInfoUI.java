package cn.com.jinke.wh_drugcontrol.persion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.customview.AppOneDialog;
import cn.com.jinke.wh_drugcontrol.me.model.Persion;
import cn.com.jinke.wh_drugcontrol.utils.CommUtils;
import cn.com.jinke.wh_drugcontrol.utils.DateUtil;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;

/**
 * Created by jinke on 2017/8/3.
 */

@Route(path = RouteUtils.R_PERSION_BASEINFO_UI)
public class PersionBaseInfoUI extends ProjectBaseUI {

    @ViewInject(R.id.drug_details_name)
    private TextView mNameTV;

    @ViewInject(R.id.drug_details_sex)
    private TextView mSexTV;

    @ViewInject(R.id.drug_details_birth)
    private TextView mTimeTV;

    @ViewInject(R.id.drug_details_name_other)
    private TextView mOtherNameTV;

    @ViewInject(R.id.drug_details_educational_level)
    private TextView mWhcdTV;

    @ViewInject(R.id.drug_details_lxfs)
    private TextView mPhoneTV;

    @ViewInject(R.id.drug_details_address)
    private TextView mAddressTV;

    @ViewInject(R.id.drug_details_hjxxdz)
    private TextView mHJAddressTV;

    @ViewInject(R.id.drug_details_xjzdxz)
    private TextView mXJAddressTV;

    private Persion mPersion = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_persion_base_info);
    }

    @Override
    protected void onInitView() {
        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle = intent.getBundleExtra(BUNDLE);
            mPersion = (Persion)bundle.getSerializable(PERSION);
        }

        Header header = getHeader();
        if(header != null){
            String name = mPersion.getRealName();
            String title = String.format(getString(R.string.xdryxq), name);
            header.titleText.setText(title);
        }
    }

    @Override
    protected void onInitData() {
        mNameTV.setText(mPersion.getRealName());
        mSexTV.setText(CommUtils.getInstance().getSexZw(mPersion.getSex()));
        mTimeTV.setText(DateUtil.getInstance().changeDate(mPersion.getBirthdate()));
        mOtherNameTV.setText(CommUtils.getInstance().getWU(mPersion.getPreRealName()));
        mWhcdTV.setText(CommUtils.getInstance().getWhcdZW(mPersion.getEducation()));
        mPhoneTV.setText(CommUtils.getInstance().getWU(mPersion.getCellphone()));
        mAddressTV.setText(CommUtils.getInstance().getWU(mPersion.getHouseholdAddressName()));
        mHJAddressTV.setText(CommUtils.getInstance().getWU(mPersion.getHouseholdAddress()));
        mXJAddressTV.setText(CommUtils.getInstance().getWU(mPersion.getCurrentAddress()));
    }

    private void showMsgDialog(int title, String message){

        AppOneDialog dialog = new AppOneDialog(this, message, getString(title), true, null);
        dialog.show();
    }

    @Event(value = {R.id.address_layout, R.id.hjxxdz_layout, R.id.xjzdxz_layout}, type = View.OnClickListener.class)
    private void onClick(View view){
        switch (view.getId()){
            case R.id.address_layout:
                showMsgDialog(R.string.hjssqywm, CommUtils.getInstance().getWU(mPersion.getHouseholdAddressName()));
                break;
            case R.id.hjxxdz_layout:
                showMsgDialog(R.string.hjxxdz, CommUtils.getInstance().getWU(mPersion.getHouseholdAddress()));
                break;
            case R.id.xjzdxz_layout:
                showMsgDialog(R.string.xjzdxz, CommUtils.getInstance().getWU(mPersion.getCurrentAddress()));
                break;
            default:
                break;
        }
    }
}
