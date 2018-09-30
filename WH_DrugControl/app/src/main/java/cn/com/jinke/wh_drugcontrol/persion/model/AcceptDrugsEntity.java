package cn.com.jinke.wh_drugcontrol.persion.model;

import java.io.Serializable;

/**
 * Created by xkr on 2017/10/31.
 */

public class AcceptDrugsEntity implements Serializable {
    private String therapyname;
    private String input_date;
    private String first_dose_date;
    private String last_dose_date;
    private String clinic_date;
    private String clinic;
    private String exit_date;
    private String exit_reason;
    private String leader;
    private String leaderphone;
    private String methadone_id;
    private String name;
    private String id_card;
    private String clinic_no;//门诊编号

    public String getTherapyname() {
        return therapyname;
    }

    public void setTherapyname(String therapyname) {
        this.therapyname = therapyname;
    }

    public String getInput_date() {
        return input_date;
    }

    public void setInput_date(String input_date) {
        this.input_date = input_date;
    }

    public String getFirst_dose_date() {
        return first_dose_date;
    }

    public void setFirst_dose_date(String first_dose_date) {
        this.first_dose_date = first_dose_date;
    }

    public String getLast_dose_date() {
        return last_dose_date;
    }

    public void setLast_dose_date(String last_dose_date) {
        this.last_dose_date = last_dose_date;
    }

    public String getClinic_date() {
        return clinic_date;
    }

    public void setClinic_date(String clinic_date) {
        this.clinic_date = clinic_date;
    }

    public String getClinic() {
        return clinic;
    }

    public void setClinic(String clinic) {
        this.clinic = clinic;
    }

    public String getExit_date() {
        return exit_date;
    }

    public void setExit_date(String exit_date) {
        this.exit_date = exit_date;
    }

    public String getExit_reason() {
        return exit_reason;
    }

    public void setExit_reason(String exit_reason) {
        this.exit_reason = exit_reason;
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

    public String getMethadone_id() {
        return methadone_id;
    }

    public void setMethadone_id(String methadone_id) {
        this.methadone_id = methadone_id;
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

    public String getClinic_no() {
        return clinic_no;
    }

    public void setClinic_no(String clinic_no) {
        this.clinic_no = clinic_no;
    }
}
