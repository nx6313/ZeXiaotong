package cn.gk.action.comm;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

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
import cn.gk.util.JSONSerializer;

/**
 * 学生Action
 * 
 * @author NGD
 */
@Controller("studentAction")
@Scope("prototype")
public class StudentAction extends BaseAction {
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
	public StudentCard studentCardIn;

	public StudentCard getStudentCardIn() {
		return studentCardIn;
	}

	public void setStudentCardIn(StudentCard studentCardIn) {
		this.studentCardIn = studentCardIn;
	}

	/**
	 * 跳转到学生会员卡管理
	 * 
	 * @return
	 */
	public String toStudentCardManager() {
		// 如果是代理商查看，获取该代理用户的学生卡数量信息
		// 如果是管理员查看，获取所有代理商数据，并默认获取第一个代理商的学生卡数量信息
		String defaultShowAgency = null;
		if (userInfo.getType() == 2) {
			// 代理商查看
			defaultShowAgency = userInfo.getId();
		} else if (userInfo.getType() == 0 || userInfo.getType() == 1) {
			// 管理员查看
			getRequest().setAttribute("seeStudentCardManagerUserType", "manager");
			List<UserInfo> agencyList = userInfoService.getUserListByParamesAjax(null, 2);
			if (ComFun.strNull(agencyList, true)) {
				getRequest().setAttribute("agencyList", agencyList);
				UserInfo agencyDefault = agencyList.get(0);
				defaultShowAgency = agencyDefault.getId();
			}
		}
		String sessionStuCardAgencyId = (String) getSession().getAttribute("studentCardManager_defaultAgencyId");
		if (ComFun.strNull(sessionStuCardAgencyId)) {
			defaultShowAgency = sessionStuCardAgencyId;
		} else {
			getSession().setAttribute("studentCardManager_defaultAgencyId", defaultShowAgency);
		}
		getRequest().setAttribute("defaultShowAgency", defaultShowAgency);
		// 获取代理商下的学生卡数据
		int allStudentCardNum = studentCardService.getPageCount(defaultShowAgency, null, false);
		int saleStudentCardNum = studentCardService.getPageCount(defaultShowAgency, 1, false);
		int unsaleStudentCardNum = studentCardService.getPageCount(defaultShowAgency, 0, false);
		int dongStudentCardNum = studentCardService.getPageCount(defaultShowAgency, 2, false);
		getRequest().setAttribute("allStudentCardNum", allStudentCardNum);
		getRequest().setAttribute("saleStudentCardNum", saleStudentCardNum);
		getRequest().setAttribute("unsaleStudentCardNum", unsaleStudentCardNum);
		getRequest().setAttribute("dongStudentCardNum", dongStudentCardNum);
		return SUCCESS;
	}

	/**
	 * 管理员登录，点击代理商选项卡按钮，获取对应代理商学生卡数据
	 * 
	 * @throws IOException
	 */
	public void getStudentCardManagerData() throws IOException {
		String agencyId = getRequest().getParameter("agencyId");
		getSession().setAttribute("studentCardManager_defaultAgencyId", agencyId);
		int allStudentCardNum = studentCardService.getPageCount(agencyId, null, false);
		int saleStudentCardNum = studentCardService.getPageCount(agencyId, 1, false);
		int unsaleStudentCardNum = studentCardService.getPageCount(agencyId, 0, false);
		int dongStudentCardNum = studentCardService.getPageCount(agencyId, 2, false);
		Map<String, List<Integer>> studentCardBaseDataMap = new HashMap<>();
		List<Integer> baseData = new ArrayList<>();
		baseData.add(allStudentCardNum);
		baseData.add(saleStudentCardNum);
		baseData.add(unsaleStudentCardNum);
		baseData.add(dongStudentCardNum);
		studentCardBaseDataMap.put(agencyId, baseData);
		out(JSONSerializer.serialize(studentCardBaseDataMap));
		out().flush();
		out().close();
	}

	/**
	 * 获取代理商学生卡分页数据
	 * 
	 * @throws IOException
	 */
	public void getStudentCardPageData() throws IOException {
		String agencyId = getRequest().getParameter("agencyId");
		String type = getRequest().getParameter("type");
		String currentPage = getRequest().getParameter("pageIndex");
		int searchTypePageIndex = 1;
		if (ComFun.strNull(type) && type.equals("-1")) {
			searchTypePageIndex = ComFun.getAboutPageIndex(getSession(), "studentCardManagerList_all", currentPage);
		} else if (ComFun.strNull(type) && type.equals("1,2")) {
			searchTypePageIndex = ComFun.getAboutPageIndex(getSession(), "studentCardManagerList_sale", currentPage);
		} else if (ComFun.strNull(type) && type.equals("0")) {
			searchTypePageIndex = ComFun.getAboutPageIndex(getSession(), "studentCardManagerList_unsale", currentPage);
		}
		Map<String, List<StudentCard>> studentCardDataMap = new HashMap<>();
		List<StudentCard> studentCards = studentCardService.getStudentPageListByAgencyId(agencyId, type,
				searchTypePageIndex);
		if (ComFun.strNull(type) && type.equals("-1")) {
			studentCardDataMap.put("all", studentCards);
		} else if (ComFun.strNull(type) && type.equals("1,2")) {
			studentCardDataMap.put("sale", studentCards);
		} else if (ComFun.strNull(type) && type.equals("0")) {
			studentCardDataMap.put("unsale", studentCards);
		}
		out(JSONSerializer.serialize(studentCardDataMap));
		out().flush();
		out().close();
	}

