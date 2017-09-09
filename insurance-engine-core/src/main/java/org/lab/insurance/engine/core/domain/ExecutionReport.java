package org.lab.insurance.engine.core.domain;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.Data;

@Data
public class ExecutionReport {

	@Id
	String id;

	Boolean success;

	Date from;
	Date to;

	Date executionStart;
	Date executionEnd;

	List<ExecutionReportDetail> details;

}
