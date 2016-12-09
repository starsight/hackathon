package org.java.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.java.entity.Pagination;

public interface BaseDao<T> {
	
	/**
	 * �������
	 * @param t
	 */
	public abstract void save(T t);
	
	/**
	 * �޸�����
	 * @param t
	 */
	public abstract void update(T t);
	
	/**
	 * ͨ������ɾ��
	 * @param id
	 */
	public abstract void delById(Integer id);
	
	/**
	 * ͨ����������
	 * @param id
	 * @return
	 */
	public abstract T findById(Integer id);
	
	/**
	 * ����ȫ������ҳ
	 * @param p
	 * @return
	 */
	public abstract List<T> findAllByPage(Pagination p);
	
	/**
	 * ����������
	 * @return
	 */
	public abstract int findMax();
	
	/**
	 * ������������������
	 * @param dc
	 * @return
	 */
	public abstract Integer findMaxByCondition(DetachedCriteria dc);
	
	/**
	 * DetachedCriteria������ѯ
	 * @param dc
	 * @return
	 */
	public abstract List<T> findByCondition(DetachedCriteria dc);
	
	/**
	 * DetachedCriteria������ѯ����ҳ
	 * @param dc
	 * @param p
	 * @return
	 */
	public abstract List<T> findByConditionPage(DetachedCriteria dc, Pagination p);
	
	/**
	 * �Զ���hql����ѯ����ҳ
	 * hql����в��贫�����
	 */
	public abstract List<T> findByHqlAndPage(String hql, Pagination p);
	
	/**
	 * �Զ���hql����ѯ����ҳ
	 * value����hql����е�һ����
	 */
	public abstract List<T> findByHqlAndPage(String hql, Object value, Pagination p);
	
	/**
	 * �Զ���hql����ѯ����ҳ
	 * values�Ǵ���hql������贫��Ĳ�������
	 */
	public abstract List<T> findByHqlAndPage(String hql, Object[] values, Pagination p);
}
