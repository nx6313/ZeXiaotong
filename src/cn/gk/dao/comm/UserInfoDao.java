package cn.gk.dao.comm;

import java.util.List;

import com.fr.json.JSONObject;

import cn.gk.dao.base.BaseDao;
import cn.gk.model.comm.UserInfo;

public interface UserInfoDao extends BaseDao<UserInfo, String> {
	public static final String HQL_LIST = "from UserInfo u where u.state > 0 ";
	public static final String HQL_LIST_DELETE = "from UserInfo u where u.state = 0 ";
	public static final String HQL_LIST_COUNT = "select count(u) from UserInfo u where u.state > 0";
	
	/**
	 * 获取分页数
	 * 
	 * @return
	 */
	public Integer getPageCount(Integer groupType);
	
	/**
	 * 获取总条数
	 * 
	 * @return
	 */
	public Integer getAllCount(Integer groupType);
	
	/**
	 * 根据账户、密码查找用户
	 */
	public UserInfo getUserInfoByNamePass(String loginName, String password, boolean ignore);
	
	/**
	 * 根据用户账户查找
	 * 
	 * @param num
	 * @return
	 */
	public UserInfo getUserInfoByLoginName(String loginName);
	
	/**
	 * 多条件，分页，自定义排序 查找用户列表
	 * 
	 * @param pageSize
	 * @param pn
	 * @param sortName
	 * @param sortOrder
	 * @param paramlist
	 * @return
	 */
	public List<UserInfo> listByWhereSortPage(final int pageSize, final int pn,
			final String sortName, final String sortOrder,
			final Object... paramlist);

	/**
	 * 根据条件查找 查找用户总数
	 * 
	 * @param paramlist
	 * @return
	 */
	public int countByWhere(final Object... paramlist);
	
	/**
	 * 获取最大用户表数据序号
	 * @return
	 */
	public int getMaxIndexs();

	/**
	 * 根据条件异步筛选用户
	 * 
	 * @param paramesJson
	 *            筛选参数JSON
	 * @param searchType
	 *            0：查超级管理员、1：查普通管理员、2：查代理用户、3：查学生会员用户、4：查专家用户
	 * @return
	 */
	public List<UserInfo> getUserListByParamesAjax(JSONObject paramesJson, int searchType);
	
}
