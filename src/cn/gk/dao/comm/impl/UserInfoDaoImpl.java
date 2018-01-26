package cn.gk.dao.comm.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.spring.SpringCreator;
import org.springframework.stereotype.Repository;

import com.fr.json.JSONException;
import com.fr.json.JSONObject;

import cn.gk.dao.base.impl.BaseDaoImpl;
import cn.gk.dao.comm.UserInfoDao;
import cn.gk.model.comm.UserInfo;
import cn.gk.util.ComFun;
import cn.gk.util.Constants;

@Repository("userInfoDao")
@RemoteProxy(creator = SpringCreator.class)
public class UserInfoDaoImpl extends BaseDaoImpl<UserInfo, String> implements UserInfoDao {

	@Override
	public Integer getPageCount(Integer groupType) {
		StringBuilder hql = new StringBuilder(HQL_LIST);
		if(groupType != null) {
			hql.append(" and u.type =" + groupType);
		}
		List<UserInfo> list = super.listByWhere(hql.toString());
		if (ComFun.strNull(list, true)) {
			return (int) Math.ceil(Float.parseFloat(list.size() + "f") / Float.parseFloat(Constants.DEFAULT_PAGE_SIZE + "f"));
		}
		return 1;
	}

	@Override
	public Integer getAllCount(Integer groupType) {
		StringBuilder hql = new StringBuilder(HQL_LIST);
		if(groupType != null) {
			hql.append(" and u.type =" + groupType);
		}
		List<UserInfo> list = super.listByWhere(hql.toString());
		if (ComFun.strNull(list, true)) {
			return list.size();
		}
		return 0;
	}

	public UserInfo getUserInfoByNamePass(String loginName, String password, boolean ignore) {
		StringBuilder hql = new StringBuilder(HQL_LIST);
		hql.append(" and (lower(u.userName) = ? or u.mobile = ? or u.email = ?) and u.pass = ?");
		return super.unique(hql.toString(), loginName.toLowerCase(), loginName, loginName, password);
	}

	public UserInfo getUserInfoByLoginName(String loginName) {
		StringBuilder hql = new StringBuilder(HQL_LIST);
		hql.append(" and (lower(u.userName) = ? or u.mobile = ? or u.email = ?)");
		return super.unique(hql.toString(), loginName.toLowerCase(), loginName, loginName);
	}

	public List<UserInfo> listByWhereSortPage(int pageSize, int pn, String sortName, String sortOrder,
			Object... paramlist) {
		StringBuilder builder = new StringBuilder(HQL_LIST);
		Map<String, List<String>> map = getHql(builder, paramlist);
		List<UserInfo> list = new ArrayList<UserInfo>();
		for (Map.Entry<String, List<String>> e : map.entrySet()) {
			list = super.listByWhereSortPage(e.getKey(), pageSize, pn, sortName, sortOrder, e.getValue().toArray());
		}
		return list;
	}

	public int countByWhere(Object... paramlist) {
		StringBuilder builder = new StringBuilder(HQL_LIST_COUNT);
		Map<String, List<String>> map = getHql(builder, paramlist);
		int count = 0;
		for (Map.Entry<String, List<String>> e : map.entrySet()) {
			count = super.count(e.getKey(), e.getValue().toArray());
		}
		return count;
	}

	@SuppressWarnings("unchecked")
	public Map<String, List<String>> getHql(StringBuilder builder, Object... paramlist) {
		List<String> strList = new ArrayList<String>();
		for (int i = 0; i < paramlist.length; i++) {
			if (paramlist[i] != null) {
				List<String> list = (List<String>) paramlist[i];

				if (list.size() > 0 && list.get(0) != null && !list.get(0).equals("")) {
					builder.append(" and u.userName like ? ");
					strList.add("%" + list.get(0) + "%");
				}
				if (list.size() > 1 && list.get(1) != null && !list.get(1).equals("")) {
					builder.append(" and u.userCardType = ? ");
					strList.add(list.get(1));
				}
			}
		}
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put(builder.toString(), strList);
		return map;
	}

	public int getMaxIndexs() {
		StringBuilder hql = new StringBuilder(HQL_LIST);
		hql.append(" order by u.indexs desc");
		List<UserInfo> list = super.listByWhere(hql.toString());
		if (list != null && list.size() > 0) {
			return list.get(0).getIndexs();
		}
		return -1;
	}

	public List<UserInfo> getUserListByParamesAjax(JSONObject paramesJson, int searchType) {
		// 0：查超级管理员、1：查普通管理员、2：查代理用户、3：查学生会员用户、4：查专家用户
		StringBuilder hql = new StringBuilder(HQL_LIST);
		if (ComFun.strNull(paramesJson)) {
			if (paramesJson.has("userId")) {
				try {
					hql.append(" and u.id = '" + paramesJson.getString("userId") + "'");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else if (paramesJson.has("userSearch")) {
				try {
					hql.append(" and (upper(u.userName) like '%" + paramesJson.getString("userSearch").toUpperCase() + "%'");
					hql.append(" or u.tel like '%" + paramesJson.getString("userSearch") + "%')");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		if (searchType >= 0) {
			hql.append(" and u.type = " + searchType);
		}
		hql.append(" order by u.userName desc");
		if (ComFun.strNull(paramesJson) && paramesJson.has("pageIndex")) {
			try {
				List<UserInfo> list = super.listByWherePage(hql.toString(), Constants.DEFAULT_PAGE_SIZE,
						paramesJson.getInt("pageIndex"));
				if (list != null && list.size() > 0) {
					return list;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else {
			List<UserInfo> list = super.listByWhere(hql.toString());
			if (list != null && list.size() > 0) {
				return list;
			}
		}
		return null;
	}

}
