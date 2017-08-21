package org.lab.insurance.web.rest;

import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.jboss.resteasy.plugins.guice.RequestScoped;
import org.lab.insurance.core.security.SecurityService;
import org.lab.insurance.model.common.User;
import org.lab.insurance.web.model.UserSecurityInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/security")
@RequestScoped
public class SecurityRestService {

	private static final Logger LOG = LoggerFactory.getLogger(SecurityRestService.class);

	@Inject
	private SecurityService securityService;
	@Inject
	@Named("security.logout.url")
	private String logoutUrl;
	@Inject
	@Named("rest.authorization.enabled")
	private boolean authorizationEnabled;
	@Inject
	@Named("rest.authorization.group")
	private String authorizationGroup;
	@Inject
	@Named("rest.authorization.group.reporter")
	private String authorizationGroupReporter;

	@GET
	@Path("/logged")
	@Consumes({ "application/json; charset=UTF-8" })
	@Produces({ "application/json; charset=UTF-8" })
	public UserSecurityInfo logged() {
		User currentUser = securityService.getCurrentUser();
		String name = currentUser.getName();
		Boolean isAuthorized = false;
		Boolean canWrite = false;
		if (currentUser.getGroups() != null) {
			isAuthorized = currentUser.getGroups().contains(authorizationGroup);
			canWrite = !currentUser.getGroups().contains(authorizationGroupReporter);
		}
		UserSecurityInfo result = new UserSecurityInfo(name, isAuthorized, canWrite);
		LOG.debug("Logged: {}", result);
		return result;
	}

	@GET
	@Path("/logout")
	public String logout(@Context HttpServletRequest request) {
		request.getSession().invalidate();
		return logoutUrl;
	}
}