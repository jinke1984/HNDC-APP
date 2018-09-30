package cn.com.jinke.wh_drugcontrol.message;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.manager.PullToRefreshHelper;
import cn.com.jinke.wh_drugcontrol.message.adapter.MessageListAdapter;
import cn.com.jinke.wh_drugcontrol.message.manager.HistoryMessageManager;
import cn.com.jinke.wh_drugcontrol.message.manager.MessageManager;
import cn.com.jinke.wh_drugcontrol.message.model.MessageEntity;
import cn.com.jinke.wh_drugcontrol.utils.RequestHelper;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;

/**
 * Created by jinke on 2017/9/26.
 */

@Route(path = RouteUtils.R_MSG_LIST_UI)
public class MessageListUI extends ProjectBaseUI implements OnRefreshListener2<ListView>{

    @ViewInject(R.id.message_listview)
    private PullToRefreshListView mMessageLV;

    private MessageListAdapter mAdapter;
    private List<MessageEntity> mMessageList = new ArrayList<>();
    private int mType = 0;
    private final static int[] MSG = new int[]{MESSAGE_RECEIVE};

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what){
            case MESSAGE_RECEIVE:
                mMessageLV.onRefreshComplete();
                mAdapter.setData(MessageManager.getMessageList(String.valueOf(mType)));
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
        setContentView(R.layout.ui_message_list);
    }

    @Override
    protected void onInitView() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra(BUNDLE);
            mType = bundle.getInt(TYPE, mType);
        }

        Header header = getHeader();
        if(header != null){
            String title = "";
            switch (mType){
                case MessageEntity.Type.TYPE_TAKE:
                    title = getString(R.string.jgxx);
                    break;
                case MessageEntity.Type.TYPE_WORK:
                    title = getString(R.string.gztx);
                    break;
                case MessageEntity.Type.TYPE_SYSTEM:
                    title = getString(R.string.xtxx);
                    break;
                default:
                    break;
            }
            header.titleText.setText(title);
        }

        PullToRefreshHelper.initHeader(mMessageLV);
        PullToRefreshHelper.initFooter(mMessageLV);
        mMessageLV.setOnRefreshListener(this);

        mAdapter = new MessageListAdapter(this);
        mMessageList = MessageManager.getMessageList(String.valueOf(mType));
        mAdapter.setData(mMessageList);
        mMessageLV.setAdapter(mAdapter);

    }

    @Override
    protected void onInitData() {}


    @Event(value = {R.id.message_listview}, type = android.widget.AdapterView.OnItemClickListener.class)
    private void onItemClick(AdapterView<?> parent, View view, int position, long id){

        try{
            MessageEntity messageEntity = mMessageList.get(position - 1);
            int type = messageEntity.getType();
            if (type == MessageEntity.Type.TYPE_SYSTEM){
                String extraData = messageEntity.getAppNewsList();
                JSONArray array = new JSONArray(extraData);
                JSONObject jsonObj = array.getJSONObject(0);
                String url = jsonObj.optString(MSG_LINK_URL);
                StringBuffer link = new StringBuffer();
                link.append(RequestHelper.getInstance().OUT_CHAT_URL);
                link.append(url);
                ARouter.getInstance().build(RouteUtils.R_WEB_UI)
                        .withString(TITLE, "")
                        .withString(URL, link.toString())
                        .navigation();
            }

        }catch (JSONException e){
            e.printStackTrace();
        }

    }

    @Override
    public void onPullUpToRefresh(final PullToRefreshBase<ListView> refreshView) {}

    @Override
    public void onPullDownToRefresh(final PullToRefreshBase<ListView> refreshView) {

        if (isNetworkConnected()) {
            String SType = String.valueOf(mType);
            int size = MessageManager.getMessageList(SType).size();
            MessageEntity messageEntity = MessageManager.getMessageList(SType).get(size-1);
            String beginTime = messageEntity.getCreateTime();
            int type = mType - 1;
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
}
