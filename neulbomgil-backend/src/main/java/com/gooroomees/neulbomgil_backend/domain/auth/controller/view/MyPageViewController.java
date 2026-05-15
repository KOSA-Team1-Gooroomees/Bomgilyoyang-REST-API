package com.gooroomees.neulbomgil_backend.domain.auth.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyPageViewController {

    @GetMapping("/mypage")
    public String mypageView(Model model) {
        return "auth/mypage";
    }

}
