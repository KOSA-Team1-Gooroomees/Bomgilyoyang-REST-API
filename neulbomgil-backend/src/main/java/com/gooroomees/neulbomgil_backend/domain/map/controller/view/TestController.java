package com.gooroomees.neulbomgil_backend.domain.map.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/test")
    public String tailwindTest(Model model) {
        model.addAttribute("name", "재욱");
        return "map/test";
    }
}