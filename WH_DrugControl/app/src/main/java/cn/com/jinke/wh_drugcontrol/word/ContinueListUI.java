package cn.com.jinke.wh_drugcontrol.word;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.xutils.view.annotation.ViewInject;

import java.util.List;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.me.model.Persion;
import cn.com.jinke.wh_drugcontrol.persion.model.Document;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.word.adapter.ContinueAdapter;
import cn.com.jinke.wh_drugcontrol.word.manager.ContinueManager;
import cn.com.jinke.wh_drugcontrol.word.model.Continue;

/**
 * 继续执行列表
 * Created by jinke on 2017/8/28.
 */

@Route(path = RouteUtils.R_WORD_CONTINUE_LIST_UI)
public class ContinueListUI extends ProjectBaseUI implements
        AdapterView.OnItemClickListener, PullToRefreshBase.OnRefreshListener2 {

    @ViewInject(R.id.violate_listview)
    private PullToRefreshListView mPullListView;

    private Persion mPersion;
    private Document mDocument;

    private ContinueAdapter adapter;

    private int[] MSG = new int[]{REQ_DELETE_DOC, LOAD_DRUGS_CONTINUE_LIST, ADD_GO_ON_DOC, ACCESS_NET_FAILED};

    @SuppressWarnings("unchecked")
    @Override
    protected boolean handleMessage(Message msg) {
        mPullListView.onRefreshComplete();
        switch (msg.what) {
        case LOAD_DRUGS_CONTINUE_LIST:
            dismissLoading();
            if (msg.arg1 == SUCCESS) {
                List<Continue> mData = (List<Continue>) msg.obj;
                if (ContinueManager.getInstance().isRefresh()) {
                    adapter.setData(mData);
                } else {
                    adapter.addData(mData);
                }
                adapter.notifyDataSetChanged();
            }
            break;
        case REQ_DELETE_DOC:
        case ADD_GO_ON_DOC:
            getData(true);
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
        setContentView(R.layout.ui_violate_list);
        registerMessages(MSG);
    }

    @Override
    protected void onInitView() {

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra(BUNDLE);
            mPersion = (Persion) bundle.getSerializable(PERSION);
            mDocument = (Document) bundle.getSerializable(DOCUMENT);
        }

        Header header = getHeader();
        if (header != null) {
            String title = String.format(getString(R.string.jxzx), mPersion.getRealName());
            header.titleText.setText(title);
            header.rightLayout.setVisibility(View.VISIBLE);
            header.rightImageBtn.setVisibility(View.GONE);
            header.rightText.setText(R.string.xz);
            header.rightLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bundleSendData(RouteUtils.R_WORD_SUSPENDED_LIST_UI);
                }
            });
        }

        adapter = new ContinueAdapter(this);
        mPullListView.setMode(PullToRefreshBase.Mode.BOTH);
        mPullListView.setAdapter(adapter);
        mPullListView.setOnItemClickListener(this);
        mPullListView.setOnRefreshListener(this);
    }

    @Override
    protected void onInitData() {
        getData(true);
    }

    public void getData(boolean isRefresh) {
        if (isNetworkConnected()) {
            showLoading();
            ContinueManager.getInstance().loadContinueDataList(mDocument.getId(), isRefresh);
        } else {
            mPullListView.onRefreshComplete();
            showToast(R.string.common_network_unavailable);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        bundleSendData(RouteUtils.R_WORD_GO_ON_UI, adapter.getItem(position - 1));
    }

    private void bundleSendData(String aARouterPath) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(PERSION, mPersion);
        bundle.putSerializable(DOCUMENT, mDocument);
        bundle.putBoolean(FROMCONTINUE, true);
        ARouter.getInstance().build(aARouterPath).withBundle(BUNDLE, bundle).navigation();
    }

    private void bundleSendData(String aARouterPath, Continue aContinue) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(PERSION, mPersion);
        bundle.putSerializable(DOCUMENT, mDocument);
        bundle.putSerializable(CONTINUE, aContinue);
        ARouter.getInstance().build(aARouterPath).withBundle(BUNDLE, bundle).navigation();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        ContinueManager.getInstance().loadContinueDataList(mDocument.getId(), true);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        ContinueManager.getInstance().loadContinueDataList(mDocument.getId(), false);
    }

}
