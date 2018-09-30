package cn.com.jinke.wh_drugcontrol.me.model;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

import java.io.Serializable;

/**
 * Created by jinke on 2017/7/20.
 * 用户实体类
 */

@Table(name = "t_user")
public class UserCard implements Serializable {

    /**
     * 用户编号
     */
    @Column(name = "id", isId = true, autoGen = false)
    private String userId;

    /**
     * 用户类型
     */
    @Column(name = "userType")
    private String userType;

    /**
     * 用户中文名字
     */
    @Column(name = "infoName")
    private String infoName;

    /**
     * 用户移动电话
     */
    @Column(name = "infoMobile")
    private String infoMobile;

    /**
     * 区域编号
     */
    @Column(name = "orgId")
    private String orgId;

    /**
     * 区域名字
     */
    @Column(name = "orgName")
    private String orgName;

    /**
     * 上级区域名称
     */
    @Column(name = "orgParentName")
    private String orgParentName;

    /**
     * 区域级别
     */
    @Column(name = "orgLevel")
    private String orgLevel;

    /**
     * 角色名字
     */
    @Column(name = "roleName")
    private String roleName;

    /**
     * 用户头像
     */
    @Column(name = "userPhoto")
    private String userPhoto;

    /**
     * 是否启用, 0 停用 1 启用
     */
    @Column(name = "userEnabled")
    private String userEnabled;

    /**
     * 是否锁定 0 锁定 1未锁定
     */
    @Column(name = "userLocked")
    private String userLocked;

    /**
     * 用户有效期
     */
    @Column(name = "userExpiryDate")
    private String userExpiryDate;

    /**
     * true能管理或读取子机构数据
     */
    @Column(name = "isTransmit")
    private Boolean isTransmit;

    /**
     * 机构编号
     */
    @Column(name = "bitCode")
    private String bitCode;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getInfoName() {
        return infoName;
    }

    public void setInfoName(String infoName) {
        this.infoName = infoName;
    }

    public String getInfoMobile() {
        return infoMobile;
    }

    public void setInfoMobile(String infoMobile) {
        this.infoMobile = infoMobile;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUserEnabled() {
        return userEnabled;
    }

    public void setUserEnabled(String userEnabled) {
        this.userEnabled = userEnabled;
    }

    public String getUserLocked() {
        return userLocked;
    }

    public void setUserLocked(String userLocked) {
        this.userLocked = userLocked;
    }

    public String getUserExpiryDate() {
        return userExpiryDate;
    }

    public void setUserExpiryDate(String userExpiryDate) {
        this.userExpiryDate = userExpiryDate;
    }

    public Boolean getTransmit() {
        return isTransmit;
    }

    public void setTransmit(Boolean transmit) {
        isTransmit = transmit;
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

    public String getBitCode() {
        return bitCode;
    }

    public void setBitCode(String bitCode) {
        this.bitCode = bitCode;
    }
}
