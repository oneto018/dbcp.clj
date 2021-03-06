package org.httpkit.dbcp;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.httpkit.dbcp.PerThreadDataSource;
import org.junit.Assert;
import org.junit.Test;

public class TimeoutTest {

    private void printResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();

        int count = metaData.getColumnCount();
        for (int i = 1; i <= count; i++) {
            String label = metaData.getColumnLabel(i);
            // System.out.printf(latel + "\t");
        }
        System.out.println();

        while (rs.next()) {
            for (int i = 1; i <= count; i++) {

                Object value = rs.getObject(i);
                // System.out.printf(value + "\t");
            }
            // System.out.println();
        }
    }

    PerThreadDataSource dataSource = Constants.newDS();

    @Test
    public void testTimeout() throws SQLException, InterruptedException {
        Connection con = dataSource.getConnection();

        Statement statment = con.createStatement();
        ResultSet rs = statment.executeQuery("show global variables like 'wait_timeout'");
        printResultSet(rs);
        rs.close();

        rs = statment.executeQuery("show variables like 'wait_timeout'");
        printResultSet(rs);
        rs.close();

        statment.executeUpdate("set wait_timeout = 2");

        rs = statment.executeQuery("show variables like 'wait_timeout'");
        printResultSet(rs);
        rs.close();

        Thread.sleep(3000);
        // should timeout

        try {
            rs = statment.executeQuery("show variables like 'wait_timeout'");
            printResultSet(rs);
            rs.close();
            Assert.fail("should timeout");
        } catch (SQLException ignore) {

            // ignore.printStackTrace();
        }

    }
}
