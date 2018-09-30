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
import cn.com.jinke.wh_drugcontrol.word.model.Notify;

/**
 * NotifyManager
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/9/6
 */

public class NotifyManager extends UrlSetting implements CodeConstants, MsgKey, IDocumentType {

    private static NotifyManager instance;

    private NotifyManager() {
    }

    public static NotifyManager getInstance() {
        if (instance == null) {
            synchronized (NotifyManager.class) {
                if (instance == null) {
                    instance = new NotifyManager();
                }
            }
        }
        return instance;
    }

    public void deleteDoc(Notify object) {
        DeleteDocManager.getInstance().deleteDocument(DOC_TYPE_NOTIFY, object.getId());
    }


    public void loadData(Document document) {
        ProjectParams params = new ProjectParams(FINDNOTICE)
                .with(DOCID, document.getId());

        x.http().post(params.build(), new CallbackWrapper<Notify>(LOAD_NOTIFY, 2) {
            @Override
            public void onSuccess(int state, String msg, Notify object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, object);
            }
        });
    }

    public void uploadData(Persion persion,
                           Document document,
                           Notify object) {

        final UserCard userCard = MasterManager.getInstance().getUserCard();

        ProjectParams params = new ProjectParams(SAVE_NOTIFY)
                .with(USER_ID, userCard.getUserId())// 登录人id
                .with(PERSONID, persion.getId())// 人员id
                .with(IDCARD, persion.getIdentityCard())// 身份证
                .with(REALNAME, persion.getRealName())
                .with(DOCID, document.getId())// 档案id
                .with(FILEADD, object.getFileAdd())// 图片地址
                .with(TYPE, object.getType())// 告书类型
                .with(NOTICEDATE, object.getNoticeDate())// 下达告知书日期
                .with(HANDLINGDEPARTMENT, object.getHandlingDepartment())// 下达告知书的公安机关
                .with(RECEIVEDATE, object.getReceiveDate())// 签收日期
                .with(DEPTSIGNDATE, object.getDeptSignDate())// 公安机关盖章日期
                ;

        x.http().post(params.build(), new CallbackWrapper<Void>(ADD_NOTIFY_DOC, 2) {
            @Override
            public void onSuccess(int state, String msg, Void object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, msg);
            }
        });
    }
}
