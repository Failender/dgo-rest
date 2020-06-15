package de.failender.dgo.scheduling.jobs;

import de.failender.dgo.integration.HeldenService;
import de.failender.dgo.persistance.user.UserRepositoryService;
import de.failender.ezql.EzqlConnector;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SynchronizeHeldenJob implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(SynchronizeHeldenJob.class);
    public static String IDENTITY = "SynchronizeHelden";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        long start = System.nanoTime();

        LOGGER.info("Starting Synchronize Helden Job");
        EzqlConnector.allocateConnection();
        UserRepositoryService.findAll().forEach(user -> HeldenService.updateHeldenForUser(user));
        EzqlConnector.releaseConnection();
        double elapsedTimeInSecond = (double) (System.nanoTime() - start) / 1_000_000_000;
        LOGGER.info(String.format("Synchronize Helden completed in  %f seconds", elapsedTimeInSecond));

    }
}
