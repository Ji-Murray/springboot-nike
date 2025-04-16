package jzh.entity;

import lombok.Data;
import org.apache.ibatis.type.Alias;
import java.math.BigDecimal;

@Data
@Alias("CartItem")
public class CartItem {
    private Long id;
    private Long userId;
    private Long productId;
    private String size;
    private Integer quantity;
    private BigDecimal price;
    private String createdAt;
    private String updatedAt;
    
    // 关联的商品信息
    private Product product;
} 