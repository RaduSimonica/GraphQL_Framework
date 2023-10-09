package ro.crownstudio.config;

import lombok.Getter;
import ro.crownstudio.core.TestLogger;

import java.io.IOException;
import java.util.Properties;


public class MainConfig {

    private static MainConfig INSTANCE;

    private final Properties properties;

    @Getter
    private String apiUrl;

    private MainConfig() {
        properties = new Properties();

        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
            TestLogger.trace("Loaded config.properties file. Found {} properties.", properties.size());
        } catch (IOException e) {
            TestLogger.error("Could not load config.properties file.");
            throw new RuntimeException(e);
        }

        setConfigProperties();
    }

    public static MainConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MainConfig();
            TestLogger.trace("Created a new instance for MainConfig.");
        }
        return INSTANCE;
    }

    private void setConfigProperties() {
        apiUrl = properties.getProperty("apiUrl");
    }
}
