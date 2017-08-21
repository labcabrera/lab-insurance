package org.lab.insurance.web.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UserSecurityInfo implements Serializable {

	private final String name;
	private final Boolean isAuthorized;
	private final Boolean canWrite;

	public UserSecurityInfo(String name, Boolean isAuthorized, Boolean canWrite) {
		this.name = name;
		this.isAuthorized = isAuthorized;
		this.canWrite = canWrite;
	}

	public String getName() {
		return name;
	}

	public Boolean getIsAuthorized() {
		return isAuthorized;
	}

	public Boolean getCanWrite() {
		return canWrite;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserSecurityInfo [name=").append(name).append(", isAuthorized=").append(isAuthorized).append(", canWrite=").append(canWrite).append("]");
		return builder.toString();
	}
}
