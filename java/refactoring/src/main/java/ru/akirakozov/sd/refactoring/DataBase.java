package ru.akirakozov.sd.refactoring;

import java.sql.*;

public class DataBase {
    String DATA_BASE_NAME = "test.db";
    String DATA_BASE_URL = "jdbc:sqlite:" + DATA_BASE_NAME;

    public void executeUpdateSql(String sqlQuery) {
        try {
            try (Connection c = DriverManager.getConnection(DATA_BASE_URL)) {
                Statement stmt = c.createStatement();
                stmt.executeUpdate(sqlQuery);
                stmt.close();
            }
        } catch (SQLException e) {
            System.err.println("Can't execute update sql query.\n Query: " + sqlQuery + "\n Cause:" + e.getMessage());
        }
    }

    public void executeGetSql(String sqlQuery, SqlResult result) {
        try {
            try (Connection c = DriverManager.getConnection(DATA_BASE_URL)) {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery(sqlQuery);
                result.setResult(rs);
                rs.close();
                stmt.close();
            }
        } catch (SQLException e) {
            System.err.println("Can't execute get sql query.\n Query: " + sqlQuery + "\n Cause:" + e.getMessage());
        }
    }
}
