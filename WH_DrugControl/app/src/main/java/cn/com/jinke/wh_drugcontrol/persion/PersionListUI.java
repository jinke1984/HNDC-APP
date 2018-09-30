package cn.com.jinke.wh_drugcontrol.persion;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.manager.PullToRefreshHelper;
import cn.com.jinke.wh_drugcontrol.me.adapter.PersionAdapter;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.manager.MeManager;
import cn.com.jinke.wh_drugcontrol.me.model.Persion;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;

/**
 * @author jinke
 * @date 2017/11/1
 * @description
 */

@Route(path = RouteUtils.R_PERSION_LIST_UI)
public class PersionListUI extends ProjectBaseUI implements OnRefreshListener2<ListView> {

    @ViewInject(R.id.persion_listview)
    private PullToRefreshListView mPersionLV = null;

    private UserCard mUserCard = null;
    private PersionAdapter mPersionAdapter = null;
    private int[] MSG = new int[]{SEARCH_MSG, ACCESS_NET_FAILED, COLLECTION_MSG_OK_SUC, COLLECTION_MSG_FAIL, COLLECTION_MSG_CANCEL_SUC};
    private List<Persion> mList = new ArrayList<>();
    private String mName = null;
    private String mOrgId = null;

    /**
     * true 查询关注的人，false不是
     */
    private boolean isCollect = false;

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what){
            case SEARCH_MSG:
                dismissLoading();
                mPersionLV.onRefreshComplete();
                if(msg.arg1 == SUCCESS){
                    mList = (List<Persion>)msg.obj;
                    mPersionAdapter.setData(mList);
                    mPersionAdapter.notifyDataSetChanged();
                }
                setFooter();
                break;
            case ACCESS_NET_FAILED:
                dismissLoading();
                break;
            case COLLECTION_MSG_OK_SUC:
                showToast(R.string.cccg);
                if(isCollect){
                    getPersion();
                }
                break;
            case COLLECTION_MSG_FAIL:
                showToast(R.string.ccsb);
                break;
            case COLLECTION_MSG_CANCEL_SUC:
                showToast(R.string.qxsc);
                if(isCollect){
                    getPersion();
                }
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
        setContentView(R.layout.ui_persion_list);
    }

    @Override
    protected void onInitData() {
        getPersion();
    }

    private void getPersion(){
        if(isNetworkConnected()){
            showLoading();
            int collect = 0;
            if(isCollect){
                collect = 1;
            }
            MeManager.getInstance().search(true, mUserCard.getUserId(), "", mUserCard.getUserType(), mOrgId, collect);
        }else{
            showToast(R.string.common_network_unavailable);
        }
    }

    @Override
    protected void onInitView() {
        mUserCard = MasterManager.getInstance().getUserCard();

        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle = intent.getBundleExtra(BUNDLE);
            mName = bundle.getString(B_NAME);
            mOrgId = bundle.getString(B_AREA_ID);
            isCollect = bundle.getBoolean(B_COLLECT);
        }

        Header header = getHeader();
        if(header != null){
            header.titleText.setText(mName);
        }

        PullToRefreshHelper.initHeader(mPersionLV);
        PullToRefreshHelper.initFooter(mPersionLV);
        mPersionLV.setOnRefreshListener(this);

        mPersionAdapter = new PersionAdapter(this);
        mPersionLV.setAdapter(mPersionAdapter);
        setFooter();
    }

    @Override
    public void onPullDownToRefresh(final PullToRefreshBase<ListView> refreshView) {
        if(isNetworkConnected()){
            showLoading();
            int collect = 0;
            if(isCollect){
                collect = 1;
            }
            MeManager.getInstance().search(true, mUserCard.getUserId(), "", mUserCard.getUserType(), mOrgId, collect);
        }else{
            showToast(R.string.common_network_unavailable);
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    refreshView.onRefreshComplete();
                }
            });
        }
    }

    @Override
    public void onPullUpToRefresh(final PullToRefreshBase<ListView> refreshView) {
        if(isNetworkConnected()){
            showLoading();
            int collect = 0;
            if(isCollect){
                collect = 1;
            }
            MeManager.getInstance().search(false, mUserCard.getUserId(), "", mUserCard.getUserType(), mOrgId, collect);
        }else{
            showToast(R.string.common_network_unavailable);
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    refreshView.onRefreshComplete();
                }
            });
        }
    }

    @Event(value = {R.id.persion_listview}, type = android.widget.AdapterView.OnItemClickListener.class)
    private void onItemClick(AdapterView<?> parent, View view, int position, long id){
        Bundle bundle = new Bundle();
        Persion persion = mList.get(position-1);
        bundle.putSerializable(PERSION, persion);
        ARouter.getInstance().build(RouteUtils.R_PERSION_PERSIONDETAIL_UI).withBundle(BUNDLE, bundle).navigation();
    }

    private void setFooter() {
        if (mPersionAdapter.getCount() == 0) {
            mPersionLV.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        } else {
            if (MeManager.getInstance().isFinish()) {
                mPersionLV.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
            } else {
                mPersionLV.setMode(PullToRefreshBase.Mode.BOTH);
            }
        }
    }
}
