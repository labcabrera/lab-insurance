package org.lab.insurance.core.scheduler;

import java.util.Map;
import java.util.Properties;

import org.lab.insurance.model.system.ScheduledTask;
import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.MutableTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Al crear la instancia inserta en el contexto el {@link Injector} a partir del cual podremos utilizar los servicios.
 */
public class SchedulerService {

	private static final Logger LOG = LoggerFactory.getLogger(SchedulerService.class);

	private final Scheduler scheduler;
	private final ClassLoader classLoader;

	public SchedulerService(/*Injector injector*/) {
		LOG.info("Iniciando servicio de tareas programadas");
		try {
			Properties properties = new Properties();
			properties.load(getClass().getResourceAsStream("/org/quartz/quartz.properties"));
			scheduler = new StdSchedulerFactory(properties).getScheduler();
			// TODO remove guice integration
			// scheduler.getContext().put(Injector.class.getName(), injector);
			// entityManagerProvider = injector.getProvider(EntityManager.class);
			classLoader = Thread.currentThread().getContextClassLoader();
		}
		catch (Exception ex) {
			throw new RuntimeException("Error al arrancar el servicio de tareas programadas", ex);
		}
	}

	/**
	 * Podemos consultar la aplicación <a href="http://www.cronmaker.com/">cronmaker.com</a> para generar las
	 * expresiones cron.
	 * 
	 * @throws SchedulerException
	 */
	@SuppressWarnings("unchecked")
	public void registerJobs() throws SchedulerException {
		throw new RuntimeException("Not implemented: jpa -> mongo");
		// LOG.debug("Registrando tareas programadas");
		// try {
		// // EntityManager entityManager = null;
		// String qlString = "select e from ScheduledTask e where e.disabled is null or e.disabled = false";
		//// TypedQuery<ScheduledTask> query = entityManager.createQuery(qlString, ScheduledTask.class);
		// List<ScheduledTask> tasks = query.getResultList();
		// ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		// LOG.debug("Encontradas {} tareas programadas", tasks.size());
		// for (ScheduledTask task : tasks) {
		// try {
		// Class<? extends Job> jobClass = (Class<? extends Job>) classLoader.loadClass(task.getClassName());
		// registerJob(jobClass, task.getCronExpression(), task.getParams());
		// }
		// catch (Exception ex) {
		// LOG.error("Error al registrar la tarea {}", task);
		// }
		// }
		// }
		// catch (Exception ex) {
		// throw new RuntimeException("Error al registrar las tareas programadas", ex);
		// }
	}

	@SuppressWarnings("unchecked")
	public void registerTask(ScheduledTask task) throws SchedulerException, ClassNotFoundException {
		LOG.debug("Registrando task {}", task);
		Class<? extends Job> jobClass = (Class<? extends Job>) classLoader.loadClass(task.getClassName());
		registerJob(jobClass, task.getCronExpression());
	}

	public void unregisterTask(ScheduledTask task) throws SchedulerException {
		LOG.debug("Eliminando task {}", task);
		JobKey jobKey = new JobKey(task.getClassName());
		scheduler.deleteJob(jobKey);
	}

	private void registerJob(Class<? extends Job> jobClass, String cronExpression) throws SchedulerException {
		registerJob(jobClass, cronExpression, null);
	}

	private void registerJob(Class<? extends Job> jobClass, String cronExpression, Map<String, String> params)
			throws SchedulerException {
		LOG.debug("Registrando job {} con la expresion cron {}", jobClass.getName(), cronExpression);
		if (params != null) {
			for (String key : params.keySet()) {
				String contextKey = jobClass.getName() + "." + key;
				scheduler.getContext().put(contextKey, params.get(key));
			}
		}
		String name = jobClass.getName();
		JobDetail jobDetail = JobBuilder.newJob(jobClass).withDescription(name).withIdentity(name).build();
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
		MutableTrigger trigger = scheduleBuilder.build();
		trigger.setKey(new TriggerKey(name));
		scheduler.scheduleJob(jobDetail, trigger);
	}

	@SuppressWarnings("unchecked")
	public void execute(ScheduledTask task) throws SchedulerException, ClassNotFoundException {
		LOG.debug("Ejecutando tarea {}", task);
		Class<? extends Job> jobClass = (Class<? extends Job>) classLoader.loadClass(task.getClassName());
		String name = String.format("%s_%s", System.currentTimeMillis(), jobClass.getName());
		JobDetail jobDetail = JobBuilder.newJob(jobClass).withDescription(name).withIdentity(name).build();
		SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1)
				.withRepeatCount(1);
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("Trigger_" + name).withSchedule(builder).build();
		scheduler.scheduleJob(jobDetail, trigger);
	}

	public Scheduler getScheduler() {
		return scheduler;
	}
}
