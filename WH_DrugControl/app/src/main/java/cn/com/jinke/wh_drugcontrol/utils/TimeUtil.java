package cn.com.jinke.wh_drugcontrol.utils;

import android.text.TextUtils;

/** 时间工具
 * Created by xkr on 2017/12/27.
 */

public class TimeUtil {

    /**
     * 去除时分秒 的显示
     * @param time
     * @return
     */
    public static String removeHour_Min_Seconds(String time){
        if(TextUtils.isEmpty(time)){
            return "无";
        }
        String[] times = time.split(" ");
        return times[0];
    }
}
