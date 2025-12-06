package com.product.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.product.project.model.Product;

@Repository
public interface ProductRepo extends JpaRepository<Product, Integer> {

}
