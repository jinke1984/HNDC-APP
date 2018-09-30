package cn.com.jinke.wh_drugcontrol.persion.manager;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import cn.com.jinke.wh_drugcontrol.persion.model.E_EmploymentPlacementEntity;
import cn.com.jinke.wh_drugcontrol.persion.model.E_EmploymentRecommendationEntity;
import cn.com.jinke.wh_drugcontrol.persion.model.IntergratedQueryResult;
import cn.com.jinke.wh_drugcontrol.persion.model.MedicationEntity;
import cn.com.jinke.wh_drugcontrol.persion.model.Result_AcceptDrugsEntity;
import cn.com.jinke.wh_drugcontrol.persion.model.Result_CommunityDrugDetoxificationOrRecovery;
import cn.com.jinke.wh_drugcontrol.persion.model.Result_DetoxificationSuperviseEntity;
import cn.com.jinke.wh_drugcontrol.persion.model.Result_EmploymentEntity;
import cn.com.jinke.wh_drugcontrol.persion.model.Result_PublicSecurityEntity;
import cn.com.jinke.wh_drugcontrol.persion.model.Result_SpecialWardEntity;
import cn.com.jinke.wh_drugcontrol.persion.model.S_AidAndRescueEntity;
import cn.com.jinke.wh_drugcontrol.persion.model.S_EmploymentIntentionEntity;
import cn.com.jinke.wh_drugcontrol.persion.model.S_HeartCounselingEntity;
import cn.com.jinke.wh_drugcontrol.persion.model.S_PhysicalExaminationEntity;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.MsgKey;
import cn.com.jinke.wh_drugcontrol.utils.UrlSetting;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CommonRequestParams;

/**
 * @author jinke
 * @date 2018/2/5
 * @description
 */

public class QueryDataManager extends UrlSetting implements CodeConstants, MsgKey {

    private static QueryDataManager instance;

    private QueryDataManager() {

    }

    public static QueryDataManager getInstance() {
        if (instance == null) {
            synchronized (PersionManager.class) {
                if (instance == null) {
                    instance = new QueryDataManager();
                }
            }
        }
        return instance;
    }

