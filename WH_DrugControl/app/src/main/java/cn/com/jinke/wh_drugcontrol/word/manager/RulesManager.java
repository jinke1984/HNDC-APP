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
import cn.com.jinke.wh_drugcontrol.word.model.Rule;

/**
 * RulesManager
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/9/6
 */

public class RulesManager extends UrlSetting implements CodeConstants, MsgKey, IDocumentType {

    private static RulesManager instance;

    private RulesManager() {
    }

    public static RulesManager getInstance() {
        if (instance == null) {
            synchronized (RulesManager.class) {
                if (instance == null) {
                    instance = new RulesManager();
                }
            }
        }
        return instance;
    }

    public void deleteDoc(Rule object) {
        DeleteDocManager.getInstance().deleteDocument(DOC_TYPE_PERSON_RULES, object.getId());
    }

    public void loadData(Document document) {
        ProjectParams params = new ProjectParams(FINDRULE)
                .with(DOCID, document.getId());

        x.http().post(params.build(), new CallbackWrapper<Rule>(LOAD_RULE, 2) {
            @Override
            public void onSuccess(int state, String msg, Rule object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, object);
            }
        });
    }

    public void uploadData(Persion persion,
                           Document document,
                           Rule object) {

        final UserCard userCard = MasterManager.getInstance().getUserCard();

        ProjectParams params = new ProjectParams(SAVE_RULE)
                .with(USER_ID, userCard.getUserId())// 登录人id
                .with(PERSONID, persion.getId())// 人员id
                .with(IDCARD, persion.getIdentityCard())// 身份证
                .with(DOCID, document.getId())// 档案id
                .with(TYPE, document.getType())
                .with(REALNAME, persion.getRealName())// 受送达人
                .with(FILEADD, object.getFileAdd())// 图片地址
                .with(SENDDATE, object.getSendDate())// 送达时间
                .with(SENDLACTION, object.getSendLaction())// 送达地点
                .with(SENDTYPE, object.getSendType())// 送达方式
                .with(SENDTYPEDETAIL, object.getSendTypeDetail())// 送达方式 (其它)
                .with(ISDENY, object.getIsDeny())// 是否拒收
                .with(DENYREASON, object.getDenyReason())// 拒收原因
                .with(ISREPLACE, object.getIsReplace())// 是否代收
                .with(REPLACEPERSON, object.getReplacePerson())// 代收人
                .with(REPLACEREASON, object.getReplaceReason())// 代收原因
                .with(RELATION, object.getRelation())// 与受送达人关系
                .with(WITNESS, object.getWitness())// 见证人
                ;

        x.http().post(params.build(), new CallbackWrapper<Void>(ADD_RULE_DOC, 2) {
            @Override
            public void onSuccess(int state, String msg, Void object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, msg);
            }
        });
    }
}
