package org.lab.insurance.services.insurance;

import java.util.Date;
import java.util.List;

import org.lab.insurance.model.exceptions.NoCotizationException;
import org.lab.insurance.model.insurance.AssetPrice;
import org.lab.insurance.model.insurance.BaseAsset;

public class CotizationsService {

	/**
	 * Obtiene el precio de un asset para un determinado dia.
	 * 
	 * @param asset
	 * @param when
	 * @return
	 * @throws NoCotizationException Si no encuentra el precio.
	 */
	public AssetPrice findPriceAtDate(BaseAsset asset, Date when) throws NoCotizationException {
		throw new RuntimeException("Not implemented jpa -> mongo");
		// EntityManager entityManager = entityManagerProvider.get();
		// TypedQuery<AssetPrice> query = entityManager.createNamedQuery("AssetPrice.selectByDate", AssetPrice.class);
		// query.setParameter("asset", asset).setParameter("date", when);
		// try {
		// return query.getSingleResult();
		// }
		// catch (NoResultException ex) {
		// throw new NoCotizationException(asset, when);
		// }
	}

	/**
	 * Obtiene la lista de precios de un fondo dentro del rango especificado.
	 * 
	 * @param asset
	 * @param from
	 * @param to
	 * @return
	 */
	public List<AssetPrice> findPricesInRange(BaseAsset asset, Date from, Date to) {
		throw new RuntimeException("Not implemented jpa -> mongo");
		// EntityManager entityManager = entityManagerProvider.get();
		// TypedQuery<AssetPrice> query = entityManager.createNamedQuery("AssetPrice.selectInRange", AssetPrice.class);
		// query.setParameter("asset", asset).setParameter("from", from).setParameter("to", to);
		// return query.getResultList();
	}

	/**
	 * Obtiene el ultimo precio de un fondo anterior a una fecha dada. Devuelve <code>null</code> si no encuentra
	 * precios anteriores a la fecha dada.
	 * 
	 * @param asset
	 * @param notAfter
	 * @return
	 */
	public AssetPrice findLastPrice(BaseAsset asset, Date notAfter) {
		throw new RuntimeException("Not implemented jpa -> mongo");
		// EntityManager entityManager = entityManagerProvider.get();
		// TypedQuery<AssetPrice> query = entityManager.createNamedQuery("AssetPrice.selectLast", AssetPrice.class);
		// query.setParameter("asset", asset).setParameter("notAfter", notAfter);
		// query.setMaxResults(1);
		// try {
		// return query.getSingleResult();
		// }
		// catch (Exception ex) {
		// return null;
		// }
	}
}
