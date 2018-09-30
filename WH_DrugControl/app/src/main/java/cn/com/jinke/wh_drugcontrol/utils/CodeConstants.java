package cn.com.jinke.wh_drugcontrol.utils;

import cn.cloudwalk.FaceInterface;

/**
 * Created by jinke on 16/8/9.
 * 存放一些返回参数的接口
 */
public interface CodeConstants {

    //------------------  档案的类型操作 ----------------//
    /**
     * 删除类型
     */
    int T_DELETE = 2;

    /**
     * 修改类型
     */
    int T_MODIFY = 1;

    /**
     * 禁毒专干
     */
    String JDZG = "1";

    /**
     * 工作人员
     */
    String GZRU = "2";

    /**
     * 居委会的level
     */
    String JWHLEVEL = "5";

    /**
     * 已收藏
     */
    int YES_COLLECTION = 1;

    /**
     * 未收藏
     */
    int NO_COLLECTION = 0;

    String PUSH_ID = "push_id";
    String KEY_AUTO_FOCUS = "preferences_auto_focus";

    int WORK_TALK = 2;
    int WORK_URINE = 1;
    int WORK_REPORT = 4;
    int WORK_EVALUATE = 3;
    int WORK_DYNAMIC = 5;

    int REQUEST_CODE_GET_PIC = 1000;

    String CHAT_PHOTO_NAME = ".jpg";
    String DATA = "data";
    String URL = "url";

    /**
     * 面对面
     */
    int MDM = 0;

    /**
     * 不是面对面
     */
    int BMDM = 1;

    /**
     * 二维码
     */
    int EWM = 2;

    /**
     * 视频访谈
     */
    int SPFT = 3;

    int PHOTORESOULT = 3;// 结果
    String IMAGE_MASTER_CROP = "camera_result_temp";// 存储的文件名�?

    float BEEP_VOLUME = 0.10f;
    long BOOTTIME = 3000;   //启动页的时间为3秒


    String B_NUMBER = "number";
    String B_FROM = "from";

    String ACCOUNT = "username";
    String PASSWORD = "password";
    String AREAID = "id";

    int FAIL = 0;
    int SUCCESS = 1;
    int SUCCESS_AUTO = 2;

    int FINISH_YES = 1;
    int FINISH_NO = 0;

    int PAGE_START = 0;
    int TOTAL = 0;
    int DATA_PAGE_SIZE = 10;    //一次显示10条数据

    String USERID = "user_id";
    String INPUT = "input";
    String START = "start";
    String LIMIT = "limit";
    String DRUGID = "drugId";
    String PERSIONID = "persionId";
    String APPNEWLIST = "appNewsList";

    String NUM = "num";
    String AGREEMENT = "agreement";
    String DOCUMENT = "document";
    String SUSPENDED = "suspended";
    String CONTINUE = "continue";
    String FROMCONTINUE = "fromcontinue";
    String PERSION = "persion";
    String BUNDLE = "bundle";
    String OBJECT = "object";
    String SUCC = "success";
    String PHOTOTYPE = "photoType";
    String DOCID = "docId";
    String PHOTOURL = "photoUrl";
    String FILEADD = "fileAdd";
    String MAKEPERSION = "makePerson";
    String PERSONID = "personId";
    String CURRENTID = "currentId";
    String MODIFYITEM = "modifyItem";
    String UPDATEMODEL = "updateModel";
    String DELETEREASON = "deleteReason";
    String DELETEMODEL = "deleteModel";
    String IDCARD = "identityCard";
    String USER_ID = "userId";
    String USER_TYPE = "userType";
    String AREA_ID = "areaId";
    String URINEPLACE = "urinePlace";
    String TESTCONTACTPERSION = "testcontactPerson";
    String URINERESULT = "urineResult";
    String TITLE = "title";
    String SENDNO = "sendNo";
    String ID = "id";
    String CREATETIME = "createTime";
    String IDNO = "idCard";
    String ORGPARENTID = "orgParentId";
    String CONTROLID = "controlId";
    String ISCOLLECTION = "isCollection";
    String DOCTYPE = "docType";
    String SOURCEID = "sourceId";
    String ENTITYID = "entityId";
    String ENTITYDELID = "entityDelId";
    String COMMENT = "comment";
    String DEALSTATUS = "dealStatus";
    String OPTTYPE = "optType";
    String TASKENTITY = "taskEntity";
    String TASKINFO = "taskInfo";
    String PROCESSINSTANCEID = "processInstanceId";
    String FLOWTYPE= "flowType";
    String CHANGEID = "changeId";
    String ISBACK = "isBack";

