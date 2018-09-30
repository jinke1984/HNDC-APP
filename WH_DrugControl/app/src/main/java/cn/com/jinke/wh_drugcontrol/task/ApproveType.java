package cn.com.jinke.wh_drugcontrol.task;

/**
 * Created by xkr on 2017/12/19.
 * 审批类型
 */

public interface ApproveType {
    /**
     * 1帮教计划 2定期评估 3解除申请
     4变更执行的 5请假申请 6修改删除
     */

    int type_help_plan = 1;
    int type_periodic_assessment = 2;
    int type_pescission_of_application = 3;
    int type_change_execution = 4;
    int type_application_for_leave = 5;
    int type_modify_and_delete = 6;

}
