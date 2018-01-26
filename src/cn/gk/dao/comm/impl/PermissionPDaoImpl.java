package cn.gk.dao.comm.impl;

import java.util.List;

import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.spring.SpringCreator;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import cn.gk.dao.base.impl.BaseDaoImpl;
import cn.gk.dao.comm.PermissionPDao;
import cn.gk.model.comm.PermissionP;
import cn.gk.util.ComFun;

@Repository("permissionPDao")
@RemoteProxy(creator = SpringCreator.class)
public class PermissionPDaoImpl extends BaseDaoImpl<PermissionP, String> implements PermissionPDao {

	@Override
	public PermissionP getPermisPByName(String permissionName) {
		StringBuilder hql = new StringBuilder(HQL_LIST);
		hql.append(" and permissionName = ? ");
		List<PermissionP> list = super.listByWhere(hql.toString(), permissionName);
		if (ComFun.strNull(list, true)) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<PermissionP> getPermissionPList(boolean slipDisabledFlag) {
		StringBuilder hql = new StringBuilder(HQL_LIST);
		if (slipDisabledFlag) {
			hql.append(" and permissionStatus = 1 ");
		}
		List<PermissionP> list = super.listByWhere(hql.toString());
		if (ComFun.strNull(list, true)) {
			return list;
		}
		return null;
	}

	@Override
	public List<PermissionP> getPermissionPListByPage(boolean slipDisabledFlag, int pageIndex, int pageCount) {
		StringBuilder hql = new StringBuilder(HQL_LIST);
		if (slipDisabledFlag) {
			hql.append(" and permissionStatus = 1 ");
		}
		List<PermissionP> list = super.listByWherePage(hql.toString(), pageCount, pageIndex);
		if (ComFun.strNull(list, true)) {
			return list;
		}
		return null;
	}

	@Override
	public int getMaxIndexs() {
		StringBuilder hql = new StringBuilder(HQL_LIST);
		hql.append(" order by indexs desc");
		List<PermissionP> list = super.listByWhere(hql.toString());
		if (list != null && list.size() > 0) {
			return list.get(0).getIndexs();
		}
		return -1;
	}

	@Override
	public List<PermissionP> getPermissionPBySort(Integer fromIndex, Integer toIndex) {
		StringBuilder hql = new StringBuilder(HQL_LIST);
		if (toIndex == null) {
			hql.append(" and indexs = " + fromIndex);
		} else {
			hql.append(" and indexs >= " + fromIndex);
			if (toIndex >= 0) {
				hql.append(" and indexs <= " + toIndex);
			}
		}
		List<PermissionP> list = super.listByWhere(hql.toString());
		if (list != null && list.size() > 0) {
			return list;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getPermissionPAndStateList(String type, String aboutId) {
		StringBuilder sql = new StringBuilder("select perP.id, perP.permission_name, perP.permission_intro, perP.permission_status, perP.indexs, ");
		if(type.equals("user")){
			sql.append(" (select count(id) from t_comm_user_permission userP ");
			sql.append(" where userP.state > 0 and userP.user_id = '" + aboutId + "' and userP.permission_pid = perP.id) hasFlag ");
		}else{
			sql.append(" (select count(id) from t_comm_group_permission groupP ");
			sql.append(" where groupP.state > 0 and groupP.group_type = " + aboutId + " and groupP.permission_pid = perP.id) hasFlag ");
		}
		sql.append(" from t_comm_permission_p perP ");
		sql.append(" where perP.state > 0 ");
		sql.append(" and perP.permission_status = 1 ");
		sql.append(" order by perP.indexs ");
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		List<Object[]> list = query.list();
		if (ComFun.strNull(list, true)) {
			return list;
		}
		return null;
	}

}
