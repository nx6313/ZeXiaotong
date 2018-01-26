package cn.gk.action.base;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cn.gk.model.comm.StudentCard;
import cn.gk.model.comm.UserInfo;
import cn.gk.util.ComFun;
import cn.gk.util.Constants;

import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {
	private static final long serialVersionUID = 1L;
	public StudentCard studentCardInfo = (StudentCard) getSession().getAttribute(Constants.USER_SESSION_STUDENT_CARD);
	public UserInfo userInfo = (UserInfo) getSession().getAttribute(Constants.USER_SESSION);
	public String userNavigationInfo = (String) getSession().getAttribute(Constants.USER_NAVIGATION_SESSION);
	public String IS_MANAGER_LOGIN = "isManagerLogin";

	public static HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	public static HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	public static HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}

	public static PrintWriter out() throws IOException {
		getResponse().setCharacterEncoding("UTF-8");
		return getResponse().getWriter();
	}

	public static void out(Object msg) {
		try {
			out().print(msg);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out().flush();
				out().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static String getParameter(String paramName) {
		String value = getRequest().getParameter(paramName);
		return value;
	}

	public static String[] getParameterValues(String paramName) {
		String[] value = getRequest().getParameterValues(paramName);
		return value;
	}

	public static void setAttribute(String key, Object value) {
		getRequest().setAttribute(key, value);
	}

	public static void setAttributeS(String key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static Object getAttributeS(String key) {
		return getSession().getAttribute(key);
	}
	
	public static String getLoginUserId() {
		UserInfo userInfo = (UserInfo) getSession().getAttribute(Constants.USER_SESSION);
		StudentCard studentCardInfo = (StudentCard) getSession().getAttribute(Constants.USER_SESSION_STUDENT_CARD);
		if(ComFun.strNull(userInfo)) {
			return userInfo.getId();
		}
		if(ComFun.strNull(studentCardInfo)) {
			return studentCardInfo.getId();
		}
		return null;
	}
	
	public static boolean isManagerLoginFlag() {
		UserInfo sysUserInfo = (UserInfo) getSession().getAttribute(Constants.USER_SESSION);
		if(ComFun.strNull(sysUserInfo) && (sysUserInfo.getType() == 0 || sysUserInfo.getType() == 1)) {
			return true;
		}
		return false;
	}

}
