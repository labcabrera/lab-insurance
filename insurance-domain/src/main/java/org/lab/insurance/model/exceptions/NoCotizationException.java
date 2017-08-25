package org.lab.insurance.model.exceptions;

import java.util.Date;

import org.lab.insurance.model.insurance.BaseAsset;

/**
 * Excepcion que representa la ausencia de un fondo en una determinada fecha.
 */
@SuppressWarnings("serial")
public class NoCotizationException extends RuntimeException {

	private final BaseAsset asset;
	private final Date when;

	public NoCotizationException(BaseAsset asset, Date when) {
		this.asset = asset;
		this.when = when;
	}

	public BaseAsset getAsset() {
		return asset;
	}

	public Date getValueDate() {
		return when;
	}
}
