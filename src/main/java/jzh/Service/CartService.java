package jzh.Service;

import jzh.entity.CartItem;
import jzh.entity.Product;
import jzh.mapper.CartMapper;
import jzh.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartMapper cartMapper;
    
    @Autowired
    private ProductMapper productMapper;

    @Transactional
    public String addToCart(Long userId, Long productId, String size, Integer quantity) {
        // 检查商品是否存在
        Product product = productMapper.findById(productId);
        if (product == null) {
            return "商品不存在";
        }

        // 检查是否已存在相同商品和尺码的购物车项
        CartItem existingItem = cartMapper.findByUserIdAndProductIdAndSize(userId, productId, size);
        
        if (existingItem != null) {
            // 如果已存在，更新数量
            cartMapper.updateQuantity(userId, productId, size, quantity);
            return "已更新购物车";
        } else {
            // 如果不存在，创建新的购物车项
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(productId);
            cartItem.setSize(size);
            cartItem.setQuantity(quantity);
            cartItem.setPrice(product.getPrice());
            cartItem.setProduct(product); // 设置商品信息
            
            cartMapper.insert(cartItem);
            return "已添加到购物车";
        }
    }

    public List<CartItem> getCartItems(Long userId) {
        List<CartItem> cartItems = cartMapper.findByUserId(userId);
        // 为每个购物车项设置商品信息
        for (CartItem item : cartItems) {
            Product product = productMapper.findById(item.getProductId());
            item.setProduct(product);
        }
        return cartItems;
    }

    @Transactional
    public String removeFromCart(Long id) {
        int result = cartMapper.deleteById(id);
        return result > 0 ? "删除成功" : "删除失败";
    }

    @Transactional
    public String clearCart(Long userId) {
        int result = cartMapper.deleteByUserId(userId);
        return result > 0 ? "清空成功" : "清空失败";
    }
} 