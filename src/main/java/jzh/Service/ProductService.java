package jzh.Service;

import jzh.mapper.ProductMapper;
import jzh.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.time.LocalDateTime;

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
    
    public boolean addProduct(Product product) {
        try {
            product.setCreateTime(LocalDateTime.now());
            product.setUpdateTime(LocalDateTime.now());
            return productMapper.insert(product) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean updateProduct(Product product) {
        try {
            product.setUpdateTime(LocalDateTime.now());
            return productMapper.update(product) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteProduct(Long id) {
        try {
            return productMapper.deleteById(id) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<Product> searchProducts(String keyword) {
        return productMapper.searchProducts(keyword);
    }
    
    public List<Product> getHotProducts(int limit) {
        return productMapper.findHotProducts(limit);
    }
    
    public Product findById(Long id) {
        return productMapper.findById(id);
    }
    
    public boolean update(Product product) {
        try {
            product.setUpdateTime(LocalDateTime.now());
            return productMapper.update(product) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteById(Long id) {
        try {
            return productMapper.deleteById(id) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
} 