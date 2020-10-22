package ru.akirakozov.sd.refactoring;

/**
 * @author akirakozov
 */
public class Main {
    public static void main(String[] args) {
        ServletServer server = new ServletServer(8081);
        server.clearDataBase();
        server.initDataBase();
        server.run();
    }
}
