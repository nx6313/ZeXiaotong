package cn.gk.dao.base.impl;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import cn.gk.dao.base.BaseDao;
import cn.gk.util.ConditionQuery;
import cn.gk.util.OrderBy;

import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;

public abstract class BaseDaoImpl<M extends java.io.Serializable, PK extends java.io.Serializable> implements BaseDao<M, PK> {
	protected static final Logger LOGGER = (Logger) LoggerFactory
			.getLogger(BaseDaoImpl.class);

	private final Class<M> entityClass;
	private final String HQL_LIST_ALL;
	private final String HQL_COUNT_ALL;

	@SuppressWarnings("unchecked")
	public BaseDaoImpl() {
		this.entityClass = (Class<M>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];

		HQL_LIST_ALL = "from " + this.entityClass.getSimpleName()
				+ " where state != 0 ";
		HQL_COUNT_ALL = " select count(*) from "
				+ this.entityClass.getSimpleName() + " where state != 0";
	}

	@Autowired
	@Qualifier("sessionFactory")
	private SessionFactory sessionFactory;

	public Session getSession() {
		// 事务必须是开启的(Required)，否则获取不到
		return sessionFactory.getCurrentSession();
	}

	public void save(M model) {
		getSession().save(model);
	}

	public void saveOrUpdate(M model) {
		getSession().saveOrUpdate(model);
	}

	public void update(M model) {
		getSession().update(model);
	}

	public void merge(M model) {
		getSession().merge(model);
	}

	public void delete(PK id) {
		getSession().delete(this.get(id));

	}

	public void deleteObject(M model) {
		getSession().delete(model);

	}

	public boolean exists(PK id) {
		return get(id) != null;
	}

	@SuppressWarnings("unchecked")
	public M get(PK id) {
		return (M) getSession().get(this.entityClass, id);
	}

	public M getModelByWhere(String hql, Object... paramlist) {
		return aggregate(hql, paramlist);
	}

	public List<M> listAll() {
		StringBuilder hql = new StringBuilder(HQL_LIST_ALL);
		hql.append("order by createDate ");
		return list1(hql.toString());
	}

	/**
	 * 可自定义排序，分页 返回List
	 */
	public List<M> listBySortPage(int pageSize, int pn, String sortName,
			String sortOrder) {
		StringBuilder hql = new StringBuilder(HQL_LIST_ALL);
		if (null == sortName || sortName.isEmpty()) {
			hql.append("order by createDate ");
		} else {
			hql.append("order by ");
			hql.append(sortName);
		}
		if (null == sortOrder || sortOrder.isEmpty()) {
			hql.append(" desc");
		} else {
			hql.append(" ");
			hql.append(sortOrder);
		}
		return list(hql.toString(), pageSize, pn);
	}

	/**
	 * 多条件查询，不分页
	 * 
	 * @param hql
	 * @param paramlist
	 * @return
	 */
	public List<M> listByWhere(final String hql, final Object... paramlist) {
		return this.<M> list1(hql, paramlist);
	}

	/**
	 * 多条件查询，分页
	 * 
	 * @param hql
	 * @param pageSize页大
	 *            	（一页显示的行数）
	 * @param pn
	 *            页数（第几页）
	 * @param paramlist参数
	 * @return
	 */
	public List<M> listByWherePage(final String hql, final int pageSize,
			final int pn, final Object... paramlist) {
		return this.<M> list(hql, pageSize, pn, paramlist);
	}
	
	/**
	 * 多条件查询，可自定义排序，分页
	 * 
	 * @param hql
	 * @param pageSize页大小
	 * 				（一页显示的行数）
	 * @param pn 
	 * 				 页数（第几页）
	 * @param paramlist参数
	 * @return
	 */
	public List<Object> listSortPageByWhere(final String hql, final int pageSize,
			final int pn, final String sortName, final String sortOrder,
			final Object... paramlist) {
		StringBuilder shql = new StringBuilder(hql);
		if (sortName.isEmpty()) {
			shql.append(" order by createDate ");
		} else {
			shql.append(" order by ");
			shql.append(sortName);
		}
		if (sortOrder.isEmpty()) {
			shql.append(" desc");
		} else {
			shql.append(" ");
			shql.append(sortOrder);
		}
		return this.<Object> list(shql.toString(), pageSize, pn, paramlist);
	}

