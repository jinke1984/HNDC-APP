package cn.com.jinke.wh_drugcontrol.word.manager;

import org.xutils.x;

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
import cn.com.jinke.wh_drugcontrol.word.model.Audit;
import cn.com.jinke.wh_drugcontrol.word.model.Plan;

/**
 * PlanManager
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/9/6
 */

public class PlanManager extends UrlSetting implements CodeConstants, MsgKey, IDocumentType {

    private static PlanManager instance;

    private PlanManager() {
    }

    public static PlanManager getInstance() {
        if (instance == null) {
            synchronized (PlanManager.class) {
                if (instance == null) {
                    instance = new PlanManager();
                }
            }
        }
        return instance;
    }

    public void deleteDoc(Plan object) {
        DeleteDocManager.getInstance().deleteDocument(DOC_TYPE_PLAN, object.getId());
    }

    public void loadData(Document document) {
        ProjectParams params = new ProjectParams(FINDHELPPLAN)
                .with(DOCID, document.getId());

        x.http().post(params.build(), new CallbackWrapper<Plan>(LOAD_HELP_PLAN, 2) {
            @Override
            public void onSuccess(int state, String msg, Plan object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, object);
            }
        });
    }

    public void uploadData(Persion persion,
                           Document document,
                           Plan object,
                           Audit audit) {
        final UserCard userCard = MasterManager.getInstance().getUserCard();

        ProjectParams params = new ProjectParams(SAVE_HELP_PLAN)
                .with(USER_ID, userCard.getUserId())// 登录人id
                .with(PERSONID, persion.getId())// 人员id
                .with(IDCARD, persion.getIdentityCard())// 身份证
                .with(DOCID, document.getId())// 档案id
                .with(TYPE, document.getType())
                .with(REALNAME, persion.getRealName())
                .with(FILEADD, object.getFileAdd())// 图片地址
                .with(SIGNDATE, object.getSignDate())
                .with(STAMPDATE, object.getStampDate())
                .with(PLANCOMMENT, object.getPlanComment())
                .with(NEXTPEOPLE, audit.getUserId());

        x.http().post(params.build(), new CallbackWrapper<Void>(ADD_PLAN_DOC, 2) {
            @Override
            public void onSuccess(int state, String msg, Void object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, msg);
            }
        });
    }
}
