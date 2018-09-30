package cn.com.jinke.wh_drugcontrol.persion;

import android.os.Bundle;
import android.os.Message;

import com.alibaba.android.arouter.facade.annotation.Route;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.persion.adapter.D_EmploymentAdapter;
import cn.com.jinke.wh_drugcontrol.persion.manager.QueryDataManager;
import cn.com.jinke.wh_drugcontrol.persion.model.Result_EmploymentEntity;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;

/**
 * Created by xkr on 2017/10/31.
 * 就业信息
 */
@Route(path = RouteUtils.R_PERSION_SUNSHINE)
public class D_EmploymentInformationUI extends RefreshableUI {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerMessages(MSG_PERSION_SUNSHINE);
    }

    @Override
    protected ProjectBaseAdapter createAdapter() {
        return new D_EmploymentAdapter(this);
    }

    @Override
    protected String getTitleText() {
        return getString(R.string.ygqyjyxx);
    }

    @Override
    public void loadData(){
        showLoading();
        QueryDataManager.getInstance().getResultEmploymentData(mIdCard, mLoadMoreManager.getPageIndex(),
                mLoadMoreManager.getPageItemSize());
    }

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what){
            case MSG_PERSION_SUNSHINE:
                dismissLoading();
                mPullToRefreshListView.onRefreshComplete();
                List<Result_EmploymentEntity> obj  = (List<Result_EmploymentEntity>) msg.obj;
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
