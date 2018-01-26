package cn.gk.action.comm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.fr.json.JSONException;
import com.fr.json.JSONObject;

import cn.gk.action.base.BaseAction;
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
import cn.gk.util.DateFormatUtil;
import cn.gk.util.JSONSerializer;

/**
 * 权限Action
 * 
 * @author NGD
 */
@Controller("permissionAction")
@Scope("prototype")
public class PermissionAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	@Resource
	private UserInfoService userInfoService;
	@Resource
	private PermissionPService permissionPService;
	@Resource
	private PermissionCService permissionCService;
	@Resource
	private UserPermissionService userPermissionService;

	private static Logger logger = Logger.getLogger(PermissionAction.class.getName());

	/**
	 * 跳转到权限管理
	 * 
	 * @return
	 */
	public String toPermissionManager() {
		// 获取权限数据
		Map<PermissionP, Integer> permissionData = new TreeMap<>();
		List<String> perPIdList = new ArrayList<>();
		// 先获取权限主表数据
		List<PermissionP> permissionPs = permissionPService.getPermissionPList(false);
		if (ComFun.strNull(permissionPs, true)) {
			for (PermissionP permissionP : permissionPs) {
				perPIdList.add(permissionP.getId());
				// 获取主权限对应的子权限集合
				// List<PermissionC> permissionCs =
				// permissionCService.getPermissionCListByPId(permissionP.getId(),
				// false);
				// if (!ComFun.strNull(permissionCs, true)) {
				// permissionCs = new ArrayList<>();
				// }
				permissionData.put(permissionP, 0);
			}
		}
		getRequest().setAttribute("perPIdList", perPIdList);
		getRequest().setAttribute("permissionData", permissionData);
		return SUCCESS;
	}

	/**
	 * 跳转到权限模块添加、修改
	 * 
	 * @return
	 */
	public String skipPermissionWrite() {
		String type = getRequest().getParameter("type");
		getRequest().setAttribute("type", type);
		if (type.equals("1") || type.equals("2")) {
			// 添加
			String perPId = getRequest().getParameter("perPId");
			getRequest().setAttribute("perPId", perPId);
		} else if (type.equals("3") || type.equals("4")) {
			// 修改
			String perPId = getRequest().getParameter("perPId");
			String perCId = getRequest().getParameter("perCId");
			String nameCanUpdateFlag = getRequest().getParameter("nameCanUpdateFlag");
			getRequest().setAttribute("nameCanUpdateFlag", nameCanUpdateFlag);
			if (type.equals("3")) {
				// 修改父级
				PermissionP permissionP = permissionPService.get(perPId);
				getRequest().setAttribute("updatePerP", permissionP);
			} else {
				// 修改子级
				PermissionC permissionC = permissionCService.get(perCId);
				getRequest().setAttribute("updatePerC", permissionC);
			}
		}
		return SUCCESS;
	}

	/**
	 * 执行权限模块添加、修改操作
	 * 
	 * @return
	 * @throws IOException
	 */
	public void doPermissionWrite() throws IOException {
		String result = Constants.AJAX_SUCCESS;
		String updatePerPid = getRequest().getParameter("updatePerPid");
		String updatePerCid = getRequest().getParameter("updatePerCid");
		String perPid = getRequest().getParameter("perPid");
		String perGrade = getRequest().getParameter("perGrade");
		String perName = getRequest().getParameter("perName").trim();
		String perIntro = getRequest().getParameter("perIntro");
		String perLink = getRequest().getParameter("perLink");
		String perStatus = getRequest().getParameter("perStatus"); // on null
		if (ComFun.strNull(updatePerPid) || ComFun.strNull(updatePerCid)) {
			if (ComFun.strNull(updatePerPid)) {
				PermissionP permissP = permissionPService.get(updatePerPid);
				permissP.setPermissionName(perName);
				permissP.setPermissionIntro(perIntro);
				if (ComFun.strNull(perStatus) && perStatus.equals("on")) {
					permissP.setPermissionStatus(1);
				} else {
					permissP.setPermissionStatus(0);
				}
				permissP.setUpdateInfo(userInfo.getId());
				permissionPService.update(permissP);
				logger.info(DateFormatUtil.dateToStr(new Date(), DateFormatUtil.TYPE) + " 修改父级权限模块：" + updatePerPid);
			} else {
				PermissionC permissC = permissionCService.get(updatePerCid);
				permissC.setPermissionName(perName);
				permissC.setPermissionIntro(perIntro);
				permissC.setPermissionLink(perLink);
				if (ComFun.strNull(perStatus) && perStatus.equals("on")) {
					permissC.setPermissionStatus(1);
				} else {
					permissC.setPermissionStatus(0);
				}
				permissC.setUpdateInfo(userInfo.getId());
				permissionCService.update(permissC);
				logger.info(DateFormatUtil.dateToStr(new Date(), DateFormatUtil.TYPE) + " 修改子级权限模块：" + updatePerCid);
			}
		} else {
			if (perGrade.equals("p")) {
				// 添加父级权限
				PermissionP permissP = new PermissionP();
				permissP.setPermissionName(perName);
				permissP.setPermissionIntro(perIntro);
				if (ComFun.strNull(perStatus) && perStatus.equals("on")) {
					permissP.setPermissionStatus(1);
				} else {
					permissP.setPermissionStatus(0);
				}
				permissP.setIndexs(permissionPService.getMaxIndexs() + 1);
				permissP.setCreateInfo(userInfo.getId());
				permissionPService.save(permissP);
				logger.info(DateFormatUtil.dateToStr(new Date(), DateFormatUtil.TYPE) + " 添加父级权限模块：" + perName);
			} else {
				// 添加子级权限
				PermissionC permissC = new PermissionC();
				permissC.setPerPid(perPid);
				permissC.setPermissionName(perName);
				permissC.setPermissionIntro(perIntro);
				permissC.setPermissionLink(perLink);
				if (ComFun.strNull(perStatus) && perStatus.equals("on")) {
					permissC.setPermissionStatus(1);
				} else {
					permissC.setPermissionStatus(0);
				}
				permissC.setCreateInfo(userInfo.getId());
				permissionCService.save(permissC);
				logger.info(DateFormatUtil.dateToStr(new Date(), DateFormatUtil.TYPE) + " 添加子级权限模块：" + perName);
			}
		}
		getResponse().setCharacterEncoding("UTF-8");
		out(result);
		out().flush();
		out().close();
	}

	/**
	 * 删除权限模块
	 * 
	 * @return
	 * @throws IOException
	 */
	public void permissionDelete() throws IOException {
		String result = Constants.AJAX_SUCCESS;
		String type = getRequest().getParameter("type");
		String id = getRequest().getParameter("id");
		if (type.equals("1")) {
			int deletePerPIndex = -1;
			// 删除父级权限
			PermissionP permissionP = permissionPService.get(id);
			deletePerPIndex = permissionP.getIndexs();
			permissionP.setIndexs(-1);
			permissionP.setDeleteInfo(userInfo.getId());
			permissionPService.update(permissionP);
			// 删除其下的所有子级权限
			List<PermissionC> permissionCs = permissionCService.getPermissionCListByPId(id, false);
			if (ComFun.strNull(permissionCs, true)) {
				for (PermissionC permissionC : permissionCs) {
					permissionC.setDeleteInfo(userInfo.getId());
					permissionCService.update(permissionC);
				}
			}
			// 删除父级权限后，对父级权限列表进行重新排序
			List<PermissionP> sortList = permissionPService.getPermissionPBySort(deletePerPIndex, -1);
			if (ComFun.strNull(sortList, true)) {
				for (PermissionP p : sortList) {
					p.setIndexs(p.getIndexs() - 1);
					p.setUpdateInfo(userInfo.getId());
					permissionPService.update(p);
				}
			}
		} else {
			// 删除子级权限
			PermissionC permissionC = permissionCService.get(id);
			permissionC.setDeleteInfo(userInfo.getId());
			permissionCService.update(permissionC);
		}
		getResponse().setCharacterEncoding("UTF-8");
		out(result);
		out().flush();
		out().close();
	}

	/**
	 * 将权限模块进行上下架
	 */
	public void permissionUpDown() {
		String type = getRequest().getParameter("type");
		String id = getRequest().getParameter("id");
		String pcType = id.split("_")[0];
		String pcId = id.split("_")[1];
		if (type.equals("1")) {
			// 上架
			if (pcType.equals("p")) {
				PermissionP permissP = permissionPService.get(pcId);
				permissP.setPermissionStatus(1);
				permissP.setUpdateInfo(userInfo.getId());
				permissionPService.update(permissP);
			} else {
				PermissionC permissC = permissionCService.get(pcId);
				permissC.setPermissionStatus(1);
				permissC.setUpdateInfo(userInfo.getId());
				permissionCService.update(permissC);
			}
		} else {
			// 下架
			if (pcType.equals("p")) {
				PermissionP permissP = permissionPService.get(pcId);
				permissP.setPermissionStatus(0);
				permissP.setUpdateInfo(userInfo.getId());
				permissionPService.update(permissP);
			} else {
				PermissionC permissC = permissionCService.get(pcId);
				permissC.setPermissionStatus(0);
				permissC.setUpdateInfo(userInfo.getId());
				permissionCService.update(permissC);
			}
		}
	}

	/**
	 * 根据父级权限Id获取子权限数据（提供页面AJAX调用）
	 * 
	 * @throws IOException
	 */
	public void getPerCList() throws IOException {
		String type = getRequest().getParameter("type");
		String aboutId = getRequest().getParameter("aboutId");
		String perPId = getRequest().getParameter("perPId").trim();
		PermissionP permissionP = permissionPService.get(perPId);
		boolean slipDisabledFlag = Boolean.valueOf(getRequest().getParameter("slipDisabledFlag").trim());
		List<PermissionC> permissionCs = permissionCService.getPermissionCListByPId(perPId, slipDisabledFlag);
		if (ComFun.strNull(permissionCs, true)) {
			Map<String, Object[]> permissionCsMap = new HashMap<>();
			// 0：子级Id、1：子级名称、2：子级说明、3：父级状态、4：子级状态、5：父级名称、6：子级链接、7：用户是否有该权限、8：子级下架数量
			int perCDownNum = 0;
			List<Object[]> perCArrData = new ArrayList<>();
			List<Object> perCData = null;
			for (PermissionC permissionC : permissionCs) {
				perCData = new ArrayList<>();
				if (permissionC.getPermissionStatus() == 0) {
					perCDownNum++;
				}
				boolean hasPerFlag = false;
				if (ComFun.strNull(type) && type.equals("user")) {
					UserPermission uPermission = userPermissionService.getByUserIdPerPid(aboutId, permissionP.getId(),
							permissionC.getId());
					if (ComFun.strNull(uPermission)) {
						hasPerFlag = true;
					}
				}
				perCData.add(permissionC.getId());
				perCData.add(permissionC.getPermissionName());
				perCData.add(
						ComFun.strNull(permissionC.getPermissionIntro()) ? permissionC.getPermissionIntro() : "null");
				perCData.add(permissionP.getPermissionStatus());
				perCData.add(permissionC.getPermissionStatus());
				perCData.add(permissionP.getPermissionName());
				perCData.add(
						ComFun.strNull(permissionC.getPermissionLink()) ? permissionC.getPermissionLink() : "null");
				perCData.add(hasPerFlag);
				perCArrData.add(perCData.toArray());
			}
			permissionCsMap.put(permissionP.getId(), new Object[] { perCDownNum, perCArrData });
			out(JSONSerializer.serialize(permissionCsMap));
		} else {
			out("");
		}
		out().flush();
		out().close();
	}

	/**
	 * 权限排序
	 * 
	 * @throws IOException
	 */
	public void sortPermission() throws IOException {
		String from = getRequest().getParameter("from");
		String to = getRequest().getParameter("to");
		// 如果向下改变顺序，被改变项直接改序号为目标值，其他的序号依次递减
		// 如果向上改变顺序，被改变项直接改序号为目标值，其他的序号依次递增
		List<PermissionP> movePList = permissionPService.getPermissionPBySort(Integer.parseInt(from), null);
		if (Integer.parseInt(from) > Integer.parseInt(to)) {
			// 递增
			List<PermissionP> pList = permissionPService.getPermissionPBySort(Integer.parseInt(to),
					Integer.parseInt(from) - 1);
			if (ComFun.strNull(pList, true)) {
				for (PermissionP p : pList) {
					p.setIndexs(p.getIndexs() + 1);
					p.setUpdateInfo(userInfo.getId());
					permissionPService.update(p);
				}
			}
		} else {
			// 递减
			List<PermissionP> pList = permissionPService.getPermissionPBySort(Integer.parseInt(from) + 1,
					Integer.parseInt(to));
			if (ComFun.strNull(pList, true)) {
				for (PermissionP p : pList) {
					p.setIndexs(p.getIndexs() - 1);
					p.setUpdateInfo(userInfo.getId());
					permissionPService.update(p);
				}
			}
		}
		if (ComFun.strNull(movePList, true)) {
			PermissionP p = movePList.get(0);
			p.setIndexs(Integer.parseInt(to));
			p.setUpdateInfo(userInfo.getId());
			permissionPService.update(p);
		}
		out(Constants.AJAX_SUCCESS);
		out().flush();
		out().close();
	}

	/**
	 * 跳转到用户权限设置
	 * 
	 * @return
	 * @throws JSONException
	 */
	public String toPermissionSet() throws JSONException {
		// 获取系统所有人员树列表数据
		JSONObject jsob = userInfoService.getUserTree();
		getRequest().setAttribute("userTreeData", jsob);
		// 获取父级权限集合
		List<PermissionP> permissionPs = permissionPService.getPermissionPList(true);
		getRequest().setAttribute("permissionPs", permissionPs);
		return SUCCESS;
	}

	/**
	 * 获取用户或者用户组的权限数据(主表数据部分)
	 * 
	 * @throws IOException
	 */
	public void getUserOrGroupPerData() throws IOException {
		String type = getRequest().getParameter("type");
		String aboutId = getRequest().getParameter("aboutId");
		List<Object[]> permissionPDatas = permissionPService.getPermissionPAndStateList(type, aboutId);
		if (ComFun.strNull(permissionPDatas, true)) {
			out(JSONSerializer.serialize(permissionPDatas));
		} else {
			out("");
		}
		out().flush();
		out().close();
	}

	/**
	 * 修改设置用户或组的权限
	 * 
	 * @throws IOException
	 */
	public void changeGroupOrUserPer() throws IOException {
		String typeAndAboutId = getRequest().getParameter("aboutId");
		String type = typeAndAboutId.split("#")[0];
		String aboutId = typeAndAboutId.split("#")[1];
		String[] groupPerSetValues = getRequest().getParameterValues("groupPerSetValues");
		String[] userPerSetValues = getRequest().getParameterValues("userPerSetValues");
		if (type.equals("group")) {
			List<UserInfo> uInfos = userInfoService.getUserListByParamesAjax(null, Integer.parseInt(aboutId));
			System.out.println(uInfos.size());
			if (ComFun.strNull(uInfos, true)) {
				for (UserInfo uInfo : uInfos) {
					setUserGroupPer(uInfo.getId(), groupPerSetValues);
				}
			}
		}
		if (type.equals("user")) {
			setUserGroupPer(aboutId.trim(), userPerSetValues);
		}
		out(Constants.AJAX_SUCCESS);
		out().flush();
		out().close();
	}

	/**
	 * 保存用户权限
	 * 
	 * @param userId
	 * @param userPerSetValues
	 */
	private void setUserGroupPer(String userId, String[] userPerSetValues) {
		// 删除用户之前的权限数据
		List<UserPermission> uPerList = userPermissionService.getUserPermisByUserId(userId);
		if (ComFun.strNull(uPerList, true)) {
			for (UserPermission uPermission : uPerList) {
				userPermissionService.delete(uPermission.getId());
			}
		}
		if(ComFun.strNull(userPerSetValues, true)) {
			for (String userPerSet : userPerSetValues) {
				String[] perDataArr = userPerSet.split("#");
				String perPId = perDataArr[0];
				boolean perCAllSelectedFlag = Boolean.parseBoolean(perDataArr[1]);
				String selectedPerCId = perDataArr[2];
				if (perCAllSelectedFlag) {
					// 仅保持pId
					UserPermission userPermission = new UserPermission();
					userPermission.setUserId(userId);
					userPermission.setPermissionPid(perPId);
					userPermission.setCreateInfo(userInfo.getId());
					userPermissionService.save(userPermission);
				} else {
					UserPermission userPermission = new UserPermission();
					userPermission.setUserId(userId);
					userPermission.setPermissionPid(perPId);
					userPermission.setPermissionCid(selectedPerCId);
					userPermission.setCreateInfo(userInfo.getId());
					userPermissionService.save(userPermission);
				}
			}
		}
	}

}
