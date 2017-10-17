package org.lab.oauth.server.domain;

import java.util.ArrayList;
import java.util.List;

public class Role {

	public Role() {
		super();
	}

	public Role(String name, List<Permission> permissions) {
		super();
		this.name = name;
		this.permissions = permissions;
	}

	private String name;
	private List<Permission> permissions = new ArrayList<>();

	public String getName() {
		return name;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}
}
