package org.lab.insurance.services.jpa;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.xml.parsers.ParserConfigurationException;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.diff.output.DiffOutputControl;
import liquibase.exception.LiquibaseException;
import liquibase.integration.commandline.CommandLineUtils;
import liquibase.resource.FileSystemResourceAccessor;
import liquibase.snapshot.InvalidExampleException;

import org.apache.commons.io.FileUtils;

public class LiquibaseSchemaGenerator {

	private static final String master = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+ "\n\n<databaseChangeLog xmlns=\"http://www.liquibase.org/xml/ns/dbchangelog\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-2.0.xsd\">"
			+ "\n\n\t<include file=\"dbchangelog/db.changelog-initial.xml\" />\n\n</databaseChangeLog>";
	public static final String DEFAULT_SRC_FOLDER = "src/main/resources/";
	public static final String DEFAULT_MASTER_CHANGELOG = "dbchangelog/db.changelog-master.xml";
	public static final String DEFAULT_INITIAL_CHANGELOG = "dbchangelog/db.changelog-initial.xml";
	public static final String DEFAULT_DIFF_CHANGELOG = "dbchangelog/db.changelog";

	@Inject
	private Provider<EntityManager> entityManagerProvider;

	public void generateInitialSchema() throws IOException, InvalidExampleException, ParserConfigurationException, LiquibaseException {
		Connection connection = resolveConnection();
		Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
		// Bootstrap.createSchema(config);
		File masterFile = new File(DEFAULT_SRC_FOLDER + DEFAULT_MASTER_CHANGELOG);
		FileUtils.writeStringToFile(masterFile, master);
		String changeLogFile = DEFAULT_SRC_FOLDER + DEFAULT_INITIAL_CHANGELOG;
		Database originalDatabase = database;
		String catalogName = database.getDefaultCatalogName();
		String schemaName = database.getDefaultSchemaName();
		String snapshotTypes = null;
		String author = "lab";
		String context = null;
		String dataDir = null;
		DiffOutputControl diffOutputControl = new DiffOutputControl();
		CommandLineUtils.doGenerateChangeLog(changeLogFile, originalDatabase, catalogName, schemaName, snapshotTypes, author, context, dataDir, diffOutputControl);
		// limpiamos el esquema generado
		// File initialFile = new File(DEFAULT_SRC_FOLDER + DEFAULT_INITIAL_CHANGELOG);
		// FileUtils.writeStringToFile(initialFile, changeXmlValues(FileUtils.readFileToString(initialFile)));
		Liquibase liquibase = new Liquibase(DEFAULT_MASTER_CHANGELOG, new FileSystemResourceAccessor(DEFAULT_SRC_FOLDER), database);
		liquibase.changeLogSync("initial");
		database.close();
	}

	private Connection resolveConnection() {
		// ServerSession sess = entityManagerProvider.get().unwrap(ServerSession.class);
		// Connection connection = sess.getAccessor().getConnection();
		return null;
	}
}
