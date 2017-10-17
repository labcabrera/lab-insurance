package org.lab.oauth.server.domain;

import java.util.List;

import lombok.Data;

@Data
public class Permission {

	String resource;
	String method;
	List<Object> allowIds;
	List<Object> denyIds;
	Boolean allowAll;
	Boolean ownEntities;

}
