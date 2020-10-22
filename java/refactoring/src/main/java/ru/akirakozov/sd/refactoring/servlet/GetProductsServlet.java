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
public class GetProductsServlet extends HttpServlet {
    private final DataBase dataBase = new DataBase();

    private static class GetProductsResult extends SqlResult {
        @Override
        public void setResult(ResultSet result) {
            try {
                while (result.next()) {
                    String  name = result.getString("name");
                    int price  = result.getInt("price");
                    builder.append(name).append("\t").append(price).append("</br>\n");
                }
            } catch (SQLException e) {
                System.err.println("Get products result failed. Cause:" + e.getMessage());
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        GetProductsResult res = new GetProductsResult();
        dataBase.executeGetSql("SELECT * FROM PRODUCT", res);
        String html = Utils.generateHtml(res);
        response.getWriter().println(html);
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
