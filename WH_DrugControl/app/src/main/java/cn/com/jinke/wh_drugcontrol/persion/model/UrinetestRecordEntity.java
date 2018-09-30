package cn.com.jinke.wh_drugcontrol.persion.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/10/31.
 */

public class UrinetestRecordEntity implements Serializable {
    private String numb;
    private String type;
    private String result;     //结果
    private String date;
    private String examinant;  //检测人
    private String address;
    private String creatTime;
    private String urine_file_count;//附件数量
    private String urine_type_name;
    List<UrineFileEntity> urine_file_list;

    public String getNumb() {
        return numb;
    }

    public void setNumb(String numb) {
        this.numb = numb;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExaminant() {
        return examinant;
    }

    public void setExaminant(String examinant) {
        this.examinant = examinant;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatTime() {
        return creatTime;
    }

    public void setCreatTime(String creatTime) {
        this.creatTime = creatTime;
    }

    public String getUrine_file_count() {
        return urine_file_count;
    }

    public void setUrine_file_count(String urine_file_count) {
        this.urine_file_count = urine_file_count;
    }

    public String getUrine_type_name() {
        return urine_type_name;
    }

    public void setUrine_type_name(String urine_type_name) {
        this.urine_type_name = urine_type_name;
    }

    public List<UrineFileEntity> getUrine_file_list() {
        return urine_file_list;
    }

    public void setUrine_file_list(List<UrineFileEntity> urine_file_list) {
        this.urine_file_list = urine_file_list;
    }
}
