package org.lab.insurance.services.insurance;

import java.util.Date;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.lab.insurance.model.exceptions.NoCotizationException;
import org.lab.insurance.model.jpa.insurance.AssetPrice;
import org.lab.insurance.model.jpa.insurance.BaseAsset;

public class CotizationsService {

	private final Provider<EntityManager> entityManagerProvider;

	@Inject
	public CotizationsService(Provider<EntityManager> entityManagerProvider) {
		this.entityManagerProvider = entityManagerProvider;
	}

	public AssetPrice getPrice(BaseAsset asset, Date when) throws NoCotizationException {
		EntityManager entityManager = entityManagerProvider.get();
		TypedQuery<AssetPrice> query = entityManager.createNamedQuery("AssetPrice.selectByDateAndAsset", AssetPrice.class);
		query.setParameter("asset", asset);
		query.setParameter("date", when);
		try {
			return query.getSingleResult();
		} catch (NoResultException ex) {
			throw new NoCotizationException(asset, when);
		}
	}
}
