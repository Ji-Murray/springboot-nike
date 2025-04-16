package jzh.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Product {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private String category; // 分类：新品、男子、女子、儿童
    private Integer stock; // 库存数量
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
} 