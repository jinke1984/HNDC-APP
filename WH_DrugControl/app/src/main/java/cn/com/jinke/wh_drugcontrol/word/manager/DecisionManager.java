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
import cn.com.jinke.wh_drugcontrol.word.model.Decision;

/**
 * DecisionManager
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/9/6
 */

public class DecisionManager extends UrlSetting implements CodeConstants, MsgKey, IDocumentType {

    private static DecisionManager instance;

    private DecisionManager() {
    }

    public static DecisionManager getInstance() {
        if (instance == null) {
            synchronized (DecisionManager.class) {
                if (instance == null) {
                    instance = new DecisionManager();
                }
            }
        }
        return instance;
    }

    public void deleteDoc(Decision object) {
        DeleteDocManager.getInstance().deleteDocument(DOC_TYPE_DECISION, object.getId());
    }

    public void loadData(Document document) {
        ProjectParams params = new ProjectParams(FIND_DECISION).with(DOCID, document.getId());

        x.http().post(params.build(), new CallbackWrapper<Decision>(LOAD_DECISION, 2) {
            @Override
            public void onSuccess(int state, String msg, Decision object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, object);
            }
        });
    }

    public void uploadData(Persion persion,
                           Document document,
                           Decision object) {

        final UserCard userCard = MasterManager.getInstance().getUserCard();

        ProjectParams params = new ProjectParams(SAVE_DECISION)
                .with(USER_ID, userCard.getUserId())// 登录人id
                .with(PERSONID, persion.getId())// 人员id
                .with(IDCARD, persion.getIdentityCard())// 身份证
                .with(DOCID, document.getId())// 档案id
                .with(REALNAME, persion.getRealName())
                .with(TYPE, object.getType()) // 决定书类型
                .with(FILEADD, object.getFileAdd())// 图片地址
                .with(ISDECISION, object.getIsDecision())// 是否有公安决定书,1有2无
                .with(NONDECISIONREASON, object.getNonDecisionReason())// 无公安决定书原因
                .with(DECISIONNO1, object.getDecisionNo1())// 决定书文号1
                .with(DECISIONNO2, object.getDecisionNo2())// 决定书文号2
                .with(DECISIONNO3, object.getDecisionNo3())// 决定书文号3
                .with(DECISIONNO4, object.getDecisionNo4())// 决定书文号4
                .with(DRUGSBEGINDATE, object.getDrugsBeginDate())// 社区戒毒开始日期
                .with(DRUGSENDDATE, object.getDrugsEndDate())// 社区戒毒结束日期
                .with(HANDLINGDEPARTMENT, object.getHandlingDepartment())// 公安机关名称
                .with(REGISTERDATE, object.getRegisterDate())// 报到日期
                .with(CATCHDETAIL, object.getCatchDeatil())// 查获详情
                ;

        x.http().post(params.build(), new CallbackWrapper<Void>(ADD_DECISION_DOC, 2) {
            @Override
            public void onSuccess(int state, String msg, Void object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, msg);
            }
        });
    }
}
