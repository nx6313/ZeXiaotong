package cn.gk.model.comm;

import cn.gk.model.base.BaseClass;

public class UserPermission extends BaseClass {
	private static final long serialVersionUID = 1L;
	private String id;
	/**
	 * 用户Id
	 */
	private String userId;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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
