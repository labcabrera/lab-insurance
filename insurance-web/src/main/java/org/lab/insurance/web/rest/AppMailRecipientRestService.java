package org.lab.insurance.web.rest;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.commons.lang3.StringUtils;
import org.lab.insurance.model.common.internal.Message;
import org.lab.insurance.model.common.internal.SearchParams;
import org.lab.insurance.model.common.internal.SearchResults;
import org.lab.insurance.model.system.AppMailRecipient;

@Path("/mailRecipient")
@Consumes({ "application/json; charset=UTF-8" })
@Produces({ "application/json; charset=UTF-8" })
public class AppMailRecipientRestService extends AbstractRestEntityService<AppMailRecipient> {

	@GET
	@Path("/findById/{id}")
	public AppMailRecipient findById(@PathParam("id") Long id) {
		return entityManagerProvider.get().find(AppMailRecipient.class, id);
	}

	@GET
	@Path("/search")
	public SearchResults<AppMailRecipient> search(@QueryParam("p") Integer page, @QueryParam("n") Integer maxResults) {
		SearchParams params = new SearchParams();
		if (page != null && page > 0) {
			params.setCurrentPage(page);
		}
		if (maxResults != null && maxResults > 0) {
			params.setItemsPerPage(maxResults);
		}
		return find(params);
	}

	@POST
	@Path("/save")
	@Transactional
	public Message<AppMailRecipient> save(AppMailRecipient mailRecipient) {
		Message<AppMailRecipient> message = new Message<AppMailRecipient>();
		validate(mailRecipient, message);
		if (message.hasErrors()) {
			return message;
		}
		entityManagerProvider.get().merge(mailRecipient);
		message.setMessage("mail.recipient.saved");
		return message;
	}

	private void validate(AppMailRecipient entity, Message<AppMailRecipient> message) {
		if (StringUtils.isEmpty(entity.getMailAddress())) {
			message.addError("mail.recipient.missing.mailAddress");
		}
		if (StringUtils.isBlank(entity.getName())) {
			message.addError("mail.recipient.missing.name");
		}
	}

	@Override
	protected Class<AppMailRecipient> getEntityClass() {
		return AppMailRecipient.class;
	}

}
