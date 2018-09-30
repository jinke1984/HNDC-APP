package cn.com.jinke.wh_drugcontrol.persion;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import java.util.ArrayList;
import java.util.List;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.input.manager.PhotoManager;
import cn.com.jinke.wh_drugcontrol.me.WebUI;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.Persion;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.persion.adapter.GatherInformationAdapter;
import cn.com.jinke.wh_drugcontrol.persion.model.Photo;
import cn.com.jinke.wh_drugcontrol.utils.AppLogger;
import cn.com.jinke.wh_drugcontrol.utils.RequestHelper;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;

/**
 * Created by xkr on 2017/11/3.
 * 采集信息
 */
@Route(path = RouteUtils.R_PERSION_GATHERING_INFORMATION)
public class GatheringInformationUI extends AbsPersionInfoUi implements AdapterView.OnItemClickListener {

    Persion mPersion;

    @Override
    protected void onInitView() {
        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle =intent.getBundleExtra(BUNDLE);
            mPersion = (Persion) bundle.getSerializable(PERSION);
        }
        super.onInitView();
        mPullToRefreshListView.setOnItemClickListener(this);
        ColorDrawable drawable = new ColorDrawable();
        drawable.setBounds(0,0,100,2);
        drawable.setColor(getResources().getColor(R.color.food_bg));
        mPullToRefreshListView.getRefreshableView()
                .setDivider(drawable);
        mPullToRefreshListView.getRefreshableView().setDividerHeight(2);
    }

    @Override
    protected String getTitleText() {
        return mPersion.getRealName() + getString(R.string.cjxxx);
    }

    @Override
    protected void onInitData() {
        String[] sArray = getResources().getStringArray(R.array.base_info);
        List<String> list = new ArrayList<>();
        for (String str: sArray){
            list.add(str);
        }
        mAdapter.addData(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadData() {

    }

    @Override
    protected ProjectBaseAdapter createAdapter() {
        return new GatherInformationAdapter(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String title = (String) parent.getAdapter().getItem(position);
        String url = null;
        switch (position) {
            case 1:
                url = createWebUrl("drugs/baseinfo/baseinfo_edit/baseinfo1.html?");
                ARouter.getInstance().build(RouteUtils.R_WEB_UI)
                        .withString(TITLE,title)
                        .withString(URL,url)
                        .navigation();
                break;
            case 2:
                url = createWebUrl("drugs/baseinfo/baseinfo_edit/baseinfo2.html?");
                ARouter.getInstance().build(RouteUtils.R_WEB_UI)
                        .withString(TITLE,title)
                        .withString(URL,url)
                        .navigation();
                break;
            case 3:
                url = createWebUrl("drugs/baseinfo/baseinfo_edit/baseinfo3.html?");
                ARouter.getInstance().build(RouteUtils.R_WEB_UI)
                        .withString(TITLE,title)
                        .withString(URL,url)
                        .navigation();
                break;
            default:
                break;
        }
    }

    private String createWebUrl(String path){
        StringBuilder sb = new StringBuilder(RequestHelper.getInstance().IN_IMAGE_URL+path);
        UserCard userCard = MasterManager.getInstance().getUserCard();
        if(userCard != null){
            sb.append("id=").append(mPersion.getId())
                    .append("&controlId=").append(userCard.getUserId())
                    .append("&userType=").append(userCard.getUserType());
        }
        AppLogger.d("url:"+sb.toString());
        return sb.toString();
    }
}
