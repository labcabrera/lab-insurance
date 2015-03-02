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

	// TODO mover a otro lado
	public static final class OrderStates {
		public static final String INITIAL = "INITIAL";
		public static final String PROCESSED = "PROCESSED";
		public static final String VALUED = "VALUED";
		public static final String ACCOUNTED = "ACCOUNTED";
	}

}
