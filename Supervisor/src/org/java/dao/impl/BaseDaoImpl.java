package org.java.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.java.dao.BaseDao;
import org.java.entity.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;



public class BaseDaoImpl<T> extends HibernateDaoSupport implements BaseDao<T> {

	protected Class<T> clazz;	
	/*********************此框架使用前必须声明的方法***************************/
	/**
	 * 然后继承此BaseDaoImpl类的其他Dao类，需要重写父类中的构造方法，并将泛型T改为对应的具体的实体类
	 * 如：	public SalChanceDaoImpl() {
				super(SalChance.class);
			}
	 */
	//1、声明无参的构造方法
	public BaseDaoImpl() {
		
	}
	
	//2、带参数的构造方法
	public BaseDaoImpl(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	//3、由于继承了HibernateDaoSupport类，需要注入sessionFactory，进行自动绑定
	@Autowired
	public void setSessionFactory2(SessionFactory factory) {
		super.setSessionFactory(factory);
	}
	/***********************************************/
	
	/**
	 * 按照主键删除，输入主键值，无返回
	 */
	public void delById(Integer id) {
		getHibernateTemplate().delete(findById(id));
	}
	
	/**
	 * 主键查找，输入主键值，返回该实体类
	 */
	public T findById(Integer id) {
		return (T) getHibernateTemplate().get(clazz, id);
	}

	/**
	 * 添加，输入添加的实体类（对应数据库中的一张数据表），无返回
	 */
	public void save(T t) {
		getHibernateTemplate().save(t);
	}
	
	/**
	 * 修改，输入实体类，无返回
	 */
	public void update(T t) {
		getHibernateTemplate().update(t);
	}

	/**
	 * 分页查询，输入分页实体类，返回实体类集合
	 */
	public List<T> findAllByPage(Pagination p) {
		String hql = "from " + clazz.getSimpleName();
		return findByHqlAndPage(hql, p);
	}

	/**
	 * 条件查询，输入DC接口，返回实体类集合
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByCondition(DetachedCriteria dc) {
		return getHibernateTemplate().findByCriteria(dc);
	}

	/**
	 * 分页条件查询，输入DC接口、分页实体类，返回实体类集合
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByConditionPage(DetachedCriteria dc, Pagination p) {
		return getHibernateTemplate().findByCriteria(dc, p.getFirstIndex(),
				p.getSize());
	}

	/**
	 * 条件查找数据总数count，输入DC，返回数据总数（整型）
	 * Projections功能需要补充
	 * 
	 */
	public Integer findMaxByCondition(DetachedCriteria dc) {
		return (Integer) getHibernateTemplate().findByCriteria(
				dc.setProjection(Projections.rowCount())).get(0);
	}

	/**
	 * 查找数据总数，无输入，返回数据总数（整型）
	 * 此处使用回调函数，需补充
	 */
	public int findMax() {
		// TODO Auto-generated method stub
		return getHibernateTemplate().execute(new HibernateCallback<Integer>() {

			public Integer doInHibernate(Session session)
					throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				Object val = session.createQuery(
						"select count(*) from " + clazz.getSimpleName())
						.uniqueResult();
				return Integer.parseInt(val.toString());
			}

		});
	}

	/**
	 * HQL语句进行分页查询，输入hql语句、分页实体类，返回实体类集合
	 * 内部函数需要补充
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByHqlAndPage(final String hql, final Pagination p) {
		return getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createQuery(hql).setFirstResult(
						p.getFirstIndex()).setMaxResults(p.getSize()).list();
			}
		});
	}

	/**
	 * 单条件分页查询
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByHqlAndPage(final String hql, final Object value,
			final Pagination p) {
		return getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				return session.createQuery(hql).setParameter(0, value)
						.setFirstResult(p.getFirstIndex()).setMaxResults(
								p.getSize()).list();
			}
		});
	}
	
	/**
	 * 多条件分页查询，输入hql语句，条件数组，返回实体类集合
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByHqlAndPage(final String hql, final Object[] values,
			final Pagination p) {
		return getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				// TODO Auto-generated method stub
				Query query = session.createQuery(hql);
				for (int i = 0; i < values.length; i++) {
					query.setParameter(i, values[i]);
				}
				System.out.println(query.toString());
				return query.setFirstResult(p.getFirstIndex()).setMaxResults(
						p.getSize()).list();
			}

		});
	}

}
