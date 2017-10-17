package org.lab.oauth.server.service;

import org.lab.oauth.server.domain.UserSecurityDetailsImpl;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Abstract {@code org.springframework.security.core.userdetails.UserDetailsService}
 */
public abstract class AbstractUserDetailsService implements UserDetailsService {

	private static final int MAX_TIME_IN_CACHE = 10000;

	private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();
	protected final Cache cache;

	public AbstractUserDetailsService(CacheManager cacheManager) {
		super();
		cache = cacheManager.getCache("INSURANCE_CACHE_NAME");
	}

	@Override
	public final UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserSecurityDetailsImpl user = cache.get(username, UserSecurityDetailsImpl.class);
		if (user == null || (user != null && user.getLastLoadTime() > MAX_TIME_IN_CACHE)) {
			user = findUser(username);
			cache.put(username, user);
		}
		if (user == null) {
			throw new UsernameNotFoundException(String.format("Missing user %s", username));
		}
		detailsChecker.check(user);
		return user;
	}

	protected abstract UserSecurityDetailsImpl findUser(String username);

}
