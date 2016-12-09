package org.java.service;

import java.util.List;

import org.java.entity.UserRight;
import org.java.entity.Pagination;

public interface UserRightService {

	public abstract void add(UserRight ur);

	public abstract void update(UserRight ur);

	public abstract void del(Integer id);

	public abstract UserRight findById(Integer id);

	public abstract int findMaxByCondition(UserRight ur);

	public abstract int findMax();

	public abstract List<UserRight> findByPage(Pagination p);

	public abstract List<UserRight> findByConditionPage(UserRight ur, Pagination p);

	public abstract List<UserRight> findByCondition(UserRight ur);

}
