package ru.otus.java.basic.http.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

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
                Queue<Runnable> taskList = new LinkedList<>();
                Task task = new Task(socket, dispatcher);
                taskList.add(task);

                Thread thread = new Thread(() -> {
                    while (true) {
                        Runnable newTask = taskList.poll();
                        if (newTask != null) {
                            newTask.run();
                        }
                    }
                });
                thread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
