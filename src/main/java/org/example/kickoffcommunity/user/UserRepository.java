package org.example.kickoffcommunity.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<SiteUser, Long>{
    Optional<SiteUser> findByusername(String username);
}
