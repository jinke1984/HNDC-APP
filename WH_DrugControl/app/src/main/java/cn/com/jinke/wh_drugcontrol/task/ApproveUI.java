package cn.com.jinke.wh_drugcontrol.task;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import org.xutils.view.annotation.ViewInject;

import java.util.List;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.task.adapter.AuditAdapter;
import cn.com.jinke.wh_drugcontrol.task.manager.ApproveManager;
import cn.com.jinke.wh_drugcontrol.task.modle.TaskEntity;
import cn.com.jinke.wh_drugcontrol.task.modle.TaskInfo;
import cn.com.jinke.wh_drugcontrol.task.modle.TaskRecord;
import cn.com.jinke.wh_drugcontrol.utils.MyTextUtils;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.utils.TaskUtil;
import cn.com.jinke.wh_drugcontrol.word.manager.AuditManager;
import cn.com.jinke.wh_drugcontrol.word.model.Audit;

/**
 * Created by xkr on 2017/11/21.
 * 审批 界面
 */
@Route(path = RouteUtils.R_TASK_APPROVE)
public class ApproveUI extends ProjectBaseUI implements AdapterView.OnItemSelectedListener {

    @ViewInject(R.id.rg_approve)
    private RadioGroup approveGroup;

    @ViewInject(R.id.et_suggest_edit)
    private EditText suggestEdit;

    @ViewInject(R.id.btn_save)
    private Button saveBtn;

    @ViewInject(R.id.personContainer)
    private LinearLayout personContainer;

    @ViewInject(R.id.person_spinner)
    private Spinner personSp;

    private AuditAdapter mAdapter;

    private Audit mSelectedAudit;

    /**
     * 审批状态：1 通过 2 不通过
     */
    private String mDealStatus;
    private TaskEntity mTaskEntity;
    private TaskRecord mTaskRecord;
    private TaskInfo mTaskInfo;

    /**
     * 审批人的id
     */
    private String mAuditId = null;

    private int mType = 0;

