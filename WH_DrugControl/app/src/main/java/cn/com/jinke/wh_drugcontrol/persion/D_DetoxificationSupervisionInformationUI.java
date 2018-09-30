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
import cn.com.jinke.wh_drugcontrol.persion.adapter.D_DetoxificationSuperviseAdapter;
import cn.com.jinke.wh_drugcontrol.persion.manager.QueryDataManager;
import cn.com.jinke.wh_drugcontrol.persion.model.Result_DetoxificationSuperviseEntity;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;

/**
 * Created by xkr on 2017/10/31.
 * 戒毒监管场所入所/出所信息
 */
@Route(path = RouteUtils.R_PERSION_SUPERVISE)
public class D_DetoxificationSupervisionInformationUI extends AbsPersionInfoUi {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerMessages(MSG_PERSION_DETOXIFICATION_SUPERVISION);
    }

    @Override
    protected String getTitleText() {
        return getString(R.string.jdjgsrscs);
    }

    @Override
    public void loadData() {
        showLoading();
        QueryDataManager.getInstance().getDetoxificationSupervise(mIdCard);
    }

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what){
            case MSG_PERSION_DETOXIFICATION_SUPERVISION:
                dismissLoading();
                List<Result_DetoxificationSuperviseEntity> obj = (List<Result_DetoxificationSuperviseEntity>) msg.obj;
                if(obj == null){
                    break;
                }
                mAdapter.addData(obj);
                mAdapter.notifyDataSetChanged();
                break;
        }
        return super.handleMessage(msg);
    }

    @Override
    protected ProjectBaseAdapter createAdapter() {
        return new D_DetoxificationSuperviseAdapter(this);
    }

}
