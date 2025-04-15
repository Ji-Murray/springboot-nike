package jzh.mapper;

import jzh.entity.Order;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderMapper {
    
    @Insert("INSERT INTO `order` (user_id, product_id, quantity, price, size, status, create_time) " +
            "VALUES (#{userId}, #{productId}, #{quantity}, #{price}, #{size}, #{status}, #{createTime})")
    void insert(Order order);
    
    @Select("SELECT * FROM `order` WHERE user_id = #{userId}")
    List<Order> findByUserId(Long userId);
    
    @Select("SELECT COUNT(*) FROM `order` WHERE user_id = #{userId}")
    int countByUserId(Long userId);
    
    @Select("SELECT * FROM `order` WHERE user_id = #{userId} ORDER BY create_time DESC LIMIT #{size} OFFSET #{offset}")
    List<Order> findByUserIdWithPagination(@Param("userId") Long userId, @Param("offset") int offset, @Param("size") int size);
    
    @Select("SELECT * FROM `order` ORDER BY create_time DESC LIMIT #{limit}")
    List<Order> findRecentOrders(int limit);
    
    @Select("SELECT * FROM `order`")
    List<Order> findAll();
    
    @Select("SELECT * FROM `order` WHERE status = #{status}")
    List<Order> findByStatus(String status);
    
    @Select("SELECT * FROM `order` WHERE id = #{id}")
    Order findById(Long id);
    
    @Update("UPDATE `order` SET status = #{status} WHERE id = #{id}")
    void updateStatus(@Param("id") Long id, @Param("status") String status);
    
    @Delete("DELETE FROM `order` WHERE id = #{id}")
    void deleteById(Long id);
    
    @Select("SELECT COUNT(*) FROM `order`")
    int count();
    
    @Select("SELECT COUNT(*) FROM `order` WHERE status = #{status}")
    int countByStatus(String status);
} 