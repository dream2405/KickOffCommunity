package org.example.kickoffcommunity.board.boardController;

import org.example.kickoffcommunity.board.boardService.TennisBoardService;
import org.example.kickoffcommunity.board.entity.TennisEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class TennisBoardController {
    @Autowired
    private TennisBoardService tennisBoardService;

    @GetMapping("tennis/publish/write")
    public String BoradWrite(Model model) {

        model.addAttribute("tenniswrite", tennisBoardService.tennisBoardList());

        return "fragments/contentFrag/tabFrag/boardFrag/tennis/tennisboardwrite";
    }
    
    @PostMapping("tennis/publish/writepro")
    public String BoardWritePro(TennisEntity tennisentity){
        tennisBoardService.write(tennisentity);
        
        return "";
    }

    @GetMapping("tennis/publish/list")
    public String BoardList(Model model) {

        model.addAttribute("tennislist", tennisBoardService.tennisBoardList());

        return "fragments/contentFrag/tabFrag/boardFrag/tennis/tennisboardlist";
    }
    
    @GetMapping("tennis/publish/view")  // localhost:8080/tennis/publish/view?id=1
    public String BoardView(Model model, Integer id) {

        model.addAttribute("tennisarticle", tennisBoardService.boardView(id));

        return "fragments/contentFrag/tabFrag/boardFrag/tennis/tennisboardview";
    }
    
}
