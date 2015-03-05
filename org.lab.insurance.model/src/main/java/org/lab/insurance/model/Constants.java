package org.lab.insurance.model;

public class Constants {

	public static final String PERSISTENCE_UNIT_NAME = "org.lab.insurance.model.jpa";

	public static final String CONFIGURATION_BASE_CLASSPATH_FILE = "org.lab.insurance.properties";

	/** Nombre de la clave en el fichero de configuracion que indica la ruta del repositorio de ficheros de la aplicacion */
	public static final String CONFIGURATION_KEY_REPOSITORY_BASE_PATH = "repository.path.base";

	/**
	 * Nombre de la clave en el fichero de configuracion que indica la ruta relativa al repositorio del directorio de plantillas de la
	 * aplicacion
	 */
	public static final String CONFIGURATION_KEY_REPOSITORY_TEMPLATES_PATH = "repository.path.templates";

	/**
	 * Nombre de la clave en el fichero de configuracion que indica la ruta relativa al repositorio del directorio donde se guardan las
	 * cartas generadas por la aplicacion
	 */
	public static final String CONFIGURATION_KEY_REPOSITORY_LETTERS_PATH = "repository.path.letters";

	/**
	 * Nombre de la clave en el fichero de configuracion que indica la ruta relativa al repositorio del directorio donde se guardan los
	 * reports
	 */
	public static final String CONFIGURATION_KEY_REPOSITORY_REPORTS_PATH = "repository.path.reports";

	/**
	 * <ul>
	 * <li>INITIAL: el contrato esta dado de alta pero no se ha recibido el pago.</li>
	 * <li>PAYED: se ha recibido la confirmacion de todos los pagos iniciales pero aun no han sido valorizados.</li>
	 * <li>ACTIVE: contrato en vigor.</li>
	 * <li>CLOSED: contrato cerrado.</li>
	 * </ul>
	 */
	public static final class ContractStates {
		public static final String INITIAL = "CONTRACT_INITIAL";
		public static final String PAYED = "CONTRACT_PAYED";
		public static final String ACTIVE = "CONTRACT_ACTIVE";
		public static final String CLOSED = "CONTRACT_CLOSED";
	}

	public static final class OrderStates {
		public static final String INITIAL = "ORDER_INITIAL";
		public static final String PROCESSED = "ORDER_PROCESSED";
		public static final String VALUED = "ORDER_VALUED";
		public static final String ACCOUNTED = "ORDER_ACCOUNTED";
	}

	public static final class MarketOrderStates {
		public static final String INITIAL = "MARKET_ORDER_INITIAL";
		public static final String PROCESSED = "MARKET_ORDER_PROCESSED";
		public static final String VALUED = "MARKET_ORDER_VALUED";
		public static final String ACCOUNTED = "MARKET_ORDER_ACCOUNTED";
	}

}
