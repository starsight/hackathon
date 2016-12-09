package org.java.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.java.entity.Pagination;

public interface BaseDao<T> {
	
	/**
	 * 添加数据
	 * @param t
	 */
	public abstract void save(T t);
	
	/**
	 * 修改数据
	 * @param t
	 */
	public abstract void update(T t);
	
	/**
	 * 通过主键删除
	 * @param id
	 */
	public abstract void delById(Integer id);
	
	/**
	 * 通过主键查找
	 * @param id
	 * @return
	 */
	public abstract T findById(Integer id);
	
	/**
	 * 查找全部并分页
	 * @param p
	 * @return
	 */
	public abstract List<T> findAllByPage(Pagination p);
	
	/**
	 * 查找数据数
	 * @return
	 */
	public abstract int findMax();
	
	/**
	 * 按条件查找总数据数
	 * @param dc
	 * @return
	 */
	public abstract Integer findMaxByCondition(DetachedCriteria dc);
	
	/**
	 * DetachedCriteria条件查询
	 * @param dc
	 * @return
	 */
	public abstract List<T> findByCondition(DetachedCriteria dc);
	
	/**
	 * DetachedCriteria条件查询并分页
	 * @param dc
	 * @param p
	 * @return
	 */
	public abstract List<T> findByConditionPage(DetachedCriteria dc, Pagination p);
	
	/**
	 * 自定义hql语句查询并分页
	 * hql语句中不需传入参数
	 */
	public abstract List<T> findByHqlAndPage(String hql, Pagination p);
	
	/**
	 * 自定义hql语句查询并分页
	 * value传入hql语句中单一参数
	 */
	public abstract List<T> findByHqlAndPage(String hql, Object value, Pagination p);
	
	/**
	 * 自定义hql语句查询并分页
	 * values是传入hql语句中需传入的参数数组
	 */
	public abstract List<T> findByHqlAndPage(String hql, Object[] values, Pagination p);
}
