package cn.gk.util;

import java.util.HashMap;
import java.util.Map;

public class Constants {
	/**
	 * 添加状态
	 */
	public static final int STATE_ADD = 1;

	/**
	 * 删除状态
	 */
	public static final int STATE_DELETE = 0;

	/**
	 * userSession,将用户信息放在session中
	 */
	public static final String USER_SESSION = "userSession";

	/**
	 * userSessionStudentCard,将用户信息放在session中
	 */
	public static final String USER_SESSION_STUDENT_CARD = "userSessionStudentCard";

	/**
	 * defaultActionSession,将用户信息放在session中
	 */
	public static final String DEFAULT_ACTION_SESSION = "defaultActionSession";

	/**
	 * userNavigationSession,将用户导航栏信息放在session中
	 */
	public static final String USER_NAVIGATION_SESSION = "userNavigationSession";

	/******************************** 分页 end **************************************/
	/**
	 * 默认分页显示条数
	 */
	public static final int DEFAULT_PAGE_SIZE = 10;
	/**
	 * 超级密码
	 */
	public static final String SUPER_PWD = MD5andKL.MD5("zxt_Administrator");
	/**
	 * 默认密码
	 */
	public static final String DEFAULT_PWD = MD5andKL.MD5("123456");
	/**
	 * 存放上传文件的目录
	 */
	public static final String FOLDER_APPEND = "append";
	/**
	 * 项目主要配置文件名称
	 */
	public static final String BOUILLI = "zeXiaotong.properties";
	/**
	 * AJAX请求无返回值
	 */
	public static final String AJAX_NONE = "ajaxNone";
	/**
	 * AJAX请求成功返回值
	 */
	public static final String AJAX_SUCCESS = "ajaxSuccess";
	/**
	 * AJAX请求失败返回值
	 */
	public static final String AJAX_FAIL = "ajaxfailure";
	/**
	 * AJAX请求存在返回值
	 */
	public static final String AJAX_EXIST = "ajaxExist";
	/**
	 * 文件上传成功
	 */
	public static final int UPLOAD_SUCCESS = 0;
	/**
	 * 文件上传失败
	 */
	public static final int UPLOAD_FAILT = -1;

	/********************************
	 * 基本参数配置（添加新用户时使用、基础权限检测时使用）
	 **************************************/
	public static final Map<String, Map<String, String[]>> basePerSetMap = new HashMap<>();
	static {
		/***************** 超级管理员权限 *****************/
		Map<String, String[]> superPerMap = new HashMap<>();
		basePerSetMap.put("superManager", superPerMap);

		/***************** 普通管理员权限 *****************/
		Map<String, String[]> normalPerMap = new HashMap<>();
		normalPerMap.put("系统设置", new String[] { "用户权限设置" });
		normalPerMap.put("用户管理", null);
		basePerSetMap.put("normalManager", normalPerMap);

		/***************** 代理用户权限 *****************/
		Map<String, String[]> agencyPerMap = new HashMap<>();
		agencyPerMap.put("用户管理", new String[] { "卡管理/会员卡管理/学生卡管理" });
		basePerSetMap.put("agencyMan", agencyPerMap);

		/***************** 专家用户权限 *****************/
		Map<String, String[]> expertPerMap = new HashMap<>();
		basePerSetMap.put("expertMan", expertPerMap);

		/***************** 学生会员用户权限 *****************/
		Map<String, String[]> studentPerMap = new HashMap<>();
		studentPerMap.put("院校信息", null);
		basePerSetMap.put("studentMan", studentPerMap);

		/***************** 系统必须的权限 *****************/
		Map<String, String[]> limitPerMap = new HashMap<>();
		// 管理员级别权限
		limitPerMap.put("系统设置", new String[] { "导航模块管理:toPermissionManager:管理程序所有模块权限，用于用户权限设置使用",
				"用户权限设置:toPermissionSet:为用户设置相关权限，用于用户导航菜单显示" });
		limitPerMap.put("用户管理",
				new String[] { "学生卡管理:toStudentCardManager:学生卡的添加与管理", "系统用户管理:toUserManager:非学生卡用户的管理" });
		// 学生卡级别权限
		limitPerMap.put("院校信息", new String[] { "院校查询:toSchoolRefer:查看院校信息" });
		// 代理级别权限
		// 专家级别权限
		basePerSetMap.put("limitPer", limitPerMap);
	}

}
