package cn.com.jinke.wh_drugcontrol.word.manager;

import org.xutils.x;

import cn.com.jinke.wh_drugcontrol.booter.ProjectParams;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.MessageProxy;
import cn.com.jinke.wh_drugcontrol.utils.MsgKey;
import cn.com.jinke.wh_drugcontrol.utils.UrlSetting;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;

/**
 * DeleteDocManager
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/11/22
 */

public class DeleteDocManager extends UrlSetting implements CodeConstants, MsgKey {

    private static DeleteDocManager instance;

    private DeleteDocManager() {
    }

    public static DeleteDocManager getInstance() {
        if (instance == null) {
            synchronized (DeleteDocManager.class) {
                if (instance == null) {
                    instance = new DeleteDocManager();
                }
            }
        }
        return instance;
    }

    /**
     * 删除档案
     *
     * @param docType  档案类型
     * @param sourceId 资源id
     */
    public void deleteDocument(final int docType, final String sourceId) {
        final UserCard userCard = MasterManager.getInstance().getUserCard();
        ProjectParams params = new ProjectParams(DELETE_DOC)
                .with(USER_ID, userCard.getUserId())
                .with(DOCTYPE, docType)
                .with(SOURCEID, sourceId);

        x.http().post(params.build(), new CallbackWrapper<Void>(REQ_DELETE_DOC, 2) {
            @Override
            public void onSuccess(int state, String msg, Void object, int total) {
                MessageProxy.sendMessage(mMsgCode, state, msg);
            }
        });
    }
}
