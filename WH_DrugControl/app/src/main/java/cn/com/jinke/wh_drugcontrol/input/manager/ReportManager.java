package cn.com.jinke.wh_drugcontrol.input.manager;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.com.jinke.wh_drugcontrol.booter.ProjectParams;
import cn.com.jinke.wh_drugcontrol.input.model.Report;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.MsgKey;
import cn.com.jinke.wh_drugcontrol.utils.UrlSetting;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;

/**
 * Created by jinke on 2017/8/8.
 */

public class ReportManager extends UrlSetting implements CodeConstants, MsgKey {

    private static ReportManager instance;

    private final List<Report> mReportList = new ArrayList<>();
    private final static int PAGE_SIZE = 10;
    public static long sLastRefreshTime; // 上次刷新时间
    private static int sPageStart;
    private static int sTotalCount;
    private static int mPage = 0;

    private ReportManager() {

    }

    public static ReportManager getInstance() {
        if (instance == null) {
            synchronized (ReportManager.class) {
                if (instance == null) {
                    instance = new ReportManager();
                }
            }
        }
        return instance;
    }

    public void getReportList(String drug_id, boolean isRefresh) {

        if (isRefresh) {
            sPageStart = 0;
            mPage = 0;
            mReportList.clear();
            sLastRefreshTime = System.currentTimeMillis();
        }

        ProjectParams params = new ProjectParams(REPORT_URL)
                .with(DRUGID, drug_id)
                .with(PAGESIZE, PAGE_SIZE)
                .with(PAGENO, isRefresh ? 0 : sPageStart);
        
        x.http().post(params.build(), new CallbackWrapper<List<Report>>(REPORT_MSG, 2) {

            @Override
            public void onSuccess(int state, String msg, List<Report> object, int total) {

                if (state == SUCCESS) {

                    if (object != null) {
                        mReportList.addAll(object);
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
                MessageProxy.sendMessage(mMsgCode, state, mReportList);
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
