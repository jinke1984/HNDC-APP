package cn.com.jinke.wh_drugcontrol.message;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
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
import cn.com.jinke.wh_drugcontrol.manager.PullToRefreshHelper;
import cn.com.jinke.wh_drugcontrol.message.adapter.StudyMsgListAdapter;
import cn.com.jinke.wh_drugcontrol.message.manager.HistoryMessageManager;
import cn.com.jinke.wh_drugcontrol.message.manager.MessageManager;
import cn.com.jinke.wh_drugcontrol.message.model.MessageEntity;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;

/**
 * @author jinke
 * @date 2018/4/12
 * @description  学习园地数据展现界面
 */

@Route(path = RouteUtils.R_STUDY_LIST_UI)
public class StudyListUI extends ProjectBaseUI implements OnRefreshListener2<ListView> {

    @ViewInject(R.id.study_listview)
    private PullToRefreshListView mStudyLV = null;

    private StudyMsgListAdapter mAdapter = null;
    private List<MessageEntity> mMessageList = new ArrayList<>();

    private final static int[] MSG = new int[]{MESSAGE_RECEIVE};

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what){
            case MESSAGE_RECEIVE:
                mStudyLV.onRefreshComplete();
                mAdapter.setData(MessageManager.getMessageList(String.valueOf(MessageEntity.Type.TYPE_STUDY)));
                mAdapter.notifyDataSetChanged();
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
        setContentView(R.layout.ui_study_list);
    }

    @Override
    protected void onInitView() {
        Header header = getHeader();
        if(header != null){
            header.titleText.setText(R.string.xxyd);
            header.rightLayout.setVisibility(View.GONE);
        }

        PullToRefreshHelper.initHeader(mStudyLV);
        PullToRefreshHelper.initFooter(mStudyLV);
        mStudyLV.setOnRefreshListener(this);

        mAdapter = new StudyMsgListAdapter(this);
        mMessageList = MessageManager.getMessageList(String.valueOf(MessageEntity.Type.TYPE_STUDY));
        mAdapter.setData(mMessageList);
        mStudyLV.setAdapter(mAdapter);
    }

    @Override
    protected void onInitData() {}

    @Override
    public void onPullDownToRefresh(final PullToRefreshBase<ListView> refreshView) {
        if (isNetworkConnected()) {
            int msgType = MessageEntity.Type.TYPE_STUDY;
            int size = MessageManager.getMessageList(String.valueOf(msgType)).size();
            MessageEntity messageEntity = MessageManager.getMessageList(String.valueOf(msgType)).get(size-1);
            String beginTime = messageEntity.getCreateTime();
            int type = msgType - 1;
            HistoryMessageManager.getInstance().getHistoryMessageDetail(type, beginTime, false);
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
    public void onPullUpToRefresh(final PullToRefreshBase<ListView> refreshView) {}

    public final static void startActivity(){
        ARouter.getInstance().build(RouteUtils.R_STUDY_LIST_UI).navigation();
    }
}
