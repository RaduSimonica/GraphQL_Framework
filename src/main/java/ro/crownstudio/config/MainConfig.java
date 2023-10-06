package ro.crownstudio.config;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Properties;


public class MainConfig {

    private static final Logger LOGGER = LogManager.getLogger(MainConfig.class);
    private static MainConfig INSTANCE;

    private Properties properties;

    @Getter
    private String apiUrl;

    private MainConfig() {
        properties = new Properties();

        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
            LOGGER.trace("Loaded config.properties file. Found {} properties.", properties.size());
        } catch (IOException e) {
            LOGGER.fatal("Could not load config.properties file.", e);
            throw new RuntimeException(e);
        }

        setConfigProperties();
    }

    public static MainConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MainConfig();
            LOGGER.trace("Created a new instance for MainConfig.");
        }
        return INSTANCE;
    }

    private void setConfigProperties() {
        apiUrl = properties.getProperty("apiUrl");
    }
}
