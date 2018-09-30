package cn.com.jinke.wh_drugcontrol.persion;

import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

/**
 * Created by xkr on 2017/11/1.
 */

public abstract class AbsPersionInfoUi extends RefreshableUI implements ILoadData {

    @Override
    protected void onInitView() {
        super.onInitView();
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.DISABLED);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

    }
}
