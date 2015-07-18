/**
 * 
 */
package com.nuevatel.common.ds;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interface for executing JDBC queries.
 * 
 * @author asalazar
 *
 */
public interface DataSourceManager {

    /**
     * @return Connection to perform an JDBC query. <b>After to use, needs to release conn.close()</b>
     * 
     * @throws SQLException Conncetion could not be retrieved.
     */
    Connection getConnection() throws SQLException;

    /**
     * Make an statement for sql <b>query</b>.
     * 
     * @param conn JDBC Connection from which build the statement.
     * @param query SQL Query to execute.
     * @param values Parameterized values for the SQL Query.
     * @return Statement for sql <b>query</b>.
     * @throws SQLException The <q>query</q> could not be executed.
     */
    CallableStatement makeStatement(Connection conn, String query, Object...values) throws SQLException;

    /**
     * Make an statement for sql <b>query</b>.
     * 
     * @param conn JDBC Connection from which build the statement.
     * @param query SQL Query to execute.
     * @param parameters Parameterized values for the SQL Query. Use {@link InSqlParam} to indicate
     * input parameter, and {@link OutSqlParam} to indicate out parameter.
     * @return Statement for sql <b>query</b>.
     * @throws SQLException The <q>query</q> could not be executed.
     */
    CallableStatement makeStatement(Connection conn, String query, AbstractSqlParam<?>[] parameters) throws SQLException;

    /**
     * Execute sql <b>query</b> and gets its result set.
     * 
     * @param conn JDBC Connection from which build the statement.
     * @param query SQL Query to execute.
     * @param values Parameterized values for the SQL Query.
     * @return The ResultSet from <q>query</q>.
     * @throws SQLException The <q>query</q> could not be executed.
     */
    ResultSet execute(Connection conn, String query, Object...values) throws SQLException;

    /**
     * Release all JDBC connections of the pool.
     */
    void shutdownConnPool();
}
