package org.lab.insurance.web.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.lab.insurance.model.common.Message;
import org.lab.insurance.model.jpa.common.AppFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/file")
public class FileRestService {

	private static final Logger LOG = LoggerFactory.getLogger(FileRestService.class);

	private static final String HEADER_CONTENT_DISPOSITION = "Content-Disposition";
	private static final String HEADER_CONTENT_TYPE = "Content-Type";
	private static final String CONTENT_DISPOSITION_TEMPLATE = "attachment; filename=\"%s\"";

	@Inject
	private Provider<EntityManager> entityManagerProvider;

	@Inject
	@Named("Constants.CONFIGURATION_KEY_REPOSITORY_BASE_PATH")
	private String repositoryBasePath;
	@Inject
	@Named("Constants.CONFIGURATION_KEY_REPOSITORY_TEMPLATES_PATH")
	private String repositoryTemplatePath;
	@Inject
	@Named("Constants.CONFIGURATION_KEY_REPOSITORY_LETTERS_PATH")
	private String repositoryNotificationPath;

	@GET
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	@Path("download/{id}")
	public Response getArtifactBinaryContent(@PathParam("id") Long id) throws Exception {
		try {
			EntityManager entityManager = entityManagerProvider.get();
			AppFile appFile = entityManager.find(AppFile.class, id);
			InputStream contentInputStream = resolveInputStream(appFile);
			ResponseBuilder response = Response.ok(contentInputStream);
			response.header(HEADER_CONTENT_DISPOSITION, String.format(CONTENT_DISPOSITION_TEMPLATE, appFile.getName()));
			response.header(HEADER_CONTENT_TYPE, appFile.getContentType());
			return response.build();
		} catch (FileNotFoundException ex) {
			LOG.error(ex.getMessage(), ex);
			return Response.status(Response.Status.NOT_FOUND).build();
		} catch (Exception ex) {
			LOG.error(ex.getMessage(), ex);
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
		}
	}

	private InputStream resolveInputStream(AppFile appFile) throws FileNotFoundException {
		File repositoryFolder = new File(repositoryBasePath);
		File targetFile = new File(repositoryFolder, appFile.getPath());
		if (!targetFile.exists()) {
			throw new FileNotFoundException(targetFile.getAbsolutePath());
		}
		return new FileInputStream(targetFile);
	}

	@POST
	@Path("upload/{sinisterId}")
	@Consumes("multipart/form-data")
	@SuppressWarnings("unused")
	public Message<String> uploadFile(@PathParam("sinisterId") Long sinisterId, MultipartFormDataInput input) {
		Map<String, List<InputPart>> formParts = input.getFormDataMap();
		List<InputPart> inPart = formParts.get("file");
		for (InputPart inputPart : inPart) {
			try {
				MultivaluedMap<String, String> headers = inputPart.getHeaders();
				InputStream istream = inputPart.getBody(InputStream.class, null);
				File file = saveFile(istream, parseFileName(headers));
				// saveDatabase(sinisterId, file, parseContentType(headers));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return new Message<String>(Message.SUCCESS, "file.uploaded.successfully");
	}

	public File saveFile(InputStream uploadedInputStream, String fileName) {
		File folderBase = new File(repositoryBasePath);
		File targetBaseFolder = new File(folderBase, repositoryNotificationPath);
		File path = new File(targetBaseFolder, fileName);
		OutputStream outpuStream = null;
		try {
			outpuStream = new FileOutputStream(path);
			int read = 0;
			byte[] bytes = new byte[1024];
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				outpuStream.write(bytes, 0, read);
			}
			outpuStream.flush();
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		} finally {
			if (outpuStream != null) {
				try {
					outpuStream.close();
				} catch (IOException e) {
					LOG.error(e.getMessage(), e);
				}
			}
		}
		return path;
	}

	// @Transactional
	// public void saveDatabase(Long sinisterId, File file, String contentType) {
	// EntityManager entityManager = entityManagerProvider.get();
	// Policy sinister = entityManager.find(Policy.class, sinisterId);
	// Date now = Calendar.getInstance().getTime();
	// SinisterLetter entity = new SinisterLetter();
	// entity.setSinister(sinister);
	// entity.setTarget(sinister.getPolicy().getInsured());
	// entity.setType(SinisterLetterType.ATTACHMENT);
	// entity.setCreated(now);
	// entity.setFile(new AppFile());
	// entity.getFile().setCreated(now);
	// entity.getFile().setName(file.getName());
	// entity.getFile().setPath(file.getAbsolutePath().substring(repositoryBasePath.length() + 1));
	// entity.getFile().setContentType(contentType);
	// entityManager.persist(entity);
	// }

	private String parseFileName(MultivaluedMap<String, String> headers) {
		String fileName = "attachment";
		String[] contentDispositionHeader = headers.getFirst("Content-Disposition").split(";");
		for (String name : contentDispositionHeader) {
			if ((name.trim().startsWith("filename"))) {
				fileName = name.split("=")[1].trim().replaceAll("\"", "");
				break;
			}
		}
		return new SimpleDateFormat("ddMMyyyy_HHmmss_").format(new Date()) + fileName;
	}

	@SuppressWarnings("unused")
	private String parseContentType(MultivaluedMap<String, String> headers) {
		String contentType = headers.getFirst("Content-Type");
		return contentType != null ? contentType : "undefined";
	}
}