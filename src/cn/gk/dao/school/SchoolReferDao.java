package cn.gk.dao.school;

import java.util.List;

import com.fr.json.JSONObject;

import cn.gk.dao.base.BaseDao;
import cn.gk.model.student.Academy;

public interface SchoolReferDao extends BaseDao<Academy, String> {
	public static final String HQL_LIST = "from Academy where state > 0 ";
	public static final String HQL_LIST_DELETE = "from Academy where state = 0 ";
	public static final String HQL_LIST_COUNT = "select count(id) from Academy where state > 0";
	
	/**
	 * 获取分页数
	 * 
	 * @return
	 */
	public Integer getPageCount(Integer pageSize, boolean forPageFlag);
	
	/**
	 * 获取分页数据
	 * 
	 * @return
	 */
	public List<Academy> getPageData(Integer pageIndex, Integer pageSize, JSONObject searchValJson);
	
}
