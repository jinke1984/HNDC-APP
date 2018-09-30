package cn.com.jinke.wh_drugcontrol.persion;

import android.os.Bundle;
import android.os.Message;

import com.alibaba.android.arouter.facade.annotation.Route;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.persion.adapter.MedicationAdapter;
import cn.com.jinke.wh_drugcontrol.persion.manager.QueryDataManager;
import cn.com.jinke.wh_drugcontrol.persion.model.MedicationEntity;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;

/**
 * Created by xkr on 2017/10/31.
 * 服药情况
 */
@Route(path = RouteUtils.R_PERSION_MEDICATION)
public class A_MedicationUI extends RefreshableUI {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerMessages(MSG_PERSION_MEDICATION_SITUATION);
    }

    @Override
    protected String getTitleText() {
        return getString(R.string.fyqks);
    }

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what){
            case MSG_PERSION_MEDICATION_SITUATION:
                dismissLoading();
                mPullToRefreshListView.onRefreshComplete();
                List<MedicationEntity> list = (List<MedicationEntity>) msg.obj;
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
        return new MedicationAdapter(this);
    }

    @Override
    public void loadData() {
        showLoading();
        QueryDataManager.getInstance().getMedicationData(mIdCard, mLoadMoreManager.getPageIndex(),
                mLoadMoreManager.getPageItemSize());
    }

}
