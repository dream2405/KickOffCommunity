package org.example.kickoffcommunity.database;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TeamService {
    private TeamRepository teamRepository;

    @Autowired
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
}
