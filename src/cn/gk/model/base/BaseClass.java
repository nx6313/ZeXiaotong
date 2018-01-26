package cn.gk.model.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import cn.gk.util.Constants;

//子类继承父类映射的时候需要
@MappedSuperclass
public class BaseClass implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private String createId;
	private Date createDate;
	private String updateId;
	private Date updateDate;
	private String deleteId;
	private Date deleteDate;
	private int state;

	@Column(name = "create_id")
	public String getCreateId() {
		return createId;
	}

	public void setCreateId(String createId) {
		this.createId = createId;
	}

	@Column(name = "create_date")
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "update_id")
	public String getUpdateId() {
		return updateId;
	}

	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}

	@Column(name = "update_date")
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "delete_id")
	public String getDeleteId() {
		return deleteId;
	}

	public void setDeleteId(String deleteId) {
		this.deleteId = deleteId;
	}

	@Column(name = "delete_date")
	public Date getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	@Column(name = "state")
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public void setCreateInfo(String userId) {
		this.setCreateDate(new Date());
		this.setCreateId(userId);
		this.setState(Constants.STATE_ADD);
	}

	public void setUpdateInfo(String userId) {
		this.setUpdateDate(new Date());
		this.setUpdateId(userId);
	}

	public void setDeleteInfo(String userId) {
		this.setDeleteDate(new Date());
		this.setDeleteId(userId);
		this.setState(Constants.STATE_DELETE);
	}

}
