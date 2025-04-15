package jzh.mapper;

import jzh.entity.CartItem;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CartMapper {
    
    @Insert("INSERT INTO cart_item (user_id, product_id, size, quantity, price) " +
            "VALUES (#{userId}, #{productId}, #{size}, #{quantity}, #{price})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(CartItem cartItem);
    
    @Select("SELECT * FROM cart_item WHERE user_id = #{userId}")
    List<CartItem> findByUserId(Long userId);
    
    @Select("SELECT * FROM cart_item WHERE user_id = #{userId} AND product_id = #{productId} AND size = #{size}")
    CartItem findByUserIdAndProductIdAndSize(@Param("userId") Long userId, 
                                           @Param("productId") Long productId,
                                           @Param("size") String size);
    
    @Update("UPDATE cart_item SET quantity = quantity + #{quantity} " +
            "WHERE user_id = #{userId} AND product_id = #{productId} AND size = #{size}")
    int updateQuantity(@Param("userId") Long userId,
                      @Param("productId") Long productId,
                      @Param("size") String size,
                      @Param("quantity") Integer quantity);
    
    @Delete("DELETE FROM cart_item WHERE id = #{id}")
    int deleteById(Long id);
    
    @Delete("DELETE FROM cart_item WHERE user_id = #{userId}")
    int deleteByUserId(Long userId);
} 