package org.lab.insurance;

public class Constants {

	public static final String PERSISTENCE_UNIT_NAME = "com.cnp.ciis.model.jpa";
	public static final String APP_PROPERTIES_NAME = "ciis.properties";

	public static final class OrderStates {
		public static final String INITIAL = "INITIAL";
		public static final String PROCESSED = "PROCESSED";
		public static final String VALUED = "VALUED";
		public static final String ACCOUNTED = "ACCOUNTED";
	}

}
