package cn.gk.dao.school.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.spring.SpringCreator;
import org.springframework.stereotype.Repository;

import com.fr.json.JSONException;
import com.fr.json.JSONObject;

import cn.gk.dao.base.impl.BaseDaoImpl;
import cn.gk.dao.school.SchoolReferDao;
import cn.gk.model.student.Academy;
import cn.gk.util.ComFun;

@Repository("schoolReferDao")
@RemoteProxy(creator = SpringCreator.class)
public class SchoolReferDaoImpl extends BaseDaoImpl<Academy, String> implements SchoolReferDao {

	@Override
	public Integer getPageCount(Integer pageSize, boolean forPageFlag) {
		StringBuilder hql = new StringBuilder(HQL_LIST);
		List<Academy> list = super.listByWhere(hql.toString());
		if (forPageFlag) {
			if (ComFun.strNull(list, true)) {
				return (int) Math.ceil(Float.parseFloat(list.size() + "f") / Float.parseFloat(pageSize + "f"));
			}
			return 1;
		} else {
			if (ComFun.strNull(list, true)) {
				return list.size();
			}
			return 0;
		}
	}

	@Override
	public List<Academy> getPageData(Integer pageIndex, Integer pageSize, JSONObject searchValJson) {
		StringBuilder builder = new StringBuilder(HQL_LIST);
		Map<String, List<String>> map = getHql(builder, searchValJson);
		List<Academy> list = new ArrayList<Academy>();
		for (Map.Entry<String, List<String>> e : map.entrySet()) {
			list = super.listByWhereSortPage(e.getKey(), pageSize, pageIndex, "globalHeat", "desc",
					e.getValue().toArray());
		}
		return list;
	}

	@SuppressWarnings({ "rawtypes" })
	public Map<String, List<String>> getHql(StringBuilder builder, JSONObject searchValJson) {
		List<String> strList = new ArrayList<String>();
		// 院校名称模糊查询、院校类型、所在地（省）、学历层次、特殊属性、通讯地址模糊查询
		if (ComFun.strNull(searchValJson)) {
			for (Iterator iter = searchValJson.keys(); iter.hasNext();) {
				String searchName = (String) iter.next();
				try {
					String searchVal = searchValJson.getString(searchName);
					if (ComFun.strNull(searchVal) && !searchVal.equals("NULL")) {
						String searchNameRealVal = searchName.replace("search_", "");
						if (searchNameRealVal.endsWith("%")) {
							builder.append(" and " + searchNameRealVal.substring(0, searchNameRealVal.length() - 1)
									+ " like ? ");
							strList.add("%" + searchVal + "%");
						} else {
							builder.append(" and " + searchNameRealVal + " = ? ");
							strList.add(searchVal);
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put(builder.toString(), strList);
		return map;
	}

}
