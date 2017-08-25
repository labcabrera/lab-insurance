package org.lab.insurance.model.insurance;

import java.math.BigDecimal;
import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class AssetGuaranteePercent {

	@Id
	private ObjectId id;
	private BaseAsset asset;
	private Date from;
	private Date to;
	private BigDecimal guaranteePercent;

}
