package org.lab.insurance.domain.system;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Deprecated
@Data
public class ScheduledTask {

	@Id
	private String id;
	private String name;
	private String className;
	private String cronExpression;
	private Date startDate;
	private Date endDate;
	private Map<String, String> params = new HashMap<String, String>();

}
