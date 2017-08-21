package org.lab.insurance.core.security;

import javax.inject.Singleton;

import org.lab.insurance.model.common.User;

@Singleton
public class SecurityService {

	private ThreadLocal<User> currentUser = new ThreadLocal<User>();

	public User getCurrentUser() {
		User user = currentUser.get();
		return user != null ? user : buildSystemUser();
	}

	public void setCurrentUser(User value) {
		currentUser.set(value);
	}

	public void setDefaulySystemUser() {
		setCurrentUser(buildSystemUser());
	}

	private User buildSystemUser() {
		return new User().withName("SYSTEM");
	}
}
