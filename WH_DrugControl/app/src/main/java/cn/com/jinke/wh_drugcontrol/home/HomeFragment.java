package cn.com.jinke.wh_drugcontrol.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.com.jinke.wh_drugcontrol.BuildConfig;
import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseFragment;
import cn.com.jinke.wh_drugcontrol.customview.NewGridView;
import cn.com.jinke.wh_drugcontrol.home.adapter.HomeTaskAdapter;
import cn.com.jinke.wh_drugcontrol.home.manager.HomeManager;
import cn.com.jinke.wh_drugcontrol.home.model.HomeTask;
import cn.com.jinke.wh_drugcontrol.home.model.TaskCount;
import cn.com.jinke.wh_drugcontrol.home.model.WorkRemind;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.message.manager.MessageManager;
import cn.com.jinke.wh_drugcontrol.message.model.MessageEntity;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils;
import cn.com.jinke.wh_drugcontrol.utils.DateUtil;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.jpush.android.api.JPushInterface;

/**
 * 我的工作
 * Created by jinke on 2017/7/10.
 */

public class HomeFragment extends ProjectBaseFragment {

    @ViewInject(R.id.home_area_tv)
    private TextView mAreaTV = null;

    @ViewInject(R.id.show_name_tv)
    private TextView mNameTV = null;

    @ViewInject(R.id.mytask_gridview)
    private NewGridView mTaskGV = null;

    @ViewInject(R.id.dqft_number)
    private TextView mDqftNumTV = null;

    @ViewInject(R.id.dqlj_number)
    private TextView mDqljNumTV = null;

    @ViewInject(R.id.dqbg_number)
    private TextView mDqbgNumTV = null;

    @ViewInject(R.id.dqpg_number)
    private TextView mDqpgNumTV = null;

    @ViewInject(R.id.dtxxwh_number)
    private TextView mDtxxNumTV = null;

    @ViewInject(R.id.new_notice_layout)
    private LinearLayout mNewNoticeContainer;

    @ViewInject(R.id.new_notice_tv)
    private TextView mNewNoticeTv;

    @ViewInject(R.id.home_bg_iv)
    private ImageView mHomeBannerIV = null;

    private MessageReceiver mMessageReceiver;

    public static boolean isForeground = false;
    private List<HomeTask> mHomeTaskList = new ArrayList<>();
    private WorkRemind mWorkRemind = null;

    /**
     * 定期谈话未完成条数
     */
    private int mTalkNoFinish = 0;

    /**
     * 定期尿检未完成条数
     */
    private int mUrinalysisNoFinish = 0;

    /**
     * 定期报告未完成条数
     */
    private int mReportNoFinish = 0;

    /**
     * 定期评估未完成条数
     */
    private int mAppraiseNoFinish = 0;

    /**
     * 动态信息未完成条数
     */
    private int mDynamiNoFinish = 0;

