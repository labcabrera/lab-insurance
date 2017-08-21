package org.lab.insurance.web.web.json;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.lab.insurance.core.serialization.Serializer;
import org.lab.insurance.model.common.internal.Message;
import org.lab.insurance.model.common.internal.MessageEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GsonExceptionHandler implements ExceptionMapper<RuntimeException> {

	private static final Logger LOG = LoggerFactory.getLogger(GsonExceptionHandler.class);

	@Inject
	private Serializer serializer;

	@Override
	public Response toResponse(RuntimeException exception) {
		LOG.error(exception.getMessage(), exception);
		Message<Exception> message = new Message<Exception>();
		message.addError("general.error");
		message.addError(new MessageEntry("general.error.cause").withParameter("cause", exception.getMessage()));
		message.addError(new MessageEntry("general.error.stacktrace").withParameter("stacktrace", getStackTrace(exception)));
		return Response.ok(serializer.toJson(message)).build();
	}

	private String getStackTrace(RuntimeException exception) {
		StringBuilder sb = new StringBuilder();
		appendStackTrace(exception, sb);
		return sb.toString();
	}

	private void appendStackTrace(Throwable exception, StringBuilder sb) {
		for (StackTraceElement element : exception.getStackTrace()) {
			sb.append(element.toString()).append("\n");
		}
		if (exception.getCause() != null) {
			sb.append("Caused by\n");
			appendStackTrace(exception.getCause(), sb);
		}
	}
}