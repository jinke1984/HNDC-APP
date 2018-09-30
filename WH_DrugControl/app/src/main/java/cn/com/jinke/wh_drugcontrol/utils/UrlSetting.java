package cn.com.jinke.wh_drugcontrol.utils;

/**
 * Created by jinke on 2016/10/9.
 * 这个接口是用来存放请求地址的
 */

public abstract class UrlSetting implements CodeConstants{

    public String BASE_URL = "";
    public String OUT_BASE_URL = "";


    public String LOGIN_URL = BASE_URL + "sys/userManage.html?command=adminLogin";
    public String PERSION_URL = BASE_URL + "app/query.html?command=appList";

    /**
     * 定期尿检列表
     */
    public String URINE_URL = BASE_URL + "app/query.html?command=getUrineTestList";

    /**
     * 定期访谈列表
     */
    public String TALK_URL = BASE_URL + "app/query.html?command=getConversationList";

    /**
     * 定期报告列表
     */
    public String REPORT_URL = BASE_URL + "app/query.html?command=getRegularReportList";

    /**
     * // 获取居住位置图
     */
    public String HOUSE_PHOTO_URL = BASE_URL + "app/query.html?command=getHousePhoto";
    public String DOCID_URL = BASE_URL + "app/query.html?command=getDocument";
    public String SAVEFILE_URL = BASE_URL + "app/manage.html?command=uploadImg";
    public String GETDOCUMENTBYID = BASE_URL + "query/queryWHData.html?command=getDocumentById";
    public String HOUSE_PHOTO_SAVE_URL = BASE_URL + "/app/manage.html?command=saveHousePhoto";
    public String SAVE_REPORT = OUT_BASE_URL + "app/manage.html?command=saveRegularReport";
    public String HOME_URL = BASE_URL + "app/query.html?command=getWorkRemind";
    public String WORK_URL = BASE_URL + "app/query.html?command=getWorkRemindDel";
    public String UPLOAD_REGID_URL = BASE_URL + "sys/userManage.html?command=uploadSendNo";
    public String SOURCE_DETAIL_URL = BASE_URL + "app/query.html?command=getScoreDetailsList";
    public String AREA_URL = BASE_URL + "app/query.html?command=getSysOrganization";
    public String ADD_DJPHOTO_URL = BASE_URL + "app/manage.html?command=updateDrugInfoPhotoPath";
    public String PERSION_COLLECTION_URL = BASE_URL + "app/collection.html?command=collection";


    public String OUT_SAVEFILE_URL = OUT_BASE_URL + "app/manage.html?command=uploadImg";

    /**
     * 获取违反协议
     */
    public String FIND_VIOLATION_AGREEMENT = BASE_URL + "app/query.html?command=getViolationAgreement";

    /**
     * 获取审批人
     */
    public String FIND_AUDIT_PERSON_LIST = BASE_URL + "app/query.html?command=getNextPeople";

    /**
     * 获取协议书
     */
    public String FIND_AGREEMENT = BASE_URL + "app/query.html?command=getAgreement";
    public String FIND_LEAVE = BASE_URL + "queryChatRecordListInInit/queryWHData.html?command=findLeave";

    /**
     * 获取决定书
     */
    public String FIND_DECISION = BASE_URL + "app/query.html?command=getDecision";

    /**
     * 获取劝诫书、告诫书
     */
    public String FIND_EXHORT = BASE_URL + "app/query.html?command=findViolationNotice";

    /**
     * 获取继续执行记录
     */
    public String FINDDRUGSCONTINUE = BASE_URL + "app/query.html?command=getDrugsContinue";

    /**
     * 获取告知书
     */
    public String FINDNOTICE = BASE_URL + "app/query.html?command=getNotice";

    /**
     * 获取人员守则
     */
    public String FINDRULE = BASE_URL + "app/query.html?command=getRule";

    /**
     * 获取帮教计划
     */
    public String FINDHELPPLAN = BASE_URL + "app/query.html?command=findHelpPlan";

    /**
     * 获取中止记录
     */
    public String FINDDRUGSBREAK = BASE_URL + "app/query.html?command=getDrugsBreak";

    /**
     * 通知公告
     */
    public String FINDCHANNEL = BASE_URL + "app/query.html?command=getChannel";

    /**
     * 通知公告详情
     */
    public String FINDARTICLEDEL = BASE_URL + "app/query.html?command=getArticleDel";
    public String FINDPERSONBYIDCARD = BASE_URL + "queryChatRecordListInInit/queryWHData.html?command=findPersonByIdcard";
    public String GET_MY_TASK = BASE_URL + "app/query.html?command=getMyTask";
    public String GET_MY_TASK_LIST = BASE_URL + "app/query.html?command=getMyTaskList";
    public String TASK_DETAIL = BASE_URL + "app/query.html?command=getWorkflowNew";
    public String TASK_BACK = BASE_URL + "app/work.html?command=goBackWork";

