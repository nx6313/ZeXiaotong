package cn.gk.model.comm;

import cn.gk.model.base.BaseClass;

public class PermissionC extends BaseClass {
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 父级权限Id
	 */
	private String perPid;
	/**
	 * 子级权限名称
	 */
	private String permissionName;
	/**
	 * 子级权限简介
	 */
	private String permissionIntro;
	/**
	 * 子级权限链接
	 */
	private String permissionLink;
	/**
	 * 是否可用（0：不可用、1：可用）
	 */
	private int permissionStatus;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPerPid() {
		return perPid;
	}

	public void setPerPid(String perPid) {
		this.perPid = perPid;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getPermissionIntro() {
		return permissionIntro;
	}

	public void setPermissionIntro(String permissionIntro) {
		this.permissionIntro = permissionIntro;
	}

	public String getPermissionLink() {
		return permissionLink;
	}

	public void setPermissionLink(String permissionLink) {
		this.permissionLink = permissionLink;
	}

	public int getPermissionStatus() {
		return permissionStatus;
	}

	public void setPermissionStatus(int permissionStatus) {
		this.permissionStatus = permissionStatus;
	}
}
