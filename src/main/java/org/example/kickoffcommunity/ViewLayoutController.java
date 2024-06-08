package org.example.kickoffcommunity;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ViewLayoutController {
    @GetMapping("/")
    public String mainLayout(Model model, Authentication authentication) {
        // 기본탭은 테니스
        return "redirect:/tennis";
    }
}
