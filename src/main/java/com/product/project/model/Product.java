package com.product.project.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;
    private String productName;
    private String description;
    private String brand;
    private BigDecimal price;
    private String category;

    private LocalDate releaseDate;
    private boolean productAvailable;
    private int stockQuantity;  
    private String productStatus;
    //public boolean isProductAvailable();
    
  
}
