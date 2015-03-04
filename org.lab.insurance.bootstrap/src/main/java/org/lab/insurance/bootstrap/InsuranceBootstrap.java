package org.lab.insurance.bootstrap;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.lab.insurance.bootstrap.feeders.AgreementFeeder;
import org.lab.insurance.bootstrap.feeders.BaseAssetFeeder;
import org.lab.insurance.bootstrap.feeders.CountryFeeder;
import org.lab.insurance.bootstrap.feeders.CurrencyFeeder;
import org.lab.insurance.bootstrap.feeders.GuaranteePriceFeeder;
import org.lab.insurance.bootstrap.feeders.HolidayCalendarFeeder;
import org.lab.insurance.bootstrap.feeders.HolidayFeeder;
import org.lab.insurance.bootstrap.feeders.StateDefinitionFeeder;
import org.lab.insurance.bootstrap.feeders.TriggerFeeder;
import org.lab.insurance.bootstrap.mock.AssetPriceFeeder;
import org.lab.insurance.engine.guice.InsuranceCoreModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.Transactional;

public class InsuranceBootstrap implements Runnable {

	private static final Class<?>[] RAW_FEEDERS = { CountryFeeder.class, CurrencyFeeder.class, HolidayCalendarFeeder.class, HolidayFeeder.class, BaseAssetFeeder.class,
			StateDefinitionFeeder.class, AgreementFeeder.class, TriggerFeeder.class, GuaranteePriceFeeder.class };
	private static final Class<?>[] SERVICE_FEEDERS = { AssetPriceFeeder.class };
	private static final Logger LOG = LoggerFactory.getLogger(InsuranceBootstrap.class);

	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new InsuranceCoreModule());
		PersistService persistService = injector.getInstance(PersistService.class);
		persistService.start();
		Long t0 = System.currentTimeMillis();
		try {
			injector.getInstance(InsuranceBootstrap.class).run();
			LOG.debug("Boostrap completed in {} ms", System.currentTimeMillis() - t0);
		} finally {
			persistService.stop();
		}
	}

	private final List<Runnable> runnableRawFeeders;
	private final List<Runnable> runnableServiceFeeders;

	@Inject
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public InsuranceBootstrap(Injector injector) {
		runnableRawFeeders = new ArrayList<Runnable>();
		for (Class feederClass : RAW_FEEDERS) {
			runnableRawFeeders.add((Runnable) injector.getInstance(feederClass));
		}
		runnableServiceFeeders = new ArrayList<Runnable>();
		for (Class feederClass : SERVICE_FEEDERS) {
			runnableServiceFeeders.add((Runnable) injector.getInstance(feederClass));
		}
	}

	/**
	 * Nota: tenemos servicios que solamente insertan las entidades a traves del entityManager y servicios que acceder al
	 * actionExecutionService que controla que ya controla de forma autonoma las transacciones de modo que separamos en dos grupos para
	 * hacer transaccional el primero.
	 */
	@Override
	public void run() {
		run(runnableRawFeeders);
		run(runnableServiceFeeders);
	}

	@Transactional
	protected void run(List<Runnable> runnables) {
		for (Runnable runnable : runnables) {
			LOG.debug("Running " + runnable.getClass().getName());
			runnable.run();
		}
	}
}
