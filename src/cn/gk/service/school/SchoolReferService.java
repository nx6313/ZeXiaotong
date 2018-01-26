package cn.gk.service.school;

import java.util.List;

import com.fr.json.JSONObject;

import cn.gk.model.student.Academy;
import cn.gk.service.base.BaseService;
import cn.gk.util.PageWrap;

/**
 * 学院信息Service
 */
public interface SchoolReferService extends BaseService<Academy, String> {
	
	/**
	 * 获取分页数
	 * 
	 * @return
	 */
	public Integer getPageCount(Integer pageSize, boolean forPageFlag);
	
	/**
	 * 获取分页数据
	 * 
	 * @return
	 */
	public List<Academy> getPageData(Integer pageIndex, Integer pageSize, JSONObject searchValJson);
	
	/**
	 * 获取封装的分页数据
	 * 
	 * @return
	 */
	public PageWrap<Academy> getDataByPage(Integer pageIndex, Integer pageSize, JSONObject searchValJson);

}
