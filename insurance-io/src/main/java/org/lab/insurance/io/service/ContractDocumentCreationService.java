package org.lab.insurance.io.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;

import org.lab.insurance.common.exception.InsuranceException;
import org.lab.insurance.domain.core.contract.Contract;
import org.lab.insurance.io.domain.InsuranceS3Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ContractDocumentCreationService {

	@Autowired
	private InsuranceS3Service s3Service;

	@Autowired
	private ObjectMapper mapper;

	public Contract process(Contract contract) {
		log.debug("Creating contract documentation");

		InputStream in = createContractInfo(contract);

		InsuranceS3Resource resource = new InsuranceS3Resource();
		resource.setContentEncoding(StandardCharsets.UTF_8.name());
		resource.setContentType("application/json");
		resource.setCreated(Calendar.getInstance().getTime());
		resource.setName("contract-" + contract.getId() + "example.json");
		resource.setParentPath("contract-docs");
		resource.setStatus("created");

		s3Service.upload(resource, in);
		return contract;
	}

	// TODO generacion zip / pdf
	private InputStream createContractInfo(Contract contract) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			mapper.writeValue(out, contract);
			return new ByteArrayInputStream(out.toByteArray());
		}
		catch (Exception ex) {
			throw new InsuranceException("Contract doc creation error", ex);
		}
	}

}
