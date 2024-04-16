package org.example.kickoffcommunity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewLayoutController {
    @GetMapping("/")
    public String mainLayout(Model model) {
        // 기본값 - 테니스탭의 팀 탭을 보여줌
        model.addAttribute("sportsType", "tennis");
        model.addAttribute("menu", "team");

        return "main";
    }
}
