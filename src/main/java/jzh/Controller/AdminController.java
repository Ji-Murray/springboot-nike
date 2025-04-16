package jzh.Controller;

import jzh.Service.AdminService;
import jzh.Service.OrderService;
import jzh.Service.ProductService;
import jzh.Service.UserService;
import jzh.entity.Admin;
import jzh.entity.Order;
import jzh.entity.Product;
import jzh.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestParam String username, @RequestParam String password) {
        Admin admin = adminService.login(username, password);
        if (admin != null) {
            return "登录成功";
        } else {
            return "用户名或密码错误";
        }
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("productCount", productService.getAllProducts().size());
        model.addAttribute("orderCount", orderService.getOrderCount());
        model.addAttribute("userCount", userService.getAllUsers().size());
        model.addAttribute("recentOrders", orderService.getRecentOrders(5));
        model.addAttribute("hotProducts", productService.getHotProducts(5));
        return "admin/dashboard";
    }

    @GetMapping("/products")
    public String products(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "admin/products";
    }

    @PostMapping("/products")
    @ResponseBody
    public Map<String, Object> addProduct(@RequestParam("name") String name,
                                        @RequestParam("price") BigDecimal price,
                                        @RequestParam("stock") Integer stock,
                                        @RequestParam("category") String category,
                                        @RequestParam(value = "description", required = false) String description,
                                        @RequestParam("image") MultipartFile image) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 保存图片
            String imageUrl = saveImage(image);
            
            // 创建商品对象
            Product product = new Product();
            product.setName(name);
            product.setPrice(price);
            product.setStock(stock);
            product.setCategory(category);
            product.setDescription(description);
            product.setImageUrl(imageUrl);
            product.setCreateTime(LocalDateTime.now());
            product.setUpdateTime(LocalDateTime.now());
            
            if (productService.addProduct(product)) {
                result.put("success", true);
                result.put("message", "商品添加成功");
            } else {
                result.put("success", false);
                result.put("message", "商品添加失败");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("message", "商品添加失败：" + e.getMessage());
        }
        return result;
    }

    private String saveImage(MultipartFile image) throws IOException {
        if (image.isEmpty()) {
            throw new RuntimeException("请选择图片");
        }
        
        // 获取文件扩展名
        String originalFilename = image.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        
        // 生成新的文件名
        String newFilename = UUID.randomUUID().toString() + extension;
        
        // 获取项目根目录
        String projectRoot = System.getProperty("user.dir");
        
        // 保存文件
        String uploadDir = projectRoot + "/src/main/resources/static/img/";
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        
        File file = new File(dir, newFilename);
        image.transferTo(file);
        
        // 返回图片URL
        return "/img/" + newFilename;
    }

    @GetMapping("/products/get/{id}")
    @ResponseBody
    public Product getProduct(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping("/products/update")
    @ResponseBody
    public String updateProduct(@ModelAttribute Product product) {
        try {
            // 调用service层更新商品
            boolean result = productService.updateProduct(product);
            if (result) {
                return "success";
            } else {
                return "更新失败";
            }
        } catch (Exception e) {
            return "error: " + e.getMessage();
        }
    }

    @PostMapping("/products/delete/{id}")
    @ResponseBody
    public String deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteById(id);
            return "success";
        } catch (Exception e) {
            return "error";
        }
    }

    @GetMapping("/orders")
    public String orders(@RequestParam(required = false) String status, Model model) {
        List<Order> orders;
        if (status != null && !status.isEmpty()) {
            orders = orderService.getOrdersByStatus(status);
        } else {
            orders = orderService.getAllOrders();
        }
        model.addAttribute("orders", orders);
        return "admin/orders";
    }

    @PostMapping("/orders/update-status")
    @ResponseBody
    public String updateOrderStatus(@RequestParam Long id, @RequestParam String status) {
        try {
            orderService.updateOrderStatus(id, status);
            return "success";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
    }

    @PostMapping("/users/delete/{id}")
    @ResponseBody
    public String deleteUser(@PathVariable Long id) {
        try {
            int result = userService.deleteById(id);
            if (result > 0) {
                return "success";
            } else {
                return "删除失败";
            }
        } catch (Exception e) {
            return "error: " + e.getMessage();
        }
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/admin/login";
    }
}