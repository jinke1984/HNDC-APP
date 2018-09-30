package cn.com.jinke.wh_drugcontrol.word;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.customview.CustomEditText;
import cn.com.jinke.wh_drugcontrol.input.manager.PhotoManager;
import cn.com.jinke.wh_drugcontrol.persion.model.Document;
import cn.com.jinke.wh_drugcontrol.persion.model.DocumentStatus;
import cn.com.jinke.wh_drugcontrol.task.manager.TaskManager;
import cn.com.jinke.wh_drugcontrol.utils.BirthdayPicker;
import cn.com.jinke.wh_drugcontrol.utils.DateUtil;
import cn.com.jinke.wh_drugcontrol.utils.MyTextUtils;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.utils.SingleChoicePicker;
import cn.com.jinke.wh_drugcontrol.word.manager.AuditManager;
import cn.com.jinke.wh_drugcontrol.word.manager.PlanManager;
import cn.com.jinke.wh_drugcontrol.word.model.Audit;
import cn.com.jinke.wh_drugcontrol.word.model.Plan;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.OptionPicker;

/**
 * 计划书管理
 * Created by jinke on 2017/8/24.
 */

@Route(path = RouteUtils.R_WORD_PLAN_UI)
public class PlanUI extends BaseWordUI {

    @ViewInject(R.id.gzz_et)
    private CustomEditText mGzzEt; // 工作站

    @ViewInject(R.id.qzrq_et)
    private CustomEditText mQzrqEt; // 签字日期

    @ViewInject(R.id.gzrq_et)
    private CustomEditText mGzrqEt; // 盖章日期

    @ViewInject(R.id.bj_detail_et)
    private EditText mBjjhEt; // 帮教计划

    @ViewInject(R.id.scan_btn)
    private LinearLayout mScanBtn;

    @ViewInject(R.id.bjspr_et)
    private CustomEditText mBjjhsprEt; // 帮教计划审批人

    @ViewInject(R.id.preview_iv)
    private ImageView mPreviewIv; // 预览图

    @ViewInject(R.id.bottom_actions_container)
    private LinearLayout mBottomActionsContainer;
    @ViewInject(R.id.alter_layout)
    private LinearLayout mAlterLayout;
    @ViewInject(R.id.delete_layout)
    private LinearLayout mDeleteLayout;

    private Audit mAudit;

    private ArrayList<Audit> mAuditPersonList;
    private ArrayList<String> mAuditStrList;

    private int alterType = T_MODIFY; // 1档案修改 2档案删除

    private Plan object = new Plan();

    private int[] MSG = new int[]{JUDGE_CREATETIME, LOAD_HELP_PLAN, ADD_PLAN_DOC, LOAD_AUDIT_PERSON_LIST, LOAD_NEXT_PEOPLE};

