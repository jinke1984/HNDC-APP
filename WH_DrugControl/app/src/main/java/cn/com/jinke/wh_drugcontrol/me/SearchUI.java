package cn.com.jinke.wh_drugcontrol.me;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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
import cn.com.jinke.wh_drugcontrol.utils.ActivityHelper;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;

/**
 * Created by jinke on 2017/7/24.
 */

@Route(path = RouteUtils.R_SEARCH_UI)
public class SearchUI extends ProjectBaseUI implements OnRefreshListener2<ListView>, OnItemClickListener {

    @ViewInject(R.id.search_edittext)
    private EditText mSearchET = null;

    @ViewInject(R.id.search_tv)
    private TextView mSearchTV = null;

    @ViewInject(R.id.search_listview)
    private PullToRefreshListView mSearchLV = null;

    private UserCard mUserCard = null;
    private PersionAdapter mPersionAdapter = null;
    private int[] MSG = new int[]{SEARCH_MSG, ACCESS_NET_FAILED, COLLECTION_MSG_OK_SUC, COLLECTION_MSG_FAIL, COLLECTION_MSG_CANCEL_SUC};
    private List<Persion> mList = new ArrayList<>();
    private String mContent = null;

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what){
            case SEARCH_MSG:
                dismissLoading();
                mSearchLV.onRefreshComplete();
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
                break;
            case COLLECTION_MSG_FAIL:
                showToast(R.string.ccsb);
                break;
            case COLLECTION_MSG_CANCEL_SUC:
                showToast(R.string.qxsc);
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
        setContentView(R.layout.ui_search);
    }

    @Override
    protected void onInitView() {

        mUserCard = MasterManager.getInstance().getUserCard();

        SpannableString hint_string = new SpannableString(getString(R.string.qsrsfzhhmz));
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(16, true);
        hint_string.setSpan(ass, 0, hint_string.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mSearchET.setHint(new SpannedString(hint_string));

        PullToRefreshHelper.initHeader(mSearchLV);
        PullToRefreshHelper.initFooter(mSearchLV);
        mSearchLV.setOnRefreshListener(this);
        mSearchLV.setOnItemClickListener(this);
    }

    @Override
    protected void onInitData() {

        mPersionAdapter = new PersionAdapter(this);
        mSearchLV.setAdapter(mPersionAdapter);
        setFooter();
    }

    @Event(value = {R.id.back_btn, R.id.search_tv}, type = View.OnClickListener.class)
    private void onClick(View view){

        switch (view.getId()){
            case R.id.back_btn:
                finish();
                break;
            case R.id.search_tv:
                mContent = mSearchET.getText().toString().trim();
                if (TextUtils.isEmpty(mContent)) {
                    showToast(R.string.qsrycxddx);
                    return;
                }

                if(!isNetworkConnected()){
                    showToast(R.string.common_network_unavailable);
                    return;
                }

                showLoading();
                if(mList.size() > 0){
                    mList.clear();
                }
                ActivityHelper.hideSoftInput(this, mSearchET);
                MeManager.getInstance().search(true, mUserCard.getUserId(), mContent,
                        mUserCard.getUserType(), mUserCard.getOrgId(), NO_COLLECTION);
                break;
            default:
                break;
        }
    }

    @Override
    public void onPullDownToRefresh(final PullToRefreshBase<ListView> refreshView) {
        if (isNetworkConnected()){
             MeManager.getInstance().search(true, mUserCard.getUserId(), mContent,
                     mUserCard.getUserType(), mUserCard.getOrgId(), NO_COLLECTION);
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
        if (isNetworkConnected()){
            MeManager.getInstance().search(false, mUserCard.getUserId(), mContent,
                    mUserCard.getUserType(), mUserCard.getOrgId(), NO_COLLECTION);
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
        Bundle bundle = new Bundle();
        Persion persion = mList.get(position-1);
        bundle.putSerializable(PERSION, persion);
        ARouter.getInstance().build(RouteUtils.R_PERSION_PERSIONDETAIL_UI).withBundle(BUNDLE, bundle).navigation();
    }

    private void setFooter() {
        if (mPersionAdapter.getCount() == 0) {
            mSearchLV.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        } else {
            if (MeManager.getInstance().isFinish()) {
                mSearchLV.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
            } else {
                mSearchLV.setMode(PullToRefreshBase.Mode.BOTH);
            }
        }
    }
}
