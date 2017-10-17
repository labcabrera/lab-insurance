package org.lab.oauth.server.domain;

import java.io.Serializable;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * {@code org.springframework.security.core.userdetails.UserDetails} extension with role support and storage identifier.
 */
public interface UserSecurityDetails extends UserDetails {

	public List<Role> getRoles();

	public Serializable getId();
}
