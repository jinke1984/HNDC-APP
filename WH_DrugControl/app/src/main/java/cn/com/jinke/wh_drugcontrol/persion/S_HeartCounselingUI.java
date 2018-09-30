package cn.com.jinke.wh_drugcontrol.persion;

import android.os.Bundle;
import android.os.Message;

import com.alibaba.android.arouter.facade.annotation.Route;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.persion.adapter.S_HeartCounselingAdapter;
import cn.com.jinke.wh_drugcontrol.persion.manager.QueryDataManager;
import cn.com.jinke.wh_drugcontrol.persion.model.S_HeartCounselingEntity;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;

/**
 * Created by xkr on 2017/11/2.
 */
@Route(path = RouteUtils.R_PERSION_HEART_COUNSELING)
public class S_HeartCounselingUI extends AbsPersionInfoUi {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerMessages(MSG_PERSION_HEART_COUNSELING);
    }

    @Override
    protected String getTitleText() {
        return mName + getString(R.string.xlzxqkk);
    }

    @Override
    public void loadData() {
        showLoading();
        QueryDataManager.getInstance().getHeartCounselingData(mIdCard, 0, 15);
    }

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what){
            case MSG_PERSION_HEART_COUNSELING:
                dismissLoading();
                List<S_HeartCounselingEntity> list = (List<S_HeartCounselingEntity>) msg.obj;
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
        return new S_HeartCounselingAdapter(this);
    }
}
