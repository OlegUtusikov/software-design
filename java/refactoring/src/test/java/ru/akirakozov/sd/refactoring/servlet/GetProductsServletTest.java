package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.ServletServer;

import static org.junit.Assert.*;

class GetProductsServletTest {


    private void trySleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            System.err.println("Can't sleep. Cause: " + e.getMessage());
        }
    }

    @Test
    public void testServer() {
        ServletServer server = new ServletServer(8081);
        server.clearDataBase();
        server.initDataBase();
        Thread serverThread = new Thread(server);
        serverThread.start();
        trySleep(200);
        String response = Utils.getResponse(Utils.sendRequest("http://localhost:8081/get-products"));
        assertEquals("<html><body></body></html>", response);
        response = Utils.getResponse(Utils.sendRequest("http://localhost:8081/add-product?name=iphone6&price=1200"));
        assertEquals("OK", response);
        response = Utils.getResponse(Utils.sendRequest("http://localhost:8081/get-products"));
        assertEquals("<html><body>iphone6\t1200</br></body></html>", response);
        response = Utils.getResponse(Utils.sendRequest("http://localhost:8081/query?command=sum"));
        assertEquals("Summary price: <html><body>1200</body></html>", response);
        response = Utils.getResponse(Utils.sendRequest("http://localhost:8081/query?command=count"));
        assertEquals("Number of products: <html><body>1</body></html>", response);
        response = Utils.getResponse(Utils.sendRequest("http://localhost:8081/add-product?name=honor20&price=300"));
        assertEquals("OK", response);
        response = Utils.getResponse(Utils.sendRequest("http://localhost:8081/query?command=count"));
        assertEquals("Number of products: <html><body>2</body></html>", response);
        response = Utils.getResponse(Utils.sendRequest("http://localhost:8081/query?command=sum"));
        assertEquals("Summary price: <html><body>1500</body></html>", response);
        response = Utils.getResponse(Utils.sendRequest("http://localhost:8081/query?command=min"));
        assertEquals("<h1>Product with min price: </h1><html><body>honor20\t300</br></body></html>", response);
        response = Utils.getResponse(Utils.sendRequest("http://localhost:8081/query?command=max"));
        assertEquals("<h1>Product with max price: </h1><html><body>iphone6\t1200</br></body></html>", response);
        response = Utils.getResponse(Utils.sendRequest("http://localhost:8081/get-products"));
        assertEquals("<html><body>iphone6\t1200</br>honor20\t300</br></body></html>", response);
        serverThread.suspend();
    }
}