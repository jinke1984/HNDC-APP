package cn.com.jinke.wh_drugcontrol.persion;

import android.os.Bundle;
import android.os.Message;

import com.alibaba.android.arouter.facade.annotation.Route;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.persion.adapter.S_AidAndRescueAdapter;
import cn.com.jinke.wh_drugcontrol.persion.manager.QueryDataManager;
import cn.com.jinke.wh_drugcontrol.persion.model.S_AidAndRescueEntity;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;

/**
 * Created by xkr on 2017/11/2.
 * 帮扶救助
 */
@Route(path = RouteUtils.R_PERSION_AID_AND_RESCUE)
public class S_AidAndRescueUI extends RefreshableUI {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerMessages(MSG_PERSION_AID_AND_RESCUE);
    }

    @Override
    protected ProjectBaseAdapter createAdapter() {
        return new S_AidAndRescueAdapter(this);
    }

    @Override
    protected String getTitleText() {
        return mName + getString(R.string.bfjzqkk);
    }

    @Override
    public void loadData() {
        showLoading();
        QueryDataManager.getInstance().getAidAndRescueData(mIdCard, mLoadMoreManager.getPageIndex(),
                mLoadMoreManager.getPageItemSize());
    }

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what){
            case MSG_PERSION_AID_AND_RESCUE:
                dismissLoading();
                mPullToRefreshListView.onRefreshComplete();
                List<S_AidAndRescueEntity> list = (List<S_AidAndRescueEntity>) msg.obj;
                if(list == null){
                    break;
                }
                mAdapter.addData(list);
                mAdapter.notifyDataSetChanged();
                break;
        }
        return super.handleMessage(msg);
    }

}
