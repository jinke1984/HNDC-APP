package cn.com.jinke.wh_drugcontrol.input.model;

import java.io.Serializable;

/**
 * Created by jinke on 2017/7/28.
 */

public class Nation implements Serializable {

    private String id;
    private String text;
    private String sc;


    public Nation(String id, String text) {
        this(id, text, id + "-" + text);
    }

    public Nation(String id, String text, String sc) {
        this.id = id;
        this.text = text;
        this.sc = sc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSc() {
        return sc;
    }

    public void setSc(String sc) {
        this.sc = sc;
    }
}
