package ru.otus.java.basic.http.server.processors;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.otus.java.basic.http.server.BadRequestException;
import ru.otus.java.basic.http.server.HttpRequest;
import ru.otus.java.basic.http.server.app.Products;
import ru.otus.java.basic.http.server.app.ProductsRepo;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class CreateNewProductProcessor implements RequestProcessor{
    private ProductsRepo productsRepo;
    private static final Logger logger = LogManager.getLogger(CreateNewProductProcessor.class.getName());

    public CreateNewProductProcessor(ProductsRepo productsRepo) {
        this.productsRepo = productsRepo;
    }

    @Override
    public void execute(HttpRequest request, OutputStream out) throws IOException, BadRequestException {
        try {
            Gson gson = new Gson();
            Products product = productsRepo.add(gson.fromJson(request.getBody(), Products.class));
            String productJson = gson.toJson(product);
            String response = "" +
                    "HTTP/1.1 201 Created\r\n" +
                    "Content-Type: application/json\r\n" +
                    "\r\n" +
                    productJson;
            out.write(response.getBytes(StandardCharsets.UTF_8));
        } catch (JsonParseException e) {
            logger.error("Wrong JSON format.", e);
            throw new BadRequestException("Некорректный формат входящего JSON объекта");
        }
    }
}
