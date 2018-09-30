package cn.com.jinke.wh_drugcontrol.persion;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.manager.PullToRefreshHelper;
import cn.com.jinke.wh_drugcontrol.me.model.Persion;
import cn.com.jinke.wh_drugcontrol.persion.adapter.SourceDetailAdapter;
import cn.com.jinke.wh_drugcontrol.persion.manager.SourceManager;
import cn.com.jinke.wh_drugcontrol.persion.model.Source;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;

/**
 * @author jinke
 * @date 2017/10/26
 * @description  管控积分详情页
 */
@Route(path = RouteUtils.R_PERSION_SOURCE_DETAIL_UI)
public class SourceDetailUI extends ProjectBaseUI implements OnRefreshListener2<ListView> {

    @ViewInject(R.id.point_list)
    private PullToRefreshListView mSourceLV = null;

    private Persion mPersion = null;
    private SourceDetailAdapter mSourceAdapter = null;

    private int[] MSG = new int[]{SOURCE_SUC, ACCESS_NET_FAILED};
    private List<Source> mSourceList = new ArrayList<>();

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what){
            case SOURCE_SUC:
                dismissLoading();
                mSourceLV.onRefreshComplete();
                if(msg.arg1 == SUCCESS){
                    mSourceList = (List<Source>)msg.obj;
                    mSourceAdapter.setData(mSourceList);
                    mSourceAdapter.notifyDataSetChanged();
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
        setContentView(R.layout.ui_source_detail);
    }

    @Override
    protected void onInitView() {
        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle = intent.getBundleExtra(BUNDLE);
            mPersion = (Persion) bundle.getSerializable(PERSION);
        }

        Header header = getHeader();
        if(header != null){
            String title = String.format(getString(R.string.sdjf), mPersion.getRealName());
            header.titleText.setText(title);
        }

        PullToRefreshHelper.initHeader(mSourceLV);
        PullToRefreshHelper.initFooter(mSourceLV);
        mSourceLV.setOnRefreshListener(this);

        mSourceAdapter = new SourceDetailAdapter(this);
        mSourceLV.setAdapter(mSourceAdapter);
        setFooter();
    }

    @Override
    protected void onInitData() {
        if (isNetworkConnected()) {
            showLoading();
            String idCard = mPersion.getIdentityCard();
            SourceManager.getInstance().getSourceDetail(true, idCard);
        } else {
            showToast(R.string.common_network_unavailable);
        }
    }

    @Override
    public void onPullDownToRefresh(final PullToRefreshBase<ListView> refreshView) {
        if (isNetworkConnected()) {
            showLoading();
            String idCard = mPersion.getIdentityCard();
            SourceManager.getInstance().getSourceDetail(true, idCard);
        } else {
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
        if (isNetworkConnected()) {
            showLoading();
            String idCard = mPersion.getIdentityCard();
            SourceManager.getInstance().getSourceDetail(false, idCard);
        } else {
            showToast(R.string.common_network_unavailable);
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    refreshView.onRefreshComplete();
                }
            });
        }
    }

    private void setFooter() {
        if (mSourceAdapter.getCount() == 0) {
            mSourceLV.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        } else {
            if (SourceManager.getInstance().isFinish()) {
                mSourceLV.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
            } else {
                mSourceLV.setMode(PullToRefreshBase.Mode.BOTH);
            }
        }
    }
}