    String PAGENO = "pageNo";
    String PAGESIZE = "pageSize";
    String P_SIZE = "pagesize";

    String PHOTO = "photo";
    String TYPE = "type";
    String NOTICEDATE = "noticeDate";
    String HANDLINGDEPARTMENT = "handlingDepartment";
    String RECEIVEDATE = "receiveDate";
    String DEPTSIGNDATE = "deptSignDate";
    String ISDECISION = "isDecision";
    String NONDECISIONREASON = "nonDecisionReason";
    String DECISIONNO1 = "decisionNo1";
    String DECISIONNO2 = "decisionNo2";
    String DECISIONNO3 = "decisionNo3";
    String DECISIONNO4 = "decisionNo4";
    String DRUGSBEGINDATE = "drugsBeginDate";
    String DRUGSENDDATE = "drugsEndDate";
    String REGISTERDATE = "registerDate";
    String CATCHDETAIL = "catchDeatil";
    String DRUGSDEPT = "drugsDept";
    String DRUGSDEPTSIGNDATE = "drugsDeptSignDate";
    String SIGNDATE = "signDate";
    String REALNAME = "realName";
    String PREREALNAME = "preRealName";
    String NICKNAME = "nickname";
    String SEX = "sex";
    String BIRTHDATE = "birthdate";
    String COMMUNITYID = "communityID";
    String COMMUNITYCODE = "communityCode";
    String COMMUNITYNAME = "communityName";
    String NATION = "nation";
    String HEIGHT = "height";
    String LIVINGWITHPARENTS = "livingWithParents";
    String MARRIAGE = "marriage";
    String HEALTHCONDITION = "healthCondition";
    String HEALTHCONDITIONDETAIL = "healthConditionDetail";
    String FAMILYCONDITION = "familyCondition";
    String HOUSEHOLDADDRESSCODE = "householdAddressCode";
    String HOUSEHOLDADDRESSNAME = "householdAddressName";
    String HOUSEHOLDADDRESS = "householdAddress";
    String CURRENTADDRESSCODE = "currentAddressCode";
    String CURRENTADDRESSNAME = "currentAddressName";
    String CURRENTADDRESS = "currentAddress";
    String CELLPHONE = "cellphone";
    String TELEPHONE = "telePhone";
    String QQ = "qq";
    String WEIXIN = "weixin";
    String MAIL = "mail";
    String POLITICALLANDSCAPE = "politicalLandscape";
    String POLITICALLANDSCAPEDETAIL = "politicalLandscapeDetail";
    String EDUCATION = "education";
    String VOCATIONALTRAINING = "vocationalTraining";
    String SKILLS = "skills";
    String SKILLSDETAIL = "skillsDetail";
    String EMPLOYMENTCONDITION = "employmentCondition";
    String INCOME = "income";
    String SOCIALSECURITY = "socialSecurity";
    String SOCIALSECURITYDETAIL = "socialSecurityDetail";
    String PERSONALRESUME = "personalResume";
    String FIRSTDRUGSDATE = "firstDrugsDate";
    String DRUGTYPES = "drugTypes";
    String DRUGTYPESDETAIL = "drugTypesDetail";
    String CRIMECONDITION = "crimeCondition";
    String GRIDPERSON = "gridPerson";
    String COMMUNITYPOLICE = "communityPolice";
    String PHOTOADDRESS = "photoAddress";
    String REMARK = "remark";
    String SENDDATE = "sendDate";
    String SENDLACTION = "sendLaction";
    String SENDTYPE = "sendType";
    String SENDTYPEDETAIL = "sendTypeDetail";
    String ISDENY = "isDeny";
    String DENYREASON = "denyReason";
    String ISREPLACE = "isReplace";
    String REPLACEPERSON = "replacePerson";
    String REPLACEREASON = "replaceReason";
    String RELATION = "relation";
    String WITNESS = "witness";
    String STAMPDATE = "stampDate";
    String PLANCOMMENT = "planComment";
    String AUDITDEPT = "auditDept";
    String AUDITPERSON = "auditPerson";
    String AUDITPERSONID = "auditPersonId";
    String COMMUNITY = "community";
    String BREAKREASON = "breakReason";
    String BREAKDATE = "breakDate";
    String BREAKID = "breakID";
    String CONSTRAINTS = "constraints";
    String ISOLATIONPERIOD = "isolationPeriod";
    String DESCRIPTION = "description";
    String FILEADDDESC = "fileAddDesc";
    String DECIDEDEPT = "decideDept";
    String APPROVEDEPT = "approveDept";
    String CONTINUEDATE = "continueDate";
    String CONTINUEREASON = "continueReason";
    String PUNISHCONDITION = "punishCondition";
    String REPAPSE = "repapse";
    String VIOLATECONDITINO = "violateConditino";
    String VIOLATELEVEL = "violateLevel";
    String VIOLATEDATE = "violateDate";
    String VIOLATIONID = "violationId";
    String NOTICEREASON = "noticeReason";
    String NOTICEREASONREMARK = "noticeReasonRemark";
    String NOTICETYPE = "noticeType";
    String VIOLATIONUSERID = "violationUserId";
    String VIOLATIONUSER = "violationUser";
    String OCCURDATE = "occurDate";
    String OCCURPLACE = "occurPlace";
    String PUNISHDEPT = "punishDept";
    String WITNESSPAPERTYPE = "witnessPaperType";
    String WITNESSPAPERNO = "witnessPaperNO";
    String LEAVEDAYS = "leaveDays";
    String LEAVECOUNTS = "leaveCounts";
    String DENYCOUNTS = "denyCounts";
    String DENYDATE = "denyDate";
    String DESTINATION = "destination";
    String LEAVEREASON = "leaveReason";
    String BEGINDATE = "beginDate";
    String BEGINENDDATE = "beginEndDate";
    String APPLYDATE = "applyDate";
    String NEXTPEOPLE = "nextPeople";
    String NEXTPEOPLEDEL = "nextPeopleDel";
    String NEXTDEALPEOPLE = "nextDealPeople";
    String IDENTITYCARD = "identityCard";
    String FAMILYCONDITIONDETAIL = "familyConditionDetail";
    String BEGINTIME = "beginTime";
    String FLAG = "flag";

