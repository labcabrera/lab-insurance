package org.lab.insurance.bootstrap;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.lab.insurance.bootstrap.feeders.AssetPriceFeeder;
import org.lab.insurance.bootstrap.feeders.BaseAssetFeeder;
import org.lab.insurance.bootstrap.feeders.CountryFeeder;
import org.lab.insurance.bootstrap.feeders.CurrencyFeeder;
import org.lab.insurance.bootstrap.feeders.HolidayCalendarFeeder;
import org.lab.insurance.engine.guice.InsuranceCoreModule;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.Transactional;

public class InsuranceBootstrap implements Runnable {

	private static final Class<?>[] FEEDERS = { CountryFeeder.class, CurrencyFeeder.class, HolidayCalendarFeeder.class, BaseAssetFeeder.class, AssetPriceFeeder.class };

	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new InsuranceCoreModule());
		PersistService persistService = injector.getInstance(PersistService.class);
		persistService.start();
		try {
			injector.getInstance(InsuranceBootstrap.class).run();
		} finally {
			persistService.stop();
		}
	}

	private final List<Runnable> runnableFeeders;

	@Inject
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public InsuranceBootstrap(Injector injector) {
		runnableFeeders = new ArrayList<Runnable>();
		for (Class feederClass : FEEDERS) {
			runnableFeeders.add((Runnable) injector.getInstance(feederClass));
		}
	}

	@Override
	@Transactional
	public void run() {
		for (Runnable runnable : runnableFeeders) {
			runnable.run();
		}
	}
}
