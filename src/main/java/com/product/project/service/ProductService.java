package com.product.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.product.project.model.Product;
import com.product.project.repository.ProductRepo;
import java.util.List;

@Service
public class ProductService 
{
    @Autowired
    private ProductRepo repo;
    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    public Product getProductById(int id) {
        return repo.findById(id)
                   .orElseThrow(() -> new RuntimeException("Product not found"));
    }

 public Product addProduct(Product product) {
    return repo.save(product);
}


    public Product updateProduct(int id,Product product) {
        return repo.save(product);
            }

    public void deleteProduct(int id) {
        repo.deleteById(id);
    }
   
public Product getById(int id) {
        return repo.findById(id)
                   .orElseThrow(() -> new RuntimeException("Product not found"));
    }
       
      public List<Product> getAllProduct() {
        return repo.findAll();
    }

    

   }
