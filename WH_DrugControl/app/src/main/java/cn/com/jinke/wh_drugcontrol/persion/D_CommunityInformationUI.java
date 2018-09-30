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
import cn.com.jinke.wh_drugcontrol.persion.adapter.D_CommunityAdapter;
import cn.com.jinke.wh_drugcontrol.persion.manager.QueryDataManager;
import cn.com.jinke.wh_drugcontrol.persion.model.Result_CommunityDrugDetoxificationOrRecovery;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;

/**
 * Created by admin on 2017/10/31.
 * 社区戒毒/社区康复信息
 */
@Route(path = RouteUtils.R_PERSION_RECOVERY_INFORMATION)
public class D_CommunityInformationUI extends AbsPersionInfoUi {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerMessages(MSG_PERSION_RECOVERY_INFO);
    }

    @Override
    protected String getTitleText() {
        return getString(R.string.sqjdkf);
    }

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what){
            case MSG_PERSION_RECOVERY_INFO:
                dismissLoading();
                Result_CommunityDrugDetoxificationOrRecovery result = (Result_CommunityDrugDetoxificationOrRecovery) msg.obj;
                if(result == null){
                    break;
                }
                List<Result_CommunityDrugDetoxificationOrRecovery> list = new ArrayList<>();
                list.add(result);
                mAdapter.addData(list);
                mAdapter.notifyDataSetChanged();
                break;
        }
        return super.handleMessage(msg);
    }

    @Override
    protected ProjectBaseAdapter createAdapter() {
        return new D_CommunityAdapter(this);
    }

    /**
     * 获取 社区戒毒/社区康复信息 详情
     */
    @Override
    public void loadData(){
        showLoading();
        QueryDataManager.getInstance().getCommunityDrugData(mIdCard);
    }
}
