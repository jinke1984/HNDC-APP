package cn.com.jinke.wh_drugcontrol.persion.manager;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.persion.model.Document;
import cn.com.jinke.wh_drugcontrol.persion.model.WorkPersion;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.MsgKey;
import cn.com.jinke.wh_drugcontrol.utils.UrlSetting;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CommonRequestParams;

/**
 * Created by jinke on 2017/8/24.
 */

public class PersionManager extends UrlSetting implements CodeConstants, MsgKey {

    private static PersionManager instance;
    private final List<WorkPersion> mWorkPersionList = new ArrayList<>();

    /**
     * 档案实体
     */
    private Document mDocument = new Document();

    private final static int PAGE_SIZE = 10;

    /**
     * 上次刷新时间
     */
    public static long sLastRefreshTime;
    private static int sPageStart;
    private static int sTotalCount;
    private static int mPage = 0;

    private PersionManager() {

    }

    public static PersionManager getInstance(){
        if (instance == null) {
            synchronized (PersionManager.class) {
                if (instance == null) {
                    instance = new PersionManager();
                }
            }
        }
        return instance;
    }

    public void getDocId(String aPersionId){

        CommonRequestParams.Builder builder = new CommonRequestParams.Builder(DOCID_URL);
        builder.putParams(IDNO, aPersionId);
        x.http().post(builder.build(), new CallbackWrapper<Document>(DOC_ID_MSG, 2){

            @Override
            public void onSuccess(int state, String msg, Document object, int total) {
                setDocument(object);
                MessageProxy.sendMessage(mMsgCode, state);
            }
        });
    }

    public Document getDocument() {
        return mDocument;
    }

    private void setDocument(Document mDocument) {
        this.mDocument = mDocument;
    }

    public boolean isFinish() {
        boolean result;
        if (sTotalCount == 0) {
            return true;
        }
        else {
            int pageCount = sTotalCount/PAGE_SIZE;
            if(pageCount >= mPage){
                result = false;
            }else{
                result = true;
            }
        }
        return  result;
    }

    public void updateDJPhoto(String id, String path){
        CommonRequestParams.Builder builder = new CommonRequestParams.Builder(ADD_DJPHOTO_URL);
        builder.putParams(PERSONID, id);
        builder.putParams(PHOTO, path);
        x.http().post(builder.build(), new CallbackWrapper<Void>(UPDATE_DJ_PHOTO, 2){

            @Override
            public void onSuccess(int state, String msg, Void object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, msg);
            }
        });
    }

    public void getWorkPersionData(boolean isRefresh, String title){

        if (isRefresh) {
            sPageStart = 0;
            mPage = 0;
            mWorkPersionList.clear();
            sLastRefreshTime = System.currentTimeMillis();
        }

        UserCard userCard = MasterManager.getInstance().getUserCard();
        CommonRequestParams.Builder builder = new CommonRequestParams.Builder(WORK_URL);
        builder.putParams(USER_ID, userCard.getUserId());
        builder.putParams(TYPE, title);
        builder.putParams(PAGENO, isRefresh ? 0 : sPageStart);
        builder.putParams(PAGESIZE, PAGE_SIZE);
        x.http().post(builder.build(), new CallbackWrapper<List<WorkPersion>>(WORK_REMIND_MSG, 2){

            @Override
            public void onSuccess(int state, String msg, List<WorkPersion> object, int total) {

                if(state == SUCCESS){
                    if(object != null){
                        mWorkPersionList.addAll(object);
                    }

                    if (sTotalCount >= 0) {
                        mPage = mPage + 1;
                        sTotalCount = total;
                        int pageCount = sTotalCount/PAGE_SIZE;
                        if(pageCount >= mPage){
                            sPageStart = sPageStart + PAGE_SIZE;
                        }
                    }
                }
                MessageProxy.sendMessage(mMsgCode, state, mWorkPersionList);
            }
        });
    }

    public List<WorkPersion> getWorkPersionList() {
        return mWorkPersionList;
    }

    public void clear(){
        if(mWorkPersionList.size() > 0){
            mWorkPersionList.clear();
        }
    }

    public interface onCollectNotiyUILister{

        /**
         * 收藏吸毒人员接口回调
         * @param state
         * @param msg
         * @param type
         */
        void onCollectNotiyUI(int state, String msg, int type);
    }

    /**
     * 收藏吸毒人员
     * @param idCard  吸毒人员身份证号码
     * @param type 类型 1收藏 2取消收藏
     */
    public void collection(String idCard, final int type, final onCollectNotiyUILister collectNotiyUILister){
        UserCard userCard = MasterManager.getInstance().getUserCard();
        CommonRequestParams.Builder builder = new CommonRequestParams.Builder(PERSION_COLLECTION_URL);
        builder.putParams(IDNO, idCard);
        builder.putParams(CONTROLID, userCard.getUserId());
        builder.putParams(TYPE, type);
        x.http().post(builder.build(), new CallbackWrapper<Void>(COLLECTION_MSG, 2){

            @Override
            public void onSuccess(int state, String msg, Void object, int total) {
                collectNotiyUILister.onCollectNotiyUI(state, msg, type);
            }
        });
    }

}
