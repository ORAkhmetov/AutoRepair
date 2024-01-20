package ru.akhmetov.AutoRepair.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
/**  Репозиторий Spring Data JPA, предназначенный для работы с сущностью "aUser"  **/

@Repository
public interface AUsersRepository extends JpaRepository<AUser, Integer> {
    Optional<AUser> getAUserByUsername(String username);
}
