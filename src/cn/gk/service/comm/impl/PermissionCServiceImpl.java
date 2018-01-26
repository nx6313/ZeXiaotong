package cn.gk.service.comm.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.gk.dao.base.BaseDao;
import cn.gk.dao.comm.PermissionCDao;
import cn.gk.model.comm.PermissionC;
import cn.gk.service.base.impl.BaseServiceImpl;
import cn.gk.service.comm.PermissionCService;

/**
 * 权限子表Service实现
 */
@Service("permissionCService")
public class PermissionCServiceImpl extends BaseServiceImpl<PermissionC, String> implements PermissionCService {
	private PermissionCDao permissionCDao;

	@Resource
	@Qualifier("permissionCDao")
	@Override
	public void setBaseDao(BaseDao<PermissionC, String> baseDao) {
		this.baseDao = baseDao;
		// 注意要强制转换
		this.permissionCDao = (PermissionCDao) baseDao;
	}

	@Override
	public PermissionC getPermisCByName(String permissionName) {
		return permissionCDao.getPermisCByName(permissionName);
	}

	@Override
	public List<PermissionC> getPermissionCListByPId(String perPId, boolean slipDisabledFlag) {
		return permissionCDao.getPermissionCListByPId(perPId, slipDisabledFlag);
	}

}
