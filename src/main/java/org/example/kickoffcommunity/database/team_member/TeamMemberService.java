package org.example.kickoffcommunity.database.team_member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class TeamMemberService {
    private final TeamMemberRepository teamMemberRepository;

    @Autowired
    public TeamMemberService(TeamMemberRepository teamMemberRepository) {
        this.teamMemberRepository = teamMemberRepository;
    }

    public void insertTeamMember(TeamMember teamMember) {
        teamMemberRepository.save(teamMember);
    }

    public Optional<TeamMember> getTeamMemberById(int teamMemberId) {
        return teamMemberRepository.findById(teamMemberId);
    }

    public Iterable<TeamMember> getAllTeamMembers() {
        return teamMemberRepository.findAll();
    }

    public void deleteTeamMemberById(int teamMemberId) {
        this.teamMemberRepository.deleteById(teamMemberId);
    }

    public Iterable<TeamMember> getTeamMembersByTeamName(String teamName) {
        var result = new ArrayList<TeamMember>();
        var teamMembers = teamMemberRepository.findAll();

        for (TeamMember teamMember : teamMembers) {
            if (teamMember.getTeamName().equals(teamName)) {
                result.add(teamMember);
            }
        }
        return result;
    }
}
