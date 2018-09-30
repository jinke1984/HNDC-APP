package cn.com.jinke.wh_drugcontrol.input;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
import cn.com.jinke.wh_drugcontrol.customview.AppOneDialog;
import cn.com.jinke.wh_drugcontrol.input.adapter.UrineAdapter;
import cn.com.jinke.wh_drugcontrol.input.manager.InputManager;
import cn.com.jinke.wh_drugcontrol.input.model.Urine;
import cn.com.jinke.wh_drugcontrol.manager.PullToRefreshHelper;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.Persion;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.persion.model.Document;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils.ESHARE;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils.ETYPE;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;

/**
 * 尿检列表
 * Created by jinke on 2017/8/4.
 */

@Route(path = RouteUtils.R_URINE_TEST_UI)
public class UrineTestListUI extends ProjectBaseUI implements OnRefreshListener2<ListView>, OnItemClickListener {

    @ViewInject(R.id.urine_listview)
    private PullToRefreshListView mUrineLV = null;

    private Persion mPersion = null;
    private Document mDocument = null;

    private UrineAdapter mUrineAdapter = null;
    private List<Urine> mList = new ArrayList<>();

    private int[] MSG = new int[]{URINE_MSG, ACCESS_NET_FAILED, ADD_URINE_MSG, REQ_DELETE_DOC};

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what) {
            case URINE_MSG:
                dismissLoading();
                mUrineLV.onRefreshComplete();
                if (msg.arg1 == SUCCESS) {
                    mList = (List<Urine>) msg.obj;
                    mUrineAdapter.setData(mList);
                    mUrineAdapter.notifyDataSetChanged();
                }
                setFooter();
                break;
            case ACCESS_NET_FAILED:
                dismissLoading();
                break;
            case REQ_DELETE_DOC:
            case ADD_URINE_MSG:
                getUrineList();
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
        setContentView(R.layout.ui_urine_test);
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
            UserCard userCard = MasterManager.getInstance().getUserCard();
            String name = mPersion.getRealName();
            String title = String.format(getString(R.string.njjl), name);
            header.titleText.setText(title);

            if (JDZG.equals(userCard.getUserType())) {
                header.rightLayout.setVisibility(View.VISIBLE);
                header.rightImageBtn.setVisibility(View.GONE);
                header.rightText.setText(R.string.xz);
                header.rightLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //bundleSendData(RouteUtils.R_ADD_URINE_UI);
                        switchOutNet();
                    }
                });
            }
        }

        PullToRefreshHelper.initHeader(mUrineLV);
        PullToRefreshHelper.initFooter(mUrineLV);
        mUrineLV.setOnRefreshListener(this);
        mUrineLV.setOnItemClickListener(this);

        mUrineAdapter = new UrineAdapter(this);
        mUrineLV.setAdapter(mUrineAdapter);
        setFooter();
    }

    @Override
    protected void onInitData() {
        getUrineList();
    }

    @Override
    public void onPullDownToRefresh(final PullToRefreshBase<ListView> refreshView) {

        if (isNetworkConnected()) {
            String id = mPersion.getId();
            InputManager.getInstance().getUrineList(id, true);
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
            String id = mPersion.getId();
            InputManager.getInstance().getUrineList(id, false);
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

    private void getUrineList() {
        if (isNetworkConnected()) {
            showLoading();
            String id = mPersion.getId();
            InputManager.getInstance().getUrineList(id, true);
        } else {
            showToast(R.string.common_network_unavailable);
        }
    }

    private void setFooter() {
        if (mUrineAdapter.getCount() == 0) {
            mUrineLV.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        } else {
            if (InputManager.getInstance().isFinish()) {
                mUrineLV.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
            } else {
                mUrineLV.setMode(PullToRefreshBase.Mode.BOTH);
            }
        }
    }

    private void bundleSendData(String aARouterPath) {
        int num = 0;
        if(mList.size() > 0){
            num = mList.get(0).getUrineCounts();
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(PERSION, mPersion);
        bundle.putSerializable(DOCUMENT, mDocument);
        bundle.putSerializable(NUM, num);
        ARouter.getInstance().build(aARouterPath).withBundle(BUNDLE, bundle).navigation();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Urine urine = mList.get(position - 1);
        Bundle bundle = new Bundle();
        bundle.putSerializable(OBJECT, urine);
        bundle.putSerializable(PERSION, mPersion);
        bundle.putSerializable(DOCUMENT, mDocument);
        ARouter.getInstance().build(RouteUtils.R_SHOW_URINE_UI).withBundle(BUNDLE, bundle).navigation();
    }

    /**
     * 定期尿检新增功能在外网操作
     */
    private void switchOutNetDialog(){
        String content = String.format(getString(R.string.wwts), getString(R.string.xzdqlj));
        AppOneDialog dialog = new AppOneDialog(this, content, getString(R.string.tishi), true, getString(R.string.qd));
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.one_ok:
                        break;
                    default:
                        break;
                }
            }
        });
        dialog.show();
    }

    /**
     * true 代表在内网，false代表在外网
     */
    private void switchOutNet(){
        boolean result = ShareUtils.getInstance().get(ESHARE.SYS, SWITCH_NET, ETYPE.BOOL);
        if(result){
            switchOutNetDialog();
        }else{
            bundleSendData(RouteUtils.R_ADD_URINE_UI);
        }
    }
}
