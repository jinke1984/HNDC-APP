package cn.com.jinke.wh_drugcontrol.manager;

import android.text.TextUtils;

import org.xutils.http.RequestParams;
import org.xutils.x;

import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils.ESHARE;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils.ETYPE;
import cn.com.jinke.wh_drugcontrol.utils.AppLogger;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.UrlSetting;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CallbackWrapper;
import cn.com.jinke.wh_drugcontrol.webinterface.core.CommonRequestParams;

/**
 * @author: lufengwen
 * @Date: 2016-05-07 15:01
 * @Description:
 */
public class CommonManager extends UrlSetting implements CodeConstants{

	private static CommonManager instance;

	private String sPushId = null;

	private CommonManager() {

	}

	public static CommonManager getInstance() {
		if (instance == null) {
			synchronized (CommonManager.class) {
				if (instance == null) {
					instance = new CommonManager();
				}
			}
		}
		return instance;
	}

	public String getPushId() {
		return sPushId;
	}

	public void setPushId(String sPushId) {
		this.sPushId = sPushId;
	}

	/**
	 * 0吸毒人员 1专干 2工作人员
	 */
	public void uploadPushId() {
		String sPushId = getPushId();
		UserCard userCard = MasterManager.getInstance().getUserCard();
		if(sPushId == null && TextUtils.isEmpty(sPushId)){
			sPushId = ShareUtils.getInstance().get(ESHARE.SYS, PUSH_ID, ETYPE.STRING);
		}
		AppLogger.e("用户类型" + userCard.getUserType());
		if (!TextUtils.isEmpty(sPushId) && userCard != null) {
			AppLogger.e("上传极光推送ID：" + sPushId);
			CommonRequestParams.Builder builder = new CommonRequestParams.Builder(UPLOAD_REGID_URL);
			builder.putParams(USER_ID, userCard.getUserId());
			builder.putParams(SENDNO, sPushId);
			builder.putParams(TYPE, userCard.getUserType());
			x.http().post(builder.build(), new CallbackWrapper<Void>() {
				@Override
				public void onSuccess(int state, String msg, Void object, int total) {
					AppLogger.e("极光推送ID上传" + (state == CodeConstants.SUCCESS ? "成功" : "失败"));
				}
			});
		}
		else {
			AppLogger.e("极光推送ID为空");
		}
	}

	public String updataUrl(){
		return UPDATEURL;
	}

	public String baseUrl(){
		return BASE_URL;
	}
}