    String URINECOUNTS = "urineCounts";
    String TALKER = "talker";
    String TALKOBJECT = "talkObject";
    String TALKCOUNTS = "talkCounts";
    String TALKPLACE = "talkPlace";
    String FACETOFACE = "isCamera";
    String RECORDPERSION = "recordPerson";
    String RECORDCOUNTS = "reportCounts";
    String RECORDPLACE = "reportPlache";
    String FINDAPPVERSION = "getAppVersion";

    int COMPLETED_NONE = 0;     // 未填写
    int COMPLETED_DOING = 1;    // 正在录入
    int COMPLETED_DONE = 2;     // 录入完成

    String SP_KEY_RELOGIN = "sp_key_relogin";
    String SP_KEY_PASSWORD = "sp_key_password";
    String SP_KEY_SPE_NOTICE = "sp_key_spe_notice";
    String SP_KEY_USE_GESTURE = "sp_key_use_gesture";

    String ACTION_MESSAGE_RECEIVED = "cn.com.jinke.wh_drugcontrol.MESSAGE_RECEIVED_ACTION";

    String EXTRA_SET_GESTURE_PASSWORD = "extra_set_gesture_password";
    String EXTRA_FIRST_IN_APP = "extra_first_in_app";
    String EXTRA_PHOTO_PATH = "extra_photo_path";
    String EXTRA_PERSON = "extra_gowster";

    String TARGET_UI = "target_ui";

