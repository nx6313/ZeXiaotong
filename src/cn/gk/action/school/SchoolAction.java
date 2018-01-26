package cn.gk.action.school;

import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.fr.json.JSONObject;

import cn.gk.action.base.BaseAction;
import cn.gk.model.student.Academy;
import cn.gk.service.school.SchoolReferService;
import cn.gk.util.ComFun;
import cn.gk.util.Constants;
import cn.gk.util.JSONSerializer;

/**
 * 学校Action
 * 
 * @author NGD
 */
@Controller("schoolAction")
@Scope("prototype")
public class SchoolAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	@Resource
	private SchoolReferService schoolReferService;
	public Academy academy;

	public Academy getAcademy() {
		return academy;
	}

	public void setAcademy(Academy academy) {
		this.academy = academy;
	}

	/**
	 * 查看院校信息
	 * 
	 * @return
	 */
	public String toSchoolRefer() {
		// 院校类型数据
		Map<String, String> schoolTypeMap = ComFun.getSchoolTypeMap();
		getRequest().setAttribute("schoolTypeMap", JSONSerializer.serialize(schoolTypeMap));
		// 院校所在地（省）数据
		Map<String, String> schoolLocationMap = ComFun.getSchoolLocationMap();
		getRequest().setAttribute("schoolLocationMap", JSONSerializer.serialize(schoolLocationMap));
		// 院校学历层次数据
		Map<String, String> schoolEducationMap = ComFun.getSchoolEducationMap();
		getRequest().setAttribute("schoolEducationMap", JSONSerializer.serialize(schoolEducationMap));
		// 院校学历类型数据
		Map<String, String> schoolEducationTypeMap = ComFun.getSchoolEducationTypeMap();
		getRequest().setAttribute("schoolEducationTypeMap", JSONSerializer.serialize(schoolEducationTypeMap));
		getRequest().setAttribute(IS_MANAGER_LOGIN, isManagerLoginFlag());
		return SUCCESS;
	}

	/**
	 * 分页获取学校信息
	 * 
	 * @throws IOException
	 */
	public void getSchoolDataByPage() throws IOException {
		String currentPage = getRequest().getParameter("currentPage");
		JSONObject searchValJson = ComFun.getSearchParams(getRequest());
		int pageIndex = ComFun.getAboutPageIndex(getSession(), "schoolDataList_refer", currentPage);
		Map<String, Object> sDataMap = schoolReferService
				.getDataByPage(pageIndex, Constants.DEFAULT_PAGE_SIZE, searchValJson).getPageDataWrapMap();
		out(JSONSerializer.serialize(sDataMap));
		out().flush();
		out().close();
	}

	/**
	 * 跳转到学校信息添加
	 * 
	 * @return
	 */
	public String skipSchoolReferAdd() {
		// 院校类型数据
		Map<String, String> schoolTypeMap = ComFun.getSchoolTypeMap();
		getRequest().setAttribute("schoolTypeMap", schoolTypeMap);
		// 院校所在地（省）数据
		Map<String, String> schoolLocationMap = ComFun.getSchoolLocationMap();
		getRequest().setAttribute("schoolLocationMap", schoolLocationMap);
		// 院校学历层次数据
		Map<String, String> schoolEducationMap = ComFun.getSchoolEducationMap();
		getRequest().setAttribute("schoolEducationMap", schoolEducationMap);
		// 院校学历类型数据
		Map<String, String> schoolEducationTypeMap = ComFun.getSchoolEducationTypeMap();
		getRequest().setAttribute("schoolEducationTypeMap", schoolEducationTypeMap);
		return SUCCESS;
	}

	/**
	 * 执行学校信息添加
	 * 
	 * @throws IOException
	 */
	public void doSchoolInfoAdd() throws IOException {
		if (ComFun.strNull(academy)) {
			academy.setCreateInfo(getLoginUserId());
			schoolReferService.save(academy);
			out(Constants.AJAX_SUCCESS);
		} else {
			out(Constants.AJAX_FAIL);
		}
		out().flush();
		out().close();
	}

	/**
	 * 跳转到院校详情页
	 * 
	 * @return
	 */
	public String skipSchoolDetail() {
		String schoolId = getRequest().getParameter("schoolId");
		System.out.println(schoolId);
		return SUCCESS;
	}
	
	/**
	 * 跳转到院校信息修改页
	 * 
	 * @return
	 */
	public String skipSchoolInfoUpdate() {
		// 院校类型数据
		Map<String, String> schoolTypeMap = ComFun.getSchoolTypeMap();
		getRequest().setAttribute("schoolTypeMap", schoolTypeMap);
		// 院校所在地（省）数据
		Map<String, String> schoolLocationMap = ComFun.getSchoolLocationMap();
		getRequest().setAttribute("schoolLocationMap", schoolLocationMap);
		// 院校学历层次数据
		Map<String, String> schoolEducationMap = ComFun.getSchoolEducationMap();
		getRequest().setAttribute("schoolEducationMap", schoolEducationMap);
		// 院校学历类型数据
		Map<String, String> schoolEducationTypeMap = ComFun.getSchoolEducationTypeMap();
		getRequest().setAttribute("schoolEducationTypeMap", schoolEducationTypeMap);
		
		String schoolId = getRequest().getParameter("schoolId");
		Academy academy = schoolReferService.get(schoolId);
		getRequest().setAttribute("academy", academy);
		return SUCCESS;
	}

	/**
	 * 执行学校信息删除
	 * 
	 * @throws IOException
	 */
	public void deleteSchoolInfo() throws IOException {
		String schoolId = getRequest().getParameter("schoolId");
		Academy academy = schoolReferService.get(schoolId);
		if (ComFun.strNull(academy)) {
			academy.setDeleteInfo(getLoginUserId());
			schoolReferService.update(academy);
		}
		out(Constants.AJAX_SUCCESS);
		out().flush();
		out().close();
	}

}
