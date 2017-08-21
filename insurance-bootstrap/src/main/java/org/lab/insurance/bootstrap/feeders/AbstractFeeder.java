package org.lab.insurance.bootstrap.feeders;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import net.sf.flatpack.DataSet;
import net.sf.flatpack.DefaultParserFactory;
import net.sf.flatpack.Parser;

import org.apache.commons.lang.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.lab.insurance.model.jpa.geo.Country;
import org.lab.insurance.model.jpa.insurance.BaseAsset;

public abstract class AbstractFeeder implements Runnable {

	private static final char DELIMITER_CHAR = ',';
	private static final char DELIMITER_CHAR_STRING = '"';
	private static final String DATE_PATTERN = "yyyy-MM-dd";

	@Inject
	protected Provider<EntityManager> entityManagerProvider;

	protected DataSet buildParser(String resourceName) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream source = classLoader.getResourceAsStream(resourceName);
		Reader reader = new InputStreamReader(source, Charset.forName("UTF8"));
		Parser parser = DefaultParserFactory.getInstance().newDelimitedParser(reader, DELIMITER_CHAR, DELIMITER_CHAR_STRING);
		return parser.parse();
	}

	protected Date parseDate(String value) {
		return StringUtils.isBlank(value) ? null : DateTimeFormat.forPattern(DATE_PATTERN).parseDateTime(value).toDate();
	}

	protected Country loadCountryFromIso2(String iso2) {
		Country country = null;
		if (StringUtils.isNotBlank(iso2)) {
			country = entityManagerProvider.get().find(Country.class, iso2);
		}
		return country;
	}

	protected BaseAsset loadBaseAssetFromIsin(String isin) {
		BaseAsset country = null;
		if (StringUtils.isNotBlank(isin)) {
			country = entityManagerProvider.get().createNamedQuery("BaseAsset.selectByIsin", BaseAsset.class).setParameter("isin", isin).getSingleResult();
		}
		return country;
	}
}
