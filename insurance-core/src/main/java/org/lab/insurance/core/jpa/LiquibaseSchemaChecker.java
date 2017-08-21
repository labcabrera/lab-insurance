package org.lab.insurance.core.jpa;

import java.sql.Connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;

@Deprecated
public class LiquibaseSchemaChecker {

	public static final String DEFAULT_MASTER_CHANGELOG = "dbchangelog/db.changelog-master.xml";
	private static final Logger LOG = LoggerFactory.getLogger(LiquibaseSchemaChecker.class);

	public void checkSchema() throws LiquibaseException {
		checkSchema(DEFAULT_MASTER_CHANGELOG);
	}

	public void checkSchema(String schema) throws LiquibaseException {
		Connection connection = resolveConnection();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor(classLoader);
		Database database = DatabaseFactory.getInstance()
				.findCorrectDatabaseImplementation(new JdbcConnection(connection));
		Liquibase liquibase = new Liquibase(schema, resourceAccessor, database);
		liquibase.forceReleaseLocks();
		LOG.warn("Comprobando actualizaciones de esquema de BBDD");
		liquibase.update("");
		LOG.warn("Comprobacion de esquema de BBDD finalizada");
	}

	private Connection resolveConnection() {
		throw new RuntimeException("NOT IMPLEMENTED");
		// ServerSession sess = entityManagerProvider.get().unwrap(ServerSession.class);
		// return sess.getAccessor().getConnection();
	}
}
