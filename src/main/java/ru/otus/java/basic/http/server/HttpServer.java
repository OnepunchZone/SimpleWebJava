package ru.otus.java.basic.http.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    private int port;
    private Dispatcher dispatcher;

    public HttpServer(int port) {
        this.port = port;
        this.dispatcher = new Dispatcher();
    }

    public void start() {

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server run on port: " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                new ProcessingRequests(socket, dispatcher);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
