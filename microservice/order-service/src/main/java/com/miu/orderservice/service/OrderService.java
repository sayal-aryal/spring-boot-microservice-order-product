package com.miu.orderservice.service;

import com.miu.orderservice.Enum.Status;
import com.miu.orderservice.model.Order;
import com.miu.orderservice.repository.OrderRepository;
import com.miu.orderservice.response.ProductResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class OrderService {
    @Autowired
    private OrderRepository repository;
    @Autowired
    private WebClient webClient;

    public void placeOrderByProductId(Long productId) {
        //Getting the product By ID
        ProductResponse product = webClient.get().uri("http://localhost:8082/products/{id}", productId).retrieve().bodyToMono(ProductResponse.class)//to retrieve format in ProductResponse
                .block();//to receive Synchronous Request
        product.setQuantity(product.getQuantity() - 1);
        //Updating the product after order has been placed
        webClient.put().uri("http://localhost:8082/products/{id}", productId).body(BodyInserters.fromValue(product)).retrieve().bodyToMono(ProductResponse.class).subscribe(updatedProduct -> {
            System.out.println("Updated Product" + updatedProduct.getName());
        });
        Order order = new Order(productId, LocalDateTime.now(), Status.SUCCESS.getName());
        repository.save(order);
    }

    public boolean cancel(Long id) {
        Optional<Order> order = repository.findById(id);
        if (order.isEmpty()){
            return false;
        }
        if (order.get().getStatus().equals(Status.SUCCESS.getName())) {

            //Getting the product By ID
            ProductResponse product = webClient.get().uri("http://localhost:8082/products/{id}", order.get().getProduct_id())
                    .retrieve().bodyToMono(ProductResponse.class)//to retrieve format in ProductResponse
                    .block();//to receive Synchronous Request
            product.setQuantity(product.getQuantity() + 1);
            //Updating the product after order has been cancelled
            webClient.put().uri("http://localhost:8082/products/{id}", order.get().getProduct_id()).body(BodyInserters.fromValue(product)).retrieve().bodyToMono(ProductResponse.class).subscribe(updatedProduct -> {
                System.out.println("Updated Product" + updatedProduct.getName());
            });

            order.get().setStatus(Status.CANCEL.getName());
            repository.save(order.get());
            return true;
        }
        return false;


    }


}
