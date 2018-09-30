package cn.com.jinke.wh_drugcontrol.input;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseFragmentUI;
import cn.com.jinke.wh_drugcontrol.input.model.CachePerson;
import cn.com.jinke.wh_drugcontrol.manager.FragmentTabManager;
import cn.com.jinke.wh_drugcontrol.manager.FragmentTabManager.FragmentTab;
import cn.com.jinke.wh_drugcontrol.manager.FragmentTabManager.OnTabChangedListener;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;

/**
 * Created by jinke on 2017/7/25.
 */

@Route(path = RouteUtils.R_ADD_PERSION_UI)
public class AddNewPersionUI extends ProjectBaseFragmentUI implements
        OnTabChangedListener, FragmentTabManager.OnTabChangeListener, CodeConstants {

    @ViewInject(R.id.base_info_ly)
    private RelativeLayout mBaseInfoBtn;

    @ViewInject(R.id.tel_address_ly)
    private RelativeLayout mTelAddressBtn;

    @ViewInject(R.id.skill_job_ly)
    private RelativeLayout mSkillJobBtn;

    @ViewInject(R.id.take_drugs_ly)
    private RelativeLayout mTakeDrugBtn;

    @ViewInject(R.id.other_info_ly)
    private RelativeLayout mOtherInfoBtn;

    @ViewInject(R.id.base_info_iv)
    private ImageView mBaseInfoIv;

    @ViewInject(R.id.tel_address_iv)
    private ImageView mTelAddressIv;

    @ViewInject(R.id.skill_job_iv)
    private ImageView mSkillJobIv;

    @ViewInject(R.id.take_drugs_iv)
    private ImageView mTakeDrugIv;

    @ViewInject(R.id.other_info_iv)
    private ImageView mOtherInfoIv;

    private FragmentTabManager mTabManager;

    private CachePerson mPerson = null;

    private int[] MSG = new int[]{AREA_LIST, ACCESS_NET_FAILED};

    @Override
    protected boolean handleMessage(Message msg) {
        switch (msg.what) {
        case AREA_LIST:
            dismissLoading();
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
        setContentView(R.layout.ui_addnewpersion);
        registerMessages(MSG);

        if (getIntent() != null) {
            mPerson = (CachePerson) getIntent().getSerializableExtra(EXTRA_PERSON);
        }
        if (mPerson == null) {
            final UserCard userCard = MasterManager.getInstance().getUserCard();
            mPerson = new CachePerson(userCard.getUserId());
        }

        Bundle bundle = new Bundle();
        bundle.putSerializable(EXTRA_PERSON, mPerson);

        List<FragmentTab> items = new ArrayList<>();
        items.add(new FragmentTab(BaseInfoUI.class, bundle, BaseInfoUI.class.getName()));
        items.add(new FragmentTab(TelAddressUI.class, bundle, TelAddressUI.class.getName()));
        items.add(new FragmentTab(SkillJobUI.class, bundle, SkillJobUI.class.getName()));
        items.add(new FragmentTab(TakeDrugsUI.class, bundle, TakeDrugsUI.class.getName()));
        items.add(new FragmentTab(OtherInfoUI.class, bundle, OtherInfoUI.class.getName()));
        int containerId = R.id.add_content_container;
        mTabManager = new FragmentTabManager(this, getSupportFragmentManager(), items, containerId);
        mTabManager.setOnTabChangedListener(this);
        mTabManager.setCurrent(0);
    }

    @Override
    protected void onInitView() {
        super.onInitView();
        Header header = getHeader();
        if (header != null) {
            header.rightLayout.setVisibility(View.GONE);
            header.titleText.setText(R.string.addperson);
        }
    }

    @Override
    public void onTabChanged(Fragment current, int index) {
        changeInfoBtnBackground();
    }

    @Override
    public void onTabChange(Fragment current, int toIndex) {
        mTabManager.setCurrent(toIndex);
    }

    @Event(value = {R.id.base_info_ly, R.id.tel_address_ly, R.id.skill_job_ly, R.id.take_drugs_ly,
            R.id.other_info_ly}, type = View.OnClickListener.class)
    private void onClick(View view) {
        int id = view.getId();
//        switch (id) {
//        case R.id.base_info_ly:
//            mTabManager.setCurrent(BaseInfoUI.class.getName());
//            break;
//        case R.id.tel_address_ly:
//            mTabManager.setCurrent(TelAddressUI.class.getName());
//            break;
//        case R.id.skill_job_ly:
//            mTabManager.setCurrent(SkillJobUI.class.getName());
//            break;
//        case R.id.take_drugs_ly:
//            mTabManager.setCurrent(TakeDrugsUI.class.getName());
//            break;
//        case R.id.other_info_ly:
//            mTabManager.setCurrent(OtherInfoUI.class.getName());
//            break;
//        default:
//            break;
//        }
    }

    /**
     * 改变顶部button状态（背景颜色）
     *
     * @param infoBtn 要改变的button
     * @param status  状态（0：未填写-灰色；1：正在录入-黄色；2：录入完成-绿色）
     */
    private void changeInfoBtnStatus(View infoBtn, int status) {
        switch (status) {
        case COMPLETED_NONE:
            infoBtn.setBackgroundResource(R.drawable.add_gray);
            break;
        case COMPLETED_DOING:
            infoBtn.setBackgroundResource(R.drawable.add_yelow);
            break;
        case COMPLETED_DONE:
            infoBtn.setBackgroundResource(R.drawable.add_green);
            break;
        default:
            // No code
            break;
        }
    }

    private void changeInfoBtnBackground() {
        changeInfoBtnStatus(mBaseInfoIv, mPerson.getCacheState1());
        changeInfoBtnStatus(mTelAddressIv, mPerson.getCacheState2());
        changeInfoBtnStatus(mSkillJobIv, mPerson.getCacheState3());
        changeInfoBtnStatus(mTakeDrugIv, mPerson.getCacheState4());
        changeInfoBtnStatus(mOtherInfoIv, mPerson.getCacheState5());
    }

}
