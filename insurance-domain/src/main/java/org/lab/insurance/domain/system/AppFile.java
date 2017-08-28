package org.lab.insurance.domain.system;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
@Data
public class AppFile {

	@Id
	private ObjectId id;
	private String name;
	private String path;
	private String contentType;
	private Date created;

}
