package org.java.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.java.dao.UserRightDao;
import org.java.entity.UserRight;
import org.java.entity.Pagination;
import org.java.service.UserRightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRightServiceImpl implements UserRightService {

	@Autowired
	private UserRightDao dao;

	public void add(UserRight ur){
		dao.save(ur);
	}

	public void del(Integer id) {
		dao.delById(id);
	}

	public List<UserRight> findByCondition(UserRight ur) {
		DetachedCriteria dc=DetachedCriteria.forClass(UserRight.class);
		return dao.findByCondition(dc);
	}

	public List<UserRight> findByConditionPage(UserRight ur, Pagination p) {
		DetachedCriteria dc=DetachedCriteria.forClass(UserRight.class);
		return dao.findByConditionPage(dc, p);
	}

	public UserRight findById(Integer id) {
		return dao.findById(id);
	}

	public List<UserRight> findByPage(Pagination p) {
		return dao.findAllByPage(p);
	}

	public int findMax() {
		return dao.findMax();
	}

	public int findMaxByCondition(UserRight ur) {
		DetachedCriteria dc=DetachedCriteria.forClass(UserRight.class);
		return dao.findMaxByCondition(dc);
	}

	public void update(UserRight ur) {
		dao.update(ur);
	}

}
