package cn.com.jinke.wh_drugcontrol.task;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

import org.xutils.x;

import java.util.List;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseAdapter;
import cn.com.jinke.wh_drugcontrol.booter.ProjectBaseUI;
import cn.com.jinke.wh_drugcontrol.booter.ProjectParams;
import cn.com.jinke.wh_drugcontrol.home.manager.HomeManager;
import cn.com.jinke.wh_drugcontrol.home.model.HomeTask;
import cn.com.jinke.wh_drugcontrol.home.model.TaskDetail;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.persion.AbsPersionInfoUi;
import cn.com.jinke.wh_drugcontrol.persion.RefreshableUI;
import cn.com.jinke.wh_drugcontrol.persion.model.MedicationEntity;
import cn.com.jinke.wh_drugcontrol.task.adapter.TaskAdapter;
import cn.com.jinke.wh_drugcontrol.task.manager.TaskManager;
import cn.com.jinke.wh_drugcontrol.task.modle.TaskEntity;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;

/**
 * TaskDistributeUI
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/10/20
 * <p>
 * alter  by xkr
 * 任务列表
 */
@Route(path = RouteUtils.R_TASK_DISTRIBUTE_UI)
public class TaskDistributeUI extends RefreshableUI implements AdapterView.OnItemClickListener {

    private int[] MSG = new int[]{ACCESS_NET_FAILED, TASK_SUCCESS, UPDATE_DELETE_SUCCESS};

    @Override
    protected boolean handleMessage(Message msg) {

        switch (msg.what) {
            case TASK_SUCCESS:
                dismissLoading();
                mPullToRefreshListView.onRefreshComplete();
                List<TaskEntity> list = (List<TaskEntity>) msg.obj;
                if (list == null) {
                    break;
                }
                mAdapter.addData(list);
                mAdapter.notifyDataSetChanged();
                showToast("获取数据成功");
                break;
            case UPDATE_DELETE_SUCCESS:
                finish();
                break;
            case ACCESS_NET_FAILED:
            default:
                dismissLoading();
                break;
        }
        return super.handleMessage(msg);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerMessages(MSG);
    }

    @Override
    protected void onInitView() {
        super.onInitView();
        mPullToRefreshListView.setOnItemClickListener(this);
        if (getIntent() != null) {
            HomeTask homeTask = (HomeTask) getIntent().getSerializableExtra("hometask");
            Header header = getHeader();
            if (homeTask != null && header != null) {
                header.titleText.setText(homeTask.getTaskName());
            }
        }

    }

    @Override
    protected ProjectBaseAdapter createAdapter() {
        return new TaskAdapter(this);
    }

    @Override
    public void loadData() {
        showLoading();
        TaskManager.getInstance().getTaskList(mLoadMoreManager.getPageIndex(), mLoadMoreManager.getPageItemSize());
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //跳转到任务详情
        TaskEntity taskEntity = (TaskEntity) parent.getAdapter().getItem(position);
        Bundle bundle = new Bundle();
        bundle.putSerializable("taskEntity", taskEntity);
        ARouter.getInstance().build(RouteUtils.R_TASK_DETAIL)
                .withBundle(BUNDLE, bundle).navigation();
    }
}
