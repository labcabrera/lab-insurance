package org.lab.insurance.domain.insurance;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class AssetPrice {

	@Id
	private String id;
	private Asset asset;
	private Currency currency;
	private Date priceDate;
	private BigDecimal price;
	private BigDecimal buyPrice;
	private BigDecimal sellPrice;
	private Date generated;

}
