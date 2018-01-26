package cn.gk.service.comm;

import java.util.List;

import cn.gk.model.comm.PermissionC;
import cn.gk.service.base.BaseService;

/**
 * 权限子表Service
 */
public interface PermissionCService extends BaseService<PermissionC, String> {

	/**
	 * 通过权限名称获取子权限对象
	 * 
	 * @param permissionName
	 * @return
	 */
	public PermissionC getPermisCByName(String permissionName);

	/**
	 * 通过权限主表Id获取对应的自权限数据集合
	 * 
	 * @param perPId
	 * @param slipDisabledFlag 是否忽略不可用的
	 * @return
	 */
	public List<PermissionC> getPermissionCListByPId(String perPId, boolean slipDisabledFlag);

}
