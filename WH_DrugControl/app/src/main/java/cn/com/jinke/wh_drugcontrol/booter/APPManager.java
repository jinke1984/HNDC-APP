package cn.com.jinke.wh_drugcontrol.booter;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.mapapi.SDKInitializer;
import com.netease.nim.avchatkit.AVChatKit;

import org.xutils.x;

import cn.com.jinke.wh_drugcontrol.BuildConfig;
import cn.com.jinke.wh_drugcontrol.database.DbManager;
import cn.com.jinke.wh_drugcontrol.manager.PackageHelper;
import cn.com.jinke.wh_drugcontrol.manager.VersionManager;
import cn.com.jinke.wh_drugcontrol.me.manager.MasterManager;
import cn.com.jinke.wh_drugcontrol.me.model.UserCard;
import cn.com.jinke.wh_drugcontrol.message.manager.MessageManager;
import cn.com.jinke.wh_drugcontrol.thread.Dispatcher;
import cn.com.jinke.wh_drugcontrol.utils.CodeConstants;
import cn.com.jinke.wh_drugcontrol.utils.CommUtils;
import cn.com.jinke.wh_drugcontrol.utils.ShareConstant;
import cn.jpush.android.api.JPushInterface;

/**
 * Created by jinke on 2017/5/2.
 */

public class APPManager implements ShareConstant, CodeConstants{

    /**
     * 需要异步事件的初始化
     */
    public static void initAsync() {

        Dispatcher.runOnSingleThread(new Runnable() {

            @Override
            public void run() {

            }
        });
    }

    /**
     * 同步事件的初始化
     */
    public static void initSync() {

        // 设置开启日志,发布时请关闭日志
        JPushInterface.setDebugMode(BuildConfig.DEBUG);
        // 初始化 JPush
        JPushInterface.init(ProjectApplication.getContext());

        SDKInitializer.initialize(ProjectApplication.getContext());

        VersionManager.setVersionCode(PackageHelper.getVersionCode(ProjectApplication.getContext()));
        VersionManager.setVersionName(PackageHelper.getVersionName(ProjectApplication.getContext()));

        if (BuildConfig.DEBUG) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }

        // 尽可能早，推荐在Application中初始化
        ARouter.init((Application) ProjectApplication.getContext());

        x.Ext.init((Application) ProjectApplication.getContext());

        // 是否输出debug日志
        x.Ext.setDebug(BuildConfig.DEBUG);

        DbManager.initDatabase();
        MasterManager.getInstance().init();

        //确保登录才初始化消息数据到内存
        UserCard userCard = MasterManager.getInstance().getUserCard();
        if (userCard != null) {
            MessageManager.init();
        }

        //初始化一些基础数据
        CommUtils.getInstance().assetsNationToList();
        CommUtils.getInstance().assetsStzkToList();
        CommUtils.getInstance().assetsHyzkToList();

        AVChatKit.setContext(ProjectApplication.getContext());
    }
}
