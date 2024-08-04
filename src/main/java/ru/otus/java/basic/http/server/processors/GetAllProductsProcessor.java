package ru.otus.java.basic.http.server.processors;

import com.google.gson.Gson;
import ru.otus.java.basic.http.server.HttpRequest;
import ru.otus.java.basic.http.server.app.Products;
import ru.otus.java.basic.http.server.app.ProductsRepository;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class GetAllProductsProcessor implements RequestProcessor{
    private ProductsRepository productsRepository;

    public GetAllProductsProcessor(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @Override
    public void execute(HttpRequest request, OutputStream out) throws IOException {
        List<Products> productsLst = productsRepository.getProducts();
        Gson gson = new Gson();
        String productsJson = gson.toJson(productsLst);
        String response = "" +
                "HTTP/1.1 200 OK\r\n" +
                "Content-type: application/json\r\n" +
                "\r\n" +
                productsJson;
        out.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
