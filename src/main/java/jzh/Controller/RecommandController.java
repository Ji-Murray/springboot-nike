package jzh.Controller;

import jzh.Service.ProductService;
import jzh.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RecommandController {

    @Autowired
    private ProductService productService;

    @GetMapping("/shouye")
    public String home(Model model) {
        // 获取推荐商品
        List<Product> recommendedProducts = productService.getRecommendedProducts();
        model.addAttribute("recommendedProducts", recommendedProducts);
        return "shouye";
    }


}