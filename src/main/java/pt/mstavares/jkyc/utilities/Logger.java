package pt.mstavares.jkyc.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class is used for debugging purposes
 * @author Miguel Tavares
 */
public abstract class Logger {

    /** This attribute is used to format the timestamp */
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * This method is used to log an information message
     * @param className class name to being debugged
     * @param toLog string to being logged
     */
    public static void logInfo(String className, String toLog) {
        if(Boolean.parseBoolean(System.getenv("KYC_DEBUG"))) {
            System.out.println(buildLog(className, "INFO", toLog));
        }
    }

    /**
     * This method is used to log an error message
     * @param className class name to being debugged
     * @param toLog string to being logged
     */
    public static void logError(String className, String toLog) {
        if(Boolean.parseBoolean(System.getenv("KYC_DEBUG"))) {
            System.err.println(buildLog(className, "ERROR", toLog));
        }
    }

    /**
     * This methid builds the log message
     * @param className class name to being debugged
     * @param logType type of log, information or error
     * @param toLog string to being logged
     * @return log message
     */
    private static String buildLog(String className, String logType, String toLog) {
        StringBuilder stringBuilder = new StringBuilder();
        String timestamp = sdf.format(new Date());
        stringBuilder
                .append("[").append(logType).append("] ")
                .append("[").append(timestamp).append("] ")
                .append("[").append(className).append("] ")
                .append(toLog);
        return stringBuilder.toString();
    }

}
