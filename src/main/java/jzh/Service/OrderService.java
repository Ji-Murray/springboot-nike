package jzh.Service;

import jzh.entity.Order;
import jzh.entity.Product;
import jzh.entity.User;
import jzh.mapper.OrderMapper;
import jzh.mapper.ProductMapper;
import jzh.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;
    
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public String createOrder(Long userId, Long productId, String size, Integer quantity) {
        Product product = productMapper.findById(productId);
        if (product == null) {
            return "商品不存在";
        }
        
        if (product.getStock() < quantity) {
            return "商品库存不足";
        }
        
        Order order = new Order();
        order.setUserId(userId);
        order.setProductId(productId);
        order.setSize(size);
        order.setQuantity(quantity);
        order.setPrice(product.getPrice());
        order.setStatus("待付款");
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        
        orderMapper.insert(order);
        return "订单创建成功";
    }

    public List<Order> getOrdersByUserId(Long userId) {
        List<Order> orders = orderMapper.findByUserId(userId);
        for (Order order : orders) {
            setOrderDetails(order);
        }
        return orders;
    }

    public int countOrdersByUserId(Long userId) {
        return orderMapper.countByUserId(userId);
    }

    public List<Order> getOrdersByUserIdWithPagination(Long userId, int page, int size) {
        int offset = (page - 1) * size;
        List<Order> orders = orderMapper.findByUserIdWithPagination(userId, offset, size);
        for (Order order : orders) {
            setOrderDetails(order);
        }
        return orders;
    }

    public Order getOrderById(Long id) {
        Order order = orderMapper.findById(id);
        if (order != null) {
            setOrderDetails(order);
        }
        return order;
    }

    @Transactional
    public String updateOrderStatus(Long id, String status) {
        Order order = orderMapper.findById(id);
        if (order == null) {
            return "订单不存在";
        }
        
        orderMapper.updateStatus(id, status);
        return "订单状态更新成功";
    }

    public List<Order> getAllOrders() {
        List<Order> orders = orderMapper.findAll();
        for (Order order : orders) {
            setOrderDetails(order);
        }
        return orders;
    }
    
    @Transactional
    public String deleteOrder(Long id) {
        Order order = orderMapper.findById(id);
        if (order == null) {
            return "订单不存在";
        }
        
        orderMapper.deleteById(id);
        return "订单删除成功";
    }
    
    public int getOrderCount() {
        return orderMapper.count();
    }
    
    public int getPendingOrderCount() {
        return orderMapper.countByStatus("待付款");
    }
    
    public int getCompletedOrderCount() {
        return orderMapper.countByStatus("已完成");
    }

    public List<Order> getRecentOrders(int limit) {
        List<Order> orders = orderMapper.findRecentOrders(limit);
        for (Order order : orders) {
            setOrderDetails(order);
        }
        return orders;
    }

    public List<Order> getOrdersByStatus(String status) {
        List<Order> orders = orderMapper.findByStatus(status);
        for (Order order : orders) {
            setOrderDetails(order);
        }
        return orders;
    }

    private void setOrderDetails(Order order) {
        Product product = productMapper.findById(order.getProductId());
        User user = userMapper.findById(order.getUserId());
        order.setProduct(product);
        order.setUser(user);
        order.setTotalAmount(order.getPrice().multiply(new BigDecimal(order.getQuantity())));
    }
}