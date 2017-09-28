package org.lab.insurance.documentation.domain;

import java.util.Date;

import lombok.Data;

@Data
public class InsuranceS3Resource {

	String parentPath;
	String name;

	String contentType;
	String contentEncoding;
	Long contentLength;

	String status;
	Date created;

}
