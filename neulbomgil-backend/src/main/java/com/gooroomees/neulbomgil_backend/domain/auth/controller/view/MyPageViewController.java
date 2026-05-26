package com.gooroomees.neulbomgil_backend.domain.auth.controller.view;

import com.gooroomees.neulbomgil_backend.domain.auth.entity.UserAuth;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyPageViewController {

    @GetMapping("/mypage")
    public String mypageView(@AuthenticationPrincipal UserAuth userAuth, Model model) {
        if (userAuth == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", userAuth);
        return "auth/mypage";
    }

}

