/**
 * 
 */
package com.nuevatel.common.ds;

import static com.nuevatel.common.util.Util.castAs;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jolbox.bonecp.BoneCP;
import com.nuevatel.common.util.Parameters;

/**
 * @author asalazar
 *
 */
class SimpleDataSourceManager implements DataSourceManager {

    private BoneCP boneCP;

    public void setBoneCP(BoneCP boneCP) {
        this.boneCP = boneCP;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Connection getConnection() throws SQLException {
        return boneCP.getConnection();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CallableStatement makeStatement(Connection conn, String query, Object... values) throws SQLException {
        Parameters.checkNull(conn, "conn");
        Parameters.checkBlankString(query, "query");

        CallableStatement stm = conn.prepareCall(query);

        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                stm.setObject(i + 1, values[i]);
            }
        }

        return stm;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CallableStatement makeStatement(Connection conn, String query, AbstractSqlParam<?>[] parameters) throws SQLException {
        Parameters.checkNull(conn, "conn");
        Parameters.checkBlankString(query, "query");

        CallableStatement stm = conn.prepareCall(query);

        if (parameters != null) {
            for (int i = 0; i < parameters.length; i++) {
                AbstractSqlParam<?>param = parameters[i];
                int parameterIndex = i + 1;

                if (param instanceof OutSqlParam) {
                    stm.registerOutParameter(parameterIndex, castAs(OutSqlParam.class, param).getValue());
                } else {
                    stm.setObject(parameterIndex, param.getValue());
                }
            }
        }

        return stm;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResultSet execute(Connection conn, String query, Object... values) throws SQLException {
        Parameters.checkNull(conn, "conn");
        Parameters.checkBlankString(query, "query");

        PreparedStatement stm = conn.prepareStatement(query);

        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                stm.setObject(i + 1, values[i]);
            }
        }

        ResultSet resultSet = stm.executeQuery();

        return resultSet;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void shutdownConnPool() {
        if (boneCP != null) {
            boneCP.shutdown();
        }
    }
}
