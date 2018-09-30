package cn.com.jinke.wh_drugcontrol.utils;

import cn.com.jinke.wh_drugcontrol.share.ShareUtils;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils.ESHARE;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils.ETYPE;

/**
 * @author jinke
 * @date 2018/1/24
 * @description  客户端内外网切换的帮助类(聊天，远程视频，访谈，报告，尿检，位置图是需要在外网工作的)
 */

public class SwitchNetUtils implements CodeConstants{

    private static SwitchNetUtils instance;

    /**
     * 标识当前是在内网还是外网的标识，这个每次切换需要持久化
     * true 代表在内网，false代表在外网
     */
    private boolean isWithin = true;


    private SwitchNetUtils() {

    }

    public static SwitchNetUtils getInstance() {
        if (instance == null) {
            synchronized (SwitchNetUtils.class) {
                if (instance == null) {
                    instance = new SwitchNetUtils();
                }
            }
        }
        return instance;
    }

    public boolean switchNet(){
        boolean state = ShareUtils.getInstance().get(ESHARE.SYS, SWITCH_NET, ETYPE.BOOL);
        return state;
    }
}
