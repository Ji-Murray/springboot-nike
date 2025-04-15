package jzh.entity;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Order {
    private Long id;
    private Long userId;
    private Long productId;
    private Integer quantity;
    private BigDecimal price;
    private String size;
    private String status;  // 待付款、已付款、已完成、已取消
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private BigDecimal totalAmount;
    private String formattedCreateTime; // 格式化后的创建时间
    
    // 关联对象
    private User user;
    private Product product;
} 