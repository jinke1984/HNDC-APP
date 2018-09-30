package cn.com.jinke.wh_drugcontrol.word.manager;

import android.text.TextUtils;

import org.xutils.x;

import java.util.List;

import cn.com.jinke.wh_drugcontrol.booter.ProjectParams;
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
import cn.com.jinke.wh_drugcontrol.word.model.Continue;

/**
 * ContinueManager
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/9/6
 */

public class ContinueManager extends UrlSetting implements CodeConstants, MsgKey, IDocumentType {

    private static ContinueManager instance;
    private PagerManager continuePagerManager = new PagerManager();


    private ContinueManager() {
    }

    public static ContinueManager getInstance() {
        if (instance == null) {
            synchronized (ContinueManager.class) {
                if (instance == null) {
                    instance = new ContinueManager();
                }
            }
        }
        return instance;
    }

    public void deleteDoc(Continue object) {
        DeleteDocManager.getInstance().deleteDocument(DOC_TYPE_CONTINUE, object.getId());
    }

    public boolean isRefresh() {
        return continuePagerManager == null || continuePagerManager.isRefresh();
    }

    public void loadContinueDataList(String docId, boolean isRefresh) {
        continuePagerManager.setRefresh(isRefresh);
        ProjectParams params = new ProjectParams(FINDDRUGSCONTINUE)
                .with(DOCID, docId)
                .with(PAGENO, continuePagerManager.getNextPageStart())
                .with(PAGESIZE, continuePagerManager.getPageSize());

        x.http().post(params.build(), new CallbackWrapper<List<Continue>>(LOAD_DRUGS_CONTINUE_LIST, 2) {
            @Override
            public void onSuccess(int state, String msg, List<Continue> object, int total) {
                if (object != null && object.size() > 0) {
                    continuePagerManager.addNextPage(object.size(), total);
                }
                MessageProxy.sendMessage(mMsgCode, state, object);
            }
        });
    }

    public void uploadData(Persion persion,
                           Document document,
                           Continue object) {

        final UserCard userCard = MasterManager.getInstance().getUserCard();

        ProjectParams params = new ProjectParams(SAVE_DRUGS_CONTINUE)
                .with(USER_ID, userCard.getUserId())// 登录人id
                .with(PERSONID, persion.getId())// 人员id
                .with(IDCARD, persion.getIdentityCard())// 身份证
                .with(DOCID, document.getId())// 档案id
                .with(TYPE, document.getType())
                .with(REALNAME, persion.getRealName())
                .with(FILEADD, object.getFileAdd())// 图片地址
                .with(COMMUNITY, persion.getCurrentAddress())
                .with(BREAKID, object.getBreakID())
                .with(BREAKREASON, object.getBreakReason())
                .with(BREAKDATE, object.getBreakDate())
                .with(CONTINUEDATE, object.getContinueDate())
                .with(CONTINUEREASON, object.getContinueReason());

        if (!TextUtils.isEmpty(object.getId())) {
            params.with(ID, object.getId());
        }

        x.http().post(params.build(), new CallbackWrapper<Void>(ADD_GO_ON_DOC, 2) {
            @Override
            public void onSuccess(int state, String msg, Void object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, msg);
            }
        });
    }
}
