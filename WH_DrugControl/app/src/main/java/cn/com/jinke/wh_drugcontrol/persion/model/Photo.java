package cn.com.jinke.wh_drugcontrol.persion.model;

import java.io.Serializable;

/**
 * @author jinke
 * @date 2017/11/5
 * @description
 */

public class Photo implements Serializable {

    private int type;

    private String url;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 注意：这里主要是用于区分定期访谈和定期报告附件的类型，其他的就用默认的就可以。
     */
    public static final class Type{
        public final static int TYPE_DEFAULT = 0;
        public final static int TYPE_FACE = 1;
        public final static int TYPE_LOCATION = 2;
        public final static int TYPE_ADJUNCT = 3;
    }
}
