package cn.gk.dao.comm;

import java.util.List;

import cn.gk.dao.base.BaseDao;
import cn.gk.model.comm.PermissionP;

public interface PermissionPDao extends BaseDao<PermissionP, String> {
	public static final String HQL_LIST = "from PermissionP where state > 0 ";
	public static final String HQL_LIST_DELETE = "from PermissionP where state = 0 ";
	public static final String HQL_LIST_COUNT = "select count(t) from PermissionP t where state > 0";
	
	/**
	 * 通过权限名称获取主权限对象
	 * @param permissionName
	 * @return
	 */
	public PermissionP getPermisPByName(String permissionName);

	/**
	 * 获取权限主表数据
	 * 
	 * @param slipDisabledFlag
	 *            是否忽略不可用的
	 * @return
	 */
	public List<PermissionP> getPermissionPList(boolean slipDisabledFlag);
	
	/**
	 * 获取权限主表数据（分页查询）
	 * 
	 * @param slipDisabledFlag
	 *            是否忽略不可用的
	 * @param pageIndex 页数，从1开始
	 * @param pageCount 每页显示数量
	 * @return
	 */
	public List<PermissionP> getPermissionPListByPage(boolean slipDisabledFlag, int pageIndex, int pageCount);

	/**
	 * 获取最大权限主表数据序号
	 * 
	 * @return
	 */
	public int getMaxIndexs();

	/**
	 * 获取权限主表数据（根据排序）
	 * 
	 * @param fromIndex
	 * @param toIndex
	 * @return
	 */
	public List<PermissionP> getPermissionPBySort(Integer fromIndex, Integer toIndex);

	/**
	 * 根据类型（user/group）以及对应id获取用户权限主表数据以及是否有该权限的状态
	 * 
	 * @param type
	 * @param aboutId
	 */
	public List<Object[]> getPermissionPAndStateList(String type, String aboutId);
	
}
