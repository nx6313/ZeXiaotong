package cn.gk.model.comm;

import java.util.Date;

import cn.gk.model.base.BaseClass;

/**
 * 用户信息表
 */
public class UserInfo extends BaseClass implements Comparable<UserInfo> {
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 用户类型 0：超级管理员、1：普通管理员、2：代理用户、3：专家用户
	 */
	private int type;
	/**
	 * 注册时间
	 */
	private Date registerDate;
	/**
	 * 上级代理（代理用户填写该字段）
	 */
	private String father;
	/**
	 * 备注
	 */
	private String memo;
	/**
	 * 代理类型
	 */
	private String agencyType;
	/**
	 * 数量
	 */
	private int count;
	/**
	 * 密码
	 */
	private String pass;
	/**
	 * 代理姓名
	 */
	private String agencyName;
	/**
	 * 用户卡类型
	 */
	private String userCardType;
	/**
	 * 起始号码
	 */
	private int startCode;
	/**
	 * 结束号码
	 */
	private int endCode;
	/**
	 * 省份
	 */
	private String province;
	/**
	 * 县市
	 */
	private String city;
	/**
	 * 区县
	 */
	private String area;
	/**
	 * 电话
	 */
	private String tel;
	/**
	 * 手机
	 */
	private String mobile;
	/**
	 * 邮箱地址
	 */
	private String email;
	/**
	 * 索引值
	 */
	private int indexs;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public String getFather() {
		return father;
	}

	public void setFather(String father) {
		this.father = father;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getAgencyType() {
		return agencyType;
	}

	public void setAgencyType(String agencyType) {
		this.agencyType = agencyType;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getUserCardType() {
		return userCardType;
	}

	public void setUserCardType(String userCardType) {
		this.userCardType = userCardType;
	}

	public int getStartCode() {
		return startCode;
	}

	public void setStartCode(int startCode) {
		this.startCode = startCode;
	}

	public int getEndCode() {
		return endCode;
	}

	public void setEndCode(int endCode) {
		this.endCode = endCode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getIndexs() {
		return indexs;
	}

	public void setIndexs(int indexs) {
		this.indexs = indexs;
	}

	public int compareTo(UserInfo o) {
		return this.indexs - o.getIndexs();
	}

}
