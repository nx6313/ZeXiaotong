package cn.gk.action.comm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.gk.action.base.BaseAction;
import cn.gk.model.comm.FileRecord;
import cn.gk.model.comm.PermissionC;
import cn.gk.model.comm.PermissionP;
import cn.gk.model.student.Academy;
import cn.gk.service.comm.FileRecordService;
import cn.gk.service.comm.PermissionCService;
import cn.gk.service.comm.PermissionPService;
import cn.gk.service.comm.UserInfoService;
import cn.gk.service.comm.UserPermissionService;
import cn.gk.service.school.SchoolReferService;
import cn.gk.util.ComFun;
import cn.gk.util.Constants;
import cn.gk.util.ExcelDataUtil;
import cn.gk.util.JSONSerializer;

/**
 * 公共方法Action
 * 
 * @author NGD
 */
@Controller("commAction")
@Scope("prototype")
public class CommAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	@Resource
	private UserInfoService userInfoService;
	@Resource
	private UserPermissionService userPermissionService;
	@Resource
	private PermissionPService permissionPService;
	@Resource
	private PermissionCService permissionCService;
	@Resource
	private FileRecordService fileRecordService;
	@Resource
	private SchoolReferService schoolReferService;

	private static Logger logger = Logger.getLogger(CommAction.class.getName());

	public File uploadFile;
	public String uploadFileFileName;
	public String uploadFileContentType;

	public File getUploadFile() {
		return uploadFile;
	}

	public void setUploadFile(File uploadFile) {
		this.uploadFile = uploadFile;
	}

	public String getUploadFileFileName() {
		return uploadFileFileName;
	}

	public void setUploadFileFileName(String uploadFileFileName) {
		this.uploadFileFileName = uploadFileFileName;
	}

	public String getUploadFileContentType() {
		return uploadFileContentType;
	}

	public void setUploadFileContentType(String uploadFileContentType) {
		this.uploadFileContentType = uploadFileContentType;
	}

	/**
	 * 跳转到指定的公共页面（404、no_permission）
	 * 
	 * @return
	 */
	public String toCommPage() {
		String type = getRequest().getParameter("type");
		String page = getRequest().getParameter("page");
		if (ComFun.strNull(type)) {
			if (type.equals("cardActive")) {
				logger.error("学生卡激活：" + page);
			}
		} else {
			logger.error("访问错误：【type：" + type + "】【page：" + page + "】");
		}
		return page;
	}

	/**
	 * 超級管理員登錄，進行基礎權限補全操作
	 * 
	 * @throws IOException
	 */
	public void fillLimitPer() throws IOException {
		int allLimitsCount = 0;
		int hasLimitsCount = 0;
		Map<String, Map<String, String[]>> basePerSetMap = Constants.basePerSetMap;
		Map<String, String[]> limitPerMap = basePerSetMap.get("limitPer");
		for (Map.Entry<String, String[]> map : limitPerMap.entrySet()) {
			allLimitsCount += map.getValue().length;
			PermissionP p = permissionPService.getPermisPByName(map.getKey());
			if (ComFun.strNull(p)) {
				hasLimitsCount += map.getValue().length;
			} else {
				// 進行權限補全
				// 添加父级权限
				PermissionP permissP = new PermissionP();
				permissP.setPermissionName(map.getKey());
				permissP.setPermissionStatus(1);
				permissP.setIndexs(permissionPService.getMaxIndexs() + 1);
				permissP.setCreateInfo(userInfo.getId());
				permissionPService.save(permissP);
				// 添加子级权限
				for (String limitPerC : map.getValue()) {
					String[] limitPerCArr = limitPerC.split(":");
					PermissionC permissC = new PermissionC();
					permissC.setPerPid(permissP.getId());
					permissC.setPermissionName(limitPerCArr[0]);
					permissC.setPermissionLink(limitPerCArr[1]);
					if (ComFun.strNull(limitPerCArr[2])) {
						permissC.setPermissionIntro(limitPerCArr[2]);
					}
					permissC.setPermissionStatus(1);
					permissC.setCreateInfo(userInfo.getId());
					permissionCService.save(permissC);
				}
				hasLimitsCount += map.getValue().length;
			}
		}
		List<Integer> limitCountList = new ArrayList<>();
		limitCountList.add(hasLimitsCount);
		limitCountList.add(allLimitsCount);
		out().write(JSONSerializer.serialize(limitCountList));
		out().close();
		out().flush();
	}

	/**
	 * 文件上传
	 * 
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	public void uploadExcelFile() throws IOException {
		getResponse().setHeader("Content-Type", "text/html");
		Map<String, Object> resultData = new HashMap<>();
		String doType = getRequest().getParameter("doType");
		if (doType.equals("img")) {
			FileRecord fileRecord = ComFun.saveUploadImg(getRequest(), uploadFile, uploadFileFileName,
					uploadFileContentType, resultData);
			if (ComFun.strNull(fileRecord)) {
				fileRecord.setCreateInfo(getLoginUserId());
				fileRecordService.save(fileRecord);
				resultData.put("fileRecordId", fileRecord.getId());
			}
		} else if (doType.equals("excel")) {
			String excelType = getRequest().getParameter("excelType");
			if (excelType.equals("schoolInfoExcel")) {
				List<Academy> academyExcelList = (List<Academy>) ExcelDataUtil.readExcelData(uploadFile, uploadFileFileName, 1,
						Academy.class, getRequest(), fileRecordService, getLoginUserId());
				if (ComFun.strNull(academyExcelList, true)) {
					for (Academy academy : academyExcelList) {
						academy.setCreateInfo(getLoginUserId());
						schoolReferService.save(academy);
					}
					resultData.put("code", Constants.UPLOAD_SUCCESS);
					resultData.put("msg", "成功导入数据 " + academyExcelList.size() + " 条");
				}else {
					resultData.put("code", Constants.UPLOAD_FAILT);
					resultData.put("msg", "导入数据 0 条");
				}
			}
		}
		if (!resultData.containsKey("code")) {
			resultData.put("code", Constants.UPLOAD_SUCCESS);
		}
		out().write(JSONSerializer.serialize(resultData));
		out().close();
		out().flush();
	}

}
