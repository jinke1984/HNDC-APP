package cn.com.jinke.wh_drugcontrol.task.modle;

/**
 * Created by xkr on 2017/11/20.
 */

import cn.com.jinke.wh_drugcontrol.R;

/**
 * 任务类型taskType
 deleteProcess : 删除审批
 updateProcess : 修改审批
 changeProcess : 变更执行地审批
 helpPlanProcess : 帮扶救助审批
 leaveAuditProcess : 请假审批
 regularAppraisalProcess : 定期评估审批
 HigRiskScor : 高危积分审批
 */
public class TaskType {
    public static String getTaskTypeText(String taskType){
        String taskTypeText = null;
        if("deleteProcess".equals(taskType)){
            taskTypeText = "删除审批";
        }else if("updateProcess".equals(taskType)){
            taskTypeText = "修改审批";
        }else if("changeProcess".equals(taskType)){
            taskTypeText = "变更执行地审批";
        }else if("helpPlanProcess".equals(taskType)){
            taskTypeText = "帮教计划审批";
        }else if("leaveAuditProcess".equals(taskType)){
            taskTypeText = "请假审批";
        }else if("regularAppraisalProcess".equals(taskType)){
            taskTypeText = "定期评估审批";
        }else if("HigRiskScor".equals(taskType)){
            taskTypeText = "高危积分审批";
        }
        return taskTypeText;
    }

    public static int getTaskTypeResId(String taskType){
        int resId = R.mipmap.task_create;
        if("deleteProcess".equals(taskType)){
            resId = R.mipmap.task_deliver;
        }else if("updateProcess".equals(taskType)){
            resId = R.mipmap.task_reciever;
        }else if("changeProcess".equals(taskType)){
            resId = R.mipmap.task_tansaction;
        }else if("helpPlanProcess".equals(taskType)){
            resId = R.mipmap.task_tansaction;
        }else if("leaveAuditProcess".equals(taskType)){
            resId = R.mipmap.task_tansaction;
        }else if("regularAppraisalProcess".equals(taskType)){
            resId = R.mipmap.task_tansaction;
        }else if("HigRiskScor".equals(taskType)){
            resId = R.mipmap.task_tansaction;
        }
        return resId;
    }
}
