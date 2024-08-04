package ru.otus.java.basic.http.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private final ExecutorService executorService;
    private int port;
    private Dispatcher dispatcher;
    private static final Logger logger = LogManager.getLogger(HttpServer.class);


    public HttpServer(int port) {
        this.port = port;
        this.dispatcher = new Dispatcher();
        this.executorService = Executors.newFixedThreadPool(10);
    }

    public void start() {

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Server run on port: " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                executorService.submit(new Task(socket, dispatcher));
            }

        } catch (IOException e) {
            logger.error("Wrong server action, invalid operation .", e);
        } finally {
            executorService.shutdown();
        }
    }
}