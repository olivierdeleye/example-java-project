package com.zonaut.project.templates.java.basic.configuration;

import liquibase.change.custom.CustomTaskChange;
import liquibase.database.Database;
import liquibase.exception.CustomChangeException;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.resource.ResourceAccessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.inject.Inject;

/**
 * @author David Debuck
 * @since 28/04/2016
 */
public class DataLoaderTask implements CustomTaskChange {

    private static final Logger logger = LogManager.getLogger();

    //to hold the parameter value
    private String file;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    @Inject
    private ResourceAccessor resourceAccessor;

    @Override
    public void execute(final Database database) throws CustomChangeException {

        try {

            logger.info("File found: " + file);

//            //Opening my data file
//            BufferedReader in = new BufferedReader(
//                    new InputStreamReader((InputStream) resourceAccessor.getResourcesAsStream(file)));
//
//            //Ignore header
//            String str = in.readLine();
//
//            while ((str = in.readLine()) != null && !str.trim().equals("")) {
//                logger.info("Processing line "+ str);
//                //Do whatever is necessary
//            }
//            in.close();
        } catch (Exception e) {
            throw new CustomChangeException(e);
        }
    }

    @Override
    public String getConfirmationMessage() {
        logger.info("Show confirmation message");
        return null;
    }

    @Override
    public void setUp() throws SetupException {
        logger.info("Persons csv setup");
    }

    @Override
    public void setFileOpener(ResourceAccessor resourceAccessor) {
        this.resourceAccessor = resourceAccessor;
    }

    @Override
    public ValidationErrors validate(Database database) {
        logger.info("Validate errors");
        return null;
    }

}