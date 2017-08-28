package org.lab.insurance.domain.insurance;

import java.math.BigDecimal;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class AssetGuaranteePercent {

	@Id
	private ObjectId id;
	private Asset asset;
	private Date from;
	private Date to;
	private BigDecimal guaranteePercent;

}
