package cn.com.jinke.wh_drugcontrol.task;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.task.adapter.AuditAdapter;
import cn.com.jinke.wh_drugcontrol.task.manager.TaskManager;
import cn.com.jinke.wh_drugcontrol.utils.MyTextUtils;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.word.manager.AuditManager;
import cn.com.jinke.wh_drugcontrol.word.model.Audit;

/**
 * @author jinke
 * @date 2017/11/14
 * @description
 */

@Route(path = RouteUtils.R_TASK_STARTTASK_UI)
public class StartTaskUI extends ProjectBaseUI implements AdapterView.OnItemSelectedListener {

    /**
     * 操作类型 1档案修改 2档案删除
     */
    private int mType;
    private String mDocId; // 文档id
    private String mPersonId; // 吸毒人员id
    private String mSourceId; // 资源id
    private int mSourceType; // 资源类型

    @ViewInject(R.id.task_reason_tv)
    private TextView mReasonLabelTV;

    @ViewInject(R.id.task_reason_et)
    private EditText mReasonET = null;

    @ViewInject(R.id.task_remask_et)
    private EditText mRemarkET = null;

    @ViewInject(R.id.persion_spinner)
    private Spinner mPersionSP = null;

    private AuditAdapter mAdapter;

    private Audit mSelectedAudit;

    private int[] MSG = new int[]{UPDATE_OR_DETELE_SUCCESS, LOAD_AUDIT_PERSON_LIST, ACCESS_NET_FAILED, LOAD_NEXT_PEOPLE};

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what) {
        case LOAD_NEXT_PEOPLE:
            dismissLoading();
            if (msg.arg1 == SUCCESS) {
                List<Audit> auditList = (List<Audit>) msg.obj;
                mAdapter.setData(auditList);
                mAdapter.notifyDataSetChanged();
            }
            break;
        case UPDATE_OR_DETELE_SUCCESS:
            final String message = (String) msg.obj;
            showToast(message);
            if (msg.arg1 == SUCCESS) {
                finish();
            }
            break;
        case ACCESS_NET_FAILED:
            break;
        default:
            break;
        }
        return super.handleMessage(msg);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_start_task);
        registerMessages(MSG);
    }

    @Override
    protected void onInitView() {

        Intent intent = getIntent();
        if (intent != null) {
            mType = intent.getIntExtra(TYPE, T_DELETE);
            mDocId = intent.getStringExtra(DOCID);
            mPersonId = intent.getStringExtra(PERSONID);
            mSourceId = intent.getStringExtra(SOURCEID);
            mSourceType = intent.getIntExtra(DOCTYPE, DOC_TYPE_DECISION);

            if (mType == T_MODIFY) {
                mReasonLabelTV.setText(R.string.xgsx);
            } else {
                mReasonLabelTV.setText(R.string.scyy);
            }
        }

        Header header = getHeader();
        if (header != null) {
            String title = getString(R.string.sqxg);
            if (mType == T_DELETE) {
                title = getString(R.string.sqsc);
            }
            header.titleText.setText(title);
            header.rightLayout.setVisibility(View.GONE);
            header.rightImageBtn.setVisibility(View.GONE);
        }

        mAdapter = new AuditAdapter(this);
        mPersionSP.setAdapter(mAdapter);
        mPersionSP.setOnItemSelectedListener(this);
    }

    @Override
    protected void onInitData() {
        getAuditList();
    }

    @Event(value = {R.id.start_btn, R.id.persion_spinner}, type = View.OnClickListener.class)
    private void onClick(View view) {
        switch (view.getId()) {
        case R.id.start_btn:
            uploadData();
            break;
        case R.id.persion_spinner:
            if (mAdapter.isEmpty()) {
                getAuditList();
            }
            break;
        default:
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

    private void getAuditList() {
        if (isNetworkConnected()) {
            showLoading();
            //AuditManager.getInstance().loadAuditList();
            AuditManager.getInstance().getNextPeople(1, 1, null);
        } else {
            showToast(R.string.common_network_unavailable);
        }
    }

    /**
     * 提交申请
     */
    private void uploadData() {
        final String content = mReasonET.getText().toString().trim();
        final String remark = mRemarkET.getText().toString().trim();

        if (mSelectedAudit == null || !MyTextUtils.isAllNotEmpty(content, remark)) {
            showToast(R.string.qjxxtxwz);
            return;
        }

        TaskManager.getInstance().beginUpdateOrDelete(mDocId, mPersonId, mSourceId, remark, content,
                mSelectedAudit.getUserId(), mSourceType, mType);
    }

    /**
     * 导航到发起任务界面
     *
     * @param alterType 1档案修改 2档案删除
     * @param docId     档案id
     * @param personId  吸毒人员id
     * @param sourceId  资源id
     * @param docType   档案资源类型（如：1=决定书）
     */
    public static void navigation(int alterType, String docId, String personId, String sourceId, int docType) {
        ARouter.getInstance()
                .build(RouteUtils.R_TASK_STARTTASK_UI)
                .withInt(TYPE, alterType)
                .withString(DOCID, docId)
                .withString(PERSONID, personId)
                .withString(SOURCEID, sourceId)
                .withInt(DOCTYPE, docType)
                .navigation();
    }

}
