package jzh.entity;

import lombok.Data;

@Data
public class Product {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Double price;
    private String category; // 分类：新品、男子、女子、儿童
    private Boolean recommended; // 是否推荐
} 