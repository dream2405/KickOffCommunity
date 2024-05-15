package org.example.kickoffcommunity.board.boardRepository;

import java.time.LocalDate;

public interface TennisReservationRepository {  //예약정보를 저장하기 위한 repository
    void saveReservation(LocalDate date, String location, byte reservedtime);
    byte getReservation(LocalDate date, String location);
    void updateReservation(LocalDate date, String location, byte reservedtime);
    void deleteReservation(LocalDate date, String location);
}
