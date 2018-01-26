package cn.gk.action.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import cn.gk.model.comm.StudentCard;
import cn.gk.model.comm.UserInfo;
import cn.gk.util.ComFun;
import cn.gk.util.Constants;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * 拦截器
 */
public class LoginInterceptor extends MethodFilterInterceptor {

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(LoginInterceptor.class.getName());

	/**
	 * 拦截器，用户为空，返回到登录页面
	 */
	@Override
	protected String doIntercept(ActionInvocation actionInvocation)
			throws Exception {
		String actionName = actionInvocation.getInvocationContext().getName();
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		StudentCard sCard = (StudentCard) request.getSession().getAttribute(Constants.USER_SESSION_STUDENT_CARD);
		UserInfo user = (UserInfo) request.getSession().getAttribute(Constants.USER_SESSION);
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("utf-8");

		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);

		if (sCard != null || user != null) {
			if(sCard != null){
				logger.info(ComFun.getStudentCardInfo(sCard) + " === action name is " + actionName);
			}
			if(user != null){
				logger.info(ComFun.getUserInfo(user) + " === action name is " + actionName);
			}
			if(actionName.startsWith("to")){
				ServletActionContext.getRequest().getSession().setAttribute(Constants.DEFAULT_ACTION_SESSION, actionName);
			}
			if(actionName.equals("skipLogin")){
				return "hasLogin";
			}
			return actionInvocation.invoke();
		} else {
			if(!actionName.equals("skipLogin")){
				return Action.LOGIN;
			}else{
				return actionInvocation.invoke();
			}
		}
	}

}
