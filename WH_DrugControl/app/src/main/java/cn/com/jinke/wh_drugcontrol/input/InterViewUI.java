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
import cn.com.jinke.wh_drugcontrol.input.adapter.TalkAdapter;
import cn.com.jinke.wh_drugcontrol.input.manager.InterViewManager;
import cn.com.jinke.wh_drugcontrol.input.model.Talk;
import cn.com.jinke.wh_drugcontrol.manager.PullToRefreshHelper;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.Persion;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.persion.model.Document;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils.ETYPE;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils.ESHARE;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;

/**
 * Created by jinke on 2017/8/7.
 * 定期访谈
 */

@Route(path = RouteUtils.R_INTERVIEW_UI)
public class InterViewUI extends ProjectBaseUI implements OnRefreshListener2<ListView>, OnItemClickListener {

    @ViewInject(R.id.interview_listview)
    private PullToRefreshListView mInterLV = null;

    private Persion mPersion = null;
    private Document mDocument = null;

    private TalkAdapter mTalkAdapter = null;
    private List<Talk> mTalkList = new ArrayList<>();

    private int[] MSG = new int[]{ADD_CONVERSATION_MSG, TALK_MSG, ACCESS_NET_FAILED, REQ_DELETE_DOC};

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what) {
            case TALK_MSG:
                dismissLoading();
                mInterLV.onRefreshComplete();
                if (msg.arg1 == SUCCESS) {
                    mTalkList = (List<Talk>) msg.obj;
                    mTalkAdapter.setData(mTalkList);
                    mTalkAdapter.notifyDataSetChanged();
                }
                setFooter();
                break;
            case ACCESS_NET_FAILED:
                dismissLoading();
                break;
            case REQ_DELETE_DOC:
            case ADD_CONVERSATION_MSG:
                getTalkList();
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
        setContentView(R.layout.ui_interview);
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
            String name = mPersion.getRealName();
            String title = String.format(getString(R.string.sddqft), name);
            header.titleText.setText(title);
            UserCard userCard = MasterManager.getInstance().getUserCard();
            if(JDZG.equals(userCard.getUserType())){
                header.rightLayout.setVisibility(View.VISIBLE);
                header.rightImageBtn.setVisibility(View.GONE);
                header.rightText.setText(R.string.xz);
                header.rightLayout.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        //addTalk();
                        switchOutNet();
                    }
                });
            }
        }

        PullToRefreshHelper.initHeader(mInterLV);
        PullToRefreshHelper.initFooter(mInterLV);
        mInterLV.setOnRefreshListener(this);
        mInterLV.setOnItemClickListener(this);

        mTalkAdapter = new TalkAdapter(this);
        mInterLV.setAdapter(mTalkAdapter);
        setFooter();
    }

    @Override
    protected void onInitData() {
        getTalkList();
    }

    @Override
    public void onPullDownToRefresh(final PullToRefreshBase<ListView> refreshView) {

        if (isNetworkConnected()) {
            String idCard = mPersion.getId();
            InterViewManager.getInstance().getTalkList(idCard, true);
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
            String idCard = mPersion.getId();
            InterViewManager.getInstance().getTalkList(idCard, false);
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

    private void getTalkList() {

        if (isNetworkConnected()) {
            showLoading();
            String idCard = mPersion.getId();
            InterViewManager.getInstance().getTalkList(idCard, true);
        } else {
            showToast(R.string.common_network_unavailable);
        }
    }

    private void setFooter() {
        if (mTalkAdapter.getCount() == 0) {
            mInterLV.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        } else {
            if (InterViewManager.getInstance().isFinish()) {
                mInterLV.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
            } else {
                mInterLV.setMode(PullToRefreshBase.Mode.BOTH);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Talk talk = mTalkList.get(position - 1);
        Bundle bundle = new Bundle();
        bundle.putSerializable(DOCUMENT, mDocument);
        bundle.putSerializable(OBJECT, talk);
        bundle.putSerializable(PERSION, mPersion);
        ARouter.getInstance().build(RouteUtils.R_INTERVIEW_SHOW_UI).withBundle(BUNDLE, bundle).navigation();
    }

    /**
     * 新增定期访谈
     */
    private void addTalk(){
        int num = 0;
        if(mTalkList.size() > 0){
            num = mTalkList.get(0).getTalkCounts();
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(PERSION, mPersion);
        bundle.putSerializable(DOCUMENT, mDocument);
        bundle.putSerializable(NUM, num);
        ARouter.getInstance().build(RouteUtils.R_TALK_SAVE_UI).withBundle(BUNDLE, bundle).navigation();
    }

    /**
     * 定期访谈新增功能在外网操作
     */
    private void switchOutNetDialog(){
        String content = String.format(getString(R.string.wwts), getString(R.string.xzdqft));
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
            addTalk();
        }
    }
}
