package cn.com.jinke.wh_drugcontrol.me.model;

import java.io.Serializable;

/**
 * @author jinke
 * @date 2017/11/3
 * @description
 */

public class Map implements Serializable {

    /**
     * 地图截图的路径
     */
    private String photo;

    /**
     * 定位的详细地址
     */
    private String location;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
