package cn.com.jinke.wh_drugcontrol.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseFragment;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.utils.CommUtils;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;

/**
 * Created by jinke on 2017/7/10.
 */

public class MeFragment extends ProjectBaseFragment {

    @ViewInject(R.id.current_area_tv)
    private TextView mCurrentArea = null;

    @ViewInject(R.id.area_person)
    private TextView mAreaPersion = null;

    private UserCard mUserCard = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ui_me, container, false);
        x.view().inject(this, view);
        initHeader(view);
        initHeadView();
        initData();
        return view;
    }

    private void initHeadView(){
        mUserCard = MasterManager.getInstance().getUserCard();
        Header header = getHeader();
        if(header != null){
            header.leftLayout.setVisibility(View.GONE);
            header.titleText.setText(R.string.gkry);

            if(GZRU.equals(mUserCard.getUserType())){
                header.rightLayout.setVisibility(View.VISIBLE);
                header.rightImageBtn.setVisibility(View.GONE);
                header.rightImageBtn.setBackgroundResource(R.drawable.attention);
                header.rightText.setVisibility(View.GONE);
            }else if(JDZG.equals(mUserCard.getUserType())){
                header.rightLayout.setVisibility(View.VISIBLE);
                header.rightImageBtn.setVisibility(View.GONE);
                header.rightImageBtn.setBackgroundResource(R.drawable.message_chat_add_icon);
                header.rightText.setVisibility(View.GONE);
            }

            header.rightImageBtn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(GZRU.equals(mUserCard.getUserType())){
                        //工作人员
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(B_NAME, getString(R.string.wdgzhu));
                        bundle.putSerializable(B_AREA_ID, mUserCard.getOrgId());
                        bundle.putBoolean(B_COLLECT, true);
                        ARouter.getInstance().build(RouteUtils.R_PERSION_LIST_UI).withBundle(BUNDLE, bundle).navigation();
                    } else if(JDZG.equals(mUserCard.getUserType())){
                        //禁毒专干
                        String a_url = CommUtils.getInstance().createAddUrl("/drugs/baseinfo/baseinfo_add/baseinfoAddMain.html?").toString();
                        ARouter.getInstance().build(RouteUtils.R_WEB_UI)
                                .withString(TITLE, getString(R.string.xz))
                                .withString(URL, a_url)
                                .navigation();
                    }
                }
            });
        }
    }

    private void initData(){

        mCurrentArea.setText(mUserCard.getOrgName());
        if(JDZG.equals(mUserCard.getUserType())){
            if(mAreaPersion.getVisibility() == View.VISIBLE){
                mAreaPersion.setVisibility(View.GONE);
            }
        }else{
            if(mAreaPersion.getVisibility() == View.GONE){
                mAreaPersion.setVisibility(View.VISIBLE);
            }
        }
    }

    @Event(value = {R.id.search_layout, R.id.current_area_tv}, type = View.OnClickListener.class)
    private void onClick(View view){
        switch (view.getId()){
            case R.id.search_layout:
                ARouter.getInstance().build(RouteUtils.R_SEARCH_UI).navigation();
                break;
            case R.id.current_area_tv:
                String type = mUserCard.getUserType();
                String level = mUserCard.getOrgLevel();
                if(JDZG.equals(type)){
                    showToast(R.string.qdjsfjxss);
                }else{
                    if(level != null && JWHLEVEL.equals(level)){
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(B_NAME, mUserCard.getOrgName());
                        bundle.putSerializable(B_AREA_ID, mUserCard.getOrgId());
                        ARouter.getInstance().build(RouteUtils.R_PERSION_LIST_UI).withBundle(BUNDLE, bundle).navigation();
                    }else{
                        ARouter.getInstance().build(RouteUtils.R_AREA_UI).navigation();
                    }
                }
                break;
            default:
                break;
        }
    }
}
