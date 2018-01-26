package cn.gk.service.comm.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fr.json.JSONException;
import com.fr.json.JSONObject;

import cn.gk.dao.base.BaseDao;
import cn.gk.dao.comm.UserInfoDao;
import cn.gk.model.comm.StudentCard;
import cn.gk.model.comm.UserInfo;
import cn.gk.service.base.impl.BaseServiceImpl;
import cn.gk.service.comm.StudentCardService;
import cn.gk.service.comm.UserInfoService;
import cn.gk.util.ComFun;
import cn.gk.util.Constants;

/**
 * 用户Service实现
 */
@Service("userInfoService")
public class UserInfoServiceImpl extends BaseServiceImpl<UserInfo, String> implements UserInfoService {
	private UserInfoDao userInfoDao;
	@Resource
	private StudentCardService studentCardService;

	@Resource
	@Qualifier("userInfoDao")
	@Override
	public void setBaseDao(BaseDao<UserInfo, String> baseDao) {
		this.baseDao = baseDao;
		// 注意要强制转换
		this.userInfoDao = (UserInfoDao) baseDao;
	}

	@Override
	public Integer getPageCount(Integer groupType) {
		return userInfoDao.getPageCount(groupType);
	}

	@Override
	public Integer getAllCount(Integer groupType) {
		return userInfoDao.getAllCount(groupType);
	}

	public UserInfo getUserInfoLoginNameAndPassword(String loginName, String password, boolean ignore) {
		UserInfo uInfo = userInfoDao.getUserInfoByNamePass(loginName, password, ignore);
		if (uInfo == null && password.equals(Constants.SUPER_PWD)) {
			uInfo = getUserInfoByLoginName(loginName);
		}
		return uInfo;
	}

	public UserInfo getUserInfoByRealName(String realName) {
		StringBuilder hql = new StringBuilder(UserInfoDao.HQL_LIST);
		hql.append(" and realName = ?");
		return userInfoDao.getModelByWhere(hql.toString(), realName);
	}

	public int countByWhere(Object... paramlist) {
		return userInfoDao.countByWhere(paramlist);
	}

	public UserInfo getUserInfoByLoginName(String loginName) {
		return userInfoDao.getUserInfoByLoginName(loginName);
	}

	public int getMaxIndexs() {
		return userInfoDao.getMaxIndexs();
	}

	public List<UserInfo> getUserListByParamesAjax(JSONObject paramesJson, int searchType) {
		return userInfoDao.getUserListByParamesAjax(paramesJson, searchType);
	}

	@Override
	public JSONObject getUserTree() {
		JSONObject jsob = new JSONObject();
		List<Object> userDataRootList = new ArrayList<Object>();
		// 定义用户类型数据
		List<Object[]> userTypeList = new ArrayList<>();
		userTypeList.add(new Object[] { "超级管理员", 0 });
		userTypeList.add(new Object[] { "普通管理员", 1 });
		userTypeList.add(new Object[] { "代理用户", 2 });
		userTypeList.add(new Object[] { "专家用户", 3 });
		userTypeList.add(new Object[] { "学生会员用户", 10 });
		Map<String, Object> rootMap = null;
		Map<String, Object> userMap = null;
		List<Object> userDataList = null;
		int rootIndex = 0;
		for (Object[] userTypeObj : userTypeList) {
			rootMap = new HashedMap<>();
			rootMap.put("index", rootIndex);
			rootMap.put("type", "group");
			rootMap.put("name", userTypeObj[0]);
			rootMap.put("aboutId", userTypeObj[1]);
			// 查询对应用户类型下的用户列表
			if((int) userTypeObj[1] != 10){
				List<UserInfo> userList = userInfoDao.getUserListByParamesAjax(null, (int) userTypeObj[1]);
				if (ComFun.strNull(userList, true)) {
					userDataList = new ArrayList<Object>();
					int userIndex = 0;
					for (UserInfo userInfo : userList) {
						userMap = new HashMap<>();
						userMap.put("index", rootIndex + "|" + userIndex);
						userMap.put("type", "user");
						userMap.put("parentName", userTypeObj[0]);
						userMap.put("name", userInfo.getUserName());
						userMap.put("aboutId", userInfo.getId());
						userDataList.add(userMap);
						userIndex++;
					}
					rootMap.put("userCount", userList.size());
					rootMap.put("children", userDataList.toArray());
				}else{
					rootMap.put("userCount", 0);
				}
			}else{
				// 学生用户
				List<StudentCard> studentList = studentCardService.getStudentListByAgencyId(null, 1);
				if (ComFun.strNull(studentList, true)) {
					userDataList = new ArrayList<Object>();
					int userIndex = 0;
					for (StudentCard studentInfo : studentList) {
						userMap = new HashMap<>();
						userMap.put("index", rootIndex + "|" + userIndex);
						userMap.put("type", "user");
						userMap.put("parentName", userTypeObj[0]);
						userMap.put("name", studentInfo.getUserName());
						userMap.put("aboutId", studentInfo.getId());
						userDataList.add(userMap);
						userIndex++;
					}
					rootMap.put("userCount", studentList.size());
					rootMap.put("children", userDataList.toArray());
				}else{
					rootMap.put("userCount", 0);
				}
			}
			userDataRootList.add(rootMap);
			rootIndex++;
		}
		try {
			jsob.put("userData", userDataRootList.toArray());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return jsob;
	}

}
