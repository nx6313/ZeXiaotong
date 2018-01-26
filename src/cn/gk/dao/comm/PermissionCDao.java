package cn.gk.dao.comm;

import java.util.List;

import cn.gk.dao.base.BaseDao;
import cn.gk.model.comm.PermissionC;

public interface PermissionCDao extends BaseDao<PermissionC, String> {
	public static final String HQL_LIST = "from PermissionC where state > 0 ";
	public static final String HQL_LIST_DELETE = "from PermissionC where state = 0 ";
	public static final String HQL_LIST_COUNT = "select count(t) from PermissionC t where state > 0";

	/**
	 * 通过权限名称获取子权限对象
	 * 
	 * @param permissionName
	 * @return
	 */
	public PermissionC getPermisCByName(String permissionName);

	/**
	 * 通过权限主表Id获取对应的自权限数据集合(仅获取可用权限)
	 * 
	 * @param perPId
	 * @param slipDisabledFlag 是否忽略不可用的
	 * @return
	 */
	public List<PermissionC> getPermissionCListByPId(String perPId, boolean slipDisabledFlag);
	
}
