package org.lab.insurance.domain.product;

import java.util.Date;

import org.bson.types.ObjectId;
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
	private ObjectId id;

	private String name;
	private String code;

	private Date startDate;
	private Date endDate;

}
