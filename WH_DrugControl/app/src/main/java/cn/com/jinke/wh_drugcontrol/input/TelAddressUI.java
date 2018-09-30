package cn.com.jinke.wh_drugcontrol.input;

import android.text.TextUtils;
import android.view.View;

import com.blankj.utilcode.utils.RegexUtils;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.customview.CustomEditText;
import cn.com.jinke.wh_drugcontrol.input.manager.InputManager;
import cn.com.jinke.wh_drugcontrol.input.model.CachePerson;
import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;
import cn.qqtheme.framework.picker.AddressPicker;

/**
 * Created by jinke on 2017/7/25.
 */

public class TelAddressUI extends BasePersonFragment {

    @ViewInject(R.id.hjszd_et)
    private CustomEditText mHjszdET; // 户籍所在地

    @ViewInject(R.id.hjszdxz_et)
    private CustomEditText mHjszdxzET; // 户籍所在地详细地址

    @ViewInject(R.id.xjzd_et)
    private CustomEditText mXjzdET; // 现居住地

    @ViewInject(R.id.xjzdxz_et)
    private CustomEditText mXjzdxzET; // 现居住地详细地址

    @ViewInject(R.id.yddh_et)
    private CustomEditText mYddhET; // 移动电话

    @ViewInject(R.id.gddh_et)
    private CustomEditText mGddhET; // 固定电话

    @ViewInject(R.id.qqhm_et)
    private CustomEditText mQqhmET; // QQ号码

    @ViewInject(R.id.wxh_et)
    private CustomEditText mWxhET; // 微信号

    @ViewInject(R.id.yx_et)
    private CustomEditText mEmailET; // 互联网邮箱

    @Override
    void onSaveData(CachePerson person) {
        person.setHouseholdAddress(mHjszdxzET.getText().trim());
        person.setCurrentAddress(mXjzdxzET.getText().trim());
        person.setCellphone(mYddhET.getText().trim());
        person.setTelePhone(mGddhET.getText().trim());
        person.setQq(mQqhmET.getText().trim());
        person.setWeixin(mWxhET.getText().trim());
        person.setMail(mEmailET.getText().trim());
    }

    @Override
    int getContentViewResId() {
        return R.layout.ui_tel_address;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(CachePerson person) {
        mHjszdET.setText(person.getHouseholdAddressName());
        mHjszdxzET.setText(person.getHouseholdAddress());
        mXjzdET.setText(person.getCurrentAddressName());
        mXjzdxzET.setText(person.getCurrentAddress());
        mYddhET.setText(person.getCellphone());
        mGddhET.setText(person.getTelePhone());
        mQqhmET.setText(person.getQq());
        mWxhET.setText(person.getWeixin());
        mEmailET.setText(person.getMail());
    }

    /**
     * 验证输入有效性
     *
     * @return true：数据有效
     */
    private boolean checkInputValidity() {
        // 移动电话不能为空且是正确的号码
        if (!RegexUtils.isMobileExact(mYddhET.getText())) {
            showToast(R.string.toast_valid_tel);
            return false;
        }

        // 固定电话不为空时，必须是正确的号码
        if (!TextUtils.isEmpty(mGddhET.getText()) && !RegexUtils.isTel(mGddhET.getText())) {
            showToast(R.string.toast_valid_tel);
            return false;
        }

        // email不为空时，必须是正确的email
        if (!TextUtils.isEmpty(mEmailET.getText()) && !RegexUtils.isEmail(mEmailET.getText())) {
            showToast(R.string.toast_valid_email);
            return false;
        }

        return true;
    }

    @Event(value = {R.id.pre_btn, R.id.next_btn, R.id.hjszd_et, R.id.xjzd_et})
    private void onClick(View view) {
        final ArrayList<Province> provinces = InputManager.getInstance().getProvince();

        switch (view.getId()) {

        case R.id.hjszd_et:
            if (provinces == null || provinces.size() == 0) {
                InputManager.getInstance().getAreaList();
            } else {
                AddressPicker picker = new AddressPicker(getActivity(), provinces);
                picker.setHideProvince(false);
                picker.setHideCounty(false);
                picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
                    @Override
                    public void onAddressPicked(Province province, City city, County county) {
                        mPerson.setHouseholdAddressCode(county.getAreaId());
                        mPerson.setHouseholdAddressName(String.format("%s%s%s", province.getName(), city.getName(), county.getName()));
                        mHjszdET.setText(mPerson.getHouseholdAddressName());
                        mHjszdxzET.setText(mPerson.getHouseholdAddressName());
                    }
                });
                picker.show();
            }
            break;

        case R.id.xjzd_et:
            if (provinces == null || provinces.size() == 0) {
                InputManager.getInstance().getAreaList();
            } else {
                AddressPicker picker = new AddressPicker(getActivity(), provinces);
                picker.setHideProvince(false);
                picker.setHideCounty(false);
                picker.setOnAddressPickListener(new AddressPicker.OnAddressPickListener() {
                    @Override
                    public void onAddressPicked(Province province, City city, County county) {
                        mPerson.setCurrentAddressCode(county.getAreaId());
                        mPerson.setCurrentAddressName(String.format("%s%s%s", province.getName(), city.getName(), county.getName()));
                        mXjzdET.setText(mPerson.getCurrentAddressName());
                        mXjzdxzET.setText(mPerson.getCurrentAddressName());
                    }
                });
                picker.show();
            }
            break;

        case R.id.pre_btn:
            changeToFragment(0);
            break;

        case R.id.next_btn: // 下一步
            if (!checkInputValidity()) {
                return;
            }
            changeToFragment(2);
            break;
        default: // no code
            break;
        }
    }

}
