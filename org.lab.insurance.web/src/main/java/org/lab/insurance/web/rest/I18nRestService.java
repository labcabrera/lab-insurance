package org.lab.insurance.web.rest;

import java.io.InputStream;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.lab.insurance.core.i18n.I18nService;

@Path("/i18n")
@Consumes({ "application/json; charset=UTF-8" })
@Produces({ "application/json; charset=UTF-8" })
@Singleton
public class I18nRestService {

	private final I18nService i18nService;

	@Inject
	public I18nRestService(I18nService i18nService) {
		this.i18nService = i18nService;
	}

	@GET
	@Path("/json")
	public Response getJson(@QueryParam("lang") String locale) {
		InputStream inputStream = i18nService.getJson(locale);
		return inputStream != null ? Response.ok(inputStream).build() : Response.status(Response.Status.NOT_FOUND).build();
	}
}
