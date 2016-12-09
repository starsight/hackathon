package org.java.entity;

public class Pagination {

	private int currentPage = 1;  //当前页
	private int size = 5;			//每页信息数
	private int count;				//总信息数

	//是否有下一页
	public boolean hasNextPage() {
		return (currentPage >= getMaxPage()) ? false : true;
	}
	public boolean getHasNextPage(){
		return hasNextPage();
	}
	
	//是否有上一页
	public boolean hasPrevPage() {
		return (currentPage <= 1) ? false : true;
	}
	public boolean getHasPrevPage() {
		return hasPrevPage();
	}

	//得到当前页第一个下标
	public int getFirstIndex() {
		return (currentPage - 1) * size;
	}

	//得到当前页最后一个下标
	public int getNextIndex() {
		return currentPage * size;
	}
	
	//得到总页数
	public int getMaxPage() {
		return (count % size == 0) ? (count / size) : ((count / size) + 1);
	}
	
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
