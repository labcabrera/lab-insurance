package org.lab.insurance.web.servlet;

import org.jboss.resteasy.plugins.guice.ext.RequestScopeModule;
import org.lab.insurance.engine.guice.InsuranceCoreModule;
import org.lab.insurance.web.rest.AgreementRestService;
import org.lab.insurance.web.rest.AppMailRecipientRestService;
import org.lab.insurance.web.rest.CountryRestService;
import org.lab.insurance.web.rest.FileRestService;
import org.lab.insurance.web.rest.I18nRestService;
import org.lab.insurance.web.rest.SchedulerRestService;
import org.lab.insurance.web.rest.SecurityRestService;
import org.lab.insurance.web.web.json.GsonExceptionHandler;
import org.lab.insurance.web.web.json.GsonMessageBodyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Binder;
import com.google.inject.Module;

/**
 * Modulo de Guice encargado de registrar los servicios REST que expone la aplicacion.
 */
public class RestModule implements Module {

	private static final Logger LOG = LoggerFactory.getLogger(RestModule.class);

	@Override
	public void configure(final Binder binder) {
		LOG.debug("Configuring Guice Module");
		binder.install(new InsuranceCoreModule());
		binder.install(new RequestScopeModule());
		binder.bind(GsonMessageBodyHandler.class);
		binder.bind(GsonExceptionHandler.class);
		bindRestServices(binder);
		// binder.bind(RestPersistFilter.class);
	}

	private void bindRestServices(final Binder binder) {
		binder.bind(AgreementRestService.class);
		binder.bind(CountryRestService.class);
		binder.bind(FileRestService.class);
		binder.bind(I18nRestService.class);
		binder.bind(SecurityRestService.class);
		binder.bind(AppMailRecipientRestService.class);
		binder.bind(SchedulerRestService.class);
	}
}
