package org.example.kickoffcommunity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/tennis")
public class TennisLayoutController {
    // 테스트용 목업 데이터 - 추후 DB로 교체 예정
    private List<MockUpTeamData> datas = new ArrayList<>();

    @GetMapping()
    public String mainLayout(Model model) {
        model.addAttribute("teamDatas", datas);
        return "main";
    }

    @RequestMapping("/team/{tab}/{idx}")
    public String tabLayout(@PathVariable String tab, @PathVariable int idx, Model model) {
        model.addAttribute("teamName", datas.get(idx).teamName());
        model.addAttribute("sportsType", "tennis");

        System.out.println(tab);
        switch (tab) {
            case "desc" -> {
                model.addAttribute("tab", "desc");
                model.addAttribute("test1", datas.get(idx).desc());
            }
            case "member" -> {
                model.addAttribute("tab", "member");
                model.addAttribute("test2", "test");
            }
            default -> {
                model.addAttribute("tab", "picture");
                model.addAttribute("test3", "test");
            }
        }
        return "teamDesc";
    }

    @GetMapping("/teamAdd")
    public String teamAdd(Model model) {
        model.addAttribute("sportsType", "tennis");
        model.addAttribute("team", new MockUpTeamData("", "", null, ""));
        return "teamAdd";
    }

    @PostMapping("/submit")
    public String submitUser(@ModelAttribute MockUpTeamData team) {
        if (datas.stream().anyMatch(data -> data.teamName().equals(team.teamName()))) {
            return "redirect:/tennis?error=true";
        }
        datas.add(team);

        return "redirect:/tennis?error=false";
    }
}
