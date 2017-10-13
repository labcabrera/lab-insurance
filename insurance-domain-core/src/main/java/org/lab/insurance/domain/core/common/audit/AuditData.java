package org.lab.insurance.domain.core.common.audit;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditData {

	@Id
	String id;

	String level;
	String message;
	Object payload;
	List<String> tags;

	Date created;
	Date modified;

}
