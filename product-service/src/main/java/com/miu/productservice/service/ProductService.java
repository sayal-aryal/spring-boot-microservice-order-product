package com.miu.productservice.service;

import com.miu.productservice.model.Product;
import com.miu.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public List<Product> getAll()
    {
        List<Product> products = new ArrayList<>();
        repository.findAll().forEach(product -> products.add(product));
        return products;
    }

    public Product getById(Long id)
    {
        return repository.findById(id).get();
    }

    public Product saveOrUpdate(Product product)
    {
       return  repository.save(product);
    }

    public void delete(Long id)
    {
        repository.deleteById(id);
    }

}
