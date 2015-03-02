package org.lab.insurance.model.jpa.insurance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.lab.insurance.model.HasAsset;
import org.lab.insurance.model.HasIdentifier;

/**
 * Representa el valor de un fondo en un determinado dia.
 */
@Entity
@Table(name = "I_ASSET_PRICE")
@SuppressWarnings("serial")
@NamedQueries({
		@NamedQuery(name = "AssetPrice.selectByDate", query = "select e from AssetPrice e where e.priceDate = :date and e.asset = :asset"),
		@NamedQuery(name = "AssetPrice.selectLast", query = "select e from AssetPrice e where e.asset = :asset and e.priceDate <= :notAfter order by e.priceDate desc"),
		@NamedQuery(name = "AssetPrice.selectInRange", query = "select e from AssetPrice e where e.asset = :asset and e.priceDate >= :from and e.priceDate <= :to order by e.priceDate") })
public class AssetPrice implements Serializable, HasIdentifier<String>, HasAsset {

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "system-uuid")
	private String id;

	@ManyToOne
	@JoinColumn(name = "ASSET_ID", nullable = false)
	private BaseAsset asset;

	@Column(name = "PRICE_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date priceDate;

	@Column(name = "PRICE_IN_EUROS", nullable = false)
	private BigDecimal priceInEuros;

	@Column(name = "BUY_PRICE_IN_EUROS", nullable = false)
	private BigDecimal buyPriceInEuros;

	@Column(name = "SELL_PRICE_IN_EUROS", nullable = false)
	private BigDecimal sellPriceInEuros;

	@Column(name = "GENERATED", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date generated;

	@Override
	public String getId() {
		return id;
	}

	@Override
	public void setId(String id) {
		this.id = id;
	}

	@Override
	public BaseAsset getAsset() {
		return asset;
	}

	public void setAsset(BaseAsset asset) {
		this.asset = asset;
	}

	public Date getPriceDate() {
		return priceDate;
	}

	public void setPriceDate(Date priceDate) {
		this.priceDate = priceDate;
	}

	public BigDecimal getPriceInEuros() {
		return priceInEuros;
	}

	public void setPriceInEuros(BigDecimal priceInEuros) {
		this.priceInEuros = priceInEuros;
	}

	public BigDecimal getBuyPriceInEuros() {
		return buyPriceInEuros;
	}

	public void setBuyPriceInEuros(BigDecimal buyPriceInEuros) {
		this.buyPriceInEuros = buyPriceInEuros;
	}

	public BigDecimal getSellPriceInEuros() {
		return sellPriceInEuros;
	}

	public void setSellPriceInEuros(BigDecimal sellPriceInEuros) {
		this.sellPriceInEuros = sellPriceInEuros;
	}

	public Date getGenerated() {
		return generated;
	}

	public void setGenerated(Date generated) {
		this.generated = generated;
	}
}
