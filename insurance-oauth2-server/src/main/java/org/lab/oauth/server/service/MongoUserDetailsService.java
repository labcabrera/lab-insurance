package org.lab.oauth.server.service;

import java.util.ArrayList;
import java.util.List;

import org.lab.oauth.server.domain.Permission;
import org.lab.oauth.server.domain.Role;
import org.lab.oauth.server.domain.UserSecurityDetailsImpl;
import org.springframework.cache.CacheManager;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;

/**
 * MongoDB {@link AbstractUserDetailsService} implementation.
 */
public class MongoUserDetailsService extends AbstractUserDetailsService {

	public static final String BEAN_NAME = "mongoUserDeailsService";

	private final MongoClient mongoClient;

	public MongoUserDetailsService(CacheManager cacheManager, MongoClient mongoClient) {
		super(cacheManager);
		this.mongoClient = mongoClient;
	}

	protected UserSecurityDetailsImpl findUser(String username) {
		BasicDBObject result = (BasicDBObject) mongoClient.getDatabase("lab-insurance").getCollection("user")
				.find(new BasicDBObject().append("username", username));
		if (result != null) {
			List<Role> roles = new ArrayList<>();
			BasicDBList rolesDB = (BasicDBList) result.get("roles");
			for (Object roleObject : rolesDB) {
				BasicDBObject roleDB = (BasicDBObject) roleObject;
				String roleName = roleDB.getString("name");
				List<Permission> permissions = new ArrayList<>();
				if (roleDB.containsField("permissions")) {
					BasicDBList permissionsDB = (BasicDBList) roleDB.get("permissions");
					for (Object permissionObject : permissionsDB) {
						BasicDBObject permissionDB = (BasicDBObject) permissionObject;
						String resource = permissionDB.getString("resource");
						String method = permissionDB.getString("method");
						BasicDBList allowIds = (BasicDBList) permissionDB.get("allowIds");
						BasicDBList denyIds = (BasicDBList) permissionDB.get("denyIds");
						boolean allowAll = permissionDB.getBoolean("allowAll", false);
						boolean ownEntities = permissionDB.getBoolean("ownEntities", false);
						Permission permission = new Permission();
						permission.setResource(resource);
						permission.setMethod(method);
						permission.setAllowIds(allowIds);
						permission.setDenyIds(denyIds);
						permission.setAllowAll(allowAll);
						permission.setOwnEntities(ownEntities);
						permissions.add(permission);
					}
				}
				Role role = new Role(roleName, permissions);
				roles.add(role);
			}
			String password = result.getString("password");
			boolean accountNonExpired = true;
			boolean accountNonLocked = true;
			boolean credentialsNonExpired = true;
			boolean enabled = true;
			UserSecurityDetailsImpl user = new UserSecurityDetailsImpl(result, roles, password, username,
					accountNonExpired, accountNonLocked, credentialsNonExpired, enabled);
			return user;

		}
		return null;
	}
}