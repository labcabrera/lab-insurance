package org.lab.insurance.services.indexing;

import org.apache.solr.common.SolrInputDocument;

public interface EntityIndexer<T> {

	SolrInputDocument index(T entity);

}