    public String DELETE_DOC = BASE_URL + "app/work.html?command=deleteDoc";
    /**
     * 新增定期尿检
     */
    public String SAVE_URINE_TEST = OUT_BASE_URL + "app/manage.html?command=saveUrineTest";

    /**
     * 新增定期访谈
     */
    public String SAVE_CONVERSATION = OUT_BASE_URL + "app/manage.html?command=saveConversation";

    /**
     * 新增决定书
     */
    public String SAVE_DECISION = BASE_URL + "app/manage.html?command=saveDecision";

    /**
     * 新增告知书
     */
    public String SAVE_NOTIFY = BASE_URL + "app/manage.html?command=saveNotice";

    /**
     * 新增协议书
     */
    public String SAVE_AGREEMENT = BASE_URL + "app/manage.html?command=saveAgreement";

    /**
     * 新增人员守则
     */
    public String SAVE_RULE = BASE_URL + "app/manage.html?command=saveRule";

    /**
     * 新增计划书
     */
    public String SAVE_HELP_PLAN = BASE_URL + "app/manage.html?command=saveHelpPlan";

    /**
     * 新增劝诫书
     */
    public String SAVE_EXHORT = BASE_URL + "app/manage.html?command=saveViolationNotice";

    /**
     * 新增违反协议
     */
    public String SAVE_VIOLATE_AGREEMENT = BASE_URL + "app/manage.html?command=saveViolationAgreement";

    /**
     * 新增中止记录
     */
    public String SAVE_DRUGS_BREAK = BASE_URL + "app/manage.html?command=saveDrugsBreak";

    /**
     * 新增继续执行
     */
    public String SAVE_DRUGS_CONTINUE = BASE_URL + "app/manage.html?command=saveDrugsContinue";

    /**
     * 新增请假文档
     */
    public String SAVE_LEAVE = BASE_URL + "drug/saveManage.html?command=saveLeave";

    public String SAVEPERSONBASE1 = BASE_URL + "drug/saveManage.html?command=savePersonBase1";
    public String SAVEPERSONBASE2 = BASE_URL + "drug/saveManage.html?command=savePersonBase2";
    public String SAVEPERSONBASE3 = BASE_URL + "drug/saveManage.html?command=savePersonBase3";
    public String SAVEPERSONBASE4 = BASE_URL + "drug/saveManage.html?command=savePersonBase4";
    public String SAVEPERSONBASE5 = BASE_URL + "drug/saveManage.html?command=savePersonBase5";

    public String UPDATEURL = BASE_URL + "sys/userManage.html";

    /**
     * 发送聊天接口
     **/
    public String CHAT_SEND_CHAT = "/sys/userManage.html?command=sendMsg";

    /**
     * 上传聊天附件接口
     */
    public String CHAT_UPLOAD = OUT_BASE_URL + "/app/manage.html?command=uploadChatFile";

    /**
     * 综合查询
     */
    public String PERSION_INTERGRATED_QUERY = BASE_URL + "app/query.html?command=getZongHeSeo";
    /**
     * 社区戒毒/社区康复信息 详情
     */
    public String PERSION_COMMUNITY_RECOVER_INFO = BASE_URL + "app/query.html?command=getCommunityDrugRehabInfo";

    /**
     * 接受药物治疗基本信息
     */
    public String PERSION_ACCEPT_DRUG_MAINTENANCE_THERAPY = BASE_URL + "app/query.html?command=getMedicineTreat";

    /**
     * 就业推荐
     */
    public String PERSION_EMPLOYMENT_RECOMMEND = BASE_URL + "app/query.html?command=getSettlementList";

    /**
     * 就业意愿
     */
    public String PERSION_EMPLOYMENT_INTENTION = BASE_URL + "app/query.html?command=getStationEmployList";

    /**
     * 帮扶救助
     */
    public String PERSION_AID_AND_RESCURE = BASE_URL + "app/query.html?command=getHelpingList";

    /**
     * 身体检查情况
     */
    public String PERSION_PHYSICAL_EXAMINATION = BASE_URL + "app/query.html?command=getPhysicalList";

    /**
     * 心里咨询情况
     */
    public String PERSION_HEART_COUNSELING = BASE_URL + "app/query.html?command=getMentalityList";

    /**
     * 公安部门办案查获信息
     */
    public String PERSION_SEIZED_INFORMATION = BASE_URL + "app/query.html?command=getPoliceCaseInfo";

    /**
     * 特殊病区入院/出院记录
     */
    public String PERSION_SPECIAL_WARD = BASE_URL + "app/query.html?command=getHospitalInAndOutList";

