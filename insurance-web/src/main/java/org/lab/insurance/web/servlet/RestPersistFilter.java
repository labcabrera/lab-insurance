package org.lab.insurance.web.servlet;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.persist.UnitOfWork;

@Singleton
@Provider
public final class RestPersistFilter implements ContainerRequestFilter, ContainerResponseFilter {

	private final UnitOfWork unitOfWork;

	@Inject
	public RestPersistFilter(UnitOfWork unitOfWork) {
		this.unitOfWork = unitOfWork;
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		unitOfWork.begin();
	}

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
		unitOfWork.end();
	}
}