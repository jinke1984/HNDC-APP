package cn.com.jinke.wh_drugcontrol.persion;

import android.os.Bundle;
import android.os.Message;

import com.alibaba.android.arouter.facade.annotation.Route;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.persion.adapter.E_EmploymentRecommentAdapter;
import cn.com.jinke.wh_drugcontrol.persion.manager.QueryDataManager;
import cn.com.jinke.wh_drugcontrol.persion.model.E_EmploymentRecommendationEntity;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;

/**
 * Created by xkr on 2017/11/3.
 * 就业推荐记录
 */
@Route(path = RouteUtils.R_PERSION_EMPLOYMENT_RECOMMENT)
public class E_EmploymentRecommendationUI extends RefreshableUI {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerMessages(MSG_PERSION_EMPLOYMENT_RECOMMENT);
    }

    @Override
    protected String getTitleText() {
        return mName +"-" + getString(R.string.jytjjll);
    }

    @Override
    protected ProjectBaseAdapter createAdapter() {
        return new E_EmploymentRecommentAdapter(this);
    }

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what){
            case MSG_PERSION_EMPLOYMENT_RECOMMENT:
                dismissLoading();
                mPullToRefreshListView.onRefreshComplete();
                List<E_EmploymentRecommendationEntity> list = (List<E_EmploymentRecommendationEntity>) msg.obj;
                if(list == null){
                    break;
                }
                mAdapter.addData(list);
                mAdapter.notifyDataSetChanged();
                break;
        }
        return super.handleMessage(msg);
    }

    @Override
    public void loadData() {
        showLoading();
        QueryDataManager.getInstance().getEmploymentRecommendationData(mIdCard, mLoadMoreManager.getPageIndex(),
                mLoadMoreManager.getPageItemSize());
    }
}
