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

public class ServletServer {
    private final Server server;
    private static final Map<String, Servlet> pathes = new HashMap<>();

    static {
        pathes.put("/add-product", new AddProductServlet());
        pathes.put("/get-products", new GetProductsServlet());
        pathes.put("/query", new QueryServlet());
    }

    public ServletServer(int port) {
        server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.setContextPath("/");
        server.setHandler(context);

        for (Map.Entry<String, Servlet> entry : pathes.entrySet()) {
            context.addServlet(new ServletHolder(entry.getValue()), entry.getKey());
        }
    }

    public void start() {
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
            System.out.println("Server stoped");
        } catch (Exception e) {
            System.err.println("Can't stop server. Cause: " + e.getMessage());
        }
    }
}
