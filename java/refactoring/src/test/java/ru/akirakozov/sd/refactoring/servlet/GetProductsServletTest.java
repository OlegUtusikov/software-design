package ru.akirakozov.sd.refactoring.servlet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.ServletServer;

import static org.junit.jupiter.api.Assertions.*;

class GetProductsServletTest {


    Thread serverThread = new Thread(new ServletServer(8081));
    @BeforeEach
    public void prepare() {
        serverThread.start();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            System.err.println("Can't sleep. Cause: " + e.getMessage());
        }
    }

    @AfterEach
    public void finish() {
        serverThread.interrupt();
    }

    @Test
    public void testSet() {

    }

    @Test
    public void testGet() {
        String response = Utils.getResponse(Utils.sendRequest("http://localhost:8081/get-products"));
        System.out.println(response);
    }

    @Test
    public void testQuery() {

    }
}