package org.java.service;

import java.util.List;

import org.java.entity.Pic;
import org.java.entity.Pagination;

public interface PicService {

	public abstract void add(Pic p);

	public abstract void update(Pic p);

	public abstract void del(Integer id);

	public abstract Pic findById(Integer id);

	public abstract int findMaxByCondition(Pic p);

	public abstract int findMax();

	public abstract List<Pic> findByPage(Pagination p);

	public abstract List<Pic> findByConditionPage(Pic p, Pagination page);

	public abstract List<Pic> findByCondition(Pic p);

	public abstract List<Pic> findAll();
}
