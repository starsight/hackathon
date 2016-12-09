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
	/*********************�˿��ʹ��ǰ���������ķ���***************************/
	/**
	 * Ȼ��̳д�BaseDaoImpl�������Dao�࣬��Ҫ��д�����еĹ��췽������������T��Ϊ��Ӧ�ľ����ʵ����
	 * �磺	public SalChanceDaoImpl() {
				super(SalChance.class);
			}
	 */
	//1�������޲εĹ��췽��
	public BaseDaoImpl() {
		
	}
	
	//2���������Ĺ��췽��
	public BaseDaoImpl(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	//3�����ڼ̳���HibernateDaoSupport�࣬��Ҫע��sessionFactory�������Զ���
	@Autowired
	public void setSessionFactory2(SessionFactory factory) {
		super.setSessionFactory(factory);
	}
	/***********************************************/
	
	/**
	 * ��������ɾ������������ֵ���޷���
	 */
	public void delById(Integer id) {
		getHibernateTemplate().delete(findById(id));
	}
	
	/**
	 * �������ң���������ֵ�����ظ�ʵ����
	 */
	public T findById(Integer id) {
		return (T) getHibernateTemplate().get(clazz, id);
	}

	/**
	 * ��ӣ�������ӵ�ʵ���ࣨ��Ӧ���ݿ��е�һ�����ݱ����޷���
	 */
	public void save(T t) {
		getHibernateTemplate().save(t);
	}
	
	/**
	 * �޸ģ�����ʵ���࣬�޷���
	 */
	public void update(T t) {
		getHibernateTemplate().update(t);
	}

	/**
	 * ��ҳ��ѯ�������ҳʵ���࣬����ʵ���༯��
	 */
	public List<T> findAllByPage(Pagination p) {
		String hql = "from " + clazz.getSimpleName();
		return findByHqlAndPage(hql, p);
	}

	/**
	 * ������ѯ������DC�ӿڣ�����ʵ���༯��
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByCondition(DetachedCriteria dc) {
		return getHibernateTemplate().findByCriteria(dc);
	}

	/**
	 * ��ҳ������ѯ������DC�ӿڡ���ҳʵ���࣬����ʵ���༯��
	 */
	@SuppressWarnings("unchecked")
	public List<T> findByConditionPage(DetachedCriteria dc, Pagination p) {
		return getHibernateTemplate().findByCriteria(dc, p.getFirstIndex(),
				p.getSize());
	}

	/**
	 * ����������������count������DC�������������������ͣ�
	 * Projections������Ҫ����
	 * 
	 */
	public Integer findMaxByCondition(DetachedCriteria dc) {
		return (Integer) getHibernateTemplate().findByCriteria(
				dc.setProjection(Projections.rowCount())).get(0);
	}

	/**
	 * �������������������룬�����������������ͣ�
	 * �˴�ʹ�ûص��������貹��
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
	 * HQL�����з�ҳ��ѯ������hql��䡢��ҳʵ���࣬����ʵ���༯��
	 * �ڲ�������Ҫ����
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
	 * ��������ҳ��ѯ
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
	 * ��������ҳ��ѯ������hql��䣬�������飬����ʵ���༯��
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
