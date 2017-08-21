package org.lab.insurance.web.rest;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.lab.insurance.core.scheduler.SchedulerService;
import org.lab.insurance.model.common.Message;
import org.lab.insurance.model.common.SearchParams;
import org.lab.insurance.model.common.SearchResults;
import org.lab.insurance.model.jpa.system.ScheduledTask;
import org.quartz.CronScheduleBuilder;
import org.quartz.spi.MutableTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

@Path("/scheduler")
@Consumes({ "application/json; charset=UTF-8" })
@Produces({ "application/json; charset=UTF-8" })
public class SchedulerRestService extends AbstractRestEntityService<ScheduledTask> {

	private static final Logger LOG = LoggerFactory.getLogger(SchedulerRestService.class);

	@Inject
	private SchedulerService schedulerService;

	@GET
	@Path("/search")
	public SearchResults<ScheduledTask> search(@QueryParam("p") Integer page, @QueryParam("n") Integer maxResults) {
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
	@Path("/merge")
	@Transactional
	public Message<ScheduledTask> merge(ScheduledTask task) {
		try {
			EntityManager entityManager = entityManagerProvider.get();
			ScheduledTask current = entityManager.find(ScheduledTask.class, task.getId());
			current.merge(task);
			schedulerService.unregisterTask(task);
			schedulerService.registerTask(task);
			entityManager.merge(current);
			return new Message<ScheduledTask>(Message.SUCCESS, "scheduler.task.merge.ok").withPayload(current);
		}
		catch (Exception ex) {
			LOG.error("Merge error", ex);
			return new Message<ScheduledTask>(Message.GENERIC_ERROR).withMessage("scheduler.task.merge.error");
		}
	}

	@POST
	@Path("/pause/{id}")
	@Transactional
	public Message<ScheduledTask> pause(@PathParam("id") Long taskId) {
		try {
			EntityManager entityManager = entityManagerProvider.get();
			ScheduledTask current = entityManager.find(ScheduledTask.class, taskId);
			current.setEndDate(Calendar.getInstance().getTime());
			entityManager.merge(current);
			schedulerService.unregisterTask(current);
			return new Message<ScheduledTask>(Message.SUCCESS).withPayload(current)
					.withMessage("scheduler.task.pause.ok");
		}
		catch (Exception ex) {
			LOG.error("Pause error", ex);
			return new Message<ScheduledTask>(Message.GENERIC_ERROR).withMessage("scheduler.task.pause.error");
		}
	}

	@POST
	@Path("/resume/{id}")
	@Transactional
	public Message<ScheduledTask> resume(@PathParam("id") Long taskId) {
		try {
			EntityManager entityManager = entityManagerProvider.get();
			ScheduledTask current = entityManager.find(ScheduledTask.class, taskId);
			current.setEndDate(null);
			entityManager.merge(current);
			schedulerService.registerTask(current);
			return new Message<ScheduledTask>(Message.SUCCESS).withPayload(current)
					.withMessage("scheduler.task.resume.ok");
		}
		catch (Exception ex) {
			LOG.error("Resume error", ex);
			return new Message<ScheduledTask>(Message.GENERIC_ERROR).withMessage("scheduler.task.resume.error");
		}
	}

	@POST
	@Path("/pauseAll")
	@Transactional
	public Message<ScheduledTask> pauseAll() {
		try {
			schedulerService.getScheduler().pauseAll();
			return new Message<ScheduledTask>(Message.SUCCESS).withMessage("scheduler.task.pauseAll.ok");
		}
		catch (Exception ex) {
			return new Message<ScheduledTask>(Message.GENERIC_ERROR).withMessage("scheduler.task.pauseAll.error");
		}
	}

	@POST
	@Path("/resumeAll")
	@Transactional
	public Message<ScheduledTask> resumeAll() {
		try {
			schedulerService.getScheduler().resumeAll();
			return new Message<ScheduledTask>(Message.SUCCESS).withMessage("scheduler.task.resumeAll.ok");
		}
		catch (Exception ex) {
			return new Message<ScheduledTask>(Message.GENERIC_ERROR).withMessage("scheduler.task.resumeAll.error");
		}
	}

	@POST
	@Path("/execute/{id}")
	@Transactional
	public Message<ScheduledTask> execute(@PathParam("id") Long taskId) {
		try {
			EntityManager entityManager = entityManagerProvider.get();
			ScheduledTask task = entityManager.find(ScheduledTask.class, taskId);
			schedulerService.execute(task);
			return new Message<ScheduledTask>(Message.SUCCESS).withMessage("scheduler.task.resumeAll.ok");
		}
		catch (Exception ex) {
			return new Message<ScheduledTask>(Message.GENERIC_ERROR).withMessage("scheduler.task.resumeAll.error");
		}
	}

	/**
	 * Metodo que devuelve un mensaje con las siguientes fechas de ejecucion de una tarea planificada
	 * 
	 * @param taskId
	 * @return
	 */
	@GET
	@Path("/nextExecutions/{id}")
	public Message<String> getNextExecutions(@PathParam("id") Long taskId, @QueryParam("c") Integer count) {
		try {
			EntityManager entityManager = entityManagerProvider.get();
			ScheduledTask task = entityManager.find(ScheduledTask.class, taskId);
			if (StringUtils.isNoneEmpty(task.getCronExpression())) {
				Message<String> message = new Message<String>(Message.SUCCESS, "scheduler.nextExecutions");
				CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(task.getCronExpression());
				MutableTrigger trigger = scheduleBuilder.build();
				Date checkDate = Calendar.getInstance().getTime();
				count = (count != null && count > 0) ? count : 5;
				for (int i = 0; i < count; i++) {
					Date targetDate = trigger.getFireTimeAfter(checkDate);
					message.addInfo(DateFormatUtils.ISO_DATETIME_FORMAT.format(targetDate));
					checkDate = targetDate;
				}
				return message;
			}
			else {
				return new Message<String>(Message.SUCCESS)
						.withMessage("scheduler.nextExecutions.undefinedCronExpression");
			}
		}
		catch (Exception ex) {
			return new Message<String>(Message.GENERIC_ERROR).withMessage("scheduler.nextExecutions.error");
		}

	}

	@Override
	protected Class<ScheduledTask> getEntityClass() {
		return ScheduledTask.class;
	}
}
