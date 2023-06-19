package com.miu.productservice.controller;

import com.miu.productservice.model.Product;
import com.miu.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/products")
    private ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/products/{id}")
    private ResponseEntity<Product> getProducts(@PathVariable("id") Long id) {
        return ResponseEntity.ok(service.getById(id).get());
    }

    //creating a delete mapping that deletes a specified product
    @DeleteMapping("/products/{id}")
    private ResponseEntity<Void> delete(@PathVariable("id") Long id) {

        return ResponseEntity.noContent().build();
    }

    //creating post mapping that post the product detail in the database
    @PostMapping("/products")
    private ResponseEntity<Product> save(@RequestBody Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.saveOrUpdate(product));

    }

    //creating put mapping that updates the product detail
    @PutMapping("/products/{id}")
    private ResponseEntity<Product> update(@RequestBody Product product) {
        service.saveOrUpdate(product);
        return ResponseEntity.status(HttpStatus.OK).body(service.saveOrUpdate(product));
    }
}
