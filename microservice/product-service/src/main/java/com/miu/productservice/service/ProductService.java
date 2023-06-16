package com.miu.productservice.service;

import com.miu.productservice.model.Product;
import com.miu.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public List<Product> getAll()
    {
       return repository.findAll().stream().filter(product->product.getQuantity()>0).collect(Collectors.toList());
    }

    public Optional<Product> getById(Long id)
    {
        Optional<Product> product=repository.findById(id);
        if(product.isEmpty()){
            return Optional.empty();
        }
       return product;

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
