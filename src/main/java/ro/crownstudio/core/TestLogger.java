package ro.crownstudio.core;

import io.qameta.allure.Allure;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TestLogger {

    public static synchronized void trace(String message, Object... args) {
        log(Level.TRACE, message, args);
    }

    public static synchronized void debug(String message, Object... args) {
        log(Level.DEBUG, message, args);
    }

    public static synchronized void info(String message, Object... args) {
        log(Level.INFO, message, args);
    }

    public static synchronized void warn(String message, Object... args) {
        log(Level.WARN, message, args);
    }

    public static synchronized void error(String message, Object... args) {
        log(Level.ERROR, message, args);
    }

    public static synchronized void log(Level level, String message, Object... args) {
        Allure.addAttachment(level.name(), message.replace("{}", "%s").formatted(args));

        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        if (trace.length >= 3) {
            try {
                Logger logger = LogManager.getLogger(Class.forName(trace[2].getClassName()));
                logger.log(level, message, args);
            } catch (ClassNotFoundException ignored) {}
        }
    }
}
