package cn.com.jinke.wh_drugcontrol.input;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

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
import cn.com.jinke.wh_drugcontrol.input.adapter.LeaveAdapter;
import cn.com.jinke.wh_drugcontrol.input.manager.LeaveManager;
import cn.com.jinke.wh_drugcontrol.input.model.Leave;
import cn.com.jinke.wh_drugcontrol.manager.PullToRefreshHelper;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.Persion;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.persion.model.Document;
import cn.com.jinke.wh_drugcontrol.utils.CommUtils;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;

/**
 * @author jinke
 * @date 2018/3/6
 * @description 请假列表
 */

@Route(path = RouteUtils.R_LEAVE_UI)
public class LeaveUI extends ProjectBaseUI implements OnRefreshListener2<ListView>, OnItemClickListener {

    @ViewInject(R.id.leave_listview)
    private PullToRefreshListView mLeaveLV;

    private Persion mPersion = null;
    private Document mDocument = null;
    private List<Leave> mLeaveList = new ArrayList<>();
    private LeaveAdapter mLeaveAdapter = null;

    private int[] MSG = new int[]{LEAVE_MSG, ACCESS_NET_FAILED};

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what){
            case LEAVE_MSG:
                dismissLoading();
                mLeaveLV.onRefreshComplete();
                if(msg.arg1 == SUCCESS){
                    mLeaveList = (List<Leave>)msg.obj;
                    mLeaveAdapter.setData(mLeaveList);
                    mLeaveAdapter.notifyDataSetChanged();
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
        setContentView(R.layout.ui_leave);
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
            String title = String.format(getString(R.string.sdqj), name);
            header.titleText.setText(title);

            UserCard userCard = MasterManager.getInstance().getUserCard();
            if(JDZG.equals(userCard.getUserType())){
                header.rightLayout.setVisibility(View.VISIBLE);
                header.rightImageBtn.setVisibility(View.GONE);
                header.rightText.setText(R.string.xz);
                header.rightLayout.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        String l_url = CommUtils.getInstance().createWebUrl(mPersion,mDocument,"/drugs/file/leaveApp/main.html?",
                                false, null).toString();
                        ARouter.getInstance().build(RouteUtils.R_WEB_UI)
                                .withString(TITLE, getString(R.string.xzqj))
                                .withString(URL,l_url)
                                .navigation();
                    }
                });
            }
        }

        PullToRefreshHelper.initHeader(mLeaveLV);
        PullToRefreshHelper.initFooter(mLeaveLV);

        mLeaveLV.setOnRefreshListener(this);
        mLeaveLV.setOnItemClickListener(this);

        mLeaveAdapter = new LeaveAdapter(this);
        mLeaveLV.setAdapter(mLeaveAdapter);
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
            LeaveManager.getInstance().getLeaveList(persionId, 0, true);
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
            LeaveManager.getInstance().getLeaveList(persionId, 0, false);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Leave leave = mLeaveList.get(position-1);
        String leaveId = leave.getId();
        String l_url = CommUtils.getInstance().createWebUrl(mPersion, mDocument, "/drugs/file/leaveDestroyApp/main.html?",
                true, leaveId).toString();
        ARouter.getInstance().build(RouteUtils.R_WEB_UI)
                .withString(TITLE, getString(R.string.status_12))
                .withString(URL, l_url)
                .navigation();
    }

    private void getLeaveList(){
        if(isNetworkConnected()){
            showLoading();
            String persionId = mPersion.getId();
            LeaveManager.getInstance().getLeaveList(persionId, 0, true);
        }else{
            showToast(R.string.common_network_unavailable);
        }
    }

    private void setFooter() {
        if (mLeaveAdapter.getCount() == 0) {
            mLeaveLV.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        } else {
            if (LeaveManager.getInstance().isFinish()) {
                mLeaveLV.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
            } else {
                mLeaveLV.setMode(PullToRefreshBase.Mode.BOTH);
            }
        }
    }
}
