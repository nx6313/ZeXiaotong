package cn.gk.model.comm;

import cn.gk.model.base.BaseClass;

public class PermissionP extends BaseClass implements Comparable<PermissionP> {
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 父级权限名称
	 */
	private String permissionName;
	/**
	 * 父级权限简介
	 */
	private String permissionIntro;
	/**
	 * 是否可用（0：不可用、1：可用）
	 */
	private int permissionStatus;
	/**
	 * 排序索引值
	 */
	private int indexs;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public int getPermissionStatus() {
		return permissionStatus;
	}

	public void setPermissionStatus(int permissionStatus) {
		this.permissionStatus = permissionStatus;
	}

	public int getIndexs() {
		return indexs;
	}

	public void setIndexs(int indexs) {
		this.indexs = indexs;
	}

	@Override
	public int compareTo(PermissionP o) {
		return this.indexs - o.getIndexs();
	}
}
