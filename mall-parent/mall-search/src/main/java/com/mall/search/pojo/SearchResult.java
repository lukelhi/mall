package com.mall.search.pojo;

import java.util.List;

public class SearchResult {
	private List<SearchItem> itemList;//商品的列表
	private Long recordCount;//查询结果的总记录数
	private int pageCount;//总页数
	private int curPage;//当前页码
	public List<SearchItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<SearchItem> itemList) {
		this.itemList = itemList;
	}
	public Long getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(Long recordCount) {
		this.recordCount = recordCount;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	@Override
	public String toString() {
		return "SearchResult [itemList=" + itemList + ", recordCount=" + recordCount + ", pageCount=" + pageCount
				+ ", curPage=" + curPage + "]";
	}
	
	
}
