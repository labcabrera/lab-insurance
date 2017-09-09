package org.lab.insurance.domain.core.system;

import java.util.Date;

import lombok.Data;

@Data
public class AppFile {

	private String name;
	private String path;
	private String contentType;
	private Date created;

}
