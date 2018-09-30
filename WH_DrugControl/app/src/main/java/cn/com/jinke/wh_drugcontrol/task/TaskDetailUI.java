package cn.com.jinke.wh_drugcontrol.task;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.booter.ProjectParams;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.task.adapter.TaskDetailAdapter;
import cn.com.jinke.wh_drugcontrol.task.manager.TaskManager;
import cn.com.jinke.wh_drugcontrol.task.modle.TaskDetail;
import cn.com.jinke.wh_drugcontrol.task.modle.TaskEntity;
import cn.com.jinke.wh_drugcontrol.task.modle.TaskInfo;
import cn.com.jinke.wh_drugcontrol.task.modle.TaskType;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.utils.TaskUtil;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;

/**
 * Created by xkr on 2017/11/10.
 * 任务详情
 */
@Route(path = RouteUtils.R_TASK_DETAIL)
public class TaskDetailUI extends ProjectBaseUI {

    @ViewInject(R.id.iv_task_drawable)
    private ImageView taskDrawableTv;

    /**
     * 任务名称
     */
    @ViewInject(R.id.tv_task_name)
    private TextView taskNameTv;

    /**
     * 创建者
     */
    @ViewInject(R.id.tv_task_creater)
    private TextView taskCreaterTv;

    /**
     * 创建时间
     */
    @ViewInject(R.id.tv_task_create_time)
    private TextView taskCreateTimeTv;

    @ViewInject(R.id.pull_to_refresh_list_view)
    protected PullToRefreshListView mPullToRefreshListView;

    @ViewInject(R.id.default_tv)
    protected ImageView emptyView;

    /**
     * 底部layout
     */
    @ViewInject(R.id.operate_layout)
    private LinearLayout bottomLayout;

    /**
     * 处理
     */
    @ViewInject(R.id.manager_btn)
    private LinearLayout managerBtn;

    /**
     * 审批
     */
    @ViewInject(R.id.approve_btn)
    private LinearLayout approveBtn;

    private TaskEntity mTaskEntity;
    private TaskDetail mLastTaskDetail;
    private TaskDetailAdapter mAdapter;
    private UserCard mUserCard = null;
    private TaskInfo taskInfo = null;
    private int mType = 0;

    /**
     * 审批任务的类型
     */
    private int mSpType = 0;

