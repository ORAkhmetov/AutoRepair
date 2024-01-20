package ru.akhmetov.AutoRepair.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;
/**  Чтения данных о пользователях  **/

@RequiredArgsConstructor
@Service
public class AUsersService {
    private final AUsersRepository aUsersRepository;

    public Optional<AUser> getAUserByUsername(String username) {
        return aUsersRepository.getAUserByUsername(username);
    }
}
