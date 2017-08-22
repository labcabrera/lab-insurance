package org.lab.insurance.model.common.audit;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//TODO complete fields
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditData {

	private Date created;
	private Date modified;

}
