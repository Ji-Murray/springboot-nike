package jzh.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {


    @GetMapping("/orderlist")
    public String index() {
        System.out.println("hello.springboot的第一个controller");
        return "orderlist";
    }



} 