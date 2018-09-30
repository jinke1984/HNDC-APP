package cn.com.jinke.wh_drugcontrol.intercept;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;

import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.RouteUtils;

/**
 * Created by jinke on 2017/7/17.
 * 全局的拦截器
 */

@Interceptor(priority = 100)
public class DCInterceptor implements IInterceptor, CodeConstants {

    private Context mContext;

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {

        if (RouteUtils.R_LOGIN_UI.equals(postcard.getPath())) {
            //TODO 是否登录的拦截器(如果已经登录就调用onInterrupt方法拦截跳转到首页)
            UserCard userCard = MasterManager.getInstance().getUserCard();

            boolean reLogin = ShareUtils.getInstance().get(ShareUtils.ESHARE.SYS, SP_KEY_RELOGIN, ShareUtils.ETYPE.BOOL);
            boolean setPwd = postcard.getExtras().getBoolean(EXTRA_SET_GESTURE_PASSWORD, false);

            if (reLogin || setPwd || userCard == null) {
                callback.onContinue(postcard);
            } else {
                callback.onInterrupt(null);
            }
        } else {
            callback.onContinue(postcard);
        }

    }

    @Override
    public void init(Context context) {
        mContext = context;
    }
}
