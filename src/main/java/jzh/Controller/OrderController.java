package jzh.Controller;

import jzh.Service.OrderService;
import jzh.entity.Order;
import jzh.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/orderlist/create")
    @ResponseBody
    public String createOrder(@RequestParam Long productId,
                            @RequestParam String size,
                            @RequestParam(defaultValue = "1") Integer quantity,
                            HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "请先登录";
        }
        return orderService.createOrder(user.getId(), productId, size, quantity);
    }

    @GetMapping("/orderlist")
    public String viewOrders(@RequestParam(defaultValue = "1") int page,
                           @RequestParam(defaultValue = "5") int size,
                           Model model, 
                           HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        // 获取总记录数
        int totalOrders = orderService.countOrdersByUserId(user.getId());
        // 计算总页数
        int totalPages = (int) Math.ceil((double) totalOrders / size);
        
        // 获取当前页的订单
        List<Order> orders = orderService.getOrdersByUserIdWithPagination(user.getId(), page, size);
        
        // 格式化日期时间
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (Order order : orders) {
            if (order.getCreateTime() != null) {
                order.setFormattedCreateTime(order.getCreateTime().format(formatter));
            }
        }
        
        model.addAttribute("orders", orders);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);
        
        return "orderlist";
    }

    @GetMapping("/orderlist/{id}/status")
    @ResponseBody
    public Object updateOrderStatus(@PathVariable Long id,
                                    @RequestParam String status,
                                    HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "请先登录";
        }
        return orderService.updateOrderStatus(id, status);
    }

    @GetMapping("/orderlist/{id}/payment")
    public String showPaymentPage(@PathVariable Long id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        Order order = orderService.getOrderById(id);
        if (order == null || !order.getUserId().equals(user.getId())) {
            return "redirect:/orderlist";
        }
        
        model.addAttribute("order", order);
        return "payment";
    }
} 