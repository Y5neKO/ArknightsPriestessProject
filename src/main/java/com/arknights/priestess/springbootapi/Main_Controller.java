package com.arknights.priestess.springbootapi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Main_Controller {
    @GetMapping("/api/showLog")
    public String showLog() {
        return "test";
    }
}
