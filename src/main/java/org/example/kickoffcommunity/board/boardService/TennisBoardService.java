package org.example.kickoffcommunity.board.boardService;

import java.util.List;

import org.example.kickoffcommunity.board.boardRepository.TennisBoardRepository;
import org.example.kickoffcommunity.board.entity.TennisEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class TennisBoardService {
    
    @Autowired
    private TennisBoardRepository tennisboardRepository;
    
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
}