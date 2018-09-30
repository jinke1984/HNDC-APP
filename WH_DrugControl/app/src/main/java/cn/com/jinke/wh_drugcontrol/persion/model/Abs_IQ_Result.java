package cn.com.jinke.wh_drugcontrol.persion.model;

import java.io.Serializable;

/**
 * Created by xkr on 2017/11/1.
 * 抽象综合查询 结果
 */

public abstract class Abs_IQ_Result implements Serializable{
    private String item_title_name;

    public String getItem_title_name() {
        return item_title_name;
    }

    public void setItem_title_name(String item_title_name) {
        this.item_title_name = item_title_name;
    }
}
