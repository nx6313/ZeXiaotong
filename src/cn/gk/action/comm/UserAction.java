package cn.gk.action.comm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.fr.json.JSONException;
import com.fr.json.JSONObject;

import cn.gk.action.base.BaseAction;
import cn.gk.model.comm.PermissionC;
import cn.gk.model.comm.PermissionP;
import cn.gk.model.comm.StudentCard;
import cn.gk.model.comm.UserInfo;
import cn.gk.model.comm.UserPermission;
import cn.gk.service.comm.PermissionCService;
import cn.gk.service.comm.PermissionPService;
import cn.gk.service.comm.StudentCardService;
import cn.gk.service.comm.UserInfoService;
import cn.gk.service.comm.UserPermissionService;
import cn.gk.util.ComFun;
import cn.gk.util.Constants;
import cn.gk.util.DateFormatUtil;
import cn.gk.util.JSONSerializer;
import cn.gk.util.MD5andKL;

/**
 * 用户Action、包含用户登录、用户退出等
 * 
 * @author NGD
 */
@Controller("userAction")
@Scope("prototype")
public class UserAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	@Resource
	private UserInfoService userInfoService;
	@Resource
	private StudentCardService studentCardService;
	@Resource
	private UserPermissionService userPermissionService;
	@Resource
	private PermissionPService permissionPService;
	@Resource
	private PermissionCService permissionCService;

	private static Logger logger = Logger.getLogger(UserAction.class.getName());
	public UserInfo uInfo;

	public UserInfo getuInfo() {
		return uInfo;
	}

	public void setuInfo(UserInfo uInfo) {
		this.uInfo = uInfo;
	}

	/**
	 * 跳转到用户登录
	 * 
	 * @return
	 */
	public String skipLogin() {
		// 获取记住密码Session
		String rememberLoginName = null;
		String rememberPwd = (String) getSession().getAttribute("rememberPwd");
		if (ComFun.strNull(rememberPwd) && rememberPwd.equals("true")) {
			// 获取记住的用户登录账号
			rememberLoginName = (String) getSession().getAttribute("rememberLoginName");
		}
		getRequest().setAttribute("rememberLoginName", rememberLoginName);
		return SUCCESS;
	}

	/**
	 * 用户登录
	 * 
	 * @return
	 * @throws IOException
	 */
	public void login() throws IOException {
		String result = "";
		String loginCardNumber = getRequest().getParameter("userID");
		String loginCardPwd = getRequest().getParameter("password");
		String loginRemember = getRequest().getParameter("remember");
		// 开始执行登录验证操作
		// 会员卡用户验证
		StudentCard sCard = studentCardService.getStudentCardInfoLoginNameAndPassword(loginCardNumber, loginCardPwd, true);
		if(sCard != null){
			logger.error(ComFun.getStudentCardInfo(sCard) + "[登录]" + "========== action name is userLogin");
			// 记住账号操作
			if (ComFun.strNull(loginRemember) && loginRemember.equals("on")) {
				getSession().setAttribute("rememberPwd", "true");
				getSession().setAttribute("rememberLoginName", loginCardNumber);
			} else {
				getSession().removeAttribute("rememberPwd");
				getSession().removeAttribute("rememberLoginName");
			}
			// 获取用户权限信息并保存至程序缓存
			List<Object[]> userPerList = userPermissionService.getUserPermisByUserIdSort(sCard.getId());
			String navigationJsonStr = ComFun.getNavigationJsonByUserPermission(userPerList, permissionPService,
					permissionCService);
			setAttributeS(Constants.USER_NAVIGATION_SESSION, navigationJsonStr);
			setAttributeS(Constants.USER_SESSION_STUDENT_CARD, sCard);
			result = Constants.AJAX_SUCCESS;
		}else{
			// 非会员卡用户验证
			UserInfo user = userInfoService.getUserInfoLoginNameAndPassword(loginCardNumber, MD5andKL.MD5(loginCardPwd), true);
			if (user != null) {
				logger.error(ComFun.getUserInfo(user) + "[登录]" + "========== action name is userLogin");
				// 记住账号操作
				if (ComFun.strNull(loginRemember) && loginRemember.equals("on")) {
					getSession().setAttribute("rememberPwd", "true");
					getSession().setAttribute("rememberLoginName", loginCardNumber);
				} else {
					getSession().removeAttribute("rememberPwd");
					getSession().removeAttribute("rememberLoginName");
				}
				// 获取用户权限信息并保存至程序缓存
				List<Object[]> userPerList = userPermissionService.getUserPermisByUserIdSort(user.getId());
				String navigationJsonStr = ComFun.getNavigationJsonByUserPermission(userPerList, permissionPService,
						permissionCService);
				setAttributeS(Constants.USER_NAVIGATION_SESSION, navigationJsonStr);
				setAttributeS(Constants.USER_SESSION, user);
				result = Constants.AJAX_SUCCESS;
			} else {
				// 没有该用户（账号或者密码错误）
				result = NONE;
			}
		}
		getResponse().setCharacterEncoding("UTF-8");
		out(result);
		out().flush();
		out().close();
	}

	/**
	 * 退出登录
	 * 
	 * @return
	 * @throws IOException
	 */
	public void exitLogin() {
		// 清空session中信息
		getSession().removeAttribute(Constants.USER_SESSION);
		getSession().removeAttribute(Constants.USER_SESSION_STUDENT_CARD);
		getSession().removeAttribute(Constants.DEFAULT_ACTION_SESSION);
		getSession().removeAttribute("studentCardManager_defaultAgencyId");
	}

	/**
	 * 清除用户默认Action Session
	 * 
	 * @throws IOException
	 */
	public void clearDefaultAction() throws IOException {
		getSession().removeAttribute(Constants.DEFAULT_ACTION_SESSION);
		out(Constants.AJAX_SUCCESS);
		out().flush();
		out().close();
	}

	/**
	 * 跳转到程序首页
	 * 
	 * @return
	 */
	public String skipIndex() {
		int userType = -1;
		if(ComFun.strNull(userInfo)){
			userType = userInfo.getType();
			getRequest().setAttribute("loginUserName", userInfo.getUserName());
			getRequest().setAttribute("loginUserType", userInfo.getType());
		}else{
			userType = 10;
			getRequest().setAttribute("loginUserCardId", studentCardInfo.getId());
			getRequest().setAttribute("loginUserName", studentCardInfo.getUserName());
			getRequest().setAttribute("loginUserType", 10);
			getRequest().setAttribute("loginUserCardNum", studentCardInfo.getCardNum());
			getRequest().setAttribute("loginUserCardLevel", studentCardInfo.getCardLevel());
		}
		// 判断当前时间是否已经过了高考（xxxx年6月7日），没过传当前年份值，过了传当前年份值+1
		// 7、8、9三天考试
		Date currentDate = new Date();
		int currentYear = DateFormatUtil.getYear(currentDate);
		if (DateFormatUtil.compareDate(DateFormatUtil.dateToStr(currentDate, DateFormatUtil.MMDD), "06-07",
				DateFormatUtil.MMDD) >= 0
				&& DateFormatUtil.compareDate(DateFormatUtil.dateToStr(currentDate, DateFormatUtil.MMDD), "06-09",
						DateFormatUtil.MMDD) <= 0) {
			if (DateFormatUtil.getDay(currentDate) == 7) {
				currentYear = 1;
			} else if (DateFormatUtil.getDay(currentDate) == 8) {
				currentYear = 2;
			} else if (DateFormatUtil.getDay(currentDate) == 9) {
				currentYear = 3;
			}
		} else if (DateFormatUtil.compareDate(DateFormatUtil.dateToStr(currentDate, DateFormatUtil.MMDD), "06-09",
				DateFormatUtil.MMDD) > 0) {
			currentYear += 1;
		}
		getRequest().setAttribute("gkYear", currentYear);
		// 根据用户权限设置，生成该用户导航栏 JSON 数据
		String pageDefaultAction = ComFun.getUserDefaultPage(getSession(), userType, studentCardInfo);
		getRequest().setAttribute("pageDefaultAction", pageDefaultAction);
		if (userType == 0) {
			// 超级管理员登陆
			getRequest().setAttribute("superLoginFlag", true);
		} else {
			getRequest().setAttribute("superLoginFlag", false);
		}
		getRequest().setAttribute("navigationJson", userNavigationInfo);
		// 获取系统必须权限完善度
		int allLimitsCount = 0;
		int hasLimitsCount = 0;
		Map<String, Map<String, String[]>> basePerSetMap = Constants.basePerSetMap;
		Map<String, String[]> limitPerMap = basePerSetMap.get("limitPer");
		for(Map.Entry<String, String[]> map : limitPerMap.entrySet()) {
			allLimitsCount += map.getValue().length;
			PermissionP p = permissionPService.getPermisPByName(map.getKey());
			if(ComFun.strNull(p)) {
				hasLimitsCount += map.getValue().length;
			}
		}
		getRequest().setAttribute("allLimitsCount", allLimitsCount);
		getRequest().setAttribute("hasLimitsCount", hasLimitsCount);
		return SUCCESS;
	}

	/**
	 * 跳转到用户管理
	 * 
	 * @return
	 */
	public String toUserManager() {
		// 获取系统所有人员树列表数据
		JSONObject jsob = userInfoService.getUserTree();
		getRequest().setAttribute("userTreeData", jsob);
		return SUCCESS;
	}

	/**
	 * 查询获取用户列表或者用户信息
	 * 
	 * @throws IOException
	 * @throws JSONException
	 */
	public void searchUserInfoOrList() throws IOException, JSONException {
		String type = getRequest().getParameter("type");
		String aboutId = getRequest().getParameter("aboutId");
		String currentPage = getRequest().getParameter("currentPage");
		List<UserInfo> uList = new ArrayList<>();
		JSONObject paramesJson = new JSONObject();
		Map<String, Object> uDataMap = new HashMap<>();
		if (type.equals("group")) {
			// 分页查询，先获取第一页的数据
			int pageIndex = ComFun.getAboutPageIndex(getSession(), "userManagerList_" + aboutId, currentPage);
			paramesJson.put("pageIndex", pageIndex);
			uList = userInfoService.getUserListByParamesAjax(paramesJson, Integer.parseInt(aboutId));
			int dataCount = userInfoService.getAllCount(Integer.parseInt(aboutId));
			uDataMap.put("dataCount", dataCount);
			uDataMap.put("currentPage", pageIndex);
		} else {
			if (type.equals("user")) {
				paramesJson.put("userId", aboutId);
			} else {
				paramesJson.put("userSearch", aboutId);
			}
			uList = userInfoService.getUserListByParamesAjax(paramesJson, -1);
		}
		if (userInfo.getType() == 0) {
			// 超级管理员登陆
			uDataMap.put("superLoginFlag", true);
		} else {
			uDataMap.put("superLoginFlag", false);
		}
		uDataMap.put("pageSize", Constants.DEFAULT_PAGE_SIZE);
		uDataMap.put("child", uList);
		out(JSONSerializer.serialize(uDataMap));
		out().flush();
		out().close();
	}

	/**
	 * 跳转到添加用户
	 * 
	 * @return
	 */
	public String skipUserAdd() {
		String aboutId = getRequest().getParameter("aboutId");
		getRequest().setAttribute("addUserType", aboutId);
		return SUCCESS;
	}

	/**
	 * 执行添加用户操作
	 * 
	 * @throws IOException
	 */
	public void doUserAdd() throws IOException {
		// 判断用户是否已经存在(用户名和手机号)
		uInfo.setPass(Constants.DEFAULT_PWD);
		uInfo.setIndexs(userInfoService.getMaxIndexs() + 1);
		uInfo.setRegisterDate(new Date());
		uInfo.setCreateInfo(userInfo.getId());
		userInfoService.save(uInfo);
		// 为该用户设置对应权限
		Map<String, String[]> basePerMap = ComFun.getUserBasePerByType(uInfo.getType());
		if (ComFun.strNull(basePerMap, true)) {
			for (Map.Entry<String, String[]> map : basePerMap.entrySet()) {
				PermissionP permissionP = permissionPService.getPermisPByName(map.getKey());
				if (ComFun.strNull(permissionP)) {
					if (ComFun.strNull(map.getValue(), true)) {
						StringBuilder perCsb = new StringBuilder("");
						for (String perC : map.getValue()) {
							PermissionC pC = permissionCService.getPermisCByName(perC);
							if (ComFun.strNull(pC)) {
								perCsb.append(pC.getId());
								perCsb.append(",");
							}
						}
						if(ComFun.strNull(perCsb.toString())){
							String perCstr = perCsb.toString();
							perCstr = perCstr.substring(0, perCstr.length() - 1);
							UserPermission userPermission = new UserPermission();
							userPermission.setUserId(uInfo.getId());
							userPermission.setPermissionPid(permissionP.getId());
							userPermission.setPermissionCid(perCstr);
							userPermission.setCreateInfo(userInfo.getId());
							userPermissionService.save(userPermission);
						}
					} else {
						UserPermission userPermission = new UserPermission();
						userPermission.setUserId(uInfo.getId());
						userPermission.setPermissionPid(permissionP.getId());
						userPermission.setCreateInfo(userInfo.getId());
						userPermissionService.save(userPermission);
					}
				}
			}
		}
		out(Constants.AJAX_SUCCESS);
		out().flush();
		out().close();
	}

	/**
	 * 删除用户
	 * 
	 * @throws IOException
	 */
	public void deleteUser() throws IOException {
		String aboutId = getRequest().getParameter("aboutId");
		String type = getRequest().getParameter("type");
		if (ComFun.strNull(type) && type.equals("all")) {
			// 清空指定用户组下的所有用户
			List<UserInfo> deleteUsers = userInfoService.getUserListByParamesAjax(null, Integer.parseInt(aboutId));
			if (ComFun.strNull(deleteUsers, true)) {
				for (UserInfo deleteUser : deleteUsers) {
					deleteUser.setDeleteInfo(userInfo.getId());
					deleteUser.setIndexs(-1);
					userInfoService.update(deleteUser);
				}
			}
		} else {
			// 删除指定用户
			UserInfo deleteUser = userInfoService.get(aboutId);
			if (ComFun.strNull(deleteUser)) {
				deleteUser.setDeleteInfo(userInfo.getId());
				deleteUser.setIndexs(-1);
				userInfoService.update(deleteUser);
			}
		}
		out(Constants.AJAX_SUCCESS);
		out().flush();
		out().close();
	}

}
