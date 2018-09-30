package cn.com.jinke.wh_drugcontrol.word;

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
import cn.com.jinke.wh_drugcontrol.word.manager.RulesManager;
import cn.com.jinke.wh_drugcontrol.word.model.Rule;
import cn.qqtheme.framework.picker.DatePicker;

/**
 * 人员守则管理
 * Created by jinke on 2017/8/24.
 */

@Route(path = RouteUtils.R_WORD_RULES_UI)
public class PersionRulesUI extends BaseWordUI {

    @ViewInject(R.id.ssd_reason_et)
    private CustomEditText mSsdEt; // 受送达人

    @ViewInject(R.id.sd_date_et)
    private CustomEditText mSdDateEt; // 送达时间

    @ViewInject(R.id.ssd_address_et)
    private CustomEditText mSdAddressEt; // 送达地点

    @ViewInject(R.id.sd_type_et)
    private CustomEditText mSdTypeEt; // 送达方式

    @ViewInject(R.id.sd_type_detail_et)
    private CustomEditText mSdTypeDetailEt; // 其他具体方式

    @ViewInject(R.id.js_et)
    private CustomEditText mJsEt; // 是否拒收

    @ViewInject(R.id.js_reason_et)
    private CustomEditText mJsReasonEt; // 拒收原因

    @ViewInject(R.id.ds_et)
    private CustomEditText mDsEt; // 是否代收

    @ViewInject(R.id.ds_peason_et)
    private CustomEditText mDsPersonEt; // 代收人

    @ViewInject(R.id.ds_reason_et)
    private CustomEditText mDsReasonEt; // 代收原因

    @ViewInject(R.id.gx_et)
    private CustomEditText mGxEt; // 与受送达人关系

    @ViewInject(R.id.jzr_et)
    private CustomEditText mJzrEt; // 见证人

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

    private ArrayList<Nation> yesnoList = CommUtils.getInstance().getYesNo10AssetsList();
    private ArrayList<Nation> sdfsList = CommUtils.getInstance().getSdfsAssetsList(); // 送达方式list

    private int alterType = T_MODIFY; // 1：修改；2：删除

    private Rule object = new Rule();

