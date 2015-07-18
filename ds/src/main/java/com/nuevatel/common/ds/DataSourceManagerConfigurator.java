/**
 * 
 */
package com.nuevatel.common.ds;

import java.sql.SQLException;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import com.nuevatel.common.util.Parameters;

/**
 * @author asalazar
 *
 */
public class DataSourceManagerConfigurator {

    public Configurator configure() {
        return new Configurator();
    }

    public Configurator configure(JDBCProperties jdbcProp) {
        Parameters.checkNull(jdbcProp, "jdbcProp");

        Configurator conf = configure()
        .setJdbcDriver(jdbcProp.getDriver())
        .setJdbcUrl(jdbcProp.getUrl())
        .setJdbcUser(jdbcProp.getUser())
        .setJdbcPassword(jdbcProp.getPassword())
        .setMinConnPerPatition(jdbcProp.getMinConnPerPartition())
        .setMaxConnPerPatition(jdbcProp.getMaxConnPerPartition())
        .setPartitionCount(jdbcProp.getPartitionCount());

        return conf;
    }

    public static final class Configurator {
        private static final int DEFAULT_PARTITION_COUNT = 1;
        private static final int DEFAULT_MAX_CONN_PER_PARTITION = 10;
        private static final int DEFAULT_MIN_CONN_PER_PATITION = 4;

        // Required prop.
        private String jdbcDriver;
        private String jdbcUrl;
        private String userName;
        private String password;

        // Prop with default porp.
        private Integer minConnPerPatition = DEFAULT_MIN_CONN_PER_PATITION;
        private Integer maxConnPerPatition = DEFAULT_MAX_CONN_PER_PARTITION;
        private Integer partitionCount = DEFAULT_PARTITION_COUNT;

        public DataSourceManager build() throws ClassNotFoundException, SQLException {
            Parameters.checkBlankString(jdbcDriver, "jdbcDriver");
            Parameters.checkBlankString(jdbcUrl, "jdbcUrl");
            Parameters.checkBlankString(userName, "userName");
            Parameters.checkBlankString(password, "password");

            Class.forName(jdbcDriver);

            // Check default values.
            minConnPerPatition = minConnPerPatition == null ? DEFAULT_MIN_CONN_PER_PATITION : minConnPerPatition;
            maxConnPerPatition = maxConnPerPatition == null ? DEFAULT_MAX_CONN_PER_PARTITION : maxConnPerPatition;
            partitionCount = partitionCount == null ? DEFAULT_PARTITION_COUNT : partitionCount;

            BoneCPConfig config = new BoneCPConfig();
            config.setJdbcUrl(jdbcUrl);
            config.setUsername(userName);
            config.setPassword(password);
            config.setMinConnectionsPerPartition(minConnPerPatition);
            config.setMaxConnectionsPerPartition(maxConnPerPatition);
            config.setPartitionCount(partitionCount);

            BoneCP boneCP = new BoneCP(config);
            // Set ds connection pool.
            SimpleDataSourceManager dsManager = new SimpleDataSourceManager();
            dsManager.setBoneCP(boneCP);
            return dsManager;
        }

        /**
         * @param jdbcDriver the jdbcDriver to set
         */
        public Configurator setJdbcDriver(String jdbcDriver) {
            this.jdbcDriver = jdbcDriver;
            return this;
        }
        /**
         * @param jdbcUrl the jdbcUrl to set
         */
        public Configurator setJdbcUrl(String jdbcUrl) {
            this.jdbcUrl = jdbcUrl;
            return this;
        }
        /**
         * @param userName the userName to set
         */
        public Configurator setJdbcUser(String userName) {
            this.userName = userName;
            return this;
        }
        /**
         * @param password the password to set
         */
        public Configurator setJdbcPassword(String password) {
            this.password = password;
            return this;
        }
        /**
         * @param minConnPerPatition the minConnPerPatition to set
         */
        public Configurator setMinConnPerPatition(Integer minConnPerPatition) {
            this.minConnPerPatition = minConnPerPatition;
            return this;
        }

        /**
         * @param maxConnPerPatition the maxConnPerPatition to set
         */
        public Configurator setMaxConnPerPatition(Integer maxConnPerPatition) {
            this.maxConnPerPatition = maxConnPerPatition;
            return this;
        }
        /**
         * @param partitionCount the partitionCount to set
         */
        public Configurator setPartitionCount(Integer partitionCount) {
            this.partitionCount = partitionCount;
            return this;
        }
    }
}
