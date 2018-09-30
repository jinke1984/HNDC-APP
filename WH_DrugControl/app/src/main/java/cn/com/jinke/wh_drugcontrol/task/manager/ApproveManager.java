package cn.com.jinke.wh_drugcontrol.task.manager;

import android.text.TextUtils;

import org.xutils.x;

import cn.com.jinke.wh_drugcontrol.booter.ProjectParams;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.task.ApproveType;
import cn.com.jinke.wh_drugcontrol.task.modle.TaskRecord;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.MsgKey;
import cn.com.jinke.wh_drugcontrol.utils.UrlSetting;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;

/**
 * @author qiaotengfei
 * @date 2017/12/5
 * @description 审批
 */

public class ApproveManager extends UrlSetting implements CodeConstants, MsgKey {

    private static ApproveManager instance;

    private ApproveManager() {
    }

    public static ApproveManager getInstance() {
        if (instance == null) {
            synchronized (ApproveManager.class) {
                if (instance == null) {
                    instance = new ApproveManager();
                }
            }
        }
        return instance;
    }

    /**
     * 提交修改、删除审批
     *
     * @param entityId   修改/删除审批传的id
     * @param comment    批注/意见
     * @param dealStatus 审批状态（1 通过 2 不通过）
     * @param optType    操作类型（1 修改 2 删除）
     */
    public void updateOrDeleteAuditNew(String entityId,
                                       String comment,
                                       String dealStatus,
                                       int optType,String nextDealPeople) {
        final UserCard userCard = MasterManager.getInstance().getUserCard();

        ProjectParams params = new ProjectParams(UPDATE_DELETE_AUDIT_NEW_URL)
                .with(USER_ID, userCard.getUserId())
                .with(COMMENT, comment)
                .with(DEALSTATUS, dealStatus)
                .with(ENTITYID, entityId)
                .with(NEXTDEALPEOPLE,nextDealPeople);

        x.http().post(params.build(), new CallbackWrapper<Void>(UPDATE_DELETE_SUCCESS, 2) {
            @Override
            public void onSuccess(int state, String msg, Void object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, msg);
            }
        });
    }

    /**
     * 提交修改、删除审批
     *
     * @param entityId   修改/删除审批传的id
     * @param comment    批注/意见
     * @param dealStatus 审批状态（1 通过 2 不通过）
     * @param optType    操作类型（1 修改 2 删除）
     */
    public void updateOrDeleteAudit(String entityId,
                                    String comment,
                                    String dealStatus,
                                    String optType) {
        final UserCard userCard = MasterManager.getInstance().getUserCard();

        ProjectParams params = new ProjectParams(UPDATE_DELETE_AUDIT_URL)
                .with(USER_ID, userCard.getUserId())
                .with(COMMENT, comment)
                .with(DEALSTATUS, dealStatus)
                .with(OPTTYPE, optType);
        if ("1".equals(optType)) {
            params.with(ENTITYID, entityId);
        } else {
            params.with(ENTITYDELID, entityId);
        }
        x.http().post(params.build(), new CallbackWrapper<Void>(UPDATE_DELETE_SUCCESS, 2) {
            @Override
            public void onSuccess(int state, String msg, Void object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, msg);
            }
        });
    }

    /**
     * 除修改和删除之外的审批
     *
     * @param entityId       修改/删除审批传的id
     * @param comment        批注/意见
     * @param dealStatus     审批状态（1 通过 2 不通过）
     * @param nextDealPeople 下一步审批人
     * @param auditUrl       对应审批url
     */
    public void taskAudit(final String entityId,
                          final String comment,
                          final String dealStatus,
                          final String nextDealPeople,
                          final String auditUrl) {
        final UserCard userCard = MasterManager.getInstance().getUserCard();

        ProjectParams params = new ProjectParams(auditUrl)
                .with(USER_ID, userCard.getUserId())
                .with(ENTITYID, entityId)
                .with(COMMENT, comment)
                .with(DEALSTATUS, dealStatus);

        if (!TextUtils.isEmpty(nextDealPeople)) {
            params.with(NEXTDEALPEOPLE, nextDealPeople);
        }

        x.http().post(params.build(), new CallbackWrapper<Void>(TASK_AUDIT_SUCCESS, 2) {
            @Override
            public void onSuccess(int state, String msg, Void object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, msg);
            }
        });
    }

    /**
     * 根据流程实例id获取修改删除记录表
     *
     * @param processInstanceId 流程实例id
     */
    public void getModifyDelete(String processInstanceId) {
        final UserCard userCard = MasterManager.getInstance().getUserCard();

        ProjectParams params = new ProjectParams(GET_MODIFY_DELETE_URL)
                .with(USER_ID, userCard.getUserId())
                .with(PROCESSINSTANCEID, processInstanceId);

        x.http().post(params.build(), new CallbackWrapper<TaskRecord>(GET_MODIFY_DELETE_SUCCESS, 2) {
            @Override
            public void onSuccess(int state, String msg, TaskRecord object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, object);
            }
        });
    }

    /**
     * 公用审批接口
     * @param entityId 资源id
     * @param comment  批注/意见
     * @param dealStatus  审批状态
     * @param nextDealPeople 下一步审批人id
     * @param type  档案类型  {@link ApproveType}
     */
    public void commonApprove(String entityId,
                                    String comment,
                                    String dealStatus,
                                    String nextDealPeople,
                                    int type) {
        final UserCard userCard = MasterManager.getInstance().getUserCard();

        ProjectParams params = new ProjectParams(COMMON_APPROVE_URL)
                .with(USER_ID, userCard.getUserId())
                .with(ENTITYID, entityId)
                .with(COMMENT, comment)
                .with(DEALSTATUS, dealStatus)
                .with(TYPE, type);
        if (ApproveType.type_help_plan != type){
            params.with(NEXTDEALPEOPLE, nextDealPeople);
        }
        x.http().post(params.build(), new CallbackWrapper<Void>(UPDATE_DELETE_SUCCESS, 2) {
            @Override
            public void onSuccess(int state, String msg, Void object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, msg);
            }
        });
    }
}
