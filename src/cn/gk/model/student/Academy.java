package cn.gk.model.student;

import cn.gk.model.base.BaseClass;

/**
 * 院校表(基础表)
 */
public class Academy extends BaseClass {
	private static final long serialVersionUID = 1L;

	private String id;
	/**
	 * 学校LOGO
	 */
	private String schoolLogo;
	/**
	 * 学校名称
	 */
	private String schoolName;
	/**
	 * 院校类型（综合、理工、农林、医药、师范、语言、财经、政法、体育、艺术、民族、军事、其他）
	 */
	private String schoolType;
	/**
	 * 特殊属性（985工程、211工程、教育部直属、中央部委、自主招生试点）
	 */
	private String specialAttr;
	/**
	 * 所在地（省）（北京、天津、河北、河南、山东、山西、陕西、内蒙古、辽宁、吉林、
	 * 黑龙江、上海、江苏、安徽、江西、湖北、湖南、重庆、四川、贵州、云南、广东、
	 * 广西、福建、甘肃、宁夏、新疆、西藏、海南、浙江、青海、香港、澳门）
	 */
	private String location;
	/**
	 * 学历层次  普通本科、独立学院、高职高专、中外合作办学、远程教育学院、HND项目、成人教育、其他
	 */
	private String education;
	/**
	 * 学历类型 （本科、专科）
	 */
	private String educationType;
	/**
	 * 招生办电话
	 */
	private String admissionOfficePhone;
	/**
	 * 电子邮箱
	 */
	private String email;
	/**
	 * 通讯地址
	 */
	private String address;
	/**
	 * 招生网址
	 */
	private String admissionNet;
	/**
	 * 简介
	 */
	private String intro;
	/**
	 * 就业情况
	 */
	private String employment;
	/**
	 * 全球热度排名
	 */
	private String globalHeat;
	/**
	 * 类别热度排名
	 */
	private String classHeat;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSchoolLogo() {
		return schoolLogo;
	}

	public void setSchoolLogo(String schoolLogo) {
		this.schoolLogo = schoolLogo;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getSchoolType() {
		return schoolType;
	}

	public void setSchoolType(String schoolType) {
		this.schoolType = schoolType;
	}

	public String getSpecialAttr() {
		return specialAttr;
	}

	public void setSpecialAttr(String specialAttr) {
		this.specialAttr = specialAttr;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getEducationType() {
		return educationType;
	}

	public void setEducationType(String educationType) {
		this.educationType = educationType;
	}

	public String getAdmissionOfficePhone() {
		return admissionOfficePhone;
	}

	public void setAdmissionOfficePhone(String admissionOfficePhone) {
		this.admissionOfficePhone = admissionOfficePhone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAdmissionNet() {
		return admissionNet;
	}

	public void setAdmissionNet(String admissionNet) {
		this.admissionNet = admissionNet;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getEmployment() {
		return employment;
	}

	public void setEmployment(String employment) {
		this.employment = employment;
	}

	public String getGlobalHeat() {
		return globalHeat;
	}

	public void setGlobalHeat(String globalHeat) {
		this.globalHeat = globalHeat;
	}

	public String getClassHeat() {
		return classHeat;
	}

	public void setClassHeat(String classHeat) {
		this.classHeat = classHeat;
	}

}
