package cn.com.jinke.wh_drugcontrol.word;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.utils.RegexUtils;
import com.bumptech.glide.Glide;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Locale;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.customview.CustomEditText;
import cn.com.jinke.wh_drugcontrol.input.manager.PhotoManager;
import cn.com.jinke.wh_drugcontrol.input.model.Nation;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.persion.model.Document;
import cn.com.jinke.wh_drugcontrol.persion.model.DocumentStatus;
import cn.com.jinke.wh_drugcontrol.task.manager.TaskManager;
import cn.com.jinke.wh_drugcontrol.utils.BirthdayPicker;
import cn.com.jinke.wh_drugcontrol.utils.CommUtils;
import cn.com.jinke.wh_drugcontrol.utils.DateUtil;
import cn.com.jinke.wh_drugcontrol.utils.MyTextUtils;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.utils.SingleChoicePicker;
import cn.com.jinke.wh_drugcontrol.word.manager.ExhortManager;
import cn.com.jinke.wh_drugcontrol.word.model.Exhort;
import cn.com.jinke.wh_drugcontrol.word.model.ViolateAgreement;
import cn.qqtheme.framework.picker.DatePicker;

/**
 * 告诫书
 * Created by jinke on 2017/8/25.
 */

@Route(path = RouteUtils.R_WORD_WARNING_UI)
public class WarningUI extends BaseWordUI {

    @ViewInject(R.id.qjr_et)
    private CustomEditText mQjrEt; // 劝诫人

    @ViewInject(R.id.qjrdw_et)
    private CustomEditText mQjrdwEt; // 劝诫人单位

    @ViewInject(R.id.qjsj_et)
    private CustomEditText mQjsjEt; // 劝诫时间

    @ViewInject(R.id.qjdd)
    private CustomEditText mQjddEt; // 劝诫地点

    @ViewInject(R.id.jzr)
    private CustomEditText mJzrEt; // 见证人

    @ViewInject(R.id.jzrzjlx_et)
    private CustomEditText mJzrzjlxEt; // 见证人证件类型

    @ViewInject(R.id.jzrzjhm_et)
    private CustomEditText mJzrzjhmEt; // 见证人证件号码

    @ViewInject(R.id.szlkts_et)
    private CustomEditText mSzlktsEt; // 擅自离开天数

    @ViewInject(R.id.szlkcs)
    private CustomEditText mSzlkcsEt; // 擅自离开次数

    @ViewInject(R.id.qjyy_et)
    private CustomEditText mQjyyEt; // 劝诫原因

    @ViewInject(R.id.qjyy_other_et)
    private CustomEditText mQjyyOtherEt; // 其他劝诫原因

    @ViewInject(R.id.tbjjcs)
    private CustomEditText mTbjjcsEt; // 逃避拒绝次数

    @ViewInject(R.id.tbjjyf_et)
    private CustomEditText mTbjjyfEt; // 逃避拒绝月份

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

    private ArrayList<Nation> qjyyList = CommUtils.getInstance().assetsToList("/assets/qjyy.json");
    private ArrayList<Nation> zjlxList = CommUtils.getInstance().assetsToList("/assets/zjlx.json");

    private ViolateAgreement mViolateAgreement;

    private int alterType = T_MODIFY; // 1档案修改 2档案删除

    private Exhort object = new Exhort();

