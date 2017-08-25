package org.lab.insurance.engine.model.prices;

import java.math.BigDecimal;
import java.util.Date;

import org.lab.insurance.engine.model.ActionDefinition;
import org.lab.insurance.engine.model.ActionEntity;
import org.lab.insurance.model.insurance.BaseAsset;

@SuppressWarnings("serial")
@ActionDefinition(endpoint = "direct:guarantee_price_creation")
public class GuaranteePriceCreationAction implements ActionEntity<BaseAsset> {

	private BaseAsset asset;
	private BigDecimal percent;
	private Date from;
	private Date to;
	private Date actionDate;

	public BaseAsset getAsset() {
		return asset;
	}

	public void setAsset(BaseAsset asset) {
		this.asset = asset;
	}

	public BigDecimal getPercent() {
		return percent;
	}

	public void setPercent(BigDecimal percent) {
		this.percent = percent;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	@Override
	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}
}