    private int[] MSG = new int[]{JUDGE_CREATETIME, LOAD_RULE, ADD_RULE_DOC};

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what) {
        case LOAD_RULE:
            dismissLoading();
            if (msg.arg1 == SUCCESS) {
                finishWhenLoadErr = false;
                object = (Rule) msg.obj;

                if (object == null) {
                    object = new Rule();
                }

                mAttachmentUrl = object.getFileAdd();
                updateUi(object);
                dealDocumentStatus(TextUtils.isEmpty(object.getId()));

            } else {
                finishActivityWithToast();
            }
            break;
        case ADD_RULE_DOC:
            handleUploadMessage(msg);
            break;
        case JUDGE_CREATETIME: // 判断文档创建时间
            dismissLoading();
            if (msg.arg1 == SUCCESS) {
                showLoading();
                if (alterType == T_MODIFY) {
                    saveData2Server();
                } else {
                    RulesManager.getInstance().deleteDoc(object);
                }
            } else {
                final String message = (String) msg.obj;
                showAlterMessageDialog(message, alterType, mDocument.getId(),
                        mPersion.getId(), object.getId(), DOC_TYPE_PERSON_RULES);
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
        setContentView(R.layout.ui_word_persion_rules);
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
        mSsdEt.setText(mPersion.getRealName());
        mSsdEt.setEnabled(false);
    }

    @Override
    protected void onInitData() {
        super.onInitData();

        showLoading();
        RulesManager.getInstance().loadData(mDocument);
    }

    @Event(value = {R.id.sd_date_et, R.id.sd_type_et, R.id.js_et, R.id.ds_et, R.id.scan_btn,
            R.id.delete_layout, R.id.alter_layout})
    private void click(View view) {
        switch (view.getId()) {
        case R.id.sd_date_et:
            BirthdayPicker.showYearMonthDay(this, new DatePicker.OnYearMonthDayPickListener() {
                @Override
                public void onDatePicked(String year, String month, String day) {
                    String date = String.format("%s-%s-%s", year, month, day);
                    object.setSendDate(date);
                    mSdDateEt.setText(date);
                }
            });
            break;
        case R.id.sd_type_et:
            SingleChoicePicker.showNation(this, sdfsList, new SingleChoicePicker.OnNationOptionPickListener() {
                @Override
                public void onOptionPicked(int index, Nation nation) {
                    object.setSendType(nation.getId());
                    mSdTypeEt.setText(nation.getText());
                    if (index == sdfsList.size() - 1) {
                        mSdTypeDetailEt.setVisibility(View.VISIBLE);
                    } else {
                        mSdTypeDetailEt.setText("");
                        mSdTypeDetailEt.setVisibility(View.GONE);
                    }
                }
            });
            break;
        case R.id.js_et:
            SingleChoicePicker.showNation(this, yesnoList, new SingleChoicePicker.OnNationOptionPickListener() {
                @Override
                public void onOptionPicked(int index, Nation nation) {
                    object.setIsDeny(nation.getId());
                    mJsEt.setText(nation.getText());
                    if (index == 0) {
                        mJsReasonEt.setText("");
                        mJsReasonEt.setVisibility(View.GONE);
                    } else {
                        mJsReasonEt.setVisibility(View.VISIBLE);
                    }
                }
            });
            break;
        case R.id.ds_et:
            SingleChoicePicker.showNation(this, yesnoList, new SingleChoicePicker.OnNationOptionPickListener() {
                @Override
                public void onOptionPicked(int index, Nation nation) {
                    mDsEt.setText(nation.getText());
                    object.setIsReplace(nation.getId());
                    if (index == 0) {
                        mDsPersonEt.setVisibility(View.GONE);
                        mDsReasonEt.setVisibility(View.GONE);
                        mGxEt.setVisibility(View.GONE);
                    } else {
                        mDsPersonEt.setVisibility(View.VISIBLE);
                        mDsReasonEt.setVisibility(View.VISIBLE);
                        mGxEt.setVisibility(View.VISIBLE);
                    }
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
            TaskManager.getInstance().judgeCreateTime(object.getId(), DOC_TYPE_PERSON_RULES);
            break;
        default: // no code
            break;
        }
    }

    @Override
    protected int onSetTitleFormatResId() {
        return R.string.rysz;
    }

    @Override
    protected boolean checkDataBeforeUpload() {
        object.setFileAdd(mAttachmentUrl);

        object.setSendLaction(mSdAddressEt.getText().trim());
        object.setDenyReason(mJsReasonEt.getText().trim());
        object.setReplacePerson(mDsPersonEt.getText().trim());
        object.setReplaceReason(mDsReasonEt.getText().trim());
        object.setRelation(mGxEt.getText().trim());
        object.setWitness(mJzrEt.getText().trim());

        if (MyTextUtils.isAllNotEmpty(
                object.getFileAdd(),
                object.getSendDate(),
                object.getSendLaction(),
                object.getSendType(),
                object.getIsDeny(),
                object.getIsReplace(),
                object.getWitness())) {

            if (object.getSendType().equals(sdfsList.get(sdfsList.size() - 1).getId())
                    && MyTextUtils.isAllEmpty(object.getSendTypeDetail()))
                return false;

            if (object.getIsDeny().equals(yesnoList.get(yesnoList.size() - 1).getId())
                    && TextUtils.isEmpty(object.getDenyReason()))
                return false;

            return !object.getIsReplace().equals(yesnoList.get(yesnoList.size() - 1).getId())
                    || MyTextUtils.isAllNotEmpty(
                    object.getReplacePerson(),
                    object.getReplaceReason(),
                    object.getRelation());

        } else {
            return false;
        }
    }

    @Override
    protected void uploadData() {
        RulesManager.getInstance().uploadData(mPersion, mDocument, object);
    }

    private void disableUi(boolean disable) {
        mSdDateEt.setEnabled(!disable);
        mSdAddressEt.setEnabled(!disable);
        mSdTypeEt.setEnabled(!disable);
        mJsEt.setEnabled(!disable);
        mJsReasonEt.setEnabled(!disable);
        mDsEt.setEnabled(!disable);
        mDsReasonEt.setEnabled(!disable);
        mJzrEt.setEnabled(!disable);
        mScanBtn.setEnabled(!disable);
    }

    private void updateUi(Rule rule) {
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

        mSsdEt.setText(mPersion.getRealName());
        mSdDateEt.setText(DateUtil.getInstance().changeDate(rule.getSendDate()));
        mSdAddressEt.setText(rule.getSendLaction());
        int len = sdfsList.size();
        for (int inx = 0; inx < len; ++inx) {
            Nation nation = sdfsList.get(inx);
            if (nation.getId().equals(rule.getSendType())) {
                mSdTypeEt.setText(nation.getText());
                if (inx == len - 1) {
                    mSdTypeDetailEt.setVisibility(View.VISIBLE);
                    mSdTypeDetailEt.setText(rule.getSendTypeDetail());
                }
                break;
            }
        }
        for (Nation nation : yesnoList) {
            if (nation.getId().equals(rule.getIsDeny())) {
                mJsEt.setText(nation.getText());
                if (rule.getIsDeny().equals(yesnoList.get(yesnoList.size() - 1).getId())) {
                    mJsReasonEt.setVisibility(View.VISIBLE);
                    mJsReasonEt.setText(rule.getDenyReason());
                }
                break;
            }
        }
        for (Nation nation : yesnoList) {
            if (nation.getId().equals(rule.getIsReplace())) {
                mDsEt.setText(nation.getText());
                if (rule.getIsReplace().equals(yesnoList.get(yesnoList.size() - 1).getId())) {
                    mDsPersonEt.setVisibility(View.VISIBLE);
                    mDsReasonEt.setVisibility(View.VISIBLE);
                    mGxEt.setVisibility(View.VISIBLE);
                    mDsPersonEt.setText(rule.getReplacePerson());
                    mDsReasonEt.setText(rule.getReplaceReason());
                    mGxEt.setText(rule.getRelation());
                }
                break;
            }
        }
        mJzrEt.setText(rule.getWitness());
        mPreviewIv.setVisibility(View.VISIBLE);
        //Glide.with(this).load(PhotoManager.getInstance().IMAGE_URL + rule.getFileAdd()).into(mPreviewIv);
        PhotoManager.getInstance().getPic(rule.getFileAdd(), mPreviewIv);
    }

}
