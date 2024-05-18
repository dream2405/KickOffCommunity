package org.example.kickoffcommunity;

import org.example.kickoffcommunity.database.Team;
import org.example.kickoffcommunity.database.TeamService;
import org.example.kickoffcommunity.match_record.MatchRecord;
import org.example.kickoffcommunity.match_record.MatchRecordRepository;
import org.example.kickoffcommunity.match_record.MatchSchedule;
import org.example.kickoffcommunity.match_record.MatchScheduleRepository;
import org.example.kickoffcommunity.storage.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.example.kickoffcommunity.board.boardService.TennisBoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/tennis")
public class TennisLayoutController {
    // 테스트용 목업 데이터 - 추후 DB로 교체 예정
    private List<MockUpTeamData> datas = new ArrayList<>();
    private final TeamService teamService;
    private final FileUploadService fileUploadService;

    @Autowired
    public TennisLayoutController(TeamService teamService, FileUploadService fileUploadService) {
        this.teamService = teamService;
        this.fileUploadService = fileUploadService;
    }


    @Autowired
    private TennisBoardService tennisBoardService;
    


    // 테니스탭의 초기 페이지는 팀
    @GetMapping()
    public String mainLayout() {
        return "redirect:/tennis/team";
    }

    // 테니스 탭의 팀 버튼 클릭시 컨텐츠 model 제어
    @GetMapping("/team")
    public String teamLayout(Model model) {
        model.addAttribute("teamDatas", teamService.findAllTeams());
        model.addAttribute("menu", "team");

        return "main";
    }
    @Autowired
    private MatchRecordRepository matchRecordRepository;
    @Autowired
    private MatchScheduleRepository matchScheduleRepository;


    @GetMapping("/calender")
    public String calenderLayout(Model model) {
        List<MatchSchedule> schedules = matchScheduleRepository.findAll();
        model.addAttribute("schedules", schedules);
        model.addAttribute("menu", "calender");
        return "main";
    }

    // 테니스 탭의 팀순위 버튼 클릭시 컨텐츠 model 제어
    @GetMapping("/ranking")
    public String rankingLayout(Model model) {
        model.addAttribute("menu", "ranking");
        model.addAttribute("ranking", datas);
        return "main";
    }
    @GetMapping("/history")
    public String historyLayout(Model model) {
        List<MatchRecord> records = matchRecordRepository.findAll();
        model.addAttribute("records", records);
        model.addAttribute("menu", "history");
        return "main";
    }
    
    @GetMapping("/publish")
    public String publishLayout(Model model) {
        model.addAttribute("menu", "publish");
        model.addAttribute("pubExample", datas);

        

        return "main";
    }


    @RequestMapping("/team/{tab}/{id}")
    public String tabLayout(@PathVariable String tab, @PathVariable Integer id, Model model) {
        model.addAttribute("team", teamService.findTeam(id).get());

        return "teamDesc";
    }

    @GetMapping("/team/teamAdd")
    public String teamAdd(Model model) {
        model.addAttribute("sportsType", "tennis");
        model.addAttribute("team", new Team());
        return "teamAdd";
    }

    @PostMapping("/submit")
    public String submitUser(@ModelAttribute Team team, @RequestParam("file") MultipartFile file) {
        if (!teamService.findByName(team.getName()) && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            String fileFormat = fileName.substring(fileName.lastIndexOf("."));
            String name = UUID.randomUUID() + fileFormat;

            fileUploadService.uploadFile(file, name);

            team.setType("tennis");
            team.setImgPath("/img/tennis/" + name);

            teamService.insertTeam(team);

            return "redirect:/tennis/team";
        }
        return "redirect:/tennis/team/teamAdd";
    }

    @PostMapping("/{id}")
    public String updateTeam(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
        Team team = teamService.findTeam(id).get();
        String fileName = team.getImgPath().substring(file.getOriginalFilename().lastIndexOf("/") + 1);
        fileUploadService.deleteFile(fileName);

        String newFileName = file.getOriginalFilename();
        String fileFormat = newFileName.substring(newFileName.lastIndexOf("."));
        String name = UUID.randomUUID() + fileFormat;
        fileUploadService.uploadFile(file, name);
        teamService.updateTeamImgPath(id, "/img/tennis/" + name);

        return "redirect:/tennis/team/desc/" + id;
    }
}
