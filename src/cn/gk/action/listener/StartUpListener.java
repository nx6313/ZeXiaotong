package cn.gk.action.listener;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.gk.model.comm.PermissionC;
import cn.gk.model.comm.PermissionP;
import cn.gk.model.comm.UserInfo;
import cn.gk.model.comm.UserPermission;
import cn.gk.service.comm.PermissionCService;
import cn.gk.service.comm.PermissionPService;
import cn.gk.service.comm.UserInfoService;
import cn.gk.service.comm.UserPermissionService;
import cn.gk.util.ComFun;
import cn.gk.util.Constants;
import cn.gk.util.MD5andKL;

@Component
public class StartUpListener implements ServletContextListener {
	private ServletContext context = null;
	private WebApplicationContext springContext = null;
	private UserInfoService userInfoService = null;
	private PermissionPService permissionPService = null;
	private PermissionCService permissionCService = null;
	private UserPermissionService userPermissionService = null;

	/**
	 * 根路径
	 */
	public static String rootAdress;

	public void contextDestroyed(ServletContextEvent sce) {
		context = null;
		springContext = null;
	}

	public void contextInitialized(ServletContextEvent sce) {
		if (context == null) {
			context = sce.getServletContext();
		}
		rootAdress = context.getRealPath(File.separator);
		System.setProperty("web_home", rootAdress);
		if (springContext == null) {
			springContext = WebApplicationContextUtils.getWebApplicationContext(context);
		}
		if (springContext != null) {
			// 获取Service对象
			userInfoService = (UserInfoService) springContext.getBean("userInfoService");
			permissionPService = (PermissionPService) springContext.getBean("permissionPService");
			permissionCService = (PermissionCService) springContext.getBean("permissionCService");
			userPermissionService = (UserPermissionService) springContext.getBean("userPermissionService");

			// 执行初始化任务
			initSuperManager();
			initSuperStartPermission();
		} else {
			System.out.println("----StartUpListener----获取应用程序上下文失败!");
		}
	}

	/**
	 * 在运行项目时，检测是否有超级管理员，没有则初始化超级管理员用户
	 */
	private void initSuperManager() {
		// 检测是否有超级管理员账号，没有的话为程序初始化超级管理员账号（账户登录名：Admin、账户名：超级管理员）
		List<UserInfo> superUserList = userInfoService.getUserListByParamesAjax(null, 0);
		if (!ComFun.strNull(superUserList, true)) {
			UserInfo superUserInfo = new UserInfo();
			superUserInfo.setUserName("Admin");
			superUserInfo.setType(0);
			superUserInfo.setRegisterDate(new Date());
			superUserInfo.setPass(MD5andKL.MD5("gkBackManager"));
			superUserInfo.setIndexs(userInfoService.getMaxIndexs() + 1);
			superUserInfo.setState(Constants.STATE_ADD);
			superUserInfo.setCreateId("系统初始化自动生成");
			superUserInfo.setCreateDate(new Date());
			userInfoService.save(superUserInfo);
		}
	}

	/**
	 * 在运行项目时，检测是否有 【 系统设置 】 及以下的 【 导航设置 】 模块权限，没有则进行初始化数据添加
	 */
	private void initSuperStartPermission() {
		PermissionP permissionP = permissionPService.getPermisPByName("系统设置");
		if (!ComFun.strNull(permissionP)) {
			// 添加 【 系统设置 】 父级权限
			PermissionP permissP = new PermissionP();
			permissP.setPermissionName("系统设置");
			permissP.setPermissionIntro("设置系统需要的所有参数");
			permissP.setPermissionStatus(1);
			permissP.setIndexs(permissionPService.getMaxIndexs() + 1);
			permissP.setCreateInfo("系统初始化自动生成");
			permissionPService.save(permissP);
			// 添加 【 导航设置 】 子级权限
			PermissionC permissC1 = new PermissionC();
			permissC1.setPerPid(permissP.getId());
			permissC1.setPermissionName("导航模块管理");
			permissC1.setPermissionIntro("管理程序所有模块权限，用于用户权限设置使用");
			permissC1.setPermissionStatus(1);
			permissC1.setPermissionLink("toPermissionManager");
			permissC1.setCreateInfo("系统初始化自动生成");
			permissionCService.save(permissC1);
			// 添加 【 用户权限设置 】 子级权限
			PermissionC permissC2 = new PermissionC();
			permissC2.setPerPid(permissP.getId());
			permissC2.setPermissionName("用户权限设置");
			permissC2.setPermissionIntro("为用户设置相关权限，用于用户导航菜单显示");
			permissC2.setPermissionStatus(1);
			permissC2.setPermissionLink("toPermissionSet");
			permissC2.setCreateInfo("系统初始化自动生成");
			permissionCService.save(permissC2);
		}
		// 在超级管理员没有任何权限的时候，初始化所有权限（用于程序第一次启动时）
		List<UserInfo> superUserList = userInfoService.getUserListByParamesAjax(null, 0);
		if (ComFun.strNull(superUserList, true)) {
			List<Object[]> userPerList = userPermissionService
					.getUserPermisByUserIdSort(superUserList.get(0).getId());
			if (!ComFun.strNull(userPerList, true)) {
				List<PermissionP> permissionPs = permissionPService.getPermissionPList(true);
				if (ComFun.strNull(permissionPs, true)) {
					for (PermissionP p : permissionPs) {
						UserPermission userPermission = new UserPermission();
						userPermission.setUserId(superUserList.get(0).getId());
						userPermission.setPermissionPid(p.getId());
						userPermission.setCreateInfo("系统初始化自动生成");
						userPermissionService.save(userPermission);
					}
				}
			}
		}
	}

}
