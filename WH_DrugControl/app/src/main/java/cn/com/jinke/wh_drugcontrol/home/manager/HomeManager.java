package cn.com.jinke.wh_drugcontrol.home.manager;

import org.xutils.x;

import java.util.ArrayList;

import cn.com.jinke.wh_drugcontrol.booter.ProjectParams;
import cn.com.jinke.wh_drugcontrol.home.model.TaskCount;
import cn.com.jinke.wh_drugcontrol.home.model.TaskDetail;
import cn.com.jinke.wh_drugcontrol.home.model.WorkRemind;
import cn.com.jinke.wh_drugcontrol.manager.PagerManager;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.MsgKey;
import cn.com.jinke.wh_drugcontrol.utils.UrlSetting;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;

/**
 * Created by jinke on 2017/9/12.
 */

public class HomeManager extends UrlSetting implements CodeConstants, MsgKey {

    private static HomeManager instance;
    private PagerManager taskDetailPagerManager = new PagerManager();

    private HomeManager() {

    }

    public static HomeManager getInstance() {
        if (instance == null) {
            synchronized (HomeManager.class) {
                if (instance == null) {
                    instance = new HomeManager();
                }
            }
        }
        return instance;
    }

    /**
     * 获取首页工作提醒
     */
    public void getWorkRemindData() {

        UserCard userCard = MasterManager.getInstance().getUserCard();
        ProjectParams params = new ProjectParams(HOME_URL)
                .with(USER_ID, userCard.getUserId());
        x.http().post(params.build(), new CallbackWrapper<WorkRemind>(HOME_SUCCESS, 2) {

            @Override
            public void onSuccess(int state, String msg, WorkRemind object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, object);
            }
        });
    }

    public void findMyTaskCount() {
        UserCard userCard = MasterManager.getInstance().getUserCard();
        ProjectParams params = new ProjectParams(GET_MY_TASK).with(USER_ID, userCard.getUserId());
        x.http().post(params.build(), new CallbackWrapper<TaskCount>(TASK_COUNT_SUCCESS, 2) {
            @Override
            public void onSuccess(int state, String msg, TaskCount object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, object);
            }
        });
    }

    public void findMyTask(boolean isRefresh) {
        taskDetailPagerManager.setRefresh(isRefresh);

        UserCard userCard = MasterManager.getInstance().getUserCard();
        ProjectParams params = new ProjectParams(GET_MY_TASK_LIST)
                .with(USER_ID, userCard.getUserId())
                .with(PAGENO, taskDetailPagerManager.getNextPageStart())
                .with(PAGESIZE, taskDetailPagerManager.getPageSize());

        x.http().post(params.build(), new CallbackWrapper<ArrayList<TaskDetail>>(TASK_SUCCESS, 2) {
            @Override
            public void onSuccess(int state, String msg, ArrayList<TaskDetail> object, int total) {
                if (object != null && object.size() > 0) {
                    taskDetailPagerManager.addNextPage(object.size(), total);
                }
                MessageProxy.sendMessage(mMsgCode, state, object);
            }
        });
    }

}
