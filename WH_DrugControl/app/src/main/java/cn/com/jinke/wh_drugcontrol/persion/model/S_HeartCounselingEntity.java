package cn.com.jinke.wh_drugcontrol.persion.model;

/**
 * Created by xkr on 2017/11/2.
 */

public class S_HeartCounselingEntity {
    private String helperName;// 求助者姓名
    private String sex;// 性别
    private String consultDate;// 咨询日期
    private String counts;// 咨询次数
    private String consulter;// 咨询师
    private String lastReault;// 上一次咨询结果
    private String consulterOrg;// 咨询师所属机构
    private String scoreZiType;// 得分类型
    private Integer score;// 得分
    private String content;// 内容
    private String impression;// 印象
    private String question;// 问题
    private String deal;// 处理
    private String personNum;// 人员编号
    private String personId;// 人员Id
    private String id;
    private String createTime;// 添加时间

    public String getHelperName() {
        return helperName;
    }

    public void setHelperName(String helperName) {
        this.helperName = helperName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getConsultDate() {
        return consultDate;
    }

    public void setConsultDate(String consultDate) {
        this.consultDate = consultDate;
    }

    public String getCounts() {
        return counts;
    }

    public void setCounts(String counts) {
        this.counts = counts;
    }

    public String getConsulter() {
        return consulter;
    }

    public void setConsulter(String consulter) {
        this.consulter = consulter;
    }

    public String getLastReault() {
        return lastReault;
    }

    public void setLastReault(String lastReault) {
        this.lastReault = lastReault;
    }

    public String getConsulterOrg() {
        return consulterOrg;
    }

    public void setConsulterOrg(String consulterOrg) {
        this.consulterOrg = consulterOrg;
    }

    public String getScoreZiType() {
        return scoreZiType;
    }

    public void setScoreZiType(String scoreZiType) {
        this.scoreZiType = scoreZiType;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImpression() {
        return impression;
    }

    public void setImpression(String impression) {
        this.impression = impression;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDeal() {
        return deal;
    }

    public void setDeal(String deal) {
        this.deal = deal;
    }

    public String getPersonNum() {
        return personNum;
    }

    public void setPersonNum(String personNum) {
        this.personNum = personNum;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
