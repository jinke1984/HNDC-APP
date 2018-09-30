package cn.com.jinke.wh_drugcontrol.word;

import android.content.Intent;
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
import cn.com.jinke.wh_drugcontrol.word.manager.ViolateAgreeManager;
import cn.com.jinke.wh_drugcontrol.word.model.ViolateAgreement;
import cn.qqtheme.framework.picker.DatePicker;

/**
 * 新增违反记录
 * Created by jinke on 2017/8/28.
 */

@Route(path = RouteUtils.R_WORD_VIOLATEAGREE_UI)
public class ViolateAgreeUI extends BaseWordUI {

    @ViewInject(R.id.sssq_et)
    private CustomEditText mSssqEt; // 所属社区

    @ViewInject(R.id.wfrq_et)
    private CustomEditText mWfrqEt; // 违反日期

    @ViewInject(R.id.wfcd_et)
    private CustomEditText mWfcdEt; // 违反程度

    @ViewInject(R.id.clqk_et)
    private CustomEditText mClqkEt; // 处理情况

    @ViewInject(R.id.sffx_et)
    private CustomEditText mSffxEt; // 是否服刑

    @ViewInject(R.id.wfss_detail_et)
    private EditText mWfssEt; // 违反事实

    @ViewInject(R.id.bz_detail_et)
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

    private ArrayList<Nation> yesnoList = CommUtils.getInstance().getYesNo12AssetsList();
    private ArrayList<Nation> clqkList = CommUtils.getInstance().getClqkAssetsList();
    private ArrayList<Nation> wfcdList = CommUtils.getInstance().getWfcdAssetsList(); // 违反程度

    private int alterType = T_MODIFY; // 1档案修改 2档案删除

    private ViolateAgreement object = new ViolateAgreement();

    private int[] MSG = new int[]{JUDGE_CREATETIME, ADD_VIOLATE_AGREE_DOC};

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what) {
        case ADD_VIOLATE_AGREE_DOC:
            handleUploadMessage(msg);
            break;
        case JUDGE_CREATETIME: // 判断文档创建时间
            dismissLoading();
            if (msg.arg1 == SUCCESS) {
                showLoading();
                if (alterType == T_MODIFY) {
                    saveData2Server();
                } else {
                    ViolateAgreeManager.getInstance().deleteDoc(object);
                }
            } else {
                final String message = (String) msg.obj;
                showAlterMessageDialog(message, alterType, mDocument.getId(),
                        mPersion.getId(), object.getId(), DOC_TYPE_VIOLATE_AGREEMENT);
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
        setContentView(R.layout.ui_word_violate_agree);
        registerMessages(MSG);
    }

    @Override
    protected void onInitView() {
        super.onInitView();

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra(BUNDLE);
            object = (ViolateAgreement) bundle.getSerializable(AGREEMENT);
        }

        if (object == null) {
            object = new ViolateAgreement();
        }

        mAttachmentUrl = object.getFileAdd();
        updateUi(object);
        dealDocumentStatus(TextUtils.isEmpty(object.getId()));

        mDeleteLayout.setVisibility(View.GONE);
        mSssqEt.setText(mPersion.getCurrentAddress());
    }

    @Override
    protected ImageView getPreviewImageView() {
        return mPreviewIv;
    }

    @Override
    int[] getFilterDocumentStatus() {
        return new int[]{DocumentStatus.STATUS_3, DocumentStatus.STATUS_4, DocumentStatus.STATUS_9};
    }

    @Event(value = {R.id.wfrq_et, R.id.wfcd_et, R.id.clqk_et, R.id.sffx_et, R.id.scan_btn,
            R.id.delete_layout, R.id.alter_layout})
    private void click(View view) {
        switch (view.getId()) {
        case R.id.wfrq_et:
            BirthdayPicker.showYearMonthDay(this, new DatePicker.OnYearMonthDayPickListener() {
                @Override
                public void onDatePicked(String year, String month, String day) {
                    String date = String.format("%s-%s-%s", year, month, day);
                    object.setViolateDate(date);
                    mWfrqEt.setText(date);
                }
            });
            break;
        case R.id.wfcd_et:
            SingleChoicePicker.showNation(this, wfcdList, new SingleChoicePicker.OnNationOptionPickListener() {
                @Override
                public void onOptionPicked(int index, Nation nation) {
                    object.setViolateLevel(nation.getId());
                    mWfcdEt.setText(nation.getText());
                }
            });
            break;
        case R.id.clqk_et:
            SingleChoicePicker.showNation(this, clqkList, new SingleChoicePicker.OnNationOptionPickListener() {
                @Override
                public void onOptionPicked(int index, Nation nation) {
                    object.setPunishCondition(nation.getId());
                    mClqkEt.setText(nation.getText());
                }
            });
            break;
        case R.id.sffx_et:
            SingleChoicePicker.showNation(this, yesnoList, new SingleChoicePicker.OnNationOptionPickListener() {
                @Override
                public void onOptionPicked(int index, Nation nation) {
                    object.setRepapse(nation.getId());
                    mSffxEt.setText(nation.getText());
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
            TaskManager.getInstance().judgeCreateTime(object.getId(), DOC_TYPE_VIOLATE_AGREEMENT);
            break;
        default: // no code
            break;
        }
    }


    @Override
    protected int onSetTitleFormatResId() {
        return R.string.wfjl;
    }

    @Override
    protected boolean checkDataBeforeUpload() {
        object.setFileAdd(mAttachmentUrl);
        object.setViolateConditino(mWfssEt.getText().toString().trim());
        object.setRemark(mBzEt.getText().toString().trim());

        return MyTextUtils.isAllNotEmpty(
                object.getViolateDate(),
                object.getViolateLevel(),
                object.getPunishCondition(),
                object.getRepapse(),
                object.getViolateConditino(),
                object.getRemark(),
                object.getFileAdd());
    }

    @Override
    protected void uploadData() {
        ViolateAgreeManager.getInstance().uploadData(mPersion, mDocument, object);
    }

    private void disableUi(boolean disable) {
        mSssqEt.setEnabled(!disable);
        mWfrqEt.setEnabled(!disable);
        mWfcdEt.setEnabled(!disable);
        mClqkEt.setEnabled(!disable);
        mSffxEt.setEnabled(!disable);
        mWfssEt.setEnabled(!disable);
        mBzEt.setEnabled(!disable);
        mScanBtn.setEnabled(!disable);
    }

    private void updateUi(ViolateAgreement object) {
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

        mWfrqEt.setText(DateUtil.getInstance().changeDate(object.getViolateDate()));
        for (Nation nation : wfcdList) {
            if (nation.getId().equals(object.getViolateLevel())) {
                mWfcdEt.setText(nation.getText());
                break;
            }
        }
        for (Nation nation : clqkList) {
            if (nation.getId().equals(object.getPunishCondition())) {
                mClqkEt.setText(nation.getText());
                break;
            }
        }
        for (Nation nation : yesnoList) {
            if (nation.getId().equals(object.getRepapse())) {
                mSffxEt.setText(nation.getText());
                break;
            }
        }
        mWfssEt.setText(object.getViolateConditino());
        mBzEt.setText(object.getRemark());
        mPreviewIv.setVisibility(View.VISIBLE);
        //Glide.with(this).load(PhotoManager.getInstance().IMAGE_URL + object.getFileAdd()).into(mPreviewIv);

        PhotoManager.getInstance().getPic(object.getFileAdd(), mPreviewIv);
    }

}
