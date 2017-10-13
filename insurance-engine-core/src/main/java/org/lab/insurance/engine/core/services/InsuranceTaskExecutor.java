package org.lab.insurance.engine.core.services;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.lab.insurance.common.exception.InsuranceException;
import org.lab.insurance.common.services.TimestampProvider;
import org.lab.insurance.engine.core.domain.ExecutionReport;
import org.lab.insurance.engine.core.domain.ExecutionReportDetail;
import org.lab.insurance.engine.core.domain.InsuranceTask;
import org.lab.insurance.engine.core.domain.InsuranceTaskError;
import org.lab.insurance.engine.core.domain.repository.ExecutionReportRepository;
import org.lab.insurance.engine.core.domain.repository.InsuranceTaskRepository;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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
	private AmqpTemplate amqpTemplate;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private ObjectMapper mapper;

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
					.andOperator(Criteria.where("execution").gte(from).lte(to))
				)
			);
		} else {
			query = new Query(
				Criteria.where("executed").is(null)
					.andOperator(Criteria.where("error").is(null)
					.andOperator(Criteria.where("execution").gte(from).lte(to))
				)
			);
		}
		//@formatter:on

		query.with(new Sort(Sort.Direction.ASC, "execution"));

		InsuranceTask task = mongoTemplate.findOne(query, InsuranceTask.class);
		while (task != null) {
			log.info("Executing scheduled task {} ({})", task.getClass().getName(), task);
			execute(task);
			task = mongoTemplate.findOne(query, InsuranceTask.class);
		}

		return report;
	}

	private ExecutionReportDetail execute(InsuranceTask task) {
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

	// TODO
	private void internalExecute(InsuranceTask task) throws JsonProcessingException {
		try {
			String routingKey = task.getDestinationQueue();
			Object data = task.getData();
			String json = mapper.writeValueAsString(data);

			// rabbitTemplate.convertAndSend(routingKey, json);
			rabbitTemplate.convertAndSend(routingKey, json);

			// TODO controlar cuando termina de procesarse la accion. No vale con el
			// convertSendAndReceive(...)
			try {
				Thread.sleep(5000);
			}
			catch (InterruptedException e) {
			}

		}
		catch (RuntimeException ex) {
			log.error("Task execution error");
			throw new InsuranceException("Task execution error", ex);
		}
	}

}
