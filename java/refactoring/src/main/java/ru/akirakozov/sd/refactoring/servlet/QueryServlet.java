package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.DataBase;
import ru.akirakozov.sd.refactoring.SqlResult;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {
    private final DataBase dataBase = new DataBase();

    private static class MaxProductResult extends SqlResult {
        @Override
        public void setResult(ResultSet result) {
            try {
                while (result.next()) {
                    String  name = result.getString("name");
                    int price  = result.getInt("price");
                    builder.append(name).append("\t").append(price).append("</br>");
                }
            } catch (SQLException e) {
                System.err.println("Can't parse result for get max product. Cause: " + e.getMessage());
            }
        }
    }

    private static class MinProductResult extends SqlResult {
        @Override
        public void setResult(ResultSet result) {
            try {
                while (result.next()) {
                    String  name = result.getString("name");
                    int price  = result.getInt("price");
                    builder.append(name).append("\t").append(price).append("</br>");
                }
            } catch (SQLException e) {
                System.err.println("Can't parse result for get min product. Cause: " + e.getMessage());
            }
        }
    }

    private static class SumProductResult extends SqlResult {
        @Override
        public void setResult(ResultSet result) {
            try {
                if (result.next()) {
                    builder.append(result.getInt(1));
                }
            } catch (SQLException e) {
                System.err.println("Can't parse result for get min product. Cause: " + e.getMessage());
            }
        }
    }

    private static class CountProductResult extends SqlResult {
        @Override
        public void setResult(ResultSet result) {
            try {
                if (result.next()) {
                    builder.append(result.getInt(1));
                }
            } catch (SQLException e) {
                System.err.println("Can't parse result for get min product. Cause: " + e.getMessage());
            }
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");
        SqlResult result = null;
        String description = "";
        if ("max".equals(command)) {
            description = "<h1>Product with max price: </h1>";
            result = new MaxProductResult();
            dataBase.executeGetSql("SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1", result);
        } else if ("min".equals(command)) {
            description = "<h1>Product with min price: </h1>";
            result = new MinProductResult();
            dataBase.executeGetSql("SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1", result);
        } else if ("sum".equals(command)) {
            description = "Summary price: ";
            result = new SumProductResult();
            dataBase.executeGetSql("SELECT SUM(PRICE) FROM PRODUCT", result);
        } else if ("count".equals(command)) {
            description = "Number of products: ";
            result = new CountProductResult();
            dataBase.executeGetSql("SELECT COUNT(*) FROM PRODUCT", result);
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.getWriter().println(description);
        if (result != null) {
            response.getWriter().println(Utils.generateHtml(result));
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}
