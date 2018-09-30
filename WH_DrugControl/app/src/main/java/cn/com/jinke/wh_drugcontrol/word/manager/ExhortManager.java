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
import cn.com.jinke.wh_drugcontrol.word.model.Exhort;
import cn.com.jinke.wh_drugcontrol.word.model.ViolateAgreement;

/**
 * 劝、告诫书<br>
 * <li>拉取，上传数据</li>
 *
 * @author qiaotengfei<br>
 * @date 2017/9/6
 */

public class ExhortManager extends UrlSetting implements CodeConstants, MsgKey, IDocumentType {

    private static ExhortManager instance;

    private ExhortManager() {
    }

    public static ExhortManager getInstance() {
        if (instance == null) {
            synchronized (ExhortManager.class) {
                if (instance == null) {
                    instance = new ExhortManager();
                }
            }
        }
        return instance;
    }

    public void deleteDoc(Exhort object) {
        if ("1".equals(object.getNoticeType())) {
            DeleteDocManager.getInstance().deleteDocument(DOC_TYPE_EXHORT, object.getId());
        } else {
            DeleteDocManager.getInstance().deleteDocument(DOC_TYPE_WARNING, object.getId());
        }
    }

    /**
     * @param agreement 违反协议
     */
    public void loadData(ViolateAgreement agreement, Document document) {
        ProjectParams params = new ProjectParams(FIND_EXHORT)
                .with(DOCID, document.getId())
                .with(NOTICETYPE, agreement.getViolateLevel());

        x.http().post(params.build(), new CallbackWrapper<Exhort>(LOAD_EXHORT, 2) {
            @Override
            public void onSuccess(int state, String msg, Exhort object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, object);
            }
        });
    }

    public void uploadData(Persion persion,
                           Document document,
                           ViolateAgreement violateAgreement,
                           Exhort object) {

        final UserCard userCard = MasterManager.getInstance().getUserCard();

        ProjectParams params = new ProjectParams(SAVE_EXHORT)
                .with(USER_ID, userCard.getUserId())// 登录人id
                .with(PERSONID, persion.getId())// 人员id
                .with(IDCARD, persion.getIdentityCard())// 身份证
                .with(DOCID, document.getId())// 档案id
                .with(TYPE, document.getType())
                .with(REALNAME, persion.getRealName())
                .with(FILEADD, object.getFileAdd())// 图片地址
                .with(VIOLATIONID, violateAgreement.getId())
                .with(NOTICEREASON, object.getNoticeReason())
                .with(NOTICETYPE, violateAgreement.getViolateLevel())
                .with(VIOLATIONUSERID, userCard.getUserId())
                .with(VIOLATIONUSER, userCard.getInfoName())
                .with(OCCURDATE, object.getOccurDate())
                .with(OCCURPLACE, object.getOccurPlace())
                .with(PUNISHDEPT, userCard.getOrgName())
                .with(WITNESS, object.getWitness())
                .with(WITNESSPAPERTYPE, object.getWitnessPaperType())
                .with(WITNESSPAPERNO, object.getWitnessPaperNO())
                .with(LEAVEDAYS, object.getLeaveDays())
                .with(LEAVECOUNTS, object.getLeaveCounts())
                .with(DENYCOUNTS, object.getDenyCounts())
                .with(DENYDATE, object.getDenyDate());
        if ("3".equals(object.getNoticeReason())) {
            params.with(NOTICEREASONREMARK, object.getNoticeReasonRemark());
        }

        x.http().post(params.build(), new CallbackWrapper<Void>(ADD_EXHORT_DOC, 2) {
            @Override
            public void onSuccess(int state, String msg, Void object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, msg);
            }
        });
    }
}
