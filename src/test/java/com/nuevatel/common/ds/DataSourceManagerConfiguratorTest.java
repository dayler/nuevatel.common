/**
 * 
 */
package com.nuevatel.common.ds;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author asalazar
 *
 */
public class DataSourceManagerConfiguratorTest {

    private DataSourceManagerConfigurator configurator;

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        // No op
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        // No op
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        configurator = new DataSourceManagerConfigurator();
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        configurator = null;
    }

    /**
     * Test method for {@link com.nuevatel.common.ds.DataSourceManagerConfigurator#configure()}.
     * @throws SQLException 
     * @throws ClassNotFoundException 
     */
    @Test
    public void testConfigure() throws ClassNotFoundException, SQLException {
        DataSourceManager dsManager = configurator.configure()
        .setJdbcDriver("com.mysql.jdbc.Driver")
        .setJdbcUrl("jdbc:mysql://10.40.20.148:3306/asterisk")
        .setJdbcUser("root")
        .setJdbcPassword("dev4DM")
        .setMinConnPerPatition(2)
        .setMaxConnPerPatition(4)
        .setPartitionCount(1)
        .build();

        Connection conn = dsManager.getConnection();
        assertNotNull("Conncetion is null", conn);
        dsManager.shutdownConnPool();
    }

}
