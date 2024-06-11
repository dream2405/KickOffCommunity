package org.example.kickoffcommunity;

import lombok.RequiredArgsConstructor;
import org.example.kickoffcommunity.database.team.Team;
import org.example.kickoffcommunity.database.team.TeamService;
import org.example.kickoffcommunity.storage.FileUploadService;
import org.example.kickoffcommunity.user.SiteUser;
import org.example.kickoffcommunity.user.UserRepository;
import org.example.kickoffcommunity.user.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.example.kickoffcommunity.board.boardService.TennisBoardService;
import org.example.kickoffcommunity.board.entity.TeamRanking;
import org.example.kickoffcommunity.board.entity.TennisEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/tennis")
@RequiredArgsConstructor
public class TennisLayoutController {
    private final TeamService teamService;
    private final FileUploadService fileUploadService;
    private final TennisBoardService tennisBoardService;
    private final UserRepository userRepository;
    private final UserService userService;

    // 테니스탭의 초기 페이지는 팀
    @GetMapping()
    public String mainLayout(Authentication authentication) {
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
    public String historyLayout(Model model,
                                @RequestParam(value = "page", defaultValue = "1") int page,
                                @RequestParam(value = "date", required = false)
                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        int pageSize = 15;
        Pageable pageable = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Direction.DESC, "date", "reservedtime"));
        Page<TennisEntity> tennisPage;

        if (date != null) {
            tennisPage = tennisBoardService.findMatchesByDate(date, pageable);
        } else {
            tennisPage = tennisBoardService.tennisBoardList(pageable);
        }

        List<TennisEntity> tennisList = new ArrayList<>(tennisPage.getContent());

        tennisList.sort(Comparator.comparing(TennisEntity::getDate, Comparator.reverseOrder())
                              .thenComparing(TennisEntity::getReservedtime, Comparator.reverseOrder()));

        model.addAttribute("tennislist", tennisList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", tennisPage.getTotalPages());
        model.addAttribute("menu", "history");
        model.addAttribute("selectedDate", date);
        model.addAttribute("hasMatches", !tennisList.isEmpty());

        return "main";
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
        // 현재 접속한 유저
        SiteUser siteUser = userService.getCurrentUser().get();

        if (siteUser.getTeam() != null) {
            redirectAttributes.addFlashAttribute("message", "팀은 하나만 만들 수 있습니다!");
            return "redirect:/tennis/team";
        }
        if (team.getName() == null || team.getName().isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "팀이름을 입력해주세요!");
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
        userService.updateUserTeam(siteUser.getUsername(), team.getName());

        fileUploadService.uploadFile(file, name);

        team.setType("tennis");
        team.setImgPath("/img/tennis/" + name);
        team.setLeaderName(siteUser.getName());

        teamService.insertTeam(team);

        return "redirect:/tennis/team";
    }
    
    @GetMapping("/api/teams")
    @ResponseBody
    public Iterable<Team> getAllTeams() {
        return teamService.findAllTeams();
    }
}
