package org.example.kickoffcommunity.board.boardService;

import java.util.List;

import org.example.kickoffcommunity.board.boardRepository.TennisBoardRepository;
import org.example.kickoffcommunity.board.entity.TennisEntity;
import org.example.kickoffcommunity.database.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class TennisBoardService {
    
    @Autowired
    private TennisBoardRepository tennisboardRepository;
    
    @Autowired
    private TeamRepository teamRepository;

    public void write(TennisEntity tennisentity){

        boolean exists = tennisboardRepository.existsByLocationAndDateAndReservedtime(tennisentity.getLocation(), tennisentity.getDate(), tennisentity.getReservedtime());

        if(!exists){
            tennisboardRepository.save(tennisentity);
            
        }else{
            throw new DataIntegrityViolationException("이미 해당하는 예약이 존재합니다.");
        }
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
    public void updateScore(Integer id, String score) {
        TennisEntity tennisEntity = tennisboardRepository.findById(id).get();
        
        tennisEntity.setScore(score);
        tennisboardRepository.save(tennisEntity);
        
    }
}
