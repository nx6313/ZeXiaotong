package cn.gk.dao.comm.impl;

import java.util.List;

import org.directwebremoting.annotations.RemoteProxy;
import org.directwebremoting.spring.SpringCreator;
import org.springframework.stereotype.Repository;

import cn.gk.dao.base.impl.BaseDaoImpl;
import cn.gk.dao.comm.StudentCardDao;
import cn.gk.model.comm.StudentCard;
import cn.gk.util.ComFun;
import cn.gk.util.Constants;

@Repository("studentCardDao")
@RemoteProxy(creator = SpringCreator.class)
public class StudentCardDaoImpl extends BaseDaoImpl<StudentCard, String> implements StudentCardDao {

	@Override
	public Integer getPageCount(String agencyId, Integer cardStatus, boolean forPageFlag) {
		StringBuilder hql = new StringBuilder(HQL_LIST);
		if (agencyId != null) {
			hql.append(" and s.aboutAgencyId ='" + agencyId + "' ");
		}
		if (cardStatus != null) {
			hql.append(" and s.cardStatus =" + cardStatus);
		}
		List<StudentCard> list = super.listByWhere(hql.toString());
		if (forPageFlag) {
			if (ComFun.strNull(list, true)) {
				return (int) Math.ceil(
						Float.parseFloat(list.size() + "f") / Float.parseFloat(Constants.DEFAULT_PAGE_SIZE + "f"));
			}
		} else {
			if (ComFun.strNull(list, true)) {
				return list.size();
			} else {
				return 0;
			}
		}
		return 1;
	}

	@Override
	public List<StudentCard> getStudentListByAgencyId(String agencyId, Integer cardStatus) {
		StringBuilder hql = new StringBuilder(HQL_LIST);
		if (ComFun.strNull(agencyId)) {
			hql.append(" and aboutAgencyId = '" + agencyId + "' ");
		}
		if (ComFun.strNull(cardStatus)) {
			hql.append(" and cardStatus = " + cardStatus);
		}
		return super.listByWhere(hql.toString());
	}

	@Override
	public List<StudentCard> getStudentPageListByAgencyId(String agencyId, String cardStatus, Integer pageIndex) {
		StringBuilder hql = new StringBuilder(HQL_LIST);
		hql.append(" and aboutAgencyId =  '" + agencyId + "' ");
		if (ComFun.strNull(cardStatus)) {
			if(cardStatus.contains(",")){
				hql.append(" and cardStatus in (" + cardStatus + ") ");
			}else{
				if(Integer.parseInt(cardStatus) >= 0){
					hql.append(" and cardStatus = " + Integer.parseInt(cardStatus));
				}
			}
		}
		if (ComFun.strNull(pageIndex)) {
			List<StudentCard> list = super.listByWherePage(hql.toString(), Constants.DEFAULT_PAGE_SIZE, pageIndex);
			if (list != null && list.size() > 0) {
				return list;
			}
		} else {
			List<StudentCard> list = super.listByWhere(hql.toString());
			if (list != null && list.size() > 0) {
				return list;
			}
		}
		return null;
	}

	@Override
	public List<StudentCard> searchStudentPageList(String searchVal) {
		StringBuilder hql = new StringBuilder(HQL_LIST);
		if(ComFun.strNull(searchVal)){
			hql.append(" and (");
			hql.append(" cardNum = '" + searchVal + "' or ");
			hql.append(" accountNum = '" + searchVal + "' or ");
			hql.append(" userName like '%" + searchVal + "%' or ");
			hql.append(" phoneNum = '" + searchVal + "' ");
			hql.append(") ");
		}
		return super.listByWhere(hql.toString());
	}

	@Override
	public StudentCard getStudentCardInfoLoginNameAndPassword(String loginName, String password, boolean ignore) {
		StringBuilder hql = new StringBuilder(HQL_LIST);
		hql.append(" and (s.cardNum = ? or s.phoneNum = ?) and s.passWord = ?");
		return super.unique(hql.toString(), loginName, loginName, password);
	}

	@Override
	public StudentCard getStudentCardInfoByLoginName(String loginName) {
		StringBuilder hql = new StringBuilder(HQL_LIST);
		hql.append(" and (s.cardNum = ? or s.phoneNum = ?)");
		return super.unique(hql.toString(), loginName, loginName);
	}

	@Override
	public List<StudentCard> getStudentCardListByPhoneNum(String phoneNum) {
		StringBuilder hql = new StringBuilder(HQL_LIST);
		if (ComFun.strNull(phoneNum)) {
			hql.append(" and phoneNum = '" + phoneNum + "' ");
		}
		return super.listByWhere(hql.toString());
	}

}
