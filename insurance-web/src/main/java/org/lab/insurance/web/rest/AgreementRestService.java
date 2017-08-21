package org.lab.insurance.web.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.lab.insurance.model.common.Message;
import org.lab.insurance.model.common.SearchOrder;
import org.lab.insurance.model.common.SearchParams;
import org.lab.insurance.model.common.SearchResults;
import org.lab.insurance.model.jpa.product.Agreement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/agreement")
@Consumes({ "application/json; charset=UTF-8" })
@Produces({ "application/json; charset=UTF-8" })
public class AgreementRestService extends AbstractRestEntityService<Agreement> {

	private static final Logger LOG = LoggerFactory.getLogger(AgreementRestService.class);

	@GET
	@Path("/findAll")
	public List<Agreement> findAll() {
		return entityManagerProvider.get().createQuery("select e from Agreement e order by e.code", Agreement.class)
				.getResultList();
	}

	@GET
	@Path("/search")
	public SearchResults<Agreement> search(@QueryParam("p") Integer page, @QueryParam("n") Integer maxResults) {
		return find(new SearchParams(page, maxResults, "", "code", SearchOrder.ASC));
	}

	@GET
	@Path("/findById/{id}")
	public Agreement findById(@PathParam("id") Long primaryKey) {
		return entityManagerProvider.get().find(Agreement.class, primaryKey);
	}

	@POST
	@Path("/persist")
	@Transactional
	public Message<Agreement> save(Agreement acuerdo) {
		Message<Agreement> message = new Message<Agreement>();
		try {
			EntityManager entityManager = entityManagerProvider.get();
			message = validate(acuerdo);
			if (message.hasErrors()) {
				return message;
			}
			entityManager.merge(acuerdo);
			message.setMessage("label.acuerdoMarco.saved");
		}
		catch (Exception ex) {
			LOG.error("Error al guardar el acuerdo marco", ex);
			message.addError("label.acuerdoMarco.saved");
		}
		return message;
	}

	@Override
	protected Class<Agreement> getEntityClass() {
		return Agreement.class;
	}
}