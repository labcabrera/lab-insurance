package org.lab.insurance.domain.insurance;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.Data;

@Data
public class AssetGuaranteePercent {

	@Id
	private String id;

	@DBRef
	private Asset asset;

	private Date from;
	private Date to;
	private BigDecimal guaranteePercent;

}