    private boolean mIsHide = false;
    private int[] MSG = new int[]{HOME_INDEX, ACCESS_NET_FAILED, HOME_SUCCESS, TASK_COUNT_SUCCESS};

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what) {
            case HOME_INDEX:
                holdBaseData();
                break;
            case ACCESS_NET_FAILED:
                dismissLoading();
                break;
            case HOME_SUCCESS:
                dismissLoading();
                if (msg.arg1 == SUCCESS) {
                    mWorkRemind = (WorkRemind) msg.obj;
                }
                initNumData();
                break;
            case TASK_COUNT_SUCCESS:
                dismissLoading();
                if (msg.arg1 == SUCCESS) {
                    TaskCount taskCount = (TaskCount) msg.obj;
                    initTaskData(taskCount);
                }
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
        registerMessageReceiver();
        holdBaseData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ui_home, container, false);
        x.view().inject(this, view);
        initHeader(view);
        initData();
        return view;
    }

    private void holdBaseData(){
        showLoading();
        HomeManager.getInstance().getWorkRemindData();
        HomeManager.getInstance().findMyTaskCount();
    }

    @Override
    public void onResume() {
        super.onResume();
        isForeground = true;
        if(!mIsHide){
            holdBaseData();
        }
        //解决用户切换的时候  显示的名称不正确
        initData();
    }

    @Override
    public void onStop() {
        isForeground = false;
        super.onStop();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        mIsHide = hidden;
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    private void initData() {
        UserCard mUserCard = MasterManager.getInstance().getUserCard();
        if (mUserCard != null) {
            String orgName = mUserCard.getOrgName();
            String gy = getString(R.string.gys);
            if (gy.equals(orgName)) {
                mAreaTV.setText(gy);
            } else {
                String area = String.format(getString(R.string.qysm), mUserCard.getOrgParentName(), orgName);
                mAreaTV.setText(area);
            }

            String time = DateUtil.getInstance().getTimeOfString(getActivity());
            String nameOrg = String.format(getString(R.string.xmqy), 
                    time, mUserCard.getInfoName(), mUserCard.getRoleName());
            mNameTV.setText(nameOrg);
        }

        if(isChannel()){
            mHomeBannerIV.setBackgroundResource(R.drawable.h_home_bg);
        }

        initTaskData(null);
        initSpecialNotice();
    }


    private boolean isChannel(){
        boolean result = false;
        if(BUILD_TYPE.equals(BuildConfig.BUILD_NAME)){
            result = true;
        }
        return result;
    }


    private void initTaskData(TaskCount taskCount) {
        mHomeTaskList.clear();

        int[] taskNameResIds = {R.string.pfrw, R.string.dtgz};
        int[] taskImageResIds = {R.drawable.dajs, R.drawable.xtyy};
        int[] taskNumbers = new int[taskImageResIds.length];
        Arrays.fill(taskNumbers, 0);
        if (taskCount != null) {
            taskNumbers[0] = taskCount.getAssignment();
            taskNumbers[1] = taskCount.getDynamicAtt();
        }

        for (int inx = 0; inx < taskImageResIds.length; ++inx) {
            HomeTask task = new HomeTask(getString(taskNameResIds[inx]), taskImageResIds[inx], taskNumbers[inx]);
            mHomeTaskList.add(task);
        }

        HomeTaskAdapter mHomeTaskAdapter = new HomeTaskAdapter(getActivity());
        mHomeTaskAdapter.setData(mHomeTaskList);
        mTaskGV.setSelector(new ColorDrawable(Color.TRANSPARENT));
        mTaskGV.setAdapter(mHomeTaskAdapter);
    }

    private void initSpecialNotice() {
        boolean readSpeNotice = ShareUtils.getInstance().get(ShareUtils.ESHARE.SYS, SP_KEY_SPE_NOTICE,
                ShareUtils.ETYPE.BOOL);
        if (readSpeNotice) {
            List<MessageEntity> messages = MessageManager.getMessageList();
            for (MessageEntity message : messages) {
                if (message.getType() == MessageEntity.Type.TYPE_SPECIAL) {
                    setNewNoticeMessage(message);
                    mNewNoticeContainer.setVisibility(View.VISIBLE);
                    return;
                }
            }

        } else {
            mNewNoticeContainer.setVisibility(View.GONE);
        }
    }
    
    @Event(value = {R.id.dqlj_layout, R.id.dqbg_layout, R.id.dqpg_layout, R.id.dtxxwh_layout, 
            R.id.dqft_layout, R.id.new_add_iv, R.id.search_ib, R.id.dangdang_ib,
            R.id.new_notice_tv, R.id.other_iv}, type = View.OnClickListener.class)
    private void onClick(View view) {
        switch (view.getId()) {
            case R.id.dqft_layout:
                if (mTalkNoFinish == 0) {
                    showToast(R.string.gzyjwc);
                    return;
                }
                bundleSendData(WORK_TALK);
                break;
            case R.id.dqlj_layout:
                if (mUrinalysisNoFinish == 0) {
                    showToast(R.string.gzyjwc);
                    return;
                }
                bundleSendData(WORK_URINE);
                break;
            case R.id.dqbg_layout:
                if (mReportNoFinish == 0) {
                    showToast(R.string.gzyjwc);
                    return;
                }
                bundleSendData(WORK_REPORT);
                break;
            case R.id.dqpg_layout:
                if (mAppraiseNoFinish == 0) {
                    showToast(R.string.gzyjwc);
                    return;
                }
                bundleSendData(WORK_EVALUATE);
                break;
            case R.id.dtxxwh_layout:
                // 动态信息维护
                if (mDynamiNoFinish == 0) {
                    showToast(R.string.gzyjwc);
                    return;
                }
                bundleSendData(WORK_DYNAMIC);
                break;
            case R.id.new_add_iv:
                ARouter.getInstance().build(RouteUtils.R_UNCOMPLETED_PERSION_UI).navigation();
                break;
            case R.id.search_ib:
                ARouter.getInstance().build(RouteUtils.R_SEARCH_UI).navigation();
                break;
            case R.id.dangdang_ib:
                ARouter.getInstance().build(RouteUtils.R_INFORM_NOTICE_UI).navigation();
                break;
            case R.id.new_notice_tv:
                ShareUtils.getInstance().commit(ShareUtils.ESHARE.SYS, SP_KEY_SPE_NOTICE, false);
                mNewNoticeContainer.setVisibility(View.GONE);
                ARouter.getInstance().build(RouteUtils.R_WEB_UI)
                        .withString(CONTENT, mNewNoticeTv.getText().toString())
                        .withString(TITLE, getString(R.string.new_notice))
                        .navigation();
                break;
            case R.id.other_iv:
                //关于
                ARouter.getInstance().build(RouteUtils.R_ABOUT).navigation();
                break;
            default:
                break;
        }
    }

    private void bundleSendData(int aType) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(B_TYPE, aType);
        ARouter.getInstance().build(RouteUtils.R_PERSION_WORK_UI).withBundle(BUNDLE, bundle).navigation();
    }

    private void initNumData() {

        if (mWorkRemind != null) {
            mAppraiseNoFinish = mWorkRemind.getAppraiseNoFinish();
            mTalkNoFinish = mWorkRemind.getTalkNoFinish();
            mUrinalysisNoFinish = mWorkRemind.getUrinalysisNoFinish();
            mReportNoFinish = mWorkRemind.getReportNoFinish();
            mDynamiNoFinish = mWorkRemind.getDynamiNoFinish();
        }

        String appraiseNum = String.format(getString(R.string.wd), String.valueOf(mAppraiseNoFinish));
        String talkNum = String.format(getString(R.string.wd), String.valueOf(mTalkNoFinish));
        String urinalysisNum = String.format(getString(R.string.wd), String.valueOf(mUrinalysisNoFinish));
        String reportNum = String.format(getString(R.string.wd), String.valueOf(mReportNoFinish));
        String dynamiNum = String.format(getString(R.string.wd), String.valueOf(mDynamiNoFinish));

        mDqftNumTV.setText(talkNum);
        mDqljNumTV.setText(urinalysisNum);
        mDqbgNumTV.setText(reportNum);
        mDqpgNumTV.setText(appraiseNum);
        mDtxxNumTV.setText(dynamiNum);
    }

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(ACTION_MESSAGE_RECEIVED);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
                    MessageEntity message = (MessageEntity) intent.getSerializableExtra(JPushInterface.EXTRA_MESSAGE);
                    setNewNoticeMessage(message);
                }
            } catch (Exception e) {
            }
        }
    }

    private void setNewNoticeMessage(MessageEntity message) {
        if (message == null || TextUtils.isEmpty(message.getContent())) {
            return;
        }

        mNewNoticeContainer.setVisibility(View.VISIBLE);
        mNewNoticeTv.setText(message.getContent());
    }
}