    public void getMedicationData(String aIdCard, int pageNo, int pageSize) {
        CommonRequestParams.Builder builder = new CommonRequestParams.Builder(PERSION_MEDICATION_SITUATION);
        builder.putParams("idCard", aIdCard);
        builder.putParams("pageNo", pageNo);
        builder.putParams("pageSize", pageSize);
        x.http().post(builder.build(), new CallbackWrapper<List<MedicationEntity>>(MSG_PERSION_MEDICATION_SITUATION, 2) {
            @Override
            public void onSuccess(int state, String msg, List<MedicationEntity> object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, object);
            }
        });
    }

    public void getAcceptDrugsData(String aIdCard) {
        CommonRequestParams.Builder builder = new CommonRequestParams.Builder(PERSION_ACCEPT_DRUG_MAINTENANCE_THERAPY);
        builder.putParams("idCard", aIdCard);
        x.http().post(builder.build(), new CallbackWrapper<Result_AcceptDrugsEntity>(MSG_PERSION_ACCEPT_DRUGS_CURE, 2) {
            @Override
            public void onSuccess(int state, String msg, Result_AcceptDrugsEntity object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, object);
            }
        });
    }

    public void getCommunityDrugData(String aIdCard) {
        CommonRequestParams.Builder builder = new CommonRequestParams.Builder(PERSION_COMMUNITY_RECOVER_INFO);
        builder.putParams("idCard", aIdCard);
        x.http().post(builder.build(), new CallbackWrapper<Result_CommunityDrugDetoxificationOrRecovery>(MSG_PERSION_RECOVERY_INFO, 2) {
            @Override
            public void onSuccess(int state, String msg, Result_CommunityDrugDetoxificationOrRecovery object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, object);
            }
        });
    }

    public void getDetoxificationSupervise(String aIdCard) {
        CommonRequestParams.Builder builder = new CommonRequestParams.Builder(PERSION_DETOXIFICATION_SUPERVISION);
        builder.putParams("idCard", aIdCard);
        x.http().post(builder.build(), new CallbackWrapper<List<Result_DetoxificationSuperviseEntity>>(MSG_PERSION_DETOXIFICATION_SUPERVISION, 2) {
            @Override
            public void onSuccess(int state, String msg, List<Result_DetoxificationSuperviseEntity> object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, object);
            }
        });
    }

    public void getResultEmploymentData(String aIdCard, int pageNo, int pageSize) {
        CommonRequestParams.Builder builder = new CommonRequestParams.Builder(PERSION_EMPLOYMENT_RECOMMEND);
        builder.putParams("idCard", aIdCard);
        builder.putParams("pageNo", pageNo);
        builder.putParams("pageSize", pageSize);
        x.http().post(builder.build(), new CallbackWrapper<List<Result_EmploymentEntity>>(MSG_PERSION_SUNSHINE, 2) {
            @Override
            public void onSuccess(int state, String msg, List<Result_EmploymentEntity> object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, object);
            }
        });
    }

    public void getResultPublicSecurityData(String aIdCard) {
        CommonRequestParams.Builder builder = new CommonRequestParams.Builder(PERSION_SEIZED_INFORMATION);
        builder.putParams("idCard", aIdCard);
        x.http().post(builder.build() , new CallbackWrapper<Result_PublicSecurityEntity>(MSG_PERSION_RECOVERY_INFO, 2) {
            @Override
            public void onSuccess(int state, String msg, Result_PublicSecurityEntity object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, object);
            }
        });
    }

    public void getResultSpecialWardData(String aIdCard) {
        CommonRequestParams.Builder builder = new CommonRequestParams.Builder(PERSION_SPECIAL_WARD);
        builder.putParams("idCard", aIdCard);
        x.http().post(builder.build(), new CallbackWrapper<List<Result_SpecialWardEntity>>(MSG_PERSION_SPECIAL_WARD, 2) {
            @Override
            public void onSuccess(int state, String msg, List<Result_SpecialWardEntity> object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, object);
            }
        });
    }

    public void getEmploymentPlacementData(String aIdCard, int pageNo, int pageSize) {
        CommonRequestParams.Builder builder = new CommonRequestParams.Builder(PERSION_EMPLOYMENT_PLACEMENT);
        builder.putParams("idCard", aIdCard);
        builder.putParams("pageNo", pageNo);
        builder.putParams("pageSize", pageSize);
        x.http().post(builder.build(), new CallbackWrapper<List<E_EmploymentPlacementEntity>>(MSG_PERSION_EMPLOYMENT_PLACEMENT, 2) {
            @Override
            public void onSuccess(int state, String msg, List<E_EmploymentPlacementEntity> object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, object);
            }
        });
    }

    public void getEmploymentRecommendationData(String aIdCard, int pageNo, int pageSize) {
        CommonRequestParams.Builder builder = new CommonRequestParams.Builder(PERSION_EMPLOYMENT_RECOMMENT);
        builder.putParams("idCard", aIdCard);
        builder.putParams("pageNo", pageNo);
        builder.putParams("pageSize", pageSize);
        x.http().post(builder.build(), new CallbackWrapper<List<E_EmploymentRecommendationEntity>>(MSG_PERSION_EMPLOYMENT_RECOMMENT, 2) {
            @Override
            public void onSuccess(int state, String msg, List<E_EmploymentRecommendationEntity> object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, object);
            }

        });
    }

    public void getIntergratedQueryResult(String aIdCard) {
        CommonRequestParams.Builder builder = new CommonRequestParams.Builder(PERSION_INTERGRATED_QUERY);
        builder.putParams("idCard", aIdCard);
        x.http().post(builder.build(), new CallbackWrapper<IntergratedQueryResult>(MSG_PERSION_INTERGRATED_QUERY, 2) {
            @Override
            public void onSuccess(int state, String msg, IntergratedQueryResult object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, object);
            }

        });
    }

    public void getAidAndRescueData(String aIdCard, int pageNo, int pageSize){
        CommonRequestParams.Builder builder = new CommonRequestParams.Builder(PERSION_AID_AND_RESCURE);
        builder.putParams("idCard",aIdCard);
        builder.putParams("pageNo",pageNo);
        builder.putParams("pageSize",pageSize);
        x.http().post(builder.build(),new CallbackWrapper<List<S_AidAndRescueEntity>>(MSG_PERSION_AID_AND_RESCUE,2){
            @Override
            public void onSuccess(int state, String msg, List<S_AidAndRescueEntity> object, int total) {
                MessageProxy.sendMessage(mMsgCode,state,object);
            }
        });
    }

    public void getEmploymentIntentionData(String aIdCard, int pageNo, int pageSize){
        CommonRequestParams.Builder builder = new CommonRequestParams.Builder(PERSION_EMPLOYMENT_INTENTION);
        builder.putParams("idCard",aIdCard);
        builder.putParams("pageNo",pageNo);
        builder.putParams("pageSize",pageSize);
        x.http().post(builder.build(),new CallbackWrapper<List<S_EmploymentIntentionEntity>>(MSG_PERSION_EMPLOYMENT_INTENTION,2){
            @Override
            public void onSuccess(int state, String msg, List<S_EmploymentIntentionEntity> object, int total) {
                MessageProxy.sendMessage(mMsgCode,state,object);
            }
        });
    }

    public void getHeartCounselingData(String aIdCard, int pageNo, int pageSize){
        CommonRequestParams.Builder builder = new CommonRequestParams.Builder(PERSION_HEART_COUNSELING);
        builder.putParams("idCard",aIdCard);
        builder.putParams("pageNo",pageNo);
        builder.putParams("pageSize",pageSize);
        x.http().post(builder.build(),new CallbackWrapper<List<S_HeartCounselingEntity>>(MSG_PERSION_HEART_COUNSELING,2){
            @Override
            public void onSuccess(int state, String msg, List<S_HeartCounselingEntity> object, int total) {
                MessageProxy.sendMessage(mMsgCode,state,object);
            }
        });
    }

    public void getPhysicalExaminationData(String aIdCard, int pageNo, int pageSize){

        CommonRequestParams.Builder builder = new CommonRequestParams.Builder(PERSION_PHYSICAL_EXAMINATION);
        builder.putParams("idCard",aIdCard);
        builder.putParams("pageNo",pageNo);
        builder.putParams("pageSize",pageSize);
        x.http().post(builder.build(),new CallbackWrapper<List<S_PhysicalExaminationEntity>>(MSG_PERSION_PHYSICAL_EXAMINATION,2){
            @Override
            public void onSuccess(int state, String msg, List<S_PhysicalExaminationEntity> object, int total) {
                MessageProxy.sendMessage(mMsgCode,state,object);
            }
        });

    }

}
