package cn.com.jinke.wh_drugcontrol.input;

import android.support.annotation.IdRes;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.customview.CustomEditText;
import cn.com.jinke.wh_drugcontrol.customview.MultipeChooseView;
import cn.com.jinke.wh_drugcontrol.input.model.CachePerson;
import cn.com.jinke.wh_drugcontrol.input.model.Nation;
import cn.com.jinke.wh_drugcontrol.utils.CommUtils;
import cn.com.jinke.wh_drugcontrol.utils.SingleChoicePicker;

/**
 * Created by jinke on 2017/7/25.
 */

public class SkillJobUI extends BasePersonFragment implements
        RadioGroup.OnCheckedChangeListener, MultipeChooseView.OnCheckChangedListener {

    @ViewInject(R.id.zzmm_et)
    private CustomEditText mZzmmET; // 政治面貌

    @ViewInject(R.id.zzmmxz_et)
    private CustomEditText mZzmmxqET; // 政治面貌详情

    @ViewInject(R.id.whcd_et)
    private CustomEditText mWhcdET; // 文化程度

    @ViewInject(R.id.training_radiogroup)
    private RadioGroup mTrainingRG; // 是否参加过职业培训
    @ViewInject(R.id.yes)
    private RadioButton mTrainingYesRB;
    @ViewInject(R.id.no)
    private RadioButton mTrainingNoRB;

    @ViewInject(R.id.jyqk_et)
    private CustomEditText mJyqkET; // 就业情况

    @ViewInject(R.id.srqk_et)
    private CustomEditText mSrqkEt; // 收入情况

    @ViewInject(R.id.grjl_et)
    private EditText mGrjlEt;

    @ViewInject(R.id.tcjn_multi)
    private MultipeChooseView mTcjnMulti;

    @ViewInject(R.id.tcjn_qt_et)
    private CustomEditText mQttcjnEt;

    @ViewInject(R.id.shbzqk_multi)
    private MultipeChooseView mShbzqkMulti;

    @ViewInject(R.id.qtshbzqk_multi)
    private MultipeChooseView mQtshbzMulti;

    private List<Nation> mZzmmList = CommUtils.getInstance().assetsCommToList("/assets/zzmm.json");
    private List<Nation> mWhcdList = CommUtils.getInstance().assetsCommToList("/assets/education.json");
    private List<Nation> mTcjnList = CommUtils.getInstance().assetsCommToList("/assets/tcjn.json");
    private List<Nation> mJyqkList = CommUtils.getInstance().assetsCommToList("/assets/jyqk.json");
    private List<Nation> mSrqkList = CommUtils.getInstance().assetsCommToList("/assets/srqk.json");
    private List<Nation> mShbzqkList = CommUtils.getInstance().assetsCommToList("/assets/shbzqk.json");
    private List<Nation> mQtshbzqkList = CommUtils.getInstance().assetsCommToList("/assets/qtshbzqk.json");


    @Event(value = {R.id.zzmm_et, R.id.whcd_et, R.id.jyqk_et, R.id.srqk_et, R.id.pre_btn, R.id.next_btn})
    private void onClick(View view) {

        switch (view.getId()) {
        case R.id.zzmm_et:
            SingleChoicePicker.showNation(getActivity(), mZzmmList, new SingleChoicePicker.OnNationOptionPickListener() {
                @Override
                public void onOptionPicked(int index, Nation nation) {
                    mPerson.setPoliticalLandscape(nation.getId());
                    mZzmmET.setText(nation.getText());
                }
            });
            break;
        case R.id.whcd_et:
            SingleChoicePicker.showNation(getActivity(), mWhcdList, new SingleChoicePicker.OnNationOptionPickListener() {
                @Override
                public void onOptionPicked(int index, Nation nation) {
                    mPerson.setEducation(nation.getId());
                    mWhcdET.setText(nation.getText());
                }
            });
            break;
        case R.id.jyqk_et:
            SingleChoicePicker.showNation(getActivity(), mJyqkList, new SingleChoicePicker.OnNationOptionPickListener() {
                @Override
                public void onOptionPicked(int index, Nation nation) {
                    mPerson.setEmploymentCondition(nation.getId());
                    mJyqkET.setText(nation.getText());
                }
            });
            break;
        case R.id.srqk_et:
            SingleChoicePicker.showNation(getActivity(), mSrqkList, new SingleChoicePicker.OnNationOptionPickListener() {
                @Override
                public void onOptionPicked(int index, Nation nation) {
                    mPerson.setIncome(nation.getId());
                    mSrqkEt.setText(nation.getText());
                }
            });
            break;
        case R.id.pre_btn:
            changeToFragment(1);
            break;
        case R.id.next_btn: // 下一步
            changeToFragment(3);
            break;
        default: // no code
            break;
        }
    }

    @Override
    void onSaveData(CachePerson person) {
        person.setPoliticalLandscapeDetail(mZzmmxqET.getText().trim());
        person.setSkills(mTcjnMulti.getCheckedInx());
        person.setSkillsDetail(mQttcjnEt.getText().trim());
        person.setSocialSecurity(mShbzqkMulti.getCheckedInx());
        person.setSocialSecurityDetail(mQtshbzMulti.getCheckedInx());
        person.setPersonalResume(mGrjlEt.getText().toString().trim());
    }

    @Override
    int getContentViewResId() {
        return R.layout.ui_skill_job;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView(CachePerson person) {
        for (Nation nation : mZzmmList) {
            if (nation.getId().equals(person.getPoliticalLandscape())) {
                mZzmmET.setText(nation.getText());
                break;
            }
        }
        mZzmmxqET.setText(person.getPoliticalLandscapeDetail());
        for (Nation nation : mWhcdList) {
            if (nation.getId().equals(person.getEducation())) {
                mWhcdET.setText(nation.getText());
                break;
            }
        }
        if ("0".equals(person.getVocationalTraining())) {
            mTrainingNoRB.setChecked(true);
        } else if ("1".equals(person.getVocationalTraining())) {
            mTrainingYesRB.setChecked(true);
        }
        for (Nation nation : mJyqkList) {
            if (nation.getId().equals(person.getEmploymentCondition())) {
                mJyqkET.setText(nation.getText());
                break;
            }
        }
        for (Nation nation : mSrqkList) {
            if (nation.getId().equals(person.getIncome())) {
                mSrqkEt.setText(nation.getText());
                break;
            }
        }
        mGrjlEt.setText(person.getPersonalResume());
        MultipeChooseView.initData(mTcjnMulti, getActivity(), mTcjnList, person.getSkills());
        MultipeChooseView.initData(mShbzqkMulti, getActivity(), mShbzqkList, person.getSocialSecurity());
        MultipeChooseView.initData(mQtshbzMulti, getActivity(), mQtshbzqkList, person.getSocialSecurityDetail());
        mTcjnMulti.setOnCheckChangedListener(this);
        mShbzqkMulti.setOnCheckChangedListener(this);
        mQtshbzMulti.setOnCheckChangedListener(this);
    }

    @Override
    public void onCheckChanged(MultipeChooseView view, int index, Nation nation, boolean isChecked) {
        switch (view.getId()) {
        case R.id.tcjn_multi:
            if (index == mTcjnList.size() - 1) {
                if (isChecked) {
                    mQttcjnEt.setVisibility(View.VISIBLE);
                } else {
                    mQttcjnEt.setVisibility(View.GONE);
                }
            }
            break;
        case R.id.shbzqk_multi:
            if (nation.getId().equals("5")) {
                if (isChecked) {
                    mQtshbzMulti.setVisibility(View.VISIBLE);
                } else {
                    mQtshbzMulti.setVisibility(View.GONE);
                }
            }
            break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

        switch (group.getId()) {
        case R.id.training_radiogroup:
            switch (checkedId) {
            case R.id.yes:
                mPerson.setVocationalTraining("1");
                break;
            case R.id.no:
                mPerson.setVocationalTraining("0");
                break;
            }
            break;
        }
    }
}
