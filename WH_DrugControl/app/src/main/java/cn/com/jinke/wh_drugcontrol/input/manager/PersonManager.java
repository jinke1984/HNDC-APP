package cn.com.jinke.wh_drugcontrol.input.manager;

import org.xutils.x;

import cn.com.jinke.wh_drugcontrol.booter.ProjectParams;
import cn.com.jinke.wh_drugcontrol.input.model.CachePerson;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.MsgKey;
import cn.com.jinke.wh_drugcontrol.utils.UrlSetting;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;

/**
 * Created by jinke on 2017/9/2.
 */

public class PersonManager extends UrlSetting implements CodeConstants, MsgKey {

    private static PersonManager instance;

    private PersonManager() {
    }

    public static PersonManager getInstance() {
        if (instance == null) {
            synchronized (PersonManager.class) {
                if (instance == null) {
                    instance = new PersonManager();
                }
            }
        }
        return instance;
    }

    public void findPersonByIdcard(String idCard) {
        final UserCard userCard = MasterManager.getInstance().getUserCard();
        ProjectParams params = new ProjectParams(FINDPERSONBYIDCARD)
                .with(USER_ID, userCard.getUserId())
                .with("idcard", idCard);

        x.http().post(params.build(), new CallbackWrapper<CachePerson>(LOAD_PERSONBYIDCARD, 2) {
            @Override
            public void onSuccess(int state, String msg, CachePerson object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, object);
            }
        });
    }

    public void uploadPersonPart1(CachePerson person) {
        ProjectParams params = new ProjectParams(SAVEPERSONBASE1)
                .with(USER_ID, person.getUserId())
                .with(IDENTITYCARD, person.getIdentityCard())
                .with(REALNAME, person.getRealName())
                .with("preRealName", person.getPreRealName())
                .with("nickname", person.getNickname())
                .with("sex", person.getSex())
                .with("birthdate", person.getBirthdate())
                .with("communityID", person.getCommunityID())
                .with("communityCode", person.getCommunityCode())
                .with("communityName", person.getCommunityName())
                .with("nation", person.getNation())
                .with("height", person.getHeight())
                .with("livingWithParents", person.getLivingWithParents())
                .with("marriage", person.getMarriage())
                .with("healthCondition", person.getHealthCondition())
                .with("healthConditionDetail", person.getHealthConditionDetail())
                .with("familyCondition", person.getFamilyCondition())
                .with(FAMILYCONDITIONDETAIL, person.getFamilyConditionDetail());

        x.http().post(params.build(), new CallbackWrapper<CachePerson>(ADD_PERSON_PART1, 2) {
            @Override
            public void onSuccess(int state, String msg, CachePerson object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, object);
            }
        });
    }

    public void uploadPersonPart2(CachePerson person) {
        ProjectParams params = new ProjectParams(SAVEPERSONBASE2)
                .with(USER_ID, person.getUserId())
                .with(PERSONID, person.getId())
                .with("householdAddressCode", person.getHouseholdAddressCode())
                .with("householdAddressName", person.getHouseholdAddressName())
                .with("householdAddress", person.getHouseholdAddress())
                .with("currentAddressCode", person.getCurrentAddressCode())
                .with("currentAddressName", person.getCurrentAddressName())
                .with("currentAddress", person.getCurrentAddress())
                .with("cellphone", person.getCellphone())
                .with("telePhone", person.getTelePhone())
                .with("qq", person.getQq())
                .with("weixin", person.getWeixin())
                .with("mail", person.getMail());

        x.http().post(params.build(), new CallbackWrapper<Void>(ADD_PERSON_PART2, 2) {
            @Override
            public void onSuccess(int state, String msg, Void object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, msg);
            }
        });
    }

    public void uploadPersonPart3(CachePerson person) {
        ProjectParams params = new ProjectParams(SAVEPERSONBASE3)
                .with(USER_ID, person.getUserId())
                .with(PERSONID, person.getId())
                .with("politicalLandscape", person.getPoliticalLandscape())
                .with("politicalLandscapeDetail", person.getPoliticalLandscapeDetail())
                .with("education", person.getEducation())
                .with("vocationalTraining", person.getVocationalTraining())
                .with("skills", person.getSkills())
                .with("skillsDetail", person.getSkillsDetail())
                .with("employmentCondition", person.getEmploymentCondition())
                .with("income", person.getIncome())
                .with("socialSecurity", person.getSocialSecurity())
                .with("socialSecurityDetail", person.getSocialSecurityDetail())
                .with("personalResume", person.getPersonalResume());

        x.http().post(params.build(), new CallbackWrapper<Void>(ADD_PERSON_PART3, 2) {
            @Override
            public void onSuccess(int state, String msg, Void object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, msg);
            }
        });
    }

    public void uploadPersonPart4(CachePerson person) {
        ProjectParams params = new ProjectParams(SAVEPERSONBASE4)
                .with(USER_ID, person.getUserId())
                .with(PERSONID, person.getId())
                .with("firstDrugsDate", person.getFirstDrugsDate())
                .with("drugTypes", person.getDrugTypes())
                .with("drugTypesDetail", person.getDrugTypesDetail())
                .with("crimeCondition", person.getCrimeCondition());

        x.http().post(params.build(), new CallbackWrapper<Void>(ADD_PERSON_PART4, 2) {
            @Override
            public void onSuccess(int state, String msg, Void object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, msg);
            }
        });
    }

    public void uploadPersonPart5(CachePerson person) {
        ProjectParams params = new ProjectParams(SAVEPERSONBASE5)
                .with(USER_ID, person.getUserId())
                .with(PERSONID, person.getId())
                .with("gridPerson", person.getGridPerson())
                .with("communityPolice", person.getGridPerson())
                .with("photoAddress", person.getPhotoAddress());

        x.http().post(params.build(), new CallbackWrapper<Void>(ADD_PERSON_PART5, 2) {
            @Override
            public void onSuccess(int state, String msg, Void object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, msg);
            }
        });
    }

}
