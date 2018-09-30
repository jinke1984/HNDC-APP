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

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.customview.CustomEditText;
import cn.com.jinke.wh_drugcontrol.input.manager.PhotoManager;
import cn.com.jinke.wh_drugcontrol.persion.model.Document;
import cn.com.jinke.wh_drugcontrol.persion.model.DocumentStatus;
import cn.com.jinke.wh_drugcontrol.persion.model.Photo;
import cn.com.jinke.wh_drugcontrol.task.manager.TaskManager;
import cn.com.jinke.wh_drugcontrol.utils.BirthdayPicker;
import cn.com.jinke.wh_drugcontrol.utils.DateUtil;
import cn.com.jinke.wh_drugcontrol.utils.MyTextUtils;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.word.manager.AgreementManager;
import cn.com.jinke.wh_drugcontrol.word.model.Agreement;
import cn.qqtheme.framework.picker.DatePicker;

/**
 * 协议书
 * Created by jinke on 2017/8/23.
 */

@Route(path = RouteUtils.R_WORD_AGREEMENT_UI)
public class AgreementUI extends BaseWordUI {

    @ViewInject(R.id.agreement_workstation)
    private CustomEditText mWorkstationEt; // 禁毒工作站

    @ViewInject(R.id.agreement_manager_person)
    private CustomEditText mManagerPersonEt; // 管控人员

    @ViewInject(R.id.agreement_gz_date)
    private CustomEditText mGzDateEt; // 盖章日期

    @ViewInject(R.id.agreement_qz_date)
    private CustomEditText mQzDateEt; // 签字日期

    @ViewInject(R.id.agreement_bz)
    private EditText mBzEt; // 备注

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

    private Agreement object = new Agreement(); // 从服务器拉取的协议书

    private int[] MSG = new int[]{JUDGE_CREATETIME, LOAD_AGREEMENT, ADD_AGREEMENT_DOC};

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what) {
        case LOAD_AGREEMENT:
            dismissLoading();
            if (msg.arg1 == SUCCESS) {
                finishWhenLoadErr = false;
                object = (Agreement) msg.obj;

                if (object == null) {
                    object = new Agreement();
                }

                mAttachmentUrl = object.getFileAdd();
                updateUi(object);
                dealDocumentStatus(TextUtils.isEmpty(object.getId()));

            } else {
                finishActivityWithToast();
            }
            break;
        case ADD_AGREEMENT_DOC:
            handleUploadMessage(msg);
            break;
        case JUDGE_CREATETIME: // 判断文档创建时间
            dismissLoading();
            if (msg.arg1 == SUCCESS) {
                showLoading();
                if (alterType == T_MODIFY) {
                    saveData2Server();
                } else {
                    AgreementManager.getInstance().deleteDoc(object);
                }
            } else {
                final String message = (String) msg.obj;
                showAlterMessageDialog(message, alterType, mDocument.getId(),
                        mPersion.getId(), object.getId(), DOC_TYPE_AGREEMENT);
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
        setContentView(R.layout.ui_word_agreement);
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
        mManagerPersonEt.setEnabled(false);
        mManagerPersonEt.setText(mPersion.getRealName());
    }

    @Override
    protected void onInitData() {
        showLoading();

        AgreementManager.getInstance().loadData(mDocument);
    }

    @Override
    protected int onSetTitleFormatResId() {
        return R.string.xys;
    }

    @Event(value = {R.id.agreement_qz_date, R.id.agreement_gz_date, R.id.scan_btn,
            R.id.delete_layout, R.id.alter_layout})
    private void click(View view) {
        switch (view.getId()) {
        case R.id.agreement_gz_date:
            BirthdayPicker.showYearMonthDay(this, new DatePicker.OnYearMonthDayPickListener() {
                @Override
                public void onDatePicked(String year, String month, String day) {
                    String date = String.format("%s-%s-%s", year, month, day);
                    object.setSignDate(date);
                    mGzDateEt.setText(date);
                }
            });
            break;
        case R.id.agreement_qz_date:
            BirthdayPicker.showYearMonthDay(this, new DatePicker.OnYearMonthDayPickListener() {
                @Override
                public void onDatePicked(String year, String month, String day) {
                    String date = String.format("%s-%s-%s", year, month, day);
                    object.setDrugsDeptSignDate(date);
                    mQzDateEt.setText(date);
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
            TaskManager.getInstance().judgeCreateTime(object.getId(), DOC_TYPE_AGREEMENT);
            break;
        default: // no code
            break;
        }
    }

    @Override
    protected boolean checkDataBeforeUpload() {
        object.setFileAdd(mAttachmentUrl);
        object.setDrugsDept(mWorkstationEt.getText().trim());
        object.setDrugsDeptSignDate(mGzDateEt.getText().trim());
        object.setSignDate(mQzDateEt.getText().trim());

        object.setRemark(mBzEt.getText().toString().trim()); // 不校验

        return MyTextUtils.isAllNotEmpty(
                object.getDrugsDept(),
                object.getDrugsDeptSignDate(),
                object.getSignDate(),
                object.getFileAdd());
    }

    @Override
    protected void uploadData() {
        AgreementManager.getInstance().uploadData(mPersion, mDocument, object);
    }

    private void disableUi(boolean disable) {
        mWorkstationEt.setEnabled(!disable);
        mManagerPersonEt.setEnabled(!disable);
        mGzDateEt.setEnabled(!disable);
        mQzDateEt.setEnabled(!disable);
        mBzEt.setEnabled(!disable);
        mScanBtn.setEnabled(!disable);
    }

    private void updateUi(Agreement agreement) {
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

        mWorkstationEt.setText(agreement.getDrugsDept());
        mManagerPersonEt.setText(agreement.getRealName());
        mGzDateEt.setText(DateUtil.getInstance().changeDate(agreement.getDrugsDeptSignDate()));
        mQzDateEt.setText(DateUtil.getInstance().changeDate(agreement.getSignDate()));
        mBzEt.setText(agreement.getRemark());

        mPreviewIv.setVisibility(View.VISIBLE);
        //Glide.with(this).load(PhotoManager.getInstance().IMAGE_URL + agreement.getFileAdd()).into(mPreviewIv);
        PhotoManager.getInstance().getPic(agreement.getFileAdd(), mPreviewIv);
    }

}
