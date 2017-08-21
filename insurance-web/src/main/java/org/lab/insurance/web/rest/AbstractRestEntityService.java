package org.lab.insurance.web.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Validator;

import org.apache.commons.lang3.StringUtils;
import org.lab.insurance.core.jpa.FiqlParser;
import org.lab.insurance.model.common.internal.Message;
import org.lab.insurance.model.common.internal.SearchOrder;
import org.lab.insurance.model.common.internal.SearchParams;
import org.lab.insurance.model.common.internal.SearchResults;

public abstract class AbstractRestEntityService<T> {

	protected static final Integer DEFAULT_MAX_RESULTS = 20;

	@Inject
	protected FiqlParser fiqlParser;
	@Inject
	protected Provider<EntityManager> entityManagerProvider;
	@Inject
	protected Validator validator;

	protected abstract Class<T> getEntityClass();

	protected SearchResults<T> find(SearchParams params) {
		Class<T> entityClass = getEntityClass();
		EntityManager entityManager = entityManagerProvider.get();
		CriteriaBuilder builder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> criteria = builder.createQuery(entityClass);
		Root<T> root = criteria.from(entityClass);
		Predicate predicate = StringUtils.isBlank(params.getQueryString()) ? builder.conjunction() : fiqlParser.parse(params.getQueryString(), builder, root).build();
		criteria.where(predicate);
		// Ordenación de los resultados
		addOrderParams(params, builder, criteria, root);
		buildOrderCriteria(criteria, builder, root);
		TypedQuery<T> query = entityManager.createQuery(criteria);
		configureQueryRange(query, params);
		SearchResults<T> searchResults = new SearchResults<T>();
		searchResults.setResults(query.getResultList());
		// Numero de resultados
		CriteriaQuery<Long> criteriaCount = builder.createQuery(Long.class);
		criteriaCount.select(builder.count(root));
		criteriaCount.where(predicate);
		Long totalItems = entityManager.createQuery(criteriaCount).getSingleResult();
		Long totalPages = (totalItems + (totalItems % params.getItemsPerPage())) / params.getItemsPerPage();
		searchResults.setTotalItems(totalItems);
		searchResults.setTotalPages(totalPages);
		searchResults.setCurrentPage(params.getCurrentPage());
		searchResults.setItemsPerPage(params.getItemsPerPage());
		return searchResults;
	}

	/**
	 * Método encargado de realizar la ordenación de los registros basándose en {@link SearchParams#getOrder()} y
	 * {@link SearchParams#getOrderColumn()}
	 * 
	 * @param params
	 * @param criteriaBuilder
	 * @param criteriaQuery
	 * @param root
	 */
	protected void addOrderParams(SearchParams params, final CriteriaBuilder criteriaBuilder, final CriteriaQuery<T> criteriaQuery, final Root<T> root) {
		if (params != null && params.getOrder() != null) {
			String field = params.getOrderColumn();
			SearchOrder order = params.getOrder();
			String[] split = field.split("\\s");
			List<Order> orders = new ArrayList<Order>();
			for (String s : split) {
				String[] dots = s.split("\\.");
				Path<?> path = null;
				for (String string : dots) {
					path = (path == null) ? root.get(string) : path.get(string);
				}
				if (path != null) {
					orders.add(order == SearchOrder.ASC ? criteriaBuilder.asc(path) : criteriaBuilder.desc(path));
				}
			}
			if (orders.size() > 0) {
				criteriaQuery.orderBy(orders);
			}
		}
	}

	/**
	 * Podemos sobreescribir este metodo para establecer la ordenacion de resultados en nuestras consultas.
	 * 
	 * @param criteria
	 * @param builder
	 * @param root
	 */
	protected void buildOrderCriteria(CriteriaQuery<T> criteria, CriteriaBuilder builder, Root<T> root) {
	}

	/**
	 * Podemos sobreescribir este metodo para establecer el numero de resultados por defecto.
	 * 
	 * @return
	 */
	protected Integer getDefaultsItemsPerPage() {
		return DEFAULT_MAX_RESULTS;
	}

	protected void configureQueryRange(Query query, SearchParams params) {
		if (params.getCurrentPage() == null || params.getCurrentPage() < 1) {
			params.setCurrentPage(1);
		}
		if (params.getItemsPerPage() == null || params.getItemsPerPage() < 1) {
			params.setItemsPerPage(getDefaultsItemsPerPage());
		}
		query.setMaxResults(params.getItemsPerPage());
		query.setFirstResult(params.getItemsPerPage() * (params.getCurrentPage() - 1));
	}

	protected List<T> like(LikeParams likeParams) {
		EntityManager entityManager = entityManagerProvider.get();
		TypedQuery<T> query = null;
		if (likeParams.query != null) {
			query = entityManager.createQuery(likeParams.query, getEntityClass());
			for (String parameter : likeParams.parameters.keySet()) {
				query.setParameter(parameter, likeParams.parameters.get(parameter));
			}
		} else {
			query = entityManager.createQuery(likeParams.criteria);
		}
		return query.setMaxResults(likeParams.maxResults).getResultList();
	}

	protected Message<T> validate(T entity) {
		// TODO
		return new Message<T>();
	}

	protected class LikeParams {

		private LikeParams(Integer maxResults) {
			this.maxResults = maxResults;
			if (maxResults == null) {
				this.maxResults = DEFAULT_MAX_RESULTS;
			}
		}

		private LikeParams(String expression, Integer maxResults) {
			this(maxResults);
			this.parameters.put("expression", "%" + expression + "%");
		}

		LikeParams(String expression, Integer maxResults, String query) {
			this(expression, maxResults);
			this.query = query;
		}

		LikeParams(Integer maxResults, CriteriaQuery<T> query) {
			this(maxResults);
			this.criteria = query;
		}

		Map<String, Object> parameters = new HashMap<String, Object>();
		Integer maxResults;
		String query;
		CriteriaQuery<T> criteria;

		LikeParams parameter(String parameter, Object value) {
			this.parameters.put(parameter, value);
			return this;
		}
	}

}
