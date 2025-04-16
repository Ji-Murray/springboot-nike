package jzh.Controller;

import jzh.Service.CartService;
import jzh.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import java.math.BigDecimal;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping
    public String viewCart(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }

        model.addAttribute("cartItems", cartService.getCartItems(user.getId()));
        model.addAttribute("total", calculateTotal(user.getId()));
        return "cart";
    }

    @PostMapping("/add")
    @ResponseBody
    public String addToCart(@RequestParam Long productId,
                          @RequestParam String size,
                          @RequestParam Integer quantity,
                          HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "请先登录";
        }
        return cartService.addToCart(user.getId(), productId, size, quantity);
    }

    @PostMapping("/remove")
    @ResponseBody
    public String removeFromCart(@RequestParam Long cartItemId) {
        return cartService.removeFromCart(cartItemId);
    }

    @PostMapping("/clear")
    @ResponseBody
    public String clearCart(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "请先登录";
        }
        return cartService.clearCart(user.getId());
    }

    private BigDecimal calculateTotal(Long userId) {
        return cartService.getCartItems(userId).stream()
                .map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}