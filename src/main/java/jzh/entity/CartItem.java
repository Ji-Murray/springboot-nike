package jzh.entity;

import lombok.Data;

@Data
public class CartItem {
    private Long id;
    private Long userId;
    private Long productId;
    private String size;
    private Integer quantity;
    private Double price;
    private Product product;
} 