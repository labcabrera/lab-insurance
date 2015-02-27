package org.lab.insurance.services.jpa;

import java.sql.Connection;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LiquibaseSchemaChecker {

	public static final String DEFAULT_MASTER_CHANGELOG = "dbchangelog/db.changelog-master.xml";
	private static final Logger LOG = LoggerFactory.getLogger(LiquibaseSchemaChecker.class);

	@Inject
	private Provider<EntityManager> entityManagerProvider;

	public void checkSchema() throws LiquibaseException {
		checkSchema(DEFAULT_MASTER_CHANGELOG);
	}

	public void checkSchema(String schema) throws LiquibaseException {
		Connection connection = resolveConnection();
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor(classLoader);
		Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(connection));
		Liquibase liquibase = new Liquibase(schema, resourceAccessor, database);
		liquibase.forceReleaseLocks();
		LOG.warn("Comprobando actualizaciones de esquema de BBDD");
		liquibase.update("");
		LOG.warn("Comprobacion de esquema de BBDD finalizada");
	}

	private Connection resolveConnection() {
		// ServerSession sess = entityManagerProvider.get().unwrap(ServerSession.class);
		// Connection connection = sess.getAccessor().getConnection();
		return null;
	}

}
