package jzh.Controller;

import jzh.Service.ProductService;
import jzh.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/new")
    public String showProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        
        // 添加分类数量统计
        Map<String, Integer> categoryCounts = new HashMap<>();
        categoryCounts.put("新品", productService.getProductCountByCategory("新品"));
        categoryCounts.put("男子", productService.getProductCountByCategory("男子"));
        categoryCounts.put("女子", productService.getProductCountByCategory("女子"));
        categoryCounts.put("儿童", productService.getProductCountByCategory("儿童"));
        model.addAttribute("categoryCounts", categoryCounts);
        
        return "new";
    }

    @GetMapping("/products")
    public String getProductsByCategory(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String[] genders,
            @RequestParam(required = false) String price,
            Model model) {
        
        List<Product> products;
        if (category != null) {
            products = productService.getProductsByCategory(category);
        } else if (genders != null || price != null) {
            products = productService.getFilteredProducts(genders, price);
        } else {
            products = productService.getAllProducts();
        }
        
        model.addAttribute("products", products);
        
        // 添加分类数量统计
        Map<String, Integer> categoryCounts = new HashMap<>();
        categoryCounts.put("新品", productService.getProductCountByCategory("新品"));
        categoryCounts.put("男子", productService.getProductCountByCategory("男子"));
        categoryCounts.put("女子", productService.getProductCountByCategory("女子"));
        categoryCounts.put("儿童", productService.getProductCountByCategory("儿童"));
        model.addAttribute("categoryCounts", categoryCounts);
        
        return "new";
    }

    @GetMapping("/product/{id}")
    public String getProductDetail(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        if (product == null) {
            return "redirect:/shouye";
        }
        model.addAttribute("product", product);
        return "detail";
    }
} 