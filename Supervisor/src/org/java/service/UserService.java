package org.java.service;

import java.util.List;

import org.java.entity.User;
import org.java.entity.Pagination;

public interface UserService {

	public abstract void add(User u);

	public abstract void update(User u);

	public abstract void del(Integer id);

	public abstract User findById(Integer id);

	public abstract int findMaxByCondition(User u);

	public abstract int findMax();

	public abstract List<User> findByPage(Pagination p);

	public abstract List<User> findByConditionPage(User u, Pagination p);

	public abstract List<User> findByCondition(User u);

	/**
	 * @param uname 
	 * @param pwd 
	 * @param uright 
	 * @return 
	 */
	public abstract User login(String uname,String upwd,Integer uright);
	
}
