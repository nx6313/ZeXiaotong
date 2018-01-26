package cn.gk.service.comm;

import java.util.List;

import cn.gk.model.comm.UserPermission;
import cn.gk.service.base.BaseService;

/**
 * 权限主表Service
 */
public interface UserPermissionService extends BaseService<UserPermission, String> {

	/**
	 * 通过用户Id获取其权限列表(带有Index排序)
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
