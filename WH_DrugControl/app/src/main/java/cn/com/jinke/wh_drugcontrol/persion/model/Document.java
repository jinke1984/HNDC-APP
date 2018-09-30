package cn.com.jinke.wh_drugcontrol.persion.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jinke on 2017/8/28.
 */

public class Document implements Serializable {

    /**
     * 新建[未归档]
     */
    public static final String DOC_STATUS_NEW = "1";

    /**
     * 已归档
     */
    public static final String DOC_STATUS_END = "2";


    private String id;

    /**
     * 吸毒人员ID
     */
    private String personID;
    /**
     * 档案编号
     */
    private String docNO;
    /**
     * 决定书
     */
    private Integer decisionEntity = 0;
    /**
     * 告知书
     */
    private Integer noticeEntity = 0;
    /**
     * 协议书
     */
    private Integer agreementEntity = 0;
    /**
     * 人员手册
     */
    private Integer ruleEntity = 0;
    /**
     * 劝告诫书
     */
    private Integer violationNoticeEntity = 0;
    /**
     * 违反协议记录
     */
    private Integer violationAgreementEntity = 0;
    /**
     * 计划书
     */
    private Integer helpPlanEntity = 0;
    /**
     * 位置图
     */
    private Integer housePhotoEntity = 0;
    /**
     * 尿检通知书
     */
    private Integer urineNoticeEntity = 0;
    /**
     * 尿检通知
     */
    private Integer urineNoticeDateEntity = 0;
    /**
     * 尿检记录
     */
    private Integer urineTestEntity = 0;
    /**
     * 参加活动情况
     */
    private Integer joinActivityEntity = 0;
    /**
     * 救助情况
     */
    private Integer helpConditionEntity = 0;
    /**
     * 定期访谈
     */
    private Integer conversationEntity = 0;
    /**
     * 定期报告
     */
    private Integer regularReportEntity = 0;
    /**
     * 定期评估
     */
    private Integer reportAppraisalEntity = 0;
    /**
     * 变更执行地
     */
    private Integer changeLocationEntity = 0;
    /**
     * 请假
     */
    private Integer leaveAuditEntity = 0;
    /**
     * 中止执行
     */
    private Integer discontinueDrugsEntity = 0;
    /**
     * 继续执行
     */
    private Integer continueDrugsEntity = 0;
    /**
     * 终止执行
     */
    private Integer stopDrugsEntity = 0;
    /**
     * 解除审批表
     */
    private Integer releaseEntity = 0;
    /**
     * 解除通知书
     */
    private Integer releaseNoticeEntity = 0;
    /**
     * 1:未归档；2:归档
     */
    private String docStatus = DOC_STATUS_NEW;
    /**
     * 归档人
     */
    private String archivePerson;
    /**
     * 归档时间
     */
    private Date archiveDate;
    /**
     * 决定书类型,社区戒毒/社区康复
     */
    private String type;

    /**
     * 档案状态
     */
    private int dealStatus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getDocNO() {
        return docNO;
    }

    public void setDocNO(String docNO) {
        this.docNO = docNO;
    }

    public Integer getDecisionEntity() {
        return decisionEntity;
    }

    public void setDecisionEntity(Integer decisionEntity) {
        this.decisionEntity = decisionEntity;
    }

    public Integer getNoticeEntity() {
        return noticeEntity;
    }

    public void setNoticeEntity(Integer noticeEntity) {
        this.noticeEntity = noticeEntity;
    }

    public Integer getAgreementEntity() {
        return agreementEntity;
    }

    public void setAgreementEntity(Integer agreementEntity) {
        this.agreementEntity = agreementEntity;
    }

    public Integer getRuleEntity() {
        return ruleEntity;
    }

    public void setRuleEntity(Integer ruleEntity) {
        this.ruleEntity = ruleEntity;
    }

    public Integer getViolationNoticeEntity() {
        return violationNoticeEntity;
    }

    public void setViolationNoticeEntity(Integer violationNoticeEntity) {
        this.violationNoticeEntity = violationNoticeEntity;
    }

    public Integer getViolationAgreementEntity() {
        return violationAgreementEntity;
    }

    public void setViolationAgreementEntity(Integer violationAgreementEntity) {
        this.violationAgreementEntity = violationAgreementEntity;
    }

    public Integer getHelpPlanEntity() {
        return helpPlanEntity;
    }

