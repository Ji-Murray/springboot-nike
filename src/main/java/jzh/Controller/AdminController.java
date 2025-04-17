package jzh.Controller;

import jzh.Service.AdminService;
import jzh.Service.OrderService;
import jzh.Service.ProductService;
import jzh.Service.UserService;
import jzh.entity.Admin;
import jzh.entity.Order;
import jzh.entity.Product;
import jzh.entity.User;
import jzh.mapper.ProductMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private AdminService adminService;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    private UserService userService;

    @Autowired
    private ProductMapper productMapper;

    @Value("${upload.path}")
    private String uploadPath;

    private final String UPLOAD_DIR = "uploads";

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
    public String listProducts(Model model) {
        List<Product> products = productMapper.findAll();
        model.addAttribute("products", products);
        return "admin/products";
    }

    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute Product product, @RequestParam("image") MultipartFile file) {
        logger.info("开始添加商品，商品名称：{}", product.getName());
        
        if (!file.isEmpty()) {
            try {
                logger.debug("开始处理文件上传，文件名：{}", file.getOriginalFilename());
                
                // 获取项目根目录
                String projectRoot = System.getProperty("user.dir");
                // 设置上传目录为 resources/static/img
                String uploadDir = projectRoot + "/src/main/resources/static/img/";
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                // 生成唯一的文件名
                String originalFilename = file.getOriginalFilename();
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String fileName = UUID.randomUUID().toString() + extension;
                File dest = new File(dir, fileName);
                
                // 保存文件
                file.transferTo(dest);
                
                // 设置图片URL
                String imageUrl = "/img/" + fileName;
                product.setImageUrl(imageUrl);
                
                logger.info("文件上传成功，保存路径：{}", dest.getAbsolutePath());
            } catch (IOException e) {
                logger.error("文件上传失败", e);
                return "redirect:/admin/products?error=upload_failed";
            }
        }
        
        productMapper.insert(product);
        logger.info("商品添加成功，ID：{}", product.getId());
        return "redirect:/admin/products";
    }

    @PostMapping("/products/update")
    @ResponseBody
    public String updateProduct(@RequestParam("id") Long id,
                              @RequestParam("name") String name,
                              @RequestParam("price") BigDecimal price,
                              @RequestParam("stock") Integer stock,
                              @RequestParam("category") String category,
                              @RequestParam(value = "description", required = false) String description,
                              @RequestParam(value = "image", required = false) MultipartFile file) {
        
        logger.info("开始更新商品，ID：{}", id);
        
        // 获取现有商品
        Product existingProduct = productMapper.findById(id);
        if (existingProduct == null) {
            logger.error("商品不存在，ID：{}", id);
            return "商品不存在";
        }

        // 更新商品信息
        existingProduct.setName(name);
        existingProduct.setPrice(price);
        existingProduct.setStock(stock);
        existingProduct.setCategory(category);
        if (description != null) {
            existingProduct.setDescription(description);
        }

        // 处理文件上传
        if (file != null && !file.isEmpty()) {
            try {
                String projectRoot = System.getProperty("user.dir");
                String uploadDir = projectRoot + "/src/main/resources/static/img/";
                File dir = new File(uploadDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                }

                String originalFilename = file.getOriginalFilename();
                String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String fileName = UUID.randomUUID().toString() + extension;
                File dest = new File(dir, fileName);
                
                file.transferTo(dest);
                
                String imageUrl = "/img/" + fileName;
                existingProduct.setImageUrl(imageUrl);
                
                logger.info("文件上传成功，保存路径：{}", dest.getAbsolutePath());
            } catch (IOException e) {
                logger.error("文件上传失败", e);
                return "文件上传失败";
            }
        }
        
        // 更新商品
        productMapper.update(existingProduct);
        logger.info("商品更新成功，ID：{}", existingProduct.getId());
        return "success";
    }

    @PostMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productMapper.deleteById(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/products/edit/{id}")
    public String editProduct(@PathVariable Long id, Model model) {
        Product product = productMapper.findById(id);
        if (product == null) {
            return "redirect:/admin/products?error=product_not_found";
        }
        model.addAttribute("product", product);
        return "admin/product_edit";
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

    @GetMapping("/products/get/{id}")
    @ResponseBody
    public Product getProduct(@PathVariable Long id) {
        return productMapper.findById(id);
    }

    @PostMapping("/products/toggle-recommend/{id}")
    @ResponseBody
    public String toggleRecommend(@PathVariable Long id) {
        try {
            productService.toggleRecommend(id);
            return "success";
        } catch (Exception e) {
            return "error: " + e.getMessage();
        }
    }

    @GetMapping("/products/recommended")
    @ResponseBody
    public List<Product> getRecommendedProducts() {
        return productService.getRecommendedProducts();
    }
}