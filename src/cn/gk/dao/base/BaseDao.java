package cn.gk.dao.base;

import java.util.List;

public interface BaseDao<M extends java.io.Serializable, PK extends java.io.Serializable> {
	public static final String HQL_DELETE = "";

	public void save(M model);

	public void saveOrUpdate(M model);

	public void merge(M model);

	public void update(M model);

	public void delete(PK id);

	public void deleteObject(M model);

	public M get(PK id);

	public M getModelByWhere(final String hql, final Object... paramlist);

	public int countAll();

	public List<M> listAll();

	public List<M> listBySortPage(final int pageSize, final int pn,
			final String sortName, final String sortOrder);

	public List<M> listByWhere(final String hql, final Object... paramlist);

	public List<M> listByWherePage(final String hql, final int pageSize,
			final int pn, final Object... paramlist);

	public List<M> listByWhereSortPage(final String hql, final int pageSize,
			final int pn, final String sortName, final String sortOrder,
			final Object... paramlist);
	
	public List<M> listByWhereSort(final String hql, final String sortName, final String sortOrder,
			final Object... paramlist);

	boolean exists(PK id);

	public void flush();

	public void clear();

	public void deleteORM(final String sql, String id, List<String> list);

	public void deleteORM(final String sql, String id);

	public void addORM(final String sql, String id, List<String> list);

	public int countByWhere(final String hql, final Object... paramlist);

	public List<M> getAtt(final String date, final String orgId);
}
