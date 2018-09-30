package cn.com.jinke.wh_drugcontrol.persion.constants;

import android.content.Context;
import android.text.TextUtils;

import cn.com.jinke.wh_drugcontrol.R;
import cn.com.jinke.wh_drugcontrol.booter.ProjectApplication;

/**
 * Created by xkr on 2017/11/1.
 * 吸毒人员工具类
 *
 */

public class PersionUtil {
    /**
     * 0执行中 默认状态
     1死亡 自动执行归档
     2请假
     3中止 继续执行后 变为 0执行中
     4终止 手动归档 专干发起申请
     5变更执行地 自动执行归档
     9解除 手动归档 专干发起申请
     */
    public static String getStatusStr(int status){
        Context context = ProjectApplication.getContext();
        String statusStr = "";
        switch (status){
            case 0:
                statusStr = context.getString(R.string.status_0);
                break;
            case 1:
                statusStr = context.getString(R.string.status_1);
                break;
            case 2:
                statusStr = context.getString(R.string.status_2);
                break;
            case 3:
                statusStr = context.getString(R.string.status_3);
                break;
            case 4:
                statusStr = context.getString(R.string.status_4);
                break;
            case 5:
                statusStr = context.getString(R.string.status_5);
                break;
            case 9:
                statusStr = context.getString(R.string.status_9);
                break;
        }
        return statusStr;
    }

    public static String chekParamsAndReturnStr(String parm){
        if (TextUtils.isEmpty(parm) || parm.equals("null"))
        {
            parm = ProjectApplication.getContext().getString(R.string.params_check_null);
        }
        return parm;
    }

    /**
     * 1强制隔离戒毒所送入 2办案单位送入 3监管场所送入 4其他


     * @param way
     * @return
     */
    public static String getInHospitalWay(String way){
        String result = null;
        if("1".equals(way)){
            result = "强制隔离戒毒所送入";
        }else if("2".equals(way)){
            result = "办案单位送入";
        }else if("3".equals(way)){
            result = "监管场所送入";
        }else if("4".equals(way)){
            result = "其他";
        }
        return chekParamsAndReturnStr(result);
    }

    /**
     * 1伤病治愈康复 2伤病缓解出院 3转院继续治疗 4其他

     * @param reason
     * @return
     */
    public static String getOutHospitalReason(String reason){
        String result = null;
        if("1".equals(reason)){
            result = "伤病治愈康复";
        }else if("2".equals(reason)){
            result = "伤病缓解出院";
        }else if("3".equals(reason)){
            result = "转院继续治疗";
        }else if("4".equals(reason)){
            result = "其他";
        }
        return chekParamsAndReturnStr(result);
    }

    /**
     * 1返回场所 2转入其他医院 3返回社区 4返回社区执行 5其他

     * @param reason
     * @return
     */
    public static String getOutHospitalDirection(String reason){
        String result = null;
        if("1".equals(reason)){
            result = "返回场所";
        }else if("2".equals(reason)){
            result = "转入其他医院";
        }else if("3".equals(reason)){
            result = "返回社区";
        }else if("4".equals(reason)){
            result = "社区执行";
        }else if("5".equals(reason)){
            result = "其他";
        }
        return chekParamsAndReturnStr(result);
    }

    /**
     * 诊断周期
     1短期 2三个月 3六个月
     * @param week
     * @return
     */
    public static String getDiagnoseWeek(String week){
        String result = null;
        if("1".equals(week)){
            result = "短期";
        }else if("2".equals(week)){
            result = "三个月";
        }else if("3".equals(week)){
            result = "六个月";
        }
        return chekParamsAndReturnStr(result);
    }

    /**
     * 1身体状况无明显变化 2身体疾病明显缓解 3生理机能明显恢复

     * @param resu
     * @return
     */
    public static String getDiagnoseResult(String resu){
        String result = null;
        if("1".equals(resu)){
            result = "身体状况无明显变化";
        }else if("2".equals(resu)){
            result = "身体疾病明显缓解";
        }else if("3".equals(resu)){
            result = "生理机能明显恢复";
        }
        return chekParamsAndReturnStr(result);
    }

    /**
     * 文化程度 :
     1文盲/半文盲 2小学 3初中 4高中 5大学专科 6大学本科及以上

     * @param education
     * @return
     */
    public static String getEducation(String education){

        String result = null;
        if("1".equals(education)){
            result = "盲/半文盲";
        }else if("2".equals(education)){
            result = "小学";
        }else if("3".equals(education)){
            result = "初中";
        }else if("4".equals(education)){
            result = "高中";
        }else if("5".equals(education)){
            result = "大学专科";
        }else if("6".equals(education)){
            result = "大学本科及以上";
        }
        return chekParamsAndReturnStr(result);
    }

    /**
     * 获取次数
     * @param count
     * @return
     */
    public static String getTimesCount(String count){
        String result = chekParamsAndReturnStr(count);
        if(!ProjectApplication.getContext().getString(R.string.params_check_null).equals(result)){
            result += "次";
        }
        return result;
    }
    public static String getTimesCount(int count){
        return getTimesCount(""+count);
    }

    /**
     * 薪水
     * @param salary
     * @return
     */
    public static String getSalaryUnit(int salary){
        String result = salary + "元/月";
        return result;
    }

