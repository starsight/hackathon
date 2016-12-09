package org.java.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.java.dao.UserDao;
import org.java.dao.UserRightDao;
import org.java.entity.User;
import org.java.entity.Pagination;
import org.java.entity.UserRight;
import org.java.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao dao;
	
	@Autowired
	private UserRightDao urdao;

	public void add(User u){
		dao.save(u);
	}

	public void del(Integer id) {
		dao.delById(id);
	}

	public List<User> findByCondition(User u) {
		DetachedCriteria dc=DetachedCriteria.forClass(User.class);
		dc.add(Restrictions.eq("name", u.getName()));
		dc.add(Restrictions.eq("pwd", u.getPwd()));
		return dao.findByCondition(dc);
	}

	public List<User> findByConditionPage(User u, Pagination p) {
		DetachedCriteria dc=DetachedCriteria.forClass(User.class);
		return dao.findByConditionPage(dc, p);
	}

	public User findById(Integer id) {
		return dao.findById(id);
	}

	public List<User> findByPage(Pagination p) {
		return dao.findAllByPage(p);
	}

	public int findMax() {
		return dao.findMax();
	}

	public int findMaxByCondition(User u) {
		DetachedCriteria dc=DetachedCriteria.forClass(User.class);
		return dao.findMaxByCondition(dc);
	}

	public void update(User u) {
		dao.update(u);
	}

	/**
	 * 用户登录的方法
	 */
	public User login(String uname,String upwd,Integer uright) {
		DetachedCriteria dc=DetachedCriteria.forClass(User.class);
		dc.add(Restrictions.eq("name", uname));
		dc.add(Restrictions.eq("pwd", upwd));
		UserRight userRight=urdao.findById(uright);
		dc.add(Restrictions.eq("userRight", userRight));
		
		List<User> list=dao.findByCondition(dc);
		if(list.isEmpty()){
			return null;
		}else{
			return list.get(0);
		}
	}

}
