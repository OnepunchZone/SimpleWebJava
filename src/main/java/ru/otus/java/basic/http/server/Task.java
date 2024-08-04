package ru.otus.java.basic.http.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.Socket;


public class Task implements Runnable{
    private Socket socket;
    private Dispatcher dispatcher;
    private static final Logger logger = LogManager.getLogger(Task.class.getName());

    public Task(Socket socket, Dispatcher dispatcher) {
        this.socket = socket;
        this.dispatcher = dispatcher;
    }

    @Override
    public void run() {
        try {
            byte[] buffer = new byte[8192];
            int n = socket.getInputStream().read(buffer);
            if (n < 1) {
                return;
            }
            String rawRequest = new String(buffer, 0, n);
            HttpRequest request = new HttpRequest(rawRequest);
            request.printInfo();
            dispatcher.execute(request, socket.getOutputStream());
        } catch (IOException e) {
            logger.error("Wrong server action, invalid operation .", e);
        } catch (BadRequestException e) {
            logger.error("Wrong POST request.");
            throw new RuntimeException(e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                logger.error("Socket close error.", e);
            }
        }
    }
}
