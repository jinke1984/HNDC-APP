package cn.com.jinke.wh_drugcontrol.input;

import android.view.View;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.customview.CustomEditText;
import cn.com.jinke.wh_drugcontrol.customview.MultipeChooseView;
import cn.com.jinke.wh_drugcontrol.input.model.CachePerson;
import cn.com.jinke.wh_drugcontrol.input.model.Nation;
import cn.com.jinke.wh_drugcontrol.utils.BirthdayPicker;
import cn.com.jinke.wh_drugcontrol.utils.CommUtils;
import cn.qqtheme.framework.picker.DatePicker;

/**
 * Created by jinke on 2017/7/25.
 */

public class TakeDrugsUI extends BasePersonFragment implements MultipeChooseView.OnCheckChangedListener {

    @ViewInject(R.id.ccxdrq_et)
    private CustomEditText mCcxdrqET; // 初次吸毒日期

    @ViewInject(R.id.drug_multipe_choose_view)
    private MultipeChooseView mDrugView;

    @ViewInject(R.id.fz_multipe_choose_view)
    private MultipeChooseView mCrimeView;

    @ViewInject(R.id.qtdpxq_et)
    private CustomEditText mQtdpxqEt;

    private List<Nation> mDrugList = CommUtils.getInstance().assetsCommToList("/assets/dpzl.json");
    private List<Nation> mCrimeList = CommUtils.getInstance().assetsCommToList("/assets/wffzqk.json");


    @Override
    protected void initData() {
    }

    @Override
    protected void initView(CachePerson person) {
        mCcxdrqET.setText(person.getFirstDrugsDate());
        MultipeChooseView.initData(mDrugView, getActivity(), mDrugList, person.getDrugTypes());
        MultipeChooseView.initData(mCrimeView, getActivity(), mCrimeList, person.getCrimeCondition());
        mDrugView.setOnCheckChangedListener(this);
        if (mDrugView.getCheckedInx().contains(String.valueOf(mDrugList.size() - 1))) {
            mQtdpxqEt.setVisibility(View.VISIBLE);
            mQtdpxqEt.setText(person.getDrugTypesDetail());
        }
    }

    @Override
    void onSaveData(CachePerson person) {
        person.setDrugTypes(mDrugView.getCheckedInx());
        person.setDrugTypesDetail(mQtdpxqEt.getText().trim());
        person.setCrimeCondition(mCrimeView.getCheckedInx());
    }

    @Override
    int getContentViewResId() {
        return R.layout.ui_take_drug;
    }

    @Event(value = {R.id.ccxdrq_et, R.id.pre_btn, R.id.next_btn})
    private void onClick(View view) {
        switch (view.getId()) {
        case R.id.ccxdrq_et:
            BirthdayPicker.showYearMonthDay(getActivity(), new DatePicker.OnYearMonthDayPickListener() {
                @Override
                public void onDatePicked(String year, String month, String day) {
                    mPerson.setFirstDrugsDate(String.format("%s-%s-%s", year, month, day));
                    mCcxdrqET.setText(mPerson.getFirstDrugsDate());
                }
            });
            break;

        case R.id.pre_btn:
            changeToFragment(2);
            break;

        case R.id.next_btn: // 下一步
            changeToFragment(4);
            break;
        default: // no code
            break;
        }
    }

    @Override
    public void onCheckChanged(MultipeChooseView view, int index, Nation nation, boolean isChecked) {
        switch (view.getId()) {
        case R.id.drug_multipe_choose_view:
            if (index == mDrugList.size() - 1) {
                if (isChecked) {
                    mQtdpxqEt.setVisibility(View.VISIBLE);
                } else {
                    mQtdpxqEt.setVisibility(View.GONE);
                }
            }
            break;
        }
    }
}
