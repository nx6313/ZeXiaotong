package cn.gk.service.comm.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.gk.dao.base.BaseDao;
import cn.gk.dao.comm.UserPermissionDao;
import cn.gk.model.comm.UserPermission;
import cn.gk.service.base.impl.BaseServiceImpl;
import cn.gk.service.comm.UserPermissionService;

/**
 * 用户权限表Service实现
 */
@Service("userPermissionService")
public class UserPermissionServiceImpl extends BaseServiceImpl<UserPermission, String> implements UserPermissionService {
	private UserPermissionDao userPermissionDao;

	@Resource
	@Qualifier("userPermissionDao")
	@Override
	public void setBaseDao(BaseDao<UserPermission, String> baseDao) {
		this.baseDao = baseDao;
		// 注意要强制转换
		this.userPermissionDao = (UserPermissionDao) baseDao;
	}

	@Override
	public List<Object[]> getUserPermisByUserIdSort(String userId) {
		return userPermissionDao.getUserPermisByUserIdSort(userId);
	}

	@Override
	public List<UserPermission> getUserPermisByUserId(String userId) {
		return userPermissionDao.getUserPermisByUserId(userId);
	}

	@Override
	public UserPermission getByUserIdPerPid(String userId, String perPid, String perCid) {
		return userPermissionDao.getByUserIdPerPid(userId, perPid, perCid);
	}

}
