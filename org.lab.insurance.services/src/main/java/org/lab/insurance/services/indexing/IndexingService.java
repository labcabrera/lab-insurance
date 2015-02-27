package org.lab.insurance.services.indexing;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.embedded.EmbeddedSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.core.CoreContainer;

/**
 * De momento no es mas que un wrapper de EmbeddedSolrServer.
 */
@Singleton
public class IndexingService {

	private final SolrServer solrServer;

	@Inject
	public IndexingService(@Named("solr.home") String solrHome, @Named("solr.coreName") String coreName) {
		CoreContainer container = new CoreContainer(solrHome);
		container.load();
		solrServer = new EmbeddedSolrServer(container, coreName);
	}

	public <T> void addToIndex(T source, EntityIndexer<T> indexer) throws IOException, SolrServerException {
		SolrInputDocument document = indexer.index(source);
		solrServer.add(document);
	}

	public void commit() throws SolrServerException, IOException {
		solrServer.commit();
	}

	public QueryResponse rawQuery(ModifiableSolrParams solrParams) throws SolrServerException {
		return solrServer.query(solrParams);
	}
}
