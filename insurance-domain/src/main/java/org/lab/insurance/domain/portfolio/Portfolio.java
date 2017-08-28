package org.lab.insurance.domain.portfolio;

import java.util.List;

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
public class Portfolio {

	@Id
	private String id;

	private String name;

	private PortfolioType type;

	private List<Investment> investments;

}
