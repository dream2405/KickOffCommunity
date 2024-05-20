package org.example.kickoffcommunity.database.team;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends CrudRepository<Team, Integer> { 
    boolean existsByName(String name);

    Optional<Team> findByName(String teamName);
}
