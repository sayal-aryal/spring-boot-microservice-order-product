package com.miu.apigateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpMethod;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.web.server.ResponseStatusException;


@Component

public class ProductFilter extends AbstractGatewayFilterFactory<ProductFilter.Config> {

    public static final String SECRET_KEY = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";//Generated a 32bit Secret Key

    public ProductFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            HttpHeaders headers = request.getHeaders();
            String authorizationHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);

            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String jwtToken = authorizationHeader.substring(7); // Remove "Bearer " prefix

                try {
                    Claims claims = Jwts.parser()
                            .setSigningKey(SECRET_KEY)
                            .parseClaimsJws(jwtToken)
                            .getBody();

                    String role = (String) claims.get("role");

                    if (request.getMethod() == HttpMethod.POST || request.getMethod() == HttpMethod.PUT || request.getMethod() == HttpMethod.DELETE ) {
                        if(role.equals("ROLE_ADMIN")){
                            return chain.filter(exchange);
                        } else {
                            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied");
                        }

                    } else {
                        return chain.filter(exchange);
                    }
                } catch (Exception e) {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
                }
            } else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
            }
        };
    }

    public static class Config {
        // Configuration properties, if any
    }
}






