package jzh.mapper;

import jzh.entity.CartItem;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface CartMapper {
    
    @Insert("INSERT INTO cart_item (user_id, product_id, size, quantity, price, create_time, update_time) " +
            "VALUES (#{userId}, #{productId}, #{size}, #{quantity}, #{price}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(CartItem cartItem);
    
    @Select("SELECT * FROM cart_item WHERE user_id = #{userId}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "userId", column = "user_id"),
        @Result(property = "productId", column = "product_id"),
        @Result(property = "size", column = "size"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "price", column = "price"),
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "updateTime", column = "update_time"),
        @Result(property = "product", column = "product_id", 
                one = @One(select = "jzh.mapper.ProductMapper.findById", fetchType = FetchType.EAGER))
    })
    List<CartItem> findByUserId(Long userId);
    
    @Select("SELECT * FROM cart_item WHERE user_id = #{userId} AND product_id = #{productId} AND size = #{size}")
    CartItem findByUserIdAndProductIdAndSize(@Param("userId") Long userId, 
                                           @Param("productId") Long productId,
                                           @Param("size") String size);
    
    @Update("UPDATE cart_item SET quantity = quantity + #{quantity}, update_time = #{updateTime} " +
            "WHERE user_id = #{userId} AND product_id = #{productId} AND size = #{size}")
    int updateQuantity(@Param("userId") Long userId,
                      @Param("productId") Long productId,
                      @Param("size") String size,
                      @Param("quantity") Integer quantity,
                      @Param("updateTime") String updateTime);
    
    @Delete("DELETE FROM cart_item WHERE id = #{id}")
    int deleteById(Long id);
    
    @Delete("DELETE FROM cart_item WHERE user_id = #{userId}")
    int deleteByUserId(Long userId);
} 