	/**
	 * 生成学生卡
	 * 
	 * @throws IOException
	 */
	public void doStudentCardAdd() throws IOException {
		String agencyId = getRequest().getParameter("agencyId");
		String createStudentCardNum = getRequest().getParameter("createStudentCardNum");
		for (int i = 0; i < Integer.parseInt(createStudentCardNum); i++) {
			StudentCard studentCard = new StudentCard();
			studentCard.setAboutAgencyId(agencyId);
			studentCard.setCardNum(ComFun.createStudentCardNum(15));
			studentCard.setPassWord(ComFun.createStudentCardNum(8));
			studentCard.setCardStatus(0);
			studentCard.setCreateInfo(userInfo.getId());
			studentCardService.save(studentCard);
		}
		out(Constants.AJAX_SUCCESS);
		out().flush();
		out().close();
	}

	/**
	 * 查询学生卡
	 * 
	 * @throws IOException
	 */
	public void searchStudentCardData() throws IOException {
		String searchVal = getRequest().getParameter("searchVal");
		List<StudentCard> studentCards = studentCardService.searchStudentPageList(searchVal);
		if (ComFun.strNull(studentCards, true)) {
			out(JSONSerializer.serialize(studentCards));
		} else {
			out(Constants.AJAX_NONE);
		}
		out().flush();
		out().close();
	}

	/**
	 * 跳转到学生卡激活
	 * 
	 * @return
	 */
	public String skipStudentCardActive() {
		String loginUserCardId = getRequest().getParameter("activeCardId");
		String cardNum = getRequest().getParameter("cardNum");
		getRequest().setAttribute("loginUserCardId", loginUserCardId);
		getRequest().setAttribute("userCardNum", cardNum);
		return SUCCESS;
	}

	/**
	 * 学生卡激活
	 * 
	 * @throws IOException
	 */
	public void doStudentCardActive() throws IOException {
		String activeCardId = getRequest().getParameter("activeCardId");
		StudentCard sCardActive = studentCardService.get(activeCardId);
		if (ComFun.strNull(sCardActive) && ComFun.strNull(studentCardIn)) {
			// 判断是否已经存在相同的手机号
			List<StudentCard> sList = studentCardService
					.getStudentCardListByPhoneNum(studentCardIn.getPhoneNum().trim());
			if (!ComFun.strNull(sList, true)) {
				sCardActive.setPassWord(studentCardIn.getPassWord().trim());
				sCardActive.setUserName(studentCardIn.getUserName().trim());
				sCardActive.setAccountNum(studentCardIn.getPhoneNum().trim()); // 帐号（先默认手机号为帐号）
				sCardActive.setBalance(new BigDecimal("0"));
				sCardActive.setPhoneNum(studentCardIn.getPhoneNum().trim());
				sCardActive.setFamilyType(studentCardIn.getFamilyType());
				sCardActive.setArea(studentCardIn.getArea().trim());
				sCardActive.setSchoolName(studentCardIn.getSchoolName().trim());
				sCardActive.setClassGrade(studentCardIn.getClassGrade().trim());
				sCardActive.setEmail(studentCardIn.getEmail().trim());
				sCardActive.setCardStatus(1);
				sCardActive.setCardActiveTime(new Date());
				sCardActive.setUpdateInfo(studentCardIn.getId());
				studentCardService.update(sCardActive);
				// 为该学生卡账户分配权限
				Map<String, String[]> basePerMap = ComFun.getUserBasePerByType(10);
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
								if (ComFun.strNull(perCsb.toString())) {
									String perCstr = perCsb.toString();
									perCstr = perCstr.substring(0, perCstr.length() - 1);
									UserPermission userPermission = new UserPermission();
									userPermission.setUserId(sCardActive.getId());
									userPermission.setPermissionPid(permissionP.getId());
									userPermission.setPermissionCid(perCstr);
									userPermission.setCreateInfo(studentCardIn.getId());
									userPermissionService.save(userPermission);
								}
							} else {
								UserPermission userPermission = new UserPermission();
								userPermission.setUserId(sCardActive.getId());
								userPermission.setPermissionPid(permissionP.getId());
								userPermission.setCreateInfo(studentCardIn.getId());
								userPermissionService.save(userPermission);
							}
						}
					}
				}
				// 获取该用户权限导航栏数据，并进行保存
				List<Object[]> userPerList = userPermissionService.getUserPermisByUserIdSort(sCardActive.getId());
				String navigationJsonStr = ComFun.getNavigationJsonByUserPermission(userPerList, permissionPService,
						permissionCService);
				setAttributeS(Constants.USER_NAVIGATION_SESSION, navigationJsonStr);
				setAttributeS(Constants.USER_SESSION_STUDENT_CARD, sCardActive);
				out(Constants.AJAX_SUCCESS);
			} else {
				// 存在该手机号，提示用户重新填写手机号进行绑定
				out(Constants.AJAX_EXIST);
			}
		} else {
			out(Constants.AJAX_NONE);
		}
		out().flush();
		out().close();
	}

}
