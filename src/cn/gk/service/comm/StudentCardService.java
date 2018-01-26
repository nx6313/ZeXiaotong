package cn.gk.service.comm;

import java.util.List;

import cn.gk.model.comm.StudentCard;
import cn.gk.service.base.BaseService;

/**
 * 学生卡Service
 */
public interface StudentCardService extends BaseService<StudentCard, String> {

	/**
	 * 获取分页数
	 * 
	 * @return
	 */
	public Integer getPageCount(String agencyId, Integer cardStatus, boolean forPageFlag);

	/**
	 * 根据代理商Id获取学生卡信息数据
	 * 
	 * @param agencyId
	 * @param cardStatus
	 * @return
	 */
	public List<StudentCard> getStudentListByAgencyId(String agencyId, Integer cardStatus);

	/**
	 * 根据代理商Id获取学生卡信息数据(分页)
	 * 
	 * @param agencyId
	 * @param cardStatus
	 * @param pageIndex
	 * @return
	 */
	public List<StudentCard> getStudentPageListByAgencyId(String agencyId, String cardStatus, Integer pageIndex);

	/**
	 * 查询学生卡信息
	 * 
	 * @param searchVal
	 * @return
	 */
	public List<StudentCard> searchStudentPageList(String searchVal);

	/**
	 * 根据用户名和密码得到StudentCard
	 * 
	 * @param loginName
	 * @param password
	 * @param ignore
	 * @return
	 */
	public StudentCard getStudentCardInfoLoginNameAndPassword(String loginName, String password, boolean ignore);

	/**
	 * 根据用户账户查找
	 * 
	 * @param num
	 * @return
	 */
	public StudentCard getStudentCardInfoByLoginName(String loginName);

	/**
	 * 根据绑定的手机号查询学生卡信息列表（一般只有一条）
	 * 
	 * @param phoneNum
	 * @return
	 */
	public List<StudentCard> getStudentCardListByPhoneNum(String phoneNum);

}