    @SuppressWarnings("unchecked")
    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what) {
        case LOAD_HELP_PLAN:
            dismissLoading();
            if (msg.arg1 == SUCCESS) {
                finishWhenLoadErr = false;
                object = (Plan) msg.obj;

                if (object == null) {
                    object = new Plan();
                }

                mAttachmentUrl = object.getFileAdd();
                updateUi(object);
                dealDocumentStatus(TextUtils.isEmpty(object.getId()));

            } else {
                finishActivityWithToast();
            }
            break;
        case ADD_PLAN_DOC:
            handleUploadMessage(msg);
            break;
        case LOAD_NEXT_PEOPLE:
            dismissLoading();
            if (msg.arg1 == SUCCESS) {
                mAuditPersonList = (ArrayList<Audit>) msg.obj;
                if (mAuditPersonList != null && mAuditPersonList.size() > 0) {
                    mAuditStrList = new ArrayList<>(mAuditPersonList.size());
                    for (Audit audit : mAuditPersonList) {
                        mAuditStrList.add("" + audit.getUserName());
                    }
                }
            }
            break;
        case JUDGE_CREATETIME: // 判断文档创建时间
            dismissLoading();
            if (msg.arg1 == SUCCESS) {
                showLoading();
                if (alterType == T_MODIFY) {
                    saveData2Server();
                } else {
                    PlanManager.getInstance().deleteDoc(object);
                }
            } else {
                final String message = (String) msg.obj;
                showAlterMessageDialog(message, alterType, mDocument.getId(),
                        mPersion.getId(), object.getId(), DOC_TYPE_PLAN);
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
        setContentView(R.layout.ui_word_plan);
        registerMessages(MSG);
    }

    @Override
    protected ImageView getPreviewImageView() {
        return mPreviewIv;
    }

    @Override
    int[] getFilterDocumentStatus() {
        return new int[]{DocumentStatus.STATUS_3, DocumentStatus.STATUS_4, DocumentStatus.STATUS_9};
    }

    @Override
    protected void onInitView() {
        super.onInitView();
        mDeleteLayout.setVisibility(View.GONE);
    }

    @Override
    protected void onInitData() {
        super.onInitData();

        showLoading();
        //AuditManager.getInstance().loadAuditList();
        AuditManager.getInstance().getNextPeople(1,2,null);
        PlanManager.getInstance().loadData(mDocument);
    }

    @Override
    protected int onSetTitleFormatResId() {
        return R.string.jhs;
    }

    @Event(value = {R.id.qzrq_et, R.id.gzrq_et, R.id.bjspr_et, R.id.scan_btn,
            R.id.delete_layout, R.id.alter_layout})
    private void click(View view) {
        switch (view.getId()) {
        case R.id.qzrq_et:
            BirthdayPicker.showYearMonthDay(this, new DatePicker.OnYearMonthDayPickListener() {
                @Override
                public void onDatePicked(String year, String month, String day) {
                    String date = String.format("%s-%s-%s", year, month, day);
                    object.setSignDate(date);
                    mQzrqEt.setText(date);
                }
            });
            break;
        case R.id.gzrq_et:
            BirthdayPicker.showYearMonthDay(this, new DatePicker.OnYearMonthDayPickListener() {
                @Override
                public void onDatePicked(String year, String month, String day) {
                    String date = String.format("%s-%s-%s", year, month, day);
                    object.setStampDate(date);
                    mGzrqEt.setText(date);
                }
            });
            break;
        case R.id.bjspr_et:
            if (mAuditStrList != null && mAuditStrList.size() > 0) {
                SingleChoicePicker.showString(this, mAuditStrList, new OptionPicker.OnOptionPickListener() {
                    @Override
                    public void onOptionPicked(int index, String item) {
                        mAudit = mAuditPersonList.get(index);
                        mBjjhsprEt.setText(item);
                    }
                });

            } else {
                showLoading();
                //AuditManager.getInstance().loadAuditList();
                AuditManager.getInstance().getNextPeople(1,2,null);
            }
            break;
        case R.id.scan_btn:
            takePicture();
            break;
        case R.id.delete_layout:
        case R.id.alter_layout:
            showLoading();
            if (view.getId() == R.id.alter_layout) {
                alterType = T_MODIFY;
            } else {
                alterType = T_DELETE;
            }
            TaskManager.getInstance().judgeCreateTime(object.getId(), DOC_TYPE_PLAN);
            break;
        default: // no code
            break;
        }
    }

    @Override
    protected boolean checkDataBeforeUpload() {
        object.setFileAdd(mAttachmentUrl);
        object.setPlanComment(mBjjhEt.getText().toString().trim());

        return (mAudit != null) && MyTextUtils.isAllNotEmpty(
                object.getFileAdd(),
                object.getSignDate(),
                object.getStampDate(),
                object.getPlanComment());
    }

    @Override
    protected void uploadData() {
        PlanManager.getInstance().uploadData(mPersion, mDocument, object, mAudit);
    }

    private void disableUi(boolean disable) {
        mQzrqEt.setEnabled(!disable);
        mGzrqEt.setEnabled(!disable);
        mBjjhEt.setEnabled(!disable);
        mBjjhsprEt.setEnabled(!disable);
        mScanBtn.setEnabled(!disable);
    }

    private void updateUi(Plan plan) {
        if (Document.DOC_STATUS_END.equals(mDocument.getDocStatus())) {
            disableSave();
            disableUi(true);
            mBottomActionsContainer.setVisibility(View.GONE);

        } else {
            String id = object.getId();
            if(id != null){
                disableSave();
            }
            mBottomActionsContainer.setVisibility(View.VISIBLE);
        }

        mQzrqEt.setText(DateUtil.getInstance().changeDate(plan.getSignDate()));
        mGzrqEt.setText(DateUtil.getInstance().changeDate(plan.getStampDate()));
        mBjjhEt.setText(plan.getPlanComment());
        mBjjhsprEt.setText(plan.getAuditPerson());
        mPreviewIv.setVisibility(View.VISIBLE);
//        Glide.with(this).load(PhotoManager.getInstance().IMAGE_URL + plan.getFileAdd())
//                .placeholder(R.mipmap.take_photo)
//                .error(R.mipmap.take_photo).into(mPreviewIv);

        PhotoManager.getInstance().getPic(plan.getFileAdd(), mPreviewIv);
    }

}
