package org.lab.oauth.server.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.lab.oauth.server.domain.Permission;
import org.lab.oauth.server.domain.Role;
import org.lab.oauth.server.domain.UserSecurityDetailsImpl;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

public class InMemoryUserDetailsService extends AbstractUserDetailsService {

	public static final String BEAN_NAME = "inMemoryUserDeailsService";

	public InMemoryUserDetailsService(CacheManager cacheManager) {
		super(cacheManager);
		populateCache(cache);
	}

	private void populateCache(Cache cache) {
		List<Role> roles = new ArrayList<>();
		List<Permission> permissions = new ArrayList<>();
		Permission permission = new Permission();
		permission.setResource("hello");
		permission.setMethod("read");
		permissions.add(permission);

		Permission permissionModel = new Permission();
		permissionModel.setResource("model");
		permissionModel.setMethod("read");
		permissionModel.setAllowAll(true);
		permissionModel.setAllowIds(Arrays.asList("1"));
		permissionModel.setDenyIds(Arrays.asList("5"));
		permissionModel.setOwnEntities(true);
		permissions.add(permissionModel);

		Role role = new Role("admin", permissions);
		roles.add(role);
		UserSecurityDetailsImpl user = new UserSecurityDetailsImpl(null, roles,
				"$2a$10$Hapocgd6APJAu5Aq8YlN1e986xkJwfZ1u062G8Lo9KYWzawzMMJV.", "demo", true, true, true, true);
		cache.put("demo", user);
	}

	protected UserSecurityDetailsImpl findUser(String username) {

		return cache.get(username, UserSecurityDetailsImpl.class);
	}

}