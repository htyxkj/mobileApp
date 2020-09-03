package mobile.entity;

import java.util.List;

public class PageInfo<T> {
	private Integer currentPage;//当前页号
	private Integer pageSize; //每页显示条数
	private Integer total; //总行数
	private T condition; //条件
	private List<T> rows; //结果
	private String order; //排序字段
	private Integer totalpages;//总页数
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public T getCondition() {
		return condition;
	}
	public void setCondition(T condition) {
		this.condition = condition;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	public Integer getTotalpages() {
		this.totalpages=this.total%this.pageSize==0?this.total/this.pageSize:this.total/this.pageSize+1;
		return totalpages;
	}
	public void setTotalpages(Integer totalpages) {
		this.totalpages = totalpages;
	}
}
