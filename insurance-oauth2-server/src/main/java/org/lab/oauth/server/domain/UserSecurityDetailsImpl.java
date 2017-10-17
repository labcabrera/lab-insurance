package org.lab.oauth.server.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@SuppressWarnings("serial")
public class UserSecurityDetailsImpl implements UserSecurityDetails {

	public final long loadTimeStamp;
	private String password;
	private String username;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;
	private Serializable userId;
	private List<Role> roles;

	public UserSecurityDetailsImpl() {
		super();
		this.accountNonExpired = true;
		this.accountNonLocked = true;
		this.credentialsNonExpired = true;
		this.enabled = true;
		this.loadTimeStamp = System.currentTimeMillis();

	}

	public UserSecurityDetailsImpl(Serializable dbEntity, List<Role> roles, String password, String username,
			boolean accountNonExpired, boolean accountNonLocked, boolean credentialsNonExpired, boolean enabled) {
		super();
		this.roles = roles;
		this.password = password;
		this.username = username;
		this.accountNonExpired = accountNonExpired;
		this.accountNonLocked = accountNonLocked;
		this.credentialsNonExpired = credentialsNonExpired;
		this.enabled = enabled;
		this.loadTimeStamp = System.currentTimeMillis();

	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (Role role : roles) {
			GrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
			authorities.add(authority);
		}

		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public Object getDbEntity() {
		return userId;
	}

	public long getLastLoadTime() {
		return System.currentTimeMillis() - loadTimeStamp;
	}

	@Override
	public List<Role> getRoles() {
		return roles;
	}

	@Override
	public Serializable getId() {
		return userId;
	}

}
