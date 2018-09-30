package cn.com.jinke.wh_drugcontrol.me;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.widget.ListView;
import android.os.Handler;

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
import cn.com.jinke.wh_drugcontrol.manager.PullToRefreshHelper;
import cn.com.jinke.wh_drugcontrol.me.adapter.SigninAdapter;
import cn.com.jinke.wh_drugcontrol.me.manager.SignInManager;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;

/**
 * @author jinke
 * @date 2018/9/10
 * @description
 */
@Route(path = RouteUtils.R_ME_SIGNIN_LIST)
public class SigninListUI extends ProjectBaseUI implements OnRefreshListener2<ListView> {

    @ViewInject(R.id.sign_listview)
    private PullToRefreshListView mSignLV;

    private SigninAdapter mSigninAdapter = null;

    private String mKsrq;
    private String mJsrq;

    private List<String> mList = new ArrayList<>();

    private int[] MSG = new int[]{ACCESS_NET_FAILED, SIGN_LIST_SUC};

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what){
            case SIGN_LIST_SUC:
                dismissLoading();
                mSignLV.onRefreshComplete();
                if(msg.arg1 == SUCCESS){
                    mList = (List<String>)msg.obj;
                    mSigninAdapter.setData(mList);
                    mSigninAdapter.notifyDataSetChanged();
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
        setContentView(R.layout.ui_sign_list);
    }

    @Override
    protected void onInitView() {
        Header header = getHeader();
        if(header != null){
            header.titleText.setText(R.string.qdjgcx);
        }

        Intent intent = getIntent();
        if(intent != null){
            mKsrq = intent.getStringExtra(STARTDATE);
            mJsrq = intent.getStringExtra(ENDDATE);
        }

        PullToRefreshHelper.initHeader(mSignLV);
        PullToRefreshHelper.initFooter(mSignLV);
        mSignLV.setOnRefreshListener(this);

        mSigninAdapter = new SigninAdapter(this);
        mSignLV.setAdapter(mSigninAdapter);
        setFooter();
    }

    @Override
    protected void onInitData() {
        showLoading();
        SignInManager.getInstance().querySignin(true, mKsrq, mJsrq);
    }

    /**
     * 界面调整
     * @param beginTime  开始时间
     * @param endTime    结束时间
     */
    public final static void startActivity(String beginTime, String endTime){
        ARouter.getInstance().build(RouteUtils.R_ME_SIGNIN_LIST)
                .withString(STARTDATE, beginTime)
                .withString(ENDDATE, endTime)
                .navigation();
    }

    private void setFooter() {
        if (mSigninAdapter.getCount() == 0) {
            mSignLV.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        } else {
            if (SignInManager.getInstance().isFinish()) {
                mSignLV.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
            } else {
                mSignLV.setMode(PullToRefreshBase.Mode.BOTH);
            }
        }
    }

    @Override
    public void onPullUpToRefresh(final PullToRefreshBase<ListView> refreshView) {
        if (isNetworkConnected()){
            SignInManager.getInstance().querySignin(false, mKsrq, mJsrq);
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
    public void onPullDownToRefresh(final PullToRefreshBase<ListView> refreshView) {
        if (isNetworkConnected()){
            SignInManager.getInstance().querySignin(true, mKsrq, mJsrq);
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
}
