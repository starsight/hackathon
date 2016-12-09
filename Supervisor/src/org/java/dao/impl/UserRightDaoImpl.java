package org.java.dao.impl;

import org.java.dao.UserRightDao;
import org.java.entity.UserRight;
import org.springframework.stereotype.Repository;

@Repository
public class UserRightDaoImpl extends BaseDaoImpl<UserRight> implements UserRightDao {

	public UserRightDaoImpl(){
		super(UserRight.class);
	}
}
