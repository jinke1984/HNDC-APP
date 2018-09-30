package cn.com.jinke.wh_drugcontrol.me.manager;

import org.xutils.x;

import java.util.ArrayList;

import cn.com.jinke.wh_drugcontrol.booter.ProjectParams;
import cn.com.jinke.wh_drugcontrol.me.model.NoticeParent;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.MsgKey;
import cn.com.jinke.wh_drugcontrol.utils.UrlSetting;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;

/**
 * 通知公告
 * Created by jinke on 2017/7/20.
 */

public class NoticeManager extends UrlSetting implements CodeConstants, MsgKey {

    private boolean isRefresh = true;
    private int pageNo = 0;

    private static NoticeManager instance;

    private NoticeManager() {
        isRefresh = true;
    }

    public static NoticeManager getInstance() {
        if (instance == null) {
            synchronized (NoticeManager.class) {
                if (instance == null) {
                    instance = new NoticeManager();
                }
            }
        }
        return instance;
    }

    public boolean isRefresh() {
        return isRefresh;
    }

    public void loadData(boolean isRefresh) {
        this.isRefresh = isRefresh;
        if (isRefresh) {
            pageNo = 0;
        }

        ProjectParams params = new ProjectParams(FINDCHANNEL)
                .with(PAGENO, pageNo)
                .with(PAGESIZE, DATA_PAGE_SIZE);

        x.http().post(params.build(), new CallbackWrapper<ArrayList<NoticeParent>>(LOAD_NOTICE, 2) {

            @Override
            public void onSuccess(int state, String msg, ArrayList<NoticeParent> object, int total) {
                if (state == SUCCESS && object != null && object.size() > 0) {
                    pageNo += object.size();
                }

                MessageProxy.sendMessage(mMsgCode, state, object);
            }
        });

    }
}
