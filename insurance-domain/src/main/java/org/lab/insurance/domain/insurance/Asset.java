package org.lab.insurance.domain.insurance;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
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

	@Id
	private String id;

	@NotNull
	private String isin;

	@NotNull
	private String name;

	private String shortName;

	@NotNull
	private AssetType type;
	private Integer decimals;
	private Date fromDate;
	private Date toDate;

	private Currency currency;

	public Asset(String isin) {
		this.isin = isin;
	}
}
