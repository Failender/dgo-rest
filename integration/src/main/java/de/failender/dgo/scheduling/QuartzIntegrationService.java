package de.failender.dgo.scheduling;

import de.failender.dgo.scheduling.jobs.SynchronizeHeldenJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzIntegrationService {

    public static void initialize() {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();

            registerJobs(scheduler);
            scheduler.start();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    private static void registerJobs(Scheduler scheduler) throws SchedulerException {
        JobDetail job = JobBuilder.newJob(SynchronizeHeldenJob.class)
                .withIdentity(SynchronizeHeldenJob.IDENTITY)

                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withSchedule(SimpleScheduleBuilder.repeatMinutelyForever(30))
                .withIdentity(SynchronizeHeldenJob.IDENTITY)
                .startNow()
                .build();
        scheduler.scheduleJob(job, trigger);
    }
}
