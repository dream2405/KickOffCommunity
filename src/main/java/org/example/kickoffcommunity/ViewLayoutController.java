package org.example.kickoffcommunity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewLayoutController {
    @GetMapping("/")
    public String mainLayout(Model model) {

        return "main";
    }
    @GetMapping("/header")
    public String headerLayout(Model model) {

        return "fragments/header";
    }
    @GetMapping("/basketball")
    public String basketballLayout(Model model) {

        return "/basketball";
    }
    @GetMapping("/tennis")
    public String tennisLayout(Model model) {

        return "/tennis";
    }
    @GetMapping("/futsal")
    public String futsalLayout(Model model) {

        return "/futsal";
    }
}
