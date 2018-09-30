package cn.com.jinke.wh_drugcontrol.word;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.customview.CustomEditText;
import cn.com.jinke.wh_drugcontrol.persion.model.DocumentStatus;
import cn.com.jinke.wh_drugcontrol.utils.BirthdayPicker;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.MyTextUtils;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.utils.SingleChoicePicker;
import cn.com.jinke.wh_drugcontrol.word.manager.AuditManager;
import cn.com.jinke.wh_drugcontrol.word.model.Audit;
import cn.com.jinke.wh_drugcontrol.word.model.Leave;
import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.OptionPicker;

/**
 * 请假文档
 */

@Route(path = RouteUtils.R_WORD_LEAVE_UI)
public class LeaveUI extends BaseWordUI {

    @ViewInject(R.id.leave_name)
    private CustomEditText mNameEt;

    @ViewInject(R.id.leave_qwdq)
    private CustomEditText mQwdqEt;

    @ViewInject(R.id.leave_ksrq)
    private CustomEditText mKsrqEt;

    @ViewInject(R.id.leave_jsrq)
    private CustomEditText mJsrqEt;

    @ViewInject(R.id.leave_qjsy)
    private EditText mQjsyEt;

    @ViewInject(R.id.leave_sqrq)
    private CustomEditText mSqrqEt;

    @ViewInject(R.id.leave_xzspr)
    private CustomEditText mSprEt;

    /**
     *  预览图
     */
    @ViewInject(R.id.preview_iv)
    private ImageView mPreviewIv;

    private String mQwdq;
    private String mKsrq;
    private String mJsrq;
    private String mQjsy;
    private String mSqrq;

    private Audit mSpr;
    private ArrayList<Audit> mAuditPersonList;
    private ArrayList<String> mAuditStrList;

    private int[] MSG = new int[]{JUDGE_CREATETIME, LOAD_LEAVE, LOAD_AUDIT_PERSON_LIST, ADD_LEAVE_DOC};

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what) {
            case LOAD_LEAVE:
                dismissLoading();
                if (msg.arg1 == SUCCESS) {
                    finishWhenLoadErr = false;
                    Leave obj = (Leave) msg.obj;

                    if (obj != null) {
                        updateUi(obj);
                    }
                } else {
                    finishActivityWithToast();
                }
                break;
            case LOAD_AUDIT_PERSON_LIST:
                dismissLoading();
                if (msg.arg1 == SUCCESS) {
                    mAuditPersonList = (ArrayList<Audit>) msg.obj;
                    mAuditStrList = new ArrayList<>(mAuditPersonList.size());
                    for (Audit audit : mAuditPersonList) {
                        mAuditStrList.add(audit.getUserName());
                    }
                }
                break;
            case ADD_LEAVE_DOC:
                handleUploadMessage(msg);
                break;
            default:
                break;
        }
        return super.handleMessage(msg);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_word_leave);
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
    protected void onInitData() {
        super.onInitData();

        showLoading();
        MessageProxy.sendEmptyMessage(ACCESS_NET_FAILED);
//        LeaveManager.getInstance().loadData(mDocument);
//        AuditManager.getInstance().loadAuditList();
    }

    @Override
    protected int onSetTitleFormatResId() {
        return R.string.qjb;
    }

    @Event(value = {R.id.leave_ksrq, R.id.leave_jsrq, R.id.leave_sqrq, R.id.leave_xzspr, R.id.scan_btn})
    private void click(View view) {
        switch (view.getId()) {
            case R.id.leave_ksrq:
                BirthdayPicker.showYearMonthDay(this, new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        mKsrq = String.format("%s-%s-%s", year, month, day);
                        mKsrqEt.setText(mKsrq);
                    }
                });
                break;
            case R.id.leave_jsrq:
                BirthdayPicker.showYearMonthDay(this, new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        mJsrq = String.format("%s-%s-%s", year, month, day);
                        mJsrqEt.setText(mJsrq);
                    }
                });
                break;
            case R.id.leave_sqrq:
                BirthdayPicker.showYearMonthDay(this, new DatePicker.OnYearMonthDayPickListener() {
                    @Override
                    public void onDatePicked(String year, String month, String day) {
                        mSqrq = String.format("%s-%s-%s", year, month, day);
                        mSqrqEt.setText(mSqrq);
                    }
                });
                break;
            case R.id.leave_xzspr:
                if (mAuditPersonList != null) {
                    SingleChoicePicker.showString(this, mAuditStrList, new OptionPicker.OnOptionPickListener() {
                        @Override
                        public void onOptionPicked(int index, String item) {
                            mSpr = mAuditPersonList.get(index);
                            mSprEt.setText(item);
                        }
                    });

                } else {
                    showLoading();
                    AuditManager.getInstance().loadAuditList();
                }
                break;
            case R.id.scan_btn:
                takePicture();
                break;
            default:
                break;
        }
    }

    @Override
    protected boolean checkDataBeforeUpload() {
        mQwdq = mQwdqEt.getText().trim();
        mQjsy = mQjsyEt.getText().toString().trim();

        return MyTextUtils.isAllNotEmpty(mAttachmentUrl, mQwdq, mKsrq, mJsrq, mQjsy, mSqrq) && (mSpr != null);
    }

    @Override
    protected void uploadData() {
//        LeaveManager.getInstance().uploadData(mPersion, mDocument, mSpr, mAttachmentUrl,
//                mQwdq, mQjsy, mKsrq, mJsrq, mSqrq);
    }

    private void disableUi(boolean disable) {
    }

    private void updateUi(Leave leave) {
        disableSave();
        disableUi(true);
    }

}
