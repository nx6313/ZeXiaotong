package cn.gk.model.comm;

import cn.gk.model.base.BaseClass;

public class GroupPermission extends BaseClass {
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 用户组相关Type
	 */
	private int groupType;
	/**
	 * 父级权限Id
	 */
	private String permissionPid;
	/**
	 * 子级权限Id集合（如果没有特殊情况，该字段为空）
	 */
	private String permissionCid;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getGroupType() {
		return groupType;
	}

	public void setGroupType(int groupType) {
		this.groupType = groupType;
	}

	public String getPermissionPid() {
		return permissionPid;
	}

	public void setPermissionPid(String permissionPid) {
		this.permissionPid = permissionPid;
	}

	public String getPermissionCid() {
		return permissionCid;
	}

	public void setPermissionCid(String permissionCid) {
		this.permissionCid = permissionCid;
	}

}
