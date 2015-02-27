package com.cnp.ciis.services.indexing;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.CommonParams;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.junit.Test;
import org.lab.insurance.services.indexing.EntityIndexer;
import org.lab.insurance.services.indexing.IndexingService;

public class IndexingServiceTest {

	@Test
	public void test() throws Exception {

		EntityIndexer<File> fileIndexer = new EntityIndexer<File>() {
			@Override
			public SolrInputDocument index(File file) {
				try {
					String name = file.getName();
					String content = FileUtils.readFileToString(file);
					SolrInputDocument document = new SolrInputDocument();
					document.addField("name_s", name);
					document.addField("text_s", content);
					document.addField("id", name.hashCode());
					return document;
				} catch (Exception ex) {
					throw new RuntimeException(ex);
				}
			}
		};

		IndexingService indexingService = new IndexingService("solr", "ciis");
		Collection<File> files = FileUtils.listFiles(new File("src/test/resources/data"), TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
		for (File file : files) {
			indexingService.addToIndex(file, fileIndexer);
		}
		indexingService.commit();

		ModifiableSolrParams solrParams = new ModifiableSolrParams();
		solrParams.add(CommonParams.Q, "text_s:*patata*");
		QueryResponse queryResponse = indexingService.rawQuery(solrParams);
		SolrDocumentList results = queryResponse.getResults();
		System.out.println("Results: " + results.size());
		for (SolrDocument document : results) {
			System.out.println(document);
		}
		System.out.println("End");

		// String solrDir = "solr";
		// CoreContainer container = new CoreContainer(solrDir);
		// container.load();
		// EmbeddedSolrServer server = new EmbeddedSolrServer(container, "ciis");
		// for (File file : files) {
		// String name = file.getName();
		// String content = FileUtils.readFileToString(file);
		// SolrInputDocument document = new SolrInputDocument();
		// document.addField("name_s", name);
		// document.addField("text_s", content);
		// document.addField("id", name.hashCode());
		// server.add(document);
		// }
		// server.commit();
		// Thread.sleep(5000);
		// container.shutdown();
		// server.shutdown();
		//
		// container = new CoreContainer(solrDir);
		// container.load();
		// server = new EmbeddedSolrServer(container, "ciis");
		// ModifiableSolrParams solrParams = new ModifiableSolrParams();
		// solrParams.add(CommonParams.Q, "text_s:*patata*");
		// QueryResponse queryResponse = server.query(solrParams);
		// SolrDocumentList results = queryResponse.getResults();
		// System.out.println("Results: " + results.size());
		// for (SolrDocument document : results) {
		// System.out.println(document);
		// }
		// System.out.println("End");
	}
}
