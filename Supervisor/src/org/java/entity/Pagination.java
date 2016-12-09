package org.java.entity;

public class Pagination {

	private int currentPage = 1;  //��ǰҳ
	private int size = 5;			//ÿҳ��Ϣ��
	private int count;				//����Ϣ��

	//�Ƿ�����һҳ
	public boolean hasNextPage() {
		return (currentPage >= getMaxPage()) ? false : true;
	}
	public boolean getHasNextPage(){
		return hasNextPage();
	}
	
	//�Ƿ�����һҳ
	public boolean hasPrevPage() {
		return (currentPage <= 1) ? false : true;
	}
	public boolean getHasPrevPage() {
		return hasPrevPage();
	}

	//�õ���ǰҳ��һ���±�
	public int getFirstIndex() {
		return (currentPage - 1) * size;
	}

	//�õ���ǰҳ���һ���±�
	public int getNextIndex() {
		return currentPage * size;
	}
	
	//�õ���ҳ��
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
