package org.lab.insurance.model.insurance;

import java.math.BigDecimal;

import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDistribution {

	@DBRef
	private BaseAsset asset;

	private BigDecimal amount;

	private BigDecimal percent;

}
