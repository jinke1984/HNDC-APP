package cn.com.jinke.wh_drugcontrol.persion;

import android.os.Bundle;
import android.os.Message;

import com.alibaba.android.arouter.facade.annotation.Route;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.persion.adapter.S_PhysicalExaminationAdapter;
import cn.com.jinke.wh_drugcontrol.persion.manager.QueryDataManager;
import cn.com.jinke.wh_drugcontrol.persion.model.S_PhysicalExaminationEntity;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;

/**
 * Created by xkr on 2017/11/2.
 * 身体检查
 */
@Route(path = RouteUtils.R_PERSION_PHYSICAL_EXAMINATION)
public class S_PhysicalExaminationUI extends RefreshableUI {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerMessages(MSG_PERSION_PHYSICAL_EXAMINATION);
    }

    @Override
    protected String getTitleText() {
        return mName + getString(R.string.stjcqkk);
    }

    @Override
    public void loadData() {
        showLoading();
        QueryDataManager.getInstance().getPhysicalExaminationData(mIdCard, mLoadMoreManager.getPageIndex(),
                mLoadMoreManager.getPageItemSize());
    }

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what){
            case MSG_PERSION_PHYSICAL_EXAMINATION:
                dismissLoading();
                mPullToRefreshListView.onRefreshComplete();
                List<S_PhysicalExaminationEntity> list = (List<S_PhysicalExaminationEntity>) msg.obj;
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
    protected ProjectBaseAdapter createAdapter() {
        return new S_PhysicalExaminationAdapter(this);
    }

}