    public void setHelpPlanEntity(Integer helpPlanEntity) {
        this.helpPlanEntity = helpPlanEntity;
    }

    public Integer getHousePhotoEntity() {
        return housePhotoEntity;
    }

    public void setHousePhotoEntity(Integer housePhotoEntity) {
        this.housePhotoEntity = housePhotoEntity;
    }

    public Integer getUrineNoticeEntity() {
        return urineNoticeEntity;
    }

    public void setUrineNoticeEntity(Integer urineNoticeEntity) {
        this.urineNoticeEntity = urineNoticeEntity;
    }

    public Integer getUrineNoticeDateEntity() {
        return urineNoticeDateEntity;
    }

    public void setUrineNoticeDateEntity(Integer urineNoticeDateEntity) {
        this.urineNoticeDateEntity = urineNoticeDateEntity;
    }

    public Integer getUrineTestEntity() {
        return urineTestEntity;
    }

    public void setUrineTestEntity(Integer urineTestEntity) {
        this.urineTestEntity = urineTestEntity;
    }

    public Integer getJoinActivityEntity() {
        return joinActivityEntity;
    }

    public void setJoinActivityEntity(Integer joinActivityEntity) {
        this.joinActivityEntity = joinActivityEntity;
    }

    public Integer getHelpConditionEntity() {
        return helpConditionEntity;
    }

    public void setHelpConditionEntity(Integer helpConditionEntity) {
        this.helpConditionEntity = helpConditionEntity;
    }

    public Integer getConversationEntity() {
        return conversationEntity;
    }

    public void setConversationEntity(Integer conversationEntity) {
        this.conversationEntity = conversationEntity;
    }

    public Integer getRegularReportEntity() {
        return regularReportEntity;
    }

    public void setRegularReportEntity(Integer regularReportEntity) {
        this.regularReportEntity = regularReportEntity;
    }

    public Integer getReportAppraisalEntity() {
        return reportAppraisalEntity;
    }

    public void setReportAppraisalEntity(Integer reportAppraisalEntity) {
        this.reportAppraisalEntity = reportAppraisalEntity;
    }

    public Integer getChangeLocationEntity() {
        return changeLocationEntity;
    }

    public void setChangeLocationEntity(Integer changeLocationEntity) {
        this.changeLocationEntity = changeLocationEntity;
    }

    public Integer getLeaveAuditEntity() {
        return leaveAuditEntity;
    }

    public void setLeaveAuditEntity(Integer leaveAuditEntity) {
        this.leaveAuditEntity = leaveAuditEntity;
    }

    public Integer getDiscontinueDrugsEntity() {
        return discontinueDrugsEntity;
    }

    public void setDiscontinueDrugsEntity(Integer discontinueDrugsEntity) {
        this.discontinueDrugsEntity = discontinueDrugsEntity;
    }

    public Integer getContinueDrugsEntity() {
        return continueDrugsEntity;
    }

    public void setContinueDrugsEntity(Integer continueDrugsEntity) {
        this.continueDrugsEntity = continueDrugsEntity;
    }

    public Integer getStopDrugsEntity() {
        return stopDrugsEntity;
    }

    public void setStopDrugsEntity(Integer stopDrugsEntity) {
        this.stopDrugsEntity = stopDrugsEntity;
    }

    public Integer getReleaseEntity() {
        return releaseEntity;
    }

    public void setReleaseEntity(Integer releaseEntity) {
        this.releaseEntity = releaseEntity;
    }

    public Integer getReleaseNoticeEntity() {
        return releaseNoticeEntity;
    }

    public void setReleaseNoticeEntity(Integer releaseNoticeEntity) {
        this.releaseNoticeEntity = releaseNoticeEntity;
    }

    public String getDocStatus() {
        return docStatus;
    }

    public void setDocStatus(String docStatus) {
        this.docStatus = docStatus;
    }

    public String getArchivePerson() {
        return archivePerson;
    }

    public void setArchivePerson(String archivePerson) {
        this.archivePerson = archivePerson;
    }

    public Date getArchiveDate() {
        return archiveDate;
    }

    public void setArchiveDate(Date archiveDate) {
        this.archiveDate = archiveDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDealStatus() {
        return dealStatus;
    }

    public void setDealStatus(int dealStatus) {
        this.dealStatus = dealStatus;
    }
}
