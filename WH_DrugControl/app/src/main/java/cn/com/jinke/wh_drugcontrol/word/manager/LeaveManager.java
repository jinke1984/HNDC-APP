package cn.com.jinke.wh_drugcontrol.word.manager;

import org.xutils.x;

import java.util.List;

import cn.com.jinke.wh_drugcontrol.booter.ProjectParams;
import cn.com.jinke.wh_drugcontrol.manager.PagerManager;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.Persion;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.persion.model.Document;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.MsgKey;
import cn.com.jinke.wh_drugcontrol.utils.UrlSetting;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;
import cn.com.jinke.wh_drugcontrol.word.model.Leave;
import cn.com.jinke.wh_drugcontrol.word.model.Suspended;

/**
 * 请假Manager，拉取和上传数据
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/11/22
 */

public class LeaveManager extends UrlSetting implements CodeConstants, MsgKey {

    private static LeaveManager instance;
    private PagerManager pagerManager = new PagerManager();

    private LeaveManager() {
    }

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
    
    public boolean isRefresh() {
        return pagerManager == null || pagerManager.isRefresh();
    }

    public void loadDataList(String docId, boolean isRefresh) {
        pagerManager.setRefresh(isRefresh);

        ProjectParams params = new ProjectParams(FIND_LEAVE)
                .with(DOCID, docId)
                .with(PAGENO, pagerManager.getNextPageStart())
                .with(PAGESIZE, pagerManager.getPageSize());

        x.http().post(params.build(), new CallbackWrapper<List<Leave>>(LOAD_LEAVE, 2) {
            @Override
            public void onSuccess(int state, String msg, List<Leave> object, int total) {
                if (object != null && object.size() > 0) {
                    pagerManager.addNextPage(object.size(), total);
                }

                MessageProxy.sendMessage(mMsgCode, state, object);
            }
        });
    }

    public void uploadData(Persion persion,
                           Document document,
                           String photoUrl,
                           String breakReason,
                           String breakDate,
                           String constraints,
                           String isolationPeriod,
                           String description,
                           String fileAddDesc,
                           String decideDept,
                           String approveDept) {

        final UserCard userCard = MasterManager.getInstance().getUserCard();

        ProjectParams params = new ProjectParams(SAVE_LEAVE)
                .with(USER_ID, userCard.getUserId())// 登录人id
                .with(PERSONID, persion.getId())// 人员id
                .with(IDCARD, persion.getIdentityCard())// 身份证
                .with(DOCID, document.getId())// 档案id
                .with(TYPE, document.getType())
                .with(REALNAME, persion.getRealName())
                .with(COMMUNITY, persion.getCurrentAddress())
                .with(FILEADD, photoUrl)// 图片地址
                .with(BREAKREASON, breakReason)
                .with(BREAKDATE, breakDate)
                .with(ISOLATIONPERIOD, isolationPeriod)
                .with(CONSTRAINTS, constraints)
                .with(DESCRIPTION, description)
                .with(FILEADDDESC, fileAddDesc)
                .with(DECIDEDEPT, decideDept)
                .with(APPROVEDEPT, approveDept);

        x.http().post(params.build(), new CallbackWrapper<Void>(ADD_LEAVE_DOC, 2) {
            @Override
            public void onSuccess(int state, String msg, Void object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, msg);
            }
        });
    }
}
