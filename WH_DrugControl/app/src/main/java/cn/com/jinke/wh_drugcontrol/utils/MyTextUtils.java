package cn.com.jinke.wh_drugcontrol.utils;

import android.text.TextUtils;

/**
 * MyTextUtils
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/8/24
 */

public class MyTextUtils {
    private MyTextUtils() {
    }

    public static boolean isEmpty(CharSequence str) {
        return TextUtils.isEmpty(str);
    }

    public static boolean isEmptyOfNull(CharSequence str) {
        if (TextUtils.isEmpty(str)) {
            return true;
        }

        final String trimStr = str.toString().trim();

        return TextUtils.isEmpty(trimStr) || "null".equals(trimStr);
    }

    public static boolean isAllEmpty(CharSequence... sequence) {
        if (sequence == null) {
            return true;
        }
        for (CharSequence cs : sequence) {
            if (!TextUtils.isEmpty(cs)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isAllNotEmpty(CharSequence... sequence) {
        if (sequence == null) {
            return false;
        }
        for (CharSequence cs : sequence) {
            if (TextUtils.isEmpty(cs)) {
                return false;
            }
        }
        return true;
    }

}
