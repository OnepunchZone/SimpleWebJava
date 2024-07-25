package ru.otus.java.basic.http.server;

import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProcessingRequests {
    private ExecutorService executorService = Executors.newFixedThreadPool(3);


    public ProcessingRequests() {
    }

    public ProcessingRequests(Socket socket, Dispatcher dispatcher) {
        ProcessingRequests pr = new ProcessingRequests();

        Task task1 = new Task(socket, dispatcher);
        Task task2 = new Task(socket, dispatcher);
        Task task3 = new Task(socket, dispatcher);

        pr.submitTask(task1);
        pr.submitTask(task2);
        pr.submitTask(task3);

        pr.executorService.shutdown();

    }

    public void submitTask(Task task) {
        executorService.submit(task);
    }

        /*while (true) {
            try (Socket socket = serverSocket.accept()) {
                new Thread(() -> {
                    try {
                        byte[] buffer = new byte[8192];
                        int n = socket.getInputStream().read(buffer);
                        if (n < 1) {
                            return;
                        }
                        String rawRequest = new String(buffer, 0, n);
                        HttpRequest request = new HttpRequest(rawRequest);
                        request.printInfo(true);
                        dispatcher.execute(request, socket.getOutputStream());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/

//    private void thread2(Socket socket, Dispatcher dispatcher) {
//        try {
//            byte[] buffer = new byte[8192];
//            int n = socket.getInputStream().read(buffer);
//            if (n < 1) {
//                return;
//            }
//            String rawRequest = new String(buffer, 0, n);
//            HttpRequest request = new HttpRequest(rawRequest);
//            request.printInfo(true);
//            dispatcher.execute(request, socket.getOutputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    /*new Thread(() -> {
        try (Socket socket = serverSocket.accept()) {
            new Thread(() -> {
                try {
                    byte[] buffer = new byte[8192];
                    int n = socket.getInputStream().read(buffer);
                    if (n < 1) {
                        return;
                    }
                    String rawRequest = new String(buffer, 0, n);
                    HttpRequest request = new HttpRequest(rawRequest);
                    request.printInfo(true);
                    dispatcher.execute(request, socket.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }).start();*/
}
