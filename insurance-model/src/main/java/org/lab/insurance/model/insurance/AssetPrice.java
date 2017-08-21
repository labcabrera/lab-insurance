package org.lab.insurance.model.insurance;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
@Table(name = "INS_ASSET_PRICE")
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ASSET_ID", nullable = false)
	private BaseAsset asset;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CURRENCY_ID", nullable = false)
	private Currency currency;

	@Column(name = "PRICE_DATE", nullable = false)
	@Temporal(TemporalType.DATE)
	private Date priceDate;

	@Column(name = "PRICE", nullable = false, precision = 20, scale = 7)
	private BigDecimal price;

	@Column(name = "BUY_PRICE", nullable = false, precision = 20, scale = 7)
	private BigDecimal buyPrice;

	@Column(name = "SELL_PRICE", nullable = false, precision = 20, scale = 7)
	private BigDecimal sellPrice;

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

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(BigDecimal buyPrice) {
		this.buyPrice = buyPrice;
	}

	public BigDecimal getSellPrice() {
		return sellPrice;
	}

	public void setSellPrice(BigDecimal sellPrice) {
		this.sellPrice = sellPrice;
	}

	public Date getGenerated() {
		return generated;
	}

	public void setGenerated(Date generated) {
		this.generated = generated;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
}
