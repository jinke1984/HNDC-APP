package cn.com.jinke.wh_drugcontrol.input;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.blankj.utilcode.utils.RegexUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.customview.CustomEditText;
import cn.com.jinke.wh_drugcontrol.customview.MultipeChooseView;
import cn.com.jinke.wh_drugcontrol.input.manager.InputManager;
import cn.com.jinke.wh_drugcontrol.input.manager.PersonManager;
import cn.com.jinke.wh_drugcontrol.input.model.CachePerson;
import cn.com.jinke.wh_drugcontrol.input.model.Nation;
import cn.com.jinke.wh_drugcontrol.utils.BirthdayPicker;
import cn.com.jinke.wh_drugcontrol.utils.CommUtils;
import cn.com.jinke.wh_drugcontrol.utils.SingleChoicePicker;
import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.AddressPicker;
import cn.qqtheme.framework.picker.DatePicker;

/**
 * Created by jinke on 2017/7/25.
 */

public class BaseInfoUI extends BasePersonFragment implements
        RadioGroup.OnCheckedChangeListener, MultipeChooseView.OnCheckChangedListener {

    @ViewInject(R.id.idCard_et)
    private CustomEditText mIdCardET; // 身份证

    @ViewInject(R.id.name_et)
    private CustomEditText mNameET; // 姓名

    @ViewInject(R.id.before_name_et)
    private CustomEditText mBeforeNameET; // 曾用名

    @ViewInject(R.id.other_name_et)
    private CustomEditText mOtherNameET; // 别名

    @ViewInject(R.id.sex_radiogroup)
    private RadioGroup mSexRG; // 性别
    @ViewInject(R.id.nan)
    private RadioButton mNanRB;
    @ViewInject(R.id.nv)
    private RadioButton mNvRB;

    @ViewInject(R.id.birthday_et)
    private CustomEditText mBirthdayET; // 出生日期

    @ViewInject(R.id.sssq_et)
    private CustomEditText mSssqET; // 所属社区

    @ViewInject(R.id.mz_et)
    private CustomEditText mNationET; // 民族

    @ViewInject(R.id.sg_et)
    private CustomEditText mSgET; // 身高

    @ViewInject(R.id.jz_radiogroup)
    private RadioGroup mJzRG; // 是否与父母同住
    @ViewInject(R.id.shi)
    private RadioButton mShiRB;
    @ViewInject(R.id.fou)
    private RadioButton mFouRB;

    @ViewInject(R.id.hy_et)
    private CustomEditText mHyzkET; // 婚姻状况

    @ViewInject(R.id.stzk_et)
    private CustomEditText mStzkET; // 身体状况

    @ViewInject(R.id.stxx_et)
    private CustomEditText mStxxET; // 身体信息情况

    @ViewInject(R.id.multipe_choose_view)
    private MultipeChooseView mChooseView; // 家庭情况

    @ViewInject(R.id.wlqk_container)
    private LinearLayout mWlqkContainer;
    @ViewInject(R.id.wlqk_rg)
    private RadioGroup mWlqkRg;
    @ViewInject(R.id.jcwl)
    private RadioButton mJcwlRb;
    @ViewInject(R.id.oewl)
    private RadioButton mOewlRb;
    @ViewInject(R.id.bwl)
    private RadioButton mBwlRb;

    private List<Nation> mJtqkList = CommUtils.getInstance().assetsCommToList("/assets/jtqk.json");
    private List<Nation> mNationList = CommUtils.getInstance().assetsCommToList("/assets/nation.json");
    private List<Nation> mStzkList = CommUtils.getInstance().assetsCommToList("/assets/stzk.json");
    private List<Nation> mHyzkList = CommUtils.getInstance().assetsCommToList("/assets/hyzk.json");

    private ArrayList<Province> mProvinceList = null;

    private PersonManager personManager = PersonManager.getInstance();

    private int[] MSG = new int[]{LOAD_PERSONBYIDCARD, AREA_LIST, ACCESS_NET_FAILED};

    private boolean isPersonExist = true;

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what) {
        case LOAD_PERSONBYIDCARD:
            dismissLoading();
            if (msg.arg1 == SUCCESS) {
                CachePerson person = (CachePerson) msg.obj;
                if (person != null && !TextUtils.isEmpty(person.getId())) {
                    showToast(R.string.person_exist);
                } else {
                    showToast(R.string.person_not_exist);
                    isPersonExist = false;
                }
            }
            break;
        case AREA_LIST:
            dismissLoading();
            mProvinceList = InputManager.getInstance().getProvince();
            showAddressDialog();
            break;
        case ACCESS_NET_FAILED:
            dismissLoading();
            break;
        default:
            break;

        }
        return super.handleMessage(msg);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerMessages(MSG);
    }

    @Override
    int getContentViewResId() {
        return R.layout.ui_base_info;
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView(final CachePerson person) {

        mIdCardET.setText(person.getIdentityCard());
        mNameET.setText(person.getRealName());
        mOtherNameET.setText(person.getNickname());
        mBeforeNameET.setText(person.getPreRealName());
        if ("1".equals(person.getSex())) {
            mNanRB.setChecked(true);
        } else if ("2".equals(person.getSex())) {
            mNvRB.setChecked(true);
        }
        mBirthdayET.setText(person.getBirthdate());
        mSssqET.setText(person.getCommunityName());
        for (Nation nation : mNationList) {
            if (nation.getId().equals(person.getNation())) {
                mNationET.setText(nation.getText());
                break;
            }
        }
        mSgET.setText(person.getHeight().toString());
        if ("1".equals(person.getLivingWithParents())) {
            mShiRB.setChecked(true);
        } else if ("0".equals(person.getLivingWithParents())) {
            mFouRB.setChecked(true);
        }
        for (Nation nation : mHyzkList) {
            if (nation.getId().equals(person.getMarriage())) {
                mHyzkET.setText(nation.getText());
                break;
            }
        }
        for (Nation nation : mStzkList) {
            if (nation.getId().equals(person.getHealthCondition())) {
                mStzkET.setText(nation.getText());
                break;
            }
        }
        mStxxET.setText(person.getHealthConditionDetail());
        MultipeChooseView.initData(mChooseView, getActivity(), mJtqkList, person.getFamilyCondition());
        mChooseView.setOnCheckChangedListener(this);
        if (mChooseView.getCheckedInx().contains(String.valueOf(mJtqkList.size() - 1))) {
            mWlqkContainer.setVisibility(View.VISIBLE);
        }
        if ("81".equals(person.getFamilyConditionDetail())) {
            mJcwlRb.setChecked(true);
        } else if ("82".equals(person.getFamilyConditionDetail())) {
            mOewlRb.setChecked(true);
        } else if ("83".equals(person.getFamilyConditionDetail())) {
            mBwlRb.setChecked(true);
        }
        mSexRG.setOnCheckedChangeListener(this);
        mJzRG.setOnCheckedChangeListener(this);
        mWlqkRg.setOnCheckedChangeListener(this);
    }

    private void getProvinceList() {
        if (isNetworkConnected()) {
            showLoading();
            InputManager.getInstance().getAreaList();
        } else {
            showToast(R.string.common_network_unavailable);
        }
    }

    @Override
    void onSaveData(CachePerson person) {
        person.setIdentityCard(mIdCardET.getText().trim());
        person.setRealName(mNameET.getText().trim());
        person.setPreRealName(mBeforeNameET.getText().trim());
        person.setNickname(mOtherNameET.getText().trim());
        if (!TextUtils.isEmpty(mSgET.getText())) {
            person.setHeight(Double.parseDouble(mSgET.getText().trim()));
        }
        person.setHealthConditionDetail(mStxxET.getText().trim());
        person.setFamilyCondition(mChooseView.getCheckedInx());
    }

    private boolean checkInputValidity() {
        if (!RegexUtils.isIDCard18(mIdCardET.getText().trim()) || isPersonExist) {
            showToast(R.string.toast_input_idcard);
            return false;
        }

        if (!RegexUtils.isMatch("^[0-9]{2,3}(.[0-9]{1,2})?$", mSgET.getText())) {
            showToast(R.string.toast_valid_height);
            return false;
        }

        return true;
    }

    @Event(value = {R.id.find_idcard_btn, R.id.birthday_et, R.id.mz_et, R.id.stzk_et, R.id.hy_et,
            R.id.sssq_et, R.id.next_btn},
            type = View.OnClickListener.class)
    private void onClick(View view) {
        switch (view.getId()) {
        case R.id.find_idcard_btn:
            if (!RegexUtils.isIDCard18(mIdCardET.getText().trim())) {
                showToast(R.string.input_idcard);
                return;
            }
            showLoading();
            personManager.findPersonByIdcard(mIdCardET.getText().trim());
            break;

        case R.id.birthday_et: // 出生日期
            BirthdayPicker.showYearMonthDay(getActivity(), new DatePicker.OnYearMonthDayPickListener() {
                @Override
                public void onDatePicked(String year, String month, String day) {
                    mPerson.setBirthdate(String.format("%s-%s-%s", year, month, day));
                    mBirthdayET.setText(mPerson.getBirthdate());
                }
            });
            break;
        case R.id.mz_et: // 民族
            SingleChoicePicker.showNation(getActivity(), mNationList, new SingleChoicePicker.OnNationOptionPickListener() {
                @Override
                public void onOptionPicked(int index, Nation nation) {
                    mPerson.setNation(nation.getId());
                    mNationET.setText(nation.getText());
                }
            });
            break;
        case R.id.stzk_et: // 身体状况
            SingleChoicePicker.showNation(getActivity(), mStzkList, new SingleChoicePicker.OnNationOptionPickListener() {
                @Override
                public void onOptionPicked(int index, Nation nation) {
                    mPerson.setHealthCondition(nation.getId());
                    mStzkET.setText(nation.getText());
                }
            });
            break;
        case R.id.hy_et: // 婚姻状况
            SingleChoicePicker.showNation(getActivity(), mHyzkList, new SingleChoicePicker.OnNationOptionPickListener() {
                @Override
                public void onOptionPicked(int index, Nation nation) {
                    mPerson.setMarriage(nation.getId());
                    mHyzkET.setText(nation.getText());
                }
            });
            break;
        case R.id.sssq_et: // 所属社区
            if (mProvinceList != null && mProvinceList.size() > 0) {
                showAddressDialog();
            } else {
                getProvinceList();
            }
            break;

        case R.id.next_btn: // 下一步
            if (!checkInputValidity()) {
                return;
            }
            changeToFragment(1);
            break;
        default:
            break;
        }
    }

    private void showAddressDialog() {
        if (mProvinceList != null && mProvinceList.size() > 0) {
            AddressPicker picker = new AddressPicker(getActivity(), mProvinceList);
            picker.setHideProvince(false);
            picker.setHideCounty(false);
            picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
                @Override
                public void onAddressPicked(Province province, City city, County county) {
                    mPerson.setCommunityID(county.getAreaId());
                    mPerson.setCommunityCode(String.format("%s%s%s", province.getName(), city.getName(), county.getName()));
                    mPerson.setCommunityName(mPerson.getCommunityCode());
                    mSssqET.setText(mPerson.getCommunityCode());
                }
            });
            picker.show();
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (group.getId()) {
        case R.id.sex_radiogroup:
            switch (checkedId) {
            case R.id.nan:
                mPerson.setSex("1");
                break;
            case R.id.nv:
                mPerson.setSex("2");
                break;
            }
            break;
        case R.id.jz_radiogroup:
            switch (checkedId) {
            case R.id.shi:
                mPerson.setLivingWithParents("1");
                break;
            case R.id.fou:
                mPerson.setLivingWithParents("0");
                break;
            }
            break;
        case R.id.wlqk_rg:
            switch (checkedId) {
            case R.id.jcwl:
                mPerson.setFamilyConditionDetail("81");
                break;
            case R.id.oewl:
                mPerson.setFamilyConditionDetail("82");
                break;
            case R.id.bwl:
                mPerson.setFamilyConditionDetail("83");
                break;
            }
            break;
        }
    }

    @Override
    public void onCheckChanged(MultipeChooseView view, int index, Nation nation, boolean isChecked) {
        switch (view.getId()) {
        case R.id.multipe_choose_view:
            if (index == mJtqkList.size() - 1) {
                if (isChecked) {
                    mWlqkContainer.setVisibility(View.VISIBLE);
                } else {
                    mWlqkContainer.setVisibility(View.GONE);
                }
            }
            break;
        }
    }
}
