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
import cn.com.jinke.wh_drugcontrol.word.adapter.ViolateAgreeAdapter;
import cn.com.jinke.wh_drugcontrol.word.manager.ViolateAgreeManager;
import cn.com.jinke.wh_drugcontrol.word.model.ViolateAgreement;

/**
 * 违反协议列表
 * Created by jinke on 2017/8/28.
 */

@Route(path = RouteUtils.R_WORD_VIOLATEAGREELIST_UI)
public class ViolateAgreeListUI extends ProjectBaseUI implements
        PullToRefreshBase.OnRefreshListener2, AdapterView.OnItemClickListener {

    @ViewInject(R.id.violate_listview)
    private PullToRefreshListView mPullListView;

    private Persion mPersion;
    private Document mDocument;

    private ViolateAgreeAdapter adapter;

    private int[] MSG = new int[]{REQ_DELETE_DOC, LOAD_VIOLATION_AGREEMENT_LIST, ADD_VIOLATE_AGREE_DOC, ACCESS_NET_FAILED};

    @SuppressWarnings("unchecked")
    @Override
    protected boolean handleMessage(Message msg) {
        mPullListView.onRefreshComplete();
        switch (msg.what) {
        case LOAD_VIOLATION_AGREEMENT_LIST:
            dismissLoading();
            if (msg.arg1 == SUCCESS) {
                List<ViolateAgreement> mData = (List<ViolateAgreement>) msg.obj;
                disableHeaderAdd((mData != null) && (mData.size() >= 3));
                adapter.setData(mData);
                adapter.notifyDataSetChanged();
            }
            break;
        case REQ_DELETE_DOC:
        case ADD_VIOLATE_AGREE_DOC:
            getData();
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
            String title = String.format(getString(R.string.wfxyjl), mPersion.getRealName());
            header.titleText.setText(title);
            header.rightLayout.setVisibility(View.VISIBLE);
            header.rightImageBtn.setVisibility(View.GONE);
            header.rightText.setText(R.string.xz);
            header.rightLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bundleSendData(RouteUtils.R_WORD_VIOLATEAGREE_UI, null);
                }
            });
        }

        adapter = new ViolateAgreeAdapter(this);
        mPullListView.setAdapter(adapter);
        mPullListView.setOnRefreshListener(this);
        mPullListView.setOnItemClickListener(this);
    }

    @Override
    protected void onInitData() {
        getData();
    }

    public void getData() {
        if (isNetworkConnected()) {
            showLoading();
            ViolateAgreeManager.getInstance().loadDataList(mDocument.getId());
        } else {
            showToast(R.string.common_network_unavailable);
        }
    }

    private void disableHeaderAdd(boolean gone) {
        Header header = getHeader();
        if (header != null) {
            if (gone) {
                header.rightLayout.setVisibility(View.GONE);
            } else {
                header.rightLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    private void bundleSendData(String aARouterPath, ViolateAgreement agreement) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(PERSION, mPersion);
        bundle.putSerializable(DOCUMENT, mDocument);
        bundle.putSerializable(AGREEMENT, agreement);
        ARouter.getInstance().build(aARouterPath).withBundle(BUNDLE, bundle).navigation();
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        ViolateAgreeManager.getInstance().loadDataList(mDocument.getId());
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        bundleSendData(RouteUtils.R_WORD_VIOLATEAGREE_UI, adapter.getItem(position - 1));
    }
}
