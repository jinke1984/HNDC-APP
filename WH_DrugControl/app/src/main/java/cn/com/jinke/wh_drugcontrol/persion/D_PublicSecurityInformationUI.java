package cn.com.jinke.wh_drugcontrol.persion;

import android.os.Bundle;
import android.os.Message;

import com.alibaba.android.arouter.facade.annotation.Route;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.persion.adapter.D_PublicSecurityAdapter;
import cn.com.jinke.wh_drugcontrol.persion.manager.QueryDataManager;
import cn.com.jinke.wh_drugcontrol.persion.model.Result_PublicSecurityEntity;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;

/**
 * Created by xkr on 2017/10/31.
 * 公安办案部门查获信息
 */
@Route(path = RouteUtils.R_PERSION_SEIZED_INFORMATION)
public class D_PublicSecurityInformationUI extends AbsPersionInfoUi {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerMessages(MSG_PERSION_RECOVERY_INFO);
    }

    @Override
    protected String getTitleText() {
        return getString(R.string.gababuch);
    }

    @Override
    public void loadData() {
        showLoading();
        QueryDataManager.getInstance().getResultPublicSecurityData(mIdCard);
    }

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what){
            case MSG_PERSION_RECOVERY_INFO:
                dismissLoading();
                Result_PublicSecurityEntity obj = (Result_PublicSecurityEntity) msg.obj;
                if(obj == null){
                    break;
                }
                ArrayList<Result_PublicSecurityEntity> list = new ArrayList<>();
                list.add(obj);
                mAdapter.setData(list);
                mAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
        return super.handleMessage(msg);
    }

    @Override
    protected ProjectBaseAdapter createAdapter() {
        return new D_PublicSecurityAdapter(this);
    }
}
