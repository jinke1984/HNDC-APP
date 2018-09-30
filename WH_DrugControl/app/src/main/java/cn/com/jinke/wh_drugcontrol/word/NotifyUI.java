package cn.com.jinke.wh_drugcontrol.word;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
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
import cn.com.jinke.wh_drugcontrol.word.manager.NotifyManager;
import cn.com.jinke.wh_drugcontrol.word.model.Notify;
import cn.qqtheme.framework.picker.DatePicker;

/**
 * 告知书管理
 * Created by jinke on 2017/8/23.
 */

@Route(path = RouteUtils.R_WORD_NOTIFY_UI)
public class NotifyUI extends BaseWordUI implements RadioGroup.OnCheckedChangeListener {

    @ViewInject(R.id.gzs_type_radiogroup)
    private RadioGroup mGzsTypeRg; // 告知书类型
    @ViewInject(R.id.gzs_jd)
    private RadioButton mJdRb;
    @ViewInject(R.id.gzs_kf)
    private RadioButton mKfRb;

    @ViewInject(R.id.gzs_xd_et)
    private CustomEditText mXdrqEt; // 下达日期

    @ViewInject(R.id.gzs_xdjg_et)
    private CustomEditText mXdjgEt; // 下达机关

    @ViewInject(R.id.gzs_qs_et)
    private CustomEditText mQsrqEt; // 签收日期

    @ViewInject(R.id.gzs_gz_et)
    private CustomEditText mGzrqEt; // 盖章日期

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

    private Notify object = new Notify();

    private int[] MSG = new int[]{JUDGE_CREATETIME, LOAD_NOTIFY, ADD_NOTIFY_DOC};

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what) {
        case LOAD_NOTIFY:
            dismissLoading();
            if (msg.arg1 == SUCCESS) {
                finishWhenLoadErr = false;
                object = (Notify) msg.obj;
                if (object == null) {
                    object = new Notify();
                }

                mAttachmentUrl = object.getFileAdd();
                updateUi(object);
                dealDocumentStatus(TextUtils.isEmpty(object.getId()));

            } else {
                finishActivityWithToast();
            }
            break;
        case ADD_NOTIFY_DOC:
            handleUploadMessage(msg);
            break;
        case JUDGE_CREATETIME: // 判断文档创建时间
            dismissLoading();
            if (msg.arg1 == SUCCESS) {
                showLoading();
                if (alterType == T_MODIFY) {
                    saveData2Server();
                } else {
                    NotifyManager.getInstance().deleteDoc(object);
                }
            } else {
                final String message = (String) msg.obj;
                showAlterMessageDialog(message, alterType, mDocument.getId(),
                        mPersion.getId(), object.getId(), DOC_TYPE_NOTIFY);
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
        setContentView(R.layout.ui_word_notify);
        registerMessages(MSG);
    }

    @Override
    protected void onInitData() {
        super.onInitData();

        showLoading();
        NotifyManager.getInstance().loadData(mDocument);
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
        switch (checkedId) {
        case R.id.gzs_jd:
            object.setType("1");
            break;
        case R.id.gzs_kf:
            object.setType("2");
            break;
        default: // no code
            break;
        }
    }

    @Override
    protected void onInitView() {
        super.onInitView();
        mDeleteLayout.setVisibility(View.GONE);
        mGzsTypeRg.setOnCheckedChangeListener(this);
    }

    @Event(value = {R.id.gzs_xd_et, R.id.gzs_qs_et, R.id.gzs_gz_et, R.id.scan_btn,
            R.id.delete_layout, R.id.alter_layout})
    private void click(View view) {
        switch (view.getId()) {
        case R.id.gzs_xd_et:
            BirthdayPicker.showYearMonthDay(this, new DatePicker.OnYearMonthDayPickListener() {
                @Override
                public void onDatePicked(String year, String month, String day) {
                    String date = String.format("%s-%s-%s", year, month, day);
                    object.setNoticeDate(date);
                    mXdrqEt.setText(date);
                }
            });
            break;
        case R.id.gzs_qs_et:
            BirthdayPicker.showYearMonthDay(this, new DatePicker.OnYearMonthDayPickListener() {
                @Override
                public void onDatePicked(String year, String month, String day) {
                    String date = String.format("%s-%s-%s", year, month, day);
                    object.setReceiveDate(date);
                    mQsrqEt.setText(date);
                }
            });
            break;
        case R.id.gzs_gz_et:
            BirthdayPicker.showYearMonthDay(this, new DatePicker.OnYearMonthDayPickListener() {
                @Override
                public void onDatePicked(String year, String month, String day) {
                    String date = String.format("%s-%s-%s", year, month, day);
                    object.setDeptSignDate(date);
                    mGzrqEt.setText(date);
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
            TaskManager.getInstance().judgeCreateTime(object.getId(), DOC_TYPE_NOTIFY);
            break;
        default: // no code
            break;
        }
    }

    @Override
    protected int onSetTitleFormatResId() {
        return R.string.gzs;
    }

    @Override
    protected boolean checkDataBeforeUpload() {
        object.setFileAdd(mAttachmentUrl);

        object.setNoticeDate(mXdrqEt.getText().trim());
        object.setHandlingDepartment(mXdjgEt.getText().trim());
        object.setReceiveDate(mQsrqEt.getText().trim());
        object.setDeptSignDate(mGzrqEt.getText().trim());

        return MyTextUtils.isAllNotEmpty(
                object.getNoticeDate(),
                object.getHandlingDepartment(),
                object.getDeptSignDate(),
                object.getReceiveDate(),
                object.getFileAdd());
    }

    @Override
    protected void uploadData() {
        NotifyManager.getInstance().uploadData(mPersion, mDocument, object);
    }

    private void disableUi(boolean disable) {
        mJdRb.setEnabled(!disable);
        mKfRb.setEnabled(!disable);
        mXdrqEt.setEnabled(!disable);
        mXdjgEt.setEnabled(!disable);
        mQsrqEt.setEnabled(!disable);
        mGzrqEt.setEnabled(!disable);
        mScanBtn.setEnabled(!disable);
    }

    private void updateUi(Notify notify) {
        if (Document.DOC_STATUS_END.equals(mDocument.getDocStatus())) {
            disableSave();
            disableUi(true);
            mBottomActionsContainer.setVisibility(View.GONE);

        } else {
            mBottomActionsContainer.setVisibility(View.VISIBLE);
        }

        if ("1".equals(notify.getType())) {
            mJdRb.setChecked(true);
        } else if ("2".equals(notify.getType())) {
            mKfRb.setChecked(true);
        }
        mXdrqEt.setText(DateUtil.getInstance().changeDate(notify.getNoticeDate()));
        mXdjgEt.setText(notify.getHandlingDepartment());
        mQsrqEt.setText(DateUtil.getInstance().changeDate(notify.getReceiveDate()));
        mGzrqEt.setText(DateUtil.getInstance().changeDate(notify.getDeptSignDate()));
        mPreviewIv.setVisibility(View.VISIBLE);
        //Glide.with(this).load(PhotoManager.getInstance().IMAGE_URL + notify.getFileAdd()).into(mPreviewIv);
        PhotoManager.getInstance().getPic(notify.getFileAdd(), mPreviewIv);
    }

}
