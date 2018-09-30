package cn.com.jinke.wh_drugcontrol.word;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

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
import cn.com.jinke.wh_drugcontrol.word.manager.DecisionManager;
import cn.com.jinke.wh_drugcontrol.word.model.Decision;
import cn.qqtheme.framework.picker.DatePicker;

/**
 * Created by jinke on 2017/8/23.
 * 决定书管理
 */

@Route(path = RouteUtils.R_WORD_DECISION_UI)
public class DecisionUI extends BaseWordUI implements RadioGroup.OnCheckedChangeListener {

    @ViewInject(R.id.type_radiogroup)
    private RadioGroup mTypeRg; // 决定书类型
    @ViewInject(R.id.jd)
    private RadioButton mJdRb;
    @ViewInject(R.id.kf)
    private RadioButton mKfRb;

    @ViewInject(R.id.jds_radiogroup)
    private RadioGroup mJdsRg; // 是否有公安决定书
    @ViewInject(R.id.yes)
    private RadioButton mYesRb;
    @ViewInject(R.id.no)
    private RadioButton mNoRb;

    @ViewInject(R.id.reason_et)
    private CustomEditText mReasonEt; // 无决定书原因

    @ViewInject(R.id.decision_container)
    private LinearLayout mDecisionContainer;
    @ViewInject(R.id.word_one_et)
    private CustomEditText mWord1Et; // 决定书文号1
    @ViewInject(R.id.word_two_et)
    private CustomEditText mWord2Et; // 决定书文号2
    @ViewInject(R.id.word_three_et)
    private CustomEditText mWord3Et; // 决定书文号3
    @ViewInject(R.id.word_four_et)
    private CustomEditText mWord4Et;  // 决定书文号4

    @ViewInject(R.id.start_et)
    private CustomEditText mDateStartEt; // 开始日期
    @ViewInject(R.id.end_et)
    private CustomEditText mDateEndEt;   // 结束日期

    @ViewInject(R.id.place_name_et)
    private CustomEditText mPoliceNameEt; // 公安机关名称

    @ViewInject(R.id.bd_date_et)
    private CustomEditText mDateBdEt; // 报道日期

    @ViewInject(R.id.ch_detail_et)
    private EditText mDetailEt; // 查获详情

    @ViewInject(R.id.scan_btn)
    private LinearLayout mScanBtn;

    @ViewInject(R.id.preview_iv)
    private ImageView mPreviewIv; // 预览图

    @ViewInject(R.id.bottom_actions_container)
    private LinearLayout mBottomActionsContainer;
    @ViewInject(R.id.alter_layout)
    private LinearLayout mAlterLayout;
    @ViewInject(R.id.delete_layout)
    private LinearLayout mDeleteLayout;

    private int alterType = T_MODIFY; // 1档案修改 2档案删除

    private Decision object = new Decision();

