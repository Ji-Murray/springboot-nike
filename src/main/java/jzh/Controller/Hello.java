package jzh.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Hello {

    @GetMapping("/about")
    public String index1() {
        System.out.println("hello.springboot的第一个controller");
        return "about";
    }

}
