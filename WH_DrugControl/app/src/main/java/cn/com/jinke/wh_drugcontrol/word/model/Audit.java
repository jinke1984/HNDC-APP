package cn.com.jinke.wh_drugcontrol.word.model;

/**
 * Audit审批人
 * <br><br>
 *
 * @author qiaotengfei<br>
 * @date 2017/9/7
 */

public class Audit {

    private String userId;
    private String loginId;
    private String userName;
    private String userMobile;
    private String userEmail;
    private int userType;
    private int userEnabled;
    private int userLocked;
    private String userExpiryDate;
    private String orgId;
    private String orgName;
    private String orgParentId;
    private String orgCode;
    private int userOrder;
    private String userPhoto;
    private String bitCode;

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserEnabled(int userEnabled) {
        this.userEnabled = userEnabled;
    }

    public int getUserEnabled() {
        return userEnabled;
    }

    public void setUserLocked(int userLocked) {
        this.userLocked = userLocked;
    }

    public int getUserLocked() {
        return userLocked;
    }

    public void setUserExpiryDate(String userExpiryDate) {
        this.userExpiryDate = userExpiryDate;
    }

    public String getUserExpiryDate() {
        return userExpiryDate;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgParentId(String orgParentId) {
        this.orgParentId = orgParentId;
    }

    public String getOrgParentId() {
        return orgParentId;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setUserOrder(int userOrder) {
        this.userOrder = userOrder;
    }

    public int getUserOrder() {
        return userOrder;
    }

    public void setUserPhoto(String userPhoto) {
        this.userPhoto = userPhoto;
    }

    public String getUserPhoto() {
        return userPhoto;
    }

    public void setBitCode(String bitCode) {
        this.bitCode = bitCode;
    }

    public String getBitCode() {
        return bitCode;
    }

}
