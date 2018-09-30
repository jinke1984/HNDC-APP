package cn.com.jinke.wh_drugcontrol.persion;

import android.os.Bundle;
import android.os.Message;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.utils.EmptyUtils;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.persion.adapter.D_AcceptDrugsAdapter;
import cn.com.jinke.wh_drugcontrol.persion.manager.QueryDataManager;
import cn.com.jinke.wh_drugcontrol.persion.model.Result_AcceptDrugsEntity;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;

/** 接受药物维持治疗信息
 * Created by xkr on 2017/10/31.
 */
@Route(path = RouteUtils.R_PERSION_ACCEPT_DRUGS)
public class D_AcceptDrugsCureInformationUI extends AbsPersionInfoUi {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerMessages(MSG_PERSION_ACCEPT_DRUGS_CURE);
    }

    @Override
    protected String getTitleText() {
        return getString(R.string.jsywwczl);
    }

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what){
            case MSG_PERSION_ACCEPT_DRUGS_CURE:
                dismissLoading();
                Result_AcceptDrugsEntity result = (Result_AcceptDrugsEntity) msg.obj;
                if(EmptyUtils.isNotEmpty(result)){
                    result.setIdentityCard(mIdCard);
                    List<Result_AcceptDrugsEntity> list = new ArrayList<>();
                    list.add(result);
                    mAdapter.addData(list);
                    mAdapter.notifyDataSetChanged();
                }
                break;
        }
        return super.handleMessage(msg);
    }

    @Override
    protected ProjectBaseAdapter createAdapter() {
        return new D_AcceptDrugsAdapter(this);
    }

    @Override
    public void loadData(){
        showLoading();
        QueryDataManager.getInstance().getAcceptDrugsData(mIdCard);
    }
}
