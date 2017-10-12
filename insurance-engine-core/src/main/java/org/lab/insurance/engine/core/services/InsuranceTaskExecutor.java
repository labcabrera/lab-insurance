package org.lab.insurance.engine.core.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.lab.insurance.common.services.TimestampProvider;
import org.lab.insurance.engine.core.EngineConstants;
import org.lab.insurance.engine.core.domain.ExecutionReport;
import org.lab.insurance.engine.core.domain.ExecutionReportDetail;
import org.lab.insurance.engine.core.domain.InsuranceTask;
import org.lab.insurance.engine.core.domain.InsuranceTaskError;
import org.lab.insurance.engine.core.domain.repository.ExecutionReportRepository;
import org.lab.insurance.engine.core.domain.repository.InsuranceTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class InsuranceTaskExecutor {

	@Autowired
	private InsuranceTaskRepository taskRepo;

	@Autowired
	private ExecutionReportRepository executionReportRepo;

	@Autowired
	private TimestampProvider timeStampProvider;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private RoutingKeyMapper routingKeyMapper;

	@Qualifier(EngineConstants.Channels.ExecutionRequestSync)
	@Autowired
	private MessageChannel messageChannelSync;

	@Qualifier(EngineConstants.Channels.ExecutionRequestAsync)
	@Autowired
	private MessageChannel messageChannelAsync;

	public ExecutionReport execute(Date from, Date to, List<String> tags) {
		log.info("Executing {} to {} (tags: {})", new DateTime(from), new DateTime(to), tags);
		ExecutionReport report = new ExecutionReport();
		report.setFrom(from);
		report.setTo(to);
		report.setExecutionStart(timeStampProvider.getRealDate());
		report.setSuccess(false);
		report = executionReportRepo.save(report);

		// TODO quitar iff
		//@formatter:off
		Query query = null;
		if(tags != null && tags.isEmpty()) {
			query = new Query(
					Criteria.where("executed").is(null)
					.andOperator(Criteria.where("error").is(null)
					.andOperator(Criteria.where("tags").in(tags))
				)
			);
		} else {
			query = new Query(
					Criteria.where("executed").is(null)
					.andOperator(Criteria.where("error").is(null)
				)
			);
		}
		//@formatter:on

		query.with(new Sort(Sort.Direction.ASC, "execution"));

		InsuranceTask task = mongoTemplate.findOne(query, InsuranceTask.class);
		while (task != null) {
			log.info("Processing task {} ({})", task.getClass().getName(), task);
			execute(task);
			task = mongoTemplate.findOne(query, InsuranceTask.class);
		}

		return report;
	}

	private ExecutionReportDetail execute(InsuranceTask task) {
		log.info("Executing task {}", task);
		ExecutionReportDetail detail = new ExecutionReportDetail();
		try {
			internalExecute(task);
			task.setExecuted(timeStampProvider.getCurrentDate());
			taskRepo.save(task);
		}
		catch (Exception ex) {
			log.error("Execution error", ex);
			task.setError(InsuranceTaskError.builder().message(ex.getMessage()).build());
			taskRepo.save(task);
		}
		return detail;
	}

	@SuppressWarnings("rawtypes")
	private void internalExecute(InsuranceTask task) throws JsonProcessingException {
		Boolean sync = routingKeyMapper.isSync(task);
		Map<String, Object> headers = new HashMap<>();
		headers.put("routingKey", routingKeyMapper.getRoutingKey(task));
		Message message = new GenericMessage<>(task.getAction(), headers);
		// TODO revisar recepcion de mensajes en procesamiento sincronos
		sync = false;
		if (sync) {
			messageChannelSync.send(message);
		}
		else {
			messageChannelAsync.send(message);
		}

	}

}
