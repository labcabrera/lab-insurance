package org.lab.insurance.model.common;

import java.util.List;

public class SearchResults<T> {

	private Integer currentPage;
	private Integer itemsPerPage;
	private Long totalPages;
	private Long totalItems;
	private List<T> results;

	public SearchResults() {
	}

	public SearchResults(Integer currentPage, Integer itemsPerPage, Long count, List<T> results) {
		super();
		this.results = results;
		this.currentPage = currentPage;
		this.itemsPerPage = itemsPerPage;
		this.totalItems = count;
		this.totalPages = (totalItems + (totalItems % itemsPerPage)) / itemsPerPage;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getItemsPerPage() {
		return itemsPerPage;
	}

	public void setItemsPerPage(Integer itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	public Long getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(Long totalItems) {
		this.totalItems = totalItems;
	}

	public Long getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(Long totalPages) {
		this.totalPages = totalPages;
	}

	public List<T> getResults() {
		return results;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}
}
