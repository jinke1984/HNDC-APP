package cn.com.jinke.wh_drugcontrol.utils;

import cn.com.jinke.wh_drugcontrol.share.ShareUtils;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils.ETYPE;
import cn.com.jinke.wh_drugcontrol.share.ShareUtils.ESHARE;

/**
 * @author jinke
 * @date 2018/2/1
 * @description
 */

public class RequestHelper implements CodeConstants{

    private static RequestHelper instance;

    //------------  海南项目地址开始 ------------------//

//    /**
//     * 内网服务器的地址(正式)
//     */

    public String IN_BASE_URL = "http://59.212.148.11:80/web_api/";
    public String IN_IMAGE_URL = "http://59.212.148.6:8080";
    public String IN_CHAT_URL = "http://36.101.208.228:80";

    /**
     * 外网服务器的地址(正式)
     */
    public String OUT_BASE_URL = "http://36.101.208.228:80/web_api/";
    public String OUT_IMAGE_URL = "http://36.101.208.228:80";
    public String OUT_CHAT_URL = "http://36.101.208.228:80/web_api/";

    /**
     * 学习园地的跳转地址
     */
    public String STUDY_URL = "http://36.101.208.228:80/";

//    public String IN_BASE_URL = "http://36.101.208.228:80/web_api/";
//    public String IN_IMAGE_URL = "http://36.101.208.228:80";
//    public String IN_CHAT_URL = "http://36.101.208.228:80/web_api/";

    //------------------- 海南测试地址开始 -------------------------//

//    public String IN_BASE_URL = "http://36.101.208.228:8090/web_api/";
//    public String IN_IMAGE_URL = "http://36.101.208.228:8090";
//    public String IN_CHAT_URL = "http://36.101.208.228:8090/web_api/";
//
    /**
     * 学习园地的跳转地址
     */
//    public String STUDY_URL = "http://36.101.208.228:8090/";
//
//    public String OUT_BASE_URL = "http://36.101.208.228:8090/web_api/";
//    public String OUT_IMAGE_URL = "http://36.101.208.228:8090";
//    public String OUT_CHAT_URL = "http://36.101.208.228:8090/web_api/";
//
//    public String IN_BASE_URL = "http://59.212.148.13:80/web_api/";
//    public String IN_IMAGE_URL = "http://59.212.148.13:8081";
//    public String IN_CHAT_URL = "http://36.101.208.228:8090/web_api/";

    //------------------- 海南测试地址结束 -------------------------//


//    public String IN_BASE_URL = "http://1.1.1.240:8087/web_api/";
//    public String IN_IMAGE_URL = "http://1.1.1.240:8082";
//    public String IN_CHAT_URL = "http://1.1.1.240:8087/web_api/";
//
//    public String OUT_BASE_URL = "http://1.1.1.240:8087/web_api/";
//    public String OUT_IMAGE_URL = "http://1.1.1.240:8087";
//    public String OUT_CHAT_URL = "http://1.1.1.240:8087/web_api/";

    //------------- 海南项目地址开始结束 ---------------//

    //------------- 张鑫地址开始 ---------------//

//    public String IN_BASE_URL = "http://172.16.0.127:8081/web_api/";
//    public String IN_IMAGE_URL = "http://36.101.208.228:8090";
//    public String IN_CHAT_URL = "http://172.16.0.127:8081/web_api/";
//
//    public String OUT_BASE_URL = "http://172.16.0.127:8081/web_api/";
//    public String OUT_IMAGE_URL = "http://172.16.0.127:8081";
//    public String OUT_CHAT_URL = "http://172.16.0.127:8081/web_api/";
//
//    /**
//     * 学习园地的跳转地址
//     */
//    public String STUDY_URL = "http://172.16.0.127:8081/";

    //------------- 张鑫地址结束 ---------------//

    private RequestHelper() {

    }

    public static RequestHelper getInstance() {
        if (instance == null) {
            synchronized (RequestHelper.class) {
                if (instance == null) {
                    instance = new RequestHelper();
                }
            }
        }
        return instance;
    }

    /**
     * 切换数据请求头
     * true 代表在内网，false代表在外网
     * @return
     */
    public String getRequestHeader(){
        String header = "";
        boolean state = ShareUtils.getInstance().get(ESHARE.SYS, SWITCH_NET, ETYPE.BOOL);
        if(state){
            header = IN_BASE_URL;
        }else{
            header = OUT_BASE_URL;
        }
        return header;
    }

    /**
     * 切换资源请求头
     * true 代表在内网，false代表在外网
     * @return
     */
    public String getImageRequestHeader(){
        String imageheader = "";
        boolean state = ShareUtils.getInstance().get(ESHARE.SYS, SWITCH_NET, ETYPE.BOOL);
        if(state){
            imageheader = IN_IMAGE_URL;
        }else{
            imageheader = OUT_IMAGE_URL;
        }
        return imageheader;
    }



}
