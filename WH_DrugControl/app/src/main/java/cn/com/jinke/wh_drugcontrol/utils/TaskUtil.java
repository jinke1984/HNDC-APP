package cn.com.jinke.wh_drugcontrol.utils;

/**
 * @author jinke
 * @date 2017/12/5
 * @description
 */

public class TaskUtil implements CodeConstants{

    private static TaskUtil instance;

    public final String USERTASK1 = "usertask1";
    public final String USERTASK2 = "usertask2";
    public final String USERTASK3 = "usertask3";
    public final String USERTASK4 = "usertask4";
    public final String USERTASK5 = "usertask5";
    public final String USERTASK6 = "usertask6";
    public final String USERTASK7 = "usertask7";
    public final String USERTASK8 = "usertask8";
    public final String USERTASK9 = "usertask9";
    public final String USERTASK10 = "usertask10";
    public final String USERTASK11 = "usertask11";
    public final String USERTASK14 = "usertask14";
    public final String USERTASK15 = "usertask15";

    private TaskUtil(){

    }

    public static TaskUtil getInstance() {
        if (instance == null) {
            synchronized (TaskUtil.class) {
                if (instance == null) {
                    instance = new TaskUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 删除审批和修改审批状态的判断
     * @param taskDefinitionKey
     * @return  false 代表审批  true代表处理
     */
    public boolean deleteAndUpdateProcess(String taskDefinitionKey){
        boolean isResult = false;
        if(USERTASK1.equals(taskDefinitionKey) || USERTASK2.equals(taskDefinitionKey)){
            isResult = false;
        }else if(USERTASK3.equals(taskDefinitionKey) || USERTASK4.equals(taskDefinitionKey)){
            isResult = true;
        }
        return isResult;
    }

    /**
     * 帮扶救助审批状态的判断
     * @param taskDefinitionKey
     * @return false 代表审批  true代表处理
     */
    public boolean helpPlanProcess(String taskDefinitionKey){
        boolean isResult = false;
        if(USERTASK2.equals(taskDefinitionKey)){
            isResult = false;
        }else{
            isResult = true;
        }
        return isResult;
    }

    /**
     * 变更执行地审批状态的判断
     * @param taskDefinitionKey
     * @return false 代表审批  true代表处理
     */
    public boolean changeProcess(String taskDefinitionKey){
        boolean isResult = false;
        if(USERTASK2.equals(taskDefinitionKey) || USERTASK3.equals(taskDefinitionKey)
                || USERTASK5.equals(taskDefinitionKey) || USERTASK7.equals(taskDefinitionKey) || USERTASK8.equals(taskDefinitionKey)
                || USERTASK6.equals(taskDefinitionKey) || USERTASK14.equals(taskDefinitionKey) || USERTASK15.equals(taskDefinitionKey)){
            isResult = false;
        }else{
            isResult = true;
        }
        return isResult;
    }

    /**
     * 定期评估审批状态的判断
     * @param taskDefinitionKey
     * @return false 代表审批  true代表处理
     */
    public boolean regularAppraisalProcess(String taskDefinitionKey){
        boolean isResult = false;
        if(USERTASK1.equals(taskDefinitionKey) || USERTASK2.equals(taskDefinitionKey)){
            isResult = false;
        }else{
            isResult = true;
        }
        return isResult;
    }

    /**
     * 申请解除审批状态的判断
     * @param taskDefinitionKey
     * @return false 代表审批  true代表处理
     */
    public boolean releaseProcessNew(String taskDefinitionKey){
        boolean isResult = false;
        if(USERTASK2.equals(taskDefinitionKey) || USERTASK1.equals(taskDefinitionKey)){
            isResult = false;
        }else{
            isResult = true;
        }
        return isResult;
    }

    /**
     * 请假审批
     * @param taskDefinitionKey
     * @return
     */
    public boolean leaveProcessNew(String taskDefinitionKey){
        boolean isResult = false;
        if(USERTASK2.equals(taskDefinitionKey) || USERTASK3.equals(taskDefinitionKey) || USERTASK4.equals(taskDefinitionKey)){
            isResult = false;
        }else{
            isResult = true;
        }
        return isResult;
    }

    public int getTaskType(String aType){
        int result = 0;
        if(DELETEPROCESS.equals(aType) || UPDATEPROCESS.equals(aType)){
            result = 2;
        }else{
            result = 1;
        }
        return result;
    }

}
