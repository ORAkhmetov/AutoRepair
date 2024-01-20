package ru.akhmetov.AutoRepair.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Oleg Akhmetov on 28.12.2022
 */
@Repository
public interface AUsersRepository extends JpaRepository<AUser, Integer> {
    Optional<AUser> getAUserByUsername(String username);
}
