package org.lab.insurance.model.product;

import java.util.Date;

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
public class Agreement {

	@Id
	private String id;

	private String name;
	private String code;

	private Date startDate;
	private Date endDate;

}
