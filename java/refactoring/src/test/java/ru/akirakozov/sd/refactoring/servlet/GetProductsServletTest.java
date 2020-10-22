package ru.akirakozov.sd.refactoring.servlet;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.ServletServer;

import static org.junit.jupiter.api.Assertions.*;

class GetProductsServletTest {

    ServletServer server;
    @Before
    public void prepare() {
        server = new ServletServer(8081);
        server.start();
    }

    @After
    public void finish() {
        server.stop();
    }

    @Test
    public void testAdd() {

    }

    @Test
    public void testGet() {

    }

    @Test
    public void testQuery() {

    }
}