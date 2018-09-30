package cn.com.jinke.wh_drugcontrol.input;

import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.xutils.view.annotation.ViewInject;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;

/**
 * DynamicUI
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/11/16
 */
@Route(path = RouteUtils.R_DYNAMIC_UI)
public class  DynamicUI extends ProjectBaseUI {

    @ViewInject(R.id.report_listview)
    private PullToRefreshListView mRefreshLV;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_report);
    }

    @Override
    protected void onInitView() {
        super.onInitView();
    }

    @Override
    protected void onInitData() {
        super.onInitData();
    }
}
