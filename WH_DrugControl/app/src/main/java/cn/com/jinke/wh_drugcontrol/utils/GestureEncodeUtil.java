package cn.com.jinke.wh_drugcontrol.utils;

/**
 * GestureEncodeUtil
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/11/8
 */

public final class GestureEncodeUtil {
    private GestureEncodeUtil() {
    }

    /**
     * 用MD5加密
     *
     * @param password 原始密码
     * @return 加密后的密码
     */
    public static String encodeWithMd5(String password) {
        String encode = encode(password);
        return MD5Utils.getMD5(encode);
    }

    /**
     * 加密
     *
     * @param password 原始密码
     * @return 加密后的密码
     */
    public static String encode(String password) {
        char[] chars = password.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char ch : chars) {
            sb.append(ch).append("*");
        }
        return sb.toString();
    }

    /**
     * 解密
     *
     * @param password 加密的密码
     * @return 解密后的密码
     */
    public static String decode(String password) {
        return password.replace("*", "");
    }

}