	/**
	 * 多条件查询，可自定义排序，分页
	 * 
	 * @param hql
	 * @param pageSize页大小
	 *              （一页显示的行数）
	 * @param pn
	 *            页数（第几页）
	 * @param paramlist参数
	 * @return
	 */
	public List<M> listByWhereSortPage(final String hql, final int pageSize,
			final int pn, final String sortName, final String sortOrder,
			final Object... paramlist) {
		StringBuilder shql = new StringBuilder(hql);
		if (sortName.isEmpty()) {
			shql.append(" order by createDate ");
		} else {
			shql.append(" order by ");
			shql.append(sortName);
		}
		if (sortOrder.isEmpty()) {
			shql.append(" desc");
		} else {
			shql.append(" ");
			shql.append(sortOrder);
		}
		return this.<M> list(shql.toString(), pageSize, pn, paramlist);
	}

	/**
	 * 多条件查询，可自定义排序，不分页
	 * 
	 * @param hql
	 * @param paramlist参数
	 * @return
	 */
	public List<M> listByWhereSort(final String hql, final String sortName, final String sortOrder,
			final Object... paramlist) {
		StringBuilder shql = new StringBuilder(hql);
		if (sortName.isEmpty()) {
			shql.append(" order by createDate ");
		} else {
			shql.append(" order by ");
			shql.append(sortName);
		}
		if (sortOrder.isEmpty()) {
			shql.append(" desc");
		} else {
			shql.append(" ");
			shql.append(sortOrder);
		}
		return this.<M> list1(shql.toString(), paramlist);
	}

	/**
	 * 
	 * @param <T>
	 * @param hql
	 * @param pageSize
	 *            页大小（一页显示的行数）
	 * @param pn
	 *            页数（第几页）
	 * @param paramlist
	 *            参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <T> List<T> list(final String hql, final int pageSize,
			final int pn, final Object... paramlist) {
		Query query = getSession().createQuery(hql);
		setParameters(query, paramlist);
		if (pn > -1 && pageSize > -1) {
			query.setMaxResults(pageSize);
			int start = (pn - 1) * pageSize;
			if (start != 0) {
				query.setFirstResult(start);
			}
		}
		if (pn <= 0) {
			query.setFirstResult(0);
		}
		List<T> results = query.list();
		return results;
	}

	/**
	 * 根据条件查找总数
	 * 
	 * @param paramlist
	 * @return
	 */
	public int countAll() {
		return count(HQL_COUNT_ALL);
	}

	/**
	 * 根据条件查找总数
	 * 
	 * @param paramlist
	 * @return
	 */
	public int countByWhere(final String hql, final Object... paramlist) {
		return count(hql, paramlist);
	}

	/**
	 * 根据条件查找总数
	 * 
	 * @param paramlist
	 * @return
	 */
	protected int count(final String hql, final Object... paramlist) {
		Query query = getSession().createQuery(hql);
		setParameters(query, paramlist);
		return ((Long) query.uniqueResult()).intValue();
	}

	/**
	 * 如果有多条记录，根据查询条件只返回唯一一条记录
	 */
	@SuppressWarnings("unchecked")
	protected <T> T unique(final String hql, final Object... paramlist) {
		Query query = getSession().createQuery(hql);
		setParameters(query, paramlist);
		return (T) query.setMaxResults(0).uniqueResult();
	}

	/**
	 * 分页for in map参数为in括号中的参数
	 */
	@SuppressWarnings("unchecked")
	protected <T> List<T> listWithIn(final String hql, final int start,
			final int length, final Map<String, Collection<?>> map,
			final Object... paramlist) {
		Query query = getSession().createQuery(hql);
		setParameters(query, paramlist);
		for (Entry<String, Collection<?>> e : map.entrySet()) {
			query.setParameterList(e.getKey(), e.getValue());
		}
		if (start > -1 && length > -1) {
			query.setMaxResults(length);
			if (start != 0) {
				query.setFirstResult(start);
			}
		}
		List<T> results = query.list();
		return results;
	}

	/**
	 * 
	 * for in map参数为in括号中的参数
	 */
	@SuppressWarnings("unchecked")
	protected <T> T aggregate(final String hql,
			final Map<String, Collection<?>> map, final Object... paramlist) {
		Query query = getSession().createQuery(hql);
		if (paramlist != null) {
			setParameters(query, paramlist);
			for (Entry<String, Collection<?>> e : map.entrySet()) {
				query.setParameterList(e.getKey(), e.getValue());
			}
		}

		return (T) query.uniqueResult();
	}

	/**
	 * 根据查询条件返回一条记录
	 * 
	 * @param <T>
	 * @param hql
	 * @param paramlist
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <T> T aggregate(final String hql, final Object... paramlist) {
		Query query = getSession().createQuery(hql);
		setParameters(query, paramlist);
		return (T) query.uniqueResult();

	}

	/**
	 * 执行批处理语句.如 之间insert, update, delete 等.
	 */
	protected int execteBulk(final String hql, final Object... paramlist) {
		Query query = getSession().createQuery(hql);
		setParameters(query, paramlist);
		Object result = query.executeUpdate();
		return result == null ? 0 : ((Integer) result).intValue();
	}