    private int[] MSG = new int[]{JUDGE_CREATETIME, LOAD_EXHORT, ADD_EXHORT_DOC};

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what) {
        case LOAD_EXHORT:
            dismissLoading();
            if (msg.arg1 == SUCCESS) {
                finishWhenLoadErr = false;
                object = (Exhort) msg.obj;
                if (object == null) {
                    object = new Exhort();
                }

                mAttachmentUrl = object.getFileAdd();
                updateUi(object);
                dealDocumentStatus(TextUtils.isEmpty(object.getId()));

            } else {
                finishActivityWithToast();
            }
            break;
        case ADD_EXHORT_DOC:
            handleUploadMessage(msg);
            break;
        case JUDGE_CREATETIME: // 判断文档创建时间
            dismissLoading();
            if (msg.arg1 == SUCCESS) {
                showLoading();
                if (alterType == T_MODIFY) {
                    saveData2Server();
                } else {
                    ExhortManager.getInstance().deleteDoc(object);
                }
            } else {
                final String message = (String) msg.obj;
                showAlterMessageDialog(message, alterType, mDocument.getId(),
                        mPersion.getId(), object.getId(), DOC_TYPE_WARNING);
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
        setContentView(R.layout.ui_word_warning);
        registerMessages(MSG);
    }

    @Override
    protected void onInitView() {
        super.onInitView();
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getBundleExtra(BUNDLE);
            mViolateAgreement = (ViolateAgreement) bundle.getSerializable(AGREEMENT);
        }

        mDeleteLayout.setVisibility(View.GONE);

        final UserCard userCard = MasterManager.getInstance().getUserCard();
        mQjrEt.setText(userCard.getInfoName());
        mQjrdwEt.setText(userCard.getOrgName());
        mQjrEt.setEnabled(false);
        mQjrdwEt.setEnabled(false);
        mTbjjyfEt.setEnabled(true);
    }

    @Override
    protected void onInitData() {
        super.onInitData();

        showLoading();
        ExhortManager.getInstance().loadData(mViolateAgreement, mDocument);
    }

    @Override
    protected ImageView getPreviewImageView() {
        return mPreviewIv;
    }

    @Override
    int[] getFilterDocumentStatus() {
        return new int[]{DocumentStatus.STATUS_3, DocumentStatus.STATUS_4, DocumentStatus.STATUS_9};
    }

    @Event(value = {R.id.qjsj_et, R.id.jzrzjlx_et, R.id.qjyy_et, R.id.tbjjyf_et, R.id.scan_btn,
            R.id.delete_layout, R.id.alter_layout})
    private void click(View view) {
        switch (view.getId()) {
        case R.id.qjsj_et:
            BirthdayPicker.showYearMonthDay(this, new DatePicker.OnYearMonthDayPickListener() {
                @Override
                public void onDatePicked(String year, String month, String day) {
                    String date = String.format("%s-%s-%s", year, month, day);
                    object.setOccurDate(date);
                    mQjsjEt.setText(date);
                }
            });
            break;
        case R.id.jzrzjlx_et:
            SingleChoicePicker.showNation(this, zjlxList, new SingleChoicePicker.OnNationOptionPickListener() {
                @Override
                public void onOptionPicked(int index, Nation nation) {
                    object.setWitnessPaperType(nation.getId());
                    mJzrzjlxEt.setText(nation.getText());
                }
            });
            break;
        case R.id.qjyy_et:
            SingleChoicePicker.showNation(this, qjyyList, new SingleChoicePicker.OnNationOptionPickListener() {
                @Override
                public void onOptionPicked(int index, Nation nation) {
                    object.setNoticeReason(nation.getId());
                    mQjyyEt.setText(nation.getText());
                    if (index == qjyyList.size() - 1) {
                        mQjyyOtherEt.setVisibility(View.VISIBLE);
                    } else {
                        mQjyyOtherEt.setText("");
                        mQjyyOtherEt.setVisibility(View.GONE);
                    }
                }
            });
            break;
        case R.id.tbjjyf_et:
            BirthdayPicker.showYearMonth(this, new DatePicker.OnYearMonthPickListener() {
                @Override
                public void onDatePicked(String year, String month) {
                    String date = String.format("%s-%s-01", year, month);
                    object.setDenyDate(date);
                    mTbjjyfEt.setText(date);
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
            TaskManager.getInstance().judgeCreateTime(object.getId(), DOC_TYPE_WARNING);
            break;
        default: // no code
            break;
        }
    }

    @Override
    protected int onSetTitleFormatResId() {
        return R.string.gjs;
    }

    @Override
    protected boolean checkDataBeforeUpload() {
        object.setFileAdd(mAttachmentUrl);
        object.setOccurPlace(mQjddEt.getText().trim());
        object.setWitness(mJzrEt.getText().trim());
        object.setWitnessPaperNO(mJzrzjhmEt.getText().trim());

        if (!mQjddEt.isMatchRule() || !mJzrEt.isMatchRule() || !mSzlkcsEt.isMatchRule() ||
                !mSzlktsEt.isMatchRule() || !mTbjjcsEt.isMatchRule()) {
            return false;
        }

        if (!TextUtils.isEmpty(mSzlktsEt.getText())) {
            try {
                object.setLeaveDays(Integer.parseInt(mSzlktsEt.getText()));
            } catch (NumberFormatException e) {
                showToast(R.string.input_corret_number);
                e.printStackTrace();
            }
        }

        if (!TextUtils.isEmpty(mSzlkcsEt.getText())) {
            try {
                object.setLeaveCounts(Integer.parseInt(mSzlkcsEt.getText()));
            } catch (NumberFormatException e) {
                showToast(R.string.input_corret_number);
                e.printStackTrace();
            }
        }

        if (!TextUtils.isEmpty(mTbjjcsEt.getText())) {
            try {
                object.setDenyCounts(Integer.parseInt(mTbjjcsEt.getText()));
            } catch (NumberFormatException e) {
                showToast(R.string.input_corret_number);
                e.printStackTrace();
            }
        }

        object.setNoticeReasonRemark(mQjyyOtherEt.getText().trim());

        if (!RegexUtils.isIDCard18(object.getWitnessPaperNO())) {
            showToast(R.string.input_idcard);
            return false;
        }

        if (TextUtils.isEmpty(object.getNoticeReason())) return false;
        if (object.getNoticeReason().equals(qjyyList.get(qjyyList.size() - 1).getId())) {
            return MyTextUtils.isAllNotEmpty(
                    object.getFileAdd(),
                    object.getOccurDate(),
                    object.getOccurPlace(),
                    object.getWitness(),
                    object.getWitnessPaperType(),
                    object.getWitnessPaperNO(),
                    object.getNoticeReason(),
                    object.getNoticeReasonRemark());
        } else {
            return MyTextUtils.isAllNotEmpty(
                    object.getFileAdd(),
                    object.getOccurDate(),
                    object.getOccurPlace(),
                    object.getWitness(),
                    object.getWitnessPaperType(),
                    object.getWitnessPaperNO(),
                    object.getNoticeReason());
        }
    }

    @Override
    protected void uploadData() {
        ExhortManager.getInstance().uploadData(mPersion, mDocument, mViolateAgreement, object);
    }

    private void disableUi(boolean disable) {
        mQjsjEt.setEnabled(!disable);
        mQjddEt.setEnabled(!disable);
        mJzrEt.setEnabled(!disable);
        mJzrzjlxEt.setEnabled(!disable);
        mJzrzjhmEt.setEnabled(!disable);
        mSzlktsEt.setEnabled(!disable);
        mSzlkcsEt.setEnabled(!disable);
        mQjyyEt.setEnabled(!disable);
        mTbjjcsEt.setEnabled(!disable);
        mTbjjyfEt.setEnabled(!disable);
        mScanBtn.setEnabled(!disable);
    }

    private void updateUi(Exhort object) {
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

        mQjsjEt.setText(DateUtil.getInstance().changeDate(object.getOccurDate()));
        mQjddEt.setText(object.getOccurPlace());
        mJzrEt.setText(object.getWitness());
        for (Nation nation : zjlxList) {
            if (nation.getId().equals(object.getWitnessPaperType())) {
                mJzrzjlxEt.setText(nation.getText());
                break;
            }
        }
        mJzrzjhmEt.setText(object.getWitnessPaperNO());
        mSzlktsEt.setText(String.format(Locale.CHINESE, "%d", object.getLeaveDays()));
        mSzlkcsEt.setText(String.format(Locale.CHINESE, "%d", object.getLeaveCounts()));
        for (Nation nation : qjyyList) {
            if (nation.getId().equals(object.getNoticeReason())) {
                mQjyyEt.setText(nation.getText());
                break;
            }
        }
        if (qjyyList.get(2).getId().equals(object.getNoticeReason())) {
            mQjyyOtherEt.setVisibility(View.VISIBLE);
            mQjyyOtherEt.setText(object.getNoticeReasonRemark());
        }
        mTbjjcsEt.setText(String.format(Locale.CHINESE, "%d", object.getDenyCounts()));
        mTbjjyfEt.setText(DateUtil.getInstance().changeDate(object.getDenyDate()));

        mPreviewIv.setVisibility(View.VISIBLE);
        //Glide.with(this).load(PhotoManager.getInstance().IMAGE_URL + object.getFileAdd()).into(mPreviewIv);
        PhotoManager.getInstance().getPic(object.getFileAdd(), mPreviewIv);
    }

}
