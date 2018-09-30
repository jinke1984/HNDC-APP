package cn.com.jinke.wh_drugcontrol.task.manager;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import cn.com.jinke.wh_drugcontrol.booter.ProjectParams;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.task.modle.TaskEntity;
import cn.com.jinke.wh_drugcontrol.task.modle.TaskInfo;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.MsgKey;
import cn.com.jinke.wh_drugcontrol.utils.UrlSetting;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CommonRequestParams;

/**
 * @author jinke
 * @date 2017/11/20
 * @description 任务相关的管理类
 */

public class TaskManager extends UrlSetting implements CodeConstants, MsgKey {

    private static TaskManager instance;

    private TaskManager() {

    }

    public static TaskManager getInstance() {
        if (instance == null) {
            synchronized (TaskManager.class) {
                if (instance == null) {
                    instance = new TaskManager();
                }
            }
        }
        return instance;
    }

    public void judgeCreateTime(String sourceId, int docType) {
        UserCard userCard = MasterManager.getInstance().getUserCard();
        CommonRequestParams.Builder builder = new CommonRequestParams.Builder(JUDGE_CREATETIME_URL);
        builder.putParams(USER_ID, userCard.getUserId());
        builder.putParams(DOCTYPE, docType);
        builder.putParams(SOURCEID, sourceId);
        x.http().post(builder.build(), new CallbackWrapper<Void>(JUDGE_CREATETIME, 2) {
            @Override
            public void onSuccess(int state, String msg, Void object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, msg);
            }
        });
    }

    /**
     * 档案修改/删除发起流程
     *
     * @param docId                    档案id
     * @param personId                 吸毒人员id
     * @param currentId                资源id
     * @param remark                   备注
     * @param updateOrDeleteItem       修改事项
     * @param updateOrDeleteNextPeople 下一步审批人
     * @param updateOrDeleteModel      档案类型
     * @param optType                  1档案修改 2档案删除
     */
    public void beginUpdateOrDelete(String docId,
                                    String personId,
                                    String currentId,
                                    String remark,
                                    String updateOrDeleteItem,
                                    String updateOrDeleteNextPeople,
                                    int updateOrDeleteModel,
                                    int optType) {
        final UserCard userCard = MasterManager.getInstance().getUserCard();

        ProjectParams params = new ProjectParams(BEGIN_UPDATE_OR_DELETE_URL)
                .with(USER_ID, userCard.getUserId())
                .with(DOCID, docId)
                .with(PERSONID, personId)
                .with(CURRENTID, currentId)
                .with(REMARK, remark)
                .with(OPTTYPE, optType);

        if (optType == 1) {
            params.with(MODIFYITEM, updateOrDeleteItem)
                    .with(NEXTPEOPLE, updateOrDeleteNextPeople)
                    .with(UPDATEMODEL, updateOrDeleteModel);
        } else if (optType == 2) {
            params.with(DELETEREASON, updateOrDeleteItem)
                    .with(NEXTPEOPLEDEL, updateOrDeleteNextPeople)
                    .with(DELETEMODEL, updateOrDeleteModel);
        }

        x.http().post(params.build(), new CallbackWrapper<Void>(UPDATE_OR_DETELE_SUCCESS, 2) {
            @Override
            public void onSuccess(int state, String msg, Void object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, msg);
            }
        });
    }

    /**
     * 申请修改流程
     *
     * @param docId       档案id
     * @param personId    吸毒人员id
     * @param currentId   资源id
     * @param modifyItem  修改事项
     * @param remark      备注
     * @param nextPeople  下一步审批人
     * @param updateModel 档案类型
     */
    public void changeApply(String docId, String personId, String currentId, String modifyItem,
                            String remark, String nextPeople, String updateModel) {
        final UserCard userCard = MasterManager.getInstance().getUserCard();

        ProjectParams params = new ProjectParams(CHANGE_APPLY_URL)
                .with(USER_ID, userCard.getUserId())
                .with(DOCID, docId)
                .with(PERSONID, personId)
                .with(CURRENTID, currentId)
                .with(MODIFYITEM, modifyItem)
                .with(REMARK, remark)
                .with(NEXTPEOPLE, nextPeople)
                .with(UPDATEMODEL, updateModel);
        x.http().post(params.build(), new CallbackWrapper<Void>(CHANGE_APPLY_SUCCESS, 2) {
            @Override
            public void onSuccess(int state, String msg, Void object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, msg);
            }
        });
    }

    /**
     * 申请修改流程
     *
     * @param docId        档案id
     * @param personId     吸毒人员id
     * @param currentId    资源id
     * @param deleteReason 删除原因
     * @param remark       备注
     * @param nextPeople   下一步审批人
     * @param deleteModel  档案类型
     */
    public void deleteApply(String docId, String personId, String currentId, String deleteReason,
                            String remark, String nextPeople, String deleteModel) {
        final UserCard userCard = MasterManager.getInstance().getUserCard();

        ProjectParams params = new ProjectParams(DELETE_APPLY_URL)
                .with(USER_ID, userCard.getUserId())
                .with(DOCID, docId)
                .with(PERSONID, personId)
                .with(CURRENTID, currentId)
                .with(DELETEREASON, deleteReason)
                .with(REMARK, remark)
                .with(NEXTPEOPLEDEL, nextPeople)
                .with(DELETEMODEL, deleteModel);
        x.http().post(params.build(), new CallbackWrapper<Void>(DELETE_APPLY_SUCCESS, 2) {
            @Override
            public void onSuccess(int state, String msg, Void object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, msg);
            }
        });
    }

    public void getTaskDetail(int aType, TaskEntity aTaskEntity){
        final UserCard userCard = MasterManager.getInstance().getUserCard();
        ProjectParams params = new ProjectParams(TASK_DETAIL)
                .with(USER_ID, userCard.getUserId())
                .with("sourId", aTaskEntity.getEntityId())
                .with("processInstanceId", aTaskEntity.getProcessInstanceId())
                .with(TYPE, aType);
        x.http().post(params.build(), new CallbackWrapper<TaskInfo>(TAST_DETAIL_SUCCESS, 2) {
            @Override
            public void onSuccess(int state, String msg,TaskInfo object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, object);
            }
        });
    }

    public void getTaskList(int pageIndex, int pageItemSize){
        final UserCard userCard = MasterManager.getInstance().getUserCard();
        ProjectParams params = new ProjectParams(GET_MY_TASK_LIST)
                .with(USER_ID, userCard.getUserId())
                .with(PAGENO, pageIndex)
                .with(PAGESIZE, pageItemSize);

        x.http().post(params.build(), new CallbackWrapper<List<TaskEntity>>(TASK_SUCCESS, 2) {
            @Override
            public void onSuccess(int state, String msg, List<TaskEntity> object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, object);
            }
        });
    }

}
