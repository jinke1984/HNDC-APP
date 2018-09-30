package cn.com.jinke.wh_drugcontrol.word.manager;

import org.xutils.x;

import java.util.List;

import cn.com.jinke.wh_drugcontrol.booter.ProjectParams;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.MsgKey;
import cn.com.jinke.wh_drugcontrol.utils.UrlSetting;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;
import cn.com.jinke.wh_drugcontrol.word.model.Audit;

/**
 * AuditManager审批人
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/9/6
 */

public class AuditManager extends UrlSetting implements CodeConstants, MsgKey {

    private static AuditManager instance;

    private AuditManager() {
    }

    public static AuditManager getInstance() {
        if (instance == null) {
            synchronized (AuditManager.class) {
                if (instance == null) {
                    instance = new AuditManager();
                }
            }
        }
        return instance;
    }

    /**
     * 拉取审批人
     */
    public void loadAuditList() {
        final UserCard userCard = MasterManager.getInstance().getUserCard();
        ProjectParams params = new ProjectParams(FIND_AUDIT_PERSON_LIST).with(USER_ID, userCard.getUserId());

        x.http().post(params.build(), new CallbackWrapper<List<Audit>>(LOAD_AUDIT_PERSON_LIST, 2) {
            @Override
            public void onSuccess(int state, String msg, List<Audit> object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, object);
            }
        });

    }


    /**
     * 下一步审批人合并
     */
    public void getNextPeople(int optType, int flowType, String changeId){
        final UserCard userCard = MasterManager.getInstance().getUserCard();
        ProjectParams params = new ProjectParams(NEXT_PEOPLE)
                .with(USER_ID, userCard.getUserId())
                .with(OPTTYPE, optType)
                .with(FLOWTYPE, flowType)
                .with(CHANGEID, changeId);
        x.http().post(params.build(), new CallbackWrapper<List<Audit>>(LOAD_NEXT_PEOPLE, 2){

            @Override
            public void onSuccess(int state, String msg, List<Audit> object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, object);
            }
        });
    }
    
}