    int REQUEST_CODE_GET_SCAN_PIC = 100; // 获取扫描后的图片request_code
    // chat params
    String CHAT_PERSION = "chat_persion";
    String B_TYPE = "type";
    String B_TITLE = "title";
    String B_ENTITY = "entity";
    String B_PATH = "path";
    String B_ID = "id";
    String B_AREA_ID = "areaid";
    String B_URL = "url";
    String B_NAME = "name";
    String B_ID_CARD = "id_card";
    String B_ITEM = "item";
    String B_START = "start";
    String B_SEX = "sex";
    String B_CODE = "code";
    String B_MAINPAGE = "mainpage";
    String B_DRUG_ID = "drug_id";
    String B_ENTER = "enter";
    String B_AREA_NAME = "area_name";
    String B_PEERSION = "persion";
    String B_ADDRESS = "address";
    String B_COLLECT = "collect";

    String POSITION_HOUSE = "position_house";
    String POSITION = "position";
    String SEND_TYPE = "sendType";
    String COMMAND = "command";
    String MSG_TYPE = "msg_type";
    String TO_NOS = "toNo";
    String CONTENT = "content";
    String FROM_NO = "fromNo";
    String FROM_NAME = "from_name";

    String K_CASE = "cause";
    String K_DATA = "data";
    String K_URL = "url";
    String K_SUCCESS = "success";
    String RESULT = "result";
    String URLA = "imgA";
    String INFO = "info";
    String SCORE = "score";
    String IMGAURL = "imgAUrl";
    String FILEPATH = "filePath";

    //聊天使用的自定义推送 字段
    String CHAT_USER_TYPE = "useType";
    String CHAT_SEND_TYPE = "sendType";
    String CHAT_MSG_TYPE = "msgType";
    String CHAT_MSG_CONTENT = "content";
    String CHAT_PERSION_SEX = "sex"; //吸毒人员性别
    String BOOLEAN  = "boolean";
    String LATITUDE = "latitude";
    String LONGITUDE = "longitude";
    String CITY = "city";
    String APPVERSION = "appVersion";
    String PERSIONTYPE = "personType";
    String STARTDATE = "startDate";
    String ENDDATE = "endDate";

    //--------------- 任务相关的状态码 ---------------

    /**
     * 审批通过的状态码
     */
    String SP_PASS_CODE = "1";

    /**
     * 审批打回的状态码
     */
    String SP_BACK_CODE = "2";

    /**
     * 修改类型
     */
    String MODIFY = "1";

    /**
     * 删除类型
     */
    String DELETE = "2";

    /**
     * 编译类型为具体产品化的版本的标识
     */
    String BUILD_TYPE = "product";

    /**
     * 活体检测的配置
     */
    //TODO 根据2018-4-19日张克利沟通的情况，活体检测的步骤修改为张嘴
//    int liveCount = 3;

    int liveCount = 1;

    int liveLevel = FaceInterface.LevelType.LEVEL_STANDARD;

    int liveTime = 10;

    /**
     *  人脸比对比较阀值
     */
    double F_RESULT = 0.80;

    /**
     * 模型文件根目录
     */
    String MODULES = "modules";

    /**
     * 模型文件压缩包
     */
    String MODULES_ZIP = "modules.zip";

    /**
     * 请配置服务器地址
     */
    String faceserver = "http://36.101.208.228:7010";

    /**
     * 请配置用户名
     */
    String faceappid = "user";

    /**
     * 请配置密码
     */
    String faceappser = "12345";

    /**
     * 删除审批
     */
    String DELETEPROCESS = "deleteProcess";

    /**
     * 修改审批
     */
    String UPDATEPROCESS = "updateProcess";

    /**
     * 变更执行地审批
     */
    String CHANGEPROCESS = "changeProcess";

    /**
     * 帮扶救助审批
     */
    String HELPPLANPROCESS = "helpPlanProcess";

    /**
     * 请假审批
     */
    String LEAVEAUDITPROCESS = "leaveAuditProcess";

    /**
     * 定期评估审批
     */
    String REGULARAPPRAISALPROCESS = "regularAppraisalProcess";

    /**
     * 高危积分审批
     */
    String HIGRISKSCOR = "HigRiskScor ";

    /**
     * 申请解除审批
     */
    String RELEASEPROCESS = "releaseProcess";

    /**
     *  客户端内外网标识存sp的key
     */
    String SWITCH_NET = "switch_net";


    String MSG_TITLE = "title";
    String MSG_IMG_URL = "img_url";
    String MSG_LINK_URL = "link";
}
