package ru.akirakozov.sd.refactoring;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;

import javax.servlet.Servlet;
import java.util.HashMap;
import java.util.Map;

public class ServletServer implements Runnable {
    private final Server server;
    private static final Map<String, Servlet> SERVLETS = new HashMap<>();
    private final DataBase dataBase = new DataBase();

    static {
        SERVLETS.put("/add-product", new AddProductServlet());
        SERVLETS.put("/get-products", new GetProductsServlet());
        SERVLETS.put("/query", new QueryServlet());
    }

    public ServletServer(int port) {
        server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.setContextPath("/");
        server.setHandler(context);

        for (Map.Entry<String, Servlet> entry : SERVLETS.entrySet()) {
            context.addServlet(new ServletHolder(entry.getValue()), entry.getKey());
        }
    }

    public void run() {
        try {
            server.start();
            server.join();
            System.out.println("Server started");
        } catch (Exception e) {
            System.err.println("Can't start server. Cause: " + e.getMessage());
        }
    }
    
    public void stop() {
        try {
            server.stop();
            System.out.println("Server stopped");
        } catch (Exception e) {
            System.err.println("Can't stop server. Cause: " + e.getMessage());
        }
    }

    public void clearDataBase() {
        dataBase.executeUpdateSql("DROP TABLE IF EXISTS PRODUCT");
    }

    public void initDataBase() {
        dataBase.executeUpdateSql(
                "CREATE TABLE IF NOT EXISTS PRODUCT" +
                        "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                        " NAME           TEXT    NOT NULL, " +
                        " PRICE          INT     NOT NULL)");
    }
}
