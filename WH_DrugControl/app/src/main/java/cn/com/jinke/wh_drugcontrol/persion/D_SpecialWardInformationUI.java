package cn.com.jinke.wh_drugcontrol.persion;

import android.os.Bundle;
import android.os.Message;

import com.alibaba.android.arouter.facade.annotation.Route;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.persion.adapter.D_SpecialWardAdapter;
import cn.com.jinke.wh_drugcontrol.persion.manager.QueryDataManager;
import cn.com.jinke.wh_drugcontrol.persion.model.Result_SpecialWardEntity;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;

/**
 * Created by xkr on 2017/10/31.
 * 特殊病区出院/入院信息
 */
@Route(path = RouteUtils.R_PERSION_HOSPITAL_INFORMATION)
public class D_SpecialWardInformationUI extends AbsPersionInfoUi {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerMessages(MSG_PERSION_SPECIAL_WARD);
    }

    @Override
    protected ProjectBaseAdapter createAdapter() {
        return  new D_SpecialWardAdapter(this);
    }

    @Override
    protected String getTitleText() {
        return getString(R.string.tsbqrycy);
    }

    @Override
    public void loadData() {
        showLoading();
        QueryDataManager.getInstance().getResultSpecialWardData(mIdCard);
    }

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what){
            case MSG_PERSION_SPECIAL_WARD:
                dismissLoading();
                ArrayList<Result_SpecialWardEntity> obj = (ArrayList<Result_SpecialWardEntity>) msg.obj;
                if(obj == null){
                    break;
                }
                mAdapter.addData(obj);
                mAdapter.notifyDataSetChanged();
                break;
        }
        return super.handleMessage(msg);
    }
}
