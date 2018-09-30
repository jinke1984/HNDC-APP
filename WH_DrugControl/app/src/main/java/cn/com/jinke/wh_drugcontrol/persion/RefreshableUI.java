package cn.com.jinke.wh_drugcontrol.persion;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.blankj.utilcode.utils.EmptyUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.xutils.view.annotation.ViewInject;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.manager.PullToRefreshHelper;
import cn.com.jinke.wh_drugcontrol.persion.manager.PersionManager;
import cn.com.jinke.wh_drugcontrol.persion.manager.RefreshLoadMoreManager;

/**
 * Created by xkr on 2017/11/3.
 * 有刷新  和 加载更多功能的 ui
 */

public abstract class RefreshableUI extends ProjectBaseUI implements PullToRefreshBase.OnRefreshListener2<ListView>,ILoadData{

    protected RefreshLoadMoreManager mLoadMoreManager = new RefreshLoadMoreManager();

    @ViewInject(R.id.pull_to_refresh_list_view)
    protected PullToRefreshListView mPullToRefreshListView;

    @ViewInject(R.id.default_tv)
    protected ImageView emptyView;

    protected ProjectBaseAdapter mAdapter;

    protected String mIdCard;
    protected  String mName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_pull_to_refresh_with_empty);
        registerMessages(ACCESS_NET_FAILED);
    }

    @Override
    protected void onInitView() {
//        mPullToRefreshListView.getRefreshableView().setEmptyView(emptyView);
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_END);
        PullToRefreshHelper.initFooter(mPullToRefreshListView);
        mPullToRefreshListView.setOnRefreshListener(this);
        mAdapter = createAdapter();
        if(mAdapter != null){
            mPullToRefreshListView.setAdapter(mAdapter);
        }
        Intent intent = getIntent();
        if(intent != null){
            mIdCard = intent.getStringExtra(IDCARD);
            mName = intent.getStringExtra(B_NAME);
        }
        Header header = getHeader();
        if(header != null){
            header.rightLayout.setVisibility(View.GONE);
            header.leftText.setVisibility(View.VISIBLE);
            if(EmptyUtils.isNotEmpty(getTitleText())){
                header.titleText.setText(getTitleText());
            }
        }
    }
    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what){
            case ACCESS_NET_FAILED:
                dismissLoading();
                mLoadMoreManager.resetPageIndexInExceptionStatus();
                mPullToRefreshListView.onRefreshComplete();
                break;
        }
        return super.handleMessage(msg);
    }

    @Override
    protected void onInitData() {
        if(isNetworkConnected()){
            loadData();
        }else{
            showToast(R.string.common_network_unavailable);
        }
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
        if (isNetworkConnected()){
            mLoadMoreManager.changePageIndexInLoadMoreStatus();
            loadData();
        }else{
            showToast(R.string.common_network_unavailable);
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    mPullToRefreshListView.onRefreshComplete();
                }
            });
        }

    }

    /**
     * 创建 listview 的adapter
     * @return
     */
    protected abstract ProjectBaseAdapter createAdapter();

    /**
     * 标题文字
     * @return
     */
    protected String getTitleText(){
        return null;
    }
}
