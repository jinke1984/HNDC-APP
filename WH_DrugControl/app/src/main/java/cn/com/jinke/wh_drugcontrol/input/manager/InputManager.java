package cn.com.jinke.wh_drugcontrol.input.manager;

import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.com.jinke.wh_drugcontrol.booter.ProjectParams;
import cn.com.jinke.wh_drugcontrol.input.model.HousePhoto;
import cn.com.jinke.wh_drugcontrol.input.model.Urine;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.Persion;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.persion.model.Document;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.MsgKey;
import cn.com.jinke.wh_drugcontrol.utils.UrlSetting;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CommonRequestParams;
import cn.qqtheme.framework.entity.Province;

/**
 * Created by jinke on 2017/7/27.
 */

public class InputManager extends UrlSetting implements CodeConstants, MsgKey {

    private static InputManager instance;
    private final String mID = "05ce3d2543974a1f9b73dbc2c6bd8a6a";
    private ArrayList<Province> mProvince = new ArrayList<>();

    private final List<Urine> mUrineList = new ArrayList<>();

    private final static int PAGE_SIZE = 10;
    public static long sLastRefreshTime; // 上次刷新时间
    private static int sPageStart;
    private static int sTotalCount;
    private static int mPage = 0;

    private InputManager() {

    }

    public static InputManager getInstance() {
        if (instance == null) {
            synchronized (InputManager.class) {
                if (instance == null) {
                    instance = new InputManager();
                }
            }
        }
        return instance;
    }

    public ArrayList<Province> getProvince() {
        return mProvince;
    }

    public void setProvince(ArrayList<Province> mProvince) {
        this.mProvince = mProvince;
    }

    public void getAreaList() {
        RequestParams params = new RequestParams(AREA_URL);
        params.addParameter(AREAID, mID);
        x.http().post(params, new CallbackWrapper<List<Province>>(AREA_LIST, 2) {

            @Override
            public void onSuccess(int state, String msg, List<Province> object, int total) {
                if (state == SUCCESS && object != null) {
                    setProvince((ArrayList<Province>) object);
                    MessageProxy.sendMessage(mMsgCode, state, object);
                }
            }
        });
    }

