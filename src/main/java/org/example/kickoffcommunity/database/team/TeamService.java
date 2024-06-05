package org.example.kickoffcommunity.database.team;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamService {
    private final TeamRepository teamRepository;

    
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public void insertTeam(Team team) {
        teamRepository.save(team);
    }

    public Optional<Team> findTeam(Integer id) {
        return teamRepository.findById(id);
    }
    

    public Iterable<Team> findAllTeams() {
        return teamRepository.findAll();
    }

    public int maxInt() {
        int max = 0;
        for (var team : teamRepository.findAll()) {
            if (max < team.getId())
                max = team.getId();
        }
        return max;
    }

    public boolean findByName(String name) {
        for (var team : teamRepository.findAll()) {
            if (team.getName().equals(name))
                return true;
        }
        return false;
    }

    public void deleteTeamById(Integer id) {
        teamRepository.deleteById(id);
    }

    public Team updateTeamImgPath(Integer id, String imgPath) {
        var optionalTeam = teamRepository.findById(id);
        if (optionalTeam.isPresent()) {
            var team = optionalTeam.get();
            team.setImgPath(imgPath);
            return teamRepository.save(team);
        } else {
            return null;
        }
    }

    public boolean teamExists(String teamName) {
        return teamRepository.existsByName(teamName);
    }

    public void updateTeamDesc(Integer id, String desc) {
        var optionalTeam = teamRepository.findById(id);
        if (optionalTeam.isPresent()) {
            var team = optionalTeam.get();
            team.setDesc(desc);
            teamRepository.save(team);
        }
    }
}
