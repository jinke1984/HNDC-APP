package cn.com.jinke.wh_drugcontrol.me;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ExpandableListView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshExpandableListView;

import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.manager.PullToRefreshHelper;
import cn.com.jinke.wh_drugcontrol.me.adapter.NoticeAdapter;
import cn.com.jinke.wh_drugcontrol.me.manager.MeManager;
import cn.com.jinke.wh_drugcontrol.me.manager.NoticeManager;
import cn.com.jinke.wh_drugcontrol.me.model.NoticeChild;
import cn.com.jinke.wh_drugcontrol.me.model.NoticeParent;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;

/**
 * Created by jinke on 2017/8/1.
 * 通知公告
 */

@Route(path = RouteUtils.R_INFORM_NOTICE_UI)
public class InformNoticeUI extends ProjectBaseUI implements
        OnRefreshListener2<ExpandableListView>, ExpandableListView.OnChildClickListener {

    @ViewInject(R.id.inform_notice_listview)
    private PullToRefreshExpandableListView mListView;

    private NoticeAdapter mNoticeAdapter = null;
    private ArrayList<NoticeParent> mParentList = new ArrayList<>();
    private ArrayList<ArrayList<NoticeChild>> mChildList = new ArrayList<>();

    private int[] MSG = new int[]{LOAD_NOTICE, ACCESS_NET_FAILED};

    @Override
    protected boolean handleMessage(Message msg) {
        mListView.onRefreshComplete();
        switch (msg.what) {
        case LOAD_NOTICE:
            dismissLoading();
            handleServerMessage(msg);
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
        setContentView(R.layout.ui_inform_notice);
        registerMessages(MSG);
    }

    @Override
    protected void onInitView() {

        Header header = getHeader();
        if (header != null) {
            header.rightLayout.setVisibility(View.GONE);
            header.titleText.setText(R.string.tzgg);
        }

        PullToRefreshHelper.initHeader(mListView);
        PullToRefreshHelper.initFooter(mListView);

        mNoticeAdapter = new NoticeAdapter();
        mListView.getRefreshableView().setGroupIndicator(null);
        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        mListView.getRefreshableView().setAdapter(mNoticeAdapter);
        mListView.setOnRefreshListener(this);
        mListView.getRefreshableView().setOnChildClickListener(this);
    }

    @Override
    protected void onInitData() {
        showLoading();
        NoticeManager.getInstance().loadData(true);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
        NoticeManager.getInstance().loadData(false);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
        NoticeManager.getInstance().loadData(true);
    }

    private void handleServerMessage(Message message) {
        if (message.arg1 != SUCCESS) return;
        ArrayList<NoticeParent> parentList = (ArrayList<NoticeParent>) message.obj;
        ArrayList<ArrayList<NoticeChild>> childList = new ArrayList<>();
        if (parentList == null || parentList.size() == 0) return;

        for (NoticeParent parent : parentList) {
            ArrayList<NoticeChild> children = parent.getArticles();
            childList.add(children);
        }
        if (NoticeManager.getInstance().isRefresh()) {
            mParentList.clear();
            mChildList.clear();
        }
        mParentList.addAll(parentList);
        mChildList.addAll(childList);
        mNoticeAdapter.setParent(mParentList);
        mNoticeAdapter.setChild(mChildList);
        mNoticeAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        final NoticeChild noticeChild = mNoticeAdapter.getChild(groupPosition, childPosition);
        ARouter.getInstance().build(RouteUtils.R_WEB_UI)
                .withString(URL, MeManager.getInstance().getJoinArticleUrl(noticeChild))
                .withString(TITLE, noticeChild.getTitle())
                .navigation();
        return true;
    }

}