    /**
     * 是否在职
     * @param status
     * @return
     */
    public static String getPositionStatus(String status){
        String result = null;
        if("0".equals(status)){
            result = "在职";
        }else if("1".equals(status)){
            result = "离职";
        }
        return chekParamsAndReturnStr(result);
    }

    /**
     * 获取离职原因
     * @param reason
     * @return
     */
    public static String getLeaveReason(String reason){
        /**
         * {id:"1",text:"身体不适应"},
         {id:"2",text:"薪酬不理想"},
         {id:"3",text:"工作繁重"},
         {id:"4",text:"不适应工作时间"},
         {id:"5",text:"上下班不便"},
         {id:"6",text:"与同事难以相处"},
         {id:"7",text:"不能遵守劳动纪律"},
         {id:"8",text:"不适应岗位要求"},
         {id:"9",text:"个人家庭原因"},
         {id:"10",text:"有其他就业岗位"},
         {id:"11",text:"因涉毒原因被查处"},
         {id:"12",text:"非涉毒原因被查处"},
         {id:"13",text:"其他"}

         */
        String result = reason;
        if("1".equals(reason)){
            result = "身体不适应";
        }else if("2".equals(reason)){
            result = "薪酬不理想";
        }else if("3".equals(reason)){
            result = "工作繁重";
        }else if("4".equals(reason)){
            result = "不适应工作时间";
        }else if("5".equals(reason)){
            result = "上下班不便";
        }else if("6".equals(reason)){
            result = "与同事难以相处";
        }else if("7".equals(reason)){
            result = "不能遵守劳动纪律";
        }else if("8".equals(reason)){
            result = "不适应岗位要求";
        }else if("9".equals(reason)){
            result = "个人家庭原因";
        }else if("10".equals(reason)){
            result = "有其他就业岗位";
        }else if("11".equals(reason)){
            result = "因涉毒原因被查处";
        }
        else if("12".equals(reason)){
            result = "非涉毒原因被查处";
        }
        else if("13".equals(reason)){
            result = "其他";
        }
        return result;
    }

    /**
     * 安置类别
     * @param settlementType
     * @return
     */
    public static String getSettlementType(String settlementType){
        //安置类别  1集中安置 2分散安置 3就地安置 4帮助创业 5自主择业
        String typeResult="";
        if("1".equals(settlementType)){
            typeResult = "集中安置";
        }else if("2".equals(settlementType)){
            typeResult = "分散安置";
        }else if("3".equals(settlementType)){
            typeResult = "就地安置";
        }else if("4".equals(settlementType)){
            typeResult = "帮助创业";
        }else if("5".equals(settlementType)){
            typeResult = "自主择业";
        }
        return chekParamsAndReturnStr(typeResult);
    }

    /**
     * 毒品类型
     * @param type
     * @return
     */
    public static String getDrugType(String type){
        String result = null ;
        if("1".equals(type)){
            result = "烟吸";
        }else if("2".equals(type)){
            result = "吸烫";
        }else if("3".equals(type)){
            result = "注射";
        }else if("4".equals(type)){
            result = "口服";
        }else if("5".equals(type)){
            result = "鼻吸";
        }
        return chekParamsAndReturnStr(result);
    }

    /**
     * 获取毒品名称
     * @param drugName
     * @return
     */
    public static String getDrugName(String drugName){
        String result = null ;
        if("1".equals(drugName)){
            result = "传统类毒品";
        }else if("2".equals(drugName)){
            result = "合成类毒品";
        }else if("3".equals(drugName)){
            result = "滥用致瘾药物";
        }else if("4".equals(drugName)){
            result = "两类以上";
        }
        return chekParamsAndReturnStr(result);
    }

    /**
     * 拼接 决定书文号
     * @param nos
     * @return
     */
    public static String mosaicNo(String... nos){
        StringBuilder sb = new StringBuilder();
        for (int count = 0;count < nos.length;count++){
            sb.append(chekParamsAndReturnStr(nos[count]));
        }
        return sb.toString();
    }

    /**
     * 获取 公安机关的处置结果
     * @param statu
     * @return
     */
    public static String getPublicSecurityDealResult(String statu){
        String result="无";
        String[] ary = ProjectApplication.getContext().getResources().getStringArray(R.array.public_security_result);
        if("1".equals(statu)){
            result = ary[0];
        }else if("2".equals(statu)){
            result = ary[1];
        }else if("3".equals(statu)){
            result = ary[2];
        }else if("4".equals(statu)){
            result = ary[3];
        }else if("5".equals(statu)){
            result = ary[4];
        }
        else if("6".equals(statu)){
            result = ary[5];
        }else if("7".equals(statu)){
            result = ary[6];
        }else if("8".equals(statu)){
            result = ary[7];
        }else if("9".equals(statu)){
            result = ary[8];
        }else if("10".equals(statu)){
            result = ary[9];
        }else if("11".equals(statu)){
            result = ary[10];
        }else if("12".equals(statu)){
            result = ary[11];
        }
        return result;
    }


    /**
     * 管控级别(1:1级，2：二级，3：三级)
     * @param level
     * @return
     */
    public static String getCurrentControlLevel(int level){
        String result = null ;
        switch (level){
            case 1:
                result = "一级";
                break;
            case 2:
                result = "二级";
                break;
            case 3:
                result = "三级";
                break;
        }
        return chekParamsAndReturnStr(result);
    }
}
