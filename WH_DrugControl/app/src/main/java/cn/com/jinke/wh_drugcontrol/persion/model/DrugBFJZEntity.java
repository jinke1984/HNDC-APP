package cn.com.jinke.wh_drugcontrol.persion.model;

import java.io.Serializable;

/**
 * Created by xkr on 2017/10/31.
 */

public class DrugBFJZEntity implements Serializable {

    private String service_provider;  //救助的组织或个人
    private String accept_content;   //	希望获取的帮助
    private String low_premium;    //低保金
    private String is_low_premium; //是否低保
    private String accept_situation;   //救助情况
    private String accept_effect;   //救助的效果
    private String service_date; //救助日期

    public String getService_provider() {
        return service_provider;
    }

    public void setService_provider(String service_provider) {
        this.service_provider = service_provider;
    }

    public String getAccept_content() {
        return accept_content;
    }

    public void setAccept_content(String accept_content) {
        this.accept_content = accept_content;
    }

    public String getLow_premium() {
        return low_premium;
    }

    public void setLow_premium(String low_premium) {
        this.low_premium = low_premium;
    }

    public String getIs_low_premium() {
        return is_low_premium;
    }

    public void setIs_low_premium(String is_low_premium) {
        this.is_low_premium = is_low_premium;
    }

    public String getAccept_situation() {
        return accept_situation;
    }

    public void setAccept_situation(String accept_situation) {
        this.accept_situation = accept_situation;
    }

    public String getAccept_effect() {
        return accept_effect;
    }

    public void setAccept_effect(String accept_effect) {
        this.accept_effect = accept_effect;
    }

    public String getService_date() {
        return service_date;
    }

    public void setService_date(String service_date) {
        this.service_date = service_date;
    }
}
