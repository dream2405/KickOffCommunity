package org.example.kickoffcommunity.database;

import java.util.Optional;

import org.example.kickoffcommunity.board.entity.TennisEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends CrudRepository<Team, Integer> { 
    boolean existsByName(String name);

    Optional<TennisEntity> findByName(String teamName);
}
