package cn.com.jinke.wh_drugcontrol.persion.model;

import java.io.Serializable;

/**
 * Created by xkr on 2017/10/31.
 */

public class HospitalInfoEntity implements Serializable {
    private String place_name;
    private String register_date;
    private String register_cause;
    private String register_cause_other;
    private String register_pathway;
    private String signout_date_fact;
    private String signout_reason;
    private String signout_join;
    private String signout_to_other;
    private String leader;
    private String leaderphone;
    private String name;
    private String id_card;

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public String getRegister_date() {
        return register_date;
    }

    public void setRegister_date(String register_date) {
        this.register_date = register_date;
    }

    public String getRegister_cause() {
        return register_cause;
    }

    public void setRegister_cause(String register_cause) {
        this.register_cause = register_cause;
    }

    public String getRegister_cause_other() {
        return register_cause_other;
    }

    public void setRegister_cause_other(String register_cause_other) {
        this.register_cause_other = register_cause_other;
    }

    public String getRegister_pathway() {
        return register_pathway;
    }

    public void setRegister_pathway(String register_pathway) {
        this.register_pathway = register_pathway;
    }

    public String getSignout_date_fact() {
        return signout_date_fact;
    }

    public void setSignout_date_fact(String signout_date_fact) {
        this.signout_date_fact = signout_date_fact;
    }

    public String getSignout_reason() {
        return signout_reason;
    }

    public void setSignout_reason(String signout_reason) {
        this.signout_reason = signout_reason;
    }

    public String getSignout_join() {
        return signout_join;
    }

    public void setSignout_join(String signout_join) {
        this.signout_join = signout_join;
    }

    public String getSignout_to_other() {
        return signout_to_other;
    }

    public void setSignout_to_other(String signout_to_other) {
        this.signout_to_other = signout_to_other;
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
