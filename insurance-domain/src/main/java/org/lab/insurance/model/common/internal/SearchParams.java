package org.lab.insurance.model.common.internal;

import java.io.Serializable;

/**
 * Entidad que representa los parámetros de búsqueda desde el front:
 * <ul>
 * <li>Página actual de resultados (paginación)</li>
 * <li>Número de resultados por página</li>
 * <li>Expresión FIQL con las condiciones de búsqueda</li>
 * </ul>
 * Generalmente las búsquedas devolverán un objeto de tipo {@link SearchResults} con los resultados.
 */
@SuppressWarnings("serial")
public class SearchParams implements Serializable {

	private Integer currentPage;
	private Integer itemsPerPage;
	private String queryString;
	private String orderColumn;
	private SearchOrder order;

	/**
	 * Constructor sin parámetros
	 */
	public SearchParams() {
	}

	/**
	 * Constructor a partir de la expresión de búsqueda, página actual y número de resultados por página
	 * 
	 * @param currentPage
	 * @param itemsPerPage
	 * @param queryString
	 */
	public SearchParams(Integer currentPage, Integer itemsPerPage, String queryString) {
		this.currentPage = currentPage;
		this.itemsPerPage = itemsPerPage;
		this.queryString = queryString;
	}

	public SearchParams(Integer currentPage, Integer itemsPerPage, String queryString, String orderColumn, SearchOrder order) {
		super();
		this.currentPage = currentPage;
		this.itemsPerPage = itemsPerPage;
		this.queryString = queryString;
		this.orderColumn = orderColumn;
		this.order = order;
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

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public SearchParams withQueryString(String queryString) {
		this.queryString = queryString;
		return this;
	}

	public SearchParams withCurrentPage(Integer page) {
		this.currentPage = page;
		return this;
	}

	public SearchParams withItemsPerPage(Integer value) {
		this.itemsPerPage = value;
		return this;
	}

	public String getOrderColumn() {
		return orderColumn;
	}

	public void setOrderColumn(String orderColumn) {
		this.orderColumn = orderColumn;
	}

	public SearchOrder getOrder() {
		return order;
	}

	public void setOrder(SearchOrder order) {
		this.order = order;
	}
}