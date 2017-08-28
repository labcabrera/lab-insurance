package org.lab.insurance.domain.insurance;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Asset {

	private String id;
	private Currency currency;
	private String isin;
	private String name;
	private String shortName;
	private AssetType type;
	private Integer decimals;
	private Date fromDate;
	private Date toDate;

	public Asset(String isin) {
		this.isin = isin;
	}
}
