package org.lab.insurance.web.servlet;

import javax.servlet.ServletContextListener;

import org.jboss.resteasy.plugins.guice.GuiceResteasyBootstrapServletContextListener;
import org.lab.insurance.core.jpa.LiquibaseSchemaChecker;
import org.lab.insurance.core.scheduler.SchedulerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Injector;
import com.google.inject.persist.PersistService;

/**
 * {@link ServletContextListener} encargado de iniciar los servicios de la aplicación una vez se ha inicializado el módulo de Guice.
 */
public class GuiceServletListener extends GuiceResteasyBootstrapServletContextListener {

	private static final Logger LOG = LoggerFactory.getLogger(GuiceServletListener.class);

	@Override
	protected void withInjector(Injector injector) {
		super.withInjector(injector);
		LOG.debug("Inicializando servicios");
		try {
			injector.getInstance(PersistService.class).start();
			injector.getInstance(LiquibaseSchemaChecker.class).checkSchema();
			SchedulerService schedulerService = injector.getInstance(SchedulerService.class);
			schedulerService.registerJobs();
			schedulerService.getScheduler().start();
		} catch (Exception ex) {
			LOG.error("Error durante la comprobacion del esquema");
		}
	}
}
