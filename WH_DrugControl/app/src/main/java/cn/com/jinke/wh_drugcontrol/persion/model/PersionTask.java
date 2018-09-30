package cn.com.jinke.wh_drugcontrol.persion.model;

import java.io.Serializable;

/**
 * Created by jinke on 2017/8/3.
 */

public class PersionTask implements Serializable {

    private int imgurl;
    private String title;

    public PersionTask(int imgurl, String title) {
        this.imgurl = imgurl;
        this.title = title;
    }

    public int getImgurl() {
        return imgurl;
    }

    public void setImgurl(int imgurl) {
        this.imgurl = imgurl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
