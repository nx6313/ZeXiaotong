package cn.gk.dao.comm.impl;

import java.util.List;

import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.spring.SpringCreator;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import cn.gk.dao.base.impl.BaseDaoImpl;
import cn.gk.dao.comm.UserPermissionDao;
import cn.gk.model.comm.UserPermission;
import cn.gk.util.ComFun;

@Repository("userPermissionDao")
@RemoteProxy(creator = SpringCreator.class)
public class UserPermissionDaoImpl extends BaseDaoImpl<UserPermission, String> implements UserPermissionDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getUserPermisByUserIdSort(String userId) {
		StringBuilder sql = new StringBuilder("select userper.permission_pid, userper.permission_cid, perp.permission_status from t_comm_user_permission userper, t_comm_permission_p perp ");
		sql.append(" where perp.id = userper.permission_pid ");
		sql.append(" and perp.state > 0 and userper.state > 0 ");
		sql.append(" and userper.user_id = '" + userId + "' ");
		sql.append(" and perp.permission_status = 1 ");
		sql.append(" order by perp.indexs ");
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		List<Object[]> list = query.list();
		if (ComFun.strNull(list, true)) {
			return list;
		}
		return null;
	}

	@Override
	public List<UserPermission> getUserPermisByUserId(String userId) {
		StringBuilder hql = new StringBuilder(HQL_LIST);
		hql.append(" and userId = ? ");
		List<UserPermission> list = super.listByWhere(hql.toString(), userId);
		if (ComFun.strNull(list, true)) {
			return list;
		}
		return null;
	}

	@Override
	public UserPermission getByUserIdPerPid(String userId, String perPid, String perCid) {
		StringBuilder hql = new StringBuilder(HQL_LIST);
		hql.append(" and userId = ? ");
		hql.append(" and permissionPid = ? ");
		List<UserPermission> list = super.listByWhere(hql.toString(), userId, perPid);
		if (ComFun.strNull(list, true)) {
			UserPermission uPermission = list.get(0);
			if(ComFun.strNull(perCid) && ComFun.strNull(uPermission.getPermissionCid())) {
				if(ComFun.checkObjInArr(uPermission.getPermissionCid().split(","), perCid)) {
					return uPermission;
				}else{
					return null;
				}
			}
			return uPermission;
		}
		return null;
	}

}
