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
import cn.com.jinke.wh_drugcontrol.word.model.Agreement;

/**
 * NotifyManager
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/9/6
 */

public class AgreementManager extends UrlSetting implements CodeConstants, MsgKey, IDocumentType {

    private static AgreementManager instance;

    private AgreementManager() {
    }

    public static AgreementManager getInstance() {
        if (instance == null) {
            synchronized (AgreementManager.class) {
                if (instance == null) {
                    instance = new AgreementManager();
                }
            }
        }
        return instance;
    }

    public void deleteDoc(Agreement object) {
        DeleteDocManager.getInstance().deleteDocument(DOC_TYPE_AGREEMENT, object.getId());
    }

    public void loadData(Document document) {
        ProjectParams params = new ProjectParams(FIND_AGREEMENT).with(DOCID, document.getId());

        x.http().post(params.build(), new CallbackWrapper<Agreement>(LOAD_AGREEMENT, 2) {
            @Override
            public void onSuccess(int state, String msg, Agreement object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, object);
            }
        });
    }

    public void uploadData(Persion persion, Document document, Agreement object) {

        final UserCard userCard = MasterManager.getInstance().getUserCard();

        ProjectParams params = new ProjectParams(SAVE_AGREEMENT)
                .with(USER_ID, userCard.getUserId())// 登录人id
                .with(PERSONID, persion.getId())// 人员id
                .with(REALNAME, persion.getRealName())
                .with(IDCARD, persion.getIdentityCard())// 身份证
                .with(DOCID, document.getId())// 档案id
                .with(TYPE, document.getType())
                .with(REALNAME, persion.getRealName())
                .with(FILEADD, object.getFileAdd())// 图片地址
                .with(DRUGSDEPT, object.getDrugsDept())// 禁毒工作站
                .with(DRUGSDEPTSIGNDATE, object.getDrugsDeptSignDate())// 禁毒工作站盖章日期
                .with(SIGNDATE, object.getSignDate())// 签字日期
                .with(REMARK, object.getRemark());//备注

        x.http().post(params.build(), new CallbackWrapper<Void>(ADD_AGREEMENT_DOC, 2) {
            @Override
            public void onSuccess(int state, String msg, Void object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, msg);
            }
        });
    }
}
