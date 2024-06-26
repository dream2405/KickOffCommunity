package org.example.kickoffcommunity.board.boardRepository;


import java.time.LocalDate;
import java.util.List;

import org.example.kickoffcommunity.board.entity.TennisEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TennisBoardRepository extends JpaRepository<TennisEntity, Integer>{ //예약 정보를 입력받기 위한 repository
    boolean existsByLocationAndDateAndReservedtime(String location, LocalDate date, int reservedtime);
    List<TennisEntity> findByScoreIsNotNull();
    Page<TennisEntity> findAllByDate(LocalDate date, Pageable pageable);
    
}
 