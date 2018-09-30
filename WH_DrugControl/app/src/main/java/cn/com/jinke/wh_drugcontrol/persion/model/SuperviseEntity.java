package cn.com.jinke.wh_drugcontrol.persion.model;

import java.io.Serializable;

/**
 * Created by xkr on 2017/10/31.
 */

public class SuperviseEntity implements Serializable{
    private String crcname;
    private String inther_date;
    private String inthereason;
    private String inthecrimire;
    private String intheunite;
    private String clc_date_end;
    private String clcreason;
    private String clc_yesno;
    private String clc_units;
    private String clc_person;
    private String leader;
    private String leaderphone;
    private String name;
    private String id_card;

    public String getCrcname() {
        return crcname;
    }

    public void setCrcname(String crcname) {
        this.crcname = crcname;
    }

    public String getInther_date() {
        return inther_date;
    }

    public void setInther_date(String inther_date) {
        this.inther_date = inther_date;
    }

    public String getInthereason() {
        return inthereason;
    }

    public void setInthereason(String inthereason) {
        this.inthereason = inthereason;
    }

    public String getInthecrimire() {
        return inthecrimire;
    }

    public void setInthecrimire(String inthecrimire) {
        this.inthecrimire = inthecrimire;
    }

    public String getIntheunite() {
        return intheunite;
    }

    public void setIntheunite(String intheunite) {
        this.intheunite = intheunite;
    }

    public String getClc_date_end() {
        return clc_date_end;
    }

    public void setClc_date_end(String clc_date_end) {
        this.clc_date_end = clc_date_end;
    }

    public String getClcreason() {
        return clcreason;
    }

    public void setClcreason(String clcreason) {
        this.clcreason = clcreason;
    }

    public String getClc_yesno() {
        return clc_yesno;
    }

    public void setClc_yesno(String clc_yesno) {
        this.clc_yesno = clc_yesno;
    }

    public String getClc_units() {
        return clc_units;
    }

    public void setClc_units(String clc_units) {
        this.clc_units = clc_units;
    }

    public String getClc_person() {
        return clc_person;
    }

    public void setClc_person(String clc_person) {
        this.clc_person = clc_person;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getLeaderphone() {
        return leaderphone;
    }

    public void setLeaderphone(String leaderphone) {
        this.leaderphone = leaderphone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }
}
