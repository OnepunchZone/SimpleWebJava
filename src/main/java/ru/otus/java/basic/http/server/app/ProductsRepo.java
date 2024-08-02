package ru.otus.java.basic.http.server.app;

import java.math.BigDecimal;
import java.util.*;

public class ProductsRepo {
    private List<Products> products;

    public List<Products> getProducts() {
        return Collections.unmodifiableList(products);
    }

    public ProductsRepo() {
        this.products = new ArrayList<>(Arrays.asList(
                new Products(1L, "Milk", BigDecimal.valueOf(80)),
                new Products(2L, "Bread", BigDecimal.valueOf(32)),
                new Products(3L, "Cheese", BigDecimal.valueOf(320))
        ));
    }

    public Products add(Products product) {
        Long newId = products.stream().mapToLong(Products::getId).max().orElse(0L) + 1L;
        product.setId(newId);
        this.products.add(product);
        return product;
    }
}
