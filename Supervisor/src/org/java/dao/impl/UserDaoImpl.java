package org.java.dao.impl;

import org.java.dao.UserDao;
import org.java.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	public UserDaoImpl(){
		super(User.class);
	}
}
