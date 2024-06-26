package org.example.kickoffcommunity.board.boardService;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.example.kickoffcommunity.board.boardRepository.TennisBoardRepository;
import org.example.kickoffcommunity.board.entity.TeamRanking;
import org.example.kickoffcommunity.board.entity.TennisEntity;
import org.example.kickoffcommunity.database.team.TeamRepository;
import org.example.kickoffcommunity.user.SiteUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TennisBoardService {
    
    @Autowired
    private TennisBoardRepository tennisboardRepository;
    
    @Autowired
    private TeamRepository teamRepository;
    

    public void write(TennisEntity tennisentity, SiteUser user) throws Exception {
        boolean exists = tennisboardRepository.existsByLocationAndDateAndReservedtime(
            tennisentity.getLocation(), 
            tennisentity.getDate(), 
            tennisentity.getReservedtime()
        );
    
        if (exists) {
            throw new Exception("이미 예약된 시간입니다."); // 중복 예약이 있을 경우 예외를 발생시킵니다.
        }
        
        tennisentity.setCreatedBy(user);
        tennisboardRepository.save(tennisentity);
    }
    


    public List<TennisEntity> tennisBoardList(){

        return tennisboardRepository.findAll();
    }

    public TennisEntity boardView(Integer id){

        return tennisboardRepository.findById(id).get();
    }
    
    public boolean teamExistsByName(String teamName) {
        return teamRepository.findByName(teamName).isPresent();
    }

    public void updateTeamB(Integer id, String teamB) {
        TennisEntity tennisEntity = tennisboardRepository.findById(id).get();
        
        tennisEntity.setTeamB(teamB);
        tennisboardRepository.save(tennisEntity);
        
    }
    public void updateScore(Integer id, int scoreA, int scoreB) {
        TennisEntity tennisEntity = tennisboardRepository.findById(id).orElseThrow(() -> new RuntimeException("Tennis entity not found"));

        String score = scoreA + ":" + scoreB;
        tennisEntity.setScore(score);
        tennisboardRepository.save(tennisEntity);
    }
        public List<TeamRanking> calculateTeamRankings() {
        List<TennisEntity> completedMatches = tennisboardRepository.findByScoreIsNotNull();
        Map<String, TeamRanking> teamRankingMap = new HashMap<>();

        for (TennisEntity match : completedMatches) {
            String[] scores = match.getScore().split(":");
            int scoreA = Integer.parseInt(scores[0].trim());
            int scoreB = Integer.parseInt(scores[1].trim());

            String teamA = match.getTeamA();
            String teamB = match.getTeamB();

            teamRankingMap.putIfAbsent(teamA, new TeamRanking(teamA));
            teamRankingMap.putIfAbsent(teamB, new TeamRanking(teamB));

            TeamRanking rankingA = teamRankingMap.get(teamA);
            TeamRanking rankingB = teamRankingMap.get(teamB);

            if (scoreA > scoreB) {
                rankingA.addWin();
                rankingB.addLoss();
            } else if (scoreA < scoreB) {
                rankingA.addLoss();
                rankingB.addWin();
            } else {
                rankingA.addDraw();
                rankingB.addDraw();
            }
        }

        return teamRankingMap.values().stream()
                .sorted(Comparator.comparing(TeamRanking::getPoints).reversed())
                .collect(Collectors.toList());
    }
    public Page<TennisEntity> tennisBoardList(Pageable pageable) {
        return tennisboardRepository.findAll(pageable);
    }
    public Page<TennisEntity> findMatchesByDate(LocalDate date, Pageable pageable) {
    return tennisboardRepository.findAllByDate(date, pageable);
}
    
}
