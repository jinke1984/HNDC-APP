package cn.com.jinke.wh_drugcontrol.word.manager;

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
import cn.com.jinke.wh_drugcontrol.word.model.ViolateAgreement;

/**
 * ViolateAgreeManager
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/9/6
 */

public class ViolateAgreeManager extends UrlSetting implements CodeConstants, MsgKey, IDocumentType {

    private static ViolateAgreeManager instance;

    private ViolateAgreeManager() {
    }

    public static ViolateAgreeManager getInstance() {
        if (instance == null) {
            synchronized (ViolateAgreeManager.class) {
                if (instance == null) {
                    instance = new ViolateAgreeManager();
                }
            }
        }
        return instance;
    }

    public void deleteDoc(ViolateAgreement object) {
        DeleteDocManager.getInstance().deleteDocument(DOC_TYPE_VIOLATE_AGREEMENT, object.getId());
    }

    public void loadDataList(String docId) {
        ProjectParams params = new ProjectParams(FIND_VIOLATION_AGREEMENT).with(DOCID, docId);

        x.http().post(params.build(), new CallbackWrapper<List<ViolateAgreement>>(LOAD_VIOLATION_AGREEMENT_LIST, 2) {
            @Override
            public void onSuccess(int state, String msg, List<ViolateAgreement> object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, object);
            }
        });

    }

    public void uploadData(Persion persion,
                           Document document,
                           ViolateAgreement object) {

        final UserCard userCard = MasterManager.getInstance().getUserCard();

        ProjectParams params = new ProjectParams(SAVE_VIOLATE_AGREEMENT)
                .with(USER_ID, userCard.getUserId())// 登录人id
                .with(PERSONID, persion.getId())// 人员id
                .with(IDCARD, persion.getIdentityCard())// 身份证
                .with(DOCID, document.getId())// 档案id
                .with(TYPE, document.getType())
                .with(COMMUNITY, persion.getCurrentAddress())
                .with(REALNAME, persion.getRealName())
                .with(FILEADD, object.getFileAdd())// 图片地址
                .with(PUNISHCONDITION, object.getPunishCondition())
                .with(REPAPSE, object.getRepapse())
                .with(VIOLATECONDITINO, object.getViolateConditino())
                .with(VIOLATELEVEL, object.getViolateLevel())
                .with(VIOLATEDATE, object.getViolateDate())
                .with(REMARK, object.getRemark());

        x.http().post(params.build(), new CallbackWrapper<Void>(ADD_VIOLATE_AGREE_DOC, 2) {
            @Override
            public void onSuccess(int state, String msg, Void object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, msg);
            }
        });
    }
}
