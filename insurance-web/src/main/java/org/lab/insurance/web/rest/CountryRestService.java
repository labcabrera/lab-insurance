package org.lab.insurance.web.rest;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.lab.insurance.core.serialization.Serializer;
import org.lab.insurance.model.common.internal.SearchParams;
import org.lab.insurance.model.common.internal.SearchResults;
import org.lab.insurance.model.geo.Country;

@Path("/country")
@Consumes({ "application/json; charset=UTF-8" })
@Produces({ "application/json; charset=UTF-8" })
public class CountryRestService extends AbstractRestEntityService<Country> {

	@Inject
	private Serializer serializer;

	@GET
	@Path("/findById/{id}")
	public Response findById(@PathParam("id") String id) {
		try {
			Country entity = entityManagerProvider.get().find(Country.class, id);
			return entity != null ? Response.ok(serializer.toJson(entity)).build() : Response.status(Response.Status.NOT_FOUND).build();
		} catch (Exception ex) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GET
	@Path("/search")
	public Response search(@QueryParam("p") Integer page, @QueryParam("n") Integer maxResults) {
		SearchParams params = new SearchParams();
		if (page != null && page > 0) {
			params.setCurrentPage(page);
		}
		if (maxResults != null && maxResults > 0) {
			params.setItemsPerPage(maxResults);
		}
		SearchResults<Country> results = find(params);
		return Response.ok(serializer.toJson(results)).build();
	}

	@GET
	@Path("/like/{expression}")
	public Response like(@PathParam("expression") String expression, @QueryParam("n") Integer maxResults) {
		String query = "SELECT e FROM Country e WHERE e.name LIKE :expression ORDER BY e.name";
		return Response.ok(serializer.toJson(this.like(new LikeParams(expression, maxResults, query)))).build();
	}

	@Override
	protected Class<Country> getEntityClass() {
		return Country.class;
	}
}