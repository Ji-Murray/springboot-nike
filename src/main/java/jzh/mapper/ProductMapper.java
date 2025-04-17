package jzh.mapper;

import jzh.entity.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface ProductMapper {

    @Select("SELECT * FROM product")
    List<Product> findAll();
    
    @Select("SELECT * FROM product WHERE category = #{category}")
    List<Product> findByCategory(String category);
    
    @Select("SELECT * FROM product WHERE name LIKE CONCAT('%', #{keyword}, '%') OR description LIKE CONCAT('%', #{keyword}, '%')")
    List<Product> searchProducts(String keyword);
    
    @Select("SELECT * FROM product WHERE recommended = TRUE")
    List<Product> findRecommended();
    
    @Select("SELECT * FROM product WHERE id = #{id}")
    Product findById(Long id);
    
    @Select("SELECT COUNT(*) FROM product WHERE category = #{category}")
    int countByCategory(String category);
    
    @Select("<script>" +
            "SELECT * FROM product WHERE 1=1 " +
            "<if test='genders != null and genders.length > 0'>" +
            "AND category IN " +
            "<foreach collection='genders' item='gender' open='(' separator=',' close=')'>" +
            "#{gender}" +
            "</foreach>" +
            "</if>" +
            "<if test='price != null and price != \"\"'>" +
            "<choose>" +
            "<when test='price == \"0-500\"'>AND price BETWEEN 0 AND 500</when>" +
            "<when test='price == \"500-1000\"'>AND price BETWEEN 500 AND 1000</when>" +
            "<when test='price == \"1000-2000\"'>AND price BETWEEN 1000 AND 2000</when>" +
            "<when test='price == \"2000+\"'>AND price >= 2001</when>" +
            "</choose>" +
            "</if>" +
            "</script>")
    List<Product> findFilteredProducts(@Param("genders") String[] genders, @Param("price") String price);
    
    @Insert("INSERT INTO product (name, description, price, stock, category, image_url, create_time, update_time) " +
            "VALUES (#{name}, #{description}, #{price}, #{stock}, #{category}, #{imageUrl}, #{createTime}, #{updateTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Product product);
    
    @Update("UPDATE product SET name = #{name}, description = #{description}, price = #{price}, " +
            "stock = #{stock}, category = #{category}, image_url = #{imageUrl}, update_time = #{updateTime} " +
            "WHERE id = #{id}")
    int update(Product product);
    
    @Delete("DELETE FROM product WHERE id = #{id}")
    int deleteById(Long id);

    @Select("SELECT p.*, COUNT(o.id) as order_count " +
            "FROM product p " +
            "LEFT JOIN `order` o ON p.id = o.product_id " +
            "GROUP BY p.id " +
            "ORDER BY order_count DESC " +
            "LIMIT #{limit}")
    List<Product> findHotProducts(int limit);

    @Select("SELECT * FROM products WHERE recommended = #{recommended}")
    List<Product> findByRecommended(boolean recommended);

    @Select("SELECT * FROM product WHERE recommended = true")
    List<Product> findRecommendedProducts();

    @Update("UPDATE product SET recommended = #{recommended} WHERE id = #{id}")
    void updateRecommended(@Param("id") Long id, @Param("recommended") Boolean recommended);
} 