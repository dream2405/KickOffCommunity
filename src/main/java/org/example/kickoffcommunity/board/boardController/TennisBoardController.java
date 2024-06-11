package org.example.kickoffcommunity.board.boardController;

import java.security.Principal;
import java.util.Collections;
import java.util.List;

import org.example.kickoffcommunity.board.boardService.TennisBoardService;
import org.example.kickoffcommunity.board.entity.TennisEntity;
import org.example.kickoffcommunity.database.team.TeamService;
import org.example.kickoffcommunity.user.SiteUser;
import org.example.kickoffcommunity.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class TennisBoardController {
    @Autowired
    private TennisBoardService tennisBoardService;
    @Autowired
    private TeamService teamService;
    @Autowired
    private UserService userService;
    
    @GetMapping("tennis/publish/write")
    public String BoradWrite(Model model) {

        model.addAttribute("tenniswrite", tennisBoardService.tennisBoardList());

        return "fragments/contentFrag/tabFrag/boardFrag/tennis/tennisboardwrite";
    }

    @GetMapping("/api/tennis")
    public ResponseEntity<List<TennisEntity>> getTennisData() {
        List<TennisEntity> tennisData = tennisBoardService.tennisBoardList();
        return new ResponseEntity<>(tennisData, HttpStatus.OK);
    }
    
    @PostMapping("tennis/publish/writepro")
    public String BoardWritePro(TennisEntity tennisentity, Model model, Principal principal) {
        SiteUser siteUser = this.userService.getUser(principal.getName());

        try {
            tennisBoardService.write(tennisentity, siteUser);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "tennis/publish"; // 에러 메시지를 표시할 페이지로 리다이렉트합니다.
        }

        return "redirect:/tennis/publish?page=1";
    }


    
    @GetMapping("tennis/publish")
    public String BoardList(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        int pageSize = 15;
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(Sort.Direction.ASC, "date"));
        Page<TennisEntity> tennisPage = tennisBoardService.tennisBoardList(pageable);

        model.addAttribute("tennislist", tennisPage.getContent());  // 페이징된 결과 리스트를 모델에 추가
        model.addAttribute("teamDatas", teamService.findAllTeams());
        model.addAttribute("currentPage", page);
        model.addAttribute("tennisPage", tennisPage);
        model.addAttribute("totalPages", tennisPage.getTotalPages());
        model.addAttribute("menu", "publish");
        return "main";
    }
    


    @GetMapping("tennis/publish/view")  // localhost:8080/tennis/publish/view?id=1
    public String BoardView(Model model, Integer id) {

        model.addAttribute("tennisarticle", tennisBoardService.boardView(id));
        model.addAttribute("teamDatas", teamService.findAllTeams());
        return "fragments/contentFrag/tabFrag/boardFrag/tennis/tennisboardview";
    }
    
        
    

    @GetMapping("/api/checkTeam") //team 테이블에 팀이 존재하는지 확인
    @ResponseBody
    public ResponseEntity<?> checkTeam(@RequestParam String teamName) {
        boolean exists = tennisBoardService.teamExistsByName(teamName);
        return ResponseEntity.ok().body(Collections.singletonMap("exists", exists));
    }

    @PostMapping("tennis/publish/matchingpro") //teamB에 값을 할당
    public String BoardMatchingPro(TennisEntity tennisarticle,@RequestParam("teamB") String teamB, @RequestParam("id") Integer id, Model model) {
        
        try {
            tennisBoardService.updateTeamB(id, teamB);
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "tennis/publish?page=1"; // 에러 메시지를 표시할 페이지로 리다이렉트합니다.
        }

        tennisBoardService.updateTeamB(id, teamB);
        return "redirect:/tennis/publish?page=1";
    }
 
    @GetMapping("/enter-score/{id}")
    public String enterScore(@PathVariable("id") Integer id, Model model) {
        TennisEntity tennisEntity = tennisBoardService.boardView(id);
        model.addAttribute("tennisentity", tennisEntity);
        return "fragments/contentFrag/tabFrag/matchFrag/enter_score";
    }

    @PostMapping("/tennis/calender/score")
    public String ScoreInsert(@RequestParam("scoreA") int scoreA, @RequestParam("scoreB") int scoreB, @RequestParam("id") Integer id) {
        tennisBoardService.updateScore(id, scoreA, scoreB);
        return "redirect:/tennis/calender";
    }
}
