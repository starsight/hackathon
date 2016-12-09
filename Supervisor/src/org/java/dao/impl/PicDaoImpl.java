package org.java.dao.impl;

import org.java.dao.PicDao;
import org.java.entity.Pic;
import org.springframework.stereotype.Repository;

@Repository
public class PicDaoImpl extends BaseDaoImpl<Pic> implements PicDao {

	public PicDaoImpl(){
		super(Pic.class);
	}
}
