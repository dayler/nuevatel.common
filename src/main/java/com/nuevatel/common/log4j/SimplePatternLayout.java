package com.nuevatel.common.log4j;

import com.nuevatel.common.util.StringUtils;
import org.apache.log4j.PatternLayout;

import java.util.Date;

/**
 * Overwrite default SimplePatternLayout in order to insert a header in the created log file.
 *
 * @author asalazar
 */
public class SimplePatternLayout extends PatternLayout {

    /**
     * Version for the application.
     */
    private static String version = StringUtils.EMPTY;

    /**
     * Application name.
     */
    private static String appName = StringUtils.EMPTY;

    /**
     * Set application version  to log in the log file header.
     *
     * @param ver Version to set.
     */
    public static void setVersion(String ver) {
        if (StringUtils.isBlank(version)) {
            version = ver;
        }
    }

    /**
     * Name of the application to log in the log file header.
     *
     * @param name Name of the application.
     */
    public static void setAppName(String name) {
        if (StringUtils.isBlank(appName)) {
            appName = name;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getHeader() {
        StringBuilder builder = new StringBuilder("##");
        builder.append(StringUtils.END_LINE);
        builder.append("##");
        builder.append(StringUtils.END_LINE);
        builder.append(StringUtils.END_LINE);

        builder.append("Application: ");
        builder.append(appName);
        builder.append(StringUtils.END_LINE);

        builder.append("Version: ");
        builder.append(version);
        builder.append(StringUtils.END_LINE);

        builder.append("Creation Date: ");
        builder.append(new Date());
        builder.append(StringUtils.END_LINE);
        builder.append(StringUtils.END_LINE);

        builder.append("##");
        builder.append(StringUtils.END_LINE);
        builder.append("##");
        builder.append(StringUtils.END_LINE);
        builder.append(StringUtils.END_LINE);

        return builder.toString();
    }

}
