package ru.otus.java.basic.http.server;

import ru.otus.java.basic.http.server.processors.*;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

public class Dispatcher {
    private Map<String, RequestProcessor> processors;
    private RequestProcessor defaultNotFoundRequest;
    private RequestProcessor internalServerErrorRequest;

    public Dispatcher() {
        this.processors = new HashMap<>();
        this.processors.put("/", new MainPageRequestProcessor());
        this.processors.put("/another", new AnotherRequestProcessor());

        this.defaultNotFoundRequest = new NotFoundRequestProcessor();
        this.internalServerErrorRequest = new InternalServerErrorProcessor();
    }

    public void execute(HttpRequest request, OutputStream out) throws IOException {
        try {
            if (!processors.containsKey(request.getUri())) {
                defaultNotFoundRequest.execute(request, out);
                return;
            }
            processors.get(request.getUri()).execute(request, out);
        } catch (Exception e) {
            e.printStackTrace();
            internalServerErrorRequest.execute(request, out);
        }
    }
}
