package cn.gk.service.base;

import java.util.List;

public interface BaseService<M extends java.io.Serializable, PK extends java.io.Serializable> {

	public void save(M model);

	public void saveOrUpdate(M model);

	public void merge(M model);

	public void update(M model);

	public void delete(PK id);

	public void deleteObject(M model);

	public M get(PK id);

	public int countAll();

	public List<M> listAll();

	/**
	 * 使用默认分页页面大小，默认createDate,desc排序
	 */
	public List<M> listByDefaultPageSort(int pn);

	/**
	 * 使用默认分页页面大小，自定义排序字段
	 * 
	 * @param pn
	 * @param sortName
	 * @param sortOrder
	 * @return
	 */
	public List<M> listByDefaultPageSort(int pn, String sortName,
			String sortOrder);

	/**
	 * 自定义分页大小，指定排序字段
	 * 
	 * @param pageSize
	 * @param pn
	 * @param sortName
	 * @param sortOrder
	 * @return
	 */
	public List<M> listByPageSort(int pageSize, int pn, String sortName,
			String sortOrder);

	/**
	 * 多条件查询， 不分页，不排据
	 * 
	 * @param hql
	 * @param paramlist
	 * @return
	 */
	public List<M> listByWhere(final String hql, final Object... paramlist);

	/**
	 * 多条件查询，分页，不排序
	 * 
	 * @param hql
	 * @param pageSize
	 * @param pn
	 * @param paramlist
	 * @return
	 */
	public List<M> listByWherePage(final String hql, final int pageSize,
			final int pn, final Object... paramlist);

	/**
	 * 多条件，分页，自定义排序
	 * 
	 * @param hql
	 * @param pageSize
	 * @param pn
	 * @param sortName
	 * @param sortOrder
	 * @param paramlist
	 * @return
	 */
	public List<M> listByWhereSortPage(final String hql, final int pageSize,
			final int pn, final String sortName, final String sortOrder,
			final Object... paramlist);

	/**
	 * 删除关联表数据
	 * 
	 * @param sql
	 * @param id
	 * @param list
	 */
	public void deleteORM(final String sql, String id, List<String> list);

	/**
	 * 添加关联表数据
	 * 
	 * @param sql
	 * @param id
	 * @param list
	 */
	public void addORM(final String sql, String id, List<String> list);

	/**
	 * 根据条件查找总数
	 * 
	 * @param hql
	 * @param paramlist
	 * @return
	 */
	public int countByWhere(final String hql, final Object... paramlist);

	public void flush();

	public void clear();
}
