package com.gooroomees.neulbomgil_backend.domain.auth.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthViewController {

    @GetMapping("/login")
    public String loginView(Model model) {
        return "auth/login";
    }

}
