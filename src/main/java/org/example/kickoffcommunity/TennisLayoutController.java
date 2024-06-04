package org.example.kickoffcommunity;

import org.example.kickoffcommunity.database.team.Team;
import org.example.kickoffcommunity.database.team.TeamService;
import org.example.kickoffcommunity.storage.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.example.kickoffcommunity.board.boardService.TennisBoardService;
import org.example.kickoffcommunity.board.entity.TeamRanking;
import org.example.kickoffcommunity.board.entity.TennisEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/tennis")
public class TennisLayoutController {
    // 테스트용 목업 데이터 - 추후 DB로 교체 예정
    private List<MockUpTeamData> datas = new ArrayList<>();
    private final TeamService teamService;
    private final FileUploadService fileUploadService;

    
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
    

    @GetMapping("/calender")
    public String calenderLayout(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        int pageSize = 20;
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.ASC, "date"));
        Page<TennisEntity> tennisPage = tennisBoardService.tennisBoardList(pageable);
    
        // 모든 테니스 경기 일정 가져오기
        List<TennisEntity> tennisList = new ArrayList<>(tennisPage.getContent());
    
        // date와 reservedtime을 기준으로 정렬
        tennisList.sort(Comparator.comparing(TennisEntity::getDate)
                                  .thenComparing(TennisEntity::getReservedtime));
    
        // 모델에 정렬된 테니스 경기 일정 추가
        model.addAttribute("tennislist", tennisList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", tennisPage.getTotalPages());
        model.addAttribute("menu", "calender");
        return "main";
    }
    
   
    // 테니스 탭의 팀순위 버튼 클릭시 컨텐츠 model 제어
     @GetMapping("/ranking")
    public String rankingLayout(Model model) {
        List<TeamRanking> rankings = tennisBoardService.calculateTeamRankings();
        model.addAttribute("rankings", rankings);
        model.addAttribute("menu", "ranking");
        return "main";
    }
    @GetMapping("/history")
    public String historyLayout(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        int pageSize = 15;
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.DESC, "date", "reservedtime"));
        Page<TennisEntity> tennisPage = tennisBoardService.tennisBoardList(pageable);
    
        // 모든 테니스 경기 일정 가져오기
        List<TennisEntity> tennisList = new ArrayList<>(tennisPage.getContent());
    
        // date와 reservedtime을 기준으로 내림차순 정렬
        tennisList.sort(Comparator.comparing(TennisEntity::getDate, Comparator.reverseOrder())
                                  .thenComparing(TennisEntity::getReservedtime, Comparator.reverseOrder()));
    
        // 모델에 정렬된 테니스 경기 일정 추가
        model.addAttribute("tennislist", tennisList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", tennisPage.getTotalPages());
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
        model.addAttribute("redirectURL", "/tennis/team");
        return "teamAdd";
    }

    @PostMapping("/submit")
    public String submitUser(@ModelAttribute Team team, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (team.getName() == null || team.getName().isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "팀이름을 입력해주세요!");
            return "redirect:/tennis/team/teamAdd";
        }
        if (team.getLeaderName() == null || team.getLeaderName().isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "팀장 이름을 입력해주세요!");
            return "redirect:/tennis/team/teamAdd";
        }
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "이미지 파일을 업로드해주세요!");
            return "redirect:/tennis/team/teamAdd";
        }
        if (teamService.findByName(team.getName())) {
            redirectAttributes.addFlashAttribute("message", "이미 있는 팀이름입니다!");
            return "redirect:/tennis/team/teamAdd";
        }
        String fileName = file.getOriginalFilename();
        String fileFormat = fileName.substring(fileName.lastIndexOf("."));
        String name = UUID.randomUUID() + fileFormat;

        fileUploadService.uploadFile(file, name);

        team.setType("tennis");
        team.setImgPath("/img/tennis/" + name);

        teamService.insertTeam(team);

        return "redirect:/tennis/team";
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
