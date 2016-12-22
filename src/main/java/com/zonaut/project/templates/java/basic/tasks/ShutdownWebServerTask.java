package com.zonaut.project.templates.java.basic.tasks;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.h2.tools.Server;

import java.util.concurrent.TimeUnit;

/**
 * @author David Debuck
 * @since 22/12/2016
 */
public class ShutdownWebServerTask implements Runnable {

    private static final Logger logger = LogManager.getLogger();

    private Server server = null;
    private int seconds = 0;

    public ShutdownWebServerTask(Server server, int seconds) {
        this.server = server;
        this.seconds = seconds;
    }

    @Override
    public void run() {
        this.shutdownWebserver();
    }

    public void shutdownWebserver() {
        while (seconds > 0) {
            logger.info("Webserver shutting down in {} seconds ...", seconds);
            try {
                seconds--;
                TimeUnit.SECONDS.sleep(1);
            }
            catch (InterruptedException e) {
                //I don't think you need to do anything for your particular problem
            }
        }
        server.stop();
        logger.info("Webserver has been shutdown.");
    }

}
