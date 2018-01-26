package cn.gk.service.comm.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.gk.dao.base.BaseDao;
import cn.gk.dao.comm.PermissionPDao;
import cn.gk.model.comm.PermissionP;
import cn.gk.service.base.impl.BaseServiceImpl;
import cn.gk.service.comm.PermissionPService;

/**
 * 权限主表Service实现
 */
@Service("permissionPService")
public class PermissionPServiceImpl extends BaseServiceImpl<PermissionP, String> implements PermissionPService {
	private PermissionPDao permissionPDao;

	@Resource
	@Qualifier("permissionPDao")
	@Override
	public void setBaseDao(BaseDao<PermissionP, String> baseDao) {
		this.baseDao = baseDao;
		// 注意要强制转换
		this.permissionPDao = (PermissionPDao) baseDao;
	}

	@Override
	public PermissionP getPermisPByName(String permissionName) {
		return permissionPDao.getPermisPByName(permissionName);
	}

	@Override
	public List<PermissionP> getPermissionPList(boolean slipDisabledFlag) {
		return permissionPDao.getPermissionPList(slipDisabledFlag);
	}

	@Override
	public List<PermissionP> getPermissionPListByPage(boolean slipDisabledFlag, int pageIndex, int pageCount) {
		return permissionPDao.getPermissionPListByPage(slipDisabledFlag, pageIndex, pageCount);
	}

	@Override
	public int getMaxIndexs() {
		return permissionPDao.getMaxIndexs();
	}

	@Override
	public List<PermissionP> getPermissionPBySort(Integer fromIndex, Integer toIndex) {
		return permissionPDao.getPermissionPBySort(fromIndex, toIndex);
	}

	@Override
	public List<Object[]> getPermissionPAndStateList(String type, String aboutId) {
		return permissionPDao.getPermissionPAndStateList(type, aboutId);
	}

}
