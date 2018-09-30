package cn.com.jinke.wh_drugcontrol.input.manager;

import org.xutils.x;

import cn.com.jinke.wh_drugcontrol.booter.ProjectParams;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.Persion;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.persion.model.Document;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.MsgKey;
import cn.com.jinke.wh_drugcontrol.utils.UrlSetting;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;

/**
 * 尿检Manager
 * Created by jinke on 2017/9/2.
 */

public class UrineManager extends UrlSetting implements CodeConstants, MsgKey {

    private static UrineManager instance;

    private UrineManager() {
    }

    public static UrineManager getInstance() {
        if (instance == null) {
            synchronized (UrineManager.class) {
                if (instance == null) {
                    instance = new UrineManager();
                }
            }
        }
        return instance;
    }

    public void uploadData(Persion persion, Document document, String docId, String num, String address,
                           String person, String result, String imgPath, String id) {
        final UserCard userCard = MasterManager.getInstance().getUserCard();

        ProjectParams params = new ProjectParams(SAVE_URINE_TEST)
                .with(REALNAME, persion.getRealName())
                .with(URINECOUNTS, num)
                .with(PERSONID, persion.getId())
                .with(USER_ID, userCard.getUserId())
                .with(DOCID, docId)
                .with(PHOTOURL, imgPath)
                .with(URINEPLACE, address)
                .with(TESTCONTACTPERSION, person)
                .with(URINERESULT, result)
                .with(TYPE, document.getType())
                .with(IDCARD, persion.getIdentityCard())
                .with(ID, id);

        x.http().post(params.build(), new CallbackWrapper<Void>(ADD_URINE_MSG, 2) {
            @Override
            public void onSuccess(int state, String msg, Void object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, msg);
            }
        });
    }

}
