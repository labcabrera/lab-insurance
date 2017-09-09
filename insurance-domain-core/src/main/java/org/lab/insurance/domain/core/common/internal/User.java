package org.lab.insurance.domain.core.common.internal;

import java.util.Collection;

public class User {

	private String name;
	private Collection<String> groups;

	public User() {

	}

	public User(String name, Collection<String> groups) {
		super();
		this.name = name;
		this.groups = groups;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User withName(String name) {
		this.name = name;
		return this;
	}

	public User withGroups(Collection<String> groups) {
		this.groups = groups;
		return this;
	}

	@Override
	public String toString() {
		return name != null ? name : super.toString();
	}

	public Collection<String> getGroups() {
		return groups;
	}

	public void setGroups(Collection<String> groups) {
		this.groups = groups;
	}
}
