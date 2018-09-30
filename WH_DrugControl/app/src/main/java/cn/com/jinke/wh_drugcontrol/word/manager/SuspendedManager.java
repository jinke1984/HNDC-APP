package cn.com.jinke.wh_drugcontrol.word.manager;

import android.text.TextUtils;

import org.xutils.x;

import java.util.List;

import cn.com.jinke.wh_drugcontrol.booter.ProjectParams;
import cn.com.jinke.wh_drugcontrol.manager.PagerManager;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.Persion;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.persion.model.Document;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.IDocumentType;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.MsgKey;
import cn.com.jinke.wh_drugcontrol.utils.UrlSetting;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;
import cn.com.jinke.wh_drugcontrol.word.model.Suspended;

/**
 * SuspendedManager
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/9/6
 */

public class SuspendedManager extends UrlSetting implements CodeConstants, MsgKey, IDocumentType {

    private static SuspendedManager instance;
    private PagerManager breakPagerManager = new PagerManager();

    private SuspendedManager() {
    }

    public static SuspendedManager getInstance() {
        if (instance == null) {
            synchronized (SuspendedManager.class) {
                if (instance == null) {
                    instance = new SuspendedManager();
                }
            }
        }
        return instance;
    }

    public void deleteDoc(Suspended object) {
        DeleteDocManager.getInstance().deleteDocument(DOC_TYPE_SUSPENDED, object.getId());
    }

    public boolean isRefresh() {
        return breakPagerManager == null || breakPagerManager.isRefresh();
    }

    public void loadBreakDataList(String docId, boolean isRefresh) {
        breakPagerManager.setRefresh(isRefresh);

        ProjectParams params = new ProjectParams(FINDDRUGSBREAK)
                .with(DOCID, docId)
                .with(PAGENO, breakPagerManager.getNextPageStart())
                .with(PAGESIZE, breakPagerManager.getPageSize());

        x.http().post(params.build(), new CallbackWrapper<List<Suspended>>(LOAD_DRUGS_BREAK_LIST, 2) {
            @Override
            public void onSuccess(int state, String msg, List<Suspended> object, int total) {
                if (object != null && object.size() > 0) {
                    breakPagerManager.addNextPage(object.size(), total);
                }

                MessageProxy.sendMessage(mMsgCode, state, object);
            }
        });
    }

    public void uploadData(Persion persion,
                           Document document,
                           Suspended object) {

        final UserCard userCard = MasterManager.getInstance().getUserCard();

        ProjectParams params = new ProjectParams(SAVE_DRUGS_BREAK)
                .with(USER_ID, userCard.getUserId())// 登录人id
                .with(PERSONID, persion.getId())// 人员id
                .with(IDCARD, persion.getIdentityCard())// 身份证
                .with(DOCID, document.getId())// 档案id
                .with(TYPE, document.getType())
                .with(REALNAME, persion.getRealName())
                .with(COMMUNITY, persion.getCurrentAddress())
                .with(FILEADD, object.getFileAdd())// 图片地址
                .with(BREAKREASON, object.getBreakReason())
                .with(BREAKDATE, object.getBreakDate())
                .with(ISOLATIONPERIOD, object.getIsolationPeriod())
                .with(CONSTRAINTS, object.getConstraints())
                .with(DESCRIPTION, object.getDescription())
                .with(FILEADDDESC, object.getFileAddDesc())
                .with(DECIDEDEPT, object.getDecideDept())
                .with(APPROVEDEPT, object.getApproveDept());
        
        if (!TextUtils.isEmpty(object.getId())) {
            params.with(ID, object.getId());
        }

        x.http().post(params.build(), new CallbackWrapper<Void>(ADD_SUSPENDED_DOC, 2) {
            @Override
            public void onSuccess(int state, String msg, Void object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, msg);
            }
        });
    }
}
