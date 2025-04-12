package jzh.Service;

import jzh.mapper.ProductMapper;
import jzh.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
    
    @Autowired
    private ProductMapper productMapper;
    
    public List<Product> getAllProducts() {
        return productMapper.findAll();
    }
    
    public List<Product> getProductsByCategory(String category) {
        return productMapper.findByCategory(category);
    }
    
    public List<Product> getRecommendedProducts() {
        return productMapper.findRecommended();
    }
    
    public Product getProductById(Long id) {
        return productMapper.findById(id);
    }
    
    public int getProductCountByCategory(String category) {
        return productMapper.countByCategory(category);
    }
    
    public List<Product> getFilteredProducts(String[] genders, String price) {
        return productMapper.findFilteredProducts(genders, price);
    }
    
    public int addProduct(Product product) {
        return productMapper.insert(product);
    }
    
    public int updateProduct(Product product) {
        return productMapper.update(product);
    }
    
    public int deleteProduct(Long id) {
        return productMapper.deleteById(id);
    }
} 