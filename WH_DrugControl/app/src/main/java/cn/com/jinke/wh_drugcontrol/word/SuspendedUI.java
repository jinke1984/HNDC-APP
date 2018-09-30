package cn.com.jinke.wh_drugcontrol.word;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
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
import cn.com.jinke.wh_drugcontrol.input.model.Nation;
import cn.com.jinke.wh_drugcontrol.persion.model.Document;
import cn.com.jinke.wh_drugcontrol.persion.model.DocumentStatus;
import cn.com.jinke.wh_drugcontrol.task.manager.TaskManager;
import cn.com.jinke.wh_drugcontrol.utils.BirthdayPicker;
import cn.com.jinke.wh_drugcontrol.utils.CommUtils;
import cn.com.jinke.wh_drugcontrol.utils.DateUtil;
import cn.com.jinke.wh_drugcontrol.utils.MyTextUtils;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.utils.SingleChoicePicker;
import cn.com.jinke.wh_drugcontrol.word.manager.SuspendedManager;
import cn.com.jinke.wh_drugcontrol.word.model.Suspended;
import cn.qqtheme.framework.picker.DatePicker;

/**
 * 中止记录
 */

@Route(path = RouteUtils.R_WORD_SUSPENDED_UI)
public class SuspendedUI extends BaseWordUI {

    @ViewInject(R.id.name_et)
    private CustomEditText mNameEt; // 姓名

    @ViewInject(R.id.ssqy_et)
    private CustomEditText mSsqyEt; // 所属区域

    @ViewInject(R.id.zzyy_et)
    private CustomEditText mZzyyEt; // 中止原因

    @ViewInject(R.id.zzrq_et)
    private CustomEditText mZzrqEt; // 中止日期

    @ViewInject(R.id.sxfzContent)
    private LinearLayout mSxfzContent;
    @ViewInject(R.id.jdjg_et)
    private CustomEditText mJdjgEt; // 决定机关
    @ViewInject(R.id.qzcs_et)
    private CustomEditText mQzcsEt; // 强制措施

    @ViewInject(R.id.zyjdContent)
    private LinearLayout mZyjdContent;
    @ViewInject(R.id.tyjg_et)
    private CustomEditText mTyjgEt; // 同意机关
    @ViewInject(R.id.glqx_et)
    private CustomEditText mGlqxEt; // 隔离期限

    @ViewInject(R.id.sxfzms_et)
    private CustomEditText mSxfzmsEt; // 涉嫌犯罪描述

    @ViewInject(R.id.fjsm_et)
    private CustomEditText mFjmsEt; // 附件说明

    @ViewInject(R.id.scan_btn)
    private LinearLayout mScanBtn;

    @ViewInject(R.id.preview_iv)
    private ImageView mPreviewIv;

    @ViewInject(R.id.bottom_actions_container)
    private LinearLayout mBottomActionsContainer;

    private Suspended object = new Suspended();

    private ArrayList<Nation> zzyyList = CommUtils.getInstance().getBreakReasons();
    private ArrayList<Nation> qzcsList = CommUtils.getInstance().getQzcsAssets();

    private int alterType = T_MODIFY; // 1档案修改 2档案删除

