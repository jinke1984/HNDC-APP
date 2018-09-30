package cn.com.jinke.wh_drugcontrol.me;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.customview.NewGridView;
import cn.com.jinke.wh_drugcontrol.me.adapter.AreaAdapter;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.manager.MeManager;
import cn.com.jinke.wh_drugcontrol.me.model.Area;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;

/**
 * @author jinke
 * @date 2017/10/31
 * @description 行政位置区域管理界面
 */

@Route(path = RouteUtils.R_AREA_UI)
public class AreaUI extends ProjectBaseUI {

    @ViewInject(R.id.area_grid)
    private NewGridView mAreaGV;
    private Header mHeader = null;

    private UserCard mUserCard = null;
    private AreaAdapter mAreaAdapter = null;
    private List<Area> mAreaList = new ArrayList<>();
    private int[] MSG = new int[]{AREA_MSG, ACCESS_NET_FAILED};

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what){
            case AREA_MSG:
                dismissLoading();
                if(msg.arg1 == SUCCESS){
                    mAreaList = (List<Area>)msg.obj;
                    mAreaAdapter.setData(mAreaList);
                    mAreaAdapter.notifyDataSetChanged();
                }
                break;
            case ACCESS_NET_FAILED:
                dismissLoading();
                break;
            default:
                break;
        }
        return super.handleMessage(msg);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerMessages(MSG);
        setContentView(R.layout.ui_area);
    }

    @Override
    protected void onInitData() {
        String orgId = mUserCard.getOrgId();
        getArea(orgId);
    }

    @Override
    protected void onInitView() {

        mUserCard = MasterManager.getInstance().getUserCard();
        mHeader = getHeader();
        if(mHeader != null){
            mHeader.titleText.setText(mUserCard.getOrgName());
        }

        mAreaAdapter = new AreaAdapter(this);
        mAreaGV.setAdapter(mAreaAdapter);
    }

    private void getArea(String aOrgId){
        if(isNetworkConnected()){
            showLoading();
            MeManager.getInstance().getArea(aOrgId);
        }else{
            showToast(R.string.common_network_unavailable);
        }
    }

    @Event(value = {R.id.area_grid}, type = android.widget.AdapterView.OnItemClickListener.class)
    private void onItemClick(AdapterView<?> parent, View view, int position, long id){
        Area area = mAreaList.get(position);
        String level = area.getOrgLevel();
        String name = area.getOrgName();

        if(level != null && JWHLEVEL.equals(level)){
            Bundle bundle = new Bundle();
            bundle.putSerializable(B_NAME, name);
            bundle.putSerializable(B_AREA_ID, area.getId());
            bundle.putBoolean(B_COLLECT, false);
            ARouter.getInstance().build(RouteUtils.R_PERSION_LIST_UI).withBundle(BUNDLE, bundle).navigation();
        }else{
            if(!TextUtils.isEmpty(name)){
                mHeader.titleText.setText(name);
            }
            String orgId = area.getId();
            getArea(orgId);
        }
    }
}
