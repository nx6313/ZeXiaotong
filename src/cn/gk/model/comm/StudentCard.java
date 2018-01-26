package cn.gk.model.comm;

import java.math.BigDecimal;
import java.util.Date;

import cn.gk.model.base.BaseClass;

public class StudentCard extends BaseClass {
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 所属代理商Id
	 */
	private String aboutAgencyId;
	/**
	 * 学生卡状态（0：未出售、1：已出售、2：已冻结）
	 */
	private int cardStatus;
	/**
	 * 卡激活时间
	 */
	private Date cardActiveTime;
	/**
	 * 卡号
	 */
	private String cardNum;
	/**
	 * 帐号（可默认手机号为帐号）
	 */
	private String accountNum;
	/**
	 * 密码
	 */
	private String passWord;
	/**
	 * 姓名
	 */
	private String userName;
	/**
	 * 文理科类型（1：文科、2：理科）部分省市必填
	 */
	private int familyType;
	/**
	 * 地区（必填，不得自行修改）
	 */
	private String area;
	/**
	 * 学校名称（必填，不得自行修改）
	 */
	private String schoolName;
	/**
	 * 班级（必填，不得自行修改）
	 */
	private String classGrade;
	/**
	 * 手机号（必填，使用体验卡手机号验证。一号限一次）
	 */
	private String phoneNum;
	/**
	 * 邮箱（选填，可找回密码）
	 */
	private String email;
	/**
	 * 余额（消费自动扣费）
	 */
	private BigDecimal balance;
	/**
	 * 级别（充值自动升级）
	 */
	private int cardLevel;
	/**
	 * 平时成绩
	 */
	private Double normalScore;
	/**
	 * 高考成绩
	 */
	private Double entranceScore;
	/**
	 * 我的职业测评
	 */
	private String myVocationalAssess;
	/**
	 * 我关注的院校
	 */
	private String myAttentionSchool;
	/**
	 * 我的志愿表
	 */
	private String myVolunteerChart;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAboutAgencyId() {
		return aboutAgencyId;
	}

	public void setAboutAgencyId(String aboutAgencyId) {
		this.aboutAgencyId = aboutAgencyId;
	}

	public int getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(int cardStatus) {
		this.cardStatus = cardStatus;
	}

	public Date getCardActiveTime() {
		return cardActiveTime;
	}

	public void setCardActiveTime(Date cardActiveTime) {
		this.cardActiveTime = cardActiveTime;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getAccountNum() {
		return accountNum;
	}

	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getFamilyType() {
		return familyType;
	}

	public void setFamilyType(int familyType) {
		this.familyType = familyType;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getClassGrade() {
		return classGrade;
	}

	public void setClassGrade(String classGrade) {
		this.classGrade = classGrade;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public int getCardLevel() {
		return cardLevel;
	}

	public void setCardLevel(int cardLevel) {
		this.cardLevel = cardLevel;
	}

	public Double getNormalScore() {
		return normalScore;
	}

	public void setNormalScore(Double normalScore) {
		this.normalScore = normalScore;
	}

	public Double getEntranceScore() {
		return entranceScore;
	}

	public void setEntranceScore(Double entranceScore) {
		this.entranceScore = entranceScore;
	}

	public String getMyVocationalAssess() {
		return myVocationalAssess;
	}

	public void setMyVocationalAssess(String myVocationalAssess) {
		this.myVocationalAssess = myVocationalAssess;
	}

	public String getMyAttentionSchool() {
		return myAttentionSchool;
	}

	public void setMyAttentionSchool(String myAttentionSchool) {
		this.myAttentionSchool = myAttentionSchool;
	}

	public String getMyVolunteerChart() {
		return myVolunteerChart;
	}

	public void setMyVolunteerChart(String myVolunteerChart) {
		this.myVolunteerChart = myVolunteerChart;
	}
}
