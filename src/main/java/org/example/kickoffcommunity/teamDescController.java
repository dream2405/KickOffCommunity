package org.example.kickoffcommunity;

import lombok.RequiredArgsConstructor;
import org.example.kickoffcommunity.database.team.Team;
import org.example.kickoffcommunity.database.team.TeamService;
import org.example.kickoffcommunity.storage.FileUploadService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class teamDescController {
    private final TeamService teamService;
    private final FileUploadService fileUploadService;

    @GetMapping("/{sportType}/team/*/{id}")
    public String tabLayout(@PathVariable String sportType, @PathVariable Integer id) {
        String redirectURL = '/' + sportType + "/team/desc/" + id + "/none";

        return "redirect:" + redirectURL;
    }

    @GetMapping("/{sportType}/team/*/{id}/{isEdit}")
    public String teamLayout(@PathVariable String sportType, @PathVariable Integer id, @PathVariable String isEdit, Model model) {
        var team = teamService.findTeam(id).get();
        model.addAttribute("team", team);
        model.addAttribute("redirectURL", '/' + sportType + "/team");
        model.addAttribute("editInput", new EditInput(team.getDesc()));

        // 글 수정 버튼을 클릭했으면 edit, 아니면 none
        boolean isVisible = !Objects.equals(isEdit, "edit");

        model.addAttribute("isVisible", isVisible);

        return "teamDesc";
    }

    @PostMapping("/*/team/{id}")
    public String updateTeamImg(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
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

    @PostMapping("/{sportType}/team/desc/{id}/delete")
    public String deleteTeam(@PathVariable String sportType, @PathVariable Integer id) {
        // 삭제 대상인 팀
        Team team = teamService.findTeam(id).get();

        // 서버에 저장된 이미지 파일 삭제
        String fileName = team.getImgPath().substring(team.getImgPath().lastIndexOf("/") + 1);
        fileUploadService.deleteFile(fileName);

        // DB에서 팀 삭제
        teamService.deleteTeamById(id);

        String redirectURL = '/' + sportType + "/team";
        return "redirect:" + redirectURL;
    }

    @PostMapping("/{sportType}/team/desc/{id}/is-edit")
    public String editCheck(@PathVariable String sportType, @PathVariable Integer id, @ModelAttribute("editForm") EditFormData editFormData) {
        // 소개글 수정 버튼을 클릭했으면 뒤에 "edit"이 붙고, 아니면 "none"이 붙음
        String redirectURL = '/' + sportType + "/team/desc/" + id + '/' + editFormData.getStr();

        return "redirect:" + redirectURL;
    }

    @PostMapping("/{sportType}/team/desc/{id}/edit-desc")
    public String editDesc(@PathVariable String sportType, @PathVariable Integer id, @ModelAttribute("editInput") EditInput editInput) {
        teamService.updateTeamDesc(id, editInput.getInputTxt());
        String redirectURL = '/' + sportType + "/team/desc/" + id + "/none";

        return "redirect:" + redirectURL;
    }
}
