package org.example.kickoffcommunity;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/tennis")
public class TennisLayoutController {
    @GetMapping()
    public String mainLayout(Model model) {
        // 팀 목록 생성을 위한 임시 데이터 - 추후 DB로 교체 예정
        List<MockUpTeamData> datas = new ArrayList<>();
        datas.add(
                new MockUpTeamData(
                        "불사조",
                        "Kim",
                        new String[] {
                                "1",
                                "2",
                                "3",
                                "4",
                                "5"
                        }
                )
        );
        datas.add(
                new MockUpTeamData(
                        "독수리",
                        "Lee",
                        new String[] {
                                "1",
                                "2",
                                "3",
                                "4",
                                "5",
                                "6"
                        }
                )
        );

        model.addAttribute("teamDatas", datas);
        return "main";
    }
    @GetMapping("/team")
    public String teamLayout(@RequestParam(required = false) String teamName, Model model) {
        model.addAttribute("tmp", teamName);

        return "teamDesc";
    }
}
