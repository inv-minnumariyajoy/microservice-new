package com.mongo.product.repo;

import com.mongo.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface ProductRepository extends MongoRepository<Product, String> {
}