    private int[] MSG = new int[]{JUDGE_CREATETIME, ADD_SUSPENDED_DOC};

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what) {
        case ADD_SUSPENDED_DOC:
            handleUploadMessage(msg);
            break;
        case JUDGE_CREATETIME: // 判断文档创建时间
            dismissLoading();
            if (msg.arg1 == SUCCESS) {
                showLoading();
                if (alterType == T_MODIFY) {
                    saveData2Server();
                } else {
                    SuspendedManager.getInstance().deleteDoc(object);
                }
            } else {
                final String message = (String) msg.obj;
                showAlterMessageDialog(message, alterType, mDocument.getId(),
                        mPersion.getId(), object.getId(), DOC_TYPE_SUSPENDED);
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
        setContentView(R.layout.ui_word_suspended);
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
        mNameEt.setText(mPersion.getRealName());
        mNameEt.setEnabled(false);
        mSsqyEt.setText(mPersion.getCurrentAddress());
        mSsqyEt.setEnabled(false);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra(BUNDLE);
            object = (Suspended) bundle.getSerializable(SUSPENDED);
        }

        if (object == null) {
            object = new Suspended();
        }

        mAttachmentUrl = object.getFileAdd();
        updateUi(object);
        dealDocumentStatus(TextUtils.isEmpty(object.getId()));
    }

    @Event(value = {R.id.zzyy_et, R.id.zzrq_et, R.id.qzcs_et, R.id.scan_btn,
            R.id.delete_layout, R.id.alter_layout})
    private void click(View view) {
        switch (view.getId()) {
        case R.id.zzyy_et:
            SingleChoicePicker.showNation(this, zzyyList, new SingleChoicePicker.OnNationOptionPickListener() {
                @Override
                public void onOptionPicked(int index, Nation nation) {
                    if (index == 0) {
                        mSxfzContent.setVisibility(View.VISIBLE);
                        mZyjdContent.setVisibility(View.GONE);
                    } else {
                        mSxfzContent.setVisibility(View.GONE);
                        mZyjdContent.setVisibility(View.VISIBLE);
                    }
                    object.setBreakReason(nation.getId());
                    mZzyyEt.setText(nation.getText());
                }
            });
            break;
        case R.id.zzrq_et:
            BirthdayPicker.showYearMonthDay(this, new DatePicker.OnYearMonthDayPickListener() {
                @Override
                public void onDatePicked(String year, String month, String day) {
                    String date = String.format("%s-%s-%s", year, month, day);
                    object.setBreakDate(date);
                    mZzrqEt.setText(date);
                }
            });
            break;
        case R.id.qzcs_et:
            SingleChoicePicker.showNation(this, qzcsList, new SingleChoicePicker.OnNationOptionPickListener() {
                @Override
                public void onOptionPicked(int index, Nation nation) {
                    object.setConstraints(nation.getId());
                    mQzcsEt.setText(nation.getText());
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
            TaskManager.getInstance().judgeCreateTime(object.getId(), DOC_TYPE_SUSPENDED);
            break;
        default: // no code
            break;
        }
    }

    @Override
    protected int onSetTitleFormatResId() {
        return R.string.zzjl;
    }

    @Override
    protected boolean checkDataBeforeUpload() {
        object.setFileAdd(mAttachmentUrl);
        object.setDescription(mSxfzmsEt.getText().trim());
        object.setFileAddDesc(mFjmsEt.getText().trim());
        object.setIsolationPeriod(mGlqxEt.getText().trim());
        object.setDecideDept(mJdjgEt.getText().trim());
        object.setApproveDept(mTyjgEt.getText().trim());

        if (zzyyList.get(0).getId().equals(object.getBreakReason())) {
            if (!MyTextUtils.isAllNotEmpty(
                    object.getDecideDept(),
                    object.getConstraints(),
                    object.getDescription())) return false;
        } else {
            if (!MyTextUtils.isAllNotEmpty(
                    object.getApproveDept(),
                    object.getIsolationPeriod())) return false;
        }

        return MyTextUtils.isAllNotEmpty(
                object.getFileAdd(),
                object.getBreakReason(),
                object.getBreakDate(),
                object.getFileAddDesc());
    }

    @Override
    protected void uploadData() {
        SuspendedManager.getInstance().uploadData(mPersion, mDocument, object);
    }

    private void disableUi(boolean disable) {
        mZzyyEt.setEnabled(!disable);
        mZzrqEt.setEnabled(!disable);
        mJdjgEt.setEnabled(!disable);
        mQzcsEt.setEnabled(!disable);
        mSxfzmsEt.setEnabled(!disable);
        mFjmsEt.setEnabled(!disable);
        mTyjgEt.setEnabled(!disable);
        mGlqxEt.setEnabled(!disable);
        mScanBtn.setEnabled(!disable);
    }

    private void updateUi(Suspended suspended) {
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

        for (Nation nation : zzyyList) {
            if (nation.getId().equals(suspended.getBreakReason())) {
                mZzyyEt.setText(nation.getText());
                break;
            }
        }
        mZzrqEt.setText(DateUtil.getInstance().changeDate(suspended.getBreakDate()));

        if (zzyyList.get(0).getId().equals(suspended.getBreakReason())) {
            mSxfzContent.setVisibility(View.VISIBLE);
            mJdjgEt.setText(suspended.getDecideDept());
            for (Nation nation : qzcsList) {
                if (nation.getId().equals(suspended.getConstraints())) {
                    mQzcsEt.setText(nation.getText());
                    break;
                }
            }
        } else {
            mZyjdContent.setVisibility(View.VISIBLE);
            mTyjgEt.setText(suspended.getApproveDept());
            mGlqxEt.setText(suspended.getIsolationPeriod());
        }
        mSxfzmsEt.setText(suspended.getDescription());
        mFjmsEt.setText(suspended.getFileAddDesc());
        mPreviewIv.setVisibility(View.VISIBLE);
        //Glide.with(this).load(PhotoManager.getInstance().IMAGE_URL + suspended.getFileAdd()).into(mPreviewIv);
        PhotoManager.getInstance().getPic(suspended.getFileAdd(), mPreviewIv);
    }

}
