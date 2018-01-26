package cn.gk.dao.comm.impl;

import java.util.List;

import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.spring.SpringCreator;
import org.springframework.stereotype.Repository;

import cn.gk.dao.base.impl.BaseDaoImpl;
import cn.gk.dao.comm.PermissionCDao;
import cn.gk.model.comm.PermissionC;
import cn.gk.util.ComFun;

@Repository("permissionCDao")
@RemoteProxy(creator = SpringCreator.class)
public class PermissionCDaoImpl extends BaseDaoImpl<PermissionC, String> implements PermissionCDao {

	@Override
	public List<PermissionC> getPermissionCListByPId(String perPId, boolean slipDisabledFlag) {
		StringBuilder hql = new StringBuilder(HQL_LIST);
		hql.append(" and perPid = ? ");
		if (slipDisabledFlag) {
			hql.append(" and permissionStatus = 1");
		}
		return super.listByWhere(hql.toString(), perPId);
	}

	@Override
	public PermissionC getPermisCByName(String permissionName) {
		List<PermissionC> list = null;
		StringBuilder hql = new StringBuilder(HQL_LIST);
		if(ComFun.strNull(permissionName) && permissionName.contains("/")){
			String[] permissionNameArr = permissionName.split("/");
			hql.append(" and (");
			int index = 0;
			for(String perNa : permissionNameArr){
				index++;
				hql.append(" permissionName = '" + perNa + "' ");
				if(index < permissionNameArr.length){
					hql.append(" or ");
				}
			}
			hql.append(") ");
			list = super.listByWhere(hql.toString());
		}else{
			hql.append(" and permissionName = ? ");
			list = super.listByWhere(hql.toString(), permissionName);
		}
		if (ComFun.strNull(list, true)) {
			return list.get(0);
		}
		return null;
	}

}
