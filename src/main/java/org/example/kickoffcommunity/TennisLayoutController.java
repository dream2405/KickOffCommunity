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

    // 테니스탭의 초기 페이지는 팀
    @GetMapping()
    public String mainLayout() {
        return "redirect:/tennis/team";
    }

    // 테니스 탭의 팀 버튼 클릭시 컨텐츠 model 제어
    @GetMapping("/team")
    public String teamLayout(Model model) {
        model.addAttribute("teamDatas", datas);
        model.addAttribute("menu", "team");

        return "main";
    }

    // 테니스 탭의 팀순위 버튼 클릭시 컨텐츠 model 제어
    @GetMapping("/ranking")
    public String rankingLayout(Model model) {

        return "main";
    }
    @GetMapping("/history")
    public String historyLayout(Model model) {
        // matches 데이터를 모델에 추가하여 템플릿에서 사용할 수 있도록 합니다.
        model.addAttribute("matches", datas);
        return "history"; // history.html을 반환합니다.
    }
    // ...


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

    @GetMapping("/team/teamAdd")
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
