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
import cn.com.jinke.wh_drugcontrol.word.manager.ContinueManager;
import cn.com.jinke.wh_drugcontrol.word.model.Continue;
import cn.com.jinke.wh_drugcontrol.word.model.Suspended;
import cn.qqtheme.framework.picker.DatePicker;

/**
 * 继续执行
 */

@Route(path = RouteUtils.R_WORD_GO_ON_UI)
public class ContinueUI extends BaseWordUI {

    @ViewInject(R.id.name_et)
    private CustomEditText mNameEt; // 姓名

    @ViewInject(R.id.ssqy_et)
    private CustomEditText mSsqyEt; // 所属区域

    @ViewInject(R.id.zzyy_et)
    private CustomEditText mZzyyEt; // 中止原因

    @ViewInject(R.id.zzksrq_et)
    private CustomEditText mZzksrqEt; // 中止开始日期

    @ViewInject(R.id.jczzyy_et)
    private CustomEditText mJczzyyEt; // 解除中止原因

    @ViewInject(R.id.jczzrq_et)
    private CustomEditText mJczzrqEt; // 解除中止日期

    @ViewInject(R.id.scan_btn)
    private LinearLayout mScanBtn;

    @ViewInject(R.id.preview_iv)
    private ImageView mPreviewIv;

    @ViewInject(R.id.bottom_actions_container)
    private LinearLayout mBottomActionsContainer;

    private ArrayList<Nation> zzyyList = CommUtils.getInstance().getBreakReasons();
    private ArrayList<Nation> jczzyyList = CommUtils.getInstance().assetsToList("/assets/jczzyy.json");

    private Continue object = new Continue();
    private Suspended suspended;

    private int alterType = T_MODIFY; // 1档案修改 2档案删除

    private int[] MSG = new int[]{JUDGE_CREATETIME, ADD_GO_ON_DOC};

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what) {
        case ADD_GO_ON_DOC:
            handleUploadMessage(msg);
            break;
        case JUDGE_CREATETIME: // 判断文档创建时间
            dismissLoading();
            if (msg.arg1 == SUCCESS) {
                showLoading();
                if (alterType == T_MODIFY) {
                    saveData2Server();
                } else {
                    ContinueManager.getInstance().deleteDoc(object);
                }
            } else {
                final String message = (String) msg.obj;
                showAlterMessageDialog(message, alterType, mDocument.getId(),
                        mPersion.getId(), object.getId(), DOC_TYPE_CONTINUE);
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
        setContentView(R.layout.ui_word_continue);
        registerMessages(MSG);
    }

    @Override
    protected ImageView getPreviewImageView() {
        return mPreviewIv;
    }

    @Override
    int[] getFilterDocumentStatus() {
        return new int[]{DocumentStatus.STATUS_0, DocumentStatus.STATUS_1, DocumentStatus.STATUS_9};
    }

    @Override
    protected void onInitView() {
        super.onInitView();

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra(BUNDLE);
            object = (Continue) bundle.getSerializable(CONTINUE);
            suspended = (Suspended) bundle.getSerializable(SUSPENDED);
        }

        if (object == null) {
            object = new Continue();
        }
        
        mNameEt.setText(mPersion.getRealName());
        mNameEt.setEnabled(false);
        mSsqyEt.setText(mPersion.getCurrentAddress());
        mSsqyEt.setEnabled(false);

        if (suspended != null) {
            object.setBreakID(suspended.getId());
            object.setBreakDate(suspended.getBreakDate());
            object.setBreakReason(suspended.getBreakReason());
        }

        mZzksrqEt.setText(DateUtil.getInstance().changeDate(object.getBreakDate()));
        mZzksrqEt.setEnabled(false);
        for (Nation nation : zzyyList) {
            if (nation.getId().equals(object.getBreakReason())) {
                mZzyyEt.setText(nation.getText());
                mZzyyEt.setEnabled(false);
                break;
            }
        }

        mAttachmentUrl = object.getFileAdd();
        updateUi(object);
        dealDocumentStatus(TextUtils.isEmpty(object.getId()));
    }

    @Event(value = {R.id.jczzyy_et, R.id.jczzrq_et, R.id.scan_btn,
            R.id.delete_layout, R.id.alter_layout})
    private void click(View view) {
        switch (view.getId()) {
        case R.id.jczzyy_et:
            SingleChoicePicker.showNation(this, jczzyyList, new SingleChoicePicker.OnNationOptionPickListener() {
                @Override
                public void onOptionPicked(int index, Nation nation) {
                    object.setContinueReason(nation.getId());
                    mJczzyyEt.setText(nation.getText());
                }
            });
            break;
        case R.id.jczzrq_et:
            BirthdayPicker.showYearMonthDay(this, new DatePicker.OnYearMonthDayPickListener() {
                @Override
                public void onDatePicked(String year, String month, String day) {
                    String date = String.format("%s-%s-%s", year, month, day);
                    object.setContinueDate(date);
                    mJczzrqEt.setText(date);
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
            TaskManager.getInstance().judgeCreateTime(object.getId(), DOC_TYPE_CONTINUE);
            break;
        default: // no code
            break;
        }
    }

    @Override
    protected int onSetTitleFormatResId() {
        return R.string.jxzx;
    }

    @Override
    protected boolean checkDataBeforeUpload() {
        object.setFileAdd(mAttachmentUrl);

        return MyTextUtils.isAllNotEmpty(
                object.getContinueReason(),
                object.getContinueDate(),
                object.getFileAdd());
    }

    @Override
    protected void uploadData() {
        ContinueManager.getInstance().uploadData(mPersion, mDocument, object);
    }

    private void disableUi(boolean disable) {
        mJczzyyEt.setEnabled(!disable);
        mJczzrqEt.setEnabled(!disable);
        mScanBtn.setEnabled(!disable);
    }

    private void updateUi(Continue object) {
        if (Document.DOC_STATUS_END.equals(mDocument.getDocStatus())) {
            disableSave();
            disableUi(true);
            mBottomActionsContainer.setVisibility(View.GONE);

        } else {
            mBottomActionsContainer.setVisibility(View.VISIBLE);
        }

        for (Nation nation : jczzyyList) {
            if (nation.getId().equals(object.getContinueReason())) {
                mJczzyyEt.setText(nation.getText());
                break;
            }
        }
        mJczzrqEt.setText(DateUtil.getInstance().changeDate(object.getContinueDate()));

        mPreviewIv.setVisibility(View.VISIBLE);
        //Glide.with(this).load(PhotoManager.getInstance().IMAGE_URL + object.getFileAdd()).into(mPreviewIv);
        PhotoManager.getInstance().getPic(object.getFileAdd(), mPreviewIv);
    }

}
