package cn.gk.service.base.impl;

import java.util.List;

import cn.gk.dao.base.BaseDao;
import cn.gk.service.base.BaseService;
import cn.gk.util.Constants;

public abstract class BaseServiceImpl<M extends java.io.Serializable, PK extends java.io.Serializable> implements BaseService<M, PK> {
	protected BaseDao<M, PK> baseDao;

	public abstract void setBaseDao(BaseDao<M, PK> baseDao);

	public void save(M model) {
		baseDao.save(model);
	}

	public void saveOrUpdate(M model) {
		baseDao.saveOrUpdate(model);
	}

	public void merge(M model) {
		baseDao.merge(model);
	}

	public void update(M model) {
		baseDao.update(model);
	}

	public void delete(PK id) {
		baseDao.delete(id);
	}

	public void deleteObject(M model) {
		baseDao.deleteObject(model);
	}

	public M get(PK id) {
		return baseDao.get(id);
	}

	public int countAll() {
		return baseDao.countAll();
	}

	public List<M> listAll() {
		return baseDao.listAll();
	}

	/**
	 * 默认分页大小，默认排序
	 */
	public List<M> listByDefaultPageSort(final int pn) {
		return baseDao.listBySortPage(Constants.DEFAULT_PAGE_SIZE, pn, "", "");
	}

	/**
	 * 默认分页大小，自定义排序
	 */
	public List<M> listByDefaultPageSort(final int pn, final String sortName,
			final String sortOrder) {
		return baseDao.listBySortPage(Constants.DEFAULT_PAGE_SIZE, pn,
				sortName, sortOrder);
	}

	/**
	 * 自定义分页大小，自定义排序字段
	 * 
	 * @param pageSize
	 * @param pn
	 * @param sortName
	 * @param sortOrder
	 * @return
	 */
	public List<M> listByPageSort(final int pageSize, final int pn,
			final String sortName, final String sortOrder) {
		return baseDao.listBySortPage(pageSize, pn, sortName, sortOrder);
	}

	/**
	 * 多条件查询， 不分页，不排序
	 * 
	 * @param hql
	 * @param paramlist
	 * @return
	 */
	public List<M> listByWhere(final String hql, final Object... paramlist) {
		return baseDao.listByWhere(hql, paramlist);
	}

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
			final int pn, final Object... paramlist) {
		return baseDao.listByWherePage(hql, pageSize, pn, paramlist);
	}

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
			final Object... paramlist) {
		return baseDao.listByWhereSortPage(hql, pageSize, pn, sortName,
				sortOrder, paramlist);
	}

	/**
	 * 删除关联表数据
	 * 
	 * @param sql
	 * @param id
	 * @param list
	 */
	public void deleteORM(final String sql, String id, List<String> list) {
		baseDao.deleteORM(sql, id, list);
	}

	/**
	 * 添加关联表
	 * 
	 * @param sql
	 * @param id
	 * @param list
	 */
	public void addORM(final String sql, String id, List<String> list) {
		baseDao.addORM(sql, id, list);
	}

	/**
	 * 根据条件查找总数
	 */
	public int countByWhere(final String hql, final Object... paramlist) {
		return baseDao.countByWhere(hql, paramlist);
	}

	public void flush() {
		baseDao.flush();
	}

	public void clear() {
		baseDao.clear();
	}
}
