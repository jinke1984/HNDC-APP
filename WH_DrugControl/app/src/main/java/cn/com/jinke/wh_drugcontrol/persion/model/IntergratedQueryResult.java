package cn.com.jinke.wh_drugcontrol.persion.model;

/**
 * Created by xkr on 2017/11/1.
 * 综合查询结果
 */

public class IntergratedQueryResult {
    Result_CommunityDrugDetoxificationOrRecovery drugRehabInfo;
    Result_SocialRehabilitationServiceForDrugAddicts stationIn;
    Result_EmploymentEntity settlement;
    Result_AcceptDrugsEntity medicineTreatmentInfo;

    public Result_AcceptDrugsEntity getMedicineTreatmentInfo() {
        return medicineTreatmentInfo;
    }

    public void setMedicineTreatmentInfo(Result_AcceptDrugsEntity medicineTreatmentInfo) {
        this.medicineTreatmentInfo = medicineTreatmentInfo;
    }

    public Result_CommunityDrugDetoxificationOrRecovery getDrugRehabInfo() {
        return drugRehabInfo;
    }

    public void setDrugRehabInfo(Result_CommunityDrugDetoxificationOrRecovery drugRehabInfo) {
        this.drugRehabInfo = drugRehabInfo;
    }

    public Result_SocialRehabilitationServiceForDrugAddicts getStationIn() {
        return stationIn;
    }

    public void setStationIn(Result_SocialRehabilitationServiceForDrugAddicts stationIn) {
        this.stationIn = stationIn;
    }

    public Result_EmploymentEntity getSettlement() {
        return settlement;
    }

    public void setSettlement(Result_EmploymentEntity settlement) {
        this.settlement = settlement;
    }
}
