package cn.com.jinke.wh_drugcontrol.word;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.persion.RefreshableUI;

/**
 * 请假列表
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/11/17
 */

public class LeaveListUI extends RefreshableUI {

    @Override
    protected void onInitView() {
        
        super.onInitView();
    }

    @Override
    protected void onInitData() {
        super.onInitData();
    }

    @Override
    protected String getTitleText() {
        return getString(R.string.qjjl);
    }

    @Override
    public void loadData() {
        
    }

    @Override
    protected ProjectBaseAdapter createAdapter() {
        return null;
    }
}