    /**
     * 戒毒监管场所入所/出所信息
     */
    public String PERSION_DETOXIFICATION_SUPERVISION = BASE_URL + "app/query.html?command=getRehabInAndOutList";

    /**
     * 服药情况记录
     */
    public String PERSION_MEDICATION_SITUATION = BASE_URL + "app/query.html?command=getMedicineList";

    /**
     * 就业安置
     */
    public String PERSION_EMPLOYMENT_PLACEMENT = BASE_URL + "app/query.html?command=getSettlementList";

    /**
     * 就业推荐
     */
    public String PERSION_EMPLOYMENT_RECOMMENT = BASE_URL + "app/query.html?command=getRecommentList";

    /**
     * 判断档案创建时间
     */
    public String JUDGE_CREATETIME_URL = BASE_URL + "app/work.html?command=checkDocCreateTime";
    /**
     * 申请修改流程
     */
    public String CHANGE_APPLY_URL = BASE_URL + "app/work.html?command=changeApply";
    /**
     * 档案修改/删除发起流程
     */
    public String BEGIN_UPDATE_OR_DELETE_URL = BASE_URL + "app/work.html?command=beginUpdateOrDelete";
    /**
     * 申请删除流程
     */
    public String DELETE_APPLY_URL = BASE_URL + "app/work.html?command=deleteApply";
    /**
     * 修改、删除审批
     */
    public String UPDATE_DELETE_AUDIT_URL = BASE_URL + "app/work.html?command=updateOrdeleteAudit";
    /**
     * 修改、删除审批
     */
    public String UPDATE_DELETE_AUDIT_NEW_URL = BASE_URL + "app/work.html?command=updateOrdeleteAuditNew";
    /**
     * 帮教计划审批
     */
    public String HELP_PLAN_AUDIT_URL = BASE_URL + "app/work.html?command=helpPlanAudit";
    /**
     * 定期评估审批
     */
    public String REGULAR_APPRAISAL_AUDIT_URL = BASE_URL + "app/work.html?command=regularAppraisalAudit";
    /**
     * 解除申请记录审批
     */
    public String RELEASE_AUDIT_URL = BASE_URL + "app/work.html?command=releaseAudit";
    /**
     * 变更执行审批
     */
    public String CHANGE_LOCATION_AUDIT_URL = BASE_URL + "app/work.html?command=changeLocationAudit";
    /**
     * 请假审批
     */
    public String LEAVE_AUDIT_URL = BASE_URL + "app/work.html?command=leaveAudit";

    /**
     * 公用审批接口
     */
    public String COMMON_APPROVE_URL = BASE_URL + "app/work.html?command=commonAudit";

    /**
     * 根据流程实例id获取修改删除记录表
     */
    public String GET_MODIFY_DELETE_URL = BASE_URL + "app/query.html?command=getModifyDelete";


    /**
     * 更新app 使用时间
     */
    public String UPDATE_APP_USE_TIME = BASE_URL + "sys/userManage.html?command=updateLastUseTime";


    /**
     * 人脸识别
     */
    public String FACE_COMPARE = OUT_BASE_URL + "app/face.html?command=compare";

    /**
     * 下一步审批人合并
     */
    public String NEXT_PEOPLE = BASE_URL + "/app/work.html?command=getNextPeople";

    /**
     * 视频聊天房间号推送
     */
    public String CHAT_ROOM_NO = OUT_BASE_URL + "/sys/userManage.html?command=getChatRoom";

    /**
     * app查看附件
     */
    public String GET_PC_FILE = BASE_URL + "/sys/userManage.html?command=getPCFile";

    /**
     * 椰风送暖的二维码下载地址
     */
    public String DOWNLOAD_APK_PATH = OUT_BASE_URL + "sys/userManage.html?command=scanQRDownload";

    /**
     * 二维码验证
     */
    public String QRCODEURL = BASE_URL + "app/query.html?command=qrcode";


    /**
     * 外出请假记录查询
     */
    public String LEAVELIST = "app/query.html?command=queryLeaveList";


    /**
     * 执行地变更记录查询
     */
    public String CHANGELIST = "app/query.html?command=queryChangeList";

    /**
     * 获取信息推送历史类型(最近的信息)
     */
    public String HISTORYMESSAGE = "queryPushInfo/main.html?command=getInfoTypeByUserId";

    /**
     * 根据类型获取历史推送信息
     */
    public String HISTORYMESSAGEDETAIL = "queryPushInfo/main.html?command=getPushInfoByUserId";

    /**
     * 签到功能
     */
    public String ADDSIGNIN = "sys/userManage.html?command=userSignIn";

    /**
     * 查询签到功能
     */
    public String QUERYSIGNIN = "sys/userManage.html?command=findUserSignIn";
}
