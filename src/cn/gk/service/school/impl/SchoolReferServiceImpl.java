package cn.gk.service.school.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.fr.json.JSONObject;

import cn.gk.dao.base.BaseDao;
import cn.gk.dao.school.SchoolReferDao;
import cn.gk.model.student.Academy;
import cn.gk.service.base.impl.BaseServiceImpl;
import cn.gk.service.school.SchoolReferService;
import cn.gk.util.PageWrap;

/**
 * 学院信息Service实现
 */
@Service("schoolReferService")
public class SchoolReferServiceImpl extends BaseServiceImpl<Academy, String> implements SchoolReferService {
	private SchoolReferDao schoolReferDao;

	@Resource
	@Qualifier("schoolReferDao")
	@Override
	public void setBaseDao(BaseDao<Academy, String> baseDao) {
		this.baseDao = baseDao;
		// 注意要强制转换
		this.schoolReferDao = (SchoolReferDao) baseDao;
	}

	@Override
	public Integer getPageCount(Integer pageSize, boolean forPageFlag) {
		return schoolReferDao.getPageCount(pageSize, forPageFlag);
	}

	@Override
	public List<Academy> getPageData(Integer pageIndex, Integer pageSize, JSONObject searchValJson) {
		return schoolReferDao.getPageData(pageIndex, pageSize, searchValJson);
	}

	@Override
	public PageWrap<Academy> getDataByPage(Integer pageIndex, Integer pageSize, JSONObject searchValJson) {
		PageWrap<Academy> pageWrap = new PageWrap<>();
		int pageCount = getPageCount(pageSize, true);
		int dataCount = getPageCount(pageSize, false);
		List<Academy> dataList = getPageData(pageIndex, pageSize, searchValJson);
		pageWrap.setChild(dataList);
		pageWrap.setPageCount(pageCount);
		pageWrap.setDataCount(dataCount);
		pageWrap.setCurrentPage(pageIndex);
		pageWrap.setPageSize(pageSize);
		return pageWrap;
	}

}
