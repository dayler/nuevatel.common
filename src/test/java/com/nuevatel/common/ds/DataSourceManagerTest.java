package com.nuevatel.common.ds;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataSourceManagerTest {

    private static final String TEST_QUERY_1 = "SELECT * FROM asterisk.sip_friends where name!=? limit 10;";

    private static DataSourceManager dsManager;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        DataSourceManagerConfigurator configurator = new DataSourceManagerConfigurator();
        dsManager = configurator.configure()
        .setJdbcDriver("com.mysql.jdbc.Driver")
        .setJdbcUrl("jdbc:mysql://10.40.20.148:3306/asterisk")
        .setJdbcUser("root")
        .setJdbcPassword("dev4DM")
        .setMinConnPerPatition(2)
        .setMaxConnPerPatition(4)
        .setPartitionCount(1)
        .build();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        dsManager.shutdownConnPool();
    }

    @Before
    public void setUp() throws Exception {
        // No op
    }

    @After
    public void tearDown() throws Exception {
        // No op
    }

    @Test
    public void getConnection() throws SQLException {
        Connection conn = null;

        try {
            conn = dsManager.getConnection();
            assertNotNull("conn", conn);
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
    }

    @Test
    public void makeStatement() throws SQLException {
        Connection conn = null;
        PreparedStatement stm = null;
        ResultSet rs = null;

        try {
            conn = dsManager.getConnection();
            stm = dsManager.makeStatement(conn, TEST_QUERY_1, "sometext2");
            assertNotNull("Statement null", stm);
            assertTrue("No result sets in the statement", stm.execute());
            rs = stm.getResultSet();
            assertTrue("Empty result set", rs.next());
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (stm != null) {
                stm.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
    }

    @Test
    public void execute() throws SQLException {
        Connection conn = null;
        ResultSet rs = null;

        try {
            conn = dsManager.getConnection();
            rs = dsManager.execute(conn, TEST_QUERY_1, "sometext");
            assertNotNull("ResultSet is null", rs);
            assertTrue("No rows in the RS", rs.next());
        } finally {
            if (rs != null) {
                rs.close();
            }

            if (conn != null) {
                conn.close();
            }
        }
    }
}
