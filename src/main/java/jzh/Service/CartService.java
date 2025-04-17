package jzh.Service;

import jzh.entity.CartItem;
import jzh.entity.Product;
import jzh.entity.User;
import jzh.mapper.CartItemMapper;
import jzh.mapper.CartMapper;
import jzh.mapper.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    @Autowired
    private CartMapper cartMapper;
    
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CartItemMapper cartItemMapper;
    
    @Autowired
    private ProductService productService;

    @Transactional
    public String addToCart(Long userId, Long productId, String size, Integer quantity) {
        try {
            // 检查商品是否存在
            Product product = productMapper.findById(productId);
            if (product == null) {
                logger.error("商品不存在，ID: {}", productId);
                return "商品不存在";
            }

            // 检查商品库存
            if (product.getStock() < quantity) {
                logger.error("商品库存不足，ID: {}, 库存: {}, 请求数量: {}", productId, product.getStock(), quantity);
                return "商品库存不足";
            }

            // 检查是否已存在相同商品和尺码的购物车项
            CartItem existingItem = cartItemMapper.findByUserIdAndProductIdAndSize(userId, productId, size);
            
            if (existingItem != null) {
                // 如果已存在，更新数量
                existingItem.setQuantity(existingItem.getQuantity() + quantity);
                existingItem.setUpdateTime(LocalDateTime.now().toString());
                int result = cartItemMapper.update(existingItem);
                return result > 0 ? "已更新购物车" : "更新失败";
            } else {
                // 如果不存在，创建新的购物车项
                CartItem cartItem = new CartItem();
                cartItem.setUserId(userId);
                cartItem.setProductId(productId);
                cartItem.setSize(size);
                cartItem.setQuantity(quantity);
                cartItem.setPrice(product.getPrice());
                cartItem.setCreateTime(LocalDateTime.now().toString());
                cartItem.setUpdateTime(LocalDateTime.now().toString());
                
                int result = cartItemMapper.insert(cartItem);
                if (result > 0) {
                    logger.info("成功添加购物车项，用户ID: {}, 商品ID: {}, 数量: {}", userId, productId, quantity);
                    return "已添加到购物车";
                } else {
                    logger.error("添加购物车项失败，用户ID: {}, 商品ID: {}", userId, productId);
                    return "添加失败";
                }
            }
        } catch (Exception e) {
            logger.error("添加购物车失败，用户ID: {}, 商品ID: {}", userId, productId, e);
            return "添加购物车失败：" + e.getMessage();
        }
    }

    public List<CartItem> getCartItems(Long userId) {
        List<CartItem> cartItems = cartItemMapper.findByUserId(userId);
        if (cartItems == null) {
            return new ArrayList<>();
        }
        
        // 为每个购物车项设置商品信息
        for (CartItem item : cartItems) {
            if (item != null && item.getProductId() != null) {
                Product product = productMapper.findById(item.getProductId());
                if (product != null) {
                    item.setProduct(product);
                } else {
                    logger.warn("商品不存在，ID: {}", item.getProductId());
                }
            }
        }
        return cartItems;
    }

    @Transactional
    public String removeFromCart(Long id) {
        try {
            int result = cartItemMapper.deleteById(id);
            if (result > 0) {
                return "删除成功";
            } else {
                return "删除失败，商品不存在";
            }
        } catch (Exception e) {
            logger.error("删除购物车项失败", e);
            return "删除失败: " + e.getMessage();
        }
    }

    @Transactional
    public String clearCart(Long userId) {
        int result = cartMapper.deleteByUserId(userId);
        return result > 0 ? "清空成功" : "清空失败";
    }

    @Transactional
    public void addToCart(User user, Product product, int quantity) {
        // 检查商品是否存在
        Product existingProduct = productService.findById(product.getId());
        if (existingProduct == null) {
            throw new RuntimeException("商品不存在");
        }

        // 检查商品库存
        if (existingProduct.getStock() < quantity) {
            throw new RuntimeException("商品库存不足");
        }

        // 检查购物车中是否已存在该商品
        CartItem existingItem = cartItemMapper.findByUserIdAndProductIdAndSize(user.getId(), product.getId(), "默认");
        
        if (existingItem != null) {
            // 更新数量
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            existingItem.setUpdateTime(LocalDateTime.now().toString());
            cartItemMapper.update(existingItem);
        } else {
            // 创建新的购物车项
            CartItem cartItem = new CartItem();
            cartItem.setUserId(user.getId());
            cartItem.setProductId(product.getId());
            cartItem.setSize("默认");
            cartItem.setQuantity(quantity);
            cartItem.setPrice(product.getPrice());
            cartItem.setCreateTime(LocalDateTime.now().toString());
            cartItem.setUpdateTime(LocalDateTime.now().toString());
            cartItemMapper.insert(cartItem);
        }
    }

    public BigDecimal calculateTotal(Long userId) {
        List<CartItem> cartItems = getCartItems(userId);
        return cartItems.stream()
                .map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
} 