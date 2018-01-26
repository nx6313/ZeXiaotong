package cn.gk.service.comm;

import java.util.List;

import com.fr.json.JSONObject;

import cn.gk.model.comm.UserInfo;
import cn.gk.service.base.BaseService;

/**
 * 用户Service
 */
public interface UserInfoService extends BaseService<UserInfo, String> {
	
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
	 * 根据用户名和密码得到UserInfo
	 * 
	 * @param loginName
	 * @param password
	 * @param ignore
	 * @return
	 */
	public UserInfo getUserInfoLoginNameAndPassword(String loginName, String password, boolean ignore);

	/**
	 * 根据真实姓名查询UserInfo对象
	 * 
	 * @param realName
	 * @return
	 */
	public UserInfo getUserInfoByRealName(String realName);

	/**
	 * 根据条件查找 查找用户总数
	 * 
	 * @param paramlist
	 * @return
	 */
	public int countByWhere(final Object... paramlist);

	/**
	 * 根据用户账户查找
	 * 
	 * @param num
	 * @return
	 */
	public UserInfo getUserInfoByLoginName(String loginName);

	/**
	 * 获取最大用户表数据序号
	 * 
	 * @return
	 */
	public int getMaxIndexs();

	/**
	 * 根据条件异步筛选用户
	 * 
	 * @param paramesJson
	 *            筛选参数JSON
	 * @param searchType
	 *            0：查超级管理员、1：查普通管理员、2：查代理用户、3：查专家用户
	 * @return
	 */
	public List<UserInfo> getUserListByParamesAjax(JSONObject paramesJson, int searchType);

	/**
	 * 获取用户树
	 * 
	 * @return
	 */
	public JSONObject getUserTree();

}