    private int[] MSG = new int[]{LOAD_AUDIT_PERSON_LIST, TASK_AUDIT_SUCCESS, GET_MODIFY_DELETE_SUCCESS, LOAD_NEXT_PEOPLE,
            UPDATE_DELETE_SUCCESS, ACCESS_NET_FAILED};

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what) {
            case TASK_AUDIT_SUCCESS:
            case UPDATE_DELETE_SUCCESS:
                dismissLoading();
                final String message = (String) msg.obj;
                showToast(message);
                if (msg.arg1 == SUCCESS) {
                    finish();
                }
                break;
            case GET_MODIFY_DELETE_SUCCESS:
                dismissLoading();
                if (msg.arg1 == SUCCESS) {
                    mTaskRecord = (TaskRecord) msg.obj;
                } else {
                    finish();
                }
                break;
            case LOAD_NEXT_PEOPLE:
            case LOAD_AUDIT_PERSON_LIST:
                dismissLoading();
                if (msg.arg1 == SUCCESS) {
                    List<Audit> auditList = (List<Audit>) msg.obj;
                    mAdapter.setData(auditList);
                    mAdapter.notifyDataSetChanged();
                }
                break;
            case ACCESS_NET_FAILED:
            default:
                dismissLoading();
                break;
        }
        return super.handleMessage(msg);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_approve);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerMessages(MSG);
    }

    @Override
    protected void onInitView() {
        final Intent intent = getIntent();
        mTaskEntity = (TaskEntity) intent.getSerializableExtra(TASKENTITY);
        mTaskInfo = (TaskInfo) intent.getSerializableExtra(TASKINFO);
        mType = intent.getIntExtra(TYPE, mType);

        Header header = getHeader();
        if (header != null) {
            String title = getString(R.string.sp);
            header.titleText.setText(title);
        }

        approveGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_approve_pass:
                        mDealStatus = SP_PASS_CODE;
                        if(personContainer.getVisibility() == View.GONE){
                            personContainer.setVisibility(View.VISIBLE);
                        }
                        break;
                    case R.id.rb_approve_back:
                        mDealStatus = SP_BACK_CODE;
                        if(personContainer.getVisibility() == View.VISIBLE){
                            personContainer.setVisibility(View.GONE);
                        }
                        break;
                    default:
                        break;
                }
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doApprove();
            }
        });

        final String taskType = mTaskEntity.getTaskType();
        if (DELETEPROCESS.equals(taskType) || UPDATEPROCESS.equals(taskType)) {
            showLoading();
            ApproveManager.getInstance().getModifyDelete(mTaskEntity.getProcessInstanceId());
        }

        if (REGULARAPPRAISALPROCESS.equals(taskType) || CHANGEPROCESS.equals(taskType) || LEAVEAUDITPROCESS.equals(taskType)) {
            personContainer.setVisibility(View.VISIBLE);
            showLoading();
            AuditManager.getInstance().getNextPeople(2, mType, null);
        }

        personSp.setVisibility(View.VISIBLE);
        mAdapter = new AuditAdapter(this);
        personSp.setAdapter(mAdapter);
        personSp.setOnItemSelectedListener(this);

        String type = mTaskEntity.getTaskDefinitionKey();
        if(DELETEPROCESS.equals(taskType) || UPDATEPROCESS.equals(taskType)){
            //删除和修改的流程
            if(mTaskInfo.getTimeHourSq() <= 72){
                //如果时间小于72 小时 不需要填写下一步审批人
                personContainer.setVisibility(View.GONE);
            }
        }else if(LEAVEAUDITPROCESS.equals(taskType)){
            //请假的流程
            int day = mTaskEntity.getLeaveDate();
            if(day <= 15 && type.equals(TaskUtil.getInstance().USERTASK3)){
                personContainer.setVisibility(View.GONE);
            }else if(day > 15 && type.equals(TaskUtil.getInstance().USERTASK4)){
                personContainer.setVisibility(View.GONE);
            }
        }else if(REGULARAPPRAISALPROCESS.equals(taskType)){
            //定期评估
            if(type.equals(TaskUtil.getInstance().USERTASK2)){
                personContainer.setVisibility(View.GONE);
            }
        }else if(HELPPLANPROCESS.equals(taskType)){
            //帮教计划
            personContainer.setVisibility(View.GONE);
        }else if(RELEASEPROCESS.equals(taskType)){
            //申请解除
            if(type.equals(TaskUtil.getInstance().USERTASK2)){
                personContainer.setVisibility(View.GONE);
            }
        }else if(CHANGEPROCESS.equals(taskType)){
            //变更执行地
        }
    }

    private void doApprove() {
        final String comment = suggestEdit.getText().toString();

        if (personContainer.getVisibility() == View.VISIBLE) {
            if (mSelectedAudit == null) {
                showToast(R.string.qjxxtxwz);
                return;
            }
        }

        if (!MyTextUtils.isAllNotEmpty(mDealStatus, comment)) {
            showToast(R.string.qjxxtxwz);
            return;
        }

        String entityId = mTaskEntity.getEntityId();
        if (mTaskRecord != null) {
            entityId = mTaskRecord.getId();
        }

        if (mSelectedAudit != null) {
            mAuditId = mSelectedAudit.getUserId();
        }

        // deleteProcess : 删除审批
        // updateProcess : 修改审批
        // changeProcess : 变更执行地审批
        // helpPlanProcess : 帮扶救助审批
        // leaveAuditProcess : 请假审批
        // regularAppraisalProcess : 定期评估审批
        // HigRiskScor : 高危积分审批
        showLoading();
        switch (mTaskEntity.getTaskType()) {
            case UPDATEPROCESS:
                ApproveManager.getInstance().commonApprove(entityId, comment, mDealStatus, mAuditId, ApproveType.type_modify_and_delete);
                break;
            case DELETEPROCESS:
                ApproveManager.getInstance().commonApprove(entityId, comment, mDealStatus, mAuditId, ApproveType.type_modify_and_delete);
                break;
            case HELPPLANPROCESS:
                ApproveManager.getInstance().commonApprove(entityId, comment, mDealStatus, mAuditId, ApproveType.type_help_plan);
                break;
            case REGULARAPPRAISALPROCESS:
                ApproveManager.getInstance().commonApprove(entityId, comment, mDealStatus, mAuditId, ApproveType.type_periodic_assessment);
                break;
            case CHANGEPROCESS:
                ApproveManager.getInstance().commonApprove(entityId, comment, mDealStatus, mAuditId, ApproveType.type_change_execution);
                break;
            case LEAVEAUDITPROCESS:
                ApproveManager.getInstance().commonApprove(entityId, comment, mDealStatus, mAuditId, ApproveType.type_application_for_leave);
                break;
            case RELEASEPROCESS:
                ApproveManager.getInstance().commonApprove(entityId, comment, mDealStatus, mAuditId, ApproveType.type_pescission_of_application);
                break;
            case HIGRISKSCOR:
            default:
                dismissLoading();
                break;
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mSelectedAudit = mAdapter.getItem(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    /**
     * @param entity TaskEntity
     */
    public static void navigation(TaskEntity entity, TaskInfo taskInfo, int type) {
        ARouter.getInstance().build(RouteUtils.R_TASK_APPROVE)
                .withSerializable(TASKENTITY, entity)
                .withSerializable(TASKINFO, taskInfo)
                .withInt(TYPE, type)
                .navigation();
    }

}
