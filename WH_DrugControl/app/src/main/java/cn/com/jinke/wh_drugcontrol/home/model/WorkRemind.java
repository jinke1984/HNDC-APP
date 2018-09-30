package cn.com.jinke.wh_drugcontrol.home.model;

import java.io.Serializable;

/**
 * Created by jinke on 2017/9/12.
 */

public class WorkRemind implements Serializable {

    /**
     *  定期谈话应完成条数
     */
    private int talkShoudFinish;

    /**
     *  定期谈话已完成条数
     */
    private int talkAlreadyFinish;

    /**
     *  定期谈话未完成条数
     */
    private int talkNoFinish;

    /**
     *  定期尿检应完成条数
     */
    private int urinalysisShouldFinish;

    /**
     *  定期尿检已完成条数
     */
    private int urinalysisAlreadyFinish;

    /**
     *  定期尿检未完成条数
     */
    private int urinalysisNoFinish;

    /**
     *  定期报告应完成条数
     */
    private int reportShouldFinish;

    /**
     *  定期报告已完成条数
     */
    private int reportAlreadyFinish;

    /**
     *  定期报告未完成条数
     */
    private int reportNoFinish;

    /**
     *  定期评估应完成条数
     */
    private int appraiseShouldFinish;

    /**
     *  定期评估已完成条数
     */
    private int appraiseAlreadyFinish;

    /**
     *  定期评估未完成条数
     */
    private int appraiseNoFinish;

    /**
     *  动态信息应完成条数
     */
    private int dynamiShouldFinish;

    /**
     *  动态信息已完成条数
     */
    private int dynamiAlreadyFinish;

    /**
     *  动态信息未完成条数
     */
    private int dynamiNoFinish;

    /**
     *  吸毒人员id
     */
    private String personId;

    /**
     *  组织机构名
     */
    private String orgName;

    /**
     *  用户id
     */
    private String userId;

    public int getTalkShoudFinish() {
        return talkShoudFinish;
    }

    public void setTalkShoudFinish(int talkShoudFinish) {
        this.talkShoudFinish = talkShoudFinish;
    }

    public int getTalkAlreadyFinish() {
        return talkAlreadyFinish;
    }

    public void setTalkAlreadyFinish(int talkAlreadyFinish) {
        this.talkAlreadyFinish = talkAlreadyFinish;
    }

    public int getTalkNoFinish() {
        return talkNoFinish;
    }

    public void setTalkNoFinish(int talkNoFinish) {
        this.talkNoFinish = talkNoFinish;
    }

    public int getUrinalysisShouldFinish() {
        return urinalysisShouldFinish;
    }

    public void setUrinalysisShouldFinish(int urinalysisShouldFinish) {
        this.urinalysisShouldFinish = urinalysisShouldFinish;
    }

    public int getUrinalysisAlreadyFinish() {
        return urinalysisAlreadyFinish;
    }

    public void setUrinalysisAlreadyFinish(int urinalysisAlreadyFinish) {
        this.urinalysisAlreadyFinish = urinalysisAlreadyFinish;
    }

    public int getUrinalysisNoFinish() {
        return urinalysisNoFinish;
    }

    public void setUrinalysisNoFinish(int urinalysisNoFinish) {
        this.urinalysisNoFinish = urinalysisNoFinish;
    }

    public int getReportShouldFinish() {
        return reportShouldFinish;
    }

    public void setReportShouldFinish(int reportShouldFinish) {
        this.reportShouldFinish = reportShouldFinish;
    }

    public int getReportAlreadyFinish() {
        return reportAlreadyFinish;
    }

    public void setReportAlreadyFinish(int reportAlreadyFinish) {
        this.reportAlreadyFinish = reportAlreadyFinish;
    }

    public int getReportNoFinish() {
        return reportNoFinish;
    }

    public void setReportNoFinish(int reportNoFinish) {
        this.reportNoFinish = reportNoFinish;
    }

    public int getAppraiseShouldFinish() {
        return appraiseShouldFinish;
    }

    public void setAppraiseShouldFinish(int appraiseShouldFinish) {
        this.appraiseShouldFinish = appraiseShouldFinish;
    }

    public int getAppraiseAlreadyFinish() {
        return appraiseAlreadyFinish;
    }

    public void setAppraiseAlreadyFinish(int appraiseAlreadyFinish) {
        this.appraiseAlreadyFinish = appraiseAlreadyFinish;
    }

    public int getAppraiseNoFinish() {
        return appraiseNoFinish;
    }

    public void setAppraiseNoFinish(int appraiseNoFinish) {
        this.appraiseNoFinish = appraiseNoFinish;
    }

    public int getDynamiShouldFinish() {
        return dynamiShouldFinish;
    }

    public void setDynamiShouldFinish(int dynamiShouldFinish) {
        this.dynamiShouldFinish = dynamiShouldFinish;
    }

    public int getDynamiAlreadyFinish() {
        return dynamiAlreadyFinish;
    }

    public void setDynamiAlreadyFinish(int dynamiAlreadyFinish) {
        this.dynamiAlreadyFinish = dynamiAlreadyFinish;
    }

    public int getDynamiNoFinish() {
        return dynamiNoFinish;
    }

    public void setDynamiNoFinish(int dynamiNoFinish) {
        this.dynamiNoFinish = dynamiNoFinish;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