    /**
     * 保存谈话记录
     *
     * @param personId
     * @param docId
     * @param photoUrl
     * @param talker
     * @param talkObject
     * @param talkCounts
     * @param talkPlace
     * @param identityCard
     * @param faceToFace
     * @param type
     */
    public void saveTalk(String realName, String personId, String docId, String photoUrl, String talker,
                         String talkObject, String talkCounts, String talkPlace, String identityCard, int faceToFace, String type, String id) {

        UserCard userCard = MasterManager.getInstance().getUserCard();
        CommonRequestParams.Builder builder = new CommonRequestParams.Builder(SAVE_CONVERSATION);
        builder.putParams(REALNAME, realName);
        builder.putParams(PERSONID, personId);
        builder.putParams(USER_ID, userCard.getUserId());
        builder.putParams(DOCID, docId);
        builder.putParams(PHOTOURL, photoUrl);
        builder.putParams(TALKER, talker);
        builder.putParams(TALKOBJECT, talkObject);
        builder.putParams(TALKCOUNTS, talkCounts);
        builder.putParams(TALKPLACE, talkPlace);
        builder.putParams(IDCARD, identityCard);
        builder.putParams(FACETOFACE, faceToFace);
        builder.putParams(TYPE, type);
        builder.putParams(ID, id);
        x.http().post(builder.build(), new CallbackWrapper<Void>(ADD_CONVERSATION_MSG, 2) {
            @Override
            public void onSuccess(int state, String msg, Void object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, msg);
            }
        });
    }

    /**
     * @param realName
     * @param personId
     * @param docId
     * @param photoUrl
     * @param recordPerson
     * @param reportCounts
     * @param reportPlache
     * @param identityCard
     * @param faceToFace
     * @param type
     */
    public void saveReport(String realName, String personId, String docId, String photoUrl, String recordPerson,
                           String reportCounts, String reportPlache, String identityCard, int faceToFace, String type, String id) {

        UserCard userCard = MasterManager.getInstance().getUserCard();
        CommonRequestParams.Builder builder = new CommonRequestParams.Builder(SAVE_REPORT);
        builder.putParams(REALNAME, realName);
        builder.putParams(PERSONID, personId);
        builder.putParams(USER_ID, userCard.getUserId());
        builder.putParams(DOCID, docId);
        builder.putParams(PHOTOURL, photoUrl);
        builder.putParams(RECORDPERSION, recordPerson);
        builder.putParams(RECORDCOUNTS, reportCounts);
        builder.putParams(RECORDPLACE, reportPlache);
        builder.putParams(IDCARD, identityCard);
        builder.putParams(FACETOFACE, faceToFace);
        builder.putParams(TYPE, type);
        builder.putParams(ID, id);
        x.http().post(builder.build(), new CallbackWrapper<Void>(ADD_REPORT_MSG, 2) {
            @Override
            public void onSuccess(int state, String msg, Void object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, msg);
            }
        });
    }

    /**
     * 保存居住位置图
     *
     * @param document  吸毒人员档案
     * @param persion   吸毒人员信息
     * @param photoType 1位置图 2房屋图
     * @param photoUrl  多张图片,拼接方式 图片1,图片2,图片3
     */
    public void saveHousePhoto(Document document, Persion persion, int photoType, String photoUrl) {

        UserCard userCard = MasterManager.getInstance().getUserCard();

        ProjectParams params = new ProjectParams(HOUSE_PHOTO_SAVE_URL)
                .with(USER_ID, userCard.getUserId())
                .with(MAKEPERSION, userCard.getInfoName())
                .with(PERSONID, persion.getId())
                .with(REALNAME, persion.getRealName())
                .with(IDCARD, persion.getIdentityCard())
                .with(TYPE, persion.getType())
                .with(DOCID, document.getId())
                .with(PHOTOTYPE, photoType)
                .with(PHOTOURL, photoUrl);
        x.http().post(params.build(), new CallbackWrapper<Void>(SAVE_HOUSE_PHOTO, 2) {
            @Override
            public void onSuccess(int state, String msg, Void object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, null);
            }
        });
    }

    public void getHousePhoto(String idCard) {
        ProjectParams params = new ProjectParams(HOUSE_PHOTO_URL).with(IDNO, idCard);
        x.http().post(params.build(), new CallbackWrapper<HousePhoto>(HOUSE_PHOTO_MSG, 2) {

            @Override
            public void onSuccess(int state, String msg, HousePhoto object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, object);
            }
        });
    }

    public void getUrineList(String drug_id, boolean isRefresh) {

        if (isRefresh) {
            sPageStart = 0;
            mPage = 0;
            mUrineList.clear();
            sLastRefreshTime = System.currentTimeMillis();
        }

        ProjectParams params = new ProjectParams(URINE_URL)
                .with(DRUGID, drug_id)
                .with(PAGESIZE, PAGE_SIZE)
                .with(PAGENO, isRefresh ? 0 : sPageStart);
        
        x.http().post(params.build(), new CallbackWrapper<List<Urine>>(URINE_MSG, 2) {
            @Override
            public void onSuccess(int state, String msg, List<Urine> object, int total) {
                if (state == SUCCESS) {

                    if (object != null) {
                        mUrineList.addAll(object);
                    }
                    if (sTotalCount >= 0) {
                        mPage = mPage + 1;
                        sTotalCount = total;
                        int pageCount = sTotalCount / PAGE_SIZE;
                        if (pageCount >= mPage) {
                            sPageStart = sPageStart + PAGE_SIZE;
                        }
                    }

                }
                MessageProxy.sendMessage(mMsgCode, state, mUrineList);
            }
        });

    }

    public static boolean isFinish() {
        boolean result;
        if (sTotalCount == 0) {
            return true;
        } else {
            int pageCount = sTotalCount / PAGE_SIZE;
            result = pageCount < mPage;
        }
        return result;
    }


    /**
     * 二维码验证
     * @param drugIdCard  身份证号
     * @param qrCodeType  二维码类型
     * @param time        当前时间
     * @param scanType    扫描码类型
     * @param userId      用户id
     */
    public void QRCode(String drugIdCard, String qrCodeType, String time, String scanType, String userId){
        CommonRequestParams.Builder builder = new CommonRequestParams.Builder(QRCODEURL);
        builder.putParams("idCard",drugIdCard);
        builder.putParams("qrCodeType",qrCodeType);
        builder.putParams("currentTime",time);
        builder.putParams("scanType",scanType);
        builder.putParams("userId",userId);
        x.http().post(builder.build(),new CallbackWrapper<Void>(QRCODEMSG, 2){
            @Override
            public void onSuccess(int state, String msg, Void object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, msg);
            }
        });
    }
}
