package jzh.Controller;

import jzh.Service.CartService;
import jzh.entity.CartItem;
import jzh.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping ("/cart/add")
    @ResponseBody
    public String addToCart(@RequestParam Long productId,
                          @RequestParam String size,
                          @RequestParam(defaultValue = "1") Integer quantity,
                          HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "请先登录";
        }
        return cartService.addToCart(user.getId(), productId, size, quantity);
    }

    @GetMapping("/cart")
    public String viewCart(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        List<CartItem> cartItems = cartService.getCartItems(user.getId());
        model.addAttribute("cartItems", cartItems);
        
        // 计算总价
        double total = cartItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        model.addAttribute("total", total);
        
        return "cart";
    }

    @GetMapping("/cart/remove/{id}")
    @ResponseBody
    public String removeFromCart(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "请先登录";
        }
        return cartService.removeFromCart(id);
    }

    @GetMapping("/cart/clear")
    @ResponseBody
    public String clearCart(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "请先登录";
        }
        return cartService.clearCart(user.getId());
    }
} 