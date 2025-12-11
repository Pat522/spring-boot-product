package com.product.project.controller;
import com.product.project.model.Product;
import com.product.project.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api")
public class ProductController
{
    @Autowired
    private ProductService service;
    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return service.getAllProducts();
    }

 @GetMapping("/products/{id}")
public ResponseEntity<?> getProduct(@PathVariable int id) {
    try {
        Product product = service.getProductById(id);

        if (product != null) {
            return new ResponseEntity<>(product, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Product not found", HttpStatus.NOT_FOUND);
        }

    } catch (Exception e) {
        
        return new ResponseEntity<>("An error occurred: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}


    @PostMapping("/products")
public ResponseEntity<?> addProduct(@RequestBody Product product) {

    try {
        Product product1 = service.addProduct(product);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    } catch (Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

   @PutMapping("/products/{id}")
public ResponseEntity<?> updateProduct(@PathVariable int id, @RequestBody Product product) {
    try {
        Product updatedProduct = service.updateProduct(id,product);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    } catch (Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}

   @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id) {
    try {
        Product product = service.getProductById(id);
        service.deleteProduct(id); 
        return new ResponseEntity<>("Deleted", HttpStatus.OK);

    } catch (Exception e) {     
        return new ResponseEntity<>("Product not found or could not be deleted: " + e.getMessage(),
                                    HttpStatus.NOT_FOUND);
    }
}


@GetMapping("/products/{id}/status")
public ResponseEntity<?> getProductStatus(@PathVariable int id) {
    try {
       
        Product product = service.getProductById(id);

        Map<String, Object> map = new HashMap<>();
        map.put("productId", product.getProductId());
        map.put("productName", product.getProductName());
        map.put("brand", product.getBrand());
        map.put("category", product.getCategory());
        map.put("price", product.getPrice());
        map.put("productStatus",product.getProductStatus());
        
         String status = product.isProductAvailable() && product.getStockQuantity() > 10 ? "Available": 
                            "Out of Stock";

        return ResponseEntity.ok(map);

    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Product Not Found", "productId", id));
    }
}
@GetMapping("/products/filter")
public ResponseEntity<?> filterProducts(
        @RequestParam(required = false) Boolean available
) {
    try {
        List<Product> allProducts = service.getAllProduct();

        List<Product> filteredProducts = allProducts.stream()
                .filter(p -> {
                    if (available != null && available) {
                        return p.getStockQuantity() > 10;
                    } else if (available != null && !available) {
                        return p.getStockQuantity() <= 10;
                    } else {
                        return true;}
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(filteredProducts);
    } catch (Exception e) {

        Map<String, String> error = new HashMap<>();
        error.put("message", "Error fetching product list");
        error.put("error", e.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}


}
