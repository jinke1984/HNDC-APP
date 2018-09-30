package cn.com.jinke.wh_drugcontrol.me.model;

import java.io.Serializable;

/**
 * @author jinke
 * @date 2017/10/31
 * @description
 */

public class Area implements Serializable {

    private String id;

    /**
     * 机构名称
     */
    private String orgName;

    /**
     * 上级机构编码
     */
    private String orgParentId;

    /**
     * 上级机构名称
     */
    private String orgParentName;

    /**
     * 机构级别
     */
    private String orgLevel;

    /**
     * 机构编码
     */
    private String orgCode;

    /**
     * 机构编号
     */
    private String bitCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgParentId() {
        return orgParentId;
    }

    public void setOrgParentId(String orgParentId) {
        this.orgParentId = orgParentId;
    }

    public String getOrgParentName() {
        return orgParentName;
    }

    public void setOrgParentName(String orgParentName) {
        this.orgParentName = orgParentName;
    }

    public String getOrgLevel() {
        return orgLevel;
    }

    public void setOrgLevel(String orgLevel) {
        this.orgLevel = orgLevel;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getBitCode() {
        return bitCode;
    }

    public void setBitCode(String bitCode) {
        this.bitCode = bitCode;
    }
}