    private int[] MSG = new int[]{JUDGE_CREATETIME, LOAD_DECISION, ADD_DECISION_DOC};

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what) {
        case LOAD_DECISION:
            dismissLoading();
            if (msg.arg1 == SUCCESS) {
                finishWhenLoadErr = false;
                object = (Decision) msg.obj;

                if (object == null) {
                    object = new Decision();
                }

                mAttachmentUrl = object.getFileAdd();
                updateUi(object);
                dealDocumentStatus(TextUtils.isEmpty(object.getId()));

            } else {
                finishActivityWithToast();
            }
            break;
        case ADD_DECISION_DOC:
            handleUploadMessage(msg);
            break;
        case JUDGE_CREATETIME: // 判断文档创建时间
            dismissLoading();
            if (msg.arg1 == SUCCESS) {
                showLoading();
                if (alterType == T_MODIFY) {
                    saveData2Server();
                } else {
                    DecisionManager.getInstance().deleteDoc(object);
                }
            } else {
                final String message = (String) msg.obj;
                showAlterMessageDialog(message, alterType, mDocument.getId(),
                        mPersion.getId(), object.getId(), DOC_TYPE_DECISION);
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
        setContentView(R.layout.ui_word_decision);
        registerMessages(MSG);
    }

    @Override
    protected void onInitData() {
        super.onInitData();

        showLoading();
        DecisionManager.getInstance().loadData(mDocument);
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
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (group.getId()) {
        case R.id.type_radiogroup:
            switch (checkedId) {
            case R.id.jd:
                object.setType("1");
                break;
            case R.id.kf:
                object.setType("2");
                break;
            }
            break;
        case R.id.jds_radiogroup:
            switch (checkedId) {
            case R.id.yes:
                object.setIsDecision("1");
                mReasonEt.setText("");
                object.setNonDecisionReason("");
                mReasonEt.setVisibility(View.GONE);
                mDecisionContainer.setVisibility(View.VISIBLE);
                break;
            case R.id.no:
                object.setIsDecision("2");
                object.setDecisionNo1("");
                object.setDecisionNo2("");
                object.setDecisionNo3("");
                object.setDecisionNo4("");
                mWord1Et.setText("");
                mWord2Et.setText("");
                mWord3Et.setText("");
                mWord4Et.setText("");
                mReasonEt.setVisibility(View.VISIBLE);
                mDecisionContainer.setVisibility(View.GONE);
                break;
            }
            break;
        }
    }

    @Override
    protected void onInitView() {
        super.onInitView();
        mDeleteLayout.setVisibility(View.GONE);
        mTypeRg.setOnCheckedChangeListener(this);
        mJdsRg.setOnCheckedChangeListener(this);

    }

    @Event(value = {R.id.start_et, R.id.end_et, R.id.bd_date_et, R.id.scan_btn,
            R.id.delete_layout, R.id.alter_layout})
    private void click(View view) {
        switch (view.getId()) {
        case R.id.start_et:
            BirthdayPicker.showYearMonthDay(this, new DatePicker.OnYearMonthDayPickListener() {
                @Override
                public void onDatePicked(String year, String month, String day) {
                    object.setDrugsBeginDate(String.format("%s-%s-%s", year, month, day));
                    mDateStartEt.setText(String.format("%s-%s-%s", year, month, day));
                }
            });
            break;
        case R.id.end_et:
            BirthdayPicker.showYearMonthDay(this, new DatePicker.OnYearMonthDayPickListener() {
                @Override
                public void onDatePicked(String year, String month, String day) {
                    object.setDrugsEndDate(String.format("%s-%s-%s", year, month, day));
                    mDateEndEt.setText(String.format("%s-%s-%s", year, month, day));
                }
            });
            break;
        case R.id.bd_date_et:
            BirthdayPicker.showYearMonthDay(this, new DatePicker.OnYearMonthDayPickListener() {
                @Override
                public void onDatePicked(String year, String month, String day) {
                    object.setRegisterDate(String.format("%s-%s-%s", year, month, day));
                    mDateBdEt.setText(String.format("%s-%s-%s", year, month, day));
                }
            });
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
            TaskManager.getInstance().judgeCreateTime(object.getId(), DOC_TYPE_DECISION);
            break;
        default: // no code
            break;
        }
    }

    @Override
    protected int onSetTitleFormatResId() {
        return R.string.jds;
    }

    @Override
    protected boolean checkDataBeforeUpload() {
        object.setFileAdd(mAttachmentUrl);
        object.setNonDecisionReason(mReasonEt.getText().trim());
        object.setDecisionNo1(mWord1Et.getText().trim());
        object.setDecisionNo2(mWord2Et.getText().trim());
        object.setDecisionNo3(mWord3Et.getText().trim());
        object.setDecisionNo4(mWord4Et.getText().trim());
        object.setDrugsBeginDate(mDateStartEt.getText().trim());
        object.setDrugsEndDate(mDateEndEt.getText().trim());
        object.setHandlingDepartment(mPoliceNameEt.getText().trim());
        object.setRegisterDate(mDateBdEt.getText().trim());
        object.setCatchDeatil(mDetailEt.getText().toString().trim());

        if ("1".equals(object.getIsDecision())) { // 有公安决定书
            return MyTextUtils.isAllNotEmpty(
                    object.getType(),
                    object.getDecisionNo1(),
                    object.getDecisionNo2(),
                    object.getDecisionNo3(),
                    object.getDecisionNo4(),
                    object.getDrugsBeginDate(),
                    object.getDrugsEndDate(),
                    object.getHandlingDepartment(),
                    object.getCatchDeatil(),
                    object.getFileAdd());

        } else if ("2".equals(object.getIsDecision())) {
            return MyTextUtils.isAllNotEmpty(
                    object.getType(),
                    object.getNonDecisionReason(),
                    object.getDrugsBeginDate(),
                    object.getDrugsEndDate(),
                    object.getHandlingDepartment(),
                    object.getRegisterDate(),
                    object.getCatchDeatil(),
                    object.getFileAdd(),
                    mAttachmentUrl);

        } else {
            return false;
        }
    }

    @Override
    protected void uploadData() {
        DecisionManager.getInstance().uploadData(mPersion, mDocument, object);
    }

    /**
     * 禁止输入和按钮操作
     *
     * @param disable true：禁止
     */
    private void disableUi(boolean disable) {
        mJdRb.setEnabled(!disable);
        mKfRb.setEnabled(!disable);
        mYesRb.setEnabled(!disable);
        mNoRb.setEnabled(!disable);
        mWord1Et.setEnabled(!disable);
        mWord2Et.setEnabled(!disable);
        mWord3Et.setEnabled(!disable);
        mWord4Et.setEnabled(!disable);
        mReasonEt.setEnabled(!disable);
        mDateStartEt.setEnabled(!disable);
        mDateEndEt.setEnabled(!disable);
        mPoliceNameEt.setEnabled(!disable);
        mDateBdEt.setEnabled(!disable);
        mDetailEt.setEnabled(!disable);
        mScanBtn.setEnabled(!disable);
    }

    /**
     * 使用决定书数据填充界面
     *
     * @param decision 决定书（can not be null)
     */
    private void updateUi(Decision decision) {
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

        if ("1".equals(decision.getType())) {
            mJdRb.setChecked(true);
        } else {
            mKfRb.setChecked(true);
        }
        if ("1".equals(decision.getIsDecision())) {
            mYesRb.setChecked(true);
            mDecisionContainer.setVisibility(View.VISIBLE);
            mWord1Et.setText(decision.getDecisionNo1());
            mWord2Et.setText(decision.getDecisionNo2());
            mWord3Et.setText(decision.getDecisionNo3());
            mWord4Et.setText(decision.getDecisionNo4());

        } else {
            mNoRb.setChecked(true);
            mDecisionContainer.setVisibility(View.GONE);
            mReasonEt.setText(decision.getNonDecisionReason());
        }
        mDateStartEt.setText(DateUtil.getInstance().changeDate(decision.getDrugsBeginDate()));
        mDateEndEt.setText(DateUtil.getInstance().changeDate(decision.getDrugsEndDate()));
        mPoliceNameEt.setText(decision.getHandlingDepartment());
        mDateBdEt.setText(DateUtil.getInstance().changeDate(decision.getRegisterDate()));
        mDetailEt.setText(decision.getCatchDeatil());
        mPreviewIv.setVisibility(View.VISIBLE);
        //Glide.with(this).load(PhotoManager.getInstance().IMAGE_URL + decision.getFileAdd()).into(mPreviewIv);
        PhotoManager.getInstance().getPic(decision.getFileAdd(), mPreviewIv);
    }

}
