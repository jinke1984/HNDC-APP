package cn.com.jinke.wh_drugcontrol.input.model;

import java.io.Serializable;

public class DruguserslistEntity implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;          //ID
	private String name;        //姓名
	private String idCard;      //居民身份证号码
	private String fen;         //积分
	private String preFen;		//上月积分
	private String photo;       //图片地址
	private int gender;         //性别
	private String birthday;    //出生日期
	private String formerName;  //曾用名
	private int mELevel;        //文化程度
	private String address;     //户籍所属区域
	private String phone;       //手机号码
	private String detailAddress; //详细地址
	private String liveAddress;   //
	private String level;
	private int isCollect;
	private String drug_id;
	private String unit;
	private String currentState;
	private int currentStateInt;
	private String controlId;
	private int scoreChangeState;    //积分变化状态
	private String facePhotoPath;    //新的图片地址
	private int type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getPreFen() { return preFen; }
	public void setPreFen(String preFen) { this.preFen = preFen; }
	public String getDrug_id()
	{
		return drug_id;
	}
	public void setDrug_id(String drug_id)
	{
		this.drug_id = drug_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getFen() {
		return fen;
	}
	public void setFen(String fen) {
		this.fen = fen;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getFormerName() {
		return formerName;
	}
	public void setFormerName(String formerName) {
		this.formerName = formerName;
	}
	public int getmELevel() {
		return mELevel;
	}
	public void setmELevel(int mELevel) {
		this.mELevel = mELevel;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDetailAddress() {
		return detailAddress;
	}
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	public String getLiveAddress() {
		return liveAddress;
	}
	public void setLiveAddress(String liveAddress) {
		this.liveAddress = liveAddress;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public int getIsCollect() {
		return isCollect;
	}
	public void setIsCollect(int isCollect) {
		this.isCollect = isCollect;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getCurrentState() {
		return currentState;
	}
	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}
	public int getCurrentStateInt() {
		return currentStateInt;
	}
	public void setCurrentStateInt(int currentStateInt) {
		this.currentStateInt = currentStateInt;
	}
	public String getControlId() {
		return controlId;
	}
	public void setControlId(String controlId) {
		this.controlId = controlId;
	}

	public int getScoreChangeState() {
		return scoreChangeState;
	}

	public void setScoreChangeState(int scoreChangeState) {
		this.scoreChangeState = scoreChangeState;
	}

	public String getFacePhotoPath() {
		return facePhotoPath;
	}

	public void setFacePhotoPath(String facePhotoPath) {
		this.facePhotoPath = facePhotoPath;
	}
}