	protected int execteNativeBulk(final String natvieSQL,
			final Object... paramlist) {
		Query query = getSession().createSQLQuery(natvieSQL);
		setParameters(query, paramlist);
		Object result = query.executeUpdate();
		return result == null ? 0 : ((Integer) result).intValue();
	}

	protected <T> List<T> list1(final String sql, final Object... paramlist) {
		return list(sql, -1, -1, paramlist);
	}

	/*
	 * native方法中的参数为非hql语句
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> listByNative(final String nativeSQL, final int pn,
			final int pageSize, final List<Entry<String, Class<?>>> entityList,
			final List<Entry<String, Type>> scalarList,
			final Object... paramlist) {

		SQLQuery query = getSession().createSQLQuery(nativeSQL);
		if (entityList != null) {
			for (Entry<String, Class<?>> entity : entityList) {
				query.addEntity(entity.getKey(), entity.getValue());
			}
		}
		if (scalarList != null) {
			for (Entry<String, Type> entity : scalarList) {
				query.addScalar(entity.getKey(), entity.getValue());
			}
		}

		setParameters(query, paramlist);

		if (pn > -1 && pageSize > -1) {
			query.setMaxResults(pageSize);
			int start = (pn - 1) * pageSize;
			if (start != 0) {
				query.setFirstResult(start);
			}
		}
		List<T> result = query.list();
		return result;
	}

	@SuppressWarnings("unchecked")
	protected <T> T aggregateByNative(final String natvieSQL,
			final List<Entry<String, Type>> scalarList,
			final Object... paramlist) {
		SQLQuery query = getSession().createSQLQuery(natvieSQL);
		if (scalarList != null) {
			for (Entry<String, Type> entity : scalarList) {
				query.addScalar(entity.getKey(), entity.getValue());
			}
		}

		setParameters(query, paramlist);

		Object result = query.uniqueResult();
		return (T) result;
	}

	/**
	 * 
	 * @param <T>
	 * @param query
	 * @param orderBy
	 * @param pn
	 * @param pageSize
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> list(ConditionQuery query, OrderBy orderBy,
			final int pn, final int pageSize) {
		Criteria criteria = getSession().createCriteria(this.entityClass);
		query.build(criteria);
		orderBy.build(criteria);
		int start = (pn - 1) * pageSize;
		if (start != 0) {
			criteria.setFirstResult(start);
		}
		criteria.setMaxResults(pageSize);
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> list(Criteria criteria) {
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	public <T> T unique(Criteria criteria) {
		return (T) criteria.uniqueResult();
	}

	public <T> List<T> list(DetachedCriteria criteria) {
		return list(criteria.getExecutableCriteria(getSession()));
	}

	@SuppressWarnings("unchecked")
	public <T> T unique(DetachedCriteria criteria) {
		return (T) unique(criteria.getExecutableCriteria(getSession()));
	}

	public void flush() {
		getSession().flush();
	}

	public void clear() {
		getSession().clear();
	}

	/*
	 * 根据查询语句查找ID
	 */
	protected String getIdResult(String hql, Object... paramlist) {
		String result = "";
		List<?> list = list1(hql, paramlist);
		if (list != null && list.size() > 0) {
			return list.get(0).toString();
		}
		return result;
	}

	protected void setParameters(Query query, Object[] paramlist) {
		if (paramlist != null) {
			for (int i = 0; i < paramlist.length; i++) {
				if (paramlist[i] instanceof Date) {
					// 难道这是bug 使用setParameter不行？？
					query.setTimestamp(i, (Date) paramlist[i]);
				} else {
					query.setParameter(i, paramlist[i]);
				}
			}
		}
	}

	public void deleteORM(final String sql, String id, List<String> list) {
		for (int i = 0; i < list.size(); i++) {
			Query query = getSession().createSQLQuery(sql);
			query.setParameter(0, id);
			query.setParameter(1, list.get(i));
			query.executeUpdate();
		}
	}

	public void deleteORM(final String sql, String id) {
		Query query = getSession().createSQLQuery(sql);
		query.setParameter(0, id);
		query.executeUpdate();
		getSession().clear();
	}

	public void addORM(final String sql, String id, List<String> list) {
		for (int i = 0; i < list.size(); i++) {
			Query query = getSession().createSQLQuery(sql);
			query.setParameter(0, id);
			query.setParameter(1, list.get(i));
			query.executeUpdate();
		}
	}

	@SuppressWarnings("unchecked")
	public List<M> getAtt(final String date, final String orgId) {
		Query query = getSession().getNamedQuery("att");
		query.setParameter(0, date);
		query.setParameter(1, orgId);
		return query.list();
	}
}
