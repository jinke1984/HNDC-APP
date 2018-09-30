package cn.com.jinke.wh_drugcontrol.home;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.alibaba.android.arouter.launcher.ARouter;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseFragment;
import cn.com.jinke.wh_drugcontrol.chat.manager.ChatManager;
import cn.com.jinke.wh_drugcontrol.customview.AppOneDialog;
import cn.com.jinke.wh_drugcontrol.home.adapter.MessageMainAdapter;
import cn.com.jinke.wh_drugcontrol.message.StudyListUI;
import cn.com.jinke.wh_drugcontrol.message.manager.HistoryMessageManager;
import cn.com.jinke.wh_drugcontrol.message.manager.MessageManager;
import cn.com.jinke.wh_drugcontrol.message.model.MessageEntity;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils.ESHARE;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils.ETYPE;

/**
 * Created by jinke on 2017/7/10.
 */

public class MessageFragment extends ProjectBaseFragment implements OnItemClickListener{

    @ViewInject(R.id.message_listview)
    private ListView mMsgListView;
    private MessageMainAdapter mAdapter;

    private List<MessageEntity> mMsgList = new ArrayList<>();
    private final static int[] MSG = new int[]{MESSAGE_RECEIVE, MESSAGE_INDEX};

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what){
            case MESSAGE_RECEIVE:
                mMsgList.clear();
                mMsgList.addAll(MessageManager.getMessageList());
                mAdapter.setData(mMsgList);
                mAdapter.notifyDataSetChanged();
                break;
            case MESSAGE_INDEX:
                mMsgList.clear();
                mMsgList.addAll(MessageManager.getMessageList());
                mAdapter.setData(mMsgList);
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ui_message, container, false);
        x.view().inject(this, view);
        initHeader(view);
        initHeadView();
        initView();
        initData();
        return view;
    }

    private void initHeadView(){
        Header header = getHeader();
        if(header != null){
            header.leftLayout.setVisibility(View.GONE);
            header.rightLayout.setVisibility(View.GONE);
            header.titleText.setText(R.string.wdxx);
        }
    }

    private void initView(){
        mAdapter = new MessageMainAdapter(getActivity());
        mMsgList = MessageManager.getMessageList();
        mAdapter.setData(mMsgList);
        mMsgListView.setAdapter(mAdapter);
        mMsgListView.setOnItemClickListener(this);
    }

    private void initData(){
        boolean netstate = ShareUtils.getInstance().get(ESHARE.SYS, SWITCH_NET, ETYPE.BOOL);
        List<MessageEntity> list = MessageManager.getMessageList();
        if(list != null){
            int size = list.size();
            //TODO 消息同步功能必须在外网才可以同步
            if(size == 0 && !netstate){
                HistoryMessageManager.getInstance().getHistoryMessage();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MessageEntity message = mMsgList.get(position);
        if(MessageEntity.Type.TYPE_CHAT == message.getType() || MessageEntity.Type.TYPE_PIC == message.getType()
                || MessageEntity.Type.TYPE_VOICE == message.getType()){
            //聊天消息处理逻辑
            ChatManager.getInstance().processChatMessageNoticeClick(message, true);
        }else if(MessageEntity.Type.TYPE_GROUP == message.getType()){
            ChatManager.getInstance().processChatMessageNoticeClick(message, false);
        }else if(MessageEntity.Type.TYPE_STUDY == message.getType()){
            //学习园地处理逻辑 注意：这个功能只能在外网使用哈！
            switchOutNet();
        }else{
            //其他消息处理逻辑
            bundleSendData(RouteUtils.R_MSG_LIST_UI, message);
        }
    }

    private void bundleSendData(String aARouterPath, MessageEntity message) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(TYPE, message.getType());
        ARouter.getInstance().build(aARouterPath).withBundle(BUNDLE, bundle).navigation();
    }

    /**
     * 学习园地功能在外网操作
     */
    private void switchOutNetDialog(){
        String content = String.format(getString(R.string.wwts), getString(R.string.xxyd));
        AppOneDialog dialog = new AppOneDialog(getActivity(), content, getString(R.string.tishi), true,
                getString(R.string.qd));
        dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
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
            StudyListUI.startActivity();
        }
    }
}
