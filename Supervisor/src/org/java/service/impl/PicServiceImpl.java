package org.java.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.java.dao.PicDao;
import org.java.entity.Pic;
import org.java.entity.Pagination;
import org.java.service.PicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PicServiceImpl implements PicService {

	@Autowired
	private PicDao dao;

	public void add(Pic p){
		dao.save(p);
	}

	public void del(Integer id) {
		dao.delById(id);
	}

	public List<Pic> findByCondition(Pic p) {
		DetachedCriteria dc=DetachedCriteria.forClass(Pic.class);
		dc.add(Restrictions.eq("user", p.getUser()));
		return dao.findByCondition(dc);
	}

	public List<Pic> findByConditionPage(Pic p, Pagination page) {
		DetachedCriteria dc=DetachedCriteria.forClass(Pic.class);
		return dao.findByConditionPage(dc, page);
	}

	public Pic findById(Integer id) {
		return dao.findById(id);
	}

	public List<Pic> findByPage(Pagination p) {
		return dao.findAllByPage(p);
	}

	public int findMax() {
		return dao.findMax();
	}

	public int findMaxByCondition(Pic p) {
		DetachedCriteria dc=DetachedCriteria.forClass(Pic.class);
		return dao.findMaxByCondition(dc);
	}

	public void update(Pic p) {
		dao.update(p);
	}

	public List<Pic> findAll() {
		DetachedCriteria dc=DetachedCriteria.forClass(Pic.class);
		return dao.findByCondition(dc);
	}

}
