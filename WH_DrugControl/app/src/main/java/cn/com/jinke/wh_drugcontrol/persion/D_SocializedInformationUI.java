package cn.com.jinke.wh_drugcontrol.persion;

import android.content.Intent;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.ArrayList;
import java.util.List;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.persion.adapter.D_SocializedAdapter;
import cn.com.jinke.wh_drugcontrol.persion.model.Result_SocialRehabilitationServiceForDrugAddicts;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;

/**
 * Created by xkr on 2017/10/31.
 * 社会化戒毒康复站服务信息
 */
@Route(path = RouteUtils.R_PERSION_ACCEPT_RECOVVERY_INFORMATION)
public class D_SocializedInformationUI extends AbsPersionInfoUi {

    @Override
    protected String getTitleText() {
        return getString(R.string.jssqh);
    }

    @Override
    protected ProjectBaseAdapter createAdapter() {
        return new D_SocializedAdapter(this);
    }

    @Override
    protected void onInitData() {
        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle = intent.getBundleExtra(BUNDLE);
            Result_SocialRehabilitationServiceForDrugAddicts result = (Result_SocialRehabilitationServiceForDrugAddicts) bundle.getSerializable(PERSION);
            if(result == null){
                return;
            }
            List<Result_SocialRehabilitationServiceForDrugAddicts> list = new ArrayList<>();
            list.add(result);
            mAdapter.addData(list);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void loadData() {

    }
}
