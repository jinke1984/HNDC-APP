package cn.com.jinke.wh_drugcontrol.input;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.input.adapter.ChangeAddressAdapter;
import cn.com.jinke.wh_drugcontrol.input.manager.ChangeAddressManager;
import cn.com.jinke.wh_drugcontrol.input.model.Change;
import cn.com.jinke.wh_drugcontrol.manager.PullToRefreshHelper;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.Persion;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.persion.model.Document;
import cn.com.jinke.wh_drugcontrol.utils.CommUtils;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;

/**
 * @author jinke
 * @date 2018/3/8
 * @description
 */

@Route(path = RouteUtils.R_CHANGE_ADDRESS_UI)
public class ChangeAddressUI extends ProjectBaseUI implements OnRefreshListener2<ListView> {

    @ViewInject(R.id.change_address_listview)
    private PullToRefreshListView mChangeAddressLV;

    private Persion mPersion = null;
    private Document mDocument = null;
    private List<Change> mChangeList = new ArrayList<>();
    private ChangeAddressAdapter mAdapter = null;

    private int[] MSG = new int[]{CHANGE_MSG, ACCESS_NET_FAILED};

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what){
            case CHANGE_MSG:
                dismissLoading();
                mChangeAddressLV.onRefreshComplete();
                if(msg.arg1 == SUCCESS){
                    mChangeList = (List<Change>)msg.obj;
                    mAdapter.setData(mChangeList);
                    mAdapter.notifyDataSetChanged();
                }
                setFooter();
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
        setContentView(R.layout.ui_change_address);
    }

    @Override
    protected void onInitView() {
        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle = intent.getBundleExtra(BUNDLE);
            mPersion = (Persion)bundle.getSerializable(PERSION);
            mDocument = (Document) bundle.getSerializable(DOCUMENT);
        }

        Header header = getHeader();
        if(header != null){
            String name = mPersion.getRealName();
            String title = String.format(getString(R.string.zxdbgjl), name);
            header.titleText.setText(title);

            UserCard userCard = MasterManager.getInstance().getUserCard();
            if(JDZG.equals(userCard.getUserType())){
                header.rightLayout.setVisibility(View.VISIBLE);
                header.rightImageBtn.setVisibility(View.GONE);
                header.rightText.setText(R.string.xz);
                header.rightLayout.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        //变更执行地
                        String l_url = CommUtils.getInstance().createWebUrl(mPersion, mDocument, "/drugs/file/changeApp/main.html?",
                                false, null).toString();
                        ARouter.getInstance().build(RouteUtils.R_WEB_UI)
                                .withString(TITLE, getString(R.string.status_5))
                                .withString(URL, l_url)
                                .navigation();
                    }
                });
            }
        }

        PullToRefreshHelper.initHeader(mChangeAddressLV);
        PullToRefreshHelper.initFooter(mChangeAddressLV);

        mChangeAddressLV.setOnRefreshListener(this);

        mAdapter = new ChangeAddressAdapter(this);
        mChangeAddressLV.setAdapter(mAdapter);
        setFooter();
    }

    @Override
    protected void onInitData() {
        getLeaveList();
    }

    @Override
    public void onPullDownToRefresh(final PullToRefreshBase<ListView> refreshView) {
        if(isNetworkConnected()){
            String persionId = mPersion.getId();
            ChangeAddressManager.getInstance().queryChangeList(persionId, true);
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
            String persionId = mPersion.getId();
            ChangeAddressManager.getInstance().queryChangeList(persionId, false);
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

    private void getLeaveList(){
        if(isNetworkConnected()){
            showLoading();
            String persionId = mPersion.getId();
            ChangeAddressManager.getInstance().queryChangeList(persionId, true);
        }else{
            showToast(R.string.common_network_unavailable);
        }
    }

    private void setFooter() {
        if (mAdapter.getCount() == 0) {
            mChangeAddressLV.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        } else {
            if (ChangeAddressManager.getInstance().isFinish()) {
                mChangeAddressLV.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
            } else {
                mChangeAddressLV.setMode(PullToRefreshBase.Mode.BOTH);
            }
        }
    }
}
