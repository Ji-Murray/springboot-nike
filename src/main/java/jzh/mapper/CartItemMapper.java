package jzh.mapper;

import jzh.entity.CartItem;
import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface CartItemMapper {
    @Insert("INSERT INTO cart_item (user_id, product_id, quantity, price, size, create_time, update_time) " +
            "VALUES (#{userId}, #{productId}, #{quantity}, #{price}, #{size}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(CartItem cartItem);

    @Select("SELECT * FROM cart_item WHERE user_id = #{userId} AND product_id = #{productId} AND size = #{size}")
    CartItem findByUserIdAndProductIdAndSize(@Param("userId") Long userId, @Param("productId") Long productId, @Param("size") String size);

    @Update("UPDATE cart_item SET quantity = #{quantity}, update_time = #{updateTime} WHERE id = #{id}")
    int update(CartItem cartItem);

    @Delete("DELETE FROM cart_item WHERE id = #{id}")
    int deleteById(Long id);

    @Delete("DELETE FROM cart_item WHERE user_id = #{userId}")
    int deleteByUserId(Long userId);

    @Select("SELECT ci.*, " +
            "p.id as p_id, " +
            "p.name as p_name, " +
            "p.description as p_description, " +
            "p.price as p_price, " +
            "p.image_url as p_image_url, " +
            "p.stock as p_stock, " +
            "p.category as p_category " +
            "FROM cart_item ci " +
            "LEFT JOIN product p ON ci.product_id = p.id " +
            "WHERE ci.user_id = #{userId}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "userId", column = "user_id"),
        @Result(property = "productId", column = "product_id"),
        @Result(property = "size", column = "size"),
        @Result(property = "quantity", column = "quantity"),
        @Result(property = "price", column = "price"),
        @Result(property = "createTime", column = "create_time"),
        @Result(property = "updateTime", column = "update_time"),
        @Result(property = "product.id", column = "p_id"),
        @Result(property = "product.name", column = "p_name"),
        @Result(property = "product.description", column = "p_description"),
        @Result(property = "product.price", column = "p_price"),
        @Result(property = "product.imageUrl", column = "p_image_url"),
        @Result(property = "product.stock", column = "p_stock"),
        @Result(property = "product.category", column = "p_category")
    })
    List<CartItem> findByUserId(@Param("userId") Long userId);
} 