package cn.gk.dao.comm;

import java.util.List;

import cn.gk.dao.base.BaseDao;
import cn.gk.model.comm.UserPermission;

public interface UserPermissionDao extends BaseDao<UserPermission, String> {
	public static final String HQL_LIST = "from UserPermission where state > 0 ";
	public static final String HQL_LIST_DELETE = "from UserPermission where state = 0 ";
	public static final String HQL_LIST_COUNT = "select count(t) from UserPermission t where state > 0";
	
	/**
	 * 通过用户Id获取其权限列表(带index排序)
	 * 
	 * @param userId
	 * @return
	 */
	public List<Object[]> getUserPermisByUserIdSort(String userId);

	/**
	 * 通过用户Id获取其权限列表
	 * 
	 * @param userId
	 * @return
	 */
	public List<UserPermission> getUserPermisByUserId(String userId);

	/**
	 * 通过用户Id和权限Id获取权限(主要判断用户是否拥有该子级权限)
	 * 
	 * @param userId
	 * @return
	 */
	public UserPermission getByUserIdPerPid(String userId, String perPid, String perCid);
}
