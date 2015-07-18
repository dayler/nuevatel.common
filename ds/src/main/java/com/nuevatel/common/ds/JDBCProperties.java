/**
 * 
 */
package com.nuevatel.common.ds;

import java.util.Properties;

import com.nuevatel.common.exception.InvalidPropertyValueException;
import com.nuevatel.common.util.IntegerUtil;
import com.nuevatel.common.util.Parameters;

/**
 * @author asalazar
 *
 */
public class JDBCProperties {

    // JDBC Conn pool
    public static final String PROP_JDBC_DRIVER = "datasource.jdbc.driver";
    public static final String PROP_JDBC_URL = "datasource.jdbc.url";
    public static final String PROP_JDBC_USER = "datasource.jdbc.user";
    public static final String PROP_JDBC_PASSWORD = "datasource.jdbc.password";
    public static final String PROP_JDBC_MIN_CONN_PER_PARTITION = "datasource.jdbc.minConnectionsPerPartition";
    public static final String PROP_JDBC_MAX_CONN_PER_PARTITION = "datasource.jdbc.maxConnectionsPerPartition";
    public static final String PROP_JDBC_PARTITION_COUNT = "datasource.jdbc.partitionCount";

    private String driver;

    private String url;

    private String user;

    private String password;

    private Integer minConnPerPartition=2;

    private Integer maxConnPerPartition=8;

    private Integer partitionCount = 1;

    public JDBCProperties(Properties prop) throws InvalidPropertyValueException {
        Parameters.checkNull(prop, "prop");

        driver = getRequiredProperty(PROP_JDBC_DRIVER, prop);
        url = getRequiredProperty(PROP_JDBC_URL, prop);
        user = getRequiredProperty(PROP_JDBC_USER, prop);
        password = getRequiredProperty(PROP_JDBC_PASSWORD, prop);
        minConnPerPartition = IntegerUtil.tryParse(prop.getProperty(PROP_JDBC_MIN_CONN_PER_PARTITION, null));
        maxConnPerPartition = IntegerUtil.tryParse(prop.getProperty(PROP_JDBC_MAX_CONN_PER_PARTITION, null));
        partitionCount = IntegerUtil.tryParse(prop.getProperty(PROP_JDBC_PARTITION_COUNT, null));
    }

    private String getRequiredProperty(String propName, Properties prop) throws InvalidPropertyValueException {
        String value = prop.getProperty(propName);

        if (value == null || value.isEmpty()) {
            throw new InvalidPropertyValueException(String.format("Property: %s cannot be blank", propName));
        }

        return value;
    }

    /**
     * @return the driver
     */
    public String getDriver() {
        return driver;
    }

    /**
     * @param driver the driver to set
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the minConnPerPartition
     */
    public Integer getMinConnPerPartition() {
        return minConnPerPartition;
    }

    /**
     * @param minConnPerPartition the minConnPerPartition to set
     */
    public void setMinConnPerPartition(Integer minConnPerPartition) {
        this.minConnPerPartition = minConnPerPartition;
    }

    /**
     * @return the maxConnPerPartition
     */
    public Integer getMaxConnPerPartition() {
        return maxConnPerPartition;
    }

    /**
     * @param maxConnPerPartition the maxConnPerPartition to set
     */
    public void setMaxConnPerPartition(Integer maxConnPerPartition) {
        this.maxConnPerPartition = maxConnPerPartition;
    }

    /**
     * @return the partitionCount
     */
    public Integer getPartitionCount() {
        return partitionCount;
    }

    /**
     * @param partitionCount the partitionCount to set
     */
    public void setPartitionCount(Integer partitionCount) {
        this.partitionCount = partitionCount;
    }

    @Override
    public synchronized String toString() {
        return String.format("JDBCPorperties{driver:%s url:%s user:%s password:%s "
                + "minConnPerPartition:%s maxConnPerPartition:%s partitionCount:%s}",
                driver, url, user, password, minConnPerPartition, maxConnPerPartition, partitionCount);
    }
}