    private int[] MSG = new int[]{TAST_DETAIL_SUCCESS, UPDATE_DELETE_SUCCESS};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerMessages(MSG);
    }

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what) {
            case TAST_DETAIL_SUCCESS:
                dismissLoading();
                taskInfo = (TaskInfo) msg.obj;
                if (taskInfo == null || taskInfo.getModDelWorkList() == null) {
                    break;
                }
                mLastTaskDetail = taskInfo.getModDelWorkList().get(taskInfo.getModDelWorkList().size() - 1);
                mAdapter.addData(taskInfo.getModDelWorkList());
                mAdapter.notifyDataSetChanged();
                judgeTaskDetailStatus();
                break;
            case UPDATE_DELETE_SUCCESS:
                finish();
                break;
            default:
                break;
        }
        return super.handleMessage(msg);
    }

    @Override
    protected void onInitView() {
        mAdapter = new TaskDetailAdapter(this);
        mPullToRefreshListView.setAdapter(mAdapter);
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED);
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra(BUNDLE);
            mTaskEntity = (TaskEntity) bundle.getSerializable("taskEntity");
        }

        String taskName = String.format(getString(R.string.rwmcm), mTaskEntity.getName());
        String realName = String.format(getString(R.string.xdrym), mTaskEntity.getRealName());
        String time = String.format(getString(R.string.fqsjm), mTaskEntity.getCreateTime());
        mType = TaskUtil.getInstance().getTaskType(mTaskEntity.getTaskType());

        taskNameTv.setText(taskName);
        taskCreaterTv.setText(realName);
        taskCreateTimeTv.setText(time);
        taskDrawableTv.setBackgroundResource(TaskType.getTaskTypeResId(mTaskEntity.getTaskType()));

        Header header = getHeader();
        if (header != null) {
            header.titleText.setText(getString(R.string.task_detail_title));
        }
    }

    private void judgeTaskDetailStatus() {
        String taskType = mTaskEntity.getTaskType();
        boolean isResult = false;
        if (DELETEPROCESS.equals(taskType) || UPDATEPROCESS.equals(taskType)) {
            //修改和删除
            isResult = TaskUtil.getInstance().deleteAndUpdateProcess(mTaskEntity.getTaskDefinitionKey());
            if (isResult) {

                if(bottomLayout.getVisibility() == View.VISIBLE){
                    bottomLayout.setVisibility(View.GONE);
                }

            } else {
                mSpType = 1;
                if (approveBtn.getVisibility() == View.GONE) {
                    approveBtn.setVisibility(View.VISIBLE);
                }

                if (managerBtn.getVisibility() == View.VISIBLE) {
                    managerBtn.setVisibility(View.GONE);
                }
            }
        }else if(HELPPLANPROCESS.equals(taskType)){
            //帮扶救助审批
            isResult = TaskUtil.getInstance().helpPlanProcess(mTaskEntity.getTaskDefinitionKey());
            if(!isResult){
                mSpType = 2;
                if (approveBtn.getVisibility() == View.GONE) {
                    approveBtn.setVisibility(View.VISIBLE);
                }

                if (managerBtn.getVisibility() == View.VISIBLE) {
                    managerBtn.setVisibility(View.GONE);
                }
            }else{
                if(bottomLayout.getVisibility() == View.VISIBLE){
                    bottomLayout.setVisibility(View.GONE);
                }
            }
        }else if(CHANGEPROCESS.equals(taskType)){
            //变更执行地审批
            isResult = TaskUtil.getInstance().changeProcess(mTaskEntity.getTaskDefinitionKey());
            if(!isResult){
                mSpType = 3;
                if (approveBtn.getVisibility() == View.GONE) {
                    approveBtn.setVisibility(View.VISIBLE);
                }

                if (managerBtn.getVisibility() == View.VISIBLE) {
                    managerBtn.setVisibility(View.GONE);
                }
            } else{
                if(bottomLayout.getVisibility() == View.VISIBLE){
                    bottomLayout.setVisibility(View.GONE);
                }
            }
        }else if(REGULARAPPRAISALPROCESS.equals(taskType)){
            //定期评估审批
            isResult = TaskUtil.getInstance().regularAppraisalProcess(mTaskEntity.getTaskDefinitionKey());
            if(!isResult){
                mSpType = 4;
                if (approveBtn.getVisibility() == View.GONE) {
                    approveBtn.setVisibility(View.VISIBLE);
                }

                if (managerBtn.getVisibility() == View.VISIBLE) {
                    managerBtn.setVisibility(View.GONE);
                }
            }else{
                if(bottomLayout.getVisibility() == View.VISIBLE){
                    bottomLayout.setVisibility(View.GONE);
                }
            }
        }else if(RELEASEPROCESS.equals(taskType)){
            //申请解除
            isResult = TaskUtil.getInstance().releaseProcessNew(mTaskEntity.getTaskDefinitionKey());
            if(!isResult){
                mSpType = 6;
                if (approveBtn.getVisibility() == View.GONE) {
                    approveBtn.setVisibility(View.VISIBLE);
                }

                if (managerBtn.getVisibility() == View.VISIBLE) {
                    managerBtn.setVisibility(View.GONE);
                }
            }else{
                if(bottomLayout.getVisibility() == View.VISIBLE){
                    bottomLayout.setVisibility(View.GONE);
                }
            }
        }else if(LEAVEAUDITPROCESS.equals(taskType)){
            //请假审批
            isResult = TaskUtil.getInstance().leaveProcessNew(mTaskEntity.getTaskDefinitionKey());
            if(!isResult){
                mSpType = 5;
                if (approveBtn.getVisibility() == View.GONE) {
                    approveBtn.setVisibility(View.VISIBLE);
                }

                if (managerBtn.getVisibility() == View.VISIBLE) {
                    managerBtn.setVisibility(View.GONE);
                }
            }else{
                if(bottomLayout.getVisibility() == View.VISIBLE){
                    bottomLayout.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    protected void onInitData() {
        showLoading();
        TaskManager.getInstance().getTaskDetail(mType, mTaskEntity);
    }

    @Event(value = {R.id.manager_btn, R.id.approve_btn}, type = View.OnClickListener.class)
    private void onClick(View view) {
        switch (view.getId()) {
        case R.id.manager_btn:
            //处理
            break;
        case R.id.approve_btn:
            //审批
            ApproveUI.navigation(mTaskEntity,taskInfo,mSpType);
            break;
        default:
            break;
        }
    }

}
