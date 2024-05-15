package org.example.kickoffcommunity.board.boardRepository;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

@Repository
public class TennisReservationRepositoryImpl implements TennisReservationRepository {

    @Autowired
    private EntityManager entityManager;
    // 데이터베이스나 서비스와의 상호작용을 위한 의존성 주입 추가

    @Override
    public void saveReservation(LocalDate date, String location, byte reservedTime) {
        // 예약 정보를 데이터베이스에 저장
        entityManager.createNativeQuery("INSERT INTO tennis_entity (date, reservedtime, location) VALUES (?, ?, ?)")
                .setParameter(1, date)
                .setParameter(2, reservedTime)
                .setParameter(3, location)
                .executeUpdate();
    }

    @Override
    public byte getReservation(LocalDate date, String location) {
        // 지정된 날짜와 장소에 대한 예약 정보를 조회하여 반환
        Query query = entityManager.createNativeQuery("SELECT reservedtime FROM tennis_entity WHERE date = ? AND location = ?");
        query.setParameter(1, date);
        query.setParameter(2, location);
        Object result = query.getSingleResult();
        return result != null ? (byte) result : 0;
    }

    @Override
    public void updateReservation(LocalDate date, String location, byte reservedTime) {
        // 지정된 날짜와 장소에 대한 예약 정보를 업데이트
        entityManager.createNativeQuery("UPDATE tennis_entity SET reservedtime = ? WHERE date = ? AND location = ?")
                .setParameter(1, reservedTime)
                .setParameter(2, date)
                .setParameter(3, location)
                .executeUpdate();
    }

    @Override
    public void deleteReservation(LocalDate date, String location) {
        // 지정된 날짜와 장소에 대한 예약 정보를 삭제
        entityManager.createNativeQuery("DELETE FROM tennis_entity WHERE date = ? AND location = ?")
                .setParameter(1, date)
                .setParameter(2, location)
                .executeUpdate();
    }
}
