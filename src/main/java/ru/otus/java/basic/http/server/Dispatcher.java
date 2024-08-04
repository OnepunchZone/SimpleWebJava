package ru.otus.java.basic.http.server;

import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.java.basic.http.server.app.ProductsRepository;
import ru.otus.java.basic.http.server.processors.*;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Dispatcher {
    private Map<String, RequestProcessor> processors;
    private RequestProcessor defaultNotFoundRequest;
    private RequestProcessor internalServerErrorRequest;
    private static final Logger logger = LogManager.getLogger(Dispatcher.class.getName());

    private ProductsRepository productsRepository;

    public Dispatcher() {
        this.productsRepository = new ProductsRepository();

        this.processors = new HashMap<>();
        this.processors.put("GET /", new MainPageRequestProcessor());
        this.processors.put("GET /another", new AnotherRequestProcessor());
        this.processors.put("GET /products", new GetAllProductsProcessor(productsRepository));
        this.processors.put("POST /products", new CreateNewProductProcessor(productsRepository));

        this.defaultNotFoundRequest = new NotFoundRequestProcessor();
        this.internalServerErrorRequest = new InternalServerErrorProcessor();
    }

    public void execute(HttpRequest request, OutputStream out) throws IOException, BadRequestException {
        try {
            if (!processors.containsKey(request.getRoutingKey())) {
                logger.warn("Error routing key: {}", request.getRoutingKey());
                defaultNotFoundRequest.execute(request, out);
                return;
            }
            processors.get(request.getRoutingKey()).execute(request, out);
        } catch (BadRequestException e) {
            logger.error("Wrong POST request.", e);
            DefaultErrorDto defaultErrorDto = new DefaultErrorDto("CLIENT_DEFAULT_ERROR", e.getMessage());
            String jsonError = new Gson().toJson(defaultErrorDto);
            String response = "" +
                    "HTTP/1.1 400 Bad Request\r\n" +
                    "Content-Type: application/json\r\n" +
                    "\r\n" +
                    jsonError;
            out.write(response.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            logger.error("Wrong server action, invalid operation.", e);
            internalServerErrorRequest.execute(request, out);
        }
    }
}
