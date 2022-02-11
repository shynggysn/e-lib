package edu.epam.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCDataSource
{

    private static JDBCDataSource datasource;
    private JDBCDataSource() {
    }

    private ComboPooledDataSource cpds = null;

    public static JDBCDataSource getInstance() {
        if (datasource == null) {

            datasource = new JDBCDataSource();
            datasource.cpds = new ComboPooledDataSource();
            try {
                datasource.cpds.setDriverClass(PropertyReader.getValue("driver"));
            } catch (Exception e) {

            }
            datasource.cpds.setJdbcUrl(PropertyReader.getValue("url"));
            datasource.cpds.setUser(PropertyReader.getValue("username"));
            datasource.cpds.setPassword(PropertyReader.getValue("password"));
            datasource.cpds.setInitialPoolSize(Integer.parseInt(PropertyReader.getValue("initialPoolSize")));
            datasource.cpds.setAcquireIncrement(Integer.parseInt(PropertyReader.getValue("acquireIncrement")));
            datasource.cpds.setMaxPoolSize(Integer.parseInt(PropertyReader.getValue("maxPoolSize")));
            datasource.cpds.setMaxIdleTime(DataUtility.getInt(PropertyReader.getValue("timeout")));
            datasource.cpds.setMinPoolSize(Integer.parseInt(PropertyReader.getValue("minPoolSize")));
        }
        return datasource;
    }


    public static Connection getConnection() throws SQLException {
        return getInstance().cpds.getConnection();
    }

    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}