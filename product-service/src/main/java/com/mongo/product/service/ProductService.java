package com.mongo.product.service;

import com.mongo.product.dto.ProductRequest;
import com.mongo.product.dto.ProductResponse;
import com.mongo.product.model.Product;
import com.mongo.product.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {


    @Autowired
    private ProductRepository productRepository;

    public  void createProduct(ProductRequest productRequest){
        System.out.println("Bfbfg");
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        productRepository.save(product);
        log.info("Product {} is saved", product.getId());

    }

    public List<ProductResponse> getAllProducts(){

        List<Product> products = productRepository.findAll();
        return products.stream().map(this::mapToProductResponse).toList();


    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }



}
