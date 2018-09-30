package cn.com.jinke.wh_drugcontrol.input.manager;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.com.jinke.wh_drugcontrol.booter.ProjectParams;
import cn.com.jinke.wh_drugcontrol.input.model.Leave;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.MsgKey;
import cn.com.jinke.wh_drugcontrol.utils.UrlSetting;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;

/**
 * @author jinke
 * @date 2018/3/6
 * @description
 */

public class LeaveManager extends UrlSetting implements CodeConstants, MsgKey {

    private static LeaveManager instance;

    private final List<Leave> mLeaveList = new ArrayList<>();
    private final static int PAGE_SIZE = 10;

    /**
     *  上次刷新时间
     */
    public static long sLastRefreshTime;
    private static int sPageStart;
    private static int sTotalCount;
    private static int mPage = 0;

    private LeaveManager() {}

    public static LeaveManager getInstance() {
        if (instance == null) {
            synchronized (LeaveManager.class) {
                if (instance == null) {
                    instance = new LeaveManager();
                }
            }
        }
        return instance;
    }

    /**
     * 外出请假记录查询
     * @param personId   吸毒人员id
     * @param isBack     1销假、0未销假、空所有
     * @param isRefresh  是否刷新
     */
    public void getLeaveList(String personId, int isBack, boolean isRefresh){

        if (isRefresh) {
            sPageStart = 0;
            mPage = 0;
            mLeaveList.clear();
            sLastRefreshTime = System.currentTimeMillis();
        }

        ProjectParams params = new ProjectParams(LEAVELIST)
                .with(PERSONID, personId)
                .with(ISBACK, isBack)
                .with(PAGESIZE, PAGE_SIZE)
                .with(PAGENO, isRefresh ? 0 : sPageStart);

        x.http().post(params.build(), new CallbackWrapper<List<Leave>>(LEAVE_MSG, 2){

            @Override
            public void onSuccess(int state, String msg, List<Leave> object, int total) {

                if (state == SUCCESS) {

                    if (object != null) {
                        mLeaveList.addAll(object);
                    }

                    if (sTotalCount >= 0) {
                        mPage = mPage + 1;
                        sTotalCount = total;
                        int pageCount = sTotalCount / PAGE_SIZE;
                        if (pageCount >= mPage) {
                            sPageStart = sPageStart + PAGE_SIZE;
                        }
                    }
                }
                MessageProxy.sendMessage(mMsgCode, state, mLeaveList);
            }
        });
    }

    public boolean isFinish() {
        boolean result;
        if (sTotalCount == 0) {
            return true;
        } else {
            int pageCount = sTotalCount / PAGE_SIZE;
            if (pageCount >= mPage) {
                result = false;
            } else {
                result = true;
            }
        }
        return result;
    }
}
