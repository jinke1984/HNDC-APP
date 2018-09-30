package cn.com.jinke.wh_drugcontrol.persion.model;

import java.io.Serializable;

/**
 * Created by xkr on 2017/10/31.
 */

public class SeizedInfoEntity implements Serializable {

    private String crcname;
    private String seized_date;
    private String current_drug;
    private String urine_res;
    private String is_addiction;
    private String addiction_deg;
    private String seized_area_name;
    private String seized_field;
    private String is_crime;
    private String charge_type;
    private String han_date;
    private String han_res_type;
    private String seized_where_type;
    private String sup_place_type;

    private String sup_place_cus;
    private String sup_place_det;
    private String sup_place_gua;
    private String sup_place_other;

    private String spec_name;
    private String goto_area_name;
    private String goto_other_area_name;
    private String name;
    private String id_card;

    public String getCrcname() {
        return crcname;
    }

    public void setCrcname(String crcname) {
        this.crcname = crcname;
    }

    public String getSeized_date() {
        return seized_date;
    }

    public void setSeized_date(String seized_date) {
        this.seized_date = seized_date;
    }

    public String getCurrent_drug() {
        return current_drug;
    }

    public void setCurrent_drug(String current_drug) {
        this.current_drug = current_drug;
    }

    public String getUrine_res() {
        return urine_res;
    }

    public void setUrine_res(String urine_res) {
        this.urine_res = urine_res;
    }

    public String getIs_addiction() {
        return is_addiction;
    }

    public void setIs_addiction(String is_addiction) {
        this.is_addiction = is_addiction;
    }

    public String getAddiction_deg() {
        return addiction_deg;
    }

    public void setAddiction_deg(String addiction_deg) {
        this.addiction_deg = addiction_deg;
    }

    public String getSeized_area_name() {
        return seized_area_name;
    }

    public void setSeized_area_name(String seized_area_name) {
        this.seized_area_name = seized_area_name;
    }

    public String getSeized_field() {
        return seized_field;
    }

    public void setSeized_field(String seized_field) {
        this.seized_field = seized_field;
    }

    public String getIs_crime() {
        return is_crime;
    }

    public void setIs_crime(String is_crime) {
        this.is_crime = is_crime;
    }

    public String getCharge_type() {
        return charge_type;
    }

    public void setCharge_type(String charge_type) {
        this.charge_type = charge_type;
    }

    public String getHan_date() {
        return han_date;
    }

    public void setHan_date(String han_date) {
        this.han_date = han_date;
    }

    public String getHan_res_type() {
        return han_res_type;
    }

    public void setHan_res_type(String han_res_type) {
        this.han_res_type = han_res_type;
    }

    public String getSeized_where_type() {
        return seized_where_type;
    }

    public void setSeized_where_type(String seized_where_type) {
        this.seized_where_type = seized_where_type;
    }

    public String getSup_place_type() {
        return sup_place_type;
    }

    public void setSup_place_type(String sup_place_type) {
        this.sup_place_type = sup_place_type;
    }

    public String getSup_place_cus() {
        return sup_place_cus;
    }

    public void setSup_place_cus(String sup_place_cus) {
        this.sup_place_cus = sup_place_cus;
    }

    public String getSup_place_det() {
        return sup_place_det;
    }

    public void setSup_place_det(String sup_place_det) {
        this.sup_place_det = sup_place_det;
    }

    public String getSup_place_gua() {
        return sup_place_gua;
    }

    public void setSup_place_gua(String sup_place_gua) {
        this.sup_place_gua = sup_place_gua;
    }

    public String getSup_place_other() {
        return sup_place_other;
    }

    public void setSup_place_other(String sup_place_other) {
        this.sup_place_other = sup_place_other;
    }

    public String getSpec_name() {
        return spec_name;
    }

    public void setSpec_name(String spec_name) {
        this.spec_name = spec_name;
    }

    public String getGoto_area_name() {
        return goto_area_name;
    }

    public void setGoto_area_name(String goto_area_name) {
        this.goto_area_name = goto_area_name;
    }

    public String getGoto_other_area_name() {
        return goto_other_area_name;
    }

    public void setGoto_other_area_name(String goto_other_area_name) {
        this.goto_other_area_name = goto_other_area_name;
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
