package org.example.kickoffcommunity.match_record;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MatchRecordController {
    @Autowired
    private MatchScheduleRepository matchScheduleRepository;

    @Autowired
    private MatchRecordRepository matchRecordRepository;


    

    @GetMapping("/enter-score")
    public String enterScore(@RequestParam("id") Long id, Model model) {
        MatchRecord matchRecord = matchRecordRepository.findById(id).orElse(null);
        model.addAttribute("match", matchRecord);
        return "enter_score";
    }

    @PostMapping("/save-score")
    public String saveScore(@RequestParam("id") Long id, @RequestParam("score") String score) {
        MatchRecord matchRecord = matchRecordRepository.findById(id).orElse(null);
        if (matchRecord != null) {
            matchRecord.setScore(score);
            matchRecordRepository.save(matchRecord);
        }
        return "redirect:/calendar";
    }
}